package com.advantage.order.store.order.util;

import com.advantage.root.util.StringHelper;
import com.predic8.schema.Element;
import com.predic8.schema.Schema;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regeb on 30/12/2015.
 */
public class WSDLHelper {

    /**
     * Get content of WSDL file as a {@link String}.
     * @param urlWsdlFile
     * @return
     */
    public static String getWsdlFile(String urlWsdlFile) {

        String content = null;

        URL url = null;
        URLConnection urlConnection = null;

        try {
            url = new URL(urlWsdlFile);
            urlConnection = url.openConnection();
            if(urlConnection.getContent() != null) {
                //System.out.println("\'" + urlWsdlFile + "\' is GOOD URL");

                String contentType = urlConnection.getContentType();

                content = StringHelper.getStringFromInputStream(urlConnection.getInputStream());

                //System.out.println("Content: \n" + content.toString());

            } else {
                System.out.println("BAD URL");
            }
        } catch (MalformedURLException ex) {
            System.out.println("bad URL");
        } catch (IOException ex) {
            System.out.println("Failed opening connection. Perhaps WS is not up?");
        }

        return content;

    }

    /**
     * Get {@link List} of {@link Operation}s in WSDL file.
     * @param stringURL
     * @param wsdlFileName
     * @return
     */
    public static List<Operation> getListWSDLOperations(String stringURL, String wsdlFileName) {
        List<Operation> operations = new ArrayList<>();

        WSDLParser parser = new WSDLParser();

        Definitions wsdl = parser.parse(stringURL + wsdlFileName);

        Schema schema = wsdl.getSchema(stringURL);
        if (schema != null) {
            List<Element> elements = schema.getAllElements();
            for (Element element: elements) {

            }
        }
        for (PortType pt : wsdl.getPortTypes()) {
            System.out.println(pt.getName());
            for (Operation operation : pt.getOperations()) {
                operations.add(operation);
            }
        }

        List<String> listOperations = new ArrayList<>();

        System.out.println("-=# Operations List - Begin #=-");
        for (Operation op : operations) {
            listOperations.add(op.getName());
            System.out.println("* " + op.getName());
        }
        System.out.println("-=# Operations List - End   #=-");

        return operations;
    }

    /**
     * Get list of operations names in WSDL file.
     * @param stringURL
     * @param wsdlFileName
     * @return
     */
    public static List<String> getListWSDLOperationsNames(String stringURL, String wsdlFileName) {
        List<Operation> operations = WSDLHelper.getListWSDLOperations(stringURL, wsdlFileName);

        List<String> listOperations = new ArrayList<>();

        for (Operation op : operations) {
            listOperations.add(op.getName());
        }

        return listOperations;
    }

}
