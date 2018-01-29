package com.kruczek.configuration;

import com.kruczek.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;


/**
 * Created by Patryk on 2017-09-28.
 */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    //MULTIPART
    private static final String LOCATION = "C:/temp";
    private static final long MAX_FILE_SIZE = 5242880; //bytes
    private static final int MAX_REQUEST_SIZE = 20971520; //bytes
    private static final int FILE_SIZE_THRESHOLD = 0;

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }

    private MultipartConfigElement getMultipartConfigElement()
    {
        return new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
    }
    //END MULTIPART
}
