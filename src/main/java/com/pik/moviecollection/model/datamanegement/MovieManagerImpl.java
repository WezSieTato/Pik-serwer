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
    public String addMovie(Map<String, String> movieParameters)
    {
	Movie movie = MovieFactory.createMovie(movieParameters);
	return insertMovie(movie);
    }

    private String insertMovie(Movie movie)
    {
	entityManager.persist(movie);
	return movie.getMovieID();
    }

    @Override
    public boolean deleteMovie(String movieID)
    {
	Movie movie = entityManager.find(Movie.class, movieID);
	entityManager.remove(movie);
	return true;
    }


}
