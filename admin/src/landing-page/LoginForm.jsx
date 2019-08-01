
import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';
import '../css-landing-page/login-form.css';
import {withRouter} from 'react-router-dom';



class LoginForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {userName: '', password: '', rememberMe: false, open:false};

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    handleInputChange(event) {

        switch (event.target.name) {
            case "userName":
                this.setState({userName: event.target.value});
                break;

            case "password":
                this.setState({password: event.target.value});
                break;

            case "rememberMe" :
                this.setState({rememberMe: event.target.value});
                break;
            default:
                break;
        }

    }
    handleSubmit(event) {

        let user =
        {email: "",
        loginPassword: this.state.password,
        loginUser: this.state.userName};

        let host = window.location.origin;
        let $ = require('jquery');
        require('jquery.soap');
        var parseString = require('jquery.soap');
        let me = this;
        $.soap({
            url: 'http://localhost:8080/accountservice/', //host+'/accountservice/',
            method: 'AccountLoginRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountserviceAccountLoginRequest' ,
            data: user,
            success: function (soapResponse) {
                let response = parseString(soapResponse);
                console.log(response);
                me.props.history.push('/management-console');

            },
            error: function (response) {
                console.log(response);
                me.props.history.push('/management-console');
                // me.setState({ open: true });
            },
            enableLogging: true
        });

        event.preventDefault();

    }
    closeModal () {
        this.setState({ open: false })
    }

    render(){
        return (

            <form  className= "login" onSubmit={this.handleSubmit}>
                <h1 className= "enter-details-to-log">Enter details to login</h1>
                <label className="margin-top-labels">
                    {/*<h3 className= "user-name">User Name</h3>*/}
                    <input placeholder="User Name" name="userName" className="input-style" value={this.state.value} onChange={this.handleInputChange} />
                </label>
                <br />
                <label className="margin-top-labels">
                    {/*<h3 className= "user-name">Password</h3>*/}
                    {/*type="password"*/}
                    <input placeholder="Password" name="password" className="input-style" value={this.state.value} onChange={this.handleInputChange}/>
                </label>
                <label className="margin-top-labels remember-me-label">
                    <div className="remember-me-label">
                    <input
                        type="checkbox"
                        className="rectangle-remember-me"
                        name="rememberMe"
                        onChange={this.handleInputChange} />
                    <h3 className= "remember-me">Remember me</h3>
                    </div>
                    <div className= "remember-me">Forget Password?</div>
                </label>
                <label style={{marginTop:"20px"}}>
                    <input className="sign-in-btn" type="submit" value="Sign In" />
                </label>
            </form>
        );
    }
}
export default withRouter(LoginForm);
