package com.onlinebookstore.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

	private Data data;
    private Error error;
    private String timeStamp;
    private String message;
    private List<String> errorMessages;
    

    public Response(String message, Data data) {
        this.message = message;
        this.data = data;
    }
}
