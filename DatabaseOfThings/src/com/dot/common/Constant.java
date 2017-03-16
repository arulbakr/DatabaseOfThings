package com.dot.common;

public final class Constant {
	
	// Database constants start here
	// DB credentials
	public static final String DB_USERNAME = "cassandra";
	public static final String DB_PASSWORD = "cassandra";
	// Keyspace
	public static final String DB_IPADDRESS = "localhost"; //127.0.0.1
	public static final String DB_KEYSPACE = "DatabaseOfThings";
	// Table/ColumnFamily
	public static final String DB_TABLE_THINGS = "Things";
	public static final String THING_NAME = "Name";
	public static final String THING_OBJECTID = "ThingID";
	public static final String THING_ATTRIBUTES = "Attributes";
	// Database constants end here
	
	//Application Constants start here
	public static final String JSON_KEY_RESULT = "Result";
	public static final String JSON_VALUE_TRUE = "True";
	public static final String JSON_VALUE_FALSE = "False";
	public static final String JSON_MESSAGE = "Message";
	
	public static final String DATEFORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String NEWLINE_CHAR = "\n";
	//Application Constants end here
	
	//Cloud service constants start here
	//public static final String CLOUD_IMAGEPATH = "D:\\Java_Projects\\DatabaseOfThings\\Uploads";	//Local
	public static final String CLOUD_IMAGEPATH = "/webapps/dotPhotos/";	//Server
	public static final String SERVER_URL = "http://things.photos/DatabaseOfThings";//"http://localhost:8080/DatabaseOfThings/";
	public static final String REST_URL_IMAGE = "/get/thingimage?id=";
	//Cloud service constants end here
}