package util;

import jakarta.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DbOperations {

    public static SessionFactory getSessionFactory(List<Class> annotatedClasses) {
        SessionFactory sf = null;
        try {
            Configuration con = new Configuration().configure();
            for (Class c : annotatedClasses) {
                con.addAnnotatedClass(c);
            }
            sf = con.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sf;
    }

    public static boolean insertObject(Object obj, Session session) {
        boolean success = true;
        if (obj != null) {
            //Session session = null;
            Transaction tx = null;
            try {
                //session = sf.openSession();
                tx = session.beginTransaction();
                session.persist(obj);
                tx.commit();
            } catch (Exception ex) {
                if (tx != null) {
                    tx.rollback();
                }
                System.err.println("Failed to insert objects." + ex);
                success = false;
            } finally {
                //session.close();
            }
        }
        return success;
    }

    public static boolean insertObjects(SessionFactory sf, List<Object> objs) {
        boolean success = true;
        if (objs != null && !objs.isEmpty()) {
            Session session = null;
            Transaction tx = null;
            try {
                session = sf.openSession();
                tx = session.beginTransaction();
                for (Object obj : objs) {
                    session.persist(obj);
                }
                tx.commit();
            } catch (Exception ex) {
                if (tx != null) {
                    tx.rollback();
                }
                System.err.println("Failed to insert objects." + ex);
                success = false;
            } finally {
                session.close();
            }
        }
        return success;
    }

    public static Object getObject(Object id, Class objClass) {
        boolean success = true;
        Configuration con = new Configuration().configure().addAnnotatedClass(objClass);;
        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.get(objClass, id);
            tx.commit();
        }
        catch (Exception e){
            if (tx != null) {
                tx.rollback();;
            }
            e.printStackTrace();
            success = false;
        }
        finally {
            session.close();
        }
        return success;
    }
}
