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
import java.util.ArrayList;
import ryerson.ca.helper.Account;

/**
 *
 * @author student
 */
public class Transfer_CRUD {
    private static Connection getCon(){
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            con=DriverManager.getConnection("jdbc:mysql://"+connection+"/Transfer_bub?allowPublicKeyRetrieval=true&useSSL=false", "root", "student");
            System.out.println("Connection established.");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
    
    public static ArrayList<Account> getAccounts(String username){
        ArrayList<Account> bean = null;
        try {
            Connection con = getCon();
            String q = "SELECT * FROM account WHERE username LIKE " + '"' +username + '"';
             bean = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int accountNumber = rs.getInt("account_number");
                int cardNumber = rs.getInt("card_number");
                double balance = rs.getDouble("Balance"); 
                String accountType = rs.getString("account_type");
                bean.add(new Account(accountNumber, cardNumber, balance, accountType));
            }
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }       
        return bean;
    }

    public static boolean updateAccount(String username, int accountNumber, double balance) {
        try {
            Connection con = getCon();
            String q = "update account set balance = " + balance + ""
                    + "where account_number = " + accountNumber +" and username = "+'"'+username+'"';           
            PreparedStatement ps = con.prepareStatement(q);
            ps.executeUpdate();
            con.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }         
    }
    
    
}