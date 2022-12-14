
import React from 'react';
import 'jquery';
import 'jquery.soap';
import 'bootstrap';
import  xml2jsonImpl from './Parser';
import '../css-landing-page/login-form.css';
import {withRouter} from 'react-router-dom';
import ContextProvider from "./ConsoleContext";
import ReactGA from 'react-ga';



class LoginForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {userName: '', password: '', rememberMe: false, isLoggedIn:false};

        LoginForm.addWornSignInElement = LoginForm.addWornSignInElement.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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

    static addWornSignInElement(message){
        let para = document.createElement("h3");
        para.setAttribute("id", "failedLoginText");
        let node = document.createTextNode(message);
        para.appendChild(node);
        let element = document.getElementById("failedLogin");
        element.appendChild(para);

        let signInElement = document.getElementById("signInBtn");
        signInElement.classList.add("wrong-signIn-for-btn");
        document.getElementById("signInBtn").style.marginTop = "0px";
    }

    handleSubmit(event) {

        if (document.getElementById("failedLoginText")) {
            document.getElementById("failedLoginText").remove();

            document.getElementById("signInBtn").classList.remove("wrong-signIn-for-btn");
        }

        let user =
        {email: "",
        loginPassword: this.state.password,
        loginUser: this.state.userName};


        let $ = require('jquery');
        require('jquery.soap');
        require('xml2js');
        let parseString = require('jquery.soap');
        let me = this;
        $.soap({
            url: this.context.accountServiceUrl,
            method: 'AccountLoginRequest',
            namespaceURL: 'com.advantage.online.store.accountservice',
            SOAPAction: 'com.advantage.online.store.accountserviceAccountLoginRequest' ,
            data: user,
            success: function (soapResponse) {
                let response = parseString(soapResponse);
                let statusMessage = xml2jsonImpl(response.content,"AccountLoginResponse", false);
                xml2jsonImpl(null, null, true);
                console.log(statusMessage);
                if(statusMessage.StatusMessage.success !== true){
                    LoginForm.addWornSignInElement("Wrong Username or password");
                }else{
                    me.props.history.push('/management-console');
                    ReactGA.event({
                        category: 'Management Console',
                        action: 'Admin Console Login'
                    });
                }
            },
            error: function (response) {
                LoginForm.addWornSignInElement("Server responded with error");
                console.log(response);
            },
            enableLogging: true
        });

        event.preventDefault();

    }


    render(){
        return (

            <form autoComplete="off" className= "login" onSubmit={this.handleSubmit}>
                <h1 className= "enter-details-to-log">Enter details to login</h1>
                <label className="margin-top-labels">
                    {/*<h3 className= "user-name">User Name</h3>*/}
                    <input id="userNameInput" placeholder="User Name" type="text" name="userName" className="input-style" value={this.state.value} onChange={this.handleInputChange} />
                </label>
                <br />
                <label className="margin-top-labels">
                    {/*<h3 className= "user-name">Password</h3>*/}
                    {/*type="password"*/}
                    <input placeholder="Password" type="password" name="password" className="input-style" value={this.state.value} onChange={this.handleInputChange}/>
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
                <label id={"failedLogin"} className="failed-login">
                </label>
                <label id={'signInBtn'} style={{marginTop:"20px"}}>
                    <input className="sign-in-btn"  type="submit" value="Sign In" />
                </label>
            </form>
        );
    }
}
LoginForm.contextType = ContextProvider;
export default withRouter(LoginForm);
