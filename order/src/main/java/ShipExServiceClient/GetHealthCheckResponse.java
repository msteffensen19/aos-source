package ShipExServiceClient;

import javax.xml.bind.annotation.*;

/**
 * Created by ederymi on 9/18/2017.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "status"
})
@XmlRootElement(name = "GetHealthCheckResponse")
public class GetHealthCheckResponse {

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

