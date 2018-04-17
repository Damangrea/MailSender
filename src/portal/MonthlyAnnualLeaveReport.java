/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import email.*;
import mysql.Connector;
import com.sun.mail.smtp.SMTPTransport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Damangrea
 */
public class MonthlyAnnualLeaveReport {
    public static void main(String [] args)
   {    
        try {
            /*
                Create Mail Content
            */
            Connector connector=new Connector();
            StringBuilder sb=new StringBuilder();
            Calendar cal = Calendar.getInstance();
            Date d = cal.getTime();
            
            cal.add(Calendar.MONTH, -1);
            Date searchDate = cal.getTime();

            //Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat sd= new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdMonth= new SimpleDateFormat("MM");
            SimpleDateFormat sdYear= new SimpleDateFormat("yyyy");
            String excelFileName = "D:/Annual_leave_"+sd.format(d)+".xls";//name of excel file
            String sheetName = "Annual Leave";//name of sheet
            
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(sheetName) ;
            
            //opening message at cel 0,0
            HSSFRow rowTitle = sheet.createRow(0);
            HSSFCell cellTitle = rowTitle.createCell(0);
            //span column for title
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
            cellTitle.setCellValue("Engineer that take annual leave");
            
            //Table Title
            HSSFRow rowTableTitle = sheet.createRow(2);
            HSSFCell cellTableTitle = rowTableTitle.createCell(0);
            HSSFCell cellTableTitle2 = rowTableTitle.createCell(1);
            HSSFCell cellTableTitle3 = rowTableTitle.createCell(2);
            cellTableTitle.setCellValue("No");
            cellTableTitle2.setCellValue("Name");
            cellTableTitle3.setCellValue("Date");
            
            
            HSSFRow rowTableContent;
            HSSFCell cellTableContent,cellTableContent2,cellTableContent3;
            //Table Content
            ArrayList<String[]> ar= connector.getMonthlyAnnualLeave(Integer.parseInt(sdMonth.format(searchDate)),Integer.parseInt(sdYear.format(searchDate)));
            String[] temp;
            for (int i = 0; i < ar.size(); i++) {
                temp = ar.get(i);
                //sb.append(ar.get(i)).append(System.lineSeparator()).append(System.lineSeparator()) ;
                rowTableContent = sheet.createRow(i+3);
                cellTableContent = rowTableContent.createCell(0);
                cellTableContent2 = rowTableContent.createCell(1);
                cellTableContent3 = rowTableContent.createCell(2);
                cellTableContent.setCellValue(temp[0]);
                cellTableContent2.setCellValue(temp[1]);
                cellTableContent3.setCellValue(temp[2]);
            }
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
            } catch (Exception ex) {
                Logger.getLogger(MonthlyAnnualLeaveReport.class.getName()).log(Level.SEVERE, null, ex);
            }
		
            /*
                Setting Mail Sender
            */
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "m.outlook.com");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("damangrea.balanrayoga@packet-systems.com",
                            "Jakarta##");
                }
            });
            
            // -- Create a new message --
            final MimeMessage msg = new MimeMessage(session);
            
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("it.support@packet-systems.com"));
//            msg.setFrom(new InternetAddress("it.support@packet-systems.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dmg_tb@yahoo.co.id,damangrea.balanrayoga@packet-systems.com", false));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("arenda.sebayang@packet-systems.com,damangrea.balanrayoga@packet-systems.com", false));
            
//        if (ccEmail.length() > 0) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//        }
            
            msg.setSubject("[SS Portal] Annual Leave Report");
//            msg.setSentDate(new Date());
            
            MimeBodyPart textBodyPart = new MimeBodyPart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(excelFileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Annual_leave_"+sd.format(d)+".xls");
            sb.append("Please find the attachment in this email for annual leave report");
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
            sb.append("Thank You.");
            textBodyPart.setText(sb.toString(), "utf-8");
            
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);
            
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            
//            t.connect("smtp.gmail.com", "damangrea", "dian313131");
            System.out.println("start connect");
            t.connect("smtp.office365.com", "damangrea.balanrayoga@packet-systems.com", "Indonesia1945");
//            t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            System.out.println("connect");
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("get recipient");
            t.close();
            System.out.println("close");
        } catch (MessagingException ex) {
            Logger.getLogger(MonthlyAnnualLeaveReport.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

}
