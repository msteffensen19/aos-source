import React from 'react';


export default class Itom extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.changeFlagValue = this.changeFlagValue.bind(this);
        this.saveOldValue = this.saveOldValue.bind(this);

    }

    changeFlagValue(event){
        if(this.oldvalue !== event.target.value){
            this.setState({[event.target.key]:event.target.value});
            fetch('http://localhost:8080/catalog/api/v1/DemoAppConfig/update/parameter/'+event.target.name+'/value/'+event.target.value, {
                method: 'put'
            }).then( (response) => {
                return response.json()
            })
                .then( (json) => {
                    this.setState({
                        data: json
                    });
                    console.log('parsed json', json)
                })
                .catch( (ex) => {
                    console.log('parsing failed', ex)
                });
        }

    }
    saveOldValue(event){
        this.oldvalue = event.target.value;
    }
    renderTableData() {
        return this.props.itemsToShow.map((item, index) => {
            const {description, locationInAdvantage, parameterName, parameterValue } = item;
            return (
                <tr key={parameterName} name={parameterName}>
                    <td>{parameterName}</td>
                    <td>
                        <input onFocus={this.saveOldValue} name={parameterName} defaultValue={parameterValue} value={this.state.parameterName} onBlur={this.changeFlagValue}></input>
                    </td>
                    <td>{description}</td>
                    <td>{locationInAdvantage}</td>
                </tr>
            )
        })
    }

    render() {
        return (
            <table>
                <tbody>
                <tr>
                    <th>Name</th>
                    <th>Value</th>
                    <th>Description</th>
                    <th>AOS location</th>
                </tr>
                {this.renderTableData()}
                </tbody>
            </table>
        );
    }
}