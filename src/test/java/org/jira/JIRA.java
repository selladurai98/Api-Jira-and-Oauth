package org.jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;



public class JIRA {

	public static void main(String[] args) {
		
		 //session creation 
		RestAssured.baseURI ="http://localhost:8080";
		SessionFilter  s = new SessionFilter();
		 String response = given().log().all().header("Content-Type","application/json").
		 body("{ \"username\": \"selladurai0073\", \"password\": \"Selladurai@12345\" }").filter(s)
		.when().post("/rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		
		 // issue creation 
		String  issue = given().log().all().header("Content-Type","application/json").filter(s)
		.body("{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"JIRA\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Logout button is not working\",\r\n" + 
				"       \"description\": \"Creating of an issue using ids for projects and issue types using the REST API\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}")
		   
	   .when().post("/rest/api/2/issue")
	   .then().log().all().assertThat().statusCode(201).extract().response().asString();
	    JsonPath j = new JsonPath(issue);
		String jira_id = j.get("id");
		System.out.println(jira_id);
		
		
		// add comments 
		given().log().all().pathParam("Jira_Id",jira_id).header("Content-Type","application/json").filter(s)
		.body("{\r\n" + 
				"    \"body\": \"this is my first comment from eclipse\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}")
		.when().post("/rest/api/2/issue/{Jira_Id}/comment")
		.then().log().all().assertThat().statusCode(201);
	
	// add attachment
		
		given().log().all().pathParam("Jira_Id",jira_id).header("Content-Type"," multipart/form-data")
		.header("X-Atlassian-Token","no-check").filter(s)
        .multiPart(new File(".//jira.txt"))
        .when().post("/rest/api/2/issue/{Jira_Id}/attachments")
	    .then().log().all().assertThat().statusCode(200);
	
	// get issue 
	    given().log().all().pathParam("Jira_Id",jira_id).header("Content-Type","application/json").filter(s)
		.when().get("/rest/api/2/issue/{Jira_Id}")
		.then().log().all().assertThat().statusCode(200);
	    
	// get comments
		given().log().all().pathParam("Jira_Id",jira_id).header("Content-Type","application/json").filter(s)
		.when().get("/rest/api/2/issue/{Jira_Id}/comment")
		.then().log().all().assertThat().statusCode(200);
	
		
	
	}
	
	
	
}
