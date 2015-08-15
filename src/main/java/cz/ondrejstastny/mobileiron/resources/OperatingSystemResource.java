package cz.ondrejstastny.mobileiron.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;

/**
 * Device sub-resource 
 */
@Api(tags = {"os"})
@Path("/os")
public class OperatingSystemResource {
	private static final String[] systems = new String[]{"iOS 7", "iOS 8", "Android 4.5", "Android 4.6", "WP 8.1", "WP 10.0"};
	
	/**
	 * Example: GET /os
	 * 
	 * @return list all possible operating systems
	 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String[] getSystems() {
    	//OSes are hardcoded for simplicity sake
        return systems;
    }
    
}
