import React from 'react';

export default class NavUserGuide extends React.Component {


    render() {

        return (
            <div style={{paddingTop:'45px'}}>
                <div className="nav-spacer"></div>
                <p className="aos-nav-headline">User Guides</p>
                <ul className="nav_user_guide_list">
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/0_Before_You_Begin.pdf" target='_blank' rel='noopener noreferrer'>Before you begin</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/1_Arch_and_Environments.pdf" target='_blank' rel='noopener noreferrer'>Architecture and environments</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/2_AOS_Front_End.pdf" target='_blank' rel='noopener noreferrer'>AOS front end</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/3_AOS_Back_End.pdf" target='_blank' rel='noopener noreferrer'>AOS back end</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/4_AOS_Mgt_Console.pdf" target='_blank' rel='noopener noreferrer'>AOS management console</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/5_How_To_Get_AOS.pdf" target='_blank' rel='noopener noreferrer'>How to get AOS</a>
                    </li>
                    <li>
                        <a href="https://s3.amazonaws.com/aos-on-prem-downloads/doc/6_Troubleshooting.pdf" target='_blank' rel='noopener noreferrer'>Troubleshooting</a>
                    </li>

                </ul>
            </div>

        );
    }
}