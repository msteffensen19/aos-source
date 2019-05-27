
import React from 'react';
import *as  $ from 'jquery';
import '../css-landing-page/login-form.css';



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


        let user =  {
            email:'',
                 loginPassword:this.state.password,
                 loginUser:this.state.userName
        };

        $.soap({
            url: "http://localhost:8080/accountservice/",
            method: "AccountLoginRequest",
            namespaceURL: "com.advantage.online.store.accountservice",
            SOAPAction:"com.advantage.online.store.accountservice"+"AccountLoginRequest",
            data:user,
            success: function (soapResponse) {
                //let response = soapResponse;
                console.log("Login is successful");

            },
            error: function (response) {
                //let any = response;
                console.log("Failed to login");
            },
            enableLogging: true
        });

        event.preventDefault();
    }

    render() {
        return (

            <form className= "login" onSubmit={this.handleSubmit}>
                <h1 className= "enter-details-to-log">Enter details to login</h1>
                <label>
                    <h3 className= "user-name">User Name</h3>
                    <input name="userName" className="input-style" value={this.state.value} onChange={this.handleInputChange} />
                </label>
                <br />
                <label>
                    <h3 className= "user-name">Password</h3>
                    {/*type="password"*/}
                    <input name="password" className="input-style" value={this.state.value} onChange={this.handleInputChange}/>
                </label>
                <label className="remember-me-label">
                    <div className="remember-me-label">
                    <input
                        className="rectangle-remember-me"
                        type="checkbox"
                        onChange={this.handleInputChange} />
                    <h3 className= "remember-me">Remember me</h3>
                    </div>
                    <div className= "remember-me">Forget Password?</div>
                </label>
                    <input type="submit" value="Submit" />
            </form>
        );
    }
}