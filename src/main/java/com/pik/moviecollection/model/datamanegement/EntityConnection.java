package com.pik.moviecollection.model.datamanegement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Robert on 2014-05-24.
 */
public class EntityConnection
{
    protected static String PERSISTENT_UNIT_NAME = "kunderapu";

    private static EntityManager entityManager;
    private static EntityManagerFactory emf;
    private static EntityConnection instance = new EntityConnection();

    public static EntityConnection getInstance() {
        return instance;
    }
    private EntityConnection() {}

    public static EntityManager getConnection()
    {
	emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
	entityManager = emf.createEntityManager();
	return entityManager;
    }

    public static void closeConnection()
    {
	entityManager.close();
	emf.close();
    }

}
