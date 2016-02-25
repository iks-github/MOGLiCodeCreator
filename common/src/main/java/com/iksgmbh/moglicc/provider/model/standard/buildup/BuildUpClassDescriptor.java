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
package com.iksgmbh.moglicc.provider.model.standard.buildup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iksgmbh.data.ClassNameData;
import com.iksgmbh.moglicc.provider.model.standard.AttributeDescriptor;
import com.iksgmbh.moglicc.provider.model.standard.ClassDescriptor;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaInfo;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaModelObject;

/**
 * Used to build a ClassDescriptor Object.
 * @author Reik Oberrath
 * @since 1.0.0
 */
public class BuildUpClassDescriptor extends MetaModelObject implements ClassDescriptor {

	private ClassNameData classnameData;
	private List<AttributeDescriptor> attributeDescriptorList;
	
	public BuildUpClassDescriptor(final ClassNameData classnameData) {
		if (classnameData == null || StringUtils.isEmpty(classnameData.getSimpleClassName())) {
			throw new IllegalArgumentException("Name of BuildUpClassDescriptor must not be empty!");
		}
		this.classnameData = classnameData;
		attributeDescriptorList = new ArrayList<AttributeDescriptor>();
		metaInfoList = new ArrayList<MetaInfo>();
	}

	
	@Override
	public String toString() {
		return "BuildUpClassDescriptor [classname=" + classnameData.getFullyQualifiedClassname()
				+ ", attributeNumber=" + attributeDescriptorList.size() 
				+ ", metaInfoList=" + getCommaSeparatedListOfMetaInfoNames() + "]";
	}


	public String toString2() {
		return classnameData.getFullyQualifiedClassname();
	}

	@Override
	public String getSimpleName() {
		return classnameData.getSimpleClassName();
	}
	
	@Override
	public String getName() {
		return classnameData.getSimpleClassName();
	}	

	@Override
	public String getPackage() {
		return classnameData.getPackageName();
	}

	@Override
	public String getFullyQualifiedName() {
		return classnameData.getFullyQualifiedClassname();
	}
	
	@Override
	public List<AttributeDescriptor> getAttributeDescriptorList() {
		return attributeDescriptorList;
	}
	
	public void addAttributeDescriptor(final AttributeDescriptor attributeDescriptor) {
		attributeDescriptorList.add(attributeDescriptor);
	}

	public boolean hasAttributeDescriptorAreadyInList(final String attributeName) {
		for (final AttributeDescriptor attributeDescriptor : attributeDescriptorList) {
			if (attributeName.equals(attributeDescriptor.getName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<MetaInfo> getAllMetaInfos() {
		final List<MetaInfo> totalMetaInfoList = new ArrayList<MetaInfo>();
		totalMetaInfoList.addAll(getMetaInfoList());
		
		for (final AttributeDescriptor attributeDescriptor : getAttributeDescriptorList()) {
			BuildUpAttributeDescriptor buildUpAttributeDescriptor = (BuildUpAttributeDescriptor) attributeDescriptor;
			totalMetaInfoList.addAll(buildUpAttributeDescriptor.getMetaInfoList());
		}
		return totalMetaInfoList;
	}

	@Override
	public AttributeDescriptor getAttributeDescriptor(String attributeName) 
	{
		if (attributeDescriptorList == null || attributeDescriptorList.size() == 0)
			return null;

		for (AttributeDescriptor attributeDescriptor : attributeDescriptorList) 
		{
			if (attributeName.equals(attributeDescriptor.getName())) {
				return attributeDescriptor;
			}

		}
		
		return null;
	}	
}