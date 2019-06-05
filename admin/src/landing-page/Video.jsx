import React from 'react';
import '../css-landing-page/landnig-video-frame.css';
import coverPicture1 from '../svg-png-ext/AOSCoverVideo1.jpg';
import coverPicture2 from'../svg-png-ext/Image_video2.jpg';
import any from 'jquery';

export default class Contacts extends React.Component {

    putControls(){

       document.getElementById("videoOneTag").setAttribute("controls", "controls");

    };
    removeControls(){
        document.getElementById("videoOneTag").removeAttribute("controls", "controls");
    };

    render() {
        return (
            <div className="video-frame">

                    <video width="320" height="200"
                        id="videoOneTag"
                        style={{marginBottom:'35px'}}
                        preload
                           onMouseEnter={this.putControls}
                           onMouseLeave={this.removeControls}
                        poster={coverPicture1}
                        //autoPlay
                        src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-e2e-flow.mp4" />
                    <video width="320" height="200"
                           preload
                           controls
                           poster={coverPicture2}
                        //autoPlay
                           src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-product-features.mp4" />
            </div>

        );
    }
}

