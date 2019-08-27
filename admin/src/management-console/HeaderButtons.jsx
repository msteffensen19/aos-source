import React from 'react';
import '../css-management-console/managment-main.css';

export default class UserButton extends React.Component {


    render() {
        return (

            <div className="admin-logout">
                <div className="admin-logout-buttons left-btn">Admin</div>
                <div className="vertical-line"></div>
                <div className="admin-logout-buttons">Logout</div>
            </div>
        );
    }
}
