package org.example.tiedafx.database;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {
    private static final SessionFactory sessionFactory = builder();

    private static SessionFactory builder() {

        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
