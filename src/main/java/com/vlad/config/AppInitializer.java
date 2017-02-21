package com.vlad.config;

import com.vlad.security.jwt.filter.AuthenticationTokenFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by Fedyunkin_Vladislav on 2/7/2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.vlad"})
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, SwaggerConfig.class, WebConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    protected String[] getServletMappings() {
        return new String[]{ "/*" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new AuthenticationTokenFilter()};
    }
}
