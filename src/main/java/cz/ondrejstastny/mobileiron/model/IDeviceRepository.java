package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IDeviceRepository {
	List<Device> getAllForUser(Integer userId);
	
	Device getById(Integer deviceId);
}
