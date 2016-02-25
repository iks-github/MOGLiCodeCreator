/*
 * Copyright 2016 IKS Gesellschaft fuer Informations- und Kommunikationssysteme mbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iksgmbh.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iksgmbh.utils.FileUtil;
import com.iksgmbh.utils.OSUtil;

public class NameSpaceReplacerUnitTest {
	
	public static final String SIMPLE_TEST_DIR = "../global/target/NameSpaceReplacer/SimpleTest";
	public static final String COMPLEX_TEST_DIR = "../global/target/NameSpaceReplacer/ComplexTest";
	
	private static final String STANDARD_TEST_FILE_CONTENT = "Test com.iksgmbh.moglicc Test";
	
	private final List<String> fileDataList = new ArrayList<String>();
	
	@Test
	public void replacesSimpleTest() throws Exception {
		// prepare test
		final File testDir = new File(SIMPLE_TEST_DIR);
		FileUtil.deleteDirWithContent(testDir);
		assertFalse("Dir must not exist!", testDir.exists());
		final File sourceDir = new File(SIMPLE_TEST_DIR, "src/de/iks_gmbh/automobile/test");
		sourceDir.mkdirs();
		assertTrue("Dir must exist!", sourceDir.exists());
		createTestFile(SIMPLE_TEST_DIR + "/src/de/iks_gmbh/automobile/test", "Test.java", STANDARD_TEST_FILE_CONTENT);
		
		// call functionality under test
		(new NameSpaceReplacer()).replaceWorkspace(SIMPLE_TEST_DIR);

		// verify test result
		final File file = new File(SIMPLE_TEST_DIR, "src/com/iksgmbh/moglicc/test/Test.java");
		assertTrue("File does not exist: " + file.getAbsolutePath(), file.exists());
		final String fileContent = FileUtil.getFileContent(file).trim();
		assertEquals("fileContent", "Test com.iksgmbh.moglicc Test", fileContent);
	}

	@Test
	public void replacesComplexTest() throws Exception {
		// prepare test
		fileDataList.clear();
		initComplexTestFileStructure();

		
		// call functionality under test
		(new NameSpaceReplacer()).replaceWorkspace(COMPLEX_TEST_DIR);

		// verify test result
		final String oldString;
		final String newString;
		if (OSUtil.isWindows()) {
			oldString = "de\\iks_gmbh\\automobile";
			newString = "com\\iksgmbh\\moglicc";
		} else {
			oldString = "de/iks_gmbh/automobile";
			newString = "com/iksgmbh/moglicc";
		}
		for (final String filepath : fileDataList) {
			final String path = filepath.replace(oldString, newString);
			final File file = new File(path);
			final String fileContent = FileUtil.getFileContent(file).trim();
			if ("target".equals(file.getParentFile().getName())) {
				// hier wird nicht ersetzt
				assertEquals("fileContent of " + file.getAbsolutePath(), STANDARD_TEST_FILE_CONTENT, fileContent);
			} else {
				assertEquals("fileContent", "Test com.iksgmbh.moglicc Test", fileContent);
			}
		}
	}

	private void initComplexTestFileStructure() throws Exception {
		final File testDir = new File(COMPLEX_TEST_DIR);
		FileUtil.deleteDirWithContent(testDir);
		assertFalse("Dir must not exist!", testDir.exists());
		
		createModuleFileStructure("module1");
		createModuleFileStructure("module2");
	}

	protected void createModuleFileStructure(final String moduleName)
			throws Exception {
		final File module1SourceDir = new File(COMPLEX_TEST_DIR, moduleName + "/src/de/iks_gmbh/automobile/test");
		module1SourceDir.mkdirs();
		createTestFile(module1SourceDir.getAbsolutePath(), "Test.java", STANDARD_TEST_FILE_CONTENT);
		
		final File module1TargetDir = new File(COMPLEX_TEST_DIR, moduleName + "/target");
		module1TargetDir.mkdirs();
		createTestFile(module1TargetDir.getAbsolutePath(), "Test.txt", STANDARD_TEST_FILE_CONTENT);
		
		final File module1TestDir = new File(COMPLEX_TEST_DIR, moduleName + "/test");
		module1TestDir.mkdirs();	
		createTestFile(module1TestDir.getAbsolutePath(), "Test.txt", STANDARD_TEST_FILE_CONTENT);
		
		createTestFile(COMPLEX_TEST_DIR + "/module1", "pom.xml", STANDARD_TEST_FILE_CONTENT);
	}

	protected void createTestFile(final String dir, final String filename, final String content) throws Exception {
		final File file = new File(dir, filename);
		FileUtil.createNewFileWithContent(file, content);
		assertTrue("File does not exist: " + file.getAbsolutePath(), file.exists());
		fileDataList.add(file.getAbsolutePath());
	}

}