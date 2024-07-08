package com.example.catalog_service.basics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicException extends Exception {

    private BasicResponse response;

    public BasicException(String message) {
        super(message);
    }

    public BasicException(BasicResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    
}
