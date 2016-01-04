/*
package com.advantage.root.store.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.root.store.image.ImageManagement;
import com.advantage.root.store.image.ImageManagementAccess;
import com.advantage.root.store.image.ManagedImage;
import com.advantage.common.ArgumentValidationHelper;
import com.advantage.common.util.IOHelper;
import org.apache.commons.lang3.StringUtils;

import org.springframework.core.io.ClassPathResource;

@SuppressWarnings("serial")
public class FetchImageHttpServlet extends HttpServlet {

    private static final String INIT_PARAM_REPOSITORY_DIRECTORY_PATH = "repository-directory-path";

    public static final String REQUEST_PARAM_IMAGE_ID = "image_id";

    private ImageManagement imageManagement;

    @Override
    public void init() throws ServletException {
        final String repositoryDirectoryPath;
        try {
            repositoryDirectoryPath = getPath();
            if (StringUtils.isBlank(repositoryDirectoryPath)) {
                final String errorMessageString =
                        "Init parameter [" + FetchImageHttpServlet.INIT_PARAM_REPOSITORY_DIRECTORY_PATH + "] must be set";
                throw new ServletException(errorMessageString);
            }

            imageManagement = ImageManagementAccess.getImageManagement(repositoryDirectoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res)
            throws ServletException, IOException {

        ArgumentValidationHelper.validateArgumentIsNotNull(req, "HTTP servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(res, "HTTP servlet response");
        final String imageId = req.getParameter(FetchImageHttpServlet.REQUEST_PARAM_IMAGE_ID);
        final ManagedImage managedImage = imageManagement.getManagedImage(imageId);
        final StringBuilder contentType = new StringBuilder("image/");
        final String imageType = managedImage.getType();
        contentType.append(imageType);
        final String contentTypeString = contentType.toString();
        res.setContentType(contentTypeString);
        final OutputStream out = res.getOutputStream();
        final byte[] imageContent = managedImage.getContent();
        IOHelper.outputInput(imageContent, out);
    }

    private String getPath() throws IOException {
        ClassPathResource filePath = new ClassPathResource("app.properties");
        File file = filePath.getFile();

        return file.getPath().split("WEB-INF")[0] +
                getInitParameter(FetchImageHttpServlet.INIT_PARAM_REPOSITORY_DIRECTORY_PATH);

    }
}*/
