package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
import com.pik.moviecollection.model.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;


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

    private void dummy() {
        
    }

    private void addTestData()
    {
	EntityManager connection = EntityConnection.getConnection();
	MovieManager movieManager = new MovieManagerImpl(connection);
	CategoryManager categoryManager = new CategoryManagerImpl(connection);

	Movie movie = new Movie();
	movie.setTitle("PIK");
	movie.setCountry("PL");
	Category category = categoryManager.getCategoryByName("akcja");
	movie.setCategory(category);

	movieManager.addMovie(movie);

	movie = new Movie();
	movie.setTitle("film");

	movieManager.addMovie(movie);

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
