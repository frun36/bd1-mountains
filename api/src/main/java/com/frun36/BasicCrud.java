package com.frun36;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class BasicCrud<R> implements HttpHandler {
    protected Class<R> recordClass;
    protected Gson gson = new Gson();

    protected record Response(Integer responseCode, byte[] responseBody) {}

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
            
            Response res = switch (ex.getRequestMethod()) {
                case "GET" -> handleGet(items, ex);
                case "POST" -> handlePost(items, ex);
                case "PUT" -> handlePut(items, ex);
                case "DELETE" -> handleDelete(items, ex);
                default -> new Response(405, null);
            };

            Integer contentSize = res.responseBody == null ? -1 : res.responseBody.length;
            ex.sendResponseHeaders(res.responseCode, contentSize);
            if (!contentSize.equals(-1)) {
                ex.getResponseBody().write(res.responseBody);
            }
        } catch (Exception e) {
            String message = String.format("Error of type %s occurred: %s\n", e.getClass().getName(), e.getMessage());
            byte[] messageBytes = message.getBytes();
            ex.sendResponseHeaders(500, messageBytes.length);
            ex.getResponseBody().write(messageBytes);
        }
    }

    protected Response handleGet(List<R> body, HttpExchange ex) {
        return new Response(501, null);
    }

    protected Response handlePost(List<R> body, HttpExchange ex) {
        return new Response(501, null);
    }

    protected Response handlePut(List<R> body, HttpExchange ex) {
        return new Response(501, null);
    }

    protected Response handleDelete(List<R> body, HttpExchange ex) {
        return new Response(501, null);
    }
}
