package com.advantage.root.util.xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.root.util.IOHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * A helper class for XML.
 * <br/>
 *
 * @author eli.dali@hpe.com
 */
public abstract class XmlHelper {

    private XmlHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Get XML {@link Document} from the file in the given path.
     * @param xmlFilePath
     * @return XML {@link Document} from the file in the given path.
     * @throws IOException              if an I/O error occurs.
     * @throws IllegalArgumentException if the given file path argument references
     *                                  <b>null</b>, or if it <b>is</b> a blank string.
     */
    public static Document getXmlDocument(final String xmlFilePath) {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(xmlFilePath, "file path");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.parse(xmlFilePath);
            document.getDocumentElement().normalize();

            return document;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }

        return null;
    }

    /**
     * Get a document from the file in the given path.
     *
     * @param filePath the path of the file, to a document from.
     * @return a document from the file in the given path.
     * @throws IOException              if an I/O error occurs.
     * @throws IllegalArgumentException if the given file path argument references
     *                                  <b>null</b>, or if it <b>is</b> a blank string.
     */
    public static Document getDocument(final String filePath)
            throws IOException {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
                "file path");
        final DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
        InputStream in = null;

        try {

            in = new FileInputStream(filePath);
            return documentBuilder.parse(in);
        } catch (final SAXException ex) {

            throw new RuntimeException(ex);
        } finally {

            IOHelper.closeInputStreamIfNotNull(in);
        }
    }

    /**
     * Create A new document.
     *
     * @return the newly created document.
     */
    public static Document newDocument() {

        final DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
        return documentBuilder.newDocument();
    }

    /**
     * Persist the given document, in the file in the given path.
     *
     * @param document the document to persist.
     * @param filePath the path of the file to persist the document in.
     * @throws IOException              if an I/O error occurs.
     * @throws IllegalArgumentException if any one of the arguments references <b>null</b>,
     *                                  or if the given file path argument <b>is</b> a blank string.
     */
    public static void persistDocument(final Document document, final String filePath)
            throws IOException {

        ArgumentValidationHelper.validateArgumentIsNotNull(document, "document");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
                "file path");
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        OutputStream out = null;

        try {

            final Transformer transformer = transformerFactory.newTransformer();
            final Source inputSource = new DOMSource(document.getDocumentElement());
            out = new FileOutputStream(filePath);
            final Result result = new StreamResult(out);
            transformer.transform(inputSource, result);
        } catch (final TransformerException ex) {

            throw new RuntimeException(ex);
        } finally {

            IOHelper.closeOutputStreamIfNotNull(out);
        }
    }

    private static DocumentBuilder getDocumentBuilder() {

        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            return documentBuilderFactory.newDocumentBuilder();
        } catch (final ParserConfigurationException ex) {

            throw new RuntimeException(ex);
        }
    }
}