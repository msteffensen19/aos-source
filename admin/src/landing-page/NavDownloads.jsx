import React from 'react';
import {ReactComponent as AppleLogo} from "../svg-png-ext/AppleLogo.svg"
import {ReactComponent as AndroidLogo} from "../svg-png-ext/Android_logo.svg"


export default class NavDownloads extends React.Component {


    render() {

        return (

            <div className='nav-space-between'>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">Download Mobile Apps</p>

                        <div className="nav_download_wrapper">
                            <a className="devices nav_download_container" style={{float:'left' ,'marginBottom': '16px'}} href='https://s3.amazonaws.com/aos-on-prem-downloads/2.3/Advantage+demo+2.3.ipa'>
                                <div className="devices_pic">
                                    <AppleLogo></AppleLogo>
                                </div>
                                <div className="devices_inner_line">
                                    <span className="download_span">Download</span>
                                    <span className="os_span"><b>IOS</b></span>
                                </div>
                            </a>

                                <a className="devices nav_download_container" href='https://s3.amazonaws.com/aos-on-prem-downloads/2.3/Advantage+demo+2.3.apk'>
                                    <div className="devices_pic">
                                        <AndroidLogo></AndroidLogo>
                                    </div>
                                    <div className="devices_inner_line">
                                        <span className="download_span">Download</span>
                                        <span className="devices_font" style={{fontSize:'20px'}}><b>Android</b></span>
                                    </div>
                                </a>
                        </div>
            </div>

        );
    }
}