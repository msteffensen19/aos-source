package com.advantage.util.fs;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang3.StringUtils;

import com.advantage.util.ArgumentValidationHelper;

/**
 * A helper class for file system issues.
 * <br/>
 * @author eli.dali@hpe.com 
 */
public abstract class FileSystemHelper {

	private FileSystemHelper() {

		throw new UnsupportedOperationException();
	}

	/**
	 * Check if a file in the given path exists.
	 * <br/>
	 * This method checks for the existence of an actual file - if there is a directory in
	 * the given path, it will not be considered as a file.
	 * @param filePath the path of the file to check for it's existence.
	 * @return <b>true</b> if a file in the given path exists, or <b>false</b> otherwise.
	 * @throws IllegalArgumentException if the given file path argument references
	 * <b>null</b>, or if it <b>is</b> a blank string.
	 */
	public static boolean isFileExist(final String filePath) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(filePath,
				                                                            "file path");
		final File file = new File(filePath);
		return file.exists() && file.isFile() && file.isDirectory() == false;
	}

	/**
	 * Check if a directory in the given path exists.
	 * <br/>
	 * This method checks for the existence of an actual directory - if there is a file in
	 * the given path, it will not be considered as a directory.
	 * @param directoryPath the path of the directory to check for it's existence.
	 * @return <b>true</b> if a directory in the given path exists, or <b>false</b>
	 * otherwise.
	 */
	public static boolean isDirectoryExist(final String directoryPath) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(directoryPath,
				                                                            "directory path");
		final File directory = new File(directoryPath);
		return directory.exists() && directory.isDirectory() && directory.isFile() == false;
	}

	/**
	 * Extract the file extension from the given string of file name, or file path.
	 * <br/>
	 * For example:
	 * <ul>
	 * 	   <li>
	 *         if the given file name is &quot;test.txt&quot;, this method will return the
	 *         string &quot;txt&quot;.
	 *     </li>
	 *     <li>
	 *         if the given file name is &quot;C:/temp/test.txt&quot;, this method will
	 *         return the string &quot;txt&quot;.
	 *     </li>
	 *     <li>
	 *         if the given file name is &quot;test&quot;, this method will return an empty
	 *         string (&quot;&quot;).
	 *     </li>
	 * </ul>
	 * @param fileNameOrFilePath a string with a name of a file, or a full path of a file.
	 * @return file extension, or an empty string, if there is no file extension.
	 * @throws IllegalArgumentException if the given argument for file name (or file path),
	 * references <b>null</b>, or if it <b>is</b> a blank string.
	 */
	public static String extractFileExtension(final String fileNameOrFilePath) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(fileNameOrFilePath,
                                                                            "file name");
		final int lastIndexOfDot = fileNameOrFilePath.lastIndexOf(".");
		final String extension;

		if (lastIndexOfDot == -1) {

			extension = StringUtils.EMPTY;
		} else {

			final int indexOfExtension = lastIndexOfDot + 1;
			extension = fileNameOrFilePath.substring(indexOfExtension);
		}

		return extension;
	}

	/**
	 * Get the files of the directory in the given path. Optionally, this method can filter
	 * the files, so that only files with an extension that matches one of the given
	 * file extensions (if passed), will be returned.
	 * @param directoryPath the path of the directory to get the files of.
	 * @param extensions an optional argument - if extensions are passed to the method, only
	 * files with a matching extension will be returned.
	 * @return the files of the directory in the given path.
	 * @throws IllegalArgumentException if the given directory path argument references
	 * <b>null</b>, or if it <b>is</b> a blank string.
	 */
	public static File[] getDirectoryFiles(final String directoryPath,
	 final String... extensions) {

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(directoryPath,
                                                                            "directory path");
		final File directory = new File(directoryPath);
		final File[] files;

		if (extensions == null || extensions.length == 0) {

			files = directory.listFiles();
		} else {

			final FilenameFilter filenameFilter = new ExtensionsFilenameFilter(extensions);
			files = directory.listFiles(filenameFilter);
		}

		return files;
	}

	/**
	 * Get a file separator string. This method will try to get the file separator that is
	 * configured in the Java system properties. If there is no such a property, it will
	 * return, by default, the string <b>&quot;/&quot;</b>.
	 * @return a file separator string.
	 */
	public static String getFileSeparator() {

		return System.getProperty("file.separator", "/");
	}
}