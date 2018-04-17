/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telnetapps.collector;

import java.net.Socket;

/**
 *
 * @author Damangrea
 */
public class MessageData {
    Socket sck;
    String sMessage;

    public MessageData(Socket sck,String sMessage) {
        this.sck=sck;
        this.sMessage=sMessage;
    }
    
}
