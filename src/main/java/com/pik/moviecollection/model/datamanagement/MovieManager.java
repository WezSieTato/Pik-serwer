package com.pik.moviecollection.model.datamanagement;

import java.util.Map;

/**
 * Created by Robert on 2014-05-24.
 */
public interface MovieManager
{
    String addMovie(Map<String, String> movieParameters);
    boolean deleteMovie(String movieID);
}
