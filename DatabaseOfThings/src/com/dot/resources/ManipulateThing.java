package com.dot.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;  
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.dot.common.Constant;
import com.dot.model.Thing;
import com.dot.service.ThingService;
import com.dot.utilities.Utility;

//Path: http://localhost/<appln-folder-name>/thing
@Path("/thing")
public class ManipulateThing {
		
	// HTTP Post Method
    @POST
    // Path: http://localhost/<appln-folder-name>/thing/add
    @Path("/add")  
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * @author arullap
     * @param imageStream
     * @param fileDetail
     * @param attributes
     * @return
     */
    public String addThing(@FormDataParam("uploadedFile") InputStream uploadedInputStream, @FormDataParam("uploadedFile")  
    	FormDataContentDisposition fileDetail, @FormDataParam("jsonAttributes") String attributes){
    	System.out.println("Execution started for addThing..");
    	// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null || attributes == null)
		{
			String response = Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE)
					.add(Constant.JSON_MESSAGE, "Invaild form data").build().toString(); 
			
			System.out.println("Invaild form-data..");
			return response;
		}
		try{
			System.out.println("File size :: " + uploadedInputStream.available() + "File name:: " + fileDetail.getFileName() + 
					"File size :: " + fileDetail.getSize() + " Attributes :: " + attributes);
		} catch(IOException e){
			
		}
		
        String response = "";
        Thing thing = new Thing();
        
        ///TODO - worst performing Json API --> use org.json or google json api
        JsonObject json = Json.createReader(new StringReader(attributes)).readObject();
        
		thing.setName(json.getString("name"));
		if(json.containsKey("attributes")){
			thing.setAttributes(json.getString("attributes"));//"{'PackedBy' : 'Vasan Pvt Ltd', 'Quantity' : '1KG'}"
		}
		
		ThingService service = new ThingService();
		response = service.addThing(thing);
        
		// create our destination folder, if it not exists
		try {
			String[] filePaths = fileDetail.getFileName().split("/");
			
			JsonObject resJson = Json.createReader(new StringReader(response)).readObject();
			/*String serverFolderPath = Constant.CLOUD_IMAGEPATH + "\\" + resJson.getString(Constant.JSON_MESSAGE);
			String serverFilePath = serverFolderPath + "\\" + filePaths[filePaths.length -1];*/
			
			String serverFolderPath = String.format("%s%s%s", System.getProperty("catalina.base"), 
					Constant.CLOUD_IMAGEPATH, resJson.getString(Constant.JSON_MESSAGE));
			String serverFilePath = serverFolderPath + "/" + filePaths[filePaths.length -1];
			
			createImageFolder(serverFolderPath);
			 
			System.out.println(String.format("Server filepath of addThing method in ManipulateThing ::: %1s", 
					serverFilePath));
			
			writeToServerDisk(uploadedInputStream, serverFilePath);
		} catch (SecurityException se) {
			System.out.println(String.format("Exception while writing file into server ::: %1s", 
					Utility.getExceptionMessageWithStackTrace(se)));
			response =  Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE)
					.add(Constant.JSON_MESSAGE, "Can't create a folder in server").build().toString();
		} catch(Exception e){
			System.out.println(String.format("Response of addThing method in ManipulateThing ::: %1s", 
					Utility.getExceptionMessageWithStackTrace(e)));
			response =  Json.createObjectBuilder().add(Constant.JSON_KEY_RESULT , Constant.JSON_VALUE_FALSE)
					.add(Constant.JSON_MESSAGE, "Can't create a folder in server").build().toString();
		}
        System.out.println(String.format("Response of AddThing Json ::: %1s", response));
        return response;
    }
    
    /**
     * @author arullap
     * @param serverFolderPath
     * @throws SecurityException
     */
    private void createImageFolder(String serverFolderPath)
			throws SecurityException {
		File theDir = new File(serverFolderPath);
		if (!theDir.exists()) {
			theDir.mkdir();
			System.out.println("Server image folder created.." + serverFolderPath);
		}
	}
    
    /**
     * 
     * @param uploadedInputStream
     * @param serverFileLocation
     */
    private void writeToServerDisk(InputStream uploadedInputStream,
            String serverFileLocation) {
		OutputStream out;
		
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            System.out.println("File bytes : " + bytes.length);
            out = new FileOutputStream(new File(serverFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            System.out.println("File has been written on d server : " + bytes.length);
        } catch (IOException e) {
        	System.out.println(String.format("Exception at writeToServerDisk in ManipulateThing ::: %1s", 
        			Utility.getExceptionMessageWithStackTrace(e)));
        }
    }
    
    // HTTP PUT Method
    @PUT
    // Path: http://localhost/<appln-folder-name>/thing/update
    @Path("/update")  
    @Consumes("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * @author arullap
     * @return
     */
    public String updateThing(){
        String response = "";
        
		Thing thing = new Thing();
		thing.setName("Dhal");
		thing.setAttributes("{'PackedBy' : 'Sugarcane limited', 'Quantity' : '10KG'}");
		thing.setUUID("fa634ab2-a7ce-4041-908a-11b8ff263e10");
		
		ThingService service = new ThingService();
		response = service.updateThing(thing);
        
        System.out.println(String.format("Response of AddThing Json ::: %1s", response));
        return response;
    }
}