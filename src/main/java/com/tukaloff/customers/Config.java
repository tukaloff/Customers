/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers;

import com.tukaloff.customers.dao.CustomersAuth;
import com.tukaloff.customers.dao.CustomersAuthHistory;
import com.tukaloff.customers.dao.HibernateDao;
import com.tukaloff.customers.messages.MessageHandler;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

/**
 *
 * @author tukal
 */

@Configuration
public class Config {
    
    
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName("com.mysql.jdbc.Driver");
        dbcp.setInitialSize(5);
        dbcp.setMaxIdle(10);
        return dbcp;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionBuilder.addAnnotatedClasses(CustomersAuth.class);
        sessionBuilder.addAnnotatedClasses(CustomersAuthHistory.class);
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.connection.url", "jdbc:mysql://localhost/customers?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC");
        props.put("hibernate.connection.username", "root");
        props.put("hibernate.connection.password", "root");
        props.put("hibernate.hbm2ddl.auto", "update");
        sessionBuilder.setProperties(props);
        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public HibernateDao hibernateDao() {
        return new HibernateDao(sessionFactory());
    }
    
    /*
    @Bean
    public MessageHandler msgHandler() {
        return new MessageHandler(hibernateDao());
    }*/
    
    @Bean
    public ApplicationContextProvider pplicationContextProvider() {
        return new ApplicationContextProvider();
    }
}
