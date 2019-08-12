import React from 'react';
import General from './General';
import Itom from './Itom';
import Functional from './Functional';
import Security from './Security';


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
            tabToShow:'General'
        };



    navToOpen(e){
        this.setState({tabToShow: e.target.name})
    };

    componentDidMount(){
        let tempGeneralItemsArray=[];
        let tempFunctionalItemsArray=[];
        let tempSecurityItemsArray=[];
        let tempItomItemsArray=[];
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
                    }if(item.attributeTools.includes("Fortify")){
                        tempFunctionalItemsArray.push(item);
                    }
                });
                this.setState({generalItemsArray:tempGeneralItemsArray,
                    itomItemsArray:tempItomItemsArray,
                    functionalItemsArray:tempFunctionalItemsArray,
                    securityItemsArray:tempSecurityItemsArray

                });
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <ul>
                    <li className="nav-item">
                        <button name='General' onClick={this.navToOpen}>General</button>
                    </li>
                    <li className="nav-item">
                        <button name='Itom' onClick={this.navToOpen}>Itom</button>
                    </li>
                    <li className="nav-item">
                        <button name='Security' onClick={this.navToOpen}>Security</button>
                    </li>
                    <li className="nav-item">
                        <button name='Functional' onClick={this.navToOpen}>Functional</button>
                    </li>
                </ul>
                <ul>
                    <li>
                        {this.state.generalItemsArray && this.state.generalItemsArray.length && (this.state.tabToShow ==='General') > 0 ?
                            <General itemsToShow={this.state.generalItemsArray}/> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Itom') > 0 ?
                            <Itom itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Functional') > 0 ?
                            <Functional itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Security') > 0 ?
                            <Security itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                </ul>
            </div>
        );
    }
}