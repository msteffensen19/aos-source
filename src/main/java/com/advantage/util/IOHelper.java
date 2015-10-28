package com.advantage.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
     * @throws IllegalArgumentException if the given file path argument references
     * <b>null</b>, or if it <b>is</b> a blank string.
     */
    public static byte[] fileContentToByteArray(final String filePath) throws IOException {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
        		                                                            "file path");
        final InputStream in = new FileInputStream(filePath);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOHelper.outputInput(in, out);
        return out.toByteArray();
    }

    /**
     * Read from the given input and write the content that was read to the given output
     * stream.
     * @param in the input stream to read from.
     * @param out the output stream to write to.
     * @param bufferSize the size of the buffer to be used in each read operation.
     * @throws IOException if an I/O error occurs.
     * @throws IllegalArgumentException if any one of the <b>object</b> arguments references
     * <b>null</b>, or if the given buffer size argument is not positive.
     */
    public static void outputInput(final InputStream in, final OutputStream out,
     final int bufferSize) throws IOException {

        ArgumentValidationHelper.validateArgumentIsNotNull(in, "input stream");
        ArgumentValidationHelper.validateArgumentIsNotNull(out, "output stream");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(bufferSize, "buffer size");
        final byte[] buffer = new byte[bufferSize];
        int numberOfBytesRead;

        while ((numberOfBytesRead = in.read(buffer)) != -1) {

            out.write(buffer, 0, numberOfBytesRead);
        }
    }

    /**
     * Read from the given input and write the content that was read to the given output
     * stream.
     * @param in the input stream to read from.
     * @param out the output stream to write to.
     * @throws IOException if an I/O error occurs.
     * @throws IllegalArgumentException if any one of the <b>object</b> arguments references
     * <b>null</b>.
     */
    public static void outputInput(final InputStream in, final OutputStream out)
     throws IOException {

    	ArgumentValidationHelper.validateArgumentIsNotNull(in, "input stream");
        ArgumentValidationHelper.validateArgumentIsNotNull(out, "output stream");
        IOHelper.outputInput(in, out, 512);
    }
}