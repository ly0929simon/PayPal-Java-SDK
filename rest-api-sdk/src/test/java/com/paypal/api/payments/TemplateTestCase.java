package com.paypal.api.payments;

import static com.paypal.base.util.TestConstants.SANDBOX_CONTEXT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;

@Test
public class TemplateTestCase {
	
	private static final Logger logger = Logger
			.getLogger(TemplateTestCase.class);
	
	private String id = null;

	public Template loadInvoiceTemplate() {
	    try {
		    BufferedReader br = new BufferedReader(new FileReader(new File(
				    this.getClass().getClassLoader().getResource("template_test.json").getFile())));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	            line = br.readLine();
	        }
	        br.close();
	        return JSONFormatter.fromJSON(sb.toString(), Template.class);
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	@Test(groups = "integration", enabled=true)
	public void testCreateInvoiceTemplate() throws PayPalRESTException {
		Template template = loadInvoiceTemplate();
		String templateName = UUID.randomUUID().toString();
		// Updated name as it is unique
		template.setName(templateName);
		try {
			template = template.create(SANDBOX_CONTEXT);
		} catch (PayPalRESTException ex) {
			if (ex.getResponsecode() == 400 && ex.getDetails().getMessage().contains("Exceed maximum number")) {
				// This could be because we have reached the maximum number of templates possible per app.
				Templates templates = Template.getAll(SANDBOX_CONTEXT);
				for (Template templ : templates.getTemplates()) {
					if (!templ.getIsDefault()) {
						try {
							templ.delete(SANDBOX_CONTEXT);
						} catch (Exception e) {
							// We tried our best. We will continue.
							continue;
						}
					}
				}
				template = template.create(SANDBOX_CONTEXT);
			}
		}
		logger.info("Invoice template created: ID=" + template.getTemplateId());
		this.id = template.getTemplateId();
		Assert.assertEquals(templateName, template.getName());
	}

	@Test(groups = "integration", enabled=true, dependsOnMethods = { "testCreateInvoiceTemplate" })
	public void testUpdateInvoiceTemplate() throws PayPalRESTException {
		Template template = Template.get(SANDBOX_CONTEXT, this.id);
		// change the note
		template.getTemplateData().setNote("Something else");
		// BUG: Custom is required to be manually removed.
		template.setCustom(null);

		template.update(SANDBOX_CONTEXT);
		logger.info("Invoice template returned: ID=" + template.getTemplateId());
		this.id = template.getTemplateId();
		Template updatedTemplate = Template.get(SANDBOX_CONTEXT, this.id);
		Assert.assertEquals(updatedTemplate.getTemplateData().getNote(), template.getTemplateData().getNote());
	}

	@Test(groups = "integration", enabled=true, dependsOnMethods = { "testCreateInvoiceTemplate" })
	public void testGetInvoiceTemplate() throws PayPalRESTException {
		Template template = Template.get(SANDBOX_CONTEXT, this.id);
		logger.info("Invoice template returned: ID=" + template.getTemplateId());
		this.id = template.getTemplateId();
	}

	@Test(groups = "integration", enabled=true, dependsOnMethods = { "testGetInvoiceTemplate" })
	public void testDeleteInvoiceTemplate() throws PayPalRESTException {
		Template template = Template.get(SANDBOX_CONTEXT, this.id);
		template.delete(SANDBOX_CONTEXT);
	}
}
