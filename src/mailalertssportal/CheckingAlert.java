/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailalertssportal;

import email.*;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;
import mysql.Connector;
import sun.security.provider.MD5;
/**
 *
 * @author Damangrea
 */
public class CheckingAlert {
    public static void main(String [] args) throws IOException
   {   
       String emailUser=args[0];
       String emailPassword=args[1];
       ArrayList<AlertCheck> listAlert = new ArrayList<>();
       Connector connector = new Connector();
       listAlert = connector.getAlertCheck();
       AlertCheck alertCheck;
       //CREATE PARAMETER SEND EMAIL
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
                    return new PasswordAuthentication(emailUser
                            ,emailPassword);
                }
            });
            
            for (int i = 0; i < listAlert.size(); i++) {
                alertCheck = listAlert.get(i);
                //LOOPING SENDING FROM HERE
                 // -- Create a new message --
                 final MimeMessage msg = new MimeMessage(session);

                 // -- Set the FROM and TO fields --
                 msg.setFrom(new InternetAddress("it.support@packet-systems.com"));
                 msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("damangrea.balanrayoga@packet-systems.com", false));
//                 msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(alertCheck.getMailTo()+alertCheck.getMailTo2()+alertCheck.getMailTo3(), false));
                 msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(alertCheck.getMailSelf(), false));


                 msg.setSubject(alertCheck.getTitle());
                 msg.setText(alertCheck.getContents(), "utf-8");


                 SMTPTransport t = (SMTPTransport)session.getTransport("smtp");

                 t.connect("outlook.office365.com", emailUser, emailPassword);
                 t.sendMessage(msg, msg.getAllRecipients());
                 t.close();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(CheckingAlert.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
