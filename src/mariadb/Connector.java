/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Damangrea
 */
public class Connector {
    static Connection con;

    public Connector() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mariadb://10.0.101.112/activity",
                    "wordpressuser",
                    "password");
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://10.0.101.112/activity",
                    "wordpressuser",
                    "password");
            
            Statement stmt= con.createStatement();
//            String insert="insert into Activity values('saya',now(),now(),'ada masalah','boong','')";
//            stmt.executeUpdate(insert);
            
            String query= "select * from ACTIVITY_RECORD";
            ResultSet rs= stmt.executeQuery(query);
//                System.out.println("User | Start_time | end_time | reported_problem | real_problem | remark");
            while (rs.next()) {
                System.out.print(rs.getString(1));
                System.out.print(" | ");
                System.out.print(rs.getString(2));
                System.out.print(" | ");
                System.out.print(rs.getString(3));
                System.out.print(" | ");
                System.out.print(rs.getString(4));
                System.out.print(" | ");
                System.out.print(rs.getString(5));
                System.out.print(" | ");
                System.out.println(rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
