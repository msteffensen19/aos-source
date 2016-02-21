package com.advantage.accountsoap.config;

import com.advantage.accountsoap.dto.DemoAppConfig.DemoAppConfigParameter;
import com.advantage.accountsoap.dto.DemoAppConfig.DemoAppConfigurationResponse;
import com.advantage.common.Constants;
import com.advantage.root.util.xml.XmlHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
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
 *  @author Binyamin Regev on 17/02/2016.
 */
@Configuration
@Qualifier("demoAppConfigurationXml")
public class DemoAppConfigurationXml {

    public static final String DEMO_APP_CONFIG_XML_FILE_NAME = "DemoAppConfig.xml";

    public static final String ROOT_ELEMENT_NAME = "Parameters";
    public static final String ELEMENTS_TAG_NAME = "tools";

    private File xmlFile = new File(DEMO_APP_CONFIG_XML_FILE_NAME);
    private Document doc;

    private Node parameters;        //  Root Element

    /**
     * Default Constructor
     */
    public DemoAppConfigurationXml() {
        this.setXmlFile(new File(DEMO_APP_CONFIG_XML_FILE_NAME));

        DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
        demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME));
        System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");
    }

    /**
     * @return
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * @param doc
     */
    public void setDoc(Document doc) {
        this.doc = doc;
    }

    /**
     * @return
     */
    public File getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile
     */
    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     *
     * @return
     */
    public Node getParameters() {
        return this.parameters;
    }

    /**
     *
     * @param parameters
     */
    public void setParameters(Node parameters) {
        this.parameters = parameters;
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
     * Update the value of a specific tool's parameter. Tool is identified by it's name.
     * @param parameterName     Parameter name linked to the specific tool.
     * @param parameterNewValue   Tool's new parameter value.
     */
    public DemoAppConfigurationResponse updateParameterValue(String parameterName, String parameterNewValue) {
        ////  Child Element - Tool
        //NodeList tool = doc.getElementsByTagName(ELEMENTS_TAG_NAME);

        boolean success = false;
        Node nodeToUpdate = findParameterByName(parameterName);
        if (nodeToUpdate != null) {
            nodeToUpdate.setNodeValue(parameterNewValue);
            return new DemoAppConfigurationResponse(true, "update successful");
        }
        return new DemoAppConfigurationResponse(false, "update failed with error: parameter name not found");
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

    public Node findParameterByName(String parameterName) {
        System.out.println("findParameterByName(\"" + parameterName + "\") - Begin");

        NodeList nodesList = this.getAllParametersNodeList();
        if (nodesList != null) {
            for (int i = 0; i < nodesList.getLength(); i++) {
                Node node = nodesList.item(i);

                if ((!node.getNodeName().equals("#comment")) && (! node.getNodeName().equals("#text"))) {

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

    private NodeList getAllParametersNodeList() {
        this.setParameters(this.getDoc().getElementsByTagName(ROOT_ELEMENT_NAME).item(0));

        NodeList nodesList = this.getParameters().getChildNodes();

        return (nodesList.getLength() != 0 ? nodesList : null);
    }

    public List<DemoAppConfigParameter> getAllDemoAppConfigParameters() {
        System.out.println("getAllDemoAppConfigParameters() - Begin");

        NodeList nodesList = this.getAllParametersNodeList();
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

        System.out.println("getAllDemoAppConfigParameters() - Begin");
        System.out.println("");

        return parameters;
    }

    public List<DemoAppConfigParameter> getAllParametersByTool(String toolName) {
        System.out.println("getAllParametersByTool(\"" + toolName + "\") - Begin");

        NodeList nodesList = this.getAllParametersNodeList();
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
        System.out.println("getAllParametersByTool(\"" + toolName + "\") - End");
        System.out.println("");

        return parameters;
    }

    public List<String> getDemoAppConfigXmlFile() {
        System.out.println("getDemoAppConfigXmlFile - Begin");
        NodeList nodesList = this.getAllParametersNodeList();
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
     * @param args
     */
    public static void main(String[] args) {

        try {
            DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
            demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DEMO_APP_CONFIG_XML_FILE_NAME));
            System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");

            demoAppConfigXml.getAllDemoAppConfigParameters();

            demoAppConfigXml.getAllParametersByTool("UFT");
            demoAppConfigXml.getAllParametersByTool("LeanFT");
            demoAppConfigXml.getAllParametersByTool("Sprinter");
            demoAppConfigXml.getAllParametersByTool("LoadRunner");

            // Get the staff element by tag name directly
            demoAppConfigXml.setParameters(demoAppConfigXml.getDoc().getElementsByTagName(ROOT_ELEMENT_NAME).item(0));

            NodeList nodesList = demoAppConfigXml.getParameters().getChildNodes();
            if (nodesList.getLength() == 0) {
                throw new Exception("No child nodes found for Root Element \"" + ROOT_ELEMENT_NAME + "\"");
            }

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

                        System.out.println("<" + node.getNodeName() + Constants.SPACE + ELEMENTS_TAG_NAME + "=\"" + attributeValue + "\">" + node.getTextContent() + "</" + node.getNodeName() + ">");
                        break;
                }
            }

            //// update staff attribute
            //NamedNodeMap attr = parameters.getAttributes();
            //Node nodeAttr = attr.getNamedItem("id");
            //nodeAttr.setTextContent("2");
            //
            //// append a new node to staff
            //Element age = demoAppConfigXml.getDoc().createElement("age");
            //age.appendChild(demoAppConfigXml.getDoc().createTextNode("28"));
            //parameters.appendChild(age);
            //
            //// loop the staff child node
            //NodeList list = parameters.getChildNodes();
            //
            //for (int i = 0; i < list.getLength(); i++) {
            //
            //    Node node = list.item(i);
            //
            //    // get the salary element, and update the value
            //    if ("salary".equals(node.getNodeName())) {
            //        node.setTextContent("2000000");
            //    }
            //
            //    //remove firstname
            //    if ("firstname".equals(node.getNodeName())) {
            //        parameters.removeChild(node);
            //    }
            //
            //}

            //// write the content into xml file
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            //DOMSource source = new DOMSource(demoAppConfigXml.getDoc());
            //StreamResult result = new StreamResult(new File(DEMO_APP_CONFIG_XML_FILE_NAME));
            //transformer.transform(source, result);
            //
            //System.out.println("Done");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}