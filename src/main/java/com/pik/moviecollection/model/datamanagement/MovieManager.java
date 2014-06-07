package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import java.util.List;
import java.util.Map;

/**
 * Created by Robert on 2014-05-24.
 */
public interface MovieManager
{
    String addMovie(Movie movie);
    boolean deleteMovie(String movieID);
    List<Movie> getMovies(int startPosition, int maxResults);
    List<Movie> getMovies(Map<MovieAttribute, String> searchParameters, int startPosition, int maxResults);
}
