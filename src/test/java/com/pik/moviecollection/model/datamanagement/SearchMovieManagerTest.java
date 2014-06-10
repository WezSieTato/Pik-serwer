package com.pik.moviecollection.model.datamanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Robert on 2014-06-10.
 */
public class SearchMovieManagerTest
{
    private SearchMovieManager searchMovieManager;
    protected final String MOVIE_TITLE = "title";
    protected final String MOVIE_COUNTRY = "PL";
    protected final String MOVIE_CATEGORY = "action";
    protected final int MOVIE_YEAR = 2000;

    @Before
    public void setup()
    {
	EntityManager connection = EntityConnection.getConnection();
	searchMovieManager = new SearchMovieManager(connection);
    }

    @After
    public void clean()
    {
	EntityConnection.closeConnection();
    }


    @Test
    public void searchWithoutFilter()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m order by m.title", query);
    }

    @Test
    public void searchWithAllFilters()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.TITLE, MOVIE_TITLE);
	movieAttributeStringMap.put(MovieAttribute.YEAR, Integer.toString(MOVIE_YEAR));
	movieAttributeStringMap.put(MovieAttribute.CATEGORY, MOVIE_CATEGORY);
	movieAttributeStringMap.put(MovieAttribute.COUNTRY, MOVIE_COUNTRY);

	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m where m.title = :title and m.country = :country and" +
			" m.year = :year and m.category = :category order by m.title", query);
    }


    @Test
    public void searchWithTitleFilter()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.TITLE, MOVIE_TITLE);

	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m where m.title = :title order by m.title", query);
    }

    @Test
    public void searchWithTitleYearFilter()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.TITLE, MOVIE_TITLE);
	movieAttributeStringMap.put(MovieAttribute.YEAR, Integer.toString(MOVIE_YEAR));

	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m where m.title = :title and m.year = :year order by m.title", query);
    }


    @Test
    public void searchWithCategoryFilter()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.CATEGORY, MOVIE_CATEGORY);

	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m where m.category = :category order by m.title", query);
    }

    @Test
    public void searchWithCategoryYearFilter()
    {
	Map<MovieAttribute, String> movieAttributeStringMap = new HashMap<>();
	movieAttributeStringMap.put(MovieAttribute.CATEGORY, MOVIE_CATEGORY);
	movieAttributeStringMap.put(MovieAttribute.YEAR, Integer.toString(MOVIE_YEAR));

	String query = searchMovieManager.getFilteredSearchQueryString(movieAttributeStringMap);

	assertEquals("select m from Movie m where m.year = :year and m.category = :category order by m.title", query);
    }



}
