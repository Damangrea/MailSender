/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telnetapps.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import telnetapps.ConstVar;
import telnetapps.TelnetApps;

/**
 *
 * @author Damangrea
 */
public class Gate extends Thread{
     int iSocket;
    public Gate(int iSocket) {
        this.iSocket=iSocket;
    }

    @Override
    public void run() {
        try {
                // TODO code application logic here
                ServerSocket ssc=new ServerSocket(iSocket);
                Socket sClient;
                while(true){
                    sClient=ssc.accept();
                    ClientHandler cHandler=new ClientHandler(sClient);
                    cHandler.start();
                }
            } catch (IOException ex) {
                Logger.getLogger(TelnetApps.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
        
        
    private class ClientHandler extends Thread{
        BufferedReader br;
        BufferedWriter bw;
        Socket sClient;
        
        public ClientHandler(Socket sClient) throws IOException {
            this.sClient=sClient;
            br= new BufferedReader(new InputStreamReader(sClient.getInputStream()));
            bw= new BufferedWriter(new OutputStreamWriter(sClient.getOutputStream()));
        }

        @Override
        public void run() {
            String sData;
            String sResponds;
            boolean bGuess=false;
            int iGuess;
            try { 
                while ((sData=br.readLine())!=null) {
                    System.out.println("Getting Input :"+sData);
                    //process
                     //checking email
                     if(sData.endsWith("@packet-systems.com")){
                         ConstVar.connector.addResetAccount(sData.trim());
                     }
                    //end of process
                    bw.append("ACK-"+sData);
                    bw.newLine();
                    bw.flush();
                }
                sClient.close();
            } catch (IOException ex) {
                Logger.getLogger(TelnetApps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
