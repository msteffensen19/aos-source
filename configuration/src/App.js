import React, { Component } from 'react';
import { Container , Row, Col } from 'react-bootstrap';
import LoginForm from './landing-page/LoginForm.jsx';
import VideoFrame from './landing-page/Videos.jsx';
import './App.css';
import NavigationBar from "./landing-page/NavigetionBar";


function ListItem(props) {

    return <li>{props.value}</li>;
}

function NumberList(props) {
    const numbers = [10, 77, 3, 4, 5];
    const listItems = numbers.map((number) =>
        // Correct! Key should be specified inside the array.
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
            <Container className="App">
                    <Col sm={8}>
                        <div /*upper section*/>
                            <div /*left section*/>
                                <i></i>
                                <h1></h1>
                            </div>
                            <div /*login section*/>
                                <h1></h1>
                                <LoginForm></LoginForm>
                            </div>
                            <div /*right     section*/>
                                <VideoFrame></VideoFrame>
                                <VideoFrame></VideoFrame>
                            </div>
                        </div>
                        <NumberList />
                    </Col>
                <Col sm={4}>
                    <NavigationBar className="other"></NavigationBar>
                </Col>

            </Container>
        );
    }
}

export default App;