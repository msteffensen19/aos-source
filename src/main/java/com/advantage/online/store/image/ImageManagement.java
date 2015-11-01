package com.advantage.online.store.image;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ImageManagement {

	ManagedImage addManagedImage(File imageFile, boolean copyToRepository) throws IOException;
	ManagedImage addManagedImage(String imageFilePath, boolean copyToRepository)
	 throws IOException;
	void removeManagedImage(String managedImageId);
	ManagedImage getManagedImage(String managedImageId);
	List<ManagedImage> getManagedImages();
	void persist() throws IOException;
}