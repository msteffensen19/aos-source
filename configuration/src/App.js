import React, { Component } from 'react';
import { Container , Row, Col } from 'react-bootstrap';
import LoginForm from './landing-page/LoginForm.jsx';
import VideoFrames from './landing-page/Video.jsx';
import './App.css';
import NavigationBar from "./landing-page/NavigetionBar";


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
                            <Row className= "no-warp">
                                <Col /*left section*/>
                                    <i></i>
                                    <h1>left section </h1>
                                </Col>
                                <Col /*login section*/>
                                    <h1>login section</h1>
                                    <LoginForm></LoginForm>
                                </Col>
                                <Col /*Videos section*/>
                                    <h1>Right Section</h1>
                                    <VideoFrames></VideoFrames>
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