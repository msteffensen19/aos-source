import React from 'react';
import '../css-management-console/managment-main.css';
import {ReactComponent as UserIcon} from "../svg-png-ext/User.svg";

export default class UserButton extends React.Component {


    render() {
        return (

            <div className="admin-logout">
                <div className="left-icon-header">
                    <UserIcon style={{paddingBottom:"10px"}}/>
                </div>
                <div className="admin-logout-buttons left-btn">Admin</div>
                <div className="vertical-line"></div>
                <div className="admin-logout-buttons right-logout-btn">Logout</div>
            </div>
        );
    }
}
