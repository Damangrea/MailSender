/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;
import sun.security.provider.MD5;
/**
 *
 * @author Damangrea
 */
public class MailSender365 {
    public static void main(String [] args) throws IOException
   {    
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            
            // Get a Properties object
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "outlook.office365.com");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("damangrea.balanrayoga@packet-systems.com"
                            ,"Indonesia1945");
                }
            });
            
            /*
            If set to false, the QUIT command is sent and the connection is immediately closed. If set
            to true (the default), causes the transport to wait for the response to the QUIT command.
            
            ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
            http://forum.java.sun.com/thread.jspa?threadID=5205249
            smtpsend.java - demo program from javamail
            */
//            props.put("mail.smtps.quitwait", "false");
                        
            // -- Create a new message --
            final MimeMessage msg = new MimeMessage(session);
            msg.addHeaderLine("method=REQUEST");
            msg.addHeaderLine("charset=UTF-8");
            msg.addHeaderLine("component=VEVENT");

            msg.setFrom(new InternetAddress("damangrea.balanrayoga@packet-systems.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dmg_tb@yahoo.co.id,damangrea.balanrayoga@packet-systems.com", false));
            msg.setSubject("Recorded SSPortal Activity");
            StringBuffer sb = new StringBuffer();
            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                    "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                    "VERSION:2.0\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VEVENT\n" +
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:damangrea.balanrayoga@packet-systems.com\n" +
                    "ORGANIZER:MAILTO:damangrea.balanrayoga@packet-systems.com\n" +
                    "DTSTART:20180418T053000Z\n" +
                    "DTEND:20180418T060000Z\n" +
                    "LOCATION:Customer Site\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:"+UUID.randomUUID()+".packet-systems.com\n" +
                    "DTSTAMP:20180418T080102Z\n" +
                    "CATEGORIES:Meeting\n" +
                    "DESCRIPTION:This the description of the meeting.\n\n" +
                    "SUMMARY:Test meeting request\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:PT1440M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR");
            
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

            // Create a Multipart
            Multipart multipart = new MimeMultipart();

            // Add part one
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(multipart);
            
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            
            t.connect("outlook.office365.com", "damangrea.balanrayoga@packet-systems.com", "Indonesia1945");
//            t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender365.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
