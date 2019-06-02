import React from 'react';
import'../css-landing-page/coming-soon-css.css';
import { Link } from 'react-router-dom';
// noinspection ES6CheckImport
import {ReactComponent as AdvantageLogo} from "../Advantage_logo_white.svg"
import {ReactComponent as OvalSVG} from "../css-landing-page/Oval.svg"
// import {ReactComponent as SoonSVG} from "../css-landing-page/Group.png"
// import {ReactComponent as ComingSVG} from "../css-landing-page/Coming.png"

export default class UnderConstruction extends React.Component {

    render() {
        return (
            <Link to="/about">
                 <div className="coming-soon">
                     <div className="advantage-header">
                         <AdvantageLogo className="fill-3"/><h1 className= "dvantage">dvantage</h1>
                     </div>
                    <OvalSVG />
                     <img src="Coming.png" alt="Italian asdfa"></img>

                     <img src="../css-landing-page/Group.png" alt="Italian Trulli"></img>

                 </div>
            </Link>
        );
    }
}