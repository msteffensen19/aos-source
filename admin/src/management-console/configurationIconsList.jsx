import React from 'react';
import '../css-management-console/managment-main.css';
import SearchInConfiguration from "./SearchInConfiguration"
import {ReactComponent as ExportIcon} from "../svg-png-ext/exportToExcel.svg";
import {ReactComponent as RestoreIcon} from "../svg-png-ext/RestoreIcon.svg";
import {ReactComponent as FilterIcon} from "../svg-png-ext/filterIconConfig.svg";

export default class ConfigurationIconsList extends React.Component {


    render() {
        return (
            <ul className="configuration-icons">

                <SearchInConfiguration/>
                <li>
                    <FilterIcon/>
                </li>
                <li>
                    <ExportIcon/>
                </li>
                <li>
                    <RestoreIcon/>
                </li>
            </ul>
        );
    }
}