
import 'jquery';
import 'jquery.soap';

let jsonString;
let jsonResult;



        function xml2jsonImpl(xml, rootNode, isReset) {

            if(isReset){
                jsonResult = null;
                jsonString = null;
                return;
            }

            for (let i = 0; i < xml.childNodes.length; i++) {
                let node = xml.childNodes[i];
                if (node.nodeType === 1) {

                    if (!(node.attributes.length === 0 && node.childElementCount === 0)) {
                        xml2jsonImpl(node, rootNode);
                        if (jsonResult) {
                            return jsonResult;
                        }
                    }

                    let name = node.nodeName;
                    if (name.toLowerCase() != (node.prefix + ":" + rootNode).toLowerCase()) {
                        continue;
                    }

                    jsonString = "";
                    if(node.childNodes && node.childNodes.length > 0){
                        buildJson(node.childNodes);
                    }
                    jsonResult = JSON.parse("{" + jsonString+ "}");
                }
            }
        }


        function getObjectJson(htmlNodes) {
            let temp = "";
            for (let i = 0; i < htmlNodes.childNodes.length; i++) {
                let node = htmlNodes.childNodes[i];
                if (temp !== "") {
                    temp += ", "
                }
                temp += appendToJsonString(node);
            }
            return "{" + temp + "}"
        }

        function appendToJsonString(node) {

            let textContent = getType(node.textContent);
            return "\"" + node.localName + "\":" + textContent;

        }

        function getType(textContent) {
            if (!textContent) {
                return "\"\"";
            }
            if (textContent.toLowerCase() === "true" ||
                textContent.toLowerCase() === "false") {
            }
            else if (new RegExp('^[0-9]{0,10}$').test(textContent.toLowerCase())
                && textContent[0] + "" !== "0") {
            }
            else {
                textContent = "\"" + textContent + "\""
            }
            return textContent;
        }

        function buildJson(searchNodes) {

            for (var j = 0; j < searchNodes.length; j++) {

                var node = searchNodes[j];
                var valueIsObject;

                if (!(node.attributes.length === 0 && node.childElementCount === 0)) {
                    valueIsObject = getObjectJson(searchNodes[j]);
                }

                if (jsonString !== "") {
                    jsonString += ", "
                }

                if (valueIsObject) {
                    if (searchNodes.length > 1) {
                        if (jsonString.indexOf("[") === -1) {
                            jsonString += "\"" + node.localName + "\":["
                        }
                        jsonString += valueIsObject;
                        if (j + 1 >= searchNodes.length) {
                            jsonString += "]";
                        }
                    }
                    else {
                        jsonString += "\"" + node.localName + "\":" + valueIsObject;
                    }
                }
                else {
                    var textContent = getType(node.textContent);
                    jsonString += "\"" + node.localName + "\":" + textContent;
                }

            }
        }
export default xml2jsonImpl;
