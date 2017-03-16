package com.dot.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.dot.common.Constant;
import com.dot.model.Thing;
import com.dot.utilities.Utility;

/**
 * 
 * @author arullap
 *
 */
public class CassandraDatabase implements Database {
	
	Cluster cluster = null;
	Session session = null;
	
	/**
	 * @author arullap
	 * @return JSON
	 */
	@Override
	public String getThingByID(String thingID) {
		String response = "";
        
        try {
        	// Connect to the cluster and key space "DatabaseOfThings"
			cluster = Cluster.builder().addContactPoint(Constant.DB_IPADDRESS).withCredentials(Constant.DB_USERNAME, 
					Constant.DB_PASSWORD).build();
			session = cluster.connect(Constant.DB_KEYSPACE);
			
    		String query = String.format("SELECT * FROM %1s WHERE ThingID = %2s", Constant.DB_TABLE_THINGS, thingID);
    		System.out.println(String.format("Formed query ::: %1s", query));
    		
    		ResultSet results = session.execute(query);
    		for (Row row : results) {
    	        Map<String, String> attributes = row.getMap(Constant.THING_ATTRIBUTES, String.class, String.class);
	        	JsonObjectBuilder attributesJson = Json.createObjectBuilder();
    	        
    			for (Map.Entry<String, String> attribute : attributes.entrySet()) {
					attributesJson.add(attribute.getKey(), attribute.getValue());
				}
    			response = attributesJson
    		            	.add(Constant.THING_OBJECTID, row.getUUID(Constant.THING_OBJECTID).toString())
    		            	.add(Constant.THING_NAME, row.getString(Constant.THING_NAME))
    		            	.build()
    		            	.toString();
    		}
        } catch(Exception e) {
        	System.out.println(String.format("Exception at getThingByID method in CassandraDatabase ::: %s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE).build().toString();
        } finally {
			if(cluster != null){
				cluster.close();
			}
			if(session != null){
				session.close();
			}
		}
        
        System.out.println(String.format("Json ::: 1%s", response));
        return response;
	}
	
	/**
	 * @author arullap
	 * @param thing object
	 * @return JSON
	 */
	@Override
	public String addThing(Thing thing) {
		String response = "";
		
		try {
			// Connect to the cluster and key space "DatabaseOfThings"
			cluster = Cluster.builder().addContactPoint(Constant.DB_IPADDRESS).withCredentials(Constant.DB_USERNAME, 
					Constant.DB_PASSWORD).build();
			session = cluster.connect(Constant.DB_KEYSPACE);
			
			UUID currentUUID = UUIDs.timeBased();
			// Insert one record into the things table
			String query = String.format("INSERT INTO %s (ThingID, Name, AddedDate, Attributes) VALUES(%s, '%s', dateof(now()), %s)", 
					Constant.DB_TABLE_THINGS, currentUUID, thing.getName(), thing.getAttributes());
			System.out.println(String.format("Insert Query ::: %1s", query));
			
			session.execute(query);
			
			response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_TRUE)
					.add(Constant.JSON_MESSAGE, currentUUID.toString()).build().toString();
		}
		catch (Exception e) {
			System.out.println(String.format("Exception at addThing method in CassandraDatabase ::: %s", 
					Utility.getExceptionMessageWithStackTrace(e)));
			response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE).build().toString();
		} finally {
			if(cluster != null){
				cluster.close();
			}
			if(session != null){
				session.close();
			}
		}
		return response;
	}

	/**
	 * @author arullap
	 * @param thing
	 * @return JSON
	 */
	@Override
	public String updateThing(Thing thing) {
		String response = "";
		
		try {
			// Connect to the cluster and key space "DatabaseOfThings"
			cluster = Cluster.builder().addContactPoint(Constant.DB_IPADDRESS).withCredentials(Constant.DB_USERNAME, 
					Constant.DB_PASSWORD).build();
			session = cluster.connect(Constant.DB_KEYSPACE);
			
			// Update one record in the things table
			String query = String.format("Update %s Set Name = '%s', Attributes = %s Where ThingID = %s", Constant.DB_TABLE_THINGS, 
					thing.getName(), thing.getAttributes(), thing.getUUID());
			System.out.println(String.format("Insert Query ::: %1s", query));
			
			session.execute(query);
			
			response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_TRUE).build().toString();
		}
		catch (Exception e) {
			System.out.println(String.format("Exception at updateThing method in CassandraDatabase ::: %s", 
					Utility.getExceptionMessageWithStackTrace(e)));
			response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE).build().toString();
		} finally {
			if(cluster != null){
				cluster.close();
			}
			if(session != null){
				session.close();
			}
		}
		return response;
	}
	
	/**
	 * @author arullap
	 * @param name
	 * @return JSON
	 */
	public List<String> getThings(String name){
		List<String> response = new ArrayList<String>();
		
		try {		
			// Connect to the cluster and key space "DatabaseOfThings"
			cluster = Cluster.builder().addContactPoint(Constant.DB_IPADDRESS).withCredentials(Constant.DB_USERNAME, 
					Constant.DB_PASSWORD).build();
			session = cluster.connect(Constant.DB_KEYSPACE);
			String query;
			if(name == null || name.trim() == "" || name.isEmpty()){
				query = String.format("SELECT * FROM %1s", Constant.DB_TABLE_THINGS);
			}
			else{
				query = String.format("SELECT * FROM %1s WHERE Name = '%s' ALLOW FILTERING", Constant.DB_TABLE_THINGS, name);
			}
    		System.out.println(String.format("Formed query ::: %1s", query));
    		
    		ResultSet results = session.execute(query);    		
    		for (Row row : results) {
    	        Map<String, String> attributes = row.getMap(Constant.THING_ATTRIBUTES, String.class, String.class);
    	        JsonObjectBuilder attributesJson = Json.createObjectBuilder();
    	        
    			for (Map.Entry<String, String> attribute : attributes.entrySet()) {
					attributesJson.add(attribute.getKey(), attribute.getValue());
				}
    			response.add(attributesJson
	    		            .add(Constant.THING_OBJECTID, row.getUUID(Constant.THING_OBJECTID).toString())
	    		            .add(Constant.THING_NAME, row.getString(Constant.THING_NAME))
	    		            .build()
	    		            .toString());
    			System.out.println(String.format("Json Row ::: %s | %s", row.getUUID(Constant.THING_OBJECTID), response.size()));
    		}
        } catch (Exception e) {
			System.out.println(String.format("Exception at getAllThings method in CassandraDatabase ::: %s",  
					Utility.getExceptionMessageWithStackTrace(e)));
			response.add(Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT, Constant.JSON_VALUE_FALSE).build().toString());
		} finally {
			if(cluster != null){
				cluster.close();
			}
			if(session != null){
				session.close();
			}
		}
		return response;
	}
}