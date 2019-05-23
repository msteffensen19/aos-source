import React, { Component } from 'react';
import construction from './Under_construction.png';
import './App.css';


function ListItem(props) {

    return <li>{props.value}</li>;
}

function NumberList(props) {
    const numbers = [1, 2, 3, 4, 5];
    const listItems = numbers.map((number) =>
        // Correct! Key should be specified inside the array.
        <ListItem key={number.toString()}
                  value={number} />

    );
    return (
        <ul>
            {listItems}
        </ul>
    );
}
class App extends Component {

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={construction} alt="logo" />
                    {/*<h2>Welcome to React</h2>*/}
                </div>
                <NumberList />
                    {/*<BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>*/}
                    {/*<div>*/}
                        {/*<ul className="nav">*/}
                            {/*<li><Link to="/">Homepage</Link></li>*/}
                            {/*<li><Link to="/blog">Blog Posts</Link></li>*/}
                            {/*<li><Link to="/about">About Us</Link></li>*/}
                        {/*</ul>*/}
                        {/*<Switch>*/}
                            {/*<Route path="/blog" component={BlogScreen}/>*/}
                            {/*<Route path="/about" component={AboutScreen}/>*/}
                            {/*<Route path="/" component={HomeScreen}/>*/}
                        {/*</Switch>*/}
                    {/*</div>*/}
                {/*</BrowserRouter>*/}
            </div>
        );
    }
}

export default App;