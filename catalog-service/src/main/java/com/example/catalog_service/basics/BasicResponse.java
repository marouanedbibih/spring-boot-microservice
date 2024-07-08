package com.example.catalog_service.basics;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private Object data;
    private String message;
    private Map<String, String> messagesList;
    private MessageType messageType;
    private String redirectUrl;
    private HttpStatus status;
}


