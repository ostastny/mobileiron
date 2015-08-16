package cz.ondrejstastny.mobileiron.resources;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.ondrejstastny.mobileiron.AppException;
import cz.ondrejstastny.mobileiron.model.Application;
import cz.ondrejstastny.mobileiron.model.Device;
import cz.ondrejstastny.mobileiron.model.DeviceRepository;
import cz.ondrejstastny.mobileiron.model.IApplicationRepository;
import cz.ondrejstastny.mobileiron.model.IDeviceRepository;
import cz.ondrejstastny.mobileiron.model.IUserRepository;
import cz.ondrejstastny.mobileiron.model.User;
import io.swagger.annotations.Api;

/**
 * Device sub-resource 
 */
@Api(tags = {"device"})
public class DevicesResource {
	@Inject IDeviceRepository deviceRepository;
	@Inject IUserRepository userRepository;
	@Inject IApplicationRepository applicationRepository;


	/**
	 * Example: GET /users/1/devices
	 * 
	 * @return list of all devices
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@PathParam("uid") Integer uid) {
    	List<Device> devices = deviceRepository.getAllForUser(uid);
        
        return devices;
    }
    
    /**
	 * Example: GET /devices/1
	 * 
	 * @return device with given id
	 */
    @GET
    @Path("/{did: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("uid") Integer uid, @PathParam("did") Integer did) {
    	Device device = deviceRepository.getById(did);

        return device;
    }
    
    /**
   	 * Example: POST /users/1/devices
   	 * 
   	 * @return device with new id (on create)
   	 */
     @POST
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Device createUser(@PathParam("uid") Integer uid, Device device) throws AppException  {
    	 //we make sure that there is a relation between the user and the device
    	 User user = userRepository.getById(uid);
    	 device.setUser(user);
    	 deviceRepository.saveOrUpdate(device);
       	
       	//return device back with an id
        return device;
     }
     
     /**
 	 * Example: PUT /users/1/devices/1
 	 * 
 	 * @return device with given id
 	 */
     @PUT
     @Path("/{did: \\d+}")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Device updateUser(@PathParam("uid") Integer uid, @PathParam("did") Integer did, Device device) throws AppException  {
    	//we make sure that there is a relation between the user and the device
    	User user = userRepository.getById(uid);
    	device.setUser(user);
    	device.setId(did);
    	
     	deviceRepository.saveOrUpdate(device);
     	
     	//return the user back with an id
         return device;
     }
     
     /**
  	 * Example: DELETE /users/1/devices/1
  	 * 
  	 */
     @DELETE
     @Path("/{did: \\d+}")
     @Produces(MediaType.APPLICATION_JSON)
     public Response deleteUser(@PathParam("did") Integer did) throws AppException  {
     	deviceRepository.deleteById(did);
     	
     	return Response.ok().build();	//but really should be 204 based on RFC 7231
     }
     
     /**
      * List all applications for on the device
      * 
      * Example: GET /users/1/devices/1/applications
      */
      @GET
      @Path("{did: \\d+}/applications")
      @Produces(MediaType.APPLICATION_JSON)
      public List<Application> getApplications(@PathParam("uid") Integer uid, @PathParam("did") Integer did) throws AppException  {
    	  //add user id validation
    	  
    	  List<Application> apps = applicationRepository.getAllForDevice(did);
    	  
    	  return apps;
      }
     
     /**
      * Create new association between app and device
      * 
      * Example: POST /users/1/devices/1/applications
      * 
      * @return device with new id (on create)
      */
      @POST
      @Path("{did: \\d+}/applications")
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      public Response createAppAssociation(@PathParam("uid") Integer uid, @PathParam("did") Integer did, Application app) throws AppException  {
    	  //we only accept id from App object
    	  if(app.getId() == null)
    		  throw new AppException(400, 0, "Missing parameter", "Application id is missing", null);	//Bad request
    	  app = applicationRepository.getById(app.getId());
    	  
    	  //getting user is redundant
    	  User user = userRepository.getById(uid);
     	  if(user == null)
     		  throw new AppException(404, 0, "Resource not found", "User id {" + uid+"} does not exist", null);	//Bad request
   	  
    	  //we make sure that there is a relation between the user and the device
     	  Device device = deviceRepository.getById(did);
     	  if(device == null)
     		  throw new AppException(404, 0, "Resource not found", "Device id {" + did+"} does not exist", null);	//Bad request
   	  
     	  device.getApplications().add(app);
    	  
     	  deviceRepository.saveOrUpdate(device);

      	  return Response.ok().build();	//but really should be 204 based on RFC 7231
      }
      
      /**
    	 * Example: DELETE /users/1/devices/1
    	 * 
    	 */
       @DELETE
       @Path("{did: \\d+}/applications/{aid: \\d+}")
       @Produces(MediaType.APPLICATION_JSON)
       public Response deleteAppAssociation(@PathParam("uid") Integer uid, @PathParam("did") Integer did, @PathParam("aid") Integer aid) throws AppException  {
      	  Device device = deviceRepository.getById(did);
      	  if(device == null)
      		  throw new AppException(404, 0, "Resource not found", "Device id {" + did+"} does not exist", null);	//Bad request
      	
    	  //getting user is redundant
      	  User user = userRepository.getById(uid);
    	  if(user == null)
    		  throw new AppException(404, 0, "Resource not found", "User id {" + uid+"} does not exist", null);	//Bad request
  	  
      	  Application app = applicationRepository.getById(aid);
      	  if(app == null)
    		  throw new AppException(404, 0, "Resource not found", "Application id {" + aid+ "} does not exist", null);	//Bad request

     	  device.getApplications().remove(app);

     	  deviceRepository.saveOrUpdate(device);
       	
       	  return Response.ok().build();	//but really should be 204 based on RFC 7231
       }
}
