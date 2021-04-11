/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import ryerson.ca.helper.Account;
import ryerson.ca.helper.AccountsXML;
import ryerson.ca.persistence.Transfer_CRUD;

/**
 *
 * @author student
 */
public class TransferBusiness {
    
    public AccountsXML makeTransfer(String transferType, String username,
            int outgoingAccountNumber, int incomingAccountNumber, double amount) 
            throws IOException {
        boolean successfulTransfer = false;
        System.out.printf("transfer type: %s\nusername: %s\noutgoing: %d\t"
                + "incoming: %d\tamount: %f\n", 
                transferType, username, outgoingAccountNumber, incomingAccountNumber, amount);
        AccountsXML accountXML = new AccountsXML();
        ArrayList<Account> accounts = Transfer_CRUD.getAccounts(username);
        Account outgoingAccount = new Account();
        Account incomingAccount = new Account();       
        switch (transferType) {
            case "interAccount":
                for (Account acc : accounts) {
                    if (acc.getAccountNumber() == outgoingAccountNumber) outgoingAccount = acc;
                    else if (acc.getAccountNumber() == incomingAccountNumber) incomingAccount = acc;
                }
                if (outgoingAccount.getType() == null || incomingAccount.getType() == null) successfulTransfer = false;
                else if (outgoingAccount.withdraw(amount)) {
                    if (incomingAccount.deposit(amount)) successfulTransfer = true;
                }
                
                if (successfulTransfer) {
                    ArrayList<Account> updatedAccounts = new ArrayList<>();
                    updatedAccounts.add(incomingAccount);
                    updatedAccounts.add(outgoingAccount);
                    for (Account acc : updatedAccounts) {
                        Transfer_CRUD.updateAccount(username, acc.getAccountNumber(), acc.getBalance());
                    }
                    accountXML.setAccount(updatedAccounts);
                    Messaging.sendmessage("TRANSFER:"+username+":"+outgoingAccount.getAccountNumber()+":"+outgoingAccount.getBalance()+":"+incomingAccount.getAccountNumber()+":"+incomingAccount.getBalance());
                    return accountXML;
                }
                else {
                    System.out.println("Could not complete transfer.");
                }
                break;
        }        
        return accountXML;
    }
    
    public static ArrayList<Account> getServices(String username, String token) throws IOException {            
        AccountsXML accounts = new AccountsXML();
        if (token != null) {
            String accountService = System.getenv("accountService");
            Client accountsClient = ClientBuilder.newClient();
            WebTarget accountswebTarget = accountsClient.target("http://"+accountService+"/AccountManagement/webresources/account/");
            InputStream accountData = accountswebTarget.path(username).request(MediaType.APPLICATION_XML).get(InputStream.class);            
            String accXML = IOUtils.toString(accountData, "utf-8");
            accounts = accountsxmltoObjects(accXML);
        }
        return (ArrayList<Account>) accounts.getAccounts();
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
    
    public AccountsXML getAccounts(String username) {
        AccountsXML accounts = new AccountsXML();
        accounts.setAccount(Transfer_CRUD.getAccounts(username));
        return accounts;
    }
}
