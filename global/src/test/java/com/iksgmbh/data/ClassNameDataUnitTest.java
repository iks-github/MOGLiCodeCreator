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
package com.iksgmbh.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ClassNameDataUnitTest {
	
	@Test
	public void canNotCreateInstance_withLeadingAsterisk() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("*Test.Test"));
	}
	
	@Test
	public void canNotCreateInstance_withLeadingSpace() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid(" Test.Test"));
	}
	
	@Test
	public void canNotCreateInstance_withTraildingSpace() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("Test.Test "));
	}

	@Test
	public void canNotCreateInstance_withLeadingDot() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid(".Test.Test"));
	}
	
	@Test
	public void canNotCreateInstance_withTraildingDot() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("Test.Test."));
	}
	
	@Test
	public void canNotCreateInstance_withLeadingLowerCaseLetterInSimpelName() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("Test.aTest"));
	}
	
	@Test
	public void canNotCreateInstance_withDefaultPackage() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("Test."));
	}
	
	@Test
	public void canNotCreateInstance_withUpperCasePackage() {
		assertFalse(ClassNameData.isFullyQualifiedClassnameValid("test1.Test2.Test3"));
	}
	
	@Test
	public void canNotCreateInstance_withLeadingAndTrailingSpace() {
		final String testData = " Test.Test ";
		final ClassNameData className = new ClassNameData(testData);
		assertEquals("simpelClassName", testData.trim(), className.getSimpleClassName());
		assertEquals("packageName", "", className.getPackageName());
		assertEquals("fullyQualifiedClassname", testData.trim(), className.getFullyQualifiedClassname());	}

	@Test
	public void canCreateInstanceSuccessfullyWithLeadingLowerCaseLetterInSimpelName() {
		final String testData = "Test.aTest";
		final ClassNameData className = new ClassNameData(testData);
		assertEquals("simpelClassName", testData, className.getSimpleClassName());
		assertEquals("packageName", "", className.getPackageName());
		assertEquals("fullyQualifiedClassname", testData, className.getFullyQualifiedClassname());
	}

	@Test
	public void canCreateInstanceSuccessfullyWithDefaultPackage() {
		final String testData = "Test";
		final ClassNameData className = new ClassNameData(testData);
		assertEquals("simpelClassName", testData, className.getSimpleClassName());
		assertEquals("packageName", "", className.getPackageName());
		assertEquals("fullyQualifiedClassname", testData, className.getFullyQualifiedClassname());
	}
	
	@Test
	public void canCreateInstanceSuccessfullyWithUpperCasePackage() {
		final String testData = "test1.Test2.Test3";
		final ClassNameData className = new ClassNameData(testData);
		assertEquals("simpelClassName", testData, className.getSimpleClassName());
		assertEquals("packageName", "", className.getPackageName());
		assertEquals("fullyQualifiedClassname", testData, className.getFullyQualifiedClassname());
	}
	
	@Test
	public void canCreateInstanceSuccessfullyWithSimpleClassname() {
		final ClassNameData className = new ClassNameData("DateTime");
		assertEquals("simpelClassName", "DateTime", className.getSimpleClassName());
		assertEquals("packageName", "org.joda.time", className.getPackageName());
		assertEquals("fullyQualifiedClassname", "org.joda.time.DateTime", className.getFullyQualifiedClassname());
	}
	
	@Test
	public void returnsSubdirPackageHierarchy() {
		final ClassNameData className = new ClassNameData("DateTime");
		assertEquals("subdirPackageHierarchy", "org/joda/time", className.getSubdirPackageHierarchy());
	}
}