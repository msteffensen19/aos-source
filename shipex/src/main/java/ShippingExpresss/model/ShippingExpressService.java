package ShippingExpresss.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ShippingExpressService {
    public static final int TRACK_NUMBER_LENGTH = 10;

    private ShippingExpressRepository shipexRepository ;

    @Autowired
    public ShippingExpressService(DefaultShippingExpressRepository shipexRepository) {
        this.shipexRepository = shipexRepository;
    }

    /**
     * Determines shipping cost according to country and orders quantity
     * @param countryIsoName {@link String} ISO name of country
     * @param quantity {@link Integer} products quantity in the order
     * @return {@link Double} shipping cost
     */
    public double getShippingCost(String countryIsoName, int quantity){
        if(quantity <= shipexRepository.getFreeShippingOption()) return 0;

        return shipexRepository.getShippingCostByCountry(countryIsoName);
    }

    /**
     * Generate track number
     * @return {@link String} 10 digits random number
     */
    public String getTrackNumber() {
        return getRandomNumber(TRACK_NUMBER_LENGTH);
    }

    private String getRandomNumber(int digitCount) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(digitCount);
        for(int i=0; i < digitCount; i++)
            sb.append((char)('0' + rnd.nextInt(10)));

        return sb.toString();
    }
}
