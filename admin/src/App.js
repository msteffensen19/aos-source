import React, { Component } from 'react';
import './App.css';
import './css-landing-page/navbar.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import LandingPage from './landing-page/LandingPageHome.jsx';
import UnderConstruction from './landing-page/UnderConstruction';
import ManagementConsole from './management-console/ManagementConsole';
import ContextProvider from './landing-page/ContextProvider';



class App extends Component {

        detectMobile =()=> {

            return window.innerWidth < 1024;
        };

    render() {
        return (
            <ContextProvider>
                <Router basename='/admin' forceRefresh={false}>
                    {this.detectMobile() ?
                        <Route exact path="/management-console" component={UnderConstruction}/> :
                        <Route path="/management-console" component={ManagementConsole}/>
                    }
                        <Route  path="/coming-soon" component={UnderConstruction}/>
                    {this.detectMobile()?
                        <Route exact path="/" component={UnderConstruction}/>:
                        <Route exact path="/" component={LandingPage}/>
                    }
                </Router>
            </ContextProvider>
        )
    }
}

export default App;