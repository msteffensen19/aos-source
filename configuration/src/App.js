import React, { Component } from 'react';
import { Container , Row, Col } from 'react-bootstrap';
import LoginForm from './landing-page/LoginForm.jsx';
import VideoFrames from './landing-page/Video.jsx';
import './App.css';
import NavigationBar from "./landing-page/NavigetionBar";
import {ReactComponent as AdvantageLogo} from "./Advantage_logo_white.svg"
import WhatsNewComponent from "./landing-page/WhatsNewComponent";
import './css-landing-page/navbar.css';

class App extends Component {

    render() {
        return (

            <Container className="main-container">
                <Row className="main-row-container">
                    <Col className="left-column" xs={10}>
                        <Container className="left-main-container">
                            <Row className="flex-sm-row top-container">
                                <Col xs={4} className="headers-container">
                                    <div className="icon-header-container">
                                    <AdvantageLogo className="fill-3"/><h1 className= "dvantage">dvantage</h1>
                                    </div>
                                    <div>
                                    <h1 className="management-console">Management Console</h1>
                                    </div>
                                </Col>
                                <Col xs={4} className="login-container" >

                                    <LoginForm></LoginForm>
                                </Col>
                                <Col xs={4} className="video-frame-container" /*Videos section*/>
                                    <VideoFrames></VideoFrames>
                                </Col>
                            </Row>
                            <Row className="left-bottom-container">
                                    <div className="main-left-bottom-container">
                                    <h3 className="what-s-new-in">What’s New In</h3>
                                    <h1 className="version-number">v2.0</h1>
                                    <button id="leftBtn"></button>
                                    <button id="rightBtn"></button>
                                    <WhatsNewComponent></WhatsNewComponent>
                                    </div>
                            </Row>
                        </Container>;
                    </Col>

                    <Col className="aos-nav-bar" xs={2}>
                        <NavigationBar></NavigationBar>
                    </Col>
                </Row>
            </Container>


        )

    }
}

export default App;