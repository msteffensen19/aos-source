import React from 'react';
import NavUserGuide from './NavUserGuide'
import NavDownloads from './NavDownloads'
import NavInstallation from './NavInstallation'

export default class NavigationBar extends React.Component {

    render() {
        return (
            <div className="nav-ul-container">
                <ul className="nav flex-column navigation-ul ie-modification">
                    <li className="nav-item ">
                        <NavUserGuide></NavUserGuide>
                    </li>
                    <li className="nav-item">
                      <NavDownloads></NavDownloads>
                    </li>
                    <li className="nav-item" style={{minHeight:"available"}}>
                        <NavInstallation></NavInstallation>
                    </li>
                </ul>
            </div>
        );
    }
}