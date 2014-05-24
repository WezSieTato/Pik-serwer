package com.pik.moviecollection.model.orm; /**
 * Created by Robert on 2014-05-22.
 */

import javax.persistence.*;

@Entity
@Table(name = "MOVIE", schema = "MovieCollection@kunderapu")
public class Movie
{
    @Id
    @GeneratedValue
    @Column(name="MOVIE_ID")
    private String movieID;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "COUNTRY")
    private String country;

    public String getMovieID()
    {
	return movieID;
    }

    public void setMovieID(String movieID)
    {
	this.movieID = movieID;
    }

    public String getTitle()
    {
	return title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getCountry()
    {
	return country;
    }

    public void setCountry(String country)
    {
	this.country = country;
    }
}
