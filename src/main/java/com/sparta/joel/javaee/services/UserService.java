package com.sparta.joel.javaee.services;

import com.sparta.joel.javaee.entities.User;
import com.sparta.joel.javaee.datastore.Accounts;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;

@Named
@RequestScoped
public class UserService {

    @Inject
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String welcome(){
        HashMap accounts = Accounts.getAccounts();
        if(accounts.containsKey(user.getName()) && accounts.containsValue(user.getPassword())){
            return "welcome";
        }else{
            return "loginError";
        }
    }

}
