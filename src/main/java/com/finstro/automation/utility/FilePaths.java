package com.finstro.automation.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;


public class FilePaths {
	
	
	public static String getRootProject() {
		
		return System.getProperty("user.dir");
		
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static String getResourcePath(String filename) throws Exception {
		
		URL rsc = FilePaths.class.getResource(filename);
		return Paths.get(rsc.toURI()).toFile().getAbsolutePath();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public static String getReportFolder() throws IOException {
		
		String reportFolder =  getRootProject() + File.separator + "Reports" + File.separator;
		createDirectory(reportFolder);
		
		return reportFolder;
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public static String getScreenshotFolder() throws IOException {
		
		String screenshotFolder = getReportFolder() + File.separator + "Screenshots" + File.separator;
		createDirectory(screenshotFolder);
		return screenshotFolder;
				
	}
	
	/**
	 * @param timeStamp
	 * @return
	 */
	public static String getCurrentDateTimeString(String timeStamp) {
		
		DateFormat dateFormat = new SimpleDateFormat(timeStamp);
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	
	/**
	 * Correct the file path based on the OS system type
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static String correctPath(String path) {

		return path.replaceAll("\\\\|/", "\\" + System.getProperty("file.separator"));

	}

	/**
	 * Verify file or path is existing on system
	 * 
	 * @param path
	 *            the path to file
	 */
	public static boolean pathExist(String path) {
		
			return Files.exists(new File(path).toPath());

	}

	/**
	 * Delete a file or a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws IOException 
	 */
	public static void deletePath(String path) throws IOException  {
		
			Files.delete(new File(path).toPath());

	}

	/**
	 * create a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static void createFile(String path) throws Exception {
		
			if (!pathExist(path))
				Files.createFile(new File(path).toPath());
	}

	/**
	 * create a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void createDirectory(String path) throws IOException  {
		
			if (!pathExist(path))
				Files.createDirectory(new File(path).toPath());
	}

	/**
	 * To copy a file from the source file to the destination file
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException 
	 */
	public static void copyFile(String src, String dest) throws IOException  {
		
			File sourceFile = new File(src);
			File destFile = new File(dest);
			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}

	/**
	 * To copy a folder from the source folder to the destination folder
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException 
	 */
	public static void copyDirectory(String src, String dest) throws IOException {
		
			File sourceFile = new File(src);
			File destFile = new File(dest);
			createDirectory(dest);
			FileUtils.copyDirectory(sourceFile, destFile, true);

	}
}
