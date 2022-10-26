package com.woa.core;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBConnection {
    private static DBConnection dbInstance;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private DBConnection() {

    }

    public static DBConnection getInstance() {
        if (dbInstance == null) {
            dbInstance = new DBConnection();
        }
        return dbInstance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/classicmodels";
            String uName = "root";
            String password = "root1234";
            connection = DriverManager.getConnection(url, uName, password);
        }
        return connection;
    }

    public Statement getStatement() throws SQLException {
        if (statement == null) {
            statement = connection.createStatement();
        }
        return statement;
    }

    public ResultSet getResultSet(String query) throws SQLException {

        //   return statement.executeQuery(query);
        resultSet = statement.executeQuery(query);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) {
                    System.out.print(",  ");
                }
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
        //Below methods can be used to get columns individually
        //   System.out.println(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
        return null;
    }

    public JSONArray getJsonArrayOfResultSet(String query) throws Exception{
        resultSet = statement.executeQuery(query);
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
                .mapToObj(i -> {
                    try {
                        return md.getColumnName(i + 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "?";
                    }
                })
                .collect(Collectors.toList());

        JSONArray result = new JSONArray();
        while (resultSet.next()) {
            JSONObject row = new JSONObject();
            colNames.forEach(cn -> {
                try {
                    row.put(cn, resultSet.getObject(cn));
                } catch (JSONException | SQLException e) {
                    e.printStackTrace();
                }
            });
            result.add(row);
        }
        return result;

    }


}