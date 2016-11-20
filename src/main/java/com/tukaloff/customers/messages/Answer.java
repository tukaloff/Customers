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
public class Answer {
    private String type;
    private String sequence_id;
    private Object data;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getSequenceId() {
        return sequence_id;
    }
    
    public void setSequenceId(String sequence_id) {
        this.sequence_id = sequence_id;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}
