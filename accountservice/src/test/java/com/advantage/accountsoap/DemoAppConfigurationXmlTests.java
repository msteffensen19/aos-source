package com.advantage.accountsoap;

import com.advantage.accountsoap.config.DemoAppConfigurationXml;
import com.advantage.accountsoap.dto.account.DemoAppConfigParameter;
import com.advantage.accountsoap.services.DemoAppConfigService;
import com.advantage.root.util.xml.XmlHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AccountserviceApplication.class)
public class DemoAppConfigurationXmlTests {

    private static final int DEMO_APP_CONFIG_PARAMETERS_TOTAL_NUMBER = 9;
    private static final int CONFIG_PARAM_LEAN_FT = 4;
    private static final int CONFIG_PARAM_LOAD_RUNNER = 4;
    private static final int CONFIG_PARAM_MOBILE_CENTER = 1;
    private static final int CONFIG_PARAM_NV = 1;
    private static final int CONFIG_PARAM_SPRINTER = 1;
    private static final int CONFIG_PARAM_STORM_RUNNER = 1;
    private static final int CONFIG_PARAM_SV = 1;
    private static final int CONFIG_PARAM_UFT = 3;

    @Autowired
    private DemoAppConfigService service;

    @Autowired
    private DemoAppConfigurationXml demoAppConfigurationXml;

    @Before
    public void init() {
        demoAppConfigurationXml.setDoc(XmlHelper.getXmlDocument(demoAppConfigurationXml.DEMO_APP_CONFIG_XML_FILE_NAME));
        System.out.println("Document URL\"" + demoAppConfigurationXml.getDoc().getDocumentURI() + "\"");
    }

    @Ignore
    @Test
	public void contextLoads() {
	}

    @Ignore
    @Test
    public void testGetAllDemoAppConfigParameters() {
        try {

            List<DemoAppConfigParameter> response = service.getAllDemoAppConfigParameters();
            Assert.assertEquals("Expected " + DEMO_APP_CONFIG_PARAMETERS_TOTAL_NUMBER + " total parameters, but got " + response.size(), DEMO_APP_CONFIG_PARAMETERS_TOTAL_NUMBER, response.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void testGetDemoAppConfigParameterByTool() {
        //  region Test LeanFT
        List<DemoAppConfigParameter> response = service.getDemoAppConfigParametersByTool("LeanFt");
        long numberOfParameters = response.size();
        Assert.assertEquals("For \"LeanFT\": Expected " + CONFIG_PARAM_LEAN_FT + ", but got " + numberOfParameters, CONFIG_PARAM_LEAN_FT, numberOfParameters);
        //  endregion

        //  region Test LoadRunner
        response = service.getDemoAppConfigParametersByTool("LoadRunner");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"LoadRunner\": Expected " + CONFIG_PARAM_LOAD_RUNNER + ", but got " + numberOfParameters, CONFIG_PARAM_LOAD_RUNNER, numberOfParameters);
        //  endregion

        //  region Test MobileCenter
        response = service.getDemoAppConfigParametersByTool("MobileCenter");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"MobileCenter\": Expected " + CONFIG_PARAM_MOBILE_CENTER + ", but got " + numberOfParameters, CONFIG_PARAM_MOBILE_CENTER, numberOfParameters);
        //  endregion

        //  region Test NV
        response = service.getDemoAppConfigParametersByTool("MobileCenter");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"NV\": Expected " + CONFIG_PARAM_NV + ", but got " + numberOfParameters, CONFIG_PARAM_NV, numberOfParameters);
        //  endregion

        //  region Test Sprinter
        response = service.getDemoAppConfigParametersByTool("Sprinter");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"Sprinter\": Expected " + CONFIG_PARAM_SPRINTER + ", but got " + numberOfParameters, CONFIG_PARAM_SPRINTER, numberOfParameters);
        //  endregion

        //  region Test StormRunner
        response = service.getDemoAppConfigParametersByTool("StormRunner");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"StormRunner\": Expected " + CONFIG_PARAM_STORM_RUNNER + ", but got " + numberOfParameters, CONFIG_PARAM_STORM_RUNNER, numberOfParameters);
        //  endregion

        //  region Test SV
        response = service.getDemoAppConfigParametersByTool("SV");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"SV\": Expected " + CONFIG_PARAM_SV + ", but got " + numberOfParameters, CONFIG_PARAM_SV, numberOfParameters);
        //  endregion

        //  region Test UFT
        response = service.getDemoAppConfigParametersByTool("UFT");
        numberOfParameters = response.size();
        Assert.assertEquals("For \"UFT\": Expected " + CONFIG_PARAM_UFT + ", but got " + numberOfParameters, CONFIG_PARAM_UFT, numberOfParameters);
        //  endregion

    }

    @Ignore
    @Test
    public void testFindDemoAppConfigParameterByName() {
        Assert.assertNotNull("Expected to find parameters [Add_wrong_product_to_speakers_category], but got [null]", demoAppConfigurationXml.findParameterByName("Add_wrong_product_to_speakers_category"));
        Assert.assertNotNull("Expected to find parameters [Email_address_in_login], but got [null]", demoAppConfigurationXml.findParameterByName("Email_address_in_login"));
        Assert.assertNotNull("Expected to find parameters [Generate_memory_leak], but got [null]", demoAppConfigurationXml.findParameterByName("Generate_memory_leak"));
        Assert.assertNotNull("Expected to find parameters [Max_concurrent_sessions], but got [null]", demoAppConfigurationXml.findParameterByName("Max_concurrent_sessions"));
        Assert.assertNotNull("Expected to find parameters [Mix_pictures_in_home_page], but got [null]", demoAppConfigurationXml.findParameterByName("Mix_pictures_in_home_page"));
        Assert.assertNotNull("Expected to find parameters [Repeat_ShipEx_call], but got [null]", demoAppConfigurationXml.findParameterByName("Repeat_ShipEx_call"));
        Assert.assertNotNull("Expected to find parameters [Slow_DB_call], but got [null]", demoAppConfigurationXml.findParameterByName("Slow_DB_call"));
        Assert.assertNotNull("Expected to find parameters [Spelling_mistakes_in_order_payment_page], but got [null]", demoAppConfigurationXml.findParameterByName("Spelling_mistakes_in_order_payment_page"));
        Assert.assertNotNull("Expected to find parameters [Sum_to_add_to_cart_calculation], but got [null]", demoAppConfigurationXml.findParameterByName("Sum_to_add_to_cart_calculation"));
    }

    @Ignore
    @Test
    public void testGetDemoAppConfigXmlFile() {
        List<String> lines = demoAppConfigurationXml.getDemoAppConfigXmlFile();
        Assert.assertNotNull("Expected lines of \"DemoAppConfig.xml\" file, but got [null]", lines);
    }
}
