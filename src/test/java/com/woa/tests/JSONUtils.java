package com.woa.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {
    @Test
    public void readMultipleJson() throws IOException, ParseException {
        JSONParser js= new JSONParser();
        JSONArray jsonArray=(JSONArray) js.parse(new FileReader("src/main/resources/SingleArray_MultipleJson.json"));

        System.out.println(jsonArray);

        JSONObject jso=(JSONObject) jsonArray.get(1);
        System.out.println(jso);
        System.out.println(jso.get("id"));
    }
@Test
    public void jsonInMultipleJson() throws IOException, ParseException {
        JSONParser js=new JSONParser();
        JSONArray jsonArray=(JSONArray) js.parse(new FileReader("src/main/resources/SingleArray_JsonInMultipleJson.json"));
        System.out.println(jsonArray);

        JSONObject jso1=(JSONObject) jsonArray.get(0);
        JSONObject jso2=(JSONObject) jsonArray.get(1);

        JSONObject jso1_object=(JSONObject) jso1.get("data");
        
    }
}
