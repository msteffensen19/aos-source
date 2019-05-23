
import React from 'react';

class whatsNew extends React.Component {

     whatsNewItems =[1,2,3];

     listItemsWhatsNew = whatsNewItems.map((whatsNewItem) =>
        <li key={whatsNewItem.toString()}>
            {whatsNewItem}
        </li>
    );
     render() {
    return (
<ul>{listItemsWhatsNew}</ul>
);}
}
