package CatalogRestServiceClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by fiskine on 7/3/2016.
 */
public class CatalogClient {
    private static final Logger logger = Logger.getLogger(CatalogClient.class);

    @Autowired
    private Environment environment;

}
