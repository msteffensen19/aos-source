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
                            <div onClick={ ()=> {window.location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+2_0.ipa'}  } class="devices nav_download_container" style={{float:'left' ,'margin-bottom': '20px'}} >
                                <div class="devices_pic">
                                    <AppleLogo></AppleLogo>
                                </div>
                                <div class="devices_inner_line">
                                    <span className="download_span">Download</span>
                                    <span className="os_span"><b>IOS</b></span>
                                </div>
                            </div>

                                <div onClick={ ()=> {window.location.href='https://s3.amazonaws.com/aos-on-prem-downloads/1.1.6/Advantage+demo+2_0.ipa'}  } class="devices nav_download_container" >
                                    <div class="devices_pic">
                                        <AndroidLogo></AndroidLogo>
                                    </div>
                                    <div class="devices_inner_line">
                                        <span className="download_span">Download</span>
                                        <span className="os_span"><b>Android</b></span>
                                    </div>
                                </div>
                        </div>
            </div>

        );
    }
}