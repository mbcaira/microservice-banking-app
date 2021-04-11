/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.util.ArrayList;
import ryerson.ca.helper.Account;
import ryerson.ca.helper.AccountsXML;
import ryerson.ca.persistence.Account_CRUD;

/**
 *
 * @author student
 */
public class AccountManagement {
    public AccountsXML getAccounts(String username) {
        AccountsXML accounts = new AccountsXML();
        accounts.setAccount(Account_CRUD.getAccounts(username));
        return accounts;
    }
    
    public boolean updateBalance(String username, int accountNumber, double balance) throws IOException {        
        return Account_CRUD.updateAccount(username, accountNumber, balance);
    }
}
