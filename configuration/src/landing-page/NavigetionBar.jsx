import React from 'react';
import { Navbar } from 'react-bootstrap';
import NavUserGuide from './NavUserGuide'

export default class NavigationBar extends React.Component {

    render() {
        return (
            <Navbar bg="light">
                <Navbar.Brand href="#home">Brand link</Navbar.Brand>
                <NavUserGuide></NavUserGuide>
            <br />
    <Navbar bg="light">
        <Navbar.Brand>Brand text</Navbar.Brand>
    </Navbar>
        <br />
        <Navbar bg="dark">
            <Navbar.Brand href="#home">

                <img
                    src="logo.svg"
                    width="30"
                    height="30"
                    className="d-inline-block align-top"
                    alt="React Bootstrap logo"
                />
            </Navbar.Brand>
            </Navbar>
            <br />
            <Navbar bg="dark" variant="dark">
            <Navbar.Brand href="#home">
            <img
            alt=""
            src="logo.svg"
            width="30"
            height="30"
            className="d-inline-block align-top"
            />
            {' React Bootstrap'}
        </Navbar.Brand>
    </Navbar>
            </Navbar>
        );
    }
}