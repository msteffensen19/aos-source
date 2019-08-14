import React from 'react';
import 'jquery';
import 'jquery.soap';

export default class LeftNavBar extends React.Component {

    constructor() {
        super();

        this.restoreDbTODefault = this.restoreDbTODefault.bind(this);
    }
    restoreDbTODefault(){

        let $ = require('jquery');
        require('jquery.soap');
        var parseString = require('jquery.soap');
        $.soap({
            url: 'http://localhost:8080/accountservice/', //host+'/accountservice/',
            method: 'RestoreDBToFactorySettingRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountserviceAccountLoginRequest' ,

            success: function (soapResponse) {
                let response = parseString(soapResponse);
                console.log(response);

            },
            error: function (response) {
                console.log(response);
            },
            enableLogging: true
        });
    }

    render() {
        return (
            <div className="nav-ul-container">
                <ul className="nav flex-column navigation-ul">
                    <li className="nav-item ">
                        <button>First button</button>
                    </li>
                    <li className="nav-item">
                        <button>Second button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                    <li className="nav-item">
                        <button onClick={this.restoreDbTODefault}>Fourth button</button>
                    </li>
                </ul>
            </div>
        );
    }
}