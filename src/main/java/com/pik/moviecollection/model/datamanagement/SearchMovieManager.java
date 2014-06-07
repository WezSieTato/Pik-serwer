package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert on 2014-06-01.
 */
public class SearchMovieManager
{
   private final EntityManager entityManager;
    private boolean isTitle;
    private boolean isCountry;
    private boolean isCategory;

    public SearchMovieManager(EntityManager entityManager)
    {
	this.entityManager = entityManager;
    }

    public List<Movie> getMovies(int startPosition, int maxResults)
    {
	String queryString = "select m from Movie m order by m.title";
	TypedQuery<Movie> selectQuery = getSelectQuery(queryString, startPosition, maxResults);
	return selectQuery.getResultList();
    }


    private TypedQuery<Movie> getSelectQuery(String queryString, int startPosition, int maxResults)
    {
	TypedQuery<Movie> query = entityManager.createQuery(queryString, Movie.class);
	query.setFirstResult(startPosition);
	query.setMaxResults(maxResults);
	return query;
    }

    public List<Movie> getMovies(Map<MovieAttribute, String> searchParameters, int startPosition, int maxResults)
    {
	String stringQuery = getFilteredSearchQueryString(searchParameters);

	TypedQuery<Movie> query = getSelectQuery(stringQuery, startPosition, maxResults);
	setParametersToQuery(query, searchParameters);

	return query.getResultList();
    }

    private String getFilteredSearchQueryString(Map<MovieAttribute, String> searchParameters)
    {
	StringBuilder queryStringBuilder = new StringBuilder("select m from Movie m where ");

	if (isSearchParameterCorrect(searchParameters.get(MovieAttribute.TITLE)))
	{
	    queryStringBuilder.append("m.title = :title and ");
	    isTitle = true;
	}
	if (isSearchParameterCorrect(searchParameters.get(MovieAttribute.COUNTRY)))
	{
	    queryStringBuilder.append("m.country = :country and ");
	    isCountry = true;
	}
	if (isSearchParameterCorrect(searchParameters.get(MovieAttribute.CATEGORY)))
	{
	    queryStringBuilder.append("m.category = :category ");
	    isCategory = true;
	}

	int queryLength = queryStringBuilder.length();
	String substring = queryStringBuilder.substring(queryLength - 4, queryLength - 1);
	if (substring.equals("and"))
	{
	    queryStringBuilder.replace(queryLength - 4, queryLength - 1, "");
	}

	queryStringBuilder.append(" order by m.title");
	String stringQuery = queryStringBuilder.toString();

	if (!isTitle && !isCountry && !isCategory)
	{
	    stringQuery = stringQuery.replace("where", "");
	}

	return stringQuery;
    }

    private void setParametersToQuery(TypedQuery<Movie> query, Map<MovieAttribute, String> searchParameters)
    {
	if (isTitle)
	{
	    query.setParameter("title", searchParameters.get(MovieAttribute.TITLE));
	}
	if (isCountry)
	{
	    query.setParameter("country", searchParameters.get(MovieAttribute.COUNTRY));
	}
	if (isCategory)
	{
	    query.setParameter("category", searchParameters.get(MovieAttribute.CATEGORY));
	}
	isTitle = false;
	isCountry = false;
	isCategory = false;
    }

    private boolean isSearchParameterCorrect(String parameter)
    {
	return (parameter != null && parameter.trim().length() > 0);
    }

}
