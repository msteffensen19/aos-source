import React from 'react';
import '../css-management-console/configuration-icons.css';
import {ReactComponent as SearchIcon} from "../svg-png-ext/SearchIcon.svg";

export default class SearchInConfiguration extends React.Component {

    constructor(props) {
        super(props);

        this.handleClick = this.handleClick.bind(this);
        this.handleOutsideClick = this.handleOutsideClick.bind(this);
    }
    state ={
        openSearchBar:false,
        searchTerm:''
    };

    handleClick(){
        this.setState({openSearchBar:true});

    }
    handleOutsideClick(event){
        if(event.target.value === ""){
            this.setState({openSearchBar:false});
            this.setState({searchTerm:""});
            this.props.onUserSearch(event.target.value);
        }else{
            this.setState({searchTerm:event.target.value});
            console.log(event.target.value);
            this.props.onUserSearch(event.target.value);
        }
    }
    enterPressed(event) {
        let code = event.keyCode || event.which;
        if(code === 13) { //13 is the enter keycode

        }
    }

    render(){
        return (
                <li>
                    <SearchIcon onClick={this.handleClick}/>
                    {this.state.openSearchBar === true?
                        <input autoFocus className={"search-bar-style"} placeholder={"Search"}
                               onBlur={this.handleOutsideClick}
                               onKeyPress={this.enterPressed.bind(this)}/>:null }
                </li>
        );
    }
}