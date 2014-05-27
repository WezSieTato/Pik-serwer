package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.EntityConnection;
import com.pik.moviecollection.model.datamanagement.MovieManager;
import com.pik.moviecollection.model.datamanagement.MovieManagerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/service/greeting")
@ResponseStatus(HttpStatus.OK)
public class HelloController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGreeting(@PathVariable String name) {
        String result="Witojcie panocki na moim websajcie! I nie piszta mnie tu: "+name;
        return result;
        //return new ResponseEntity<String>("{value: " + result + "}", HttpStatus.OK);
    }

    private void exampleInsertToMongoDB()
    {
	EntityManager connection = EntityConnection.getConnection();
	MovieManager movieManager = new MovieManagerImpl(connection);

	Map<String, String> movieParameters = new HashMap<>();
	movieParameters.put("title","PIK");
	movieParameters.put("country","PL");

	movieManager.addMovie(movieParameters);

	EntityConnection.closeConnection();
    }
}
