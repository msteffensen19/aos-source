import React from 'react';
import '../css-landing-page/landnig-video-frame.css';
import {ReactComponent as CoverPlay} from '../svg-png-ext/Play.svg';
import coverPicture1 from '../svg-png-ext/AOSCoverVideo1.jpg';
import coverPicture2 from'../svg-png-ext/Image_video2.jpg';

export default class Contacts extends React.Component {

    putControls(){
       document.getElementById("videoOneTag").setAttribute("controls", "controls");

    };
    removeControls(){
        document.getElementById("videoOneTag").removeAttribute("controls", "controls");
    };

    putControlsSecond(){
        document.getElementById("videoSecondTag").setAttribute("controls", "controls");

    };
    removeControlsSecond(){
        document.getElementById("videoSecondTag").removeAttribute("controls", "controls");
    };

    render() {
        return (
            <div className="video-frame">
<div className='wrapper-video-cover'>
                    <video width="260" height="190"
                        id="videoOneTag"
                        className='video-tag-css'
                        style={{marginBottom:'35px'}}
                        preload
                        onMouseEnter={this.putControls}
                        onMouseLeave={this.removeControls}
                        poster={coverPicture1}
                        //autoPlay
                        src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-e2e-flow.mp4"/>
                    <div>
                        <p className="video-tag-content">About AOS</p>
                        <CoverPlay className="video-cover-svg" />
                    </div>
</div>
                    <video width="260" height="190"
                           id="videoSecondTag"
                           className='video-tag-css'
                           preload
                           onMouseEnter={this.putControlsSecond}
                           onMouseLeave={this.removeControlsSecond}
                           poster={coverPicture2}
                           src="https://s3.amazonaws.com/aos-on-prem-downloads/video/aos-product-features.mp4" />
                    <div>
                        <p className="second-video-tag-content">End To End Case</p>
                        <CoverPlay className="second-video-cover-svg" />
                    </div>
            </div>

        );
    }
}
