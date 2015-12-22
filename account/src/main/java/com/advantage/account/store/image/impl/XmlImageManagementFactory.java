package com.advantage.account.store.image.impl;

import com.advantage.account.store.image.ImageManagement;
import com.advantage.account.store.image.ImageManagementFactory;
import com.advantage.account.util.ArgumentValidationHelper;

import java.io.IOException;

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