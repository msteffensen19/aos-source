import React from 'react';
import RestoreDBToDefault from "./RestoreDBToDefault";
import ExportToExcel from "./ExportToExcel";

export default class LeftNavBar extends React.Component {

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
                    <ExportToExcel/>
                    <RestoreDBToDefault/>
                </ul>
            </div>
        );
    }
}