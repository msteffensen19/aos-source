import React from 'react';


export default class General extends React.Component {


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
                    <tr>
                        <td>USA</td>
                        <td>Washington, D.C.</td>
                        <td>309 million</td>
                        <td>English</td>
                    </tr>
                    <tr>
                        <td>Sweden</td>
                        <td>Stockholm</td>
                        <td>9 million</td>
                        <td>Swedish</td>
                    </tr>
                </tbody>
            </table>
        );
    }
}