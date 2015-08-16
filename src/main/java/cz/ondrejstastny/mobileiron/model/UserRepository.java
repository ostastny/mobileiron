package cz.ondrejstastny.mobileiron.model;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jvnet.hk2.annotations.Service;

import cz.ondrejstastny.mobileiron.AppException;

@Service
public class UserRepository implements IUserRepository{

	@Inject Session session;
	
	@Override
	public List<User> getAll() {
    	List<User> users = null; 

    	users = session.createCriteria(User.class).list();
    	
        return users;
	}

	@Override
	public User getById(Integer id) {
		
    	User user = null;

    	user = (User) session.get(User.class, id);
    	
        return user;
	}

	@Override
	public void saveOrUpdate(User item) throws AppException {
    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   if(item.getId() != null)
    	   {
    		   User itemCopy = (User) session.merge(item);
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

    	   User user = (User) session.get(User.class, id); 
    	   session.delete(user);

    	   tx.commit();
    	}finally {
    		if(tx.isActive())
    			tx.rollback();
    	}
	}

}
