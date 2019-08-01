import React from 'react';

export default class LeftNavBar extends React.Component {

    render() {
        return (
            <div className="nav-ul-container">
                <ul className="nav flex-column navigation-ul">
                    <li className="nav-item ">
                        <button>First button</button>
                    </li>
                    <li className="nav-item">
                        <button>Second button</button>
                    </li>
                    <li className="nav-item" style={{minHeight: "available"}}>
                        <button>Third button</button>
                    </li>
                </ul>
            </div>
        );
    }
}