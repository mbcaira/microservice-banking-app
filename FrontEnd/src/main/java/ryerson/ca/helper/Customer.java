/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.helper;

/**
 *
 * @author student
 */
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    private String username = "";
    private String firstName = "";
    private String lastName = ""; 
    private ArrayList<Account> accounts;
    
    public Customer() {
        
    }
    
    public Customer(String username, String firstName, String lastName) {        
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;         
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    
    public void addAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}

