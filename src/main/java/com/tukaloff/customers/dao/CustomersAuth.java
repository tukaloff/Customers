/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author tukal
 */
@Entity
public class CustomersAuth implements Serializable {

    @Id
    private String login;
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Column
    private String api_token;

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
    
    @Column
    private LocalDateTime dtm;

    public LocalDateTime getDtm() {
        return dtm;
    }

    public void setDtm(LocalDateTime dtm) {
        this.dtm = dtm;
    }
}
