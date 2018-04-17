/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import com.sun.mail.smtp.SMTPTransport;
import data.StaticData;
import static email.MoveMails.check;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Damangrea
 */
public class RelayMail {
     public static void relay(String host, String storeType, String user,
      String password) 
   {
      try {

      //create properties field
      Properties properties = new Properties();
      properties.setProperty("mail.store.protocol", "imaps");
        
      Session emailSession = Session.getDefaultInstance(properties);
  
      Store store = emailSession.getStore("imaps");
      store.connect(host, user, password);

      Folder[] f = store.getDefaultFolder().list();
      Folder emailFolder = store.getFolder("relay");
      emailFolder.open(Folder.READ_WRITE);

      StaticData.refreshMap();
      
      SMTPTransport t = (SMTPTransport)emailSession.getTransport("smtp");

      t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            
      int iCount = emailFolder.getMessageCount();
      System.out.println("messages.total ---" + iCount);
        Message[] messages = new Message[iCount];
        for (int i = 0, n = iCount; i < n; i++) {
           Message message = emailFolder.getMessage(i+1);
           messages[i]=message;
           //relay send mail
           t.sendMessage(message, message.getAllRecipients());
           //end process
           message.setFlag(Flags.Flag.DELETED, true);
        }
        
      t.close();
      

      //close the store and folder objects
      emailFolder.close(true);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
     
     
   public static void main(String[] args) {

//      String host = "pop.bizmail.yahoo.com";// change accordingly
      String host = "imap.mail.yahoo.com";// change accordingly
      String mailStoreType = "pop3";
      String username = "damangrea.balanrayoga@packet-systems.com";// change accordingly
      String password = "310189";// change accordingly

      relay(host, mailStoreType, username, password);
      
   }
}
