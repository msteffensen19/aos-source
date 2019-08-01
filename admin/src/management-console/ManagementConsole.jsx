import React, { Component } from 'react';

import '../css-management-console/managment-main.css';
import LeftNavBar from './LeftNavBar';
import Configuration from './Configuration';


export default class LandingPageHome extends Component {

    backToAOS(){
        window.location.href = window.location.origin.toString();
    }

    render() {
        return (
            <section id="grid-container-main">
                <header></header>
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
