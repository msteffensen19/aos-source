import React from 'react';
import RestoreDBToDefault from "./RestoreDBToDefault";
import '../css-management-console/nav-management-css.css';
import {ReactComponent as AdvantageLogo} from "../svg-png-ext/Advantage-logo.svg";
import {ReactComponent as PopularItemsLogo} from "../svg-png-ext/Menu_Popular_Items.svg";
import {withRouter} from 'react-router-dom';

class LeftNavBar extends React.Component {

    constructor(props) {
        super(props);
        this.moveToPage = this.moveToPage.bind(this);
    }

    moveToPage(){

        let me = this;
        me.props.history.push('/coming-soon');
    }

    render() {
        return (
            <div className="nav-management-container">
                <div className="navbar-advantage-icon">
                    <AdvantageLogo className="nav-logo-css"/><h1 className="nav-headline-logo">dvantage</h1>
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
                    <li>
                        <div className="item-configuration" onClick={this.moveToPage}>Configuration</div>
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