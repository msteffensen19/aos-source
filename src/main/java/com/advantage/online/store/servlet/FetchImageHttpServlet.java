package com.advantage.online.store.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.util.IOHelper;

@SuppressWarnings("serial")
public class FetchImageHttpServlet extends HttpServlet {

	// TODO: implement the method properly - this is a temporary implementation
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse res)
	 throws ServletException, IOException {

		res.setContentType("image/png");
		final OutputStream out = res.getOutputStream();
		
		final byte[] imageBytes = IOHelper.fileContentToByteArray("C:/Temp/advantage/Bags.png");
		IOHelper.outputInput(new ByteArrayInputStream(imageBytes), out);
	}
}