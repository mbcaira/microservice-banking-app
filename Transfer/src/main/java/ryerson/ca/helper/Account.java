/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.helper;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author student
 */
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account implements Serializable{
    private int accountNumber = -1;
    private int cardNumber = -1;
    private double balance = -1;
    String accountType;
    
    public Account(){
    
    }

    public Account(int accountNumber, int cardNumber, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.balance = balance;        
        this.accountType = accountType;
    }

    public double getBalance() {
        return this.balance;
    }
    
    public int getCard() {
        return this.cardNumber;
    }
    
    public String getType() {
        return this.accountType;
    }
    
    public String stringBalance() {
        return String.format("CAD$ %500.2f", this.balance);
    }

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public boolean deposit(double amount){
        if (amount <= 0) return false;
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount){
        if (amount < 0 || this.balance - amount < 0 ) return false;
        this.balance -= amount;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("%d - %s - %s", this.accountNumber, this.stringBalance(), this.accountType);
    }
}

