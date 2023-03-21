package util;

import jakarta.persistence.*;

public class DbOperations {

    public static EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("default");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static boolean insertObject(Object obj, EntityManager entityManager) {
        boolean success = true;
        if (obj != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(obj);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                if (entityManager.getTransaction() != null) {
                    entityManager.getTransaction().rollback();
                }
                System.err.println("Failed to insert objects." + ex);
                success = false;
            }
        }
        return success;
    }

}
