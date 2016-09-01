package com.advantage.accountsoap.dto.account;
//Modify this class with resources/accountservice.xsd

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userName",
    "password"
})

@XmlRootElement(name = "EncodePasswordRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class EncodePasswordRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String password;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String userName;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "EncodePasswordRequest{" +
                ", password='" + password + '\'' +
                '}';
    }
}
