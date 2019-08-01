import React from 'react';
import General from './General';


export default class Configuration extends React.Component {

    constructor() {
        super();
        this.state = { items: [] };
        this.genralItemsArray = [];
        this.functionalItemsArray = [];
        this.performanceItemsArray = [];
        this.mobileItemsArray = [];
        this.securityItemsArray = [];
        this.virtualiztionItemsArray = [];
    }

    componentDidMount() {
        fetch('http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL')
            .then(res => res.json())
            .then((data) => {
                this.setState({ items: data })
                this.state.items.forEach(function(item){

                });
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <ul>
                    <li>
                        <General></General>
                    </li>
                    <li className="nav-item">
                        <button>Second button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                    <li className="nav-item">
                        <button>Third button</button>
                    </li>
                </ul>
            </div>
        );
    }
}