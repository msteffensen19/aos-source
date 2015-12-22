package com.advantage.order.store.image.impl;

import java.io.IOException;

import com.advantage.order.store.image.ImageManagementFactory;
import com.advantage.order.util.ArgumentValidationHelper;
import com.advantage.order.store.image.ImageManagement;

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