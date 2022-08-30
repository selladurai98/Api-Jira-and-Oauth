package org.details;

public class JiraBodyDetails {
	
	public  static String sessionData(String user , String pass) {	
	return("{ \"username\": \"selladurai0073\", \"password\": \"Selladurai@12345\" }");
	
	}
	public   static String issueData(String issuedata) {
		
		return("{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"JIRA\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Search button is not working.\",\r\n" + 
				"       \"description\": \"Creating of an issue using ids for projects and issue types using the REST API\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}");
		
	}

	public  static String commentsData( String commentsdata) {	
		return("{\r\n" + 
				"    \"body\": \"this is my first comment\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}");
	
	}}
	
	
	
	
	
	
	
	
	
