package com.advantage.online.store.image.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.IOHelper;
import com.advantage.util.fs.FileSystemHelper;
import com.advantage.util.xml.XmlHelper;
import com.advantage.util.xml.XmlItem;

class XmlImageManagement implements ImageManagement {

	private static final String TAG_IMAGE_MANAGEMENT = "ImageManagement";

	private static final String ATT_REPOSITORY_PATH = "repository-path";
	private static final String ATT_REPOSITORY_XML = "repository-xml";

	private final String repositoryDirectoryPath;
	private final String repositoryXmlPath;
	private final Map<String, XmlManagedImage> managedImagesMap;
	private final XmlItem imageManagementXmlItem;

	XmlImageManagement(final String repositoryDirectoryPath) throws IOException {

		this.repositoryDirectoryPath = repositoryDirectoryPath;
		repositoryXmlPath = repositoryDirectoryPath + "/imageManagement.xml";
		managedImagesMap = new HashMap<String, XmlManagedImage>();
		FileSystemHelper.makeDirectory(repositoryDirectoryPath);

		if (FileSystemHelper.isFileExist(repositoryXmlPath)) {

			final Document document = XmlHelper.getDocument(repositoryXmlPath);
			final Element documentElement = document.getDocumentElement();
			imageManagementXmlItem = new XmlItem(documentElement);
			final List<XmlItem> managedImagesXmlItems = imageManagementXmlItem.getChildrenByTagName(XmlManagedImage.TAG_MANAGED_IMAGE);

			for (final XmlItem managedImageXmlItem : managedImagesXmlItems) {

				final XmlManagedImage managedImage = new XmlManagedImage(this,
						                                                 managedImageXmlItem);
				final String managedImageId = managedImage.getId();
				managedImagesMap.put(managedImageId, managedImage);
			} 
		} else {

			final Document document = XmlHelper.newDocument();
			final Element element = document.createElement(XmlImageManagement.TAG_IMAGE_MANAGEMENT);
			imageManagementXmlItem = new XmlItem(element);
			element.setAttribute(XmlImageManagement.ATT_REPOSITORY_PATH, repositoryDirectoryPath);
			element.setAttribute(XmlImageManagement.ATT_REPOSITORY_XML, repositoryXmlPath);
			document.appendChild(element);
			final File[] files = FileSystemHelper.getDirectoryFiles(repositoryDirectoryPath,
					                                                "png", "jpg", "gif");

			if (files != null) {

				for (final File file : files) {
	
					addManagedImage(file, false);
				}
			}
		}
	}

	@Override
	public ManagedImage addManagedImage(final File imageFile,
	 final boolean copyToRepository) throws IOException {

		ArgumentValidationHelper.validateArgumentIsNotNull(imageFile, "image file");

		if (copyToRepository) {

			final byte[] fileContent = IOHelper.fileContentToByteArray(imageFile);
			final String imageFileName = imageFile.getName();
			final String newImageFilePath = figureManagedImageFilePath(imageFileName);
			IOHelper.outputInput(fileContent, newImageFilePath);
		}

		final UUID uid = UUID.randomUUID();
		final String uidString = uid.toString();
		final XmlManagedImage managedImage = new XmlManagedImage(this, uidString, imageFile);
		final String managedImageId = managedImage.getId();
		return managedImagesMap.put(managedImageId, managedImage);
	}

	@Override
	public ManagedImage addManagedImage(final String imageFilePath,
	 final boolean copyToRepository) throws IOException {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(imageFilePath,
				                                                            "image file path");
		final File imageFile = new File(imageFilePath);
		return addManagedImage(imageFile, copyToRepository);
	}

	@Override
	public void removeManagedImage(final String managedImageId) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(managedImageId,
                                                                            "managed image id");
		final XmlManagedImage xmlManagedImage = managedImagesMap.get(managedImageId);

		if (xmlManagedImage != null) {

			final XmlItem managedImageXmlItem = xmlManagedImage.getManagedImageXmlItem();
			imageManagementXmlItem.removeChild(managedImageXmlItem);
			managedImagesMap.remove(managedImageId);
		}
	}

	@Override
	public ManagedImage getManagedImage(final String managedImageId) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(managedImageId,
				                                                            "managed image id");
		return managedImagesMap.get(managedImageId);
	}

	@Override
	public List<ManagedImage> getManagedImages() {

		final Collection<XmlManagedImage> xmlManagedImages = managedImagesMap.values();
		final int managedImagesCount = xmlManagedImages.size();
		final List<ManagedImage> managedImages = new ArrayList<ManagedImage>(managedImagesCount);
		managedImages.addAll(xmlManagedImages);
		return managedImages;
	}

	@Override
	public void persist() throws IOException {

		final Document document = imageManagementXmlItem.getDocument();
		XmlHelper.persistDocument(document, repositoryXmlPath);
	}

	public XmlItem getImageManagementXmlItem() {

		return imageManagementXmlItem;
	}

	String figureManagedImageFilePath(final String fileName) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(fileName,
				                                                            "file name");
		final StringBuilder filePath = new StringBuilder(repositoryDirectoryPath);
		final String fileSeparator = FileSystemHelper.getFileSeparator();
		filePath.append(fileSeparator);
		filePath.append(fileName);
		return filePath.toString();
	}
}