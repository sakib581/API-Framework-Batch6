package com.woa.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woa.core.DBConnection;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class APITests2 {

    @Test
    public void validateAUserCanRequestReturnAllTheData() throws JsonProcessingException, SQLException {
        RestAssured.baseURI = "http://localhost:8090";

        Response response = RestAssured.given()
                .get("employees/all")
                .then()
                // .log().all()
                //.assertThat()
                //.statusCode(200)
                .extract()
                .response();

        //below code is validating weather our get method worked or not

        ObjectMapper objectMapper = new ObjectMapper();

        Employee employeeResponse = objectMapper.readValue(response.getBody().asString(), Employee.class);
        List<Employee> employeeList = Arrays.asList(employeeResponse);

        System.out.println(employeeList);
        System.out.println(employeeList.size());


        //System.out.println(employeeResponse.length);
        //System.out.println(employeeResponse[0]);

        // do a db query and get all the data from db from this table
        // store the data into Employee[] object

        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.getConnection();
        dbConnection.getStatement();
        List<String> data1 = (List<String>) dbConnection.getResultSet("SELECT * FROM worldOfAutomation.employee");
        System.out.println(data1);

    }

    @Test
    public void validateAUserCanRequestReturnSpecificData() {
        RestAssured.baseURI = "http://localhost:8090";

        Response response = RestAssured.given()
                .get("employees/1")
                .then()
                .log().all()
                //.assertThat()
                // .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());
    }

    @Test
    public void validateAUserCanDeleteTheData() {
        RestAssured.baseURI = "http://localhost:8090";

        Response response = RestAssured.given()
                .delete("employees/1")
                .then()
                .extract()
                .response();
    }

    @Test
    public void validateAUserCanAddAllTheData() {
        RestAssured.baseURI = "http://localhost:8090";

        JSONObject mydata = new JSONObject();
        mydata.put("name", "Tawhed");
        mydata.put("permanent", 1);
        mydata.put("phone_number", "11416");
        mydata.put("role", "qa");
        mydata.put("city", "ny");

        JSONObject mydata2 = (JSONObject) mydata.clone();

        mydata.put("name", "Rahman");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(mydata);
        jsonArray.add(mydata2);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonArray.toJSONString())
                .post("employees/add")
                .then()
                .extract()
                .response();

        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void validateAUserCanUpdateTheData() throws JsonProcessingException, SQLException {
        RestAssured.baseURI = "http://localhost:8090";
        JSONObject mydata = new JSONObject();
        mydata.put("name", "Tawhed");
        mydata.put("permanent", 1);
        mydata.put("phone_number", "11416");
        mydata.put("role", "Dev");
        mydata.put("city", "ozonepark");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .body(mydata)
                .put("employees/update/6")
                .then()
                .extract()
                .response();
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);

        Response response2 = RestAssured.given()
                .get("employees/6")
                .then()
                .extract()
                .response();
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeResponse = objectMapper.readValue(response2.getBody().asString(), Employee.class);

        Assert.assertEquals(employeeResponse.getRole(), "Dev");

        // query the db  and store the data into Employee object
        // Employee employeeDB = ??


        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.getConnection();
        dbConnection.getStatement();
        List<String> data = (List<String>) dbConnection.getResultSet("SELECT * FROM worldOfAutomation.employee");
        System.out.println(data);

        ObjectMapper objectMapper2 = new ObjectMapper();
        Employee employeeDB = objectMapper2.readValue(response2.getBody().asString(), Employee.class);
        Assert.assertEquals(employeeDB.getCity(), "ozonepark");
        Assert.assertEquals(employeeResponse.getRole(), "Dev");
    }

}
