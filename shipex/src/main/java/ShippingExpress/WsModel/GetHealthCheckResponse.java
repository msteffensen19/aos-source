package ShippingExpress.WsModel;

import javax.xml.bind.annotation.*;

/**
 * Created by ederymi on 9/18/2017.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "status"
})
@XmlRootElement(name = "GetHealthCheckResponse", namespace = "https://www.AdvantageOnlineShopping.com/ShipEx/")
public class GetHealthCheckResponse {
        @XmlElement(namespace = "https://www.AdvantageOnlineShopping.com/ShipEx/")
        protected String status;

        /**
         * Gets the value of the status property.
         *
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the track property.
         *
         */
        public void setStatus(String status) {
            this.status = status;
        }

}

