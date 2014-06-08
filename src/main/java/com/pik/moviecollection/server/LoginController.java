package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.User;
import com.pik.moviecollection.model.result.data.LoginResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;

/**
 * Created by marcin stepnowski on 03.06.14.
 */

@RestController
@RequestMapping("/usr")
@ResponseStatus(HttpStatus.OK)
public class LoginController {

    @RequestMapping(value= "/{login}", method = RequestMethod.POST)
    public String loginToServer( @PathVariable String login,
                         @RequestParam(value="password", required=true) String password)
    {
        LoginResult result = LoginDAO.loginUser(login, password);
        if (result == null)
            return "";
        String token = result.getToken().getCode();
        SessionManager.setToken(token);
        return  token;
    }

    @RequestMapping(value= "/add/{login}", method = RequestMethod.POST)
    public String addUser( @PathVariable String login,
                                 @RequestParam(value="password", required=true) String password)
    {
        User user = new User(login, login, login, password);
        EntityManager em = EntityConnection.getConnection();
        em.persist(user);
        EntityConnection.closeConnection();
        return "Dodano usera";

    }

    @RequestMapping(value= "/logoff", method = RequestMethod.GET)
    public String logoffFromServer()
    {
        return  "Wylogowano! Dziekujemy do widzenia!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        return SessionManager.getToken();
    }

}
