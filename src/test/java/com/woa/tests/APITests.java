package com.woa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woa.core.ExtentTestManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class APITests {
    @Test
    public void validateGetAllRequestReturnAllTheData() {
        RestAssured.baseURI = "http://localhost:8090";
        Response response = RestAssured.given()
                .get("employees/all")
                .then()
                //.log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());

    }

    @Test
    public void validateUserCanGetSpecificEmployees() {
        RestAssured.baseURI = "http://localhost:8090/";
        String endpoint = "/employees/1";
        Response response = RestAssured
                .given()
                .when()
                .get(endpoint)
                .then()
                .log()
                .all()
                .extract().response();
        System.out.println(response.asPrettyString());
    }

    @Test
    public void validateUserCanDeleteAnEmployee() {
        RestAssured.baseURI = "http://localhost:8090/";
        String endpoint = "/employees/delete/4";
        Response response = RestAssured
                .given()
                .when()
                .delete(endpoint)
                .then()
                .log()
                .all()
                .extract().response();
        System.out.println(response.asPrettyString());
    }

    @Test
    public void validateAddRequestsAddData() {
        RestAssured.baseURI = "http://localhost:8090";

        JSONObject sakibData = new JSONObject();
        sakibData.put("name", "Sakib");
        sakibData.put("permanent", 1);
        sakibData.put("phone_number", "917330");
        sakibData.put("role", "QA");
        sakibData.put("city", "Ny");

        JSONObject nayemData = (JSONObject) sakibData.clone();
        sakibData.put("name", "Nayem");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(sakibData);
        jsonArray.add(nayemData);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonArray.toJSONString())
                .post("employees/add")
                .then()
                //.log().all()
                // .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void validateUpdateRequestUpdatesTheData() throws IOException {
        RestAssured.baseURI = "http://localhost:8090";
        JSONObject sakibData = new JSONObject();
        sakibData.put("name", "Sakib");
        sakibData.put("permanent", 1);
        sakibData.put("phone_number", "917330");
        sakibData.put("role", "QA");
        sakibData.put("city", "Ny");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .body(sakibData)
                .put("employees/add/6")
                .then()
                //.log().all()
                // .statusCode(200)
                .extract()
                .response();
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);

        //do a get request and make sure the update happened
        Response getResponse = RestAssured.given()
                .get("employees/6")
                .then()
                .extract()
                .response();

        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeResponse = objectMapper.readValue(getResponse.getBody().asString(), Employee.class);

        Assert.assertEquals(employeeResponse.getRole(), "Dev");
        Assert.assertEquals(employeeResponse.getPhone_number(), 917330);

    }

}
