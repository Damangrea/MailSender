/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Damangrea
 */
public class Forwarder {
    public static void forwardEmail(ArrayList<String> arMailTo,String sMailSubject,String sMailMessage)
    {
        StringBuilder sbMailTo;
       try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            
            // Get a Properties object
            Properties props = System.getProperties();
//            props.setProperty("mail.smtps.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.host", "ironport-tbs-1.packet-systems.com");
            props.setProperty("mail.smtp.port", "25");
            //props.setProperty("mail.smtps.auth", "true");
            
            /*
            If set to false, the QUIT command is sent and the connection is immediately closed. If set
            to true (the default), causes the transport to wait for the response to the QUIT command.
            
            ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
            http://forum.java.sun.com/thread.jspa?threadID=5205249
            smtpsend.java - demo program from javamail
            */
//            props.put("mail.smtps.quitwait", "false");
            
            Session session = Session.getInstance(props, null);
            
            // -- Create a new message --
            final MimeMessage msg = new MimeMessage(session);
            
            // -- Set the FROM and TO fields --
//            msg.setFrom(new InternetAddress("damangrea@gmail.com"));
            msg.setFrom(new InternetAddress("it.support@packet-systems.com"));
            sbMailTo= new StringBuilder();
            for (int i = 0; i < arMailTo.size(); i++) {
                if(sbMailTo.toString().length()>0)
                {
                    sbMailTo.append(",");
                }
               sbMailTo.append(arMailTo.get(i));
           }
            System.out.println("MailTO : "+sbMailTo.toString());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sbMailTo.toString(), false));
            
//        if (ccEmail.length() > 0) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//        }
            msg.setSubject(sMailSubject);
            msg.setText(sMailMessage, "utf-8");
//            msg.setSentDate(new Date());
            
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            
//            t.connect("smtp.gmail.com", "damangrea", "dian313131");
            t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
