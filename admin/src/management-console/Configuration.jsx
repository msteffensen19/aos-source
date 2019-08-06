import React from 'react';
import General from './General';
import Itom from './Itom';
import Functional from './Functional';
import Security from './Security';


export default class Configuration extends React.Component {


        state = {
            generalItemsArray: [],
            allItemsArray: [],
            functionalItemsArray: [],
            securityItemsArray: [],
            itomItemsArray:[]
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
                    <li>
                        {this.state.generalItemsArray && this.state.generalItemsArray.length > 0 ?
                            <General itemsToShow={this.state.generalItemsArray}/> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length > 0 ?
                            <Itom itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length > 0 ?
                            <Functional itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                    <li>
                        {this.state.itomItemsArray && this.state.itomItemsArray.length > 0 ?
                            <Security itemsToShow={this.state.itomItemsArray} /> : null}
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                </ul>
            </div>
        );
    }
}