package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.EntityConnection;
import com.pik.moviecollection.model.datamanagement.MovieManager;
import com.pik.moviecollection.model.datamanagement.MovieManagerImpl;
import com.pik.moviecollection.model.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/service/greeting")
@ResponseStatus(HttpStatus.OK)
public class HelloController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGreeting(@PathVariable String name) {
        String result="Witojcie panocki na moim websajcie! I nie piszta mnie tu: "+name;
	//addTestData();
	result = getMovies(result);

	return result;
        //return new ResponseEntity<String>("{value: " + result + "}", HttpStatus.OK);
    }

    private void addTestData()
    {
	Map<String, String> movieParameters = new HashMap<>();
	movieParameters.put("title","PIK");
	movieParameters.put("country","PL");

	EntityManager connection = EntityConnection.getConnection();
	MovieManager movieManager = new MovieManagerImpl(connection);

	movieManager.addMovie(movieParameters);

	movieParameters = new HashMap<>();
	movieParameters.put("title","film");
	movieParameters.put("country","EN");

	movieManager.addMovie(movieParameters);

	EntityConnection.closeConnection();
    }

    private String getMovies(String result)
    {
	EntityManager connection = EntityConnection.getConnection();
	MovieManager movieManager = new MovieManagerImpl(connection);
	List<Movie> movies = movieManager.getMovies(0,2);

	for (Movie m: movies)
	{
	    result = result + " id: " + m.getMovieID() + " tytul: " + m.getTitle();
	}

	EntityConnection.closeConnection();

	return result;
    }
}
