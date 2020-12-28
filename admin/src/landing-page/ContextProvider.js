
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
            catalogServiceUrl : '',
            orderServiceUrl : ''
        }
    };

    componentWillMount(){
        this.getServicesProperties();
    }
    parseServicesAsJSON(data){
        let servicesProperties = JSON.parse(JSON.parse(data));
        let catalogPort ='';
        let catalogHost ='';
        let accountServicePort='';
        let accountServiceHost='';
        let orderPort='';
        let orderHost='';
        let gatewayOn = (servicesProperties["aos.gateway"].value);
        let isReversedProxy = (servicesProperties["reverse.proxy"].value);
        if(isReversedProxy){
            catalogPort ='';
            accountServicePort='';
            orderPort='';
            accountServiceHost='';
            catalogHost = '';
            orderHost = '';
        }else{
            catalogPort = servicesProperties["catalog.service.url.port"].value;
            catalogHost = servicesProperties["catalog.service.url.host"].value;
            accountServicePort = servicesProperties["account.soapservice.url.port"].value;
            accountServiceHost = servicesProperties["account.soapservice.url.host"].value;
            orderPort = servicesProperties["order.service.url.port"].value;
            orderHost = servicesProperties["order.service.url.host"].value;
        }

        let urlStringForCatalog = "";
        let urlStringForOrder = "";
        let accountServiceUrl = "";
        let protocol = window.location.protocol;
        let host = window.location.hostname;
        if (host.includes("localhost")){
            urlStringForCatalog = protocol + "//" + host +  (catalogPort.toString().length > 0 ? ":" + catalogPort : "") + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + host +  (orderPort.toString().length > 0 ? ":" + orderPort : "") + '/order/api/v1';
            accountServiceUrl = protocol + "//" + host +  (accountServicePort.toString().length > 0 ? ":" + accountServicePort : "") + servicesProperties['account.soapservice.url.suffix'] + '/';
        }else if(isReversedProxy && !gatewayOn){
            urlStringForCatalog = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/order/api/v1';
            accountServiceUrl = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + servicesProperties['account.soapservice.url.suffix'] + '/';
        }else if(gatewayOn){
            urlStringForCatalog = (protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + "/"  + (servicesProperties["catalog.service.url.suffix"].value) + "/");
            urlStringForOrder = (protocol + "//" + host + (window.location.port.length > 0 ? ":" + window.location.port : "") + "/"  + (servicesProperties["order.service.url.suffix"].value) + "/");
            accountServiceUrl = (protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + "/" +
                (servicesProperties["account.soapservice.url.suffix"].value) + "/");
        }else{
            urlStringForCatalog = protocol + "//" + catalogHost + ':' + catalogPort + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + orderHost + ':' + orderPort + '/order/api/v1';
            accountServiceUrl = protocol + "//" + accountServiceHost + ':' + accountServicePort + servicesProperties['account.soapservice.url.suffix'] + '/';
        }
        this.setState(prevState => {
            let portsForRouting = { ...prevState.portsForRouting };  // creating copy of state variable portsForRouting
            portsForRouting.accountService = accountServicePort;
            portsForRouting.accountServiceHost = accountServiceHost;
            portsForRouting.catalog = catalogPort;
            portsForRouting.order = orderPort;
            portsForRouting.isSingleMachineDeployment = servicesProperties["single.machine.deployment"].value;
            portsForRouting.isReverseProxy = isReversedProxy;// update the port property, assign a new value
            portsForRouting.catalogServiceUrl = urlStringForCatalog;
            portsForRouting.accountServiceUrl = accountServiceUrl;
            portsForRouting.orderServiceUrl = urlStringForOrder;
            return { portsForRouting };                                 // return new object portsForRouting object
        })
    }
    parseServicesAsTxt(data){
        let servicesProperties = Parser.parse(data);
        let catalogPort ='';
        let catalogHost ='';
        let accountServicePort='';
        let accountServiceHost='';
        let orderPort='';
        let orderHost='';
        let isReversedProxy = (servicesProperties["reverse.proxy"] === "true");
        if(isReversedProxy){
            catalogPort ='';
            accountServicePort='';
            orderPort='';
            accountServiceHost='';
            catalogHost = '';
            orderHost = '';
        }else{
            catalogPort = servicesProperties["catalog.service.url.port"];
            catalogHost = servicesProperties["catalog.service.url.host"];
            accountServicePort = servicesProperties["account.soapservice.url.port"];
            accountServiceHost = servicesProperties["account.soapservice.url.host"];
            orderPort = servicesProperties["order.service.url.port"];
            orderHost = servicesProperties["order.service.url.host"];
        }

        let urlStringForCatalog = "";
        let urlStringForOrder = "";
        let accountServiceUrl = "";
        let protocol = window.location.protocol;
        let host = window.location.hostname;
        if (host.includes("localhost")){
            urlStringForCatalog = protocol + "//" + host +  (catalogPort.length > 0 ? ":" + catalogPort : "") + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + host +  (orderPort.length > 0 ? ":" + orderPort : "") + '/order/api/v1';
            accountServiceUrl = protocol + "//" + host +  (accountServicePort.length > 0 ? ":" + accountServicePort : "") + servicesProperties['account.soapservice.url.suffix'] + '/';
        }else if(isReversedProxy){
            urlStringForCatalog = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + '/order/api/v1';
            accountServiceUrl = protocol + "//" + host  + (window.location.port.length > 0 ? ":" + window.location.port : "") + servicesProperties['account.soapservice.url.suffix'] + '/';
        }else{
            urlStringForCatalog = protocol + "//" + catalogHost + ':' + catalogPort + '/catalog/api/v1';
            urlStringForOrder = protocol + "//" + orderHost + ':' + orderPort + '/order/api/v1';
            accountServiceUrl = protocol + "//" + accountServiceHost + ':' + accountServicePort + servicesProperties['account.soapservice.url.suffix'] + '/';
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
            portsForRouting.orderServiceUrl = urlStringForOrder;
            return { portsForRouting };                                 // return new object portsForRouting object
        })
    }
    getServicesProperties = ()=>{

        let host = window.location.origin;
        fetch(host+'/services.properties',{mode:'cors',
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
            })
            .then(response=>{
                return response.text()
            })
            .then((res)=> {
                try {
                    JSON.parse(res);
                    this.parseServicesAsJSON(res);
                } catch(err) {
                    this.parseServicesAsTxt(res);
                }


        }).catch((data)=>{
            console.log(data);
        });
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