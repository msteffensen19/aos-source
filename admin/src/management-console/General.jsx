import React from 'react';
import TableHeaders from './TableHeaders';

export default class General extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.setInputAttributes = this.setInputAttributes.bind(this);
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
                .then((json) => {
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
    getHighlightedText(text, higlight) {
        // Split on higlight term and include term into parts, ignore case
        let parts = text.split(new RegExp(`(${higlight})`, 'gi'));
        return <span> { parts.map((part, i) =>
            <span key={i} style={part.toLowerCase() === higlight.toLowerCase() ? { backgroundColor: '#FFFF00' } : {} }>
            { part }
        </span>)
        } </span>;
    }
    saveOldValue(event){
        this.oldvalue = event.target.value;
    }
    setInputAttributes(datatype){
        switch (datatype) {
            case "string":
                return "input";
            case "enum:[Tip]":
                return "span";
            case "enum:[Yes,No]":
                return "yesNoSelect";
            default:
                console.log('default');
        }

    }
    renderTableData() {
        return this.props.itemsToShow.map((item, index) => {
            const {datatype, description, locationInAdvantage, parameterName, parameterValue } = item;
            let TagType = this.setInputAttributes(datatype);
            let descriptionWithHighlight = this.getHighlightedText(description,this.props.searchTerm);
            let inputTag;
            switch (TagType) {
                case "input":
                    inputTag = <select className="configuration-input-style config-input" name={parameterName} defaultValue={parameterValue}
                                       value={this.state.parameterName} onFocus={this.saveOldValue}  onBlur={this.changeFlagValue}>
                        {[...Array(1000).keys()].map((i) =>
                            <option key={i.toString()} value={i}>{i}</option>
                        )}
                    </select>;
                    break;
                case "span":
                    inputTag = <TagType className="configuration-input-style" name={parameterName}>Tip</TagType>;
                    break;
                case "yesNoSelect":
                    inputTag =<select className="configuration-input-style" name={parameterName} onChange={this.changeFlagValue}>
                        <option value="No">No</option>
                        <option value="Yes">Yes</option>
                    </select>;
                    break;
                default:
                    console.log('default');

            }

            return <tr className="data-rows-style" key={parameterName} name={parameterName}>
                <td>{parameterName}</td>
                <td>{inputTag}</td>
                <td className="description-style-config">{this.props.isSearchMode?descriptionWithHighlight:description}</td>
                <td>{locationInAdvantage}</td>
            </tr>
        })
    }

    render() {
        return (
            <table className={"configuration-table-style"}>
                <thead>
                <TableHeaders/>
                </thead>
                <tbody>
                {this.renderTableData()}
                </tbody>
            </table>
        );
    }
}