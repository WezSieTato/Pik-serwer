package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Token;
import com.pik.moviecollection.model.entity.User;
import com.pik.moviecollection.model.result.data.LoginResult;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import com.firebase.security.token.*;
import org.json.JSONException;
import org.json.JSONObject;

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

        conn.closeConnection();
        return new LoginResult(result, token);
    }

    public static boolean validateUser(Token token) {
        EntityManager em = conn.getConnection();
        String queryString = "SELECT t FROM Token t WHERE t.code = :code and t.userID = :user";
        Query query = em.createQuery(queryString);
        query.setParameter("code", token.getCode());
        query.setParameter("user", token.getUserID());
        List<Token> items = query.getResultList();
        if (items.isEmpty()) return false;
        return true;
    }

    public static boolean logoutUser(User user) {
        EntityManager em = conn.getConnection();
        String queryString = "SELECT t FROM Token t WHERE t.userID = :id";
        Query query = em.createQuery(queryString);
        query.setParameter("id", user.getUserID());
        List<Token> items = query.getResultList();

        if (!items.isEmpty()) {
            String deleteString = "DELETE FROM Token t WHERE t.userID = :id";
            Query deleteQuery = em.createQuery(deleteString);
            deleteQuery.setParameter("id", user.getUserID());
            deleteQuery.executeUpdate();
            conn.closeConnection();
            return true;
        }

        conn.closeConnection();
        return false;
    }

    private static String generateToken(String id) {
        TokenGenerator tokenGenerator = new TokenGenerator("supersecretkey");
        JSONObject arbitrary = new JSONObject();
        try {
            arbitrary.put("some", "arbitrary");
            arbitrary.put("data", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tokenGenerator.createToken(arbitrary);
    }


}
