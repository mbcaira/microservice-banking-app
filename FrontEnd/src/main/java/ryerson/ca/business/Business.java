/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import ryerson.ca.helper.Account;
import ryerson.ca.helper.AccountsXML;
import ryerson.ca.helper.Customer;

/**
 *
 * @author student
 */
public class Business {
    
    public static boolean isAuthenticated(String username, String password) {
        boolean auth = false;
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            con = DriverManager.getConnection("jdbc:mysql://"+connection+"/FrontEnd_bub?allowPublicKeyRetrieval=true&useSSL=false","root","student");
            System.out.println("Connection established.");
        } catch(Exception e) {
            System.out.println(e);
        }
        try {           
            String q = "SELECT * FROM user WHERE username LIKE " + '"' +username + '"';
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {                
                String pass = rs.getString("password");
                
                if (pass.equals(password)) {
                    auth = true;                    
                }
            }
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return auth;
    }
    
    public static Customer getServices(String username, String token) throws IOException {
        Client customerClient = ClientBuilder.newClient();
        String customerService = System.getenv("customerService");
        String accountService = System.getenv("accountService");
        WebTarget clientwebTarget = customerClient.target("http://"+customerService+"/CustomerManagement/webresources/customer");
        InputStream is = clientwebTarget.path(username).request(MediaType.APPLICATION_XML).get(InputStream.class);
        String xml = IOUtils.toString(is, "utf-8");
        Customer cust = customerxmltoObjects(xml);       
        AccountsXML accounts = new AccountsXML();
        if (token != null) {
            Client accountsClient = ClientBuilder.newClient();
            WebTarget accountswebTarget = accountsClient.target("http://"+accountService+"/AccountManagement/webresources/account");
            InputStream accountData = accountswebTarget.path(username).request(MediaType.APPLICATION_XML).get(InputStream.class);            
            String accXML = IOUtils.toString(accountData, "utf-8");
            accounts = accountsxmltoObjects(accXML);            
        }
        cust.addAccounts((ArrayList<Account>) accounts.getAccounts());
        return cust;
    }
    
    public static boolean getTransferServices(String username, String transferType, int outgoingAccountNumber, int incomingAccountNumber, double amount, String token) throws IOException {        
        AccountsXML accounts;
        String transferService = System.getenv("transferService");
        String accountService = System.getenv("accountService");
        if (token != null) {

            Client transfersClient = ClientBuilder.newClient();
            String pathString = ""+username+"/"+transferType+"/"+outgoingAccountNumber+"/"+incomingAccountNumber+"/"+amount;
          
            WebTarget transferswebTarget = transfersClient.target("http://"+transferService+"/Transfer/webresources/Transfer");
            InputStream accountData = transferswebTarget.path(pathString).request(MediaType.APPLICATION_XML).get(InputStream.class);            
            String accXML = IOUtils.toString(accountData, "utf-8");
            accounts = accountsxmltoObjects(accXML);
            Client accountsClient = ClientBuilder.newClient();
            WebTarget accountswebTarget = accountsClient.target("http://"+accountService+"/AccountManagement/webresources/account");
            for (Account acc : accounts.getAccounts()) {
                String transferString = ""+username+"/update"+"/"+acc.getAccountNumber()+"/"+acc.getBalance();
                System.out.println("http://"+accountService+"/AccountManagement/webresources/account/update/"+transferString);
                accountswebTarget.path(transferString).request().put(Entity.entity(acc, MediaType.APPLICATION_JSON));
            }
            return true;
        }
        return false;
    }
    
    public static AccountsXML accountsxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AccountsXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            AccountsXML accounts = (AccountsXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return accounts;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Customer customerxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Customer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Customer customer = (Customer) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return customer;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
}
