import React, { Component } from 'react';
import '../css-management-console/managment-main.css';
import LeftNavBar from './LeftNavBar';
import Configuration from './Configuration';
import HeaderDiv from './HeaderDiv';
import Popup from'./PopupWindow';


export default class LandingPageHome extends Component {

    state = {openPopUp:false};

    handleClick =()=>{
        this.setState({openPopUp:true});
    };

    closePopUp =()=>{

        this.setState({openPopUp:false});
    };

    render() {
        return (
            <section id="grid-container-main">
                {this.state.openPopUp?<Popup closePopUp = {this.closePopUp}/>:null}
                <header className="configurationHeader">
                    <HeaderDiv handleLogoutClick ={this.handleClick}/>
                </header>
                <main>
                    <Configuration></Configuration>
                </main>
                <nav>
                    <LeftNavBar/>
                </nav>
                <footer></footer>
            </section>
        )
    }
}
