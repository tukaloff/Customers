/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.messages;

/**
 *
 * @author tukal
 */
public class Message {
    
    private String type;
    private String sequence_id;
    private String api_token;
    private Login data;
    
    public String getType() {
        return type;
    }
    
    public String getSequenceId() {
        return sequence_id;
    }
    
    public String getApi_token() {
        return api_token;
    }
    
    public Login getLogin() {
        return data;
    }
}
