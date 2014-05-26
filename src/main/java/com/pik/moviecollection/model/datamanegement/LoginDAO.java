package com.pik.moviecollection.model.datamanegement;

import com.pik.moviecollection.model.orm.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by piotr on 26.05.14.
 */
public class LoginDAO {
    private static EntityConnection conn = EntityConnection.getInstance();
    private static LoginDAO ourInstance = new LoginDAO();
    public static LoginDAO getInstance() {
        return ourInstance;
    }
    private LoginDAO() {}

    /**
     * @return null dla blednych danych
     *         User dane zalogowanego uzytkownika
     */
    public static User loginUser(String login, String password) {

        EntityManager em = conn.getConnection();
        String queryString = "SELECT u FROM User u " +
                "WHERE u.login = :login and u.pass = :pass";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login);
        query.setParameter("pass", password);

        User result = (User)query.getResultList().get(0);
        result.setPass("");

        conn.closeConnection();
        return result;
    }





}