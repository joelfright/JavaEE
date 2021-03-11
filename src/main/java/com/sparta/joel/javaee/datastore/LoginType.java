package com.sparta.joel.javaee.datastore;

import javax.decorator.Decorator;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
@Decorator
public enum LoginType {

    ADMIN, USER

}
