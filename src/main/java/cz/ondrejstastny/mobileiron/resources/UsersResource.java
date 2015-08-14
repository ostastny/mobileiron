package cz.ondrejstastny.mobileiron.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cz.ondrejstastny.mobileiron.AppException;
import cz.ondrejstastny.mobileiron.model.IUserRepository;
import cz.ondrejstastny.mobileiron.model.User;
import cz.ondrejstastny.mobileiron.model.UserRepository;
import io.swagger.annotations.Api;

/**
 * Users resource 
 */
@Api(tags = {"user"})
@Path("users")
public class UsersResource {
	
	@Inject IUserRepository userRepository;
	@Inject DevicesResource devicesResource;


	/**
	 * Example: GET /users
	 * 
	 * @return list of all users
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	List<User> users = userRepository.getAll();
        
        return users;
    }
    
    /**
	 * Example: GET /users/1
	 * 
	 * @return user with given id
	 */
    @GET
    @Path("/{uid: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("uid") Integer uid) {
    	User user = userRepository.getById(uid);

        return user;
    }
    
    /**
	 * Example: POST /users/1
	 * 
	 * @return user with given id
	 */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User createUser(User user) throws AppException  {
    	userRepository.saveOrUpdate(user);
    	
    	//return the user back with an id
        return user;
    }
    
    /**
	 * Example: GET /users/1/devices
	 * 
	 * @return user with given id
	 */
    @Path("/{uid: \\d+}/devices")
    @PathParam("uid") 
    @Produces(MediaType.APPLICATION_JSON)
    public DevicesResource getDevices() {
        return devicesResource;
    }
}
