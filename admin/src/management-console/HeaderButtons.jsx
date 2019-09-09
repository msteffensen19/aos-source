import React from 'react';
import '../css-management-console/managment-main.css';
import {ReactComponent as UserIcon} from "../svg-png-ext/User.svg";
import Popup from'./PopupWindow';

export default class UserButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {openPopUp:false};
        this.closePopUp = this.closePopUp.bind(this);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(){

        this.setState({openPopUp:true});

    }

    closePopUp(){

        this.setState({openPopUp:false});
    }

    render() {
        return (

            <div className="admin-logout">
                <div className="left-icon-header">
                    <UserIcon/>
                </div>
                <div className="admin-logout-buttons left-btn">Admin</div>
                <div className="vertical-line"/>
                <div onClick={this.handleClick} className="admin-logout-buttons pointer-cursor right-logout-btn">Logout</div>
                {this.state.openPopUp?<Popup closePopUp = {this.closePopUp}/>:null}
            </div>
        );
    }
}
