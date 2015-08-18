package cz.ondrejstastny.mobileiron.model;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cz.ondrejstastny.mobileiron.AppException;

public class ApplicationRepository implements IApplicationRepository {

	@Inject Session session;

	@Override
	public List<Application> getAll() {
		List<Application> apps = null; 
		
		apps = session.createCriteria(Application.class).list();
		
		return apps;
	}

	@Override
	public List<Application> getAllForDevice(Integer deviceId) {
    	List<Application> apps = null; 
    	
    	apps = session.createCriteria(Application.class).createAlias("devices", "da")
    			     .add( Restrictions.eq("da.id", deviceId) )
    			     .list();
    	
        return apps;
	}

	@Override
	public Application getById(Integer applicationId) {
		Application application = null;
    	
    	application = (Application) session.get(Application.class, applicationId);
    	
        return application;
	}
	
	@Override
	public void saveOrUpdate(Application item) throws AppException {
    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   if(item.getId() != null)
    	   {
    		   Application itemCopy = (Application) session.merge(item);
        	   session.saveOrUpdate(itemCopy);
    	   }
    	   else
    	   {
    		   session.save(item);
    	   }
    	  
    	  
    	   tx.commit();
    	}catch(org.hibernate.exception.ConstraintViolationException ex) {
            Logger.getLogger(getClass()).error("DB error", ex);
    		throw new AppException(409, 0, ex.getMessage(), ex.getSQLException().getMessage(), null);	//Conflict
    	}finally {
    		if(tx.isActive())
    			tx.rollback();
    	}
	}

	@Override
	public void deleteById(Integer applicationId) {
    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   Application app = (Application) session.get(Application.class, applicationId); 
    	   session.delete(app);

    	   tx.commit();
    	}finally {
    		if(tx.isActive())
    			tx.rollback();
    	}
	}

}
