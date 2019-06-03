import React, { Component } from 'react';
import './App.css';
import './css-landing-page/navbar.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import LandingPage from './landing-page/LandingPageHome.jsx';
import UnderConstruction from './landing-page/UnderConstruction';
class App extends Component {

    render() {
        return (
<Router basename='/admin'>
    <switch>
        <Route path="/" component={LandingPage} />
        <Route path="/coming-soon" render={() => <UnderConstruction />}/>
    </switch>

</Router>

        )

    }
}

export default App;