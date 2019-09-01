import React from 'react';
import General from './General';
import Itom from './Itom';
import Functional from './Functional';
import Security from './Security';
import '../css-management-console/configuration-style.css';
import ConfigurationIconsList from "./configurationIconsList";
import MobileTab from "./MobileTab";
import PerformanceTab from "./PerformanceTab";


export default class Configuration extends React.Component {

    constructor() {
        super();

        this.navToOpen = this.navToOpen.bind(this);
    }

        state = {
            generalItemsArray: [],
            allItemsArray: [],
            functionalItemsArray: [],
            securityItemsArray: [],
            itomItemsArray:[],
            mobileItemsArray:[],
            performanceItemsArray:[],
            tabToShow:'General'
        };



    navToOpen(e){
        this.setState({tabToShow: e.target.id})
    };

    componentDidMount(){
        let tempGeneralItemsArray=[];
        let tempFunctionalItemsArray=[];
        let tempSecurityItemsArray=[];
        let tempItomItemsArray=[];
        let tempPerformanceItemsArray=[];
        let tempMobileItemsArray=[];
        let tempAllItemsArray=[];

        fetch('http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL')
            .then(res => res.json())
            .then((data) => {
                data.parameters.forEach((item) =>{
                    tempAllItemsArray.push(item);
                });

                tempAllItemsArray.forEach((item)=>{

                    if(item.attributeTools.includes("LeanFT") || item.attributeTools.includes("UFT")){
                        tempGeneralItemsArray.push(item);
                    }if(item.attributeTools.includes("LoadRunner")){
                        tempItomItemsArray.push(item);
                    }if(item.attributeTools.includes("AppPulse")){
                        tempSecurityItemsArray.push(item);
                    }if(item.attributeTools.includes("Fortify") || item.attributeTools.includes("StormRunner") ){
                        tempFunctionalItemsArray.push(item);
                    }if(item.attributeTools.includes("BPT")){
                        tempPerformanceItemsArray.push(item);
                    }if(item.attributeTools.includes("NV")){
                        tempMobileItemsArray.push(item);
                    }
                });
                this.setState({generalItemsArray:tempGeneralItemsArray,
                    itomItemsArray:tempItomItemsArray,
                    functionalItemsArray:tempFunctionalItemsArray,
                    securityItemsArray:tempSecurityItemsArray,
                    performanceItemsArray:tempPerformanceItemsArray,
                    mobileItemsArray:tempMobileItemsArray
                });
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <ConfigurationIconsList/>
                <ul className="configuration-headlines">
                    <li>
                        <div id='General' onClick={this.navToOpen}>General</div>
                        <div className={this.state.tabToShow === 'General'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Itom' onClick={this.navToOpen}>Itom</div>
                        <div className={this.state.tabToShow === 'Itom'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Security' onClick={this.navToOpen}>Security</div>
                        <div className={this.state.tabToShow === 'Security'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Functional' onClick={this.navToOpen}>Functional</div>
                        <div className={this.state.tabToShow === 'Functional'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Performance' onClick={this.navToOpen}>Performance</div>
                        <div className={this.state.tabToShow === 'Performance'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Mobile' onClick={this.navToOpen}>Mobile</div>
                        <div className={this.state.tabToShow === 'Mobile'? "focus-line" : null}/>
                    </li>
                </ul>
                <div className="config-line-separator"/>
                <div>
                    {this.state.generalItemsArray && this.state.generalItemsArray.length && (this.state.tabToShow ==='General') > 0 ?
                    <General itemsToShow={this.state.generalItemsArray}/> : null}


                    {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Itom') > 0 ?
                        <Itom itemsToShow={this.state.itomItemsArray} /> : null}


                    {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Functional') > 0 ?
                        <Functional itemsToShow={this.state.functionalItemsArray} /> : null}


                    {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Security') > 0 ?
                        <Security itemsToShow={this.state.securityItemsArray} /> : null}

                    {this.state.mobileItemsArray && this.state.mobileItemsArray.length && (this.state.tabToShow ==='Mobile') > 0 ?
                        <MobileTab itemsToShow={this.state.mobileItemsArray} /> : null}

                    {this.state.performanceItemsArray && this.state.performanceItemsArray.length && (this.state.tabToShow ==='Performance') > 0 ?
                        <PerformanceTab itemsToShow={this.state.performanceItemsArray} /> : null}
                </div>
            </div>
        );
    }
}