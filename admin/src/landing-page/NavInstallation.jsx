import React from 'react';
import {ReactComponent as MFLogo} from "../svg-png-ext/MicroFocus_logo_black.svg";

export default class NavInstallation extends React.Component {


    render() {

        return (
            <div className='nav-space-between'>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">Installation</p>
                <div className="nav_download_wrapper">
                    <div style={{fontSize:'15px'}}>If you want your own instance,</div>
                    <div style={{fontSize:'15px'}}>you can install it from here:</div>
                    <div onClick={ ()=> {window.open('https://marketplace.microfocus.com/appdelivery/content/advantage-online-shopping-aos-adm-demo-application')}  } className="devices nav_download_container" style={{float:'left' ,'margin-top': '20px'}}>
                        <div>
                            <div className="devices_pic">
                                <MFLogo></MFLogo>
                            </div>
                            <div className="devices_inner_line">
                                <span className="download_span">Get it From</span>
                                <span className="os_span"><b>ADM Market</b></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}