package com.pik.moviecollection.model.datamanegement;

import java.util.Map;

/**
 * Created by Robert on 2014-05-24.
 */
public interface MovieManager
{
    boolean addMovie(Map<String, String> movieParameters);
    // boolean edit();
    // find();
    boolean deleteMovie(String movieID);
}
