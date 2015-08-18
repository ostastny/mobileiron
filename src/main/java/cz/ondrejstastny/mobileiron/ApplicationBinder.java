package cz.ondrejstastny.mobileiron;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cz.ondrejstastny.mobileiron.model.ApplicationRepository;
import cz.ondrejstastny.mobileiron.model.DeviceRepository;
import cz.ondrejstastny.mobileiron.model.IApplicationRepository;
import cz.ondrejstastny.mobileiron.model.IDeviceRepository;
import cz.ondrejstastny.mobileiron.model.IUserRepository;
import cz.ondrejstastny.mobileiron.model.UserRepository;
import cz.ondrejstastny.mobileiron.resources.DevicesResource;
import cz.ondrejstastny.mobileiron.util.NhSessionFactory;
import cz.ondrejstastny.mobileiron.util.SessionFactoryFactory;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {    	
        bind(UserRepository.class).to(IUserRepository.class);
        bind(DeviceRepository.class).to(IDeviceRepository.class);
        bind(ApplicationRepository.class).to(IApplicationRepository.class);
        bindFactory(SessionFactoryFactory.class).to(SessionFactory.class).in(Singleton.class);
        bind(DevicesResource.class).to(DevicesResource.class);
        bindFactory(NhSessionFactory.class).to(Session.class).in(RequestScoped.class);
    }
}
