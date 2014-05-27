package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.EntityConnection;
import com.pik.moviecollection.model.datamanagement.MovieManager;
import com.pik.moviecollection.model.datamanagement.MovieManagerImpl;
import com.pik.moviecollection.model.entity.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

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
	Map<String, String> movieParameters = prepareDataToInsert();
	String movieID = movieManager.addMovie(movieParameters);

	Movie movie = entityManager.find(Movie.class, movieID);
	assertNotNull(movie);

	removeMovieAfterInsertTest(movie);
    }

    private Map<String, String> prepareDataToInsert()
    {
	Map<String, String> movieParameters = new HashMap<>();
	movieParameters.put("title","PIK");
	movieParameters.put("country","PL");

	return movieParameters;
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
