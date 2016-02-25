package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dto.account.DemoAppConfigParameter;
import com.advantage.accountsoap.dto.account.DemoAppConfigStatusResponse;
import com.advantage.common.Constants;
import com.advantage.root.util.xml.XmlHelper;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This service manages &quat;DemoAppConfig.xml&quat; parameters file. There is no
 * database involved and no transaction management reuired.
 *  <pre>
 *  Example XML file (file.xml):
 *  <code>
 *      <?xml version="1.0" encoding="UTF-8" standalone="no"?>
 *      <Parameters>
 *          <!-- User level configuration. Managed by Admin-User in ADM Demo .NET administration application -->
 *          <!-- 0=Disabled, any other positive number is the amount of memory leak in MegaBytes -->
 *          <Generate_memory_leak tools="LoadRunner;LeanFt">0</Generate_memory_leak>
 *          <!-- 0=Disabled, any other positive number is the number of repeat calls to ShipEx -->
 *          <Repeat_ShipEx_call tools="LoadRunner;SV;StormRunner">0</Repeat_ShipEx_call>
 *          <!-- 0=Disables, any other number is the sum to add to cart calculation -->
 *          <Sum_to_add_to_cart_calculation tools="LeanFT;UFT">0</Sum_to_add_to_cart_calculation>
 *          <!-- 0=Disabled, any other positive number is the product id to add to speakers category -->
 *          <Add_wrong_product_to_speakers_category tools="LeanFT;UFT">0</Add_wrong_product_to_speakers_category>
 *          <!-- "No"=(Default) Do not make OR "Yes"=make spelling mistakes in order payment page -->
 *          <Spelling_mistakes_in_order_payment_page tools="Sprinter">No</Spelling_mistakes_in_order_payment_page>
 *          <!-- (-1)=(default) Disabled, any other number is the number of concurrentSessions -->
 *          <Max_concurrent_sessions tools="">-1</Max_concurrent_sessions>
 *          <!-- "No"=(Default) Do not, or "Yes"=Mix places of pictures in home page -->
 *          <Mix_pictures_in_home_page tools="LeanFT;MobileCenter;UFT">No</Mix_pictures_in_home_page>
 *          <!-- 0=Disabled, any other positive number is the number of seconds to delay the Stored Procedure execution -->
 *          <Slow_DB_call tools="LoadRunner;StormRunner">0</Slow_DB_call>
 *      </Parameters>
 *  </code>
 *  </pre>
 *  @author Binyamin Regev on 21/02/2016.
 */
@Service
public class DemoAppConfigService {
    //  region Class CONSTANTS
    public static final String DEMO_APP_CONFIG_XML_FILE_NAME = "DemoAppConfig.xml";

    public static final String ROOT_ELEMENT_NAME = "Parameters";
    public static final String ELEMENTS_TAG_NAME = "tools";
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
            System.out.println("parameter name=\"" + node.getNodeName() + "\"");

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
                    Node nodeAttr = attr.getNamedItem(ELEMENTS_TAG_NAME);
                    String attributeValue = nodeAttr.getTextContent();

                    System.out.println("<" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
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
                    Node nodeAttr = attr.getNamedItem(ELEMENTS_TAG_NAME);
                    String attributeValue = nodeAttr.getTextContent();

                    returnList.add("<" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    System.out.println("<" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
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
            Node nodeAttr = attr.getNamedItem(ELEMENTS_TAG_NAME);
            String attributeValue = nodeAttr.getTextContent();

            parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()));

            System.out.println("<" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
        }

        System.out.println("getAllDemoAppConfigParameters() - End");
        System.out.println("");

        return parameters;
    }

    /**
     *
     * @param toolName
     * @return
     */
    public List<DemoAppConfigParameter> getDemoAppConfigParametersByTool(String toolName) {
        System.out.println("getDemoAppConfigParametersByTool(\"" + toolName + "\") - Begin");

        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        System.out.println("Document URL\"" + doc.getDocumentURI() + "\"");

        Node rootElement;   //  parameter

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
            Node nodeAttr = attr.getNamedItem(ELEMENTS_TAG_NAME);
            String attributeValue = nodeAttr.getTextContent();

            if (attributeValue.contains(toolName)) {
                parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeValue, node.getTextContent()));
                System.out.println("Found <" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
            }
        }

        System.out.println("getDemoAppConfigParametersByTool(\"" + toolName + "\") - End");
        System.out.println("");

        return parameters;
    }

    /**
     * Update the value of a specific tool's parameter. Tool is identified by it's name.
     * @param parameterName     Parameter name linked to the specific tool.
     * @param parameterNewValue   Tool's new parameter value.
     */
    public DemoAppConfigStatusResponse updateParameterValue(String parameterName, String parameterNewValue) {
        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        System.out.println("Document URL\"" + doc.getDocumentURI() + "\"");

        Node nodeToUpdate = findParameterByName(doc, parameterName);
        if (nodeToUpdate != null) {
            nodeToUpdate.setNodeValue(parameterNewValue);

            XmlHelper.writeXmlDocumentContent(doc, DEMO_APP_CONFIG_XML_FILE_NAME);

            return new DemoAppConfigStatusResponse(true, "update successful");
        }

        return new DemoAppConfigStatusResponse(false, "update failed with error: [parameter name] not found");
    }

    /**
     * Add an element to XML document
     */
    private void addElement(String elementName, String elementNewValue) {
        Element parameter = null;

        //  Child Element - Tool
        NodeList tool = doc.getElementsByTagName(ELEMENTS_TAG_NAME);

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
        NodeList tool = doc.getElementsByTagName(ELEMENTS_TAG_NAME);

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
    public void restoreFactorySettingsDemoAppConfig() {
        this.updateParameterValue("Generate_memory_leak", "0");
        this.updateParameterValue("Repeat_ShipEx_call", "0");
        this.updateParameterValue("Sum_to_add_to_cart_calculation", "0");
        this.updateParameterValue("Add_wrong_product_to_speakers_category", "0");
        this.updateParameterValue("Spelling_mistakes_in_order_payment_page", "No");
        this.updateParameterValue("Max_concurrent_sessions", "-1");
        this.updateParameterValue("Mix_pictures_in_home_page", "No");
        this.updateParameterValue("Slow_DB_call", "0");

    }

}
