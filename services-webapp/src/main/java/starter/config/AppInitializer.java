package starter.config;

import jms.config.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import starter.config.database.DatabaseConfig;
import starter.config.database.JdbcConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@Import({ActiveMQConfig.class,
        TemplateConfig.class,
        DatabaseConfig.class,
        JdbcConfig.class,
        ListenerConfig.class,
        Topics.class})
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class AppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(AppInitializer.class);
        springContext.scan("starter.app", "starter.config");

        servletContext.addListener(new ContextLoaderListener(springContext));
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
