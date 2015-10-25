package com.advantage.util;

import java.io.*;

/**
 * A helper class for input / output operations.
 */
public abstract class IOHelper {

    private IOHelper() {

        throw new UnsupportedOperationException();
    }

    public static byte[] fileContentToByteArray(final String filePath) throws IOException {

        final InputStream in = new FileInputStream(filePath);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOHelper.outputInput(in, out);
        return out.toByteArray();
    }

    public static void outputInput(final InputStream in, final OutputStream out) throws IOException {

        ArgumentValidationHelper.validateArgumentIsNotNull(in, "input stream");
        ArgumentValidationHelper.validateArgumentIsNotNull(out, "output stream");
        final byte[] buffer = new byte[512];
        int bytesReadcount;

        while ((bytesReadcount = in.read(buffer)) != -1) {

            out.write(buffer, 0, bytesReadcount);
        }
    }
}