// // ! Tamir this is an example of a function component with hooks
// import * as React from 'react';
// import General from './General';
// import '../css-management-console/configuration-style.css';
// import Popup from './DevPopupWindow';
// import SearchInConfiguration from './SearchInConfiguration';
// import NoSearchResult from './NoSearchResult';
// import { ReactComponent as ExportIcon } from '../svg-png-ext/exportToExcel.svg';
// import { ReactComponent as RestoreIcon } from '../svg-png-ext/RestoreIcon.svg';
// import { ReactComponent as FilterIcon } from '../svg-png-ext/filterIconConfig.svg';
//
// const Configuration = () => {
//   const [generalItemsArray, setGeneralItemsArray] = React.useState([]);
//   const [allItemsArray, setAllItemsArray] = React.useState([]);
//   const [functionalItemsArray, setFunctionalItemsArray] = React.useState([]);
//   const [itomItemsArray, setItomItemsArray] = React.useState([]);
//   const [mobileItemsArray, setMobileItemsArray] = React.useState([]);
//   const [performanceItemsArray, setPerformanceItemsArray] = React.useState([]);
//   const [bodyItemsToShow, setBodyItemsToShow] = React.useState([]);
//   const [tabToShow, setTabToShow] = React.useState('General');
//   const [isSearchMode, setIsSearchMode] = React.useState(false);
//   const [searchTerm, setSearchTerm] = React.useState('');
//   const [openDevPopup, setOpenDevPopup] = React.useState(false);
//
//   handleDevIconClick = () => {
//     openDevPopup ? setOpenDevPopup(false) : setOpenDevPopup(true);
//   };
//
//   navToOpen = e => {
//       setTabToShow(e.target.id)
//
//     switch (e.target.id) {
//       case 'General':
//           setBodyItemsToShow(generalItemsArray)
//
//         break;
//       case 'Performance':
//             setBodyItemsToShow(performanceItemsArray)
//
//         break;
//       case 'Functional':
//             setBodyItemsToShow(functionalItemsArray)
//
//         break;
//       case 'Mobile':
//             setBodyItemsToShow(mobileItemsArray)
//
//         break;
//       case 'Itom':
//             setBodyItemsToShow(itomItemsArray)
//         break;
//       case 'Security':
//             setBodyItemsToShow(securityItemsArray)
//
//         break;
//       default:
//         console.log('default');
//     }
//   };
//
//   isSearchMode = () => {
//
//     return isSearchMode;
//   };
//
//   handleSearch = searchTermParam => {
//     searchTermParam !== '' ? setIsSearchMode(true) : setIsSearchMode(false);
//       setSearchTerm(searchTermParam);
//       searchInItems(searchTermParam);
//
//   };
//
//   searchInItems = searchTerm => {
//     const tempGeneralItemsArray = [];
//     const tempFunctionalItemsArray = [];
//     const tempSecurityItemsArray = [];
//     const tempItomItemsArray = [];
//     const tempPerformanceItemsArray = [];
//     const tempMobileItemsArray = [];
//
//     allItemsArray && allItemsArray.forEach(item => {
//       const itemPropValues = [
//         item.parameterName,
//         item.description,
//         item.locationInAdminTool,
//       ];
//       let doesContainedSearchTerm = false;
//       itemPropValues && itemPropValues.forEach(element => {
//         if (element.toLowerCase().includes(searchTerm.toLowerCase())) {
//           doesContainedSearchTerm = true;
//         }
//       });
//       if (doesContainedSearchTerm) {
//         if (item.locationInAdminTool.includes('General')) {
//           tempGeneralItemsArray.push(item);
//         }
//         if (item.locationInAdminTool.includes('ITOM')) {
//           tempItomItemsArray.push(item);
//         }
//         if (item.locationInAdminTool.includes('Security')) {
//           tempSecurityItemsArray.push(item);
//         }
//         if (item.locationInAdminTool.includes('Functional')) {
//           tempFunctionalItemsArray.push(item);
//         }
//         if (item.locationInAdminTool.includes('Performance')) {
//           tempPerformanceItemsArray.push(item);
//         }
//         if (item.locationInAdminTool.includes('Mobile')) {
//           tempMobileItemsArray.push(item);
//         }
//       }
//     });
//     this.setState({
//       generalItemsArray: tempGeneralItemsArray,
//       itomItemsArray: tempItomItemsArray,
//       functionalItemsArray: tempFunctionalItemsArray,
//       securityItemsArray: tempSecurityItemsArray,
//       performanceItemsArray: tempPerformanceItemsArray,
//       mobileItemsArray: tempMobileItemsArray,
//     });
//     if (searchTerm === '') {
//       this.setState({ isSearchMode: false });
//     } else {
//       this.setState({ isSearchMode: true });
//     }
//     if (tempGeneralItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'General' });
//       this.setState({ bodyItemsToShow: tempGeneralItemsArray });
//       return;
//     }
//     if (tempPerformanceItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'Performance' });
//       this.setState({ bodyItemsToShow: tempPerformanceItemsArray });
//       return;
//     }
//     if (tempFunctionalItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'Functional' });
//       this.setState({ bodyItemsToShow: tempFunctionalItemsArray });
//       return;
//     }
//     if (tempMobileItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'Mobile' });
//       this.setState({ bodyItemsToShow: tempMobileItemsArray });
//       return;
//     }
//     if (tempItomItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'Itom' });
//       this.setState({ bodyItemsToShow: tempItomItemsArray });
//       return;
//     }
//     if (tempSecurityItemsArray.length !== 0) {
//       this.setState({ tabToShow: 'Security' });
//       this.setState({ bodyItemsToShow: tempSecurityItemsArray });
//     }
//     this.setState({ bodyItemsToShow: [] });
//   };
//
//
//   return (
//     <>
//       <h1></h1>
//     </>
//   );
// };
//
//
//
//
//
//
//
//
//
//
//   componentDidMount() {
//     let tempGeneralItemsArray = [];
//     let tempFunctionalItemsArray = [];
//     let tempSecurityItemsArray = [];
//     let tempItomItemsArray = [];
//     let tempPerformanceItemsArray = [];
//     let tempMobileItemsArray = [];
//     let tempAllItemsArray = [];
//
//     let host = window.location.origin;
//
//     let urlString = host.includes('localhost')
//       ? 'http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL'
//       : host + '/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL';
//
//     fetch(urlString)
//       .then(res => res.json())
//       .then(data => {
//         data.parameters.forEach(item => {
//           tempAllItemsArray.push(item);
//         });
//
//         tempAllItemsArray.forEach(item => {
//           if (item.locationInAdminTool.includes('General')) {
//             tempGeneralItemsArray.push(item);
//           }
//           if (item.locationInAdminTool.includes('ITOM')) {
//             tempItomItemsArray.push(item);
//           }
//           if (item.locationInAdminTool.includes('Security')) {
//             tempSecurityItemsArray.push(item);
//           }
//           if (item.locationInAdminTool.includes('Functional')) {
//             tempFunctionalItemsArray.push(item);
//           }
//           if (item.locationInAdminTool.includes('Performance')) {
//             tempPerformanceItemsArray.push(item);
//           }
//           if (item.locationInAdminTool.includes('Mobile')) {
//             tempMobileItemsArray.push(item);
//           }
//         });
//         this.setState({
//           generalItemsArray: tempGeneralItemsArray,
//           itomItemsArray: tempItomItemsArray,
//           functionalItemsArray: tempFunctionalItemsArray,
//           securityItemsArray: tempSecurityItemsArray,
//           performanceItemsArray: tempPerformanceItemsArray,
//           mobileItemsArray: tempMobileItemsArray,
//           bodyItemsToShow: tempGeneralItemsArray,
//           allItemsArray: tempAllItemsArray,
//         });
//       })
//       .catch(console.log);
//   }
//
//   render() {
//     return (
//       <div>
//         {this.state.openDevPopup ? (
//           <Popup closePopUp={handleDevIconClick} />
//         ) : null}
//         <ul className="configuration-icons">
//           <SearchInConfiguration
//             onUserSearch={this.handleSearch}
//             isSearchMode={this.state.isSearchMode}
//           />
//           <li>
//             <FilterIcon
//               className="pointer-cursor"
//               onClick={handleDevIconClick}
//             />
//           </li>
//           <li>
//             <ExportIcon
//               className="pointer-cursor"
//               onClick={handleDevIconClick}
//             />
//           </li>
//           <li>
//             <RestoreIcon
//               className="pointer-cursor"
//               onClick={handleDevIconClick}
//             />
//           </li>
//         </ul>
//         <ul className="configuration-headlines">
//           <li>
//             <div
//               id="General"
//               onClick={this.navToOpen}
//               className="pointer-cursor">
//               General
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode &&
//                 this.state.generalItemsArray.length > 0
//                   ? 'search-dot-gen'
//                   : null
//               }
//             />
//             <div
//               className={
//                 this.state.tabToShow === 'General' ? 'focus-line' : null
//               }
//             />
//           </li>
//           <li>
//             <div
//               id="Performance"
//               onClick={this.navToOpen}
//               className="pointer-cursor">
//               Performance
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode &&
//                 this.state.performanceItemsArray.length > 0
//                   ? 'search-dot-per'
//                   : null
//               }
//             />
//             <div
//               className={
//                 this.state.tabToShow === 'Performance' ? 'focus-line' : null
//               }
//             />
//           </li>
//           <li>
//             <div
//               id="Functional"
//               onClick={this.navToOpen}
//               className="pointer-cursor">
//               Functional
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode &&
//                 this.state.functionalItemsArray.length > 0
//                   ? 'search-dot-func'
//                   : null
//               }
//             />
//             <div
//               className={
//                 this.state.tabToShow === 'Functional' ? 'focus-line' : null
//               }
//             />
//           </li>
//           <li>
//             <div
//               id="Mobile"
//               onClick={this.navToOpen}
//               className="pointer-cursor">
//               Mobile
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode &&
//                 this.state.mobileItemsArray.length > 0
//                   ? 'search-dot-mob'
//                   : null
//               }
//             />
//             <div
//               className={
//                 this.state.tabToShow === 'Mobile' ? 'focus-line' : null
//               }
//             />
//           </li>
//           <li>
//             <div id="Itom" onClick={this.navToOpen} className="pointer-cursor">
//               ITOM
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode && this.state.itomItemsArray.length > 0
//                   ? 'search-dot-ito'
//                   : null
//               }
//             />
//             <div
//               className={this.state.tabToShow === 'Itom' ? 'focus-line' : null}
//             />
//           </li>
//           <li>
//             <div
//               id="Security"
//               onClick={this.navToOpen}
//               className="pointer-cursor">
//               Security
//             </div>
//             <div
//               className={
//                 this.state.isSearchMode &&
//                 this.state.securityItemsArray.length > 0
//                   ? 'search-dot-sec'
//                   : null
//               }
//             />
//             <div
//               className={
//                 this.state.tabToShow === 'Security' ? 'focus-line' : null
//               }
//             />
//           </li>
//         </ul>
//         <div className="config-line-separator" />
//         <div>
//           {this.state.bodyItemsToShow &&
//           this.state.bodyItemsToShow.length > 0 ? (
//             <General
//               searchTerm={this.state.searchTerm}
//               isSearchMode={this.state.isSearchMode}
//               itemsToShow={this.state.bodyItemsToShow}
//             />
//           ) : this.state.isSearchMode ? (
//             <NoSearchResult />
//           ) : null}
//         </div>
//       </div>
//     );
//   }
// }
//
// export default Configuration;
