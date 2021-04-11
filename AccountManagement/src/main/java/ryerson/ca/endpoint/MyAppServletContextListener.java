/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.endpoint;

import com.sun.istack.logging.Logger;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import java.util.logging.Level;
import javax.net.ssl.SSLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import ryerson.ca.business.Messaging;

/**
 *
 * @author student
 */
public class MyAppServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Messaging.Receiving_Events_Store("hold_book_channel");
                } catch (SSLException | ServerAddressNotSuppliedException ex) {
                 Logger.getLogger(MyAppServletContextListener.class).log(Level.SEVERE, null, ex);
             }
         }    
     };
     
     new Thread(r).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener destroyed");
    }       
}
