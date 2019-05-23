
import React from 'react';
import *as  $ from 'jquery';
import $soap from 'jquery.soap';


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

        $soap({
            url: "http://localhost:8080/accountservice/",
            method: "AccountLoginRequest",
            namespaceURL: "com.advantage.online.store.accountservice",
            SOAPAction:"com.advantage.online.store.accountservice"+"AccountLoginRequest",
            data:user,
            success: function (soapResponse) {
                let response = soapResponse;
                console.log("Login is successful");

            },
            error: function (response) {
                let any = response;
                console.log("Failed to login");
            },
            enableLogging: true
        });

        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username:
                    <input name="userName" value={this.state.value} onChange={this.handleInputChange} />
                </label>
                <br />
                <label>
                    password:
                    {/*type="password"*/}
                    <input name="password" value={this.state.value} onChange={this.handleInputChange}/>
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