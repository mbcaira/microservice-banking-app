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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ryerson.ca.business.TransferBusiness;
import ryerson.ca.helper.AccountsXML;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("Transfer/{username}/{transferType}/{outgoingAccountNumber}/{incomingAccountNumber}/{amount}")
public class TransferResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TransferResource
     */
    public TransferResource() {
    }

    /**
     * Retrieves representation of an instance of ryerson.ca.endpoint.TransferResource
     * @param username
     * @param transferType
     * @param outgoingAccountNumber
     * @param incomingAccountNumber
     * @param amount
     * @return an instance of java.lang.String
     * @throws java.io.IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
    public String getXml(@PathParam("username") String username, @PathParam("transferType") String transferType, @PathParam("outgoingAccountNumber") int outgoingAccountNumber, @PathParam("incomingAccountNumber") int incomingAccountNumber, @PathParam("amount") double amount) throws IOException {
        //TODO return proper representation object
        TransferBusiness tb = new TransferBusiness();
        System.out.printf("Transfer XML outgoing: %d\n", outgoingAccountNumber);
        AccountsXML accounts = tb.makeTransfer(transferType, username, outgoingAccountNumber, incomingAccountNumber, amount);
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
            Logger.getLogger(TransferResource.class.getName()).log(Level.SEVERE, null, ex);
            return "error happened";
        }
    }

    /**
     * PUT method for updating or creating an instance of TransferResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
