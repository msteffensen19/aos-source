import React, { Component } from 'react';

import '../css-management-console/configuration-style.css';

export default class TableHeaders extends Component {

    backToAOS(){
        window.location.href = window.location.origin.toString();
    }

    render() {
        return (
            <tr className="table-row-style">
                <th className="specific-class">Name</th>
                <th>Value</th>
                <th>Description</th>
                <th>AOS location</th>
            </tr>
        )
    }
}
