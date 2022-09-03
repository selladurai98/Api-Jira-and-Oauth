package org.oauth;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.oauth.*;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class RestAssuredSerialization {
	
	
	public static void main(String[] args) {
	
	
	  Pet pet = new Pet();

        pet.setId(123);
	  
	  Category category = new Category();
	  category.setId(12);
	  category.setName("tom");
	  
	 pet.setCategory(category);
	 
	 pet.setName("doggie");
	 
	 List<String> photoUrls = new ArrayList<String>();
	 
	 photoUrls.add("photo");
	 
	 pet.setPhotoUrls(photoUrls);
	 
	 Tags t =  new Tags();
	 t.setId(45);
	 t.setName("tom");
	 
	 Tags t1 =  new Tags();
	 t1.setId(50);
	 t1.setName("tom");
	 
	 List<Tags> tags = new ArrayList<Tags>();
	 
	 tags.add(t);
	 tags.add(t1);

	 pet.setTags(tags);
	 
	 pet.setStatus("available");
	 

	  
	RestAssured.baseURI = "https://petstore.swagger.io/v2";

	 String postresponse = given().log().all().header("Content-Type","application/json").body(pet)
		 .when().post("/pet") 
		.then().assertThat().statusCode(200).extract().response().asString();
	 System.out.println("response :"+postresponse);
	 
	 JsonPath j = new JsonPath(postresponse);
	  int i = j.getInt("id");
	 System.out.println("pet ID:"+i);
	 
	 
	 
	 
	 
	
			given().log().all().header("Content-Type","application/json").pathParam("id",i)
			.when().get("/pet/{id}")
			.then().log().all().assertThat().statusCode(200);
		 
		  
		
			  given().log().all().header("Content-Type","application/json").queryParam("status","sold")
			  .when().get("/pet/findByStatus")
			 .then().log().all().assertThat().statusCode(200);
		  
		  

	 

}}
