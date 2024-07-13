package org.gmaps.task;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;
import org.pojo.delete.DeleteRequestBody;
import org.pojo.delete.DeleteResponseBody;
import org.pojo.get.GetResponseBOdy;
import org.pojo.post.Location;
import org.pojo.post.PostRequestBody;
import org.pojo.post.PostResponseBody;
import org.pojo.put.PutRequestBody;
import org.pojo.put.PutResponseBody;
import org.testng.Assert;

public class HappiestMindsLocation {

	public static void main(String[] args) {

		PostRequestBody prb = new PostRequestBody();
		Location pl = new Location();
		List<String> lp = new ArrayList<String>();

		prb.setAccuracy(75);
		prb.setAddress("Electronics City Phase 1, Hosur Road, Bengaluru");
		prb.setLanguage("English,Tamil,Kannada");
		prb.setLocation(pl);
		pl.setLat(-68.383494);
		pl.setLng(63.427362);
		prb.setName("HappiestMinds");
		prb.setPhone_number("(+91) 123 456 7890");
		lp.add("IT SERVICES");
		lp.add("IT");
		prb.setTypes(lp);
		prb.setWebsite("https://www.happiestminds.com");

		RestAssured.baseURI = "https://rahulshettyacademy.com";

//POST METHOD	

		System.out.println("POST METHOD REQUEST BODY:- \n");

		PostResponseBody postResBody = given().log().body().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(prb).when().post("/maps/api/place/add/json").then()
				.log().body().assertThat().statusCode(200).extract().response().as(PostResponseBody.class);

		System.out.println("ID : \t" + postResBody.getId());
		String place_id = postResBody.getPlace_id();
		System.out.println("PLACE ID : \t" + place_id);
		System.out.println("REFERENCE : \t" + postResBody.getReference());
		System.out.println("SCOPE : \t" + postResBody.getScope());
		System.out.println("STATUS : \t" + postResBody.getStatus());

//PUT METHOD

		PutRequestBody putReqBody = new PutRequestBody();
		putReqBody.setPlace_id(place_id);
		putReqBody.setAddress("Thaneer Panthal, Coimbatore, Tamil Nadu");
		putReqBody.setKey("qaclick123");

		System.out.println("UPDATING ADDRESS BY USING PUT METHOD :- \n");

		PutResponseBody putResBody = given().log().body().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(putReqBody).when().put("/maps/api/place/update/json")
				.then().log().body().assertThat().statusCode(200).extract().response().as(PutResponseBody.class);

		System.out.println(putResBody.getMsg());

//GET METHOD

		System.out.println("RETRIVEING LOCATION AFTER UPDATION OF ADDRESS:- \n");

		GetResponseBOdy grp = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("/maps/api/place/get/json").then().log().body().assertThat().statusCode(200).extract().response()
				.as(GetResponseBOdy.class);

		System.out.println("1.LOCATION:- \n");
		System.out.println("\t LATITUDE:\t" + grp.getLocation().getLatitude());
		System.out.println("\t LONGITUDE:\t" + grp.getLocation().getLongitude());
		System.out.println("\n2.ACCURACY:\t" + grp.getAccuracy());
		System.out.println("\n3.NAME:\t" + grp.getName());
		System.out.println("\n4.PH.NO:\t" + grp.getPhone_number());
		System.out.println("\n5.ADDRESS:\t" + grp.getAddress());
		System.out.println("\n6.TYPES:\t" + grp.getTypes());
		System.out.println("\n7.WEBSITE:\t" + grp.getWebsite());
		System.out.println("\n8.LANGUAGE:\t" + grp.getLanguage());

//TESTNG ASSERTION

		Assert.assertEquals(grp.getAddress(), putReqBody.getAddress());

//DELETE METHOD

		System.out.println("DELETING LOCATION :- \n");

		DeleteRequestBody drp = new DeleteRequestBody();
		drp.setPlace_id(place_id);
		
		DeleteResponseBody delResBody = given().queryParam("key", "qaclick123").body(drp).when()
				.delete("/maps/api/place/delete/json").then().log().body().assertThat().statusCode(200).
				extract().response().as(DeleteResponseBody.class);
		
		System.out.println(delResBody.getStatus());		

//GET METHOD
		
		System.out.println("RETRIVEING LOCATION AFTER DELETE:- \n");

		GetResponseBOdy grpDel = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("/maps/api/place/get/json").then().log().body().assertThat().statusCode(404).extract().response()
				.as(GetResponseBOdy.class);
		
		System.out.println(grpDel.getMsg());

	}
}
