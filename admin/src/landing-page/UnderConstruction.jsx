import React from 'react';
import'../css-landing-page/coming-soon-css.css';
import {ReactComponent as AdvantageLogo} from "../Advantage_logo_white.svg";
import {ReactComponent as OvalSVG} from "../css-landing-page/Oval.svg";
import soonPng from "../css-landing-page/Group.png";
import comingPng from "../css-landing-page/Coming.png";
import {ReactComponent as LeftFlag} from "../css-landing-page/left-flag.svg";
import {ReactComponent as RightFlag} from "../css-landing-page/right-flag.svg";
import {ReactComponent as FacebookIcon} from "../css-landing-page/Facebook.svg";
import {ReactComponent as TwitterIcon} from "../css-landing-page/Twitter.svg";
import {ReactComponent as LinkedinIcon} from "../css-landing-page/LinkedinIcon.svg";


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
                window.open(window.location.origin.toString());
                break;

            case "backToManagement":
                window.open(window.location.origin.toString()+'/admin');
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
            // code block
        }
    };

    render() {
        return (
                 <div className="coming-soon regular-cursor">
                     <div className="advantage-header">
                         <AdvantageLogo className="soon-advantage-icon"/><h1 className= "dvantage more-size">dvantage</h1>
                     </div>
                    <OvalSVG className="prevent-pointer" />
                     <div className="images-container-comingSoon">
                         <div>
                             <img className="coming-soon-images coming-position regular-cursor" src={comingPng} alt="coming png"></img>
                         </div>
                         <div>
                            <img className="coming-soon-images soon-position regular-cursor" src={soonPng} alt="soon png"></img>
                         </div>
                     </div>
                         <div className="soon-title regular-cursor">
                             <RightFlag />
                                  <span className="regular-cursor">We are still working on it</span>
                             <LeftFlag />
                         </div>
                     <div className="back-buttons-container">
                             <input id="backToAOS" onClick={this.redirectToLinks} className="sign-in-btn button-modification" value="Back To AOS" />
                             <button id="backToManagement" onClick={this.redirectToLinks} className="sign-in-btn button-modification left-space">
                                 {/*Back To*/}
                                 {/*<br/>*/}
                                 Management Console
                             </button>
                     </div>
                     <div className="social-links-container">
                            <FacebookIcon id="facebookIcon" onClick={this.redirectToLinks}/>
                            <TwitterIcon id="twitterIcon" onClick={this.redirectToLinks} className="twitter-icon"/>
                            <LinkedinIcon id="linkedinIcon" onClick={this.redirectToLinks} className="linkedin-icon"/>
                     </div>
                 </div>
        );
    }
}

