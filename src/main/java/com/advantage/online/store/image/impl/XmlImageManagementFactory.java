package com.advantage.online.store.image.impl;

import java.io.IOException;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementFactory;

public class XmlImageManagementFactory extends ImageManagementFactory {

	@Override
	public ImageManagement getImageManagement(final String repositoryDirectoryPath) {

		try {

			return new XmlImageManagement(repositoryDirectoryPath);
		} catch (final IOException ex) {

			throw new RuntimeException(ex);
		}
	}
}