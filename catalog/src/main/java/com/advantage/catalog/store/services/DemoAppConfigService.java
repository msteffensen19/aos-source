package com.advantage.catalog.store.services;

import com.advantage.common.Constants;
import com.advantage.common.dto.DemoAppConfigParameter;
import com.advantage.common.dto.DemoAppConfigParameterDto;
import com.advantage.common.dto.DemoAppConfigParametersDto;
import com.advantage.common.dto.DemoAppConfigStatusResponse;
import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.root.util.xml.XmlHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Binyamin Regev on 22/03/2016.
 */
@Service
public class DemoAppConfigService {
    //  region Class CONSTANTS
    public static final String DEMO_APP_CONFIG_XML_FILE_NAME = "DemoAppConfig.xml";
    public static final String ROOT_ELEMENT_NAME = "Parameters";
    public static final String ATTRIBUTE_TOOLS_TAG_NAME = "tools";
    public static final String ATTRIBUTE_DATA_TYPE_TAG_NAME = "datatype";
    public static final String ATTRIBUTE_DESCRIPTION_TAG_NAME = "description";
    public static final String ATTRIBUTE_LOCATION_IN_ADVANTAGE_TAG_NAME = "loationInAdvantage";
    //  endregion

    //  region Class Properties
    private File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
    private static final Logger logger = Logger.getLogger(DemoAppConfigService.class);
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
        logger.trace("findParameterByName(\"" + parameterName + "\") - Begin");

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

                    if (logger.isDebugEnabled()) {
                        logger.debug("<" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "=\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    }
                    logger.trace("findParameterByName(\"" + parameterName + "\") - End" + System.lineSeparator());
                    return node;
                }
            }
        }

        logger.warn("findParameterByName(\"" + parameterName + "\") - return Null" + System.lineSeparator());
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
        logger.trace("getAllDemoAppConfigParameters() - Begin");

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

            if ((node.getNodeName().equals("#text")) || (node.getNodeName().equals("#comment"))) {
                continue;
            }

            NamedNodeMap attr = node.getAttributes();

            Node nodeAttrTools = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
            String attributeToolsValue = nodeAttrTools.getTextContent();

            Node nodeAttrDataType = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
            String attributeDataTypeValue = nodeAttrDataType.getTextContent();

            Node nodeAttrDescription = attr.getNamedItem(ATTRIBUTE_DESCRIPTION_TAG_NAME);
            String attributeDescriptionValue = nodeAttrDescription.getTextContent();

            Node nodeAttrLocation = attr.getNamedItem(ATTRIBUTE_LOCATION_IN_ADVANTAGE_TAG_NAME);
            String attributeLocationInAdvantageValue = nodeAttrDescription.getTextContent();

            //parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeToolsValue, node.getTextContent()));
            parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeDataTypeValue, attributeDescriptionValue, attributeToolsValue, attributeLocationInAdvantageValue, node.getTextContent()));

            if (logger.isDebugEnabled()) {
                logger.debug("<" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_DESCRIPTION_TAG_NAME + "\"" + attributeDescriptionValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeToolsValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
            }
        }

        logger.trace("getAllDemoAppConfigParameters() - End" + System.lineSeparator());
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

        logger.trace("getDemoAppConfigParametersByTool(\"" + toolsNames + "\") - Begin");

        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        logger.debug("Document URL\"" + doc.getDocumentURI() + "\"");

        NodeList nodesList = this.getAllParametersNodeList(doc);
        if (nodesList == null) {
            return null;
        }

        //// Use Splitter, on method, and splitToList to separate the substrings into a List
        //List<String> tools = Splitter.on(';').splitToList(toolsNames);
        String[] tools = toolsNames.split(";");
        //List<String> toolsList = Arrays.asList(tools);

        HashSet<DemoAppConfigParameter> parameters = new HashSet<>();
        for (String tool : tools) {
            for (int i = 0; i < nodesList.getLength(); i++) {
                Node node = nodesList.item(i);

                if ((node.getNodeName().equals("#comment")) || (node.getNodeName().equals("#text"))) {
                    continue;
                }

                NamedNodeMap attr = node.getAttributes();

                Node nodeAttrTools = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
                String attributeToolsValue = nodeAttrTools.getTextContent();

                Node nodeAttrDataType = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
                String attributeDataTypeValue = nodeAttrDataType.getTextContent();

                Node nodeAttrDescription = attr.getNamedItem(ATTRIBUTE_DESCRIPTION_TAG_NAME);
                String attributeDescriptionValue = nodeAttrDescription.getTextContent();

                Node nodeAttrLocation = attr.getNamedItem(ATTRIBUTE_LOCATION_IN_ADVANTAGE_TAG_NAME);
                String attributeLocationInAdvantageValue = nodeAttrDescription.getTextContent();

                if (tool.trim().equalsIgnoreCase("ALL")) {
                    parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeToolsValue, attributeLocationInAdvantageValue, node.getTextContent()));
                    if (logger.isDebugEnabled()) {
                        logger.debug("Found <" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_DESCRIPTION_TAG_NAME + "\"" + attributeDescriptionValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeToolsValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                    }
                }
                else if (attributeToolsValue.trim().toUpperCase().contains(tool.trim().toUpperCase())) {
                    if (! parameters.contains(new DemoAppConfigParameter(node.getNodeName(), attributeToolsValue, attributeLocationInAdvantageValue, node.getTextContent()))) {
                        //parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeToolsValue, node.getTextContent()));
                        parameters.add(new DemoAppConfigParameter(node.getNodeName(), attributeDataTypeValue, attributeDescriptionValue, attributeToolsValue, attributeLocationInAdvantageValue, node.getTextContent()));
                        if (logger.isDebugEnabled()) {
                            logger.debug("Found <" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_DESCRIPTION_TAG_NAME + "\"" + attributeDescriptionValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeToolsValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                        }
                    }
                }
            }
        }

        logger.trace("getDemoAppConfigParametersByTool(\"" + toolsNames + "\") - End" + System.lineSeparator());
        return new ArrayList<DemoAppConfigParameter>(parameters);
    }

    /**
     *
     *  @param parameterName Name of requested DemoAppConfiguration parameter.
     *  @return {@link DemoAppConfigParameter} with requested parameter-name.
     */
    public DemoAppConfigParameter getDemoAppConfigParametersByName(String parameterName) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(parameterName, "parameter name");

        logger.trace("getDemoAppConfigParametersByName(\"" + parameterName + "\") - Begin");

        //File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
        Document doc = XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME);
        logger.info("Document URL\"" + doc.getDocumentURI() + "\"");

        Node node = findParameterByName(doc, parameterName);
        if (node != null) {

            NamedNodeMap attr = node.getAttributes();

            Node nodeAttrTools = attr.getNamedItem(ATTRIBUTE_TOOLS_TAG_NAME);
            String attributeToolsValue = nodeAttrTools.getTextContent();

            Node nodeAttrDataType = attr.getNamedItem(ATTRIBUTE_DATA_TYPE_TAG_NAME);
            String attributeDataTypeValue = nodeAttrDataType.getTextContent();

            Node nodeAttrDescription = attr.getNamedItem(ATTRIBUTE_DESCRIPTION_TAG_NAME);
            String attributeDescriptionValue = nodeAttrDescription.getTextContent();

            Node nodeAttrLocation = attr.getNamedItem(ATTRIBUTE_LOCATION_IN_ADVANTAGE_TAG_NAME);
            String attributeLocationInAdvantageValue = nodeAttrDescription.getTextContent();

            if (logger.isDebugEnabled()) {
                logger.debug("Found <" + node.getNodeName() + Constants.SPACE + ATTRIBUTE_DATA_TYPE_TAG_NAME + "\"" + attributeDataTypeValue + "\"" + Constants.SPACE + ATTRIBUTE_DESCRIPTION_TAG_NAME + "\"" + attributeDescriptionValue + "\"" + Constants.SPACE + ATTRIBUTE_TOOLS_TAG_NAME + "=\"" + attributeToolsValue + "\" locationInAdvantage=\"" + attributeLocationInAdvantageValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
            }

            logger.trace("getDemoAppConfigParametersByName(\"" + parameterName + "\") - End" + System.lineSeparator());
            return new DemoAppConfigParameter(node.getNodeName(), attributeDataTypeValue, attributeDescriptionValue, attributeToolsValue, attributeLocationInAdvantageValue, node.getTextContent());
        }

//        if ((node.getNodeName().equals("#comment")) || (node.getNodeName().equals("#text"))) {
//            continue;
//        }
        logger.warn("Return null");
        return null;
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
        System.out.println("newValue:" + parameterNewValue);
        Node nodeToUpdate = findParameterByName(doc, parameterName);
        if (nodeToUpdate != null) {
            nodeToUpdate.setTextContent(parameterNewValue);
            XmlHelper.writeXmlDocumentContent(doc, DEMO_APP_CONFIG_XML_FILE_NAME);

            return new DemoAppConfigStatusResponse(true, "update successful");
        }

        return new DemoAppConfigStatusResponse(false, "update failed with error: [parameters name] not found");
    }

    /**
     * Update the value of a specific tool's parameters. Tool is identified by it's name.
     * @param parameters     all parameters and new values
     *
     */
    public DemoAppConfigStatusResponse updateParametersValues(DemoAppConfigParametersDto parameters) {

        System.out.println("start update parameters");
        if(parameters != null) {
            String errorMessage = "";
            boolean result = true;
            for (DemoAppConfigParameterDto parameter : parameters.getParameters()) {
                DemoAppConfigStatusResponse demoAppConfigStatusResponse = updateParameterValue(parameter.getName(), parameter.getNewValue());
                result = !result || !demoAppConfigStatusResponse.isSuccess() ? false : true;
                errorMessage += !demoAppConfigStatusResponse.isSuccess() ? demoAppConfigStatusResponse.getReason() + "\r\n" : "";
            }
            DemoAppConfigStatusResponse demoAppConfigStatusResponse;
            demoAppConfigStatusResponse = result ? new DemoAppConfigStatusResponse(result, "update successful") : new DemoAppConfigStatusResponse(result, errorMessage);
            System.out.println("end update parameters");
            return demoAppConfigStatusResponse;
        }
        System.out.println("end update parameters");
        return new DemoAppConfigStatusResponse(false,"update failed with error: parameters is null");

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
        this.updateParameterValue("Email_in_login", "No");
        this.updateParameterValue("ShipEx_repeat_calls", "0");
        this.updateParameterValue("Add_product_to_incorrect_category", "No");
        this.updateParameterValue("DB_call_delay", "0");
        this.updateParameterValue("User_alternate_WSDL", "No");
        this.updateParameterValue("Error_500", "No");
        this.updateParameterValue("Sum_added_to_cart", "0");
        this.updateParameterValue("Generate_memory_leak", "0");
        this.updateParameterValue("Typos_on_order_payment", "No");
        this.updateParameterValue("Misplace_pictures_on_Android", "No");
        this.updateParameterValue("SLA_add_delay_time", "0");
        this.updateParameterValue("SLA_add_delay_sessions", "20");
        this.updateParameterValue("Show_slow_pages", "No");
        this.updateParameterValue("Price_diffs_UI_vs_API", "No");
        this.updateParameterValue("Allow_Error_in_Login", "No");
        this.updateParameterValue("Implement_DevOps_Process", "No");

        return new DemoAppConfigStatusResponse(true, "\"DemoAppConfig.xml\" restore factory settings successful");
    }


}
