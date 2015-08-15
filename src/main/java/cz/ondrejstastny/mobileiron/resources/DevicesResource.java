package cz.ondrejstastny.mobileiron.resources;

import java.util.List;

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
import cz.ondrejstastny.mobileiron.model.Device;
import cz.ondrejstastny.mobileiron.model.DeviceRepository;
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
 	 * @return user with given id
 	 */
     @PUT
     @Path("/{did: \\d+}")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Device updateUser(@PathParam("uid") Integer uid, @PathParam("did") Integer did, Device device) throws AppException  {
    	//we make sure that there is a relation between the user and the device
    	User user = userRepository.getById(uid);
    	device.setUser(user);
    	deviceRepository.saveOrUpdate(device);
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
}
