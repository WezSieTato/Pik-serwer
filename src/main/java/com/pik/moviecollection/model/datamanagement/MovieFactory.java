package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import java.util.Map;

/**
 * Created by Robert on 2014-05-26.
 */
public class MovieFactory
{
    public static Movie createMovie(Map<String, String> movieParameters)
    {
	Movie movie = new Movie();
	movie.setTitle(movieParameters.get("title"));
	movie.setCountry(movieParameters.get("country"));

	return movie;
    }
}
