import React from 'react';
import Popup from'./SavedPopup';
import ContextProvider from "../landing-page/ConsoleContext";

export default class General extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data:{},
            isUpdateSuccess:false
        };
        this.setInputAttributes = this.setInputAttributes.bind(this);
        this.changeFlagValue = this.changeFlagValue.bind(this);
        this.saveOldValue = this.saveOldValue.bind(this);
        this.closePopUp=this.closePopUp.bind(this);
    }

    closePopUp(){
        this.setState({isUpdateSuccess:false});
    }

    changeFlagValue(event){
        let self = this;
        let host = window.location.origin;
        let port = this.context.catalog;
        let urlString ="";
        if (host.includes("localhost")){

            urlString ='http://localhost:8080/catalog/api/v1/DemoAppConfig/update/parameter/'+event.target.name+'/value/'+event.target.value;
        }else {
            urlString = host + ':' + port + '/catalog/api/v1/DemoAppConfig/update/parameter/'+event.target.name+'/value/'+event.target.value;
        }
        if(this.oldvalue !== event.target.value){
            this.setState({[event.target.key]:event.target.value});
            fetch(urlString, {
                method: 'put'
            }).then( (response) => {
                return response.json()
            })
                .then((json) => {
                    self.setState({
                        data: json
                    });
                    json.success? self.setState({isUpdateSuccess:true}):self.setState({isUpdateSuccess:false});
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
                console.log('no data type!');
        }

    }
    isProductionFunc = ()=>{
        let host = window.location.host;
        if(host.includes("advantageonlineshopping") || host.includes("107.23.171.213") || host.includes("ec2-107-23-171-213.compute-1.amazonaws.com")){
            return true;
        }else return false;
    };

    renderTableData() {
        return this.props.itemsToShow.map((item, index) => {
            const {datatype, description, locationInAdvantage, parameterName, parameterValue } = item;
            let TagType = this.setInputAttributes(datatype);
            let descriptionWithHighlight = this.props.isSearchMode?this.getHighlightedText(description,this.props.searchTerm):null;
            let parameterNameWithHighlight = this.props.isSearchMode?this.getHighlightedText(parameterName,this.props.searchTerm):null;
            let isProduction = this.isProductionFunc();
            let inputTag;
            switch (TagType) {
                case "input":
                    inputTag = <select disabled={isProduction} className="configuration-input-style config-input" name={parameterName} defaultValue={parameterValue}
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
                    inputTag =<select disabled={isProduction} className="configuration-input-style" name={parameterName} onChange={this.changeFlagValue}>
                        <option value="No">No</option>
                        <option value="Yes">Yes</option>
                    </select>;
                    break;
                default:
                    console.log('default');
            }

            return <tr className="data-rows-style" key={parameterName} name={parameterName}>
                <td>{this.props.isSearchMode?parameterNameWithHighlight:parameterName}</td>
                <td>{inputTag}</td>
                <td className="description-style-config">{this.props.isSearchMode?descriptionWithHighlight:description}</td>
                <td>{locationInAdvantage}</td>
            </tr>
        })
    }

    render() {
        return (
            <>
                {this.state.isUpdateSuccess?<Popup closePopUp = {this.closePopUp}/>:null}
                <table className="configuration-table-style">
                    <tbody>
                    {this.renderTableData()}
                    </tbody>
                </table>
            </>
        );
    }
}
General.contextType = ContextProvider;