package com.pik.moviecollection.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Robert on 2014-05-22.
 */

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

    @ManyToOne
    @JoinColumn(name="CATEGORY")
    private Category category;


    public String getMovieID()
    {
	return movieID;
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

    public Category getCategory()
    {
	return category;
    }

    public void setCategory(Category category)
    {
	this.category = category;
    }

