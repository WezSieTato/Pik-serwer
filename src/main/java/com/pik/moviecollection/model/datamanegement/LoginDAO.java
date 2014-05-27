package com.pik.moviecollection.model.datamanegement;

import com.pik.moviecollection.model.orm.Token;
import com.pik.moviecollection.model.orm.User;
import com.pik.moviecollection.model.result.data.LoginResult;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    @SuppressWarnings("unchecked")
    public static LoginResult loginUser(String login, String password) {
        EntityManager em = conn.getConnection();
        String queryString = "SELECT u FROM User u WHERE u.login = :login and u.pass = :pass";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login);
        query.setParameter("pass", password);

        List<User> items = query.getResultList();
        if (items.isEmpty()) return null;
        User result = (User)items.get(0);
        result.setPass("");


        Token token = new Token(generateToken(result.getUserID()), result);
        em.persist(token);

        //System.out.println("u id: " + result.getUserID());
        //System.out.println("token id: " + token.getTokenID() + " user from token " + token.getUser().getUserID());

        conn.closeConnection();
        return new LoginResult(result, token);
    }

    public static boolean validateUser(Token token) {
        EntityManager em = conn.getConnection();
        String queryString = "SELECT t FROM Token t WHERE t.code = :code";
        Query query = em.createQuery(queryString);
        query.setParameter("code", token.getCode());
        //query.setParameter("id", token.getUser().getUserID());
        List<Token> items = query.getResultList();
        if (items.isEmpty()) return false;
        return true;
    }

    /*
    public static boolean logoutUser(User user) {
        EntityManager em = conn.getConnection();
        String queryString = "SELECT t FROM Token t WHERE t.user.userID = :id";
        Query query = em.createQuery(queryString);
        query.setParameter("id", user.getUserID());
        List<Token> items = query.getResultList();
        conn.closeConnection();
        if (items.isEmpty()) return false;
        return true;
    }
    */

    private static String generateToken(String id) {
        return "SUPER_SECRET_TOKEN";
    }




}
