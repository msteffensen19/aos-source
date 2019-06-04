import React from 'react';
import '../css-landing-page/landnig-video-frame.css';
import coverPicture1 from'../svg-png-ext/Image_video1.png';
import coverPicture2 from'../svg-png-ext/Image_video2.png';

export default class Contacts extends React.Component {

    render() {
        return (
            <div className="video-frame">
                <video width="212" height="166"
                    controls
                    poster={coverPicture1}
                    //autoPlay
                    src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-e2e-flow.mp4" />
                <video width="212" height="166"
                       controls
                       poster={coverPicture2}
                    //autoPlay
                       src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-product-features.mp4" />
            </div>

        );
    }
}

