package org.oauth;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class GetCourses {
	public static void main(String[] args) {
		
		//get authorization
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qgFs9YvGIdTFvBXG2dRSOJV91LICIamhs-u_aY0VUXONUJ0MOE66tc0xgBLMUq-yA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
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
		 String courseResponse = given().log().all().queryParam("access_token",token).header("Content-Type","application/json")
				.when().get("https://rahulshettyacademy.com/getCourse.php")
				.then().assertThat().statusCode(200).extract().response().asString();
		 System.out.println(courseResponse);
		 
		 
		 
		// get course response all values  
		 
		JsonPath j1 = new JsonPath(courseResponse);
		
		String instructor = j1.get("instructor");
		System.out.println("instructor name:"+instructor);
		
		String URL = j1.get("url");
		System.out.println("url:"+URL);
	
		String services = j1.get("services");
		System.out.println("services:"+services);
		
		String expertise = j1.get("expertise");
		System.out.println("expertise:"+expertise);
		
	
		
		
		
		int  size1 = j1.get("courses.webAutomation.size()");
		for (int i = 0; i <size1; i++) {
			String courseTitle = j1.get("courses.webAutomation["+i+"].courseTitle");
			String price= j1.get("courses.webAutomation["+i+"].price");
			System.out.println("webAutomation course title:"+courseTitle);
			System.out.println("webAutomation course price:"+price);
		}
			
     int  size2 = j1.get("courses.api.size()");
		for (int i = 0; i <size2; i++) {
			String courseTitle = j1.get("courses.api["+i+"].courseTitle");
			String price= j1.get("courses.api["+i+"].price");
			System.out.println("api course title:"+courseTitle);
			System.out.println("api course price:"+price);
		}
		
		
	     int  size3 = j1.get("courses.mobile.size()");
			for (int i = 0; i <size3; i++) {
		String courseTitle = j1.get("courses.mobile["+i+"].courseTitle");
		String price= j1.get("courses.mobile["+i+"].price");
		System.out.println("mobile course title:"+courseTitle);
		System.out.println("mobile course price);"+price);
	
	}
			
		String linkedIn= j1.get("linkedIn");
		System.out.println("linkedIn url:"+linkedIn);
		
	}
}
