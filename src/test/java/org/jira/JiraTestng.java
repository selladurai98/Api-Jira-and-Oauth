package org.jira;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.details.JiraBodyDetails;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestng {

	SessionFilter  s = new SessionFilter();
	static String Jira_id;
	
	@Test(priority =1)
	public void sessioncreation() {
		RestAssured.baseURI ="http://localhost:8080";
		 String response = given().log().all().header("Content-Type","application/json").
		body(JiraBodyDetails.sessionData("user","pass")).filter(s)
		.when().post("/rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();	
	}
	
	@Test(priority =2)
	public void issuecreation() {
		String  issue = given().log().all().header("Content-Type","application/json").filter(s)
				.body(JiraBodyDetails.issueData("issuedata"))
				   
			.when().post("/rest/api/2/issue")
			.then().log().all().assertThat().statusCode(201).extract().response().asString();
				
				JsonPath j = new JsonPath(issue);
				  Jira_id = j.get("id");
				System.out.println( "id:"+Jira_id);
	}
	
	@Test(priority =3)
	public void comments() {

		given().log().all().pathParam("Jira_Id",Jira_id).header("Content-Type","application/json").filter(s)
		.body(JiraBodyDetails.commentsData("commentsdata"))
		.when().post("/rest/api/2/issue/{Jira_Id}/comment")
		.then().log().all().assertThat().statusCode(201);
	}
	
	@Test(priority =4)
	public void addAttachment() {
		
		given().log().all().pathParam("Jira_Id",Jira_id).header("Content-Type"," multipart/form-data")
		.header("X-Atlassian-Token","no-check").filter(s)
       .multiPart(new File(".//jira.txt"))
       .when().post("/rest/api/2/issue/{Jira_Id}/attachments")
	  .then().log().all().assertThat().statusCode(200);
	}

	@Test(priority =5)
	public void getIssue() {
		 given().log().all().pathParam("Jira_Id",Jira_id).header("Content-Type","application/json").filter(s)
			.when().get("/rest/api/2/issue/{Jira_Id}")
			 .then().log().all().assertThat().statusCode(200);
	}
	
	
	@Test(priority =6)
	public void getComments() {
		given().log().all().pathParam("Jira_Id",Jira_id).header("Content-Type","application/json").filter(s)
		.when().get("/rest/api/2/issue/{Jira_Id}/comment")
		 .then().log().all().assertThat().statusCode(200);
	}
	
	
	
	
	
}
