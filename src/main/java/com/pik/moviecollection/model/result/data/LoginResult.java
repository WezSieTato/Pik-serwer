package com.pik.moviecollection.model.result.data;

import com.pik.moviecollection.model.orm.Token;
import com.pik.moviecollection.model.orm.User;

/**
 * Created by piotr on 26.05.14.
 */
public class LoginResult {
    private User user;
    private Token token;

    public LoginResult(User user, Token token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
