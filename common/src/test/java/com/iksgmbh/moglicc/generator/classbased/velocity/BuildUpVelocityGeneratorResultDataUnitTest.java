package com.iksgmbh.moglicc.generator.classbased.velocity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.iksgmbh.moglicc.data.BuildUpGeneratorResultData;
import com.iksgmbh.moglicc.exceptions.MOGLiPluginException;
import com.iksgmbh.moglicc.generator.classbased.velocity.VelocityGeneratorResultData.KnownGeneratorPropertyNames;

public class BuildUpVelocityGeneratorResultDataUnitTest
{
	private VelocityGeneratorResultData velocityGeneratorResultData;
	private BuildUpGeneratorResultData buildUpGeneratorResultData;

	@Before
	public void setup()
	{
		buildResultData("Content", "targetDir", "filename", false);
	}

	private void buildResultData(final String content, final String targetDir, final String filename, final boolean createNew)
	{
		buildUpGeneratorResultData = new BuildUpGeneratorResultData();
		buildUpGeneratorResultData.setGeneratedContent(content);
		buildUpGeneratorResultData.addProperty(KnownGeneratorPropertyNames.TargetDir.name(), targetDir);
		buildUpGeneratorResultData.addProperty(KnownGeneratorPropertyNames.TargetFileName.name(), filename);

		if (createNew) {
			buildUpGeneratorResultData.addProperty(KnownGeneratorPropertyNames.CreateNew.name(), "true");
		}

		velocityGeneratorResultData = new BuildUpVelocityGeneratorResultData(buildUpGeneratorResultData);
	}

	@Test
	public void returnsTargetFileName()
	{
		// call functionality under test
		final String targetFileName = velocityGeneratorResultData.getTargetFileName();

		// verify test result
		assertEquals("targetFileName", "filename", targetFileName);
	}

	@Test
	public void returnsTargetDir()
	{
		// call functionality under test
		final String targetDir = velocityGeneratorResultData.getTargetDir();
		
		// verify test result
		assertEquals("targetDir", "targetDir", targetDir);
	}

	@Test
	public void returnsGeneratedContent()
	{
		// call functionality under test
		final String content = velocityGeneratorResultData.getGeneratedContent();

		// verify test result
		assertEquals("Content", "Content", content);
	}

	@Test
	public void returnsOverwriteWithNullValue()
	{
		// call functionality under test
		final boolean overwrite = velocityGeneratorResultData.isTargetToBeCreatedNewly();

		// verify test result
		assertEquals("overwrite", false, overwrite);
	}

	@Test
	public void returnsCreateNewWithWrongValue()
	{
		// prepare test
		buildUpGeneratorResultData.addProperty(KnownGeneratorPropertyNames.CreateNew.name(), "foo");
		velocityGeneratorResultData = new BuildUpVelocityGeneratorResultData(buildUpGeneratorResultData);

		// call functionality under test
		final boolean overwrite = velocityGeneratorResultData.isTargetToBeCreatedNewly();

		// verify test result
		assertEquals("overwrite", false, overwrite);
	}

	@Test
	public void returnsCreateNewWithTrueValueIgnoringCase()
	{
		// prepare test
		buildUpGeneratorResultData.addProperty(KnownGeneratorPropertyNames.CreateNew.name(), "trUe");
		velocityGeneratorResultData = new BuildUpVelocityGeneratorResultData(buildUpGeneratorResultData);

		// call functionality under test
		final boolean createNew = velocityGeneratorResultData.isTargetToBeCreatedNewly();

		// verify test result
		assertEquals("overwrite", true, createNew);
	}

	@Test
	public void returnsTargetDirWithApplicationRootButWithNullValue() throws MOGLiPluginException
	{
		// prepare test
		buildResultData("Content", VelocityGeneratorResultData.ROOT_IDENTIFIER + "/temp", "filename", false);

		// call functionality under test
		String targetDir = velocityGeneratorResultData.getTargetDirAsFile(null, "").getAbsolutePath();

		// verify test result
		targetDir = targetDir.replace('\\', '/');
		assertTrue("Unexpected targetDir", targetDir.endsWith("/temp"));
	}

	@Test
	public void returnsTargetDirWithoutApplicationRootButNotNullValue() throws MOGLiPluginException
	{
		// prepare test
		buildResultData("Content", "temp", "filename", false);

		// call functionality under test
		String targetDir = velocityGeneratorResultData.getTargetDirAsFile("root", "").getAbsolutePath();

		// verify test result
		targetDir = targetDir.replace('\\', '/');
		assertTrue("Unexpected targetDir", targetDir.endsWith("/temp"));
	}

	@Test
	public void returnsTargetDirWithApplicationRoot() throws MOGLiPluginException
	{
		// prepare test
		final String applicationRootDir = "root";
		buildResultData("Content", VelocityGeneratorResultData.ROOT_IDENTIFIER + "/temp", "filename", true);

		// call functionality under test
		String targetDir = velocityGeneratorResultData.getTargetDirAsFile(applicationRootDir, "").getAbsolutePath();

		// verify test result
		targetDir = targetDir.replace('\\', '/');
		assertTrue("targetDir not correct", targetDir.endsWith("/" + applicationRootDir + "/temp"));
	}

}
