package com.course.web.api;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        try {

            //  Leer variables de entorno
            String dbUrl = System.getenv("BD_HOST");
            String dbUser = System.getenv("BD_USERNAME");
            String dbPass = System.getenv("BD_PASSWORD");
            String dbName = System.getenv("BD_NAME");

            String smtpUser = "sdc.giancarlosrojas@gmail.com";
            String smtpPass = "ormd pbrh cckz kfkx";
            String emailTo = System.getenv("EMAIL_TO");

            //  1. Consultar la base de datos
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + dbUrl + ";databaseName=" + dbName + ";encrypt=false", dbUser, dbPass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbo.employees WHERE admission_date >= '2021-01-01' AND admission_date <= '2022-12-31'");

            //  2. Crear archivo Excel
            File excelFile = new File("empleados.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Empleados");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nombre");
            header.createCell(2).setCellValue("Número de Documento");
            header.createCell(3).setCellValue("Salario");
            header.createCell(4).setCellValue("Edad");
            header.createCell(5).setCellValue("Puesto");
            header.createCell(6).setCellValue("Fecha de Ingreso");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id"));
                row.createCell(1).setCellValue(rs.getString("name"));
                row.createCell(2).setCellValue(rs.getString("document_number"));
                row.createCell(3).setCellValue(rs.getInt("salary"));
                row.createCell(4).setCellValue(rs.getInt("age"));
                row.createCell(5).setCellValue(rs.getString("profile"));
                row.createCell(6).setCellValue(rs.getString("admission_date"));

            }

            try (FileOutputStream out = new FileOutputStream(excelFile)) {
                workbook.write(out);
            }
            workbook.close();
            rs.close();
            stmt.close();
            conn.close();

            //  3. Enviar correo con adjunto
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUser, smtpPass);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Reporte Empleados - Examen Técnico Oechsle");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Adjunto el archivo Excel con los empleados.");

            MimeBodyPart filePart = new MimeBodyPart();
            filePart.attachFile(excelFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(filePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correo enviado con el Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}