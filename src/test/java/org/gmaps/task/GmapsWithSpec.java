package org.gmaps.task;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import org.base.BaseClass;
import org.pojo.delete.DeleteRequestBody;
import org.pojo.delete.DeleteResponseBody;
import org.pojo.get.GetResponseBOdy;
import org.pojo.post.Location;
import org.pojo.post.PostRequestBody;
import org.pojo.post.PostResponseBody;
import org.pojo.put.PutRequestBody;
import org.pojo.put.PutResponseBody;
import org.testng.Assert;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GmapsWithSpec extends BaseClass{

	public static void main(String[] args) {

		PostRequestBody prb = new PostRequestBody();
		Location pl = new Location();
		List<String> lp = new ArrayList<String>();

		system.out.println("***** G I T H U B *****");

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

//POST METHOD	
		
		RequestSpecification reqSpec = RequestSpecBuild();
		ResponseSpecification resSpec = ResponseSpecBuild();

		System.out.println("POST METHOD REQUEST BODY:- \n");

		PostResponseBody postResBody = given().log().body().spec(reqSpec).body(prb).when().post("/maps/api/place/add/json").then()
				.spec(resSpec).extract().response().as(PostResponseBody.class);
		
		System.out.println("POST METHOD RESPONSE BODY:- \n");
		System.out.println("ID : \t" + postResBody.getId()+"\n");
		String place_id = postResBody.getPlace_id();
		System.out.println("PLACE ID : \t" + place_id+"\n");
		System.out.println("REFERENCE : \t" + postResBody.getReference()+"\n");
		System.out.println("SCOPE : \t" + postResBody.getScope()+"\n");
		System.out.println("STATUS : \t" + postResBody.getStatus()+"\n");

//PUT METHOD

		PutRequestBody putReqBody = new PutRequestBody();
		putReqBody.setPlace_id(place_id);
		putReqBody.setAddress("Thaneer Panthal, Coimbatore, Tamil Nadu");
		putReqBody.setKey("qaclick123");

		System.out.println("UPDATING ADDRESS BY USING PUT METHOD :- \n");

		PutResponseBody putResBody = given().spec(reqSpec).body(putReqBody).
				when().put("/maps/api/place/update/json")
				.then().spec(resSpec).extract().response().as(PutResponseBody.class);
		System.out.println("PUT METHOD RESPONSE BODY:- \n");
		System.out.println("MESSAGE : \t" +putResBody.getMsg()+"\n");

//GET METHOD

		System.out.println("RETRIVEING LOCATION AFTER UPDATION OF ADDRESS:- \n");

		GetResponseBOdy grp = given().spec(reqSpec).queryParam("place_id", place_id).when()
				.get("/maps/api/place/get/json").then().spec(resSpec).extract().response()
				.as(GetResponseBOdy.class);
		System.out.println("GET METHOD RESPONSE BODY:- \n");
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
		
		DeleteResponseBody delResBody = given().spec(reqSpec).body(drp).when()
				.delete("/maps/api/place/delete/json").then().log().body().spec(resSpec).
				extract().response().as(DeleteResponseBody.class);
		System.out.println("DELETE METHOD RESPONSE BODY:- \n");
		System.out.println("STATUS : \t" +delResBody.getStatus()+"\n");		

//GET METHOD
		
		System.out.println("RETRIVEING LOCATION AFTER DELETE:- \n");

		GetResponseBOdy grpDel = given().spec(reqSpec).queryParam("place_id", place_id).when()
				.get("/maps/api/place/get/json").then().log().body().assertThat().statusCode(404).extract().response()
				.as(GetResponseBOdy.class);
		
		System.out.println("MESSAGE : \t" +grpDel.getMsg()+"\n");
	}
}
