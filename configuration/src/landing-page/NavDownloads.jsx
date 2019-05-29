import React from 'react';
import {ReactComponent as AppleLogo} from "../css-landing-page/AppleLogo.svg"
import {ReactComponent as AndroidLogo} from "../css-landing-page/Android_logo.svg"


export default class NavDownloads extends React.Component {


    render() {

        return (

            <div style={{'padding-top':"50px"}}>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">Download Mobile Apps</p>

                        <div class="nav_download_wrapper">
                            <div class="devices nav_download_container" style={{float:'left' ,'margin-bottom': '5px'}} onclick="location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+1_1_6.ipa';">
                                <div class="devices_pic">
                                    <AppleLogo></AppleLogo>
                                </div>
                                <div class="devices_inner_line">
                                    <span className="download_span">Download</span>
                                    <span className="os_span"><b>IOS</b></span>
                                </div>
                            </div>

                                <div class="devices nav_download_container" onclick="location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+1_1_6.apk';">
                                    <div class="devices_pic">
                                        <AndroidLogo></AndroidLogo>
                                    </div>
                                    <div class="devices_inner_line">
                                        <span>Download</span>
                                        <span class="devices_font"><b>Android</b></span>
                                    </div>
                                </div>
                        </div>
            </div>

        );
    }
}