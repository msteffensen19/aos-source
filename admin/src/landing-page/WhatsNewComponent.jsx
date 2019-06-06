import React from 'react';
import WhatIsNewJson from './WhatIsNew.json';
import '../css-landing-page/whats-new.css';


export default class WhatsNewComponent extends React.Component {

    constructor(props) {
        super(props);
        let counter =0;
        let whatsNewString = JSON.stringify(WhatIsNewJson)
        let whatsNewObj = JSON.parse(whatsNewString);
        //split array into 2 arrays.
        let whatsNewObj1 = whatsNewObj.slice(0,whatsNewObj.length/2);
        let whatsNewObj2 = whatsNewObj.slice(whatsNewObj.length/2, whatsNewObj.length);
        this.content1 = whatsNewObj1.map((whatsNew) =>
            <li className="list-inline-item make-scroll-li" key={whatsNew.title}>
                <h3 className="title-style">{whatsNew.title}</h3>
                <div>
                <p className="content-style">{whatsNew.content}</p>
                </div>
            </li>
        );
        this.content2 = whatsNewObj2.map((whatsNew) =>
            <li className="list-inline-item make-scroll-li" key={whatsNew.title}>
                <h3 className="title-style">{whatsNew.title}</h3>
                <p className="content-style">{whatsNew.content}</p>
            </li>
        );
    }


    componentDidMount() {
        document.getElementById('leftBtn').onclick = function () {
            scrollLeft(document.getElementById('content1'), -300, 1000);
            scrollLeft(document.getElementById('content2'), -300, 1000);
            this.counter ++;
        };

        document.getElementById('rightBtn').onclick = function () {
            scrollLeft(document.getElementById('content1'), 300, 1000);
            scrollLeft(document.getElementById('content2'), 300, 1000);
            this.counter --;
        };
        function scrollLeft(element, change, duration) {
            var start = element.scrollLeft,
                currentTime = 0,
                increment = 20;

            var animateScroll = function(){
                currentTime += increment;
                var val = Math.easeInOutQuad(currentTime, start, change, duration);
                element.scrollLeft = val;
                if(currentTime < duration) {
                    setTimeout(animateScroll, increment);
                }
            };
            animateScroll();
        }
//t = current time
//b = start value
//c = change in value
//d = duration
        Math.easeInOutQuad = function (t, b, c, d) {
            t /= d/2;
            if (t < 1) return c/2*t*t + b;
            t--;
            return -c/2 * (t*(t-2) - 1) + b;
        };


    }


    render() {
        return (
<div>
            <ul id="content1" className="list-inline make-scroll-ul">{this.content1}</ul>
    <br/>
            <ul id="content2" className="list-inline make-scroll-ul">{this.content2}</ul>
</div>
        );
    }
}
