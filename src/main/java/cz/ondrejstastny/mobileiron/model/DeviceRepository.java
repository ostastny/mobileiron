package cz.ondrejstastny.mobileiron.model;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cz.ondrejstastny.mobileiron.AppException;

public class DeviceRepository implements IDeviceRepository {

@Inject SessionFactory sessionFactory;
	
	@Override
	public List<Device> getAllForUser(Integer userId) {
		Session session = sessionFactory.openSession();

    	List<Device> devices = null; 
    	try {
    		devices = session.createCriteria(Device.class)
    			     	.add( Restrictions.eq("user.id", userId) )
    			     	.list();
    	}finally {
    	   session.close();
    	}
    	
        return devices;
	}

	@Override
	public Device getById(Integer id) {
		Session session = sessionFactory.openSession();

		Device device = null;
    	try {
    		device = (Device) session.get(Device.class, id);
    	}finally {
    	   session.close();
    	}
    	
        return device;
	}
	
	@Override
	public void saveOrUpdate(Device item) throws AppException {
		Session session = sessionFactory.openSession();

    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   Integer id = item.getId();
    	   if(id != null) {
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
    	   session.close();
    	}
	}

	@Override
	public void deleteById(Integer id) {
		Session session = sessionFactory.openSession();

    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   Device device = (Device) session.get(Device.class, id); 
    	   session.delete(device);

    	   tx.commit();
    	}finally {
    	   session.close();
    	}
	}

}
