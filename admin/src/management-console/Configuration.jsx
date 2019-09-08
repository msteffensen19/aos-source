import React from 'react';
import General from './General';
import Itom from './Itom';
import Functional from './Functional';
import Security from './Security';
import '../css-management-console/configuration-style.css';
import MobileTab from "./MobileTab";
import PerformanceTab from "./PerformanceTab";
import SearchInConfiguration from "./SearchInConfiguration"
import {ReactComponent as ExportIcon} from "../svg-png-ext/exportToExcel.svg";
import {ReactComponent as RestoreIcon} from "../svg-png-ext/RestoreIcon.svg";
import {ReactComponent as FilterIcon} from "../svg-png-ext/filterIconConfig.svg";


export default class Configuration extends React.Component {

    constructor() {
        super();

        this.handleSearch = this.handleSearch.bind(this);
        this.navToOpen = this.navToOpen.bind(this);
        this.isSearchMode=this.isSearchMode.bind(this);
    }
        state = {
            generalItemsArray: [],
            allItemsArray: [],
            functionalItemsArray: [],
            securityItemsArray: [],
            itomItemsArray:[],
            mobileItemsArray:[],
            performanceItemsArray:[],
            tabToShow:'General',
            isSearchMode:false,
            searchTerm:''
    };


    navToOpen(e){
        this.setState({tabToShow: e.target.id})
    };

    isSearchMode(){
        return this.state.isSearchMode;
    }

    handleSearch(searchTerm){

        if(searchTerm !== ""){
            this.setState({isSearchMode:true});
            this.setState({searchTerm:searchTerm});
            this.searchInItems(searchTerm);
        }else{
            this.setState({isSearchMode:false});
            this.setState({searchTerm:searchTerm});
            this.searchInItems(searchTerm);
        }

    }
    searchInItems(searchTerm) {

        let tempGeneralItemsArray = [];
        let tempFunctionalItemsArray = [];
        let tempSecurityItemsArray = [];
        let tempItomItemsArray = [];
        let tempPerformanceItemsArray = [];
        let tempMobileItemsArray = [];

            this.state.allItemsArray.forEach((item) => {
                let itemPropValues = [item.parameterName, item.description, item.locationInAdminTool];
                let doesContainedSearchTerm = false;
                itemPropValues.forEach((element) => {
                    if (element.toLowerCase().includes(searchTerm.toLowerCase())){
                        doesContainedSearchTerm = true
                    }
                });
                if(doesContainedSearchTerm){
                    if (item.locationInAdminTool.includes("General")){
                        tempGeneralItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("ITOM")) {
                        tempItomItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Security")) {
                        tempSecurityItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Functional")) {
                        tempFunctionalItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Performance")) {
                        tempPerformanceItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Mobile")) {
                        tempMobileItemsArray.push(item);
                    }
                }
            });
            this.setState({
                generalItemsArray: tempGeneralItemsArray,
                itomItemsArray: tempItomItemsArray,
                functionalItemsArray: tempFunctionalItemsArray,
                securityItemsArray: tempSecurityItemsArray,
                performanceItemsArray: tempPerformanceItemsArray,
                mobileItemsArray: tempMobileItemsArray
            });
            if(searchTerm ===""){
                this.setState({isSearchMode:false});
            }else{
                this.setState({isSearchMode:true});
            }
    }

        componentDidMount(){
        let tempGeneralItemsArray=[];
        let tempFunctionalItemsArray=[];
        let tempSecurityItemsArray=[];
        let tempItomItemsArray=[];
        let tempPerformanceItemsArray=[];
        let tempMobileItemsArray=[];
        let tempAllItemsArray=[];

        let host = window.location.origin;

            let urlString = host.includes("localhost")? "http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL":
                host+'/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL';

        fetch(urlString)
            .then(res => res.json())
            .then((data) => {
                data.parameters.forEach((item) =>{
                    tempAllItemsArray.push(item);
                });

                tempAllItemsArray.forEach((item)=>{

                    if (item.locationInAdminTool.includes("General")){
                        tempGeneralItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("ITOM")) {
                        tempItomItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Security")) {
                        tempSecurityItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Functional")) {
                        tempFunctionalItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Performance")) {
                        tempPerformanceItemsArray.push(item);
                    }if (item.locationInAdminTool.includes("Mobile")) {
                        tempMobileItemsArray.push(item);
                    }
                });
                this.setState({generalItemsArray:tempGeneralItemsArray,
                    itomItemsArray:tempItomItemsArray,
                    functionalItemsArray:tempFunctionalItemsArray,
                    securityItemsArray:tempSecurityItemsArray,
                    performanceItemsArray:tempPerformanceItemsArray,
                    mobileItemsArray:tempMobileItemsArray,
                    allItemsArray:tempAllItemsArray
                });
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <ul className="configuration-icons">

                    <SearchInConfiguration onUserSearch={this.handleSearch} isSearchMode={this.state.isSearchMode}/>
                    <li>
                        <FilterIcon/>
                    </li>
                    <li>
                        <ExportIcon/>
                    </li>
                    <li>
                        <RestoreIcon/>
                    </li>
                </ul>
                <ul className="configuration-headlines">
                    <li>
                        <div id='General' onClick={this.navToOpen}>General</div><div className = {this.state.isSearchMode &&
                    this.state.generalItemsArray.length >0? "search-dot-gen": null}/>
                        <div className={this.state.tabToShow === 'General'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Performance' onClick={this.navToOpen}>Performance</div><div className = {this.state.isSearchMode &&
                    this.state.performanceItemsArray.length >0? "search-dot-per": null}/>
                        <div className={this.state.tabToShow === 'Performance'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Functional' onClick={this.navToOpen}>Functional</div><div className = {this.state.isSearchMode &&
                    this.state.functionalItemsArray.length >0? "search-dot-func": null}/>
                        <div className={this.state.tabToShow === 'Functional'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Mobile' onClick={this.navToOpen}>Mobile</div><div className = {this.state.isSearchMode &&
                    this.state.mobileItemsArray.length >0? "search-dot-mob": null}/>
                        <div className={this.state.tabToShow === 'Mobile'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Itom' onClick={this.navToOpen}>ITOM</div><div className = {this.state.isSearchMode &&
                    this.state.itomItemsArray.length >0? "search-dot-ito": null}/>
                        <div className={this.state.tabToShow === 'Itom'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Security' onClick={this.navToOpen}>Security</div><div className = {this.state.isSearchMode &&
                    this.state.securityItemsArray.length >0? "search-dot-sec": null}/>
                        <div className={this.state.tabToShow === 'Security'? "focus-line" : null}/>
                    </li>
                </ul>
                <div className="config-line-separator"/>
                <div>
                    {this.state.generalItemsArray && this.state.generalItemsArray.length && (this.state.tabToShow ==='General') > 0 ?
                    <General searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.generalItemsArray}/> : null}

                    {this.state.performanceItemsArray && this.state.performanceItemsArray.length && (this.state.tabToShow ==='Performance') > 0 ?
                        <PerformanceTab searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.performanceItemsArray} /> : null}

                    {this.state.functionalItemsArray && this.state.functionalItemsArray.length && (this.state.tabToShow ==='Functional') > 0 ?
                        <Functional searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.functionalItemsArray} /> : null}

                    {this.state.mobileItemsArray && this.state.mobileItemsArray.length && (this.state.tabToShow ==='Mobile') > 0 ?
                        <MobileTab searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.mobileItemsArray} /> : null}

                    {this.state.itomItemsArray && this.state.itomItemsArray.length && (this.state.tabToShow ==='Itom') > 0 ?
                        <Itom searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.itomItemsArray} /> : null}

                    {this.state.securityItemsArray && this.state.securityItemsArray.length && (this.state.tabToShow ==='Security') > 0 ?
                        <Security searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.securityItemsArray} /> : null}
                </div>
            </div>
        );
    }
}