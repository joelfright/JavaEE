package com.sparta.joel.javaee.datastore;

import java.util.HashMap;

public class Accounts {

    private static HashMap<String, String> accounts = new HashMap<>();

    static{
        accounts.put("Joel","password");
        accounts.put("Kurtis","password");
        accounts.put("Bradley","password");
        accounts.put("Wahdel","password");
        accounts.put("Aaron","password");
        accounts.put("Dominic","password");
        accounts.put("Malik","password");
    }

    public static HashMap<String, String> getAccounts(){
        return accounts;
    }

}
