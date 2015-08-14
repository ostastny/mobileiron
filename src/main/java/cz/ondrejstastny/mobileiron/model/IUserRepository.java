package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;


@Contract
public interface IUserRepository {
	List<User> getAll();
	
	User getById(Integer id);
	
	void saveOrUpdate(User item);
	
	void deleteById(Integer id);
}
