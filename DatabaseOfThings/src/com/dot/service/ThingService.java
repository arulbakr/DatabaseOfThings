package com.dot.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.dot.common.Constant;
import com.dot.database.CassandraDatabase;
import com.dot.database.Database;
import com.dot.model.Thing;
import com.dot.utilities.Utility;

public class ThingService {
	
	/**
     * @author arullap
     * @param id
     * @return
     */
    public String getThingByID(String id) {
        String response = "";
        
        try
        {
        	Database db = new CassandraDatabase();
        	response = db.getThingByID(id);

        	//Embedding image URL into response
        	JSONObject jObject = new JSONObject(response);
        	jObject.append("ImageLink", Constant.SERVER_URL + Constant.REST_URL_IMAGE + id);
        	response = jObject.toString();
        }
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at getThingByID method in ThingService ::: 1%s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	throw e;
        }
        System.out.println(String.format("Json ::: ",  response));
        return response;
    }
        
    /**
     * @author arullap
     * @return
     */
    public String addThing(Thing thing){
        String response = "";
        
        try
        {	
    		Database db = new CassandraDatabase();
        	response = db.addThing(thing);
        }
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at AddThing method in ManipulateThing ::: %1s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	throw e;
        }
        
        System.out.println(String.format("Response of AddThing Json ::: %1s", response));
        return response;
    }
    
    /**
     * @author arullap
     * @return
     */
    public String updateThing(Thing thing){
        String response = "";
        
        try
        {	
    		Database db = new CassandraDatabase();
        	response = db.updateThing(thing);
        }
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at AddThing method in ManipulateThing ::: %1s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	throw e;
        }
        
        System.out.println(String.format("Response of AddThing Json ::: %1s", response));
        return response;
    }
    
    /**
     * @author arullap
     * @param name
     * @return
     */
    public List<String> getThings(String name) {
        List<String> response = new ArrayList<String>();
        
        try
        {
        	Database db = new CassandraDatabase();
        	response = db.getThings(name);
        }
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at getThingByID method in ThingService ::: 1%s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	throw e;
        }
        System.out.println(String.format("Json ::: %s",  response));
        return response;
    }
    
    /**
     * @author arullap
     * @param id
     * @return
     * @throws Exception 
     */
    public Response getThingImageByID(String id) throws Exception {
    	Response response = null;
    	try
        {
        	//Embedding image stream into response
	    	String serverFolderPath = String.format("%s%s%s", System.getProperty("catalina.base"), Constant.CLOUD_IMAGEPATH, id, "/");
	    	//String serverFolderPath = "D:\\Java_Projects\\DatabaseOfThings\\Uploads\\" + id + "\\";
	    	System.out.println("ServerPath : " + serverFolderPath);
	    	File folder = new File(serverFolderPath);
	    	File actualImage = null;
	    	if(folder.exists()){
	    		System.out.println("File exists : " + serverFolderPath);
	    		File[] files = folder.listFiles();
	    		actualImage = files[0];
	    		BufferedImage image = ImageIO.read(files[0]);
	    		
	    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	    ImageIO.write(image, "png", baos);
	    	    byte[] imageData = baos.toByteArray();
	    	    
	    		System.out.println("Actual file : " + actualImage.getAbsolutePath());
	    		//response = Response.ok(new FileInputStream(actualImage)).build();
	    		response = Response.ok(new ByteArrayInputStream(imageData)).build();
	    	}else{
	    		System.out.println("File not exists : " + serverFolderPath);
	    	}
        }
    	catch(FileNotFoundException fe){
    		System.out.println(String.format("Exception at getThingImageByID method in ThingService ::: 1%s", 
        			Utility.getExceptionMessageWithStackTrace(fe)));
    	}
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at getThingImageByID method in ThingService ::: 1%s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
			throw e;
        }
        return response;
    }
}