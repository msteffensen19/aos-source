
import React from 'react'

import MyContext from './ConsoleContext';
import Parser from 'properties-parser';

export default class ContextProvider extends React.Component {

    state = {
        portsForRouting:{
            accountService:80,
            accountServiceHost:'',
            catalog:80,
            order:80,
            isSingleMachineDeployment:false,
            isReverseProxy:false,
            accountServiceUrl : '',
            catalogServiceUrl : ''
        }
    };

    componentWillMount(){
        this.getServicesProperties();
    }
    getServicesProperties = ()=>{

        let host = window.location.origin;

        fetch(host+'/services.properties')
            .then(response=>response.text())
            .then((data)=> {
            let servicesProperties = Parser.parse(data);
            let catalogPort ='';
            let catalogHost ='';
            let accountServicePort='';
            let accountServiceHost='';
            let orderPort='';
            let isReversedProxy = (servicesProperties["reverse.proxy"] === "true");
            if(isReversedProxy){
                catalogPort ='';
                accountServicePort='';
                orderPort='';
                accountServiceHost='';
                catalogHost = '';
            }else{
                catalogPort = servicesProperties["catalog.service.url.port"];
                catalogHost = servicesProperties["catalog.service.url.host"];
                accountServicePort = servicesProperties["account.soapservice.url.port"];
                accountServiceHost = servicesProperties["account.soapservice.url.host"];
                orderPort = servicesProperties["order.service.url.port"];
            }

                let urlStringForCatalog = "";
                let accountServiceUrl = "";
                let protocol = window.location.protocol;
                let host = window.location.hostname;
                if (host.includes("localhost")){
                    urlStringForCatalog = protocol + "//" + host +  (catalogPort.length > 0 ? ":" + catalogPort : "") + '/catalog/api/v1';
                    accountServiceUrl = protocol + "//" + host +  (accountServicePort.length > 0 ? ":" + accountServicePort : "") + '/accountservice/';
                }else if(isReversedProxy){
                    urlStringForCatalog = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/catalog/api/v1';
                    accountServiceUrl = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/accountservice/';
                }else{
                    urlStringForCatalog = protocol + "//" + catalogHost + ':' + catalogPort + '/catalog/api/v1';
                    accountServiceUrl = protocol + "//" + accountServiceHost + ':' + accountServicePort + '/accountservice/';
                }
            this.setState(prevState => {
                let portsForRouting = { ...prevState.portsForRouting };  // creating copy of state variable portsForRouting
                portsForRouting.accountService = accountServicePort;
                portsForRouting.accountServiceHost = accountServiceHost;
                portsForRouting.catalog = catalogPort;
                portsForRouting.order = orderPort;
                portsForRouting.isSingleMachineDeployment = servicesProperties["single.machine.deployment"];
                portsForRouting.isReverseProxy = isReversedProxy;// update the port property, assign a new value
                portsForRouting.catalogServiceUrl = urlStringForCatalog;
                portsForRouting.accountServiceUrl = accountServiceUrl;
                return { portsForRouting };                                 // return new object portsForRouting object
            })

        }).catch(console.log);
    };

    render() {
        return (

            <MyContext.Provider
                value={this.state.portsForRouting}>
                {this.props.children}
            </MyContext.Provider>
        );
    }
}