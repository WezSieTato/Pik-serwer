package com.pik.moviecollection.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
import com.pik.moviecollection.model.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;


/**
 * Created by losiowaty on 03.06.14.
 */
@RestController
@RequestMapping("/movies")
@ResponseStatus(HttpStatus.OK)
public class MovieController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String dodajFilm(@RequestParam String data) {

        ObjectMapper mapper = new ObjectMapper();

        Movie m = null;
        try {
            m = mapper.readValue(data, Movie.class);
        } catch (IOException e) {

            e.printStackTrace();

            return "{ \"status\": \"error\" }";
        }

        String id = "";

        if (m != null) {
            EntityManager connection = EntityConnection.getConnection();
            MovieManager movieManager = new MovieManagerImpl(connection);
            CategoryManager categoryManager = new CategoryManagerImpl(connection);
;
            Category category = categoryManager.getCategoryByName(m.getCategory().getName());

            if (category == null)
                categoryManager.addCategory(m.getCategory());

            id = movieManager.addMovie(m);

            EntityConnection.closeConnection();
        }

        return "{ \"movieID\": \"" + id + "\" }";
    }

    @RequestMapping(value = "/list/{start}/{ilosc}", method = RequestMethod.GET)
    public String listujFilmy(@PathVariable int start, @PathVariable int ilosc) {

        EntityManager connection = EntityConnection.getConnection();
        MovieManager movieManager = new MovieManagerImpl(connection);

        List<Movie> movies = movieManager.getMovies(start, ilosc);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(movies);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "{ \"status\": \"error\" }";
    }
    
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String usunFilm(@PathVariable String id) {

        boolean result = false;

        if (id != "") {

            EntityManager connection = EntityConnection.getConnection();
            MovieManager manager = new MovieManagerImpl(connection);

            result = manager.deleteMovie(id);

        }

        return result ? "{ \"status\": \"ok\" }" : "{ \"status\": \"error\" }";


    }

}
