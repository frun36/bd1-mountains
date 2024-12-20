package com.frun36;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class BasicCrud<R> implements HttpHandler {
    protected Class<R> recordClass;
    protected Gson gson = new Gson();

    public BasicCrud(Class<R> recordClass) {
        this.recordClass = recordClass;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        System.out.println("Helou");
        InputStream body = ex.getRequestBody();
        String jsonString = new String(body.readAllBytes());
        System.out.println(jsonString);

        try {
            Type listType = TypeToken.getParameterized(List.class, this.recordClass).getType();
            System.out.println(listType);

            List<R> items = gson.fromJson(jsonString, listType);
            System.out.println("Items:");
            items.forEach(System.out::println);

            ex.sendResponseHeaders(200, -1);
        } catch (JsonParseException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }
}
