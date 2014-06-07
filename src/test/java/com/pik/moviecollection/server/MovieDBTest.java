package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.*;
import com.pik.moviecollection.model.entity.Category;
import com.pik.moviecollection.model.entity.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * Created by Robert on 2014-05-25.
 */
public class MovieDBTest
{
    private CategoryManager categoryManager;
    private MovieManager movieManager;
    private EntityManager entityManager;
    protected final String MOVIE_TITLE = "title";
    protected final String MOVIE_COUNTRY = "PL";
    protected final String MOVIE_CATEGORY = "action";
    protected final int MOVIE_YEAR = 2000;

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
	movie.setTitle(MOVIE_TITLE);
	movie.setCountry(MOVIE_COUNTRY);

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
	movie.setTitle(MOVIE_TITLE);
	Category category = categoryManager.getCategoryByName(MOVIE_CATEGORY);
	movie.setCategory(category);
	movie.setYear(MOVIE_YEAR);

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


    @Test
    public void getFilteredMoviesTest()
    {
	String movieID = addTestMovieToDatabase();
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.TITLE, MOVIE_TITLE);
	movieAttributeStringMap.put(MovieAttribute.YEAR, Integer.toString(MOVIE_YEAR));

	List<Movie> movies = movieManager.getMovies(movieAttributeStringMap, 0, 10);
	assertTrue(movies.size() > 0);

	Movie movie = entityManager.find(Movie.class, movieID);
	entityManager.remove(movie);
    }
}
