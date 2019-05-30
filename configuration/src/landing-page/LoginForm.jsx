
import React from 'react';
import * as  $ from 'jquery';
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


            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('POST', 'http://localhost:8080/accountservice/AccountLoginRequest', true);

            // build SOAP request
            var sr =
                '<?xml version="1.0" encoding="UTF-8"?>' +
                '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"' +
                'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' +
                'xmlns:xsd="http://www.w3.org/2001/XMLSchema">' +
                '<soap:Body>' +
                '<AccountLoginRequest xmlns="com.advantage.online.store.accountservice">' +
                '<loginUser>admin</loginUser>' +
                '<email></email>' +
                '<loginPassword>adm1n</loginPassword>' +
                '</AccountLoginRequest>' +
                '</soap:Body>' +
                '</soap:Envelope>';
                // '<?xml version="1.0" encoding="utf-8"?>' +
                // '<soapenv:Envelope ' +
                // 'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' +
                // 'xmlns:api="http://127.0.0.1/Integrics/Enswitch/API" ' +
                // 'xmlns:xsd="http://www.w3.org/2001/XMLSchema" ' +
                // 'xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">' +
                // '<soapenv:Body>' +
                // '<AccountLoginRequest:xmlns="com.advantage.online.store.accountservice">' +
                // '<email></email>'+
                // '<loginPassword xsi:type="xsd:string">adm1n</loginPassword>' +
                // '<loginUser xsi:type="xsd:string">admin</loginUser>' +
                // '</AccountLoginRequest>' +
                // '</soapenv:Body>' +
                // '</soapenv:Envelope>';

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        alert(xmlhttp.responseText);
                        // alert('done. use firebug/console to see network response');
                    }
                }
            }
            // Send the POST request
            xmlhttp.setRequestHeader('Content-Type', 'text/xml');
            xmlhttp.setRequestHeader('Host', 'localhost:8080');
            xmlhttp.send(sr);
            // send request
            // ...


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