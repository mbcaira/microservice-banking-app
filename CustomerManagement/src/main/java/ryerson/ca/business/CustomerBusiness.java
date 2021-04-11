/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import ryerson.ca.helper.Customer;
import ryerson.ca.persistence.Customer_CRUD;

/**
 *
 * @author student
 */
public class CustomerBusiness {
    
    public Customer getCustomer(String username) throws IOException {        
        return Customer_CRUD.findUser(username);
    }
}
