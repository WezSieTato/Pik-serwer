package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.EntityConnection;
import com.pik.moviecollection.model.datamanagement.MovieManager;
import com.pik.moviecollection.model.datamanagement.MovieManagerImpl;
import com.pik.moviecollection.model.entity.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by Robert on 2014-05-25.
 */
public class MovieDBTest
{
    private MovieManager movieManager;
    private EntityManager entityManager;

    @Before
    public void setup()
    {
	entityManager = EntityConnection.getConnection();
	movieManager = new MovieManagerImpl(entityManager);
    }

    @After
    public void clean()
    {
	EntityConnection.closeConnection();
    }

    @Test
    public void canInsertMovie()
    {
	Movie movie = prepareDataToInsert();
	String movieID = movieManager.addMovie(movie);

	movie = entityManager.find(Movie.class, movieID);
	assertNotNull(movie);

	removeMovieAfterInsertTest(movie);
    }

    private Movie prepareDataToInsert()
    {
	Movie movie = new Movie();
	movie.setTitle("PIK");
	movie.setCountry("PL");

	return movie;
    }

    private void removeMovieAfterInsertTest(Movie movie)
    {
	entityManager.remove(movie);
    }


    @Test
    public void canDeleteMovie()
    {
	String movieID = addTestMovieToDatabase();

	movieManager.deleteMovie(movieID);
	Movie movie = entityManager.find(Movie.class, movieID);

	assertNull(movie);
    }

    private String addTestMovieToDatabase()
    {
	Movie movie = new Movie();
	movie.setTitle("title");

	entityManager.persist(movie);
	return movie.getMovieID();
    }

    @Test
    public void getMoviesTest()
    {
	String movieID = addTestMovieToDatabase();

	List<Movie> movies = movieManager.getMovies(0, 5);
	assertTrue(movies.size() > 0);

	movieManager.deleteMovie(movieID);
    }

}
