package com.pik.moviecollection.model.datamanegement;

import com.pik.moviecollection.model.orm.Movie;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Created by Robert on 2014-05-24.
 */
public class MovieManagerImpl implements MovieManager
{
    private final EntityManager entityManager;

    public MovieManagerImpl(EntityManager entityManager)
    {
	this.entityManager = entityManager;
    }


    @Override
    public void addMovie(Map<String, String> movieParameters)
    {
	Movie movie = new Movie();
	movie.setTitle(movieParameters.get("title"));
	movie.setCountry(movieParameters.get("country"));

	insertMovie(movie);
    }

    private void insertMovie(Movie movie)
    {
	entityManager.persist(movie);
    }

}
