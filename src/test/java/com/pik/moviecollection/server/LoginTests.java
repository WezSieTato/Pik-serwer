package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanegement.EntityConnection;
import com.pik.moviecollection.model.datamanegement.LoginDAO;
import com.pik.moviecollection.model.orm.User;
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

    private MockMvc mockMvc;
    private static EntityConnection conn = EntityConnection.getInstance();
    private static LoginDAO data = LoginDAO.getInstance();

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void loginUserLoginOkPassOkTest() {
        User u = createValidUser();
        User result = data.loginUser(u.getLogin(), u.getPass());

        Assert.notNull(result, "Nie poprawny login i haslo");
        deleteUser(u);
    }

    private User createValidUser() {
        User user = new User(USER_NAME, USER_SURNAME, USER_LOGIN, USER_PASS);
        EntityManager em = conn.getConnection();
        em.persist(user);
        conn.closeConnection();
        return user;
    }

    private void deleteUser(User u) {
        EntityManager em = conn.getConnection();
        User user = em.find(User.class, u.getUserID());
        em.remove(user);
        conn.closeConnection();
    }


}