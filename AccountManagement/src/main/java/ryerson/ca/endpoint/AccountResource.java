/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.endpoint;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ryerson.ca.business.AccountManagement;
import ryerson.ca.helper.AccountsXML;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("account/{username}")
public class AccountResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AccountResource
     */
    public AccountResource() {
    }

    /**
     * Retrieves representation of an instance of ryerson.ca.endpoint.AccountResource
     * @param username
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
    public String getXml(@PathParam("username") String username, @PathParam("action") String action) {
        //TODO return proper representation object
        
            AccountManagement am = new AccountManagement();
            AccountsXML accounts = am.getAccounts(username);
            if (accounts == null) return "";
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(AccountsXML.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(accounts, sw);
                return sw.toString();
            }
            catch (JAXBException ex) {
                Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
                return "error happened";
            }
      
    }

    /**
     * POST method for updating or creating an instance of AccountResource
     * @param username
     * @param accountNumber
     * @param balance
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{accountNumber}/{balance}")
    public Response updateAccount(@PathParam("username") String username, @PathParam("accountNumber") int accountNumber, @PathParam("balance") double balance) {
        System.out.println("Updating balances in account management");
        AccountManagement am = new AccountManagement();
        try {
            am.updateBalance(username, accountNumber, balance);
        } catch (IOException ex) {
            Logger.getLogger(AccountResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }
}
