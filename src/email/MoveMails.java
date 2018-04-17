/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Damangrea
 */
public class MoveMails {

   public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

      //create properties field
      Properties properties = new Properties();

//      properties.put("mail.pop3.host", host);
//      properties.put("mail.pop3.port", "995");
//      properties.put("mail.pop3.starttls.enable", "true");
      
        properties.setProperty("mail.store.protocol", "imaps");
        
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      
//      Store store = emailSession.getStore("pop3s");
        Store store = emailSession.getStore("imaps");

      store.connect(host, user, password);

      Folder[] f = store.getDefaultFolder().list();
      Folder emailFolder = store.getFolder("tempFolder");
      Folder emailFolder2 = store.getFolder("fromX");
      emailFolder.open(Folder.READ_WRITE);
      emailFolder2.open(Folder.READ_WRITE);

      // retrieve the messages from the folder in an array and print it
//      Message[] messages = emailFolder.getMessages();
//      emailFolder.copyMessages(messages, emailFolder2);
      int iCount = emailFolder.getMessageCount();
      System.out.println("messages.total ---" + iCount);
        Message[] messages = new Message[iCount];
        for (int i = 0, n = iCount; i < n; i++) {
           Message message = emailFolder.getMessage(i+1);
           messages[i]=message;
                System.out.println(i +". Email from " + message.getFrom()[0] + " Subject: " + message.getSubject()+" copied");
        }
        emailFolder.copyMessages(messages, emailFolder2);
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            message.setFlag(Flags.Flag.DELETED, true);
            System.out.println(i +". Email from " + message.getFrom()[0] + "Subject: " + message.getSubject()+" moved");
        }
      

      //close the store and folder objects
      emailFolder.close(true);
      emailFolder2.close(true);
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

      check(host, mailStoreType, username, password);
      
   }


    
}
