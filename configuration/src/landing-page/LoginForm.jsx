
import React from 'react';
import {Cookies} from 'react-cookie';

const [cookies, setCookie, removeCookie] = Cookies(['cookie-name']);

export default class LoginForm extends React.Component {
    constructor(props) {
        super(props);

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {

    }
    handleSubmit(event) {
        alert('Cookie value: '+ cookies('admin') );
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username:
                    <input/>
                </label>
                <br />
                <label>
                    password:
                    <input/>
                </label>
                <label>
                    Is going:
                    <input
                        type="checkbox"
                        onChange={this.handleInputChange} />
                </label>
                    <input type="submit" value="Submit" />
            </form>
        );
    }
}