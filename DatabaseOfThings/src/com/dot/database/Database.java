package com.dot.database;

import java.util.List;

import com.dot.model.Thing;

/**
 * 
 * @author arullap
 *
 */
public interface Database {
	
	/**
	 * @author arullap
	 * @param thingID
	 * @return JSON
	 */
	public String getThingByID(String thingID);
	
	/**
	 * @author arullap
	 * @param thing
	 * @return JSON
	 */
	public String addThing(Thing thing);
	
	/**
	 * @author arullap
	 * @param thing
	 * @return JSON
	 */
	public String updateThing(Thing thing);
	
	/**
	 * @author arullap
	 * @param name
	 * @return JSON
	 */
	public List<String> getThings(String name);
}