import React from 'react';
import '../css-landing-page/landnig-video-frame.css';
import {ReactComponent as CoverPlay} from '../svg-png-ext/Play.svg';


export default class Contacts extends React.Component {

    constructor(props) {
        super(props);

        this.videoPlaceholder = React.createRef();
        this.secondVideoPlaceholder = React.createRef();
        this.playFirstVideo = this.playFirstVideo.bind(this);
        this.playSecondVideo = this.playSecondVideo.bind(this);

    }
    playFirstVideo(){
        this.videoPlaceholder.current.innerHTML ='<iframe\n' +
            '                    width="260" height="190" ' +
            'src="https://www.youtube.com/embed/A_mbo8pzQVE?autoplay=1" frameborder="0" allowfullscreen allow="autoplay"></iframe>';
        document.getElementById("videoCoverElements").setAttribute("class", "none-visible");
    }
    playSecondVideo(){
        this.secondVideoPlaceholder.current.innerHTML ='<iframe\n' +
            '                    width="260" height="190" ' +
            'src="https://www.youtube.com/embed/QVqk-GNbJSw?autoplay=1" frameborder="0" allowfullscreen allow="autoplay"></iframe>';
        document.getElementById("secondVideoCoverCss").setAttribute("class", "none-visible");
    }

    render() {

        return (
            <div className="video-frame">
                <div>
                    <div className="video-placeholder"
                         ref={this.videoPlaceholder}
                         onClick={this.playFirstVideo}/>
                </div>
                    <div id="videoCoverElements">
                        <p className="video-tag-content">About AOS</p>
                        <CoverPlay className="video-cover-svg" />
                    </div>

                <div style={{marginTop:"30px"}}>
                    <div className="second-video-placeholder"
                         ref={this.secondVideoPlaceholder}
                         onClick={this.playSecondVideo}/>
                </div>
                    <div id="secondVideoCoverCss">
                        <p className="second-video-tag-content">End To End Case</p>
                        <CoverPlay className="second-video-cover-svg" />
                    </div>
            </div>

        );
    }
}
