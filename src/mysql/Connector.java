/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Damangrea
 */
public class Connector {
    Connection conn;

    public Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ssportal-tbs-2.packet-systems.com/ssportalv2?user=root&password=P@ssw0rd##");
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addResetAccount(String username){
        if(conn!=null){
            try {
                Statement stmt=conn.createStatement();
                String sQuery="insert into need_reset(username) values('"+username+"')";
                stmt.executeUpdate(sQuery);
                stmt.close();
                return 1;
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }else{
            return 0;
        }
    }
    
    public ArrayList<String[]> getMonthlyAnnualLeave(int iMonth,int iYear){
        ArrayList<String[]> result = new ArrayList();
        if(conn!=null){
            try {
                Statement stmt=conn.createStatement();
                String sQuery="select cp.name Name, ar.scheduled Scheduled "
                        + " from activity_record ar, contact_profile cp"
                        + " where "
                        + " ar.contact_profile_id=cp.id "
                        + " and ar.activity_status=2 "
                        + " and ar.approved=3 "
                        + " and ar.flag=0 "
                        + " and month(ar.scheduled)="+iMonth  
                        + " and year(ar.scheduled)="+iYear
                        + " order by cp.name,scheduled ";
                ResultSet rs=stmt.executeQuery(sQuery);
                int i=0;
                String[] temp;
                while (rs.next()) {
                    i++;
                    temp= new String[3];
                    temp[0]=i+"";
                    temp[1]=rs.getString("Name");
                    temp[2]=rs.getString("Scheduled");
                    result.add(temp);
                    //result.add(i+"). "+rs.getString("Name")+" at "+rs.getString("Scheduled")+" ");
                }
                stmt.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }else{
            return null;
        }
    }
    /*
    import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class CSV {
    public static void main(String[]args) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("Name");
        sb.append('\n');

        sb.append("1");
        sb.append(',');
        sb.append("Prashant Ghimire");
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}
    */
    
    
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            Connection connect = DriverManager.getConnection("jdbc:mysql://ssportal-tbs-2.packet-systems.com/ssportalv2?user=root&password=P@ssw0rd##");
            Statement statement = connect.createStatement();
      // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("select * from contact_profile");
            while (resultSet.next()) {                
                System.out.print(resultSet.getString(1));
                System.out.print("--");
                System.out.println(resultSet.getString(2));
            }
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
