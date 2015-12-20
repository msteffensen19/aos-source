package ShippingExpresss.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;


@Qualifier("shipexRepository")
@Repository
public class DefaultShippingExpressRepository implements ShippingExpressRepository {
    public static final String SHIPEX_DEFAULT_COST = "shipex.country.other";
    public static final String SHIPEX_FREE_SHIPPING_OPTION = "shipex.free";
    private Environment env;

    @Autowired
    public DefaultShippingExpressRepository(Environment env) {
        this.env = env;
    }

    @Override
    public double getShippingCostByCountry(String countryIsoName) {
        String cost = env.getProperty(countryIsoName);
        if(cost.isEmpty()) return getDefaultShippingCost();

        return Double.parseDouble(cost);
    }

    @Override
    public double getDefaultShippingCost() {
        return Double.parseDouble(env.getProperty(SHIPEX_DEFAULT_COST));
    }

    @Override
    public int getFreeShippingOption() {
        return Integer.parseInt(env.getProperty(SHIPEX_FREE_SHIPPING_OPTION));
    }
}
