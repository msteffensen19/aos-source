import React from "react";
import {ReactComponent as UserIcon} from "../svg-png-ext/Advantage_logo_blck.svg";


export default class RestoreDBPopup extends React.Component {
    constructor() {
        super();
        this.cancelClick = this.cancelClick.bind(this);
        this.yesClick = this.yesClick.bind(this);
    }

    cancelClick() {
        this.props.closePopUp();
    }
    yesClick(event){
        this.props.closePopUp();
        this.props.onYesClick(event);
    }


    render() {
        return (
            <div id={"popupManagement"} className={"pop-up-cover"}>
                <div className={"pop-up-style"}>
                    <div className={"popup-image"}>
                        <UserIcon className={"svg-popup-style"}/>
                    </div>
                    <span className={"restore-popup-title"}>Restore DB</span>

                    <span className="restore-DB-popup-content">Are you sure you want to restore DB to factory setting?</span>
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

