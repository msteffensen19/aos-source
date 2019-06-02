import React from 'react';
import'../css-landing-page/coming-soon-css.css';
import { Link } from 'react-router-dom';
// noinspection ES6CheckImport
import {ReactComponent as AdvantageLogo} from "../Advantage_logo_white.svg"
import {ReactComponent as OvalSVG} from "../css-landing-page/Oval.svg"

export default class UnderConstruction extends React.Component {

    render() {
        return (
            <Link to="/about">
                 <div className="coming-soon">
                     <div className="advantage-header">
                         <AdvantageLogo className="fill-3"/><h1 className= "dvantage">dvantage</h1>
                     </div>

                    <OvalSVG />

                 </div>
            </Link>
        );
    }
}