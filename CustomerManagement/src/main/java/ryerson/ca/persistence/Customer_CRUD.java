/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ryerson.ca.helper.Customer;


/**
 *
 * @author student
 */
public class Customer_CRUD {
    private static Connection getCon() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            con = DriverManager.getConnection("jdbc:mysql://"+connection+"/CustomerManagement_bub?allowPublicKeyRetrieval=true&useSSL=false","root","student");
            System.out.println("Connection established.");
        } catch(Exception e) {
            System.out.println(e);
        }
        return con;
    }
    
    public static Customer findUser(String username) {
        Customer bean = null;
        try {
            Connection con = getCon();
            String q = "SELECT * FROM customer WHERE username LIKE " + '"' +username + '"';
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");                                              
                bean = new Customer(username, fname, lname);                                   
            }
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }       
        return bean;
    }
}
