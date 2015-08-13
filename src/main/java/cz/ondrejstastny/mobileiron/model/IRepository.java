package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IRepository<T> {
	List<T> getAll();
	
	T getById(Integer id);
	
	void saveOrUpdate(T item);
	
	void deleteById(Integer id);
}