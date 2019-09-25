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

export default class Configuration extends React.Component {

    constructor() {
        super();
        this.handleDevIconClick=this.handleDevIconClick.bind(this);
        this.handleCloseDevIcon=this.handleCloseDevIcon.bind(this);
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

        componentDidMount(){
        //context is not detracted see https://github.com/facebook/react/issues/16250.
        console.log(this.context);
        let tempGeneralItemsArray=[];
        let tempFunctionalItemsArray=[];
        let tempSecurityItemsArray=[];
        let tempItomItemsArray=[];
        let tempPerformanceItemsArray=[];
        let tempMobileItemsArray=[];
        let tempAllItemsArray=[];

        let host = window.location.origin;

            let urlString = host.includes("localhost")? "http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL"://this line is used for developing on localhost:3000.
                host + ':' + this.context.catalog +'/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL';

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
                <div className="table-header-div">
                {this.state.openDevPopup?<Popup closePopUp = {this.handleCloseDevIcon}/>:null}
                <ul className="configuration-icons">

                    <SearchInConfiguration onUserSearch={this.handleSearch} isSearchMode={this.state.isSearchMode}/>
                    <li>
                        <FilterIcon className="pointer-cursor" onClick={this.handleDevIconClick}/>
                    </li>
                    <li>
                        <ExportIcon className="pointer-cursor" onClick={this.handleDevIconClick}/>
                    </li>
                    <li>
                        <RestoreIcon className="pointer-cursor" onClick={this.handleDevIconClick}/>
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