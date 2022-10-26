package com.woa.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

public class APITests3 {

@Test
    public void createNewData() throws Exception {
        RestAssured.baseURI = "http://localhost:8090";

        int idNumber=6;
        JSONObject newData = new JSONObject();
        newData.put("name", "Sam");
        newData.put("permanent",idNumber);
        newData.put("phone_number", "45676");
        newData.put("role", "QA Tester");
        newData.put("city", "Sylhet");

        JSONArray jsonArray=new JSONArray();
        jsonArray.add(newData);

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

        ObjectMapper objectMapper = new ObjectMapper();
        //Employee employeeResponse = objectMapper.readValue(response.getBody().asString(), Employee.class);

        DBConnection dbConnection=DBConnection.getInstance();
        dbConnection.getConnection();
        dbConnection.getStatement();
        JSONArray dataInJson=dbConnection.getJsonArrayOfResultSet("SELECT * FROM worldOfAutomation.employee where id='"+idNumber+"'");
        List<Employee> addressListDB = objectMapper.readValue(dataInJson.toJSONString(), new TypeReference<List<Employee>>() {});

        Assert.assertEquals(addressListDB.get(0).getId(),idNumber);
        Assert.assertEquals(addressListDB.get(0).getName(),"Jack");

    }
}
