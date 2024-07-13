package org.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClass {
	
	public static RequestSpecification RequestSpecBuild() {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		return reqSpec;
	}
	
	public static ResponseSpecification ResponseSpecBuild() {

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		return resSpec;
	}
	
	

}
