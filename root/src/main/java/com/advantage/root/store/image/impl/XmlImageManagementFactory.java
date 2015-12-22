package com.advantage.root.store.image.impl;

import java.io.IOException;

import com.advantage.root.store.image.ImageManagement;
import com.advantage.root.store.image.ImageManagementFactory;
import com.advantage.root.util.ArgumentValidationHelper;

public class XmlImageManagementFactory extends ImageManagementFactory {

    @Override
    public ImageManagement getImageManagement(final String repositoryDirectoryPath) {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(repositoryDirectoryPath,
                "repository directory path");

        try {

            return new XmlImageManagement(repositoryDirectoryPath);
        } catch (final IOException ex) {

            throw new RuntimeException(ex);
        }
    }
}