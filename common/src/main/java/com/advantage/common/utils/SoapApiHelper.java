package com.advantage.common.utils;

import com.advantage.common.dto.AppUserDto;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by dubilyer on 8/30/2016.
 */

// TODO: 8/30/2016 Change auth after changing soap to basic
public class SoapApiHelper {
    private final static String REQUEST_NAME_SPACE = "com";
    private final static String RESPONSE_NAME_SPACE = "ns2";
    private final static String DESTINATION = "com.advantage.online.store.accountservice";
    public final static String URL = "http://localhost:8080/accountservice/AccountLoginRequest";
    private static SOAPConnection soapConnection;

    /**
     * The method returns soap message ready to be sent.
     * @param requestName -                     e.g. "GetAccountByIdRequest"
     * @param data -                            Map of request parameters. Key - tag name,
     *                                          value - parameter value
     * @return SOAPMessage
     * @throws SOAPException
     */
    private static SOAPMessage createSOAPRequest(String requestName, HashMap<String, String> data) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(REQUEST_NAME_SPACE, DESTINATION);
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(requestName, REQUEST_NAME_SPACE);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            soapBodyElem
                    .addChildElement(entry.getKey(), REQUEST_NAME_SPACE)
                    .addTextNode(entry.getValue());
        }
        soapMessage.saveChanges();
        return soapMessage;
    }

    /**
     * The method is to send a previously generated SOAPMessage
     * @param request                               SOAPMessage
     * @return                             SOAPMessage responce
     * @throws SOAPException
     */
    private static SOAPMessage sendSoapMessage(SOAPMessage request) throws SOAPException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();
        //soapConnection.close();
        return soapConnection.call(request, URL);
    }

    /**
     * Returns xml parent node of all response parameters
     * @param soapResponse                      SOAPMessage
     * @param responseName                 name of Response
     * @return                                     Nodelist
     * @throws SOAPException
     */
    private static NodeList getRoot(SOAPMessage soapResponse, String responseName) throws SOAPException {
        SOAPEnvelope env = soapResponse.getSOAPPart().getEnvelope();
        SOAPBody sb = env.getBody();
        return sb.getElementsByTagName(RESPONSE_NAME_SPACE+":"+responseName).item(0).getChildNodes();
    }

    /**
     * Returns parameter from response according to the given node name
     * @param nodeName                                     String value
     * @param root                                      Parent nodelist
     * @return                                String value of parameter
     */
    private static String getResponseValue(String nodeName, NodeList root){
        for (int i = 0; i < root.getLength(); i++) {
            if(root.item(i).getNodeName().equals(RESPONSE_NAME_SPACE+":"+nodeName)){
                return root.item(i).getTextContent();
            }
        }
        throw new NoSuchElementException("There's no parameter "+nodeName + "in response.");
    }

    public static AppUserDto getUserById(long id) throws SOAPException {
        HashMap<String, String> data = new HashMap<>();
        data.put("accountId", "" + id);
        data.put("base64Token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cuYWR2YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjo5Nywic3ViIjoiZ3Vlc3QyIiwicm9sZSI6IlVTRVIifQ.eajlekjoDzAXdghjxAcSVqquHgWjMKUrflN0SewLo2A");
        SOAPMessage soapResponse = sendSoapMessage(createSOAPRequest("GetAccountByIdRequest", data));
        NodeList root = getRoot(soapResponse, "AccountResponse");
        String userName = getResponseValue("loginName", root);
        String userPassword = getResponseValue("loginPassword", root);
        int accountType = Integer.valueOf(getResponseValue("accountType", root));
        long userId = Long.valueOf(getResponseValue("id", root));
        soapConnection.close();
        return new AppUserDto(userName, userPassword, userId, accountType);
    }

    public static AppUserDto getUserByLogin(String userName) throws SOAPException  {
        HashMap<String, String> data = new HashMap<>();
        data.put("userName", userName);
        data.put("base64Token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cuYWR2YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjo5Nywic3ViIjoiZ3Vlc3QyIiwicm9sZSI6IlVTRVIifQ.eajlekjoDzAXdghjxAcSVqquHgWjMKUrflN0SewLo2A");
        SOAPMessage soapResponse = sendSoapMessage(createSOAPRequest("GetAccountByLoginRequest", data));
        NodeList root = getRoot(soapResponse, "AccountResponse");
        String userPassword = getResponseValue("loginPassword", root);
        int accountType = Integer.valueOf(getResponseValue("accountType", root));
        long userId = Long.valueOf(getResponseValue("id", root));
        soapConnection.close();
        return new AppUserDto(userName, userPassword, userId, accountType);
    }

    public static String encodePassword(String userName, String password) throws SOAPException {
        HashMap<String, String> data = new HashMap<>();
        data.put("userName", userName);
        data.put("password", password);
        SOAPMessage soapResponse = sendSoapMessage(createSOAPRequest("EncodePasswordRequest", data));
        NodeList root = getRoot(soapResponse, "EncodePasswordResponse");
        String encodedPassword = getResponseValue("password", root);
        soapConnection.close();
        return encodedPassword;
    }
}

