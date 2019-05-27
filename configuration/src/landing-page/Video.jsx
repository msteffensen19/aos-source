import React from 'react';
import '../css-landing-page/landnig-video-frame.css';

export default class Contacts extends React.Component {

    render() {
        return (
            <div className="video-frame">
                <video width="212" height="166"
                    controls
                    //autoPlay
                    src="https://s3.amazonaws.com/codecademy-content/courses/React/react_video-fast.mp4" />
                <video width="212" height="166"
                       controls
                    //autoPlay
                       src="https://s3.amazonaws.com/codecademy-content/courses/React/react_video-fast.mp4" />
            </div>

        );
    }
}

