package org.pojo.googlemap;

import java.util.ArrayList;
import java.util.List;
import io.restassured.RestAssured;
import  static io.restassured.RestAssured.*;

public class GoogleMap {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AppResponse app=new AppResponse();
		
		
	
         app.setAccuracy(50);
         app.setLanguage("French_IN");
         app.setPhone_number("9095526432");
         app.setName("Frontline house");
         app.setWebsite("www.google/com");
         app.setAddress("29,sidelayout,cohen09");
         
         List<String> types = new ArrayList<String>();
         types.add("shop");
         types.add("show park");
         
         app.setTypes(types);
         
         Location location = new Location();
         location.setLat(38.383494);
         location.setLng(33.427362);
         
         app.setLocation(location);
         
         
		Response r = given().log().all().headers("Content-Type","application/json")
	                 .queryParam("key","qaclick123").body(app)
	        .when().post("/maps/api/place/add/json")
	        .then().assertThat().statusCode(200).extract().response().as(Response.class);
		System.out.println(r.getId());
	    System.out.println(r.getPlace_id());
	    System.out.println(r.getReference());
	    System.out.println(r.getScope());
	    System.out.println(r.getStatus());


	}
}
