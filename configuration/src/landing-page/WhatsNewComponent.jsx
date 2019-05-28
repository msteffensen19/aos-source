import React from 'react';
import WhatIsNewJson from './WhatIsNew.json';
import '../css-landing-page/whats-new.css';


export default class WhatsNewComponent extends React.Component {

    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);

        let whatsNewString = JSON.stringify(WhatIsNewJson)
        let whatsNewObj = JSON.parse(whatsNewString);
        //split array into 2 arrays.
        let whatsNewObj1 = whatsNewObj.slice(0,whatsNewObj.length/2);
        let whatsNewObj2 = whatsNewObj.slice(whatsNewObj.length/2, whatsNewObj.length);
        this.content = whatsNewObj.map((whatsNew) =>
            <li className="list-inline-item make-scroll-li" key={whatsNew.title}>
                <h3 className="title-style">{whatsNew.title}</h3>
                <p className="content-style">{whatsNew.content}</p>
            </li>
        );
    }
    handleClick() {
        console.log("any");
        window.scrollBy(100, 0);
    }

    componentDidMount() {
        document.getElementById('leftBtn').onclick = function () {
            scrollLeft(document.getElementById('content'), -300, 1000);
        }

        document.getElementById('rightBtn').onclick = function () {
            scrollLeft(document.getElementById('content'), 300, 1000);
        }
        function scrollLeft(element, change, duration) {
            var start = element.scrollLeft,
                currentTime = 0,
                increment = 20;

            console.log(start)

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
    <button id="leftBtn"></button>
    <br/>
    <button id="rightBtn"></button>
            <ul id="content" className="list-inline make-scroll-ul">{this.content}</ul>
</div>
        );
    }
}
