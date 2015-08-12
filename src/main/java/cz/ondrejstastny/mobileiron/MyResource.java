package cz.ondrejstastny.mobileiron;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cz.ondrejstastny.mobileiron.model.Device;
import cz.ondrejstastny.mobileiron.model.User;
import cz.ondrejstastny.mobileiron.util.HibernateUtil;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    //@Produces(MediaType.TEXT_PLAIN)
    //@GET
    //@Path("/project_id/username/get/{projectId}/{username}/")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();

    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   User user = new User();
    	   user.setName("Ondrej Stastny");
    	   user.setEmail("mail@ondrejstastny.cz");
    	
	    	Device device = new Device();
	    	device.setOperatingSystem("Windows Phone 8.1");
	    	device.setPhoneNumber("+420724040614");
	    	
	    	Set<Device> devices = new HashSet<Device>();
	    	devices.add(device);
	    	
	    	user.setDevices(devices);
	
	    	session.save(user);

    	   tx.commit();
    	}
    	catch (Exception e) {
    	   if (tx!=null) tx.rollback();
    	   e.printStackTrace(); 
    	}finally {
    	   session.close();
    	}
    	
        return "Hello, Heroku!";
    }
}
