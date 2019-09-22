import React from 'react';
import '../css-management-console/managment-main.css';
import {ReactComponent as UserIcon} from "../svg-png-ext/User.svg";

export default class HeaderDiv extends React.Component {


    render() {
        return (
            <div>
                <h1 className="confHeaderTitle">CONFIGURATION</h1>
                <div className="admin-logout">
                    <div className="left-icon-header">
                        <UserIcon/>
                    </div>
                    <div className="admin-logout-buttons left-btn">Admin</div>
                    <div className="vertical-line"/>
                    <div onClick={this.props.handleLogoutClick} className="admin-logout-buttons pointer-cursor right-logout-btn">Logout</div>
                </div>
            </div>
        );
    }
}