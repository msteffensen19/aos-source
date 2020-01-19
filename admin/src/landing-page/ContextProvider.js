
import React from 'react'

import MyContext from './ConsoleContext';
import Parser from 'properties-parser';

export default class ContextProvider extends React.Component {

    state = {
        portsForRouting:{
            accountService:80,
            catalog:80,
            order:80,
            isSingleMachineDeployment:false,
            isReverseProxy:false
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
            let accountServicePort='';
            let accountServiceHost='';
            let orderPort='';
            let isReversedProxy = (servicesProperties["reverse.proxy"] === "true");
            if(isReversedProxy){
                catalogPort ='';
                accountServicePort='';
                orderPort='';
                accountServiceHost='';
            }else{
                catalogPort = servicesProperties["catalog.service.url.port"];
                accountServicePort = servicesProperties["account.soapservice.url.port"];
                accountServiceHost = servicesProperties["account.soapservice.url.host"];
                orderPort = servicesProperties["order.service.url.port"];
            }
            this.setState(prevState => {
                let portsForRouting = { ...prevState.portsForRouting };  // creating copy of state variable portsForRouting
                portsForRouting.accountService = accountServicePort;
                portsForRouting.catalog = catalogPort;
                portsForRouting.order = orderPort;
                portsForRouting.isSingleMachineDeployment = servicesProperties["single.machine.deployment"];
                portsForRouting.isReverseProxy = isReversedProxy;// update the port property, assign a new value
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