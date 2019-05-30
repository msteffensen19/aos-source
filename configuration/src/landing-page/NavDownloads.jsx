import React from 'react';
import {ReactComponent as AppleLogo} from "../css-landing-page/AppleLogo.svg"
import {ReactComponent as AndroidLogo} from "../css-landing-page/Android_logo.svg"


export default class NavDownloads extends React.Component {


    render() {

        return (

            <div style={{'paddingTop':"50px"}}>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">Download Mobile Apps</p>

                        <div className="nav_download_wrapper">
                            <div className="devices nav_download_container" style={{float:'left' ,'marginBottom': '5px'}} onClick="location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+1_1_6.ipa';">
                                <div className="devices_pic">
                                    <AppleLogo></AppleLogo>
                                </div>
                                <div className="devices_inner_line">
                                    <span className="download_span">Download</span>
                                    <span className="os_span"><b>IOS</b></span>
                                </div>
                            </div>

                                <div className="devices nav_download_container" onClick="location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+1_1_6.apk';">
                                    <div className="devices_pic">
                                        <AndroidLogo></AndroidLogo>
                                    </div>
                                    <div className="devices_inner_line">
                                        <span>Download</span>
                                        <span className="devices_font"><b>Android</b></span>
                                    </div>
                                </div>
                        </div>
            </div>

        );
    }
}