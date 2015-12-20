package ShippingExpresss.config;

import ShippingExpresss.ShipExApplication;
import javafx.application.Application;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class WebInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShipExApplication.class);
    }
}
