package CatalogRestServiceClient;


import com.advantage.common.Constants;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Created by fiskine on 6/29/2016.
 */
public class test {
    private static final Logger logger = Logger.getLogger(test.class);

    @Autowired
    private Environment environment;

    public void testt() {
        logger.info("environment.getProperty(Constants.ENV_ALLOW_USER_CONFIGURATION) = " + environment.getProperty(Constants.ENV_ALLOW_USER_CONFIGURATION));
    }
}
