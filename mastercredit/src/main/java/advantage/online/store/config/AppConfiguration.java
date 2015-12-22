package advantage.online.store.config;

import advantage.online.store.Constants;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by kubany on 10/11/2015.
 */

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(
        basePackageClasses = {Constants.class},
        excludeFilters = {
                @Filter(
                        type = FilterType.ANNOTATION,
                        value = {
                                RestController.class,
                                ControllerAdvice.class,
                                Configuration.class
                        }
                )
        }
)
@PropertySources(value = {
        @PropertySource("classpath:/app.properties"),
        @PropertySource("classpath:/database.properties"),
        @PropertySource("classpath:/DemoApp.properties")})
public class AppConfiguration {

}