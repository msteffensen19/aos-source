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
                "parameter"
        })
@XmlRootElement(name = "GetDemoAppConfigParametersByToolResponse")
public class GetDemoAppConfigParametersByToolResponse {
     @XmlElement(name = "parameter",namespace = WebServiceConfig.NAMESPACE_URI, required = true)
     protected List<DemoAppConfigParameter> parameter;

     public List<DemoAppConfigParameter> getParameter() {
         if (parameter == null) {
             parameter = new ArrayList<>();
         }
         return parameter;
     }

     public void setParameter(List<DemoAppConfigParameter> parameter) {
         this.parameter = parameter;
     }
}

