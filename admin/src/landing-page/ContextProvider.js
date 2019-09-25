
import React from 'react'

import MyContext from './ConsoleContext';
import Parser from 'properties-parser';

export default class ContextProvider extends React.Component {

    state = {
        portsForRouting:{
            accountService:80,
            catalog:80,
            isSingleMachineDeployment:false,
            isReverseProxy:false
        }
    };

    componentWillMount(){
        this.getServicesProperties();
    }
    getServicesProperties = ()=>{

        let host = window.location.origin;
//.then(res => res.json())
//             .then((data) => {
        fetch(host+'/services.properties')
            .then(response=>response.text())
            .then((data)=> {
            let servicesProperties = Parser.parse(data);
            this.setState(prevState => {
                let portsForRouting = { ...prevState.portsForRouting };  // creating copy of state variable portsForRouting
                portsForRouting.accountService = servicesProperties["account.soapservice.url.port"];
                portsForRouting.catalog = servicesProperties["catalog.service.url.port"];
                portsForRouting.isSingleMachineDeployment = servicesProperties["single.machine.deployment"];
                portsForRouting.isReverseProxy = servicesProperties["reverse.proxy"];// update the port property, assign a new value
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