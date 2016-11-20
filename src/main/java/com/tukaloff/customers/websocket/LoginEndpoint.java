/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.websocket;

import com.tukaloff.customers.messages.MessageHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tukal
 */
@ServerEndpoint("/login")
public class LoginEndpoint {
    
    private static final Set<Session> peers 
            = Collections.synchronizedSet(new HashSet<Session>());
    
    public MessageHandler msgHandler = new MessageHandler();
    
    @OnOpen
    public void onOpen(Session session) {
        peers.add(session);
        System.out.println(msgHandler.getVoidMessage(session.getId()));
        session.getAsyncRemote()
                .sendText(msgHandler.getVoidMessage(session.getId()));
        System.out.println("session " + session.getId());
    }
    
    @OnMessage
    public String onMessage(Session session, String message) {
        System.out.println(session.getId());
        String jsonAnswer = msgHandler.handle(session.getId(), message);
        return jsonAnswer;
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("disconnect " + session.getId());
        peers.remove(session);
    }
}
