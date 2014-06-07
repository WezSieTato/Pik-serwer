package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert on 2014-05-24.
 */
public class MovieManagerImpl implements MovieManager
{
    private final EntityManager entityManager;
    private final SearchMovieManager searchMovieManager;

    public MovieManagerImpl(EntityManager entityManager)
    {
	this.entityManager = entityManager;
	searchMovieManager = new SearchMovieManager(entityManager);
    }

    @Override
    public String addMovie(Movie movie)
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

    @Override
    public List<Movie> getMovies(int startPosition, int maxResults)
    {
	return searchMovieManager.getMovies(startPosition, maxResults);
    }

    @Override
    public List<Movie> getMovies(Map<MovieAttribute, String> searchParameters, int startPosition, int maxResults)
    {
	return searchMovieManager.getMovies(searchParameters, startPosition, maxResults);
    }

}
