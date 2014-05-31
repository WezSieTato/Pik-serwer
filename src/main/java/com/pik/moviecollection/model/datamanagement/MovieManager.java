package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Movie;

import java.util.List;

/**
 * Created by Robert on 2014-05-24.
 */
public interface MovieManager
{
    String addMovie(Movie movie);
    boolean deleteMovie(String movieID);
    List<Movie> getMovies(int startIndex, int amount);
}
