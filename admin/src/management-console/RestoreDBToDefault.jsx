import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';
import PopupWindow from './RestoreDBPopup';
import DonePopup from './SavedPopup';
import ContextProvider from "../landing-page/ConsoleContext";

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

    openDialog = ()=>{
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
        let port = this.context.catalog;
        let urlString = host.includes("localhost")? "http://localhost:8080/accountservice/"://this line is used for developing on localhost:3000.
            host + ':' + port +'/accountservice/';
        $.soap({
            timeout: 10000,
            url: urlString,
            method: 'RestoreDBToFactorySettingRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountserviceRestoreDBToFactorySettingRequest',
            data: "1",//This line is just for the SOAP request to work it has no significance.

            success: function (soapResponse) {
                let response = parseString(soapResponse);
                console.log("adv_account had successfully restored to default--" + response);
                fetch("http://localhost:8080/catalog/api/v1/catalog/Restore_db_factory_settings")
                    .then(res => res.json())
                    .then(
                        (result) => {
                            console.log("result--" + result.reason);
                            fetch("http://localhost:8080/order/api/v1/order/Restore_db_factory_settings")
                                .then(res => res.json())
                                .then(
                                    (result) => {
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
                        });

            },
            error: function (response) {
                console.log(response);

            },

            enableLogging: true
        });
        event.preventDefault();
    }

    render() {
        return (
            <>
            {this.state.openDonePopup?<DonePopup closePopUp={this.closeDonePopup} textForPopup={this.state.textForPopup}/>:null}
            {this.state.openPopUp?<PopupWindow closePopUp = {this.closePopUp} onYesClick={this.onYesClick}/>:null}
            <h3 className="navbar-headline-console-restore" onClick={this.openDialog}>Restore DB to Factory Settings</h3>
            </>
        );
    }
}
RestoreDBToDefault.contextType = ContextProvider;