import React from 'react';
import '../css-management-console/configuration-icons.css';
import closeDark from "../svg-png-ext/closeDark.png"
import {ReactComponent as SearchIcon} from "../svg-png-ext/SearchIcon.svg";

export default class SearchInConfiguration extends React.Component {

    constructor(props) {
        super(props);

        this.handleClick = this.handleClick.bind(this);
        this.handleOnBlur = this.handleOnBlur.bind(this);
        this.handleCloseClick=this.handleCloseClick.bind(this);
    }
    state ={
        openSearchBar:false,
        searchTerm:''
    };

    handleClick(){
        this.setState({openSearchBar:true});
        if(document.getElementById("searchInConfiguration") !== null){
            this.props.onUserSearch(document.getElementById("searchInConfiguration").value);
        }

    }
    handleOnBlur(event){
        if(event.target.value === ""){
            this.setState({openSearchBar:false});
        }
    }
    handleCloseClick(){
        if(this.props.isSearchMode){
            this.setState({openSearchBar:false});
            this.setState({searchTerm:""});
            this.props.onUserSearch("");
        }
        this.setState({openSearchBar:false});
        this.setState({searchTerm:""});
    }

    enterPressed(event) {
        let code = event.keyCode || event.which;
        if(code === 13) { //13 is the enter keycode
            this.handleClick();
        }
    }

    render(){
        return (
                <li>
                    <SearchIcon onClick={this.handleClick}/>
                    {this.state.openSearchBar === true?
                        <div className="search-bar-config">
                        <input autoFocus className={"search-bar-style"} placeholder={"Search"}
                               id="searchInConfiguration"
                               onBlur={this.handleOnBlur}
                               onKeyPress={this.enterPressed.bind(this)}/>
                            <img onClick={this.handleCloseClick} src={closeDark} className={"close-png"}/></div>:null }

                </li>
        );
    }
}