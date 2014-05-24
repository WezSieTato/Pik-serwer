package com.pik.moviecollection.model; /**
 * Created by Robert on 2014-05-22.
 */

import javax.persistence.*;

@Entity
@Table(name = "MOVIE", schema = "MovieCollection@kunderapu")
public class Movie
{
    @Id
    @Column(name="MOVIE_ID")
    public String movieID;

    @Column(name = "TITLE")
    public String title;

    @Column(name = "COUNTRY")
    public String country;
}
