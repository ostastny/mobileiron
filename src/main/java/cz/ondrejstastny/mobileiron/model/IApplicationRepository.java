package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

import cz.ondrejstastny.mobileiron.AppException;

@Contract
public interface IApplicationRepository {
	List<Application> getAll();
	
	List<Application> getAllForDevice(Integer deviceId);
	
	Application getById(Integer applicationId);
	
	void saveOrUpdate(Application item) throws AppException;
	
	void deleteById(Integer applicationId);
}
