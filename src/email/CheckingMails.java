/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import mysql.Connector;

/**
 *
 * @author Damangrea
 */
public class CheckingMails {

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
      Folder emailFolder = store.getFolder("resetpass");
      Folder emailFolderDone = store.getFolder("resetdone");
      emailFolder.open(Folder.READ_WRITE);
      emailFolderDone.open(Folder.READ_WRITE);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      
      //System.out.println("messages.length---" + messages.length);
      emailFolder.copyMessages(messages, emailFolderDone);
      
          Connector conn=new Connector();
      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i + 1));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
         
         String sFrom="";
         String sPureFrom="";
          try {
              Address[] addr;
              addr=message.getFrom();
              for (Address addr1 : addr) {
                  sFrom=addr1.toString();
              }
              if(sFrom.contains(">")){
                sPureFrom=sFrom.substring(sFrom.indexOf("<",0)+1, sFrom.length()-1);
                sPureFrom=sPureFrom.trim();
              }else{
                sPureFrom=sFrom.substring(sFrom.indexOf("<",0)+1, sFrom.length());
                sPureFrom=sPureFrom.trim();
              }
          } catch (Exception e) {
              sPureFrom=message.getFrom().toString();
          }
         System.out.println("Pure From: " + sPureFrom);
         System.out.println(conn.addResetAccount(sPureFrom));
         
         message.setFlag(Flags.Flag.DELETED, true);
      }
      

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
      String username = "it.support@packet-systems.com";// change accordingly
      String password = "Kucinggarong";// change accordingly

      check(host, mailStoreType, username, password);
      
   }

}
