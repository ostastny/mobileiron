package cz.ondrejstastny.mobileiron.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cz.ondrejstastny.mobileiron.model.Device;
import cz.ondrejstastny.mobileiron.model.DeviceRepository;
import cz.ondrejstastny.mobileiron.model.IDeviceRepository;
import io.swagger.annotations.Api;

/**
 * Device sub-resource 
 */
@Api(tags = {"device"})
public class DevicesResource {
	@Inject IDeviceRepository deviceRepository;


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
}
