import React from 'react';

export default class Contacts extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                <video width="320" height="240"
                    controls
                    //autoPlay
                    src="https://s3.amazonaws.com/codecademy-content/courses/React/react_video-fast.mp4" />
            </div>
        );
    }
}

