package com.advantage.util.xml;

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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.IOHelper;

/**
 * A helper class for XML.
 * <br/>
 * @author eli.dali@hpe.com 
 */
public abstract class XmlHelper {

	private XmlHelper() {

		throw new UnsupportedOperationException();
	}

	/**
	 * Get a document from the file in the given path.
	 * @param filePath the path of the file, to a document from.
	 * @return a document from the file in the given path.
	 * @throws IOException if an I/O error occurs.
	 * @throws IllegalArgumentException if the given file path argument references
	 * <b>null</b>, or if it <b>is</b> a blank string.
	 */
	public static Document getDocument(final String filePath)
	 throws IOException {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
				                                                            "file path");
		InputStream in = null;

		try {

			final DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			in = new FileInputStream(filePath);
			return documentBuilder.parse(in);
		} catch (final ParserConfigurationException ex) {

			throw new RuntimeException(ex);
		} catch (final SAXException ex) {

			throw new RuntimeException(ex);
		} finally {

			IOHelper.closeInputStreamIfNotNull(in);
		}
	}

	public static Document newDocument() throws ParserConfigurationException {

		final DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		return documentBuilder.newDocument();
	}

	private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {

		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		return documentBuilderFactory.newDocumentBuilder();
	}

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
}