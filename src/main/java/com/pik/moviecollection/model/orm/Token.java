package com.pik.moviecollection.model.orm;

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

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
