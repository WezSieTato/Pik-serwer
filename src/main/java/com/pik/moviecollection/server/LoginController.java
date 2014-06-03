package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
import com.pik.moviecollection.model.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by marcin stepnowski on 03.06.14.
 */

@RestController
@RequestMapping("/usr")
@ResponseStatus(HttpStatus.OK)
public class LoginController {

    @RequestMapping(value= "/{login}", method = RequestMethod.GET)
    public String loginToServer( @PathVariable String login,
                         @RequestParam(value="password", required=true) String password)
    {
        return  login + password;
    }

    @RequestMapping(value= "/logoff", method = RequestMethod.GET)
    public String logoffFromServer()
    {
        return  "Wylogowano! Dziekujemy do widzenia!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        return "siema";
        //return new ResponseEntity<String>("{value: " + result + "}", HttpStatus.OK);
    }

}
