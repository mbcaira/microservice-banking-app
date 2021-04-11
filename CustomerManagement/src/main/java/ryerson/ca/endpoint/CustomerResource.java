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
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ryerson.ca.business.CustomerBusiness;
import ryerson.ca.helper.Customer;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("customer/{username}")
public class CustomerResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    /**
     * Retrieves representation of an instance of ryerson.ca.endpoint.CustomerResource
     * @param username
     * @param password
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@PathParam("username") String username) throws IOException {
        CustomerBusiness cb = new CustomerBusiness();
        Customer cust = cb.getCustomer(username);
        if (cust == null) return "";
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = jaxbContext.newInstance(Customer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(cust, sw);
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(CustomerResource.class.getName()).log(Level.SEVERE, null, ex);
            return "error happened";
        }
    }

    /**
     * PUT method for updating or creating an instance of CustomerResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}