package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

 /**
 * @author Binyamin Regev on 24/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "parameters"
        })
@XmlRootElement(name = "DemoAppConfigGetParametersByToolResponse")
public class DemoAppConfigGetParametersByToolResponse {
     @XmlElement(name = "parameters",namespace = WebServiceConfig.NAMESPACE_URI, required = true)
     protected List<DemoAppConfigParameter> parameters;

     public DemoAppConfigGetParametersByToolResponse() {
     }

     public DemoAppConfigGetParametersByToolResponse(List<DemoAppConfigParameter> parameters) {
         this.parameters = parameters;
     }

     public List<DemoAppConfigParameter> getParameters() {
         if (parameters == null) {
             parameters = new ArrayList<>();
         }
         return parameters;
     }

     public void setParameters(List<DemoAppConfigParameter> parameters) {
         this.parameters = parameters;
     }
}

