package org.example.tiedafx.dao;



import org.example.tiedafx.database.HibernateUtils;
import org.example.tiedafx.model.UserHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOHibernate {
    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;

    public UserDAOHibernate() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    public void insertUser(UserHibernate userHibernate) {
        try {
            // 1 abre la sesion
            session = sessionFactory.openSession();
            // 2 crea una transaccion
            transaction = session.beginTransaction();
            // 3 ejecuta una accion
            session.persist(userHibernate); // INSERT INTO CLIENTES
            // 4 garantiza una accion
            transaction.commit();
            // 5 cierra la sesion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    public void getUser(int id) {
        try {
            // 1 abre la sesion
            session = sessionFactory.openSession();
            // 2 crea una transaccion
            transaction = session.beginTransaction();
            // 3 ejecuta una accion
            UserHibernate userHibernate = session.find(UserHibernate.class, id); // INSERT INTO CLIENTES
            // 4 garantiza una accion
            transaction.commit();
            // 5 cierra la sesion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void getUserByName(String name) {
        try {
            // 1 abre la sesion
            session = sessionFactory.openSession();
            // 2 crea una transaccion
            transaction = session.beginTransaction();
            // 3 ejecuta una accion
            // UserHibernate userHibernate = session.find(UserHibernate.class,id); // INSERT INTO CLIENTES
            List<UserHibernate> lista = session.createQuery("FROM UserHibernate u WHERE u.nombre = " + name
                    , UserHibernate.class).getResultList();
            // 4 garantiza una accion
            transaction.commit();
            // 5 cierra la sesion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void updateUserName(String name, int id) {
        try {
            // 1 abre la sesion
            session = sessionFactory.openSession();
            // 2 crea una transaccion
            transaction = session.beginTransaction();
            // 3 ejecuta una accion
            // UserHibernate userHibernate = session.find(UserHibernate.class,id); // INSERT INTO CLIENTES
            UserHibernate userHibernate = session.find(UserHibernate.class,id);
            if (userHibernate != null){
                userHibernate.setNombre(name);
            }
            // 4 garantiza una accion
            transaction.commit();
            // 5 cierra la sesion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void removeByName(int id) {
        try {
            // 1 abre la sesion
            session = sessionFactory.openSession();
            // 2 crea una transaccion
            transaction = session.beginTransaction();
            // 3 ejecuta una accion
            // UserHibernate userHibernate = session.find(UserHibernate.class,id); // INSERT INTO CLIENTES
            session.remove(id);
            // 4 garantiza una accion
            transaction.commit();
            // 5 cierra la sesion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
