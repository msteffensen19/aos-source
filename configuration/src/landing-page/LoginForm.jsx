
import React from 'react';
export default class LoginForm extends React.Component {
    constructor(props) {
        super(props);

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {

    }

    render() {
        return (
            <form>
                <label>
                    Username:
                    <input
                        name="loginName"
                        onChange={this.handleInputChange} />
                </label>
                <br />
                <label>
                    password:
                    <input
                        name="numberOfGuests"
                        onChange={this.handleInputChange} />
                </label>
            </form>
        );
    }
}