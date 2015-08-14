package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

import cz.ondrejstastny.mobileiron.AppException;


@Contract
public interface IUserRepository {
	List<User> getAll();
	
	User getById(Integer id);
	
	void saveOrUpdate(User item)  throws AppException;
	
	void deleteById(Integer id);
}
