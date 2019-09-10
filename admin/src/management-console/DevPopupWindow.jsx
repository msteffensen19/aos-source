import React from "react";
import {ReactComponent as UserIcon} from "../svg-png-ext/Advantage_logo_blck.svg";

export default class DevPopupWindow extends React.Component {
    constructor() {
        super();

        this.state = { modalOpened: false };
        this.cancelClick = this.cancelClick.bind(this);
        this.yesClick = this.yesClick.bind(this);

    }

    cancelClick() {
        this.props.closePopUp();
    }
    yesClick(){
        this.props.closePopUp();

    }


    render() {
        return (
            <div id={"popupManagement"} className={"pop-up-cover"}>


                <div className={"pop-up-style"}>
                    <div className={"popup-image"}>
                        <UserIcon className={"svg-popup-style"}/>
                    </div>
                    <span className={"popup-title"}>Module Under Development</span>

                    <p className={"dev-popup-content"}>The module is currently under development <br/> and is not yet available.
                    </p>
                    <line className={"popup-line-separator"}/>
                    <span className={"popup-yes-btn"} onClick={this.yesClick}>
                    Close
                    </span>

                </div>
            </div>
        );
    }
}
