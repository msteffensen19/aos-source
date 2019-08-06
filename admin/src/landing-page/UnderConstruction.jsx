import React from 'react';
import'../css-landing-page/coming-soon-css.css';
import {ReactComponent as AdvantageLogo} from "../Advantage_logo_white.svg";
import soonPng from "../svg-png-ext/Group.png";
import comingPng from "../svg-png-ext/Coming.png";
import {ReactComponent as LeftFlag} from "../svg-png-ext/left-flag.svg";
import {ReactComponent as RightFlag} from "../svg-png-ext/right-flag.svg";
import {ReactComponent as FacebookIcon} from "../svg-png-ext/Facebook.svg";
import {ReactComponent as TwitterIcon} from "../svg-png-ext/Twitter.svg";
import {ReactComponent as LinkedinIcon} from "../svg-png-ext/LinkedinIcon.svg";


export default class UnderConstruction extends React.Component {

    goBack (){
        let tempUrl = window.location.origin.toString();
        window.location.href = tempUrl+'/admin';
    };
    goHome(){
        let tempUrl = window.location.origin.toString();
        window.location.href = tempUrl;
    };
    redirectToLinks(event){
        switch (event.target.id) {
            case "backToAOS":
                window.location.href = window.location.origin.toString();
                break;

            case "backToManagement":
                window.location.href = window.location.origin.toString()+'/admin';
                break;

            case "Fill-1" || "facebookIcon" :
                window.open("https://www.facebook.com/MicroFocus/", '_blank');
                break;

            case "twitterIcon":
                window.open("https://twitter.com/MicroFocus", '_blank');
                break;

            case "linkedinIcon":
                window.open("https://www.linkedin.com/showcase/micro-focus-software/", '_blank');
                break;

            default:
                window.location.href = window.location.origin.toString();

        }
    };

    render() {
        return (
                 <div className="coming-soon regular-cursor">
                     <div className="advantage-header">
                         <AdvantageLogo onClick={this.redirectToLinks} className="soon-advantage-icon pointer-cursor"/><h1 onClick={this.redirectToLinks} className= "dvantage more-size pointer-cursor">dvantage</h1>
                     </div >
                     <ul className="middle-container-soon">
                         <li className="li-space images-container-comingSoon">
                             <div>
                                 <img className="image-size regular-cursor" src={comingPng} alt="coming png"></img>
                             </div>
                             <div>
                                <img className="image-size soon-position regular-cursor" src={soonPng} alt="soon png"></img>
                             </div>
                         </li>
                         <li className="li-space soon-title">
                             <RightFlag />
                                  <span>We are still working on it</span>
                             <LeftFlag />
                         </li>
                         <li className="coming-soon-btn-container">
                                 <input id="backToAOS" onClick={this.redirectToLinks} className="coming-soon-buttons pointer-cursor" style={{marginBottom:"30px"}} defaultValue="Back To AOS" />
                                 <button id="backToManagement" onClick={this.redirectToLinks} className="coming-soon-buttons">
                                     Management Console
                                 </button>
                         </li>
                         <li className="li-space social-links-container">
                             <div className="social-icon">
                                <FacebookIcon id="facebookIcon" onClick={this.redirectToLinks} />
                             </div>
                             <div className="social-icon">
                                <TwitterIcon id="twitterIcon" onClick={this.redirectToLinks}/>
                             </div>
                             <div className="social-icon">
                                <LinkedinIcon id="linkedinIcon" onClick={this.redirectToLinks} className="linkedin-icon"/>
                             </div>
                         </li>
                     </ul>
                   </div>
        );
    }
}

