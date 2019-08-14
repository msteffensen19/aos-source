import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';

export default class LeftNavBar extends React.Component {

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
                data: "1",

        success: function (soapResponse) {
            let response = parseString(soapResponse);
            console.log(response);

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
                        <button onClick={this.restoreDbToDefault}>Fourth button</button>
                    </li>
                </ul>
            </div>
        );
    }
}