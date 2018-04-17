/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Damangrea
 */
public class StaticData {
    static HashMap<String,ArrayList<String>> hm_mail;
    
    public static void refreshMap(){
        
    }
    
    public static ArrayList<String> getRecipientGroup(String p_group){
        return hm_mail.get(p_group);
    }
}
