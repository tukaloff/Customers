/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tukaloff.customers.ApplicationContextProvider;
import com.tukaloff.customers.dao.CustomersAuth;
import com.tukaloff.customers.dao.HibernateDao;
import com.tukaloff.customers.utils.Utils;
import java.time.LocalDateTime;

/**
 *
 * @author tukal
 */
public class MessageHandler {
    
    private static Gson gson = new Gson();

    private HibernateDao dao;
    
    public MessageHandler() {
        this.dao = ApplicationContextProvider.getApplicationContext().getBean("hibernateDao", HibernateDao.class);
    }    
    
    public String handle(String sessionId, String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        switch (json.get("type").getAsString()) {
            case "CUSTOMER_LOGIN":
                Message msg = gson.fromJson(message, Message.class);
                Answer answer = new Answer();
                answer.setSequenceId(sessionId);
                if (msg.getApi_token().equals("")) {
                    answer.setType("CUSTOMER_API_TOKEN");
                    Token token = new Token();
                    token.setApi_token(Utils.getAccessToken(msg.getType(), msg.getSequenceId()));
                    answer.setData(token);
                    if (!saveAuth(msg.getLogin().getLogin()/*, msg.getLogin().getPass()*/, token.getApi_token()))
                        return gson.toJson(new Failed());
                    return gson.toJson(answer);
                } else {
                    System.out.println("has token");
                    CustomersAuth auth = dao.getByLogin(msg.getLogin().getLogin());
                    if (auth == null) {
                        answer.setType("USER_DOES_NOT_EXISTS");
                        answer.setData(new Failed());
                    } else {
                        if (!auth.getDtm().plusMinutes(1).isAfter(LocalDateTime.now())) {
                            answer.setType("TOKEN_PERIOD_IS_OVER");
                            answer.setData(new Failed());
                        } else {
                            answer.setType("AUTH_SUCCESS");
                            answer.setData(new Success());
                        }
                    }
                    return gson.toJson(answer);
                }
        }
        return null;
    }
    
    private boolean saveAuth(String login/*, String pass*/, String api_token) {
        CustomersAuth auth = new CustomersAuth();
        auth.setLogin(login);
        //auth.setPass(pass);
        auth.setApi_token(api_token);
        auth.setDtm(LocalDateTime.now());
        return dao.putSession(auth);
    }
    
    public String getVoidMessage(String sessionId) {
        Answer answer = new Answer();
        answer.setType("VOID");
        answer.setSequenceId(sessionId);
        return gson.toJson(answer);
    }
}
