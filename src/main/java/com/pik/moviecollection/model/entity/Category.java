package com.pik.moviecollection.model.entity;

import javax.persistence.*;

/**
 * Created by Robert on 2014-05-31.
 */

@Entity
@Table(name = "CATEGORY", schema = "MovieCollection@kunderapu")
public class Category
{
    @Id
    @GeneratedValue (strategy=GenerationType.AUTO)
    @Column(name = "CATEGORY_ID")
    private String categoryID;

    @Column(name = "NAME")
    private String name;

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getCategoryID()
    {
	return categoryID;
    }
}
