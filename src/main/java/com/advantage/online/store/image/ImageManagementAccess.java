package com.advantage.online.store.image;

import java.util.HashMap;
import java.util.Map;

import com.advantage.util.ArgumentValidationHelper;

public abstract class ImageManagementAccess {

	private static final Map<String, ImageManagement> imageManagementsMap = new HashMap<String, ImageManagement>();

	private ImageManagementAccess() {

		throw new UnsupportedOperationException();
	}

	public static synchronized ImageManagement getImageManagement(final String repositoryDirectoryPath) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(repositoryDirectoryPath,
				                                                            "repository directory path");
		final ImageManagement imageManagement;

		if (imageManagementsMap.containsKey(repositoryDirectoryPath)) {

			imageManagement = imageManagementsMap.get(repositoryDirectoryPath);
		} else {

			final ImageManagementFactory imageManagementFactory = ImageManagementFactory.getImageManagementFactory();
			imageManagement = imageManagementFactory.getImageManagement(repositoryDirectoryPath);
			imageManagementsMap.put(repositoryDirectoryPath, imageManagement);
		}

		return imageManagement;
	}
	
	public static void main(String[] args) {
		
		try {
			
			ImageManagement ima = ImageManagementAccess.getImageManagement("c:/temp/kuku");
			ima.addManagedImage("C:\\Temp\\Laptop.jpg", true);
			ima.addManagedImage("C:\\Temp\\instegram.jpg", true);
			ima.persist();
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}
}