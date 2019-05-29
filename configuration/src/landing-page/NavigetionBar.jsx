import React from 'react';
import { Navbar } from 'react-bootstrap';
import NavUserGuide from './NavUserGuide'
import NavDownloads from './NavDownloads'
import NavInstallation from './NavInstallation'

export default class NavigationBar extends React.Component {

    render() {
        return (
            <ul className="nav flex-column ">
                <li className="nav-item">
                    <NavUserGuide></NavUserGuide>
                </li>
                <li className="nav-item">
                  <NavDownloads></NavDownloads>
                </li>
                <li className="nav-item">
                    <NavInstallation></NavInstallation>
                </li>
            </ul>
        );
    }
}