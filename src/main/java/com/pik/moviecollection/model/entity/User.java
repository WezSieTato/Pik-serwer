package com.pik.moviecollection.model.entity;

import javax.persistence.*;

/**
 * Created by piotr on 26.05.14.
 */
@Entity
@Table(name = "USER", schema = "MovieCollection@kunderapu")
public class User {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private String userID;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String pass;

    public User(String name, String surname, String login, String pass) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.pass = pass;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
