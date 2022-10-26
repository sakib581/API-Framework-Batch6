package com.woa.tests;

import lombok.Data;
import org.json.simple.JSONArray;

@Data
public class Employee {

    private int id;
    private String name;
    private  boolean permanent;
    private int phone_number;
    private String role;
    private String city;

}
