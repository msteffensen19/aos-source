import React from 'react';
import {ReactComponent as AppleLogo} from "../css-landing-page/AppleLogo.svg";

export default class NavInstallation extends React.Component {


    render() {

        return (
            <div style={{'padding-top':"50px"}}>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">Installation</p>
                <div className="nav_download_wrapper">
                    <div>If you want your own instance,</div>
                    <div>you can install it from here:</div>
                    <div className="devices nav_download_container" style={{float:'left' ,'margin-top': '15px'}} onClick="location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+1_1_6.ipa';">
                        <div className="devices_pic">
                            <AppleLogo></AppleLogo>
                        </div>
                        <div className="devices_inner_line">
                            <span className="download_span">Download</span>
                            <span className="os_span"><b>IOS</b></span>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}