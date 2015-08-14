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

	@Inject SessionFactory sessionFactory;
	
	@Override
	public List<User> getAll() {
		Session session = sessionFactory.openSession();

    	List<User> users = null; 
    	try {
    		users = session.createCriteria(User.class).list();
    	}finally {
    	   session.close();
    	}
    	
        return users;
	}

	@Override
	public User getById(Integer id) {
		Session session = sessionFactory.openSession();

    	User user = null;
    	try {
    	  user = (User) session.get(User.class, id);
    	}finally {
    	   session.close();
    	}
    	
        return user;
	}

	@Override
	public void saveOrUpdate(User item) throws AppException {
		Session session = sessionFactory.openSession();

    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   Integer id = item.getId();
    	   if(id != null) {
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
    	   session.close();
    	}
	}

	@Override
	public void deleteById(Integer id) {
		Session session = sessionFactory.openSession();

    	Transaction tx = null;
    	try {
    	   tx = session.beginTransaction();

    	   User user = (User) session.get(User.class, id); 
    	   session.delete(user);

    	   tx.commit();
    	}finally {
    	   session.close();
    	}
	}

}
