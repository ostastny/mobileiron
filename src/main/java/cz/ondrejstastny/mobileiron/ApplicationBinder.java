package cz.ondrejstastny.mobileiron;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.SessionFactory;

import cz.ondrejstastny.mobileiron.model.DeviceRepository;
import cz.ondrejstastny.mobileiron.model.IDeviceRepository;
import cz.ondrejstastny.mobileiron.model.IUserRepository;
import cz.ondrejstastny.mobileiron.model.UserRepository;
import cz.ondrejstastny.mobileiron.resources.DevicesResource;
import cz.ondrejstastny.mobileiron.util.SessionFactoryFactory;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {    	
        bind(UserRepository.class).to(IUserRepository.class).in(Singleton.class);
        bind(DeviceRepository.class).to(IDeviceRepository.class).in(Singleton.class);
        bindFactory(SessionFactoryFactory.class).to(SessionFactory.class).in(Singleton.class);
        bind(DevicesResource.class).to(DevicesResource.class);
    }
}
