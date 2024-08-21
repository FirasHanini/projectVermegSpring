package com.example.test.serviceDemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder


public class ErrorResponse {

    private  String errorMessage ;
    private String errorCode;





    public static ErrorResponse valueOf(String errorCode,String errorMessage) {
        return ErrorResponse.builder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();
    }


}
