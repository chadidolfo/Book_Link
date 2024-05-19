package com.example.Books.handler;



import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
public enum BusinessErrorCode {

    NO_CODE(0, NOT_IMPLEMENTED, "No code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Login and / or Password is incorrect"),

    ;

    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;



    BusinessErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
