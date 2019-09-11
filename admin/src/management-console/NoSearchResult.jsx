import React from "react";
import {ReactComponent as SvgNoSearchResult} from "../svg-png-ext/Search_Results.svg";


export default class NoSearchResult extends React.Component {


    render() {
        return (
            <div>
                <SvgNoSearchResult className="no-search-result-svg"/>
                <h3 className="title-no-search">No Result Found</h3>
            </div>
        );
    }
}