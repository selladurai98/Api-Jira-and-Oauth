package org.oauth;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.pojo.Api;
import org.pojo.CourseResponse;
import org.pojo.Courses;
import org.pojo.Mobile;
import org.pojo.WebAutomation;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class DeSerialization {
	
public static void main(String[] args) {
		
		//get authorization
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qjAcxge-1daIXPSQgRFZf17kEIA2j--sbv9Wt_2TRMKrOmh8l68DqOpCryLeDF0Tw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";
		String[] s = url.split("code=");
		String[] s1 = s[1].split("&scope=");
		
		
		
		// access token
		
		 String tokenresponse = given().log().all()
				 .queryParam("code",s1[0])
				 .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				 .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				 .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				 .queryParam("grant_type","authorization_code").urlEncodingEnabled(false)
				 
				 .when().post("https://www.googleapis.com/oauth2/v4/token")
				.then().assertThat().statusCode(200).extract().response().asString();
			
		
		JsonPath j = new JsonPath(tokenresponse);
		String token = j.get("access_token");
		
	
		
		// get courses
		 CourseResponse response = given().log().all().queryParam("access_token",token).header("Content-Type","application/json")
				                   .expect().defaultParser(Parser.JSON)
				 .when().get("https://rahulshettyacademy.com/getCourse.php")
				.then().assertThat().statusCode(200).extract().response().as(CourseResponse.class);
		// get course response all values  
		 
		 
		System.out.println(response.getInstructor());
		
		 System.out.println(response.getUrl());
		  
		 System.out.println(response.getServices());
		 
		 System.out.println(response.getExpertise());
		 
		 System.out.println(response.getLinkedIn());
		 
		 
		 
		 
		 Courses c = response.getCourses();
		 
		 
		 
        List<WebAutomation> web = c.getWebAutomation();		

		 for (int i = 0; i <web.size(); i++) {
			 
			 System.out.println(web.get(i).getCourseTitle());
			 
			 System.out.println(web.get(i).getPrice());

		}
		 
		List<Api> api = c.getApi();
		
		for (int i = 0; i <api.size(); i++) {
			
			System.out.println(api.get(i).getCourseTitle());
			
			 System.out.println(api.get(i).getPrice());

		}
		
		 List<Mobile> mobile = c.getMobile();
		 
			for (int i = 0; i <mobile.size(); i++) {
				
				System.out.println(mobile.get(i).getCourseTitle());
				
				 System.out.println(mobile.get(i).getPrice());

			}
		
		 
		 
		
}}
