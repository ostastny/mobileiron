package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

import cz.ondrejstastny.mobileiron.AppException;

@Contract
public interface IDeviceRepository {
	List<Device> getAllForUser(Integer userId);
	
	Device getById(Integer deviceId);
	
	void saveOrUpdate(Device item) throws AppException;
	
	void deleteById(Integer id);
}
