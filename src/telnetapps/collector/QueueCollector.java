/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telnetapps.collector;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Damangrea
 */
public class QueueCollector {
    private static ArrayList<MessageData> alQueueInput=new ArrayList();
    public static void addValue(Socket sck,String sData){
        synchronized(alQueueInput){
            alQueueInput.add(new MessageData(sck, sData));
        }
    };
    public static MessageData getValue(){
      synchronized(alQueueInput){
          return alQueueInput.get(0);
      }  
    };
}
