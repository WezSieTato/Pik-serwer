package com.pik.moviecollection.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
import com.pik.moviecollection.model.entity.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.io.IOException;


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

            return "{ \"error\" }";
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

        return "{ \"" + id + "\" }";
    }

}
