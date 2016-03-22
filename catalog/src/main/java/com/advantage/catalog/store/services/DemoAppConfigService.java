package com.advantage.catalog.store.services;

import com.advantage.common.Constants;
import com.advantage.common.dto.DemoAppConfigParameter;
import com.advantage.common.dto.DemoAppConfigStatusResponse;
import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.root.util.xml.XmlHelper;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regev on 22/03/2016.
 */
public class DemoAppConfigService {
    //  region Class CONSTANTS
    public static final String DEMO_APP_CONFIG_XML_FILE_NAME = "DemoAppConfig.xml";

    public static final String ROOT_ELEMENT_NAME = "Parameters";
    public static final String ATTRIBUTE_TOOLS_TAG_NAME = "tools";
    public static final String ATTRIBUTE_DATA_TYPE_TAG_NAME = "datatype";
    //  endregion

    //  region Class Properties
    private File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
    private Document doc;

    private Node parameters;        //  Root Element
    //  endregion

    /**
     *  @param nodeName          "staff"
     *  @param attributeName     "id"
     *  @param attributeValue    an ID value
     *  @return {@link Node}
     */
    public Node findNodeWithAttribute(Document doc, String nodeName, String attributeName, String attributeValue) {
        if (doc.getElementsByTagName(nodeName).getLength() == 0) {
            StringBuilder sb = new StringBuilder("Could not accept a node [\"")
                    .append(nodeName)
                    .append("\"] with an empty list of nodes as an argument");
            throw new IllegalArgumentException(sb.toString());
        }

        NodeList nodesList = doc.getElementsByTagName(nodeName);

        for (int i = 0; i < nodesList.getLength(); i++) {
            Node node = doc.getElementsByTagName(nodeName).item(i);
            System.out.println("parameters name=\"" + node.getNodeName() + "\"");

            NamedNodeMap attr = node.getAttributes();
            if (attr.getLength() == 0) {
                StringBuilder sb = new StringBuilder("Could not accept a node [\"")
                        .append(nodeName)
                        .append("\"] without attributes as an argument");
                throw new IllegalArgumentException(sb.toString());
            }

            Node nodeAttr = attr.getNamedItem(attributeName);
            if (nodeAttr == null) {
                continue;
            }

            String nodeAttrName = nodeAttr.getTextContent();
            if (nodeAttrName.isEmpty()) {
                continue;
            }

            if (!(";" + nodeAttrName + ";").contains(";" + attributeValue + ";")) {
                System.out.println(attributeValue + " was found in attrinbute \"" + attributeName + "\" of node \"" + nodeName + "\"");
            }

            return node;
        }

        return null;
    }

    /**
     *
     * @param parameterName
     * @return
     */
    public Node findParameterByName(Document doc, String parameterName) {
        System.out.println("findParameterByName(\"" + parameterName + "\") - Begin");

        NodeList nodesList = this.getAllParametersNodeList(doc);
        if (nodesList != null) {
            for (int i = 0; i < nodesList.getLength(); i++) {
                Node node = nodesList.item(i);

                /*if ((!node.getNodeName().equals("#comment")) && (! node.getNodeName().equals("#text"))) { */
                if (node.getNodeName().toUpperCase().equals(parameterName.toUpperCase())) {

                    NamedNodeMap attr = node.getAttributes();
                    Node nodeAttr1 = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
                    String attributeValue = nodeAttr1.getTextContent();

                    Node nodeAttr2 = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
                    String attributeDataTypeValue = nodeAttr2.getTextContent();

                    System.out.println("<" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "=\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    System.out.println("findParameterByName(\"" + parameterName + "\") - End");
                    System.out.println("");
                    return node;
                }
            }
        }

        System.out.println("findParameterByName(\"" + parameterName + "\") - return Null");
        System.out.println("");

        return null;
    }

    /**
     *
     * @return
     */
    public List<String> getDemoAppConfigXmlFile() {
        System.out.println("getDemoAppConfigXmlFile - Begin");
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);

        NodeList nodesList = this.getAllParametersNodeList(doc);
        if (nodesList == null) {
            return null;
        }

        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < nodesList.getLength(); i++) {
            Node node = nodesList.item(i);

            switch (node.getNodeName()) {
                case "#comment":
                    System.out.println("<!--" + node.getTextContent() + "-->");
                    break;
                case "#text":
                    break;
                default:
                    NamedNodeMap attr = node.getAttributes();
                    Node nodeAttr1 = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
                    String attributeValue = nodeAttr1.getTextContent();

                    Node nodeAttr2 = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
                    String attributeDataTypeValue = nodeAttr2.getTextContent();

                    System.out.println("<" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "=\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    break;
            }
        }

        System.out.println("getDemoAppConfigXmlFile - End");
        System.out.println("");

        return returnList;
    }

    /**
     *
     * @return
     */
    private NodeList getAllParametersNodeList(Document doc) {
        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        //System.out.println("Document URL\"" + doc.getDocumentURI() + "\"");

        Node rootElement = doc.getElementsByTagName(ROOT_ELEMENT_NAME).item(0);

        NodeList nodesList = rootElement.getChildNodes();

        return (nodesList.getLength() != 0 ? nodesList : null);
    }

    /**
     *
     * @return
     */
    public List<DemoAppConfigParameter> getAllDemoAppConfigParameters() {
        System.out.println("getAllDemoAppConfigParameters() - Begin");

        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        if (doc == null) {
            return null;
        }

        NodeList nodesList = this.getAllParametersNodeList(doc);
        if (nodesList == null) {
            return null;
        }

        List<DemoAppConfigParameter> parameters = new ArrayList<>();
        for (int i = 0; i < nodesList.getLength(); i++) {
            Node node = nodesList.item(i);

            if ((node.getNodeName().equals("#comment")) || (node.getNodeName().equals("#text"))) {
                continue;
            }

            NamedNodeMap attr = node.getAttributes();
            Node nodeAttr1 = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
            String attributeValue = nodeAttr1.getTextContent();

            Node nodeAttr2 = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
            String attributeDataTypeValue = nodeAttr2.getTextContent();

            parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()));

            System.out.println("<" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "=\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
        }

        System.out.println("getAllDemoAppConfigParameters() - End");
        System.out.println("");

        return parameters;
    }

    /**
     *  Gets tools names in a String separated by semi-colon (';') and returns all parameters
     *  which are requested by those tools, meaning: the parameter {@code tools} attribute
     *  contains the tool name.
     *  @param toolsNames
     *  @return {@link List} of {@link DemoAppConfigParameter} which has one or more of the
     *  tools names in its {@code tools} attribute.
     */
    public List<DemoAppConfigParameter> getDemoAppConfigParametersByTool(String toolsNames) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(toolsNames, "tools names");

        System.out.println("getDemoAppConfigParametersByTool(\"" + toolsNames + "\") - Begin");

        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        System.out.println("Document URL\"" + doc.getDocumentURI() + "\"");

        NodeList nodesList = this.getAllParametersNodeList(doc);
        if (nodesList == null) {
            return null;
        }

        //// Use Splitter, on method, and splitToList to separate the substrings into a List
        //List<String> tools = Splitter.on(';').splitToList(toolsNames);
        String[] tools = toolsNames.split(";");
        //List<String> toolsList = Arrays.asList(tools);

        List<DemoAppConfigParameter> parameters = new ArrayList<>();
        for (String tool : tools) {
            for (int i = 0; i < nodesList.getLength(); i++) {
                Node node = nodesList.item(i);

                if ((node.getNodeName().equals("#comment")) || (node.getNodeName().equals("#text"))) {
                    continue;
                }

                NamedNodeMap attr = node.getAttributes();
                Node nodeAttr1 = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
                String attributeValue = nodeAttr1.getTextContent();

                Node nodeAttr2 = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
                String attributeDataTypeValue = nodeAttr2.getTextContent();

                if (tool.trim().equalsIgnoreCase("ALL")) {
                    parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()));
                    System.out.println("Found <" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                }
                else if (attributeValue.trim().toUpperCase().contains(tool.trim().toUpperCase())) {
                    if (! parameters.contains(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()))) {
                        parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()));
                        System.out.println("Found <" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "=\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    }
                }
            }
        }

        System.out.println("getDemoAppConfigParametersByTool(\"" + toolsNames + "\") - End");
        System.out.println("");

        return parameters;
    }

    /**
     * Update the value of a specific tool's parameters. Tool is identified by it's name.
     * @param parameterName     Parameter name linked to the specific tool.
     * @param parameterNewValue   Tool's new parameters value.
     */
    public DemoAppConfigStatusResponse updateParameterValue(String parameterName, String parameterNewValue) {
        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        System.out.println("Document URL\"" + doc.getDocumentURI() + "\"");

        Node nodeToUpdate = findParameterByName(doc, parameterName);
        if (nodeToUpdate != null) {
            nodeToUpdate.setTextContent(parameterNewValue);
            XmlHelper.writeXmlDocumentContent(doc, DEMO_APP_CONFIG_XML_FILE_NAME);

            return new DemoAppConfigStatusResponse(true, "update successful");
        }

        return new DemoAppConfigStatusResponse(false, "update failed with error: [parameters name] not found");
    }

    /**
     * Add an element to XML document
     */
    private void addElement(String elementName, String elementNewValue) {
        Element parameter = null;

        //  Child Element - Tool
        NodeList tool = doc.getElementsByTagName(ATTRIBUTE_TOOLS_TAG_NAME);

        //loop for each Parameter
        for (int i = 0; i < tool.getLength(); i++) {
            parameter = (Element) tool.item(i);
            Element elementToAdd = doc.createElement(elementName);
            elementToAdd.appendChild(doc.createTextNode(elementNewValue));
            parameter.appendChild(elementToAdd);
        }

    }

    private void deleteElement(String elementName) {
        Element parameter = null;

        //  Child Element - Tool
        NodeList tool = doc.getElementsByTagName(ATTRIBUTE_TOOLS_TAG_NAME);

        //loop for each Parameter
        for (int i = 0; i < tool.getLength(); i++) {
            parameter = (Element) tool.item(i);
            Node nodeToDelete = parameter.getElementsByTagName(elementName).item(0);
            parameter.removeChild(nodeToDelete);
        }
    }

    /**
     * Restore DemoAppConfig.xml file to all default values.
     */
    public DemoAppConfigStatusResponse restoreFactorySettingsDemoAppConfig() {
        this.updateParameterValue("Email_address_in_login", "No");
        this.updateParameterValue("Generate_memory_leak", "0");
        this.updateParameterValue("Repeat_ShipEx_call", "0");
        this.updateParameterValue("Sum_to_add_to_cart_calculation", "0");
        this.updateParameterValue("Add_wrong_product_to_speakers_category", "0");
        this.updateParameterValue("Spelling_mistakes_in_order_payment_page", "No");
        this.updateParameterValue("Max_concurrent_sessions", "-1");
        this.updateParameterValue("Mix_pictures_in_home_page", "No");
        this.updateParameterValue("Slow_DB_call", "0");
        this.updateParameterValue("Slow_Page", "No");

        return new DemoAppConfigStatusResponse(true, "\"DemoAppConfig.xml\" restore factory settings successful");
    }


}
