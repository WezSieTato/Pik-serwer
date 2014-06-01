package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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
	List<Movie> movies = new ArrayList<>();

	Query query = entityManager.createQuery("select e from Movie e");
	query.setFirstResult(startPosition);
	query.setMaxResults(maxResults);
	List<?> moviesList = query.getResultList();

	for (Object movie: moviesList)
	{
	    movies.add((Movie)movie);
	}

	return movies;
    }


}
