
import React from 'react'

import MyContext from './ConsoleContext';

export default class ContextProvider extends React.Component {
    state = {
        tamir: "shina"
    };

    render() {
        return (
            <MyContext.Provider
                value={this.state.tamir}>
                {this.props.children}
            </MyContext.Provider>
        );
    }
}