package cz.ondrejstastny.mobileiron.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import cz.ondrejstastny.mobileiron.model.User;
import cz.ondrejstastny.mobileiron.model.UserRepository;

/**
 * Users resource 
 */
@Path("users")
public class Users {
	
	@Inject UserRepository userRepository;


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
    @Path("/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Integer id) {
    	User user = userRepository.getById(id);

        return user;
    }
}
