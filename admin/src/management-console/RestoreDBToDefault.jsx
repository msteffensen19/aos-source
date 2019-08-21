import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';

export default class RestoreDBToDefault extends React.Component {

    constructor(props) {
        super(props);
        this.restoreDbToDefault = this.restoreDbToDefault.bind(this);
    }

    restoreDbToDefault(event){

        let $ = require('jquery');
        require('jquery.soap');
        var parseString = require('jquery.soap');
        $.soap({
            timeout: 10000,
            url: 'http://localhost:8080/accountservice/', //host+'/accountservice/',
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
                                    },
                                    (error) => {
                                        console.log("ERROR--" + error);
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
                    <li className="nav-item">
                        <button onClick={this.restoreDbToDefault}>Fourth button</button>
                    </li>

        );
    }
}