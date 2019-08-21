import React from 'react';
import XLSX from 'xlsx';
import FileSaver from 'file-saver';

export default class ExportToExcel extends React.Component {

    constructor(props) {
        super(props);
        this.exportToExcel = this.exportToExcel.bind(this);
        this.convertToExcelAndDownload = this.convertToExcelAndDownload.bind(this);

        this.state={dataForExcel:[]};
    }


    exportToExcel(){

        fetch('http://localhost:8080/catalog/api/v1/DemoAppConfig/parameters/by_tool/ALL')
            .then(res => res.json())
            .then((data) => {
                this.setState({dataForExcel:data.parameters})
                })
            .then(this.convertToExcelAndDownload)
            .catch(console.log);


    }
//let ws_data = [['hello' , 'world'],['tamir','shina'],['assaf','jubany']];  //a row with 2 columns

    getAllDataForExcel(){

        let allDataArray = this.state.dataForExcel;
        let arrayToParseTOExcel = [['Name', 'Description', 'Value', 'Location in AOS', 'Tools']];

        allDataArray.forEach((item) => {
            let itemArray = [];
            itemArray.push(item.parameterName.toString(), item.description.toString(), item.parameterValue.toString(),item.locationInAdvantage.toString(), item.attributeTools.toString());
            arrayToParseTOExcel.push(itemArray)

        });
        return arrayToParseTOExcel;
    }

    getColumnDesign(){

        let wsCols = [
            {wpx:300},
            {wpx:400},
            {wpx:50},
            {wpx:300},
            {wpx:300}
        ];

       return  wsCols;
    }

    getRowDesign(range){

        let noRows = range.e.r; // No.of rows

        let wsrows =  [];

        for (let i = 0; i < noRows; i++) {
            wsrows.push({hpx: 50}) // row height in pixels)
        }
        return wsrows;
    }

    convertToExcelAndDownload(){


        let wb = XLSX.utils.book_new();
        wb.SheetNames.push("Test Sheet");

        let ws = XLSX.utils.aoa_to_sheet(this.getAllDataForExcel());

        ws['!cols'] = this.getColumnDesign();

        ws['!rows'] = this.getRowDesign(XLSX.utils.decode_range(ws['!ref']));

        ws['B2']={v:"tamir",
            s: { alignment: {texWarp: true },
                font: {sz: 72, bold: true}
            }};
        wb.Sheets["Test Sheet"] = ws;

        let wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

        let myBlob = new Blob([this.s2ab(wbout)],{type:"application/octet-stream"});
        FileSaver.saveAs(myBlob, "hello world.xlsx");
    }

    s2ab(s) {
        let buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
        let view = new Uint8Array(buf);  //create uint8array as viewer
        for (let i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
        return buf;
    }

    render() {
        return (
            <li className="nav-ul-container">
                <button onClick={this.exportToExcel}>ExportToExcel</button>
            </li>
        );
    }
}