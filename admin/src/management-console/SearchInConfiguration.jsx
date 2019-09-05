import React from 'react';
import '../css-management-console/configuration-icons.css';
import {ReactComponent as SearchIcon} from "../svg-png-ext/SearchIcon.svg";

export default class SearchInConfiguration extends React.Component {

    constructor() {
        super();

        this.handleClick = this.handleClick.bind(this);
        this.handleOutsideClick = this.handleOutsideClick.bind(this);
    }
    state ={
        openSearchBar:false
    };

    handleClick(){
        this.setState({openSearchBar:true});

    }
    handleOutsideClick(){
        this.setState({openSearchBar:false})
    }

    render() {
        return (
                <li>
                    <SearchIcon onClick={this.handleClick}/>
                    {this.state.openSearchBar === true?
                        <input autoFocus className={"search-bar-style"} placeholder={"Search"} onBlur={this.handleOutsideClick}/>:null }
                </li>
        );
    }
}