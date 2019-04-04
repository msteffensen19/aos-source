package ShippingExpress;

import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackageClasses = {Url_resources.class})
@Configuration
@ComponentScan
@PropertySources({@PropertySource(Constants.FILE_PROPERTIES_EXTERNAL)})
public class ShipexApplication {

    public static void main(String[] args) {
        AttachmentPartImpl a;
        SpringApplication.run(ShipexApplication.class, args);
    }
}
