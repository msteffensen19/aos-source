import React, { Component } from 'react';
import './App.css';
import './css-landing-page/navbar.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import LandingPage from './landing-page/LandingPageHome.jsx';
import UnderConstruction from './landing-page/UnderConstruction';
class App extends Component {

    render() {
        return (
<Router>
        <Route exact path="/configuration" component={LandingPage} />
        <Route exact path="/coming-soon" component={UnderConstruction} />
</Router>

        )

    }
}

export default App;