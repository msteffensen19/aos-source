import React, { Component } from 'react';
import { Container , Row, Col } from 'react-bootstrap';
import LoginForm from './landing-page/LoginForm.jsx';
import VideoFrames from './landing-page/Video.jsx';
import './App.css';
import NavigationBar from "./landing-page/NavigetionBar";
import {ReactComponent as AdvantageLogo} from "./Advantage_logo_white.svg"


function ListItem(props) {

    return <li>{props.value}</li>;
}

function NumberList(props) {
    const numbers = [10, 77, 3, 4, 5];
    const listItems = numbers.map((number) =>

        <ListItem key={number.toString()}
                  value={number} />

    );
    return (
        <ul>
            {listItems}
        </ul>
    );
}
class App extends Component {

    render() {
        return (

            <Container className="no-max-width">
                <Row>
                    <Col xs={8} sm={8} md={8} lg={8}>
                        <Container>
                            <Row className="no-wrap">
                                <Col /*left section*/>
                                    <div className="icon-header-container">
                                    <AdvantageLogo className="fill-3"/><h1 className= "dvantage">dvantage</h1>
                                    </div>
                                    <h1 className="management-console">Management Console</h1>

                                </Col>
                                <Col /*login section*/>

                                    <LoginForm></LoginForm>
                                </Col>
                                <Col /*Videos section*/>
                                    <VideoFrames></VideoFrames>
                                </Col>
                            </Row>
                            <Row/>
                            <i>Advantage icon</i>
                            <h1>What's New </h1>
                                <NumberList></NumberList>
                        </Container>;
                    </Col>

                    <Col /*NavBar section*/>
                        <i></i>
                        <h1>Navigation-Bar </h1>
                        <NavigationBar></NavigationBar>
                    </Col>
                </Row>
            </Container>


        )

    }
}

export default App;