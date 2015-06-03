package com.iksgmbh.moglicc.intest.provider.model.standard.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.iksgmbh.moglicc.core.InfrastructureService;
import com.iksgmbh.moglicc.intest.IntTestParent;
import com.iksgmbh.moglicc.provider.model.standard.Model;
import com.iksgmbh.moglicc.provider.model.standard.StandardModelProviderStarter;
import com.iksgmbh.utils.FileUtil;

public class ExcelStandardModelProviderIntTest extends IntTestParent 
{

	@Test
	public void buildsModelFromExcelData() throws Exception 
	{
		// prepare test
		final InfrastructureService infrastructure = standardModelProviderStarter.getInfrastructure();
		final File testModelFile = new File(infrastructure.getPluginInputDir(), "MOGLiCC_JavaBeanModel.txt");
		FileUtil.createNewFileWithContent(testModelFile, "model MOGLiCC_JavaBeanModel" + FileUtil.getSystemLineSeparator() +
                                                         "metainfo " + StandardModelProviderStarter.USE_EXTENSION_PLUGIN_ID + 
                                                         " ExcelStandardModelProvider");

		standardModelProviderStarter.doYourJob();

		// call functionality under test
		excelStandardModelProviderStarter.doYourJob();
		final Model model = standardModelProviderStarter.getModel("");
		
		// verify test result
		assertNotNull("Not null expected for ", model);
		assertEquals("Number of classes", 5, model.getSize());
		
		assertEquals("Attribute number for class 1", 4, model.getClassDescriptorList().get(0).getAttributeDescriptorList().size());
		assertEquals("Attribute number for class 2", 3, model.getClassDescriptorList().get(1).getAttributeDescriptorList().size());
		assertEquals("Attribute number for class 3", 4, model.getClassDescriptorList().get(2).getAttributeDescriptorList().size());
		assertEquals("Attribute number for class 4", 4, model.getClassDescriptorList().get(3).getAttributeDescriptorList().size());
		assertEquals("Attribute number for class 5", 5, model.getClassDescriptorList().get(4).getAttributeDescriptorList().size());
	}

	@Test
	public void createsWarningInReportForClassesMismatchingInPackage() throws Exception 
	{
		// prepare test
		final InfrastructureService infrastructure = standardModelProviderStarter.getInfrastructure();
		final File testModelFile = new File(infrastructure.getPluginInputDir(), "MOGLiCC_JavaBeanModel.txt");
		FileUtil.createNewFileWithContent(testModelFile, "model MOGLiCC_JavaBeanModel" + FileUtil.getSystemLineSeparator() +
                                                         "metainfo " + StandardModelProviderStarter.USE_EXTENSION_PLUGIN_ID + 
                                                         " ExcelStandardModelProvider" + FileUtil.getSystemLineSeparator() +
                                                         FileUtil.getSystemLineSeparator() + "class any.other.package.Person");

		standardModelProviderStarter.doYourJob();

		// call functionality under test
		excelStandardModelProviderStarter.doYourJob();
		standardModelProviderStarter.getModel("");
		
		// verify test result
		final String report = excelStandardModelProviderStarter.getProviderReport();
		System.err.println(report);
		assertStringContains(report, "WARNING: Class 'Person' is defined in the model of the StandardModelProvider and in the Excel data with a different package!");
	}
	
}
