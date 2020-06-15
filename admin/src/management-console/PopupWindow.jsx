import React from "react";
import {ReactComponent as UserIcon} from "../svg-png-ext/Advantage_logo_blck.svg";
import {withRouter} from 'react-router-dom';
import ReactGA from 'react-ga';

class PopupWindow extends React.Component {
    constructor() {
        super();
        this.cancelClick = this.cancelClick.bind(this);
        this.yesClick = this.yesClick.bind(this);
    }

    cancelClick() {
        this.props.closePopUp();
    }
    yesClick(){
        this.props.closePopUp();
        let me = this;
        me.props.history.push('/');
        ReactGA.event({
            category: 'Management Console',
            action: 'Admin Console Logout'
        });
    }


    render() {
        return (
            <div id={"popupManagement"} className={"pop-up-cover"}>
                <div className={"pop-up-style"}>
                    <div className={"popup-image"}>
                        <UserIcon className={"svg-popup-style"}/>
                    </div>
                    <span className={"popup-title"}>Logout</span>

                    <span className={"popup-content"}>Do you wish to logout?</span>
                    <div className={"popup-line-separator"}/>
                   <span className={"popup-yes-btn"} onClick={this.yesClick}>
                    Yes
                    </span>
                    <span className={"popup-no-btn"} onClick={this.cancelClick}>
                    Cancel
                   </span>
                </div>
            </div>
        );
    }
}

export default withRouter(PopupWindow);