package com.advantage.test.online.store.image;

//import java.util.HashMap;
//import java.util.Map;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.fs.FileSystemHelper;

/** 
 * @author Binyamin Regev
 * <br/>
 * This class purpose is to test ImageManagement interface, Abstract 
 * class and API.
 * <br/>
 * @see ImageManagement
 * @see ImageManagementAccess
 * @see ManagedImage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ImageManagementTests {

	//private static final Map<String, ImageManagement> imageManagementsMap = new HashMap<String, ImageManagement>();

	/**
	 * @author: Binyamin Regev
	 * <br/>
	 * Copy 5 files from <i>"C:\\Temp"</i> to <i>"C:\\Temp\\advantage1"</i> and then 
	 * use <ul>{@link Assert></ul> to verify that only 5 files were copied. 
	 * @throws IOException
	 */
	@Test
	public void testAddManagedImage() throws IOException {
		
		ImageManagement im = ImageManagementAccess.getImageManagement("C:\\Temp\\advantage1");
	
		im.addManagedImage("C:\\Temp\\image1.jpg", true);
		im.addManagedImage("C:\\Temp\\image2.jpg", true);
		im.addManagedImage("C:\\Temp\\image3.jpg", true);
		im.addManagedImage("C:\\Temp\\image4.jpg", true);
		im.addManagedImage("C:\\Temp\\image5.jpg", true);
	
		int managedImagesCount = im.getManagedImages().size();
		System.out.println(managedImagesCount);
		
		Assert.assertEquals(10, managedImagesCount);
	}
	
	/**
	 * @author: Binyamin Regev
	 * <br/>
	 * Use immediate <b>IIF</b> and <u>{@link Assert></u> to verify that the XML 
	 * repository file <b>"imageManagement.xml"</b> exists in directory 
	 * <b>"C:\\Temp\\advantage"</b>
	 * @throws IOException 
	 */
	@Test
	public void testIsFileExists() throws IOException {
		
		ImageManagement im = ImageManagementAccess.getImageManagement("C:\\Temp\\advantage");
		
		im.persist();
		
		int isFileExists = FileSystemHelper.isFileExist("C:\\Temp\\advantage\\imageManagement.xml") ? 1 : 0;

		Assert.assertEquals(isFileExists, 1);
	}
	
	/**
	 * Use {@link ImageManagementAccess} to add an image to directory "C:\\Temp\\advantage1", 
	 * then verify the image exists in the repository, by its ID. Then use method 
	 * <i>"removeManagedImage(managedImageId)"</i> to remove image from the repository, and 
	 * finally verify the image is no longer in the repository.
	 * <br/> 
	 * After calling method <i>removeManagedImage(imageId)</i> we expect calling 
	 * <i>getManagedFileName()</i> will return <b>Null</b>. 
	 * @throws IOException 
	 * @see ImageManagementAccess
	 * @see ManagedImage
	 */
	@Test(expected=NullPointerException.class)
	public void testImageExistsById() throws IOException {
		
		final String repositoryDirectoryPath = "C:\\Temp\\advantage1";

		ImageManagement im = ImageManagementAccess.getImageManagement(repositoryDirectoryPath);
		
		ManagedImage managedImage = im.addManagedImage("C:\\Temp\\image11.jpg", false);
		
		final String imageId = managedImage.getId();
		
		managedImage = im.getManagedImage(imageId);
		
		String expectedOutput = "image11.jpg";
		String actualOutput = im.getManagedImage(imageId).getManagedFileName();

		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(repositoryDirectoryPath,
                															"repository directory path");
		
		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(actualOutput, "Image Name");

		Assert.assertEquals(expectedOutput, actualOutput);
		
		im.removeManagedImage(imageId);

		actualOutput = im.getManagedImage(imageId).getManagedFileName();

		/* Test Failed: we did not get NullPointerException in the previous line */ 
		Assert.fail("Expected Null, but got [" + actualOutput + "]");

	}
	
}