package com.sparta.joel.javaee.entities;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.HashMap;

@Named
@RequestScoped
public class User {

    private HashMap<String, String> account = new HashMap<>();
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
