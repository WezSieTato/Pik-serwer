package com.pik.moviecollection.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Robert on 2014-05-31.
 */

@Entity
@Table(name = "CATEGORY", schema = "MovieCollection@kunderapu")
public class Category
{
    @Id
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
}
