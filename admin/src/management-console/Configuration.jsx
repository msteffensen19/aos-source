import React from 'react';
import General from './General';
import '../css-management-console/configuration-style.css';
import Popup from'./DevPopupWindow';
import SearchInConfiguration from "./SearchInConfiguration";
import NoSearchResult from "./NoSearchResult";
import TableHeaders from './TableHeaders';
import {ReactComponent as ExportIcon} from "../svg-png-ext/exportToExcel.svg";
import {ReactComponent as RestoreIcon} from "../svg-png-ext/RestoreIcon.svg";
import {ReactComponent as FilterIcon} from "../svg-png-ext/filterIconConfig.svg";
import ContextProvider from '../landing-page/ConsoleContext';
import DonePopup from './SavedPopup';

export default class Configuration extends React.Component {

    constructor() {
        super();

        this.handleRestoreConfiguration=this.handleRestoreConfiguration.bind(this);
        this.handleDevIconClick=this.handleDevIconClick.bind(this);
        this.handleCloseDevIcon=this.handleCloseDevIcon.bind(this);
        this.closeRestorePopUp=this.closeRestorePopUp.bind(this);
        this.isProductionFunc=this.isProductionFunc.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.isSearchMode=this.isSearchMode.bind(this);
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
            bodyItemsToShow:[],
            tabToShow:'General',
            isSearchMode:false,
            searchTerm:'',
            openDevPopup:false
    };

    handleDevIconClick(){
        this.setState({openDevPopup:true})
    };
    handleCloseDevIcon(){
        this.setState({openDevPopup:false})
    };
    navToOpen(e){
        this.setState({tabToShow: e.target.id});

        switch (e.target.id) {
            case "General":
                this.setState({bodyItemsToShow:this.state.generalItemsArray});
                break;
            case "Performance":
                this.setState({bodyItemsToShow:this.state.performanceItemsArray});
                break;
            case "Functional":
                this.setState({bodyItemsToShow:this.state.functionalItemsArray});
                break;
            case "Mobile":
                this.setState({bodyItemsToShow:this.state.mobileItemsArray});
                break;
            case "Itom":
                this.setState({bodyItemsToShow:this.state.itomItemsArray});
                break;
            case "Security":
                this.setState({bodyItemsToShow:this.state.securityItemsArray});
                break;
            default:
                console.log('default');
        }
    };

    isSearchMode(){
        return this.state.isSearchMode;
    };

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

    };
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
            if(tempGeneralItemsArray.length !==0){
                this.setState({tabToShow:"General"});
                this.setState({bodyItemsToShow:tempGeneralItemsArray});
                return;
            }
            if(tempPerformanceItemsArray.length !==0){
                this.setState({tabToShow:"Performance"});
                this.setState({bodyItemsToShow:tempPerformanceItemsArray});
                return;
            }
            if(tempFunctionalItemsArray.length !==0){
                this.setState({tabToShow:"Functional"});
                this.setState({bodyItemsToShow:tempFunctionalItemsArray});
                return;
            }
            if(tempMobileItemsArray.length !==0){
                this.setState({tabToShow:"Mobile"});
                this.setState({bodyItemsToShow:tempMobileItemsArray});
                return;
            }
            if(tempItomItemsArray.length !==0){
                this.setState({tabToShow:"Itom"});
                this.setState({bodyItemsToShow:tempItomItemsArray});
                return;
            }
            if(tempSecurityItemsArray.length !==0){
                this.setState({tabToShow:"Security"});
                this.setState({bodyItemsToShow:tempSecurityItemsArray});
            }
            this.setState({bodyItemsToShow:[]});

    }
    closeRestorePopUp (){
        this.setState({openDonePopup:false});
    };
    handleRestoreConfiguration (){

        if(this.isProductionFunc()){
            this.setState({openDonePopup:true});
            this.setState({textForPopup:"Not available here!"})
            return;
        }

        let urlStringForCatalog = "";
        let host = window.location.origin;
        let isReversedProxy = this.context.isReverseProxy;

        if (host.includes("localhost")){
            urlStringForCatalog ="http://localhost:8080";
        }else if(isReversedProxy){
            urlStringForCatalog = host;
        }else{
            urlStringForCatalog = host + ':' + this.context.catalog;
        }
        fetch(urlStringForCatalog+'/catalog/api/v1/DemoAppConfig/Restore_Factory_Settings')
            .then(res => res.json())
            .then(
                (result) => {
                    if(result.success !== true) {
                        console.log("ERROR--" + result.reason);
                        this.setState({openDonePopup:true});
                        this.setState({textForPopup:"Restore Failed!"})
                    }else{
                        console.log("success--" + result.reason);
                        this.setState({openDonePopup:true});
                        this.setState({textForPopup:"Restored Successfully!"})
                    }
                },
                    (error) => {
                        console.log("ERROR--" + error);

                    });
    };

    isProductionFunc (){
        let host = window.location.host.toLowerCase();
        if(host.includes("advantageonlineshopping") || host.includes("107.23.171.213") || host.includes("ec2-107-23-171-213.compute-1.amazonaws.com")){
            return true;
        }else return false;
    };

        componentDidMount(){
        let tempGeneralItemsArray=[];
        let tempFunctionalItemsArray=[];
        let tempSecurityItemsArray=[];
        let tempItomItemsArray=[];
        let tempPerformanceItemsArray=[];
        let tempMobileItemsArray=[];
        let tempAllItemsArray=[];

        let host = window.location.origin;
            let port = this.context.catalog;
            let urlString="";
            let isReversedProxy = this.context.isReverseProxy;
            if (host.includes("localhost")){
                urlString = "http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL";
            }else if(isReversedProxy){
                urlString = host + '/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL';
            }else{
                urlString = host + ':' + port + '/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL';
            }

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
                    bodyItemsToShow:tempGeneralItemsArray,
                    allItemsArray:tempAllItemsArray
                });
            })
            .catch(console.log);
    }

    render() {
        return (
            <>
                {this.state.openDonePopup?<DonePopup closePopUp={this.closeRestorePopUp} textForPopup={this.state.textForPopup}/>:null}
                <div className="table-header-div">
                {this.state.openDevPopup?<Popup closePopUp = {this.handleCloseDevIcon}/>:null}
                <ul className="configuration-icons">

                    <SearchInConfiguration onUserSearch={this.handleSearch} isSearchMode={this.state.isSearchMode}/>
                    <li title={"filter results"}>
                        <FilterIcon className="pointer-cursor" onClick={this.handleDevIconClick}/>
                    </li>
                    <li title={"export to excel"}>
                        <ExportIcon className="pointer-cursor" onClick={this.handleDevIconClick}/>
                    </li>
                    <li title={"restore all configuration to initial state"}>
                        <RestoreIcon className="pointer-cursor" onClick={this.handleRestoreConfiguration}/>
                    </li>
                </ul>
                <ul className="configuration-headlines">
                    <li>
                        <div id='General' onClick={this.navToOpen} className="pointer-cursor">General</div><div className = {this.state.isSearchMode &&
                    this.state.generalItemsArray.length >0? "search-dot-gen": null} />
                        <div className={this.state.tabToShow === 'General'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Performance' onClick={this.navToOpen} className="pointer-cursor">Performance</div><div className = {this.state.isSearchMode &&
                    this.state.performanceItemsArray.length >0? "search-dot-per": null}/>
                        <div className={this.state.tabToShow === 'Performance'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Functional' onClick={this.navToOpen} className="pointer-cursor">Functional</div><div className = {this.state.isSearchMode &&
                    this.state.functionalItemsArray.length >0? "search-dot-func": null}/>
                        <div className={this.state.tabToShow === 'Functional'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Mobile' onClick={this.navToOpen} className="pointer-cursor">Mobile</div><div className = {this.state.isSearchMode &&
                    this.state.mobileItemsArray.length >0? "search-dot-mob": null}/>
                        <div className={this.state.tabToShow === 'Mobile'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Itom' onClick={this.navToOpen} className="pointer-cursor">ITOM</div><div className = {this.state.isSearchMode &&
                    this.state.itomItemsArray.length >0? "search-dot-ito": null}/>
                        <div className={this.state.tabToShow === 'Itom'? "focus-line" : null}/>
                    </li>
                    <li>
                        <div id='Security' onClick={this.navToOpen} className="pointer-cursor">Security</div><div className = {this.state.isSearchMode &&
                    this.state.securityItemsArray.length >0? "search-dot-sec": null}/>
                        <div className={this.state.tabToShow === 'Security'? "focus-line" : null}/>
                    </li>
                </ul>
                <div className="config-line-separator"/>
                    <TableHeaders/>
                </div>
                    {this.state.bodyItemsToShow && this.state.bodyItemsToShow.length > 0 ?
                    <General searchTerm={this.state.searchTerm} isSearchMode={this.state.isSearchMode} itemsToShow={this.state.bodyItemsToShow}/> : this.state.isSearchMode?<NoSearchResult/>:null}
            </>
        );
    }
}
Configuration.contextType = ContextProvider;