package com.advantage.accountsoap;

import com.advantage.accountsoap.AccountserviceApplication;
import com.advantage.accountsoap.config.DemoAppConfigurationXml;
import com.advantage.root.util.xml.XmlHelper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccountserviceApplication.class)
public class DemoAppConfigurationXmlTests {

    private static final int CONFIG_PARAM_LEAN_FT = 4;
    private static final int CONFIG_PARAM_LOAD_RUNNER = 4;
    private static final int CONFIG_PARAM_MOBILE_CENTER = 1;
    private static final int CONFIG_PARAM_NV = 1;
    private static final int CONFIG_PARAM_SPRINTER = 1;
    private static final int CONFIG_PARAM_STORM_RUNNER = 1;
    private static final int CONFIG_PARAM_SV = 1;
    private static final int CONFIG_PARAM_UFT = 3;

    @Ignore
    @Test
	public void contextLoads() {
	}

    @Test
    public void testGetAllDemoAppConfigParameters() {
        try {
            DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
            demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DemoAppConfigurationXml.DEMO_APP_CONFIG_XML_FILE_NAME));
            System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");

            demoAppConfigXml.getAllDemoAppConfigParameters();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDemoAppConfigParameterByTool() {
        try {
            DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
            demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DemoAppConfigurationXml.DEMO_APP_CONFIG_XML_FILE_NAME));
            System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");

            long numberOfParameters = demoAppConfigXml.getAllParametersByTool("LeanFt").size();
            Assert.assertEquals("For \"LeanFT\": Expected " + CONFIG_PARAM_LEAN_FT + ", but got " + numberOfParameters, CONFIG_PARAM_LEAN_FT, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("LoadRunner").size();
            Assert.assertEquals("For \"LoadRunner\": Expected " + CONFIG_PARAM_LOAD_RUNNER + ", but got " + numberOfParameters, CONFIG_PARAM_LOAD_RUNNER, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("MobileCenter").size();
            Assert.assertEquals("For \"MobileCenter\": Expected " + CONFIG_PARAM_MOBILE_CENTER + ", but got " + numberOfParameters, CONFIG_PARAM_MOBILE_CENTER, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("NV").size();
            Assert.assertEquals("For \"NV\": Expected " + CONFIG_PARAM_NV + ", but got " + numberOfParameters, CONFIG_PARAM_NV, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("Sprinter").size();
            Assert.assertEquals("For \"Sprinter\": Expected " + CONFIG_PARAM_SPRINTER + ", but got " + numberOfParameters, CONFIG_PARAM_SPRINTER, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("StormRunner").size();
            Assert.assertEquals("For \"StormRunner\": Expected " + CONFIG_PARAM_STORM_RUNNER + ", but got " + numberOfParameters, CONFIG_PARAM_STORM_RUNNER, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("SV").size();
            Assert.assertEquals("For \"SV\": Expected " + CONFIG_PARAM_SV + ", but got " + numberOfParameters, CONFIG_PARAM_SV, numberOfParameters);

            numberOfParameters = demoAppConfigXml.getAllParametersByTool("UFT").size();
            Assert.assertEquals("For \"UFT\": Expected " + CONFIG_PARAM_UFT + ", but got " + numberOfParameters, CONFIG_PARAM_UFT, numberOfParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindDemoAppConfigParameterByName() {
        try {
            DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
            demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DemoAppConfigurationXml.DEMO_APP_CONFIG_XML_FILE_NAME));
            System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");

            Assert.assertNotNull("Expected to find parameter [Add_wrong_product_to_speakers_category], but got [null]", demoAppConfigXml.findParameterByName("Add_wrong_product_to_speakers_category"));
            Assert.assertNotNull("Expected to find parameter [Email_address_in_login], but got [null]", demoAppConfigXml.findParameterByName("Email_address_in_login"));
            Assert.assertNotNull("Expected to find parameter [Generate_memory_leak], but got [null]", demoAppConfigXml.findParameterByName("Generate_memory_leak"));
            Assert.assertNotNull("Expected to find parameter [Max_concurrent_sessions], but got [null]", demoAppConfigXml.findParameterByName("Max_concurrent_sessions"));
            Assert.assertNotNull("Expected to find parameter [Mix_pictures_in_home_page], but got [null]", demoAppConfigXml.findParameterByName("Mix_pictures_in_home_page"));
            Assert.assertNotNull("Expected to find parameter [Repeat_ShipEx_call], but got [null]", demoAppConfigXml.findParameterByName("Repeat_ShipEx_call"));
            Assert.assertNotNull("Expected to find parameter [Slow_DB_call], but got [null]", demoAppConfigXml.findParameterByName("Slow_DB_call"));
            Assert.assertNotNull("Expected to find parameter [Spelling_mistakes_in_order_payment_page], but got [null]", demoAppConfigXml.findParameterByName("Spelling_mistakes_in_order_payment_page"));
            Assert.assertNotNull("Expected to find parameter [Sum_to_add_to_cart_calculation], but got [null]", demoAppConfigXml.findParameterByName("Sum_to_add_to_cart_calculation"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDemoAppConfigXmlFile() {
        try {
            DemoAppConfigurationXml demoAppConfigXml = new DemoAppConfigurationXml();
            demoAppConfigXml.setDoc(XmlHelper.getXmlDocument(DemoAppConfigurationXml.DEMO_APP_CONFIG_XML_FILE_NAME));
            System.out.println("Document URL\"" + demoAppConfigXml.getDoc().getDocumentURI() + "\"");

            List<String> lines = demoAppConfigXml.getDemoAppConfigXmlFile();
            Assert.assertNotNull("Expected lines of \"DemoAppConfig.xml\" file, but got [null]", lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
