import React from 'react';

export default class NavUserGuide extends React.Component {


    render() {

        return (
            <div>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">User Guides</p>
                <ul className="nav_user_guide_list">
                    <li>
                        <a>User Guides Name 1</a>
                    </li>
                    <li>
                        <a>User Guides Name 2</a>
                    </li>
                    <li>
                        <a>User Guides Name 3</a>
                    </li>
                    <li>
                        <a>User Guides Name 4</a>
                    </li>
                    <li>
                        <a>User Guides Name 5</a>
                    </li>
                    <li>
                        <a>User Guides Name 6</a>
                    </li>

                </ul>
            </div>

        );
    }
}