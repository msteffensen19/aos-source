import React from 'react';
import '../css-management-console/managment-main.css';
import UserButton from "./HeaderButtons";
import LogoutButton from "./HeaderButtons";

export default class HeaderDiv extends React.Component {


    render() {
        return (
            <div>
                <h1 className="confHeaderTitle">CONFIGURATION</h1>
                <UserButton/>
            </div>
        );
    }
}