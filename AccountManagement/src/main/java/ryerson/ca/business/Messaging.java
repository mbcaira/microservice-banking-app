/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import ryerson.ca.endpoint.MyAppServletContextListener;
import ryerson.ca.persistence.Account_CRUD;

/**
 *
 * @author student
 */
public class Messaging {
    public static void Receiving_Events_Store(String cname) throws SSLException, ServerAddressNotSuppliedException {
        String ChannelName = cname, ClientID = "transfer-subscriber";
                String kubeMQAddress = System.getenv("kubeMQAddress");
        Subscriber subscriber = new Subscriber(kubeMQAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);        
        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {
            @Override
            public void onNext(EventReceive value) {
                try {
                    String val=(String) Converter.FromByteArray(value.getBody());
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                    String[] msgParts = val.split(":");
                    if(msgParts.length==6){
                        if(msgParts[0].equals("TRANSFER")){                        
                          String username=msgParts[1];
                          int outgoingAccountNumber = Integer.parseInt(msgParts[2]);
                          int incomingAccountNumber = Integer.parseInt(msgParts[4]);
                          double outgoingAccountBalance = Double.parseDouble(msgParts[3]);
                          double incomingAccountBalance = Double.parseDouble(msgParts[5]);
                          Account_CRUD.updateAccount(username, outgoingAccountNumber, outgoingAccountBalance);
                          Account_CRUD.updateAccount(username, incomingAccountNumber, incomingAccountBalance);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.printf("IOException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (Exception ex) {
                    Logger.getLogger(MyAppServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            @Override
            public void onError(Throwable t) {
                System.out.printf("onError:  %s", t.getMessage());
            }
            @Override
            public void onCompleted() {
            }
        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);
    }
}

S