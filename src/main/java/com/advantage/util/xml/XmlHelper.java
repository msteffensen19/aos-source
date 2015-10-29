package com.advantage.util.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

	public static Document getDocument(final String filePath)
	 throws IOException, ParserConfigurationException, SAXException {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
				                                                            "file path");
		final DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		InputStream in = null;

		try {

			in = new FileInputStream(filePath);
			return documentBuilder.parse(in);
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
}