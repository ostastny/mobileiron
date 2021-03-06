package cz.ondrejstastny.mobileiron.util;

import org.apache.log4j.Logger;
import org.glassfish.hk2.api.Factory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.inject.Inject;

public class NhSessionFactory implements Factory<Session> {

	@Inject SessionFactory sessionFactory;
	

    public Session provide() {
        final Session session;
        try {
            session = sessionFactory.openSession();
            System.out.println("Session open");
        } catch (Exception ex) {
            throw NhSessionFactory.<RuntimeException>unchecked(ex);
        }
        return session;
    }

    public void dispose(Session session) {
        closeQuietly(session);
    }
    
    private static <T extends Throwable> T unchecked(Throwable t) throws T {
        throw (T) t;
    }

    private static void closeQuietly(Session session) {
        try {
        	Transaction tx = session.getTransaction();
        	if(tx.isActive())
    			tx.rollback();
        	session.close();
            System.out.println("Session close");
        } catch (Exception ignore) {
    		Logger.getLogger(NhSessionFactory.class).error("NhSessionFactory error", ignore);
    	}
    }
}