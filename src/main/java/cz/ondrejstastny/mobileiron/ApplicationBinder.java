package cz.ondrejstastny.mobileiron;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.SessionFactory;

import cz.ondrejstastny.mobileiron.model.UserRepository;
import cz.ondrejstastny.mobileiron.util.SessionFactoryFactory;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserRepository.class).to(UserRepository.class).in(Singleton.class);
        bindFactory(SessionFactoryFactory.class).to(SessionFactory.class).in(Singleton.class);
    }
}
