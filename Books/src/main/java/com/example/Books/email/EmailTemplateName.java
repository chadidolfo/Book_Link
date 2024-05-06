package com.example.Books.email;

import lombok.Getter;

//Use enums when we have values aren't going to change,
// like month days, days, colors...
@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account");

    private final String name;


    //constructeur
    EmailTemplateName(String name) {
        this.name = name;
    }
}
