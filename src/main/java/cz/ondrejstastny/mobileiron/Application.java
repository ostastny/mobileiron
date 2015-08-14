package cz.ondrejstastny.mobileiron;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
    public Application() {
        register(new ApplicationBinder());
        register(new AppExceptionMapper());
        packages(true, new String[]{"io.swagger.jaxrs.listing", "cz.ondrejstastny.mobileiron.resources"});
    }
}