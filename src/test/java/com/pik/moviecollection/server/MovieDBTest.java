package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
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
    private CategoryManager categoryManager;
    private MovieManager movieManager;
    private EntityManager entityManager;

    @Before
    public void setup()
    {
	entityManager = EntityConnection.getConnection();
	movieManager = new MovieManagerImpl(entityManager);
	categoryManager = new CategoryManagerImpl(entityManager);
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

	entityManager.remove(movie);
    }

    private Movie prepareDataToInsert()
    {
	Movie movie = new Movie();
	movie.setTitle("PIK");
	movie.setCountry("PL");

	return movie;
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
	movie.setTitle("aaaaaaaaa");
	Category category = categoryManager.getCategoryByName("there is no category with this name");
	movie.setCategory(category);

	entityManager.persist(movie);
	return movie.getMovieID();
    }

    @Test
    public void getMoviesTest()
    {
	String movieID = addTestMovieToDatabase();

	List<Movie> movies = movieManager.getMovies(0, 5);
	assertTrue(movies.size() > 0);

	Movie movie = entityManager.find(Movie.class, movieID);
	entityManager.remove(movie);
    }
}
