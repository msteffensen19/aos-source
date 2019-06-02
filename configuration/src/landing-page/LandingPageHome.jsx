import React, { Component } from 'react';
import { Container , Row, Col } from 'react-bootstrap';
import LoginForm from './LoginForm.jsx';
import VideoFrames from './Video.jsx';
import '../App.css';
import NavigationBar from "./NavigetionBar";
import {ReactComponent as AdvantageLogo} from "../Advantage_logo_white.svg"
import {ReactComponent as ArrowIconLeft} from "../css-landing-page/Arrow_Arrow_left.svg"
import {ReactComponent as ArrowIconRight} from "../css-landing-page/Arrow_Arrow_right.svg"
import WhatsNewComponent from "./WhatsNewComponent";
import '../css-landing-page/navbar.css';


export default class LandingPageHome extends Component {

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
                                        <div className="scroll-btn-container">
                                            <ArrowIconLeft className="fill-arrows" id="leftBtn"></ArrowIconLeft>
                                            <ArrowIconRight className="fill-arrows" id="rightBtn"></ArrowIconRight>
                                        </div>
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
