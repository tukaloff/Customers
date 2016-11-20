/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tukal
 */
@Repository
public class HibernateDao {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public boolean putSession(CustomersAuth auth) {
        Session session = currentSession();
        session.getTransaction().begin();
        try {
            CustomersAuthHistory hist = new CustomersAuthHistory();
            CustomersAuth authHist = session.get(CustomersAuth.class, auth.getLogin());
            if (authHist != null) {
                hist.setLogin(authHist.getLogin());
                hist.setDtm(authHist.getDtm());
                hist.setApi_token(authHist.getApi_token());
                session.save(hist);
            }
            session.merge(auth);
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return false;
        }
        session.getTransaction().commit();
        return true;
    }
    
    public CustomersAuth getByLogin(String login) {
        Session session = currentSession();
        session.getTransaction().begin();
        try {
            CustomersAuth auth = session.get(CustomersAuth.class, login);
            session.getTransaction().commit();
            return auth;
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return null;
        }
    }
}
