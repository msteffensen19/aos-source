import React from 'react';
import RestoreDBToDefault from "./RestoreDBToDefault";
import '../css-management-console/nav-management-css.css';
import {ReactComponent as AdvantageLogo} from "../svg-png-ext/Advantage-logo.svg";
import {withRouter} from 'react-router-dom';

class LeftNavBar extends React.Component {

    backToLandingPage =()=>{
        this.props.history.push('/');
    };

    moveToPage=()=>{
        this.props.history.push('/coming-soon');
    };

    render() {
        return (
            <div className="nav-management-container">
                <div className="navbar-advantage-icon">
                    <AdvantageLogo onClick={this.backToLandingPage} /><h1 onClick={this.backToLandingPage} className="nav-headline-logo">dvantage</h1>
                </div>
                <h3 className="navbar-headline-console">Management-Console</h3>
                <ul className="nav-ul-css">
                    <li>
                        <div className="item-product" onClick={this.moveToPage}>Products</div>
                    </li>
                    <li>
                        <div className="item-offer" onClick={this.moveToPage}>Special Offers</div>
                    </li>
                    <li>
                        <div className="item-popular" onClick={this.moveToPage}>Popular Items</div>
                    </li>
                    <li className="add-opacity" style={{marginLeft:'-40px'}}>
                        <div className="item-configuration">Configuration</div>
                    </li>
                    <li>
                        <div className="item-management" onClick={this.moveToPage}>User Management</div>
                    </li>
                </ul>
                <RestoreDBToDefault/>
            </div>
        );
    }
}
export default withRouter(LeftNavBar);