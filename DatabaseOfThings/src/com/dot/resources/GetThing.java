package com.dot.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dot.service.ThingService;
import com.dot.utilities.Utility;

//Path: http://localhost/<appln-folder-name>/get
@Path("/get")
public class GetThing {
	
    // HTTP Get Method
    /**
     * @author arullap
     * @param id
     * @return
     */
	@GET
    // Path: http://localhost/<appln-folder-name>/get/thingByID
    @Path("/thingbyid")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    // Query parameters are parameters: http://localhost/<appln-folder-name>/get/thingbyid?id=1
    public String getThingByID(@QueryParam("id") String id) {
        String response = "";
        
        ThingService service = new ThingService();
        response = service.getThingByID(id);
        
        System.out.println(String.format("Json ::: 1%s",  response));
        return response;
    }
    
    /**
     * @author arullap
     * @param name
     * @return
     */
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/get/thingsbyname
    @Path("/thingsbyname")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/get/thingsbyname?name=
    public String getThings(@QueryParam("name") String name){
        List<String> records = new ArrayList<String>();
        
        ThingService service = new ThingService();
        records = service.getThings(name);
        
        System.out.println(String.format("Final response for getThings :: %s", records));
        return records.toString();
    }
    
    /**
     * @author arullap
     * @param id
     * @return
     * @throws Exception 
     */
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/get/thingimage
    @Path("/thingimage")  
    @Produces({"image/png", "image/jpg"})
    // Query parameters are parameters: http://localhost/<appln-folder-name>/get/thingimage?id=
    public Response getThingImageByID(@QueryParam("id") String id) throws Exception {
    	Response response = null;
        try
        {
        	ThingService service = new ThingService();
        	response = service.getThingImageByID(id);
        }
        catch(Exception e)
        {
        	System.out.println(String.format("Exception at getThingImageByID method in GetThing ::: 1%s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        	throw e;
        }
        System.out.println(String.format("Json ::: ",  response));
        return response;
    }
}