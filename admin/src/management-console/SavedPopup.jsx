import React from "react";
import {ReactComponent as UserIcon} from "../svg-png-ext/Advantage_logo_blck.svg";

export default class SavedPopup extends React.Component {


    componentDidMount(){
        setTimeout(this.props.closePopUp, 2000)
    }


    render() {
        return (
            <div id={"popupManagement"} className={"pop-up-cover"}>
                <div className={"pop-up-style"}>
                    <div className={"popup-image"}>
                        <UserIcon className={"svg-popup-style"}/>
                    </div>
                    {this.props.textForPopup?<span className="saved-popup-title">{this.props.textForPopup}</span>:
                        <span className="saved-popup-title">Saved Successfully!</span>}
                </div>
            </div>
        );
    }
}
