package com.advantage.online.store.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.IOHelper;

@SuppressWarnings("serial")
public class FetchImageHttpServlet extends HttpServlet {

	private static final String INIT_PARAM_REPOSITORY_DIRECTORY_PATH = "repository-directory-path";

	public static final String REQUEST_PARAM_IMAGE_ID = "image_id";

	private ImageManagement imageManagement;

	@Override
	public void init() {

		final String repositoryDirectoryPath = getInitParameter(FetchImageHttpServlet.INIT_PARAM_REPOSITORY_DIRECTORY_PATH);
		imageManagement = ImageManagementAccess.getImageManagement(repositoryDirectoryPath);
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse res)
	 throws ServletException, IOException {

		ArgumentValidationHelper.validateArgumentIsNotNull(req,  "HTTP servlet request");
		ArgumentValidationHelper.validateArgumentIsNotNull(res,  "HTTP servlet response");
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
}