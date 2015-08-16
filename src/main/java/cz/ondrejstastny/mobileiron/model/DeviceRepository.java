package cz.ondrejstastny.mobileiron.model;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cz.ondrejstastny.mobileiron.AppException;

public class DeviceRepository implements IDeviceRepository {

	@Inject Session session;
	
	@Override
	public List<Device> getAllForUser(Integer userId) {
    	List<Device> devices = null; 
    	devices = session.createCriteria(Device.class)
    			     	.add( Restrictions.eq("user.id", userId) )
    			     	.list();
    	
        return devices;
	}

	@Override
	public Device getById(Integer id) {
		Device device = null;
    	
		device = (Device) session.get(Device.class, id);
    	
        return device;
	}
	
	@Override
	public void saveOrUpdate(Device item) throws AppException {
    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   if(item.getId() != null)
    	   {
    		   Device itemCopy = (Device) session.merge(item);
        	   session.saveOrUpdate(itemCopy);
    	   }
    	   else
    	   {
    		   session.save(item);
    	   }
    	  
    	   tx.commit();
    	}catch(org.hibernate.exception.ConstraintViolationException ex) {
    		throw new AppException(409, 0, ex.getMessage(), ex.getSQLException().getMessage(), null);	//Conflict
    	}finally {
    		if(tx.isActive())
    			tx.rollback();
    	}
	}

	@Override
	public void deleteById(Integer id) {
    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   Device device = (Device) session.get(Device.class, id); 
    	   session.delete(device);

    	   tx.commit();
    	}finally {
    		if(tx.isActive())
    			tx.rollback();
    	}
	}

}
