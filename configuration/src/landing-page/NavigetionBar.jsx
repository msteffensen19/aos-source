import React from 'react';
import NavUserGuide from './NavUserGuide'
import NavDownloads from './NavDownloads'
import NavInstallation from './NavInstallation'

export default class NavigationBar extends React.Component {

    render() {
        return (
            <ul className="nav flex-column navigation-ul">
                <li className="nav-item anyClass">
                    <NavUserGuide></NavUserGuide>
                </li>
                <li className="nav-item anyClass">
                  <NavDownloads></NavDownloads>
                </li>
                <li className="nav-item anyClass">
                    <NavInstallation></NavInstallation>
                </li>
            </ul>
        );
    }
}