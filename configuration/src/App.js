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
                    <Col className="left-column" xs={8} sm={8} md={8} lg={8}>
                        <Container className="left-main-container">
                            <Row className="top-container">
                                <Col className="headers-container" /*left section*/>
                                    <div className="icon-header-container">
                                    <AdvantageLogo className="fill-3"/><h1 className= "dvantage">dvantage</h1>
                                    </div>
                                    <div>
                                    <h1 className="management-console">Management Console</h1>
                                    </div>
                                </Col>
                                <Col className="login-container" >

                                    <LoginForm></LoginForm>
                                </Col>
                                <Col className="video-frame-container" /*Videos section*/>
                                    <VideoFrames></VideoFrames>
                                </Col>
                            </Row>
                            <Row className="left-bottom-container">
                                    <div className="main-left-bottom-container">
                                    <h3 className="what-s-new-in">What’s New In</h3>
                                    <h1 className="version-number">v2.0</h1>
                                    <i>Advantage icon</i>
                                        <WhatsNewComponent></WhatsNewComponent>
                                    </div>
                            </Row>
                        </Container>;
                    </Col>

                    <Col className="aos-nav-bar ">
                        <NavigationBar></NavigationBar>
                    </Col>
                </Row>
            </Container>


        )

    }
}

export default App;