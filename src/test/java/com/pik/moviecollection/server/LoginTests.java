package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanegement.EntityConnection;
import com.pik.moviecollection.model.datamanegement.LoginDAO;
import com.pik.moviecollection.model.orm.Token;
import com.pik.moviecollection.model.orm.User;
import com.pik.moviecollection.model.result.data.LoginResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by piotr on 26.05.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:rest-service-servlet.xml")
public class LoginTests {

    private static final String USER_NAME = "Random name";
    private static final String USER_SURNAME = "Random surname";
    private static final String USER_LOGIN = "Random login";
    private static final String USER_PASS = "Random pass";
    private static final String WRONG_DATA = "asdf";

    private User testUser;

    private MockMvc mockMvc;
    private static EntityConnection conn = EntityConnection.getInstance();
    private static LoginDAO data = LoginDAO.getInstance();

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        testUser = createValidUser();
    }

    @After
    public void clean() {
        deleteUser(testUser);
    }

    @Test
    public void loginUserLoginOkPassOkTest() {
        LoginResult result = data.loginUser(testUser.getLogin(), testUser.getPass());
        Assert.notNull(result, "Brak uzytkownika o podanym hasle i loginie w bazie");
        deleteToken(result.getToken());
    }

    @Test
    public void loginUserLoginOkPassWrongTest() {
        LoginResult result = data.loginUser(testUser.getLogin(), WRONG_DATA);
        Assert.isNull(result);
    }

    @Test
    public void loginUserLoginWrongPassOkTest() {
        LoginResult result = data.loginUser(WRONG_DATA, testUser.getPass());
        Assert.isNull(result);
    }

    @Test
    public void loginUserLoginWrongPassWrongTest() {
        LoginResult result = data.loginUser(WRONG_DATA, WRONG_DATA);
        Assert.isNull(result);
    }

    @Test
    public void validateValidUserTest() {
        LoginResult result = data.loginUser(testUser.getLogin(), testUser.getPass());
        Assert.isTrue(data.validateUser(result.getToken()));
    }


    private User createValidUser() {
        User user = new User(USER_NAME, USER_SURNAME, USER_LOGIN, USER_PASS);
        EntityManager em = conn.getConnection();
        em.persist(user);
        conn.closeConnection();
        return user;
    }

    private void deleteUser(User u) {
        if (u == null) return;
        EntityManager em = conn.getConnection();
        User user = em.find(User.class, u.getUserID());
        em.remove(user);
        conn.closeConnection();
    }

    private void deleteToken(Token t) {
        if (t == null) return;
        EntityManager em = conn.getConnection();
        Token token = em.find(Token.class, t.getTokenID());
        em.remove(token);
        conn.closeConnection();
    }


}