package com.vlad.jettyServer;

import com.vlad.config.AppInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
public class EmbeddedJetty {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedJetty.class.getName());
    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/";
    private static final String DEFAULT_PROFILE = "dev";

    public static void main(String[] args) {
        LOGGER.info("Trying to launch Jetty server:");
        EmbeddedJetty embeddedJetty = new EmbeddedJetty();
        embeddedJetty.launchServer(args);
    }

    private void startJetty (int port) throws Exception {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(AppInitializer.class);
        webContext.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);

        ServletHolder servletHolder = new ServletHolder("Test-Dispatcher", new DispatcherServlet(webContext));
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitOrder(1);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setInitParameter("ConfigLocation", AppInitializer.class.getName());
        webAppContext.setResourceBase("webapp");


        webAppContext.addEventListener(new ContextLoaderListener(webContext));
        webAppContext.setContextPath(CONTEXT_PATH);
        webAppContext.addServlet(servletHolder, "/*");
        webAppContext.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", EnumSet.allOf(DispatcherType.class));


        LOGGER.warn("Starting Jetty server at port:" + port);
        Server server = new Server(port);
        server.setHandler(webAppContext);
        server.start();
        LOGGER.info("server successfully started");
        server.join();
    }

    public  void launchServer(String[] args1){
        if (args1.length > 0) {
            try {
                startJetty(Integer.valueOf(args1[0]));
            } catch (Exception e) {
                LOGGER.error("error 1" + e.getMessage());
            } finally {
                try {
                    startJetty(DEFAULT_PORT);
                } catch (Exception e) {
                    LOGGER.error("error 2 " + e.getMessage());
                }
            }
        }
        try {
            startJetty(DEFAULT_PORT);
        } catch (Exception e) {
            LOGGER.error("error 3" + e.getMessage());
        }
    }
}
