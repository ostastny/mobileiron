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
@Api(tags = {"application"})
@Path("")	//required to trick Swagger to produce correct metadata
public class ApplicationsResource {
	@Inject IApplicationRepository applicationRepository;


	/**
	 * Example: GET /applications
	 * 
	 * @return list of all applications in the system
	 */
    @GET
    @Path("applications")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Application> getAllApps() {
    	List<Application> apps = applicationRepository.getAll();
        
        return apps;
    }
    
    /**
	 * Example: GET /applications/1
	 * 
	 * @return list application by id
	 */
    @GET
    @Path("applications/{aid: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Application getById(@PathParam("aid") Integer aid) {
    	Application app = applicationRepository.getById(aid);
        
        return app;
    }
    
    /**
     * Create new application
     * 
   	 * Example: POST /applications
   	 * 
   	 * @return device with new id (on create)
   	 */
     @POST
     @Path("applications")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Application createApp(Application app) throws AppException  {
    	 applicationRepository.saveOrUpdate(app);
       	
       	//return app back with an id
        return app;
     }
     
     /**
     * Update application data
     * 
  	 * Example: PUT /applications/1
  	 * 
  	 * @return user with given id
  	 */
      @PUT
      @Path("applications/{aid: \\d+}")
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      public Application updateUser(@PathParam("aid") Integer aid, Application app) throws AppException  {
    	  app.setId(aid);
     	
     	  applicationRepository.saveOrUpdate(app);
      	
      	  //return the user back with an id
          return app;
      }
     
     
     /**
  	 * Example: DELETE /users/1/devices/1
  	 * 
  	 */
     @DELETE
     @Path("applications/{aid: \\d+}")
     @Produces(MediaType.APPLICATION_JSON)
     public Response deleteApp(@PathParam("aid") Integer aid) throws AppException  {
    	 applicationRepository.deleteById(aid);
     	
     	return Response.ok().build();	//but really should be 204 based on RFC 7231
     }
     
     
    
}