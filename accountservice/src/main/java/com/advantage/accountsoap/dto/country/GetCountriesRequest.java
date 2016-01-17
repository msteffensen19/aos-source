package com.advantage.accountsoap.dto.country;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "GetCountriesRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetCountriesRequest {
}
