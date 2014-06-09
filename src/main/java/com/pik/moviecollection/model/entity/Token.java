package com.pik.moviecollection.model.entity;

import javax.persistence.*;

/**
 * Created by piotr on 26.05.14.
 */
@Entity
@Table(name = "TOKEN", schema = "MovieCollection@kunderapu")
public class Token {
    @Id
    @GeneratedValue
    @Column(name="TOKEN_ID")
    private String tokenID;

    @Column(name = "CODE")
    private String code;

    @Column(name="USER_ID")
    private String userID;

    public Token(String code, User user) {
        this.code = code;
        this.userID = user.getUserID();
    }

    public Token() {
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
