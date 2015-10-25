package com.advantage.util;

import java.io.*;

/**
 * A helper class for input / output operations.
 */
public abstract class IOHelper {

    private IOHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Get the content of the file in the given file path, as a byte array.
     * @param filePath the path of the file to get it's content as a byte array.
     * @return the content of the file in the given file path, as a byte array.
     * @throws IOException if an I/O error occurs.
     * @throws IllegalArgumentException if the given file path argument references <b>null</b>, or if it <b>is</b> a
     * blank string.
     */
    public static byte[] fileContentToByteArray(final String filePath) throws IOException {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath, "file path");
        final InputStream in = new FileInputStream(filePath);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOHelper.outputInput(in, out);
        return out.toByteArray();
    }

    /**
     * Read from the given input and write the content that was read to the given output stream.
     * @param in the input stream to read from.
     * @param out the output stream to write to.
     * @throws IOException if an I/O error occurs.
     * @throws IllegalArgumentException if any one of the arguments references <b>null</b>.
     */
    public static void outputInput(final InputStream in, final OutputStream out) throws IOException {

        ArgumentValidationHelper.validateArgumentIsNotNull(in, "input stream");
        ArgumentValidationHelper.validateArgumentIsNotNull(out, "output stream");
        final byte[] buffer = new byte[512];
        int numberOfBytesRead;

        while ((numberOfBytesRead = in.read(buffer)) != -1) {

            out.write(buffer, 0, numberOfBytesRead);
        }
    }
}