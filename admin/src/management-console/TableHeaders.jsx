import React, { Component } from 'react';

import '../css-management-console/configuration-style.css';

export default class TableHeaders extends Component {

    backToAOS(){
        window.location.href = window.location.origin.toString();
    }

    render() {
        return (
            <ul className="table-row-style">
                <li>Name</li>
                <li>Value</li>
                <li>Description</li>
                <li>AOS location</li>
            </ul>
        )
    }
}
