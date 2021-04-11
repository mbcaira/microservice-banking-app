package ryerson.ca.helper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */

@XmlRootElement(name = "accounts")
@XmlAccessorType (XmlAccessType.FIELD)
public class AccountsXML {
    @XmlElement(name = "account")
    private ArrayList<Account> accounts;
    
    public List<Account> getAccounts() {
        return this.accounts;
    }
    
    public AccountsXML() {
        
    }
    
    public void setAccount(ArrayList<Account> accs) {
        this.accounts = accs;
    }
}
