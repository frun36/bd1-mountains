package com.frun36;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import com.frun36.model.DbRow;
import com.frun36.model.factory.DbRowFactory;
import com.google.gson.Gson;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicCrud<R extends DbRow> implements HttpHandler {
    protected Class<R> recordClass;
    protected String tableName;
    protected Connection conn;
    protected Gson gson = new Gson();

    protected record Response(Integer responseCode, byte[] responseBody) {
    }

    public BasicCrud(Connection conn, Class<R> recordClass, String tableName) {
        this.conn = conn;
        this.recordClass = recordClass;
        this.tableName = tableName;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        try {
            String method = ex.getRequestMethod();
            InputStream body = ex.getRequestBody();
            String jsonString = new String(body.readAllBytes());
            System.out.printf("%s %s\n%s\n\n", method, ex.getRequestURI(), jsonString);

            R item = gson.fromJson(jsonString, recordClass);
            Integer id = getRequestedId(ex);

            Response res = switch (method) {
                case "GET" -> handleGet(id, ex);
                case "POST" -> handlePost(item, ex);
                case "PUT" -> handlePut(id, item, ex);
                case "DELETE" -> handleDelete(id, ex);
                case "OPTIONS" -> new Response(200, null);
                default -> new Response(405, null);
            };

            Integer contentSize = res.responseBody == null ? -1 : res.responseBody.length;
            ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            ex.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            ex.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            ex.sendResponseHeaders(res.responseCode, contentSize);
            if (!contentSize.equals(-1)) {
                ex.getResponseBody().write(res.responseBody);
            }
            ex.close();
        } catch (Exception e) {
            String message = String.format("Error of type %s occurred: %s\n", e.getClass().getName(), e.getMessage());
            byte[] messageBytes = message.getBytes();
            ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            ex.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            ex.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            ex.sendResponseHeaders(500, messageBytes.length);
            ex.getResponseBody().write(messageBytes);
            ex.close();
        }
    }

    protected Response handleGet(Integer id, HttpExchange ex) throws SQLException {
        String sql;
        if (id == null) {
            sql = String.format("SELECT * FROM %s", this.tableName);
        } else {
            sql = String.format("SELECT * FROM %s WHERE id = ?", this.tableName);
        }

        PreparedStatement ps = this.conn.prepareStatement(sql);
        if (id != null)
            ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        List<DbRow> rows = new ArrayList<DbRow>();
        while (rs.next()) {
            DbRowFactory factory = DbRow.getFactory(this.recordClass);
            DbRow rec = factory.fromResult(rs);
            rows.add(rec);
        }

        String json = this.gson.toJson(rows);
        return new Response(200, json.getBytes());
    }

    protected Response handlePost(R item, HttpExchange ex) throws SQLException {
        Map<String, Object> map = item.getMap();

        List<String> columnNames = map.keySet().stream().filter(cn -> map.get(cn) != null).collect(Collectors.toList());

        String sqlNames = columnNames.stream().collect(Collectors.joining(", "));
        String placeholders = columnNames.stream().map(cn -> "?").collect(Collectors.joining(", "));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", this.tableName, sqlNames, placeholders);
        PreparedStatement ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (Integer i = 0; i < columnNames.size(); i++) {
            Object val = map.get(columnNames.get(i));

            switch (val) {
                case String string:
                    ps.setString(i + 1, string);
                    break;
                case Integer integer:
                    ps.setInt(i + 1, integer);
                    break;
                case Timestamp timestamp:
                    ps.setTimestamp(i + 1, timestamp);
                    break;
                default:
                    return new Response(500, "Bad data type in record".getBytes());
            }
        }

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            String insertedId = String.format("{\"id\": %d}", rs.getLong(1));
            return new Response(200, insertedId.getBytes());
        } else {
            return new Response(500, "No rows updated".getBytes());
        }
    }

    protected Response handlePut(Integer id, R item, HttpExchange ex) {
        return new Response(501, null);
    }

    protected Response handleDelete(Integer id, HttpExchange ex) throws SQLException {
        String sql;
        if (id == null) {
            sql = String.format("DELETE FROM %s", this.tableName);
        } else {
            sql = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        }

        PreparedStatement ps = this.conn.prepareStatement(sql);

        if (id != null)
            ps.setInt(1, id);

        Integer deleted = ps.executeUpdate();
        String response = String.format("{\"deleted\": %d}", deleted);

        return new Response(200, response.getBytes());
    }

    Integer getRequestedId(HttpExchange ex) {
        String query = ex.getRequestURI().normalize().getQuery();
        if (query == null)
            return null;

        String[] params = query.split("&");
        for (String param : params) {
            if (param.startsWith("id=")) {
                String idString = param.substring(3);
                return Integer.valueOf(idString);
            }
        }
        return null;
    }
}
