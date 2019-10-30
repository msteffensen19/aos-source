import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';
import PopupWindow from './RestoreDBPopup';
import DonePopup from './SavedPopup';
import DevPopup from'./DevPopupWindow';
import ContextProvider from "../landing-page/ConsoleContext";
import  xml2jsonImpl from '../landing-page/Parser';

export default class RestoreDBToDefault extends React.Component {

    constructor(props) {
        super(props);
        this.restoreDbToDefault = this.restoreDbToDefault.bind(this);
    }
    state = {openPopUp:false,
    openDonePopup:false,
    textForPopup:""};

    closePopUp =()=>{
        this.setState({openPopUp:false});
    };

    onYesClick =(event)=>{
        this.restoreDbToDefault(event);
    };

    isProductionFunc = ()=>{
        let host = window.location.host.toLowerCase();
        if(host.includes("advantageonlineshopping") || host.includes("107.23.171.213") || host.includes("ec2-107-23-171-213.compute-1.amazonaws.com")){
            return true;
        }else return false;
    };
    openDialog = ()=>{
        if(this.isProductionFunc()){
            this.setState({openDonePopup:true});
            this.setState({textForPopup:"Not available here!"})
            return;
        }
        this.setState({openPopUp:true});
    };
    closeDonePopup = ()=>{
    this.setState({openDonePopup:false});
    };

    restoreDbToDefault(event){


        let me = this;
        let $ = require('jquery');
        require('jquery.soap');
        let parseString = require('jquery.soap');
        let host = window.location.origin;
        let port = this.context.accountService;
        let urlString="";
        let isReversedProxy = this.context.isReverseProxy;
        if (host.includes("localhost")){
            urlString ="http://localhost:8080";
        }else if(isReversedProxy){
            urlString = host;
        }else{
            urlString = host + ':' + port;
        }
        let urlStringForCatalog = "";

        if (host.includes("localhost")){
            urlStringForCatalog ="http://localhost:8080";
        }else if(isReversedProxy){
            urlStringForCatalog = host;
        }else{
            urlStringForCatalog = host + ':' + this.context.catalog;
        }
        let urlStringForOrder = "";

        if (host.includes("localhost")){
            urlStringForOrder ="http://localhost:8080";
        }else if(isReversedProxy){
            urlStringForOrder = host;
        }else{
            urlStringForOrder = host + ':' + this.context.order;
        }
        $.soap({
            timeout: 10000,
            url: urlString+'/accountservice/',
            method: 'RestoreDBToFactorySettingRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountserviceRestoreDBToFactorySettingRequest',
            data: "1",//This line is just for the SOAP request to work it has no significance.

            success: function (soapResponse) {
                let response = parseString(soapResponse);
                let statusMessage = xml2jsonImpl(response.content,"RestoreDBToFactorySettingResponse", false);
                xml2jsonImpl(null, null, true);
                console.log(statusMessage);
                if(statusMessage.success !== true){
                    console.log("ERROR--" + statusMessage.reason);
                    me.setState({openDonePopup:true});
                    me.setState({textForPopup:"Restore Failed!"});
                    return;
                }else{
                    console.log("adv_account had successfully restored to default--" + response);
                }
                fetch(urlStringForCatalog+'/catalog/api/v1/catalog/Restore_db_factory_settings')
                    .then(res => res.json())
                    .then(
                        (result) => {
                            if(result.success !== true){
                                console.log("ERROR--" + result.reason);
                                me.setState({openDonePopup:true});
                                me.setState({textForPopup:"Restore Failed!"});
                                return;
                            }
                            console.log("result--" + result.reason);
                            fetch(urlStringForOrder + '/order/api/v1/order/Restore_db_factory_settings')
                                .then(res => res.json())
                                .then(
                                    (result) => {
                                        if(result.success !== true){
                                            console.log("ERROR--" + result.reason);
                                            me.setState({openDonePopup:true});
                                            me.setState({textForPopup:"Restore Failed!"})
                                            return;
                                        }
                                        console.log("result--" + result.details);
                                        me.setState({openDonePopup:true});
                                        me.setState({textForPopup:"Restored Successfully!"})
                                    },
                                    (error) => {
                                        console.log("ERROR--" + error);
                                        me.setState({openDonePopup:true});
                                        me.setState({textForPopup:"Restore Failed!"})
                                    });
                        },
                        (error) => {
                            console.log("ERROR--" + error);
                            me.setState({openDonePopup:true});
                            me.setState({textForPopup:"Restore Failed!"})
                            return;
                        });

            },
            error: function (response) {
                console.log(response);

            },

            enableLogging: true
        });
        event.preventDefault();
    }
    handleDevIconClick=()=>{
        this.setState({openDevPopup:true})
    };
    handleCloseDevIcon=()=>{
        this.setState({openDevPopup:false})
    };

    render() {
        return (
            <>
            {this.state.openDonePopup?<DonePopup closePopUp={this.closeDonePopup} textForPopup={this.state.textForPopup}/>:null}
            {this.state.openPopUp?<PopupWindow closePopUp = {this.closePopUp} onYesClick={this.onYesClick}/>:null}
            {this.state.openDevPopup?<DevPopup closePopUp = {this.handleCloseDevIcon}/>:null}
            <h3 className="navbar-headline-console-restore" onClick={this.handleDevIconClick}>Restore DB to Factory Settings</h3>
            </>
        );
    }
}
RestoreDBToDefault.contextType = ContextProvider;