
import React from 'react';
import 'jquery';
import 'jquery.soap';

import 'bootstrap';
import '../css-landing-page/login-form.css';
// import { Link } from 'react-router-dom';

//import { BrowserRouter as  Link } from 'react-router-dom';

export default class LoginForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {userName: '', password: '', rememberMe: false};

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {

        const { name, value } = event.target;
        this.setState({ [name]: value });

    }
    handleSubmit(event) {

        let user =
        {email: "",
        loginPassword: "adm1n",
        loginUser: "admin"};

        let $ = require('jquery');
        require('jquery.soap');
        $.soap({
            url: 'http://localhost:8080/accountservice/',
            method: 'AccountLoginRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountservice' + 'AccountLoginRequest' ,
            data: user,
            success: function (soapResponse) {
                let response = soapResponse;
                console.log(response);
                window.location="/coming-soon"

            },
            error: function (response) {
                console.log(response);
            },
            enableLogging: true
        });



        event.preventDefault();
    }

    render() {
        return (

            <form className= "login" onSubmit={this.handleSubmit}>
                <h1 className= "enter-details-to-log">Enter details to login</h1>
                <label className="margin-top-labels">
                    <h3 className= "user-name">User Name</h3>
                    <input name="userName" className="input-style" value={this.state.value} onChange={this.handleInputChange} />
                </label>
                <br />
                <label className="margin-top-labels">
                    <h3 className= "user-name">Password</h3>
                    {/*type="password"*/}
                    <input name="password" className="input-style" value={this.state.value} onChange={this.handleInputChange}/>
                </label>
                <label className="margin-top-labels remember-me-label">
                    <div className="remember-me-label">
                    <input
                        className="rectangle-remember-me"
                        type="checkbox"
                        onChange={this.handleInputChange} />
                    <h3 className= "remember-me">Remember me</h3>
                    </div>
                    <div className= "remember-me">Forget Password?</div>
                </label>
                    <input className="sign-in-btn" type="submit" value="Sign In" />
            </form>
        );
    }
}