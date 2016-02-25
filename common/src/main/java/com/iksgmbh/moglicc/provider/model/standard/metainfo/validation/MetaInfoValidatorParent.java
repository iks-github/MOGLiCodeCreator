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
package com.iksgmbh.moglicc.provider.model.standard.metainfo.validation;

import java.util.ArrayList;
import java.util.List;

import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaInfo;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaInfo.HierarchyLevel;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaInfoCounter;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.MetaInfoValidator;

public abstract class MetaInfoValidatorParent implements MetaInfoValidator, MetaInfoCounter {
	
	protected String metaInfoName;
	protected HierarchyLevel metaInfoHierarchyLevel;
	
	protected ValidationType validationType;
	protected String errorMessage;
	
	protected String vendorPluginId; 
	protected String nameOfValidModel;
	protected int metaInfoCounter = 0;

	public MetaInfoValidatorParent(final String metaInfoName,
			                       final HierarchyLevel metaInfoHierarchyLevel, 
			                       final ValidationType validationType, 
			                       final String nameOfValidModel) {
		this.metaInfoName = metaInfoName;
		this.metaInfoHierarchyLevel = metaInfoHierarchyLevel;
		this.validationType = validationType;
		this.nameOfValidModel = nameOfValidModel;
	}

	@Override
	public String getVendorPluginId() {
		return vendorPluginId;
	}

	public void setVendorPluginId(String vendorPluginId) {
		this.vendorPluginId = vendorPluginId;
	}

	@Override
	public String getMetaInfoName() {
		return metaInfoName;
	}

	@Override
	public HierarchyLevel getMetaInfoHierarchyLevel() {
		return metaInfoHierarchyLevel;
	}

	@Override
	public ValidationType getValidationType() {
		return validationType;
	}
	
	@Override
	public String getValidationErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return "MetaInfoValidatorParent [metaInfoName=" + metaInfoName
				+ ", metaInfoHierarchyLevel=" + metaInfoHierarchyLevel
				+ ", validationType=" + validationType 
				+ ", vendorPluginId=" + vendorPluginId + "]";
	}
	
	protected void count(final MetaInfo metaInfo) {
		metaInfoCounter++;
		metaInfo.getPluginList().add(vendorPluginId);
	}

	@Override
	public int getMetaInfoMatches() {
		return metaInfoCounter;
	}

	@Override
	public String getNameOfValidModel() {
		return nameOfValidModel;
	}

	public void setNameOfValidModel(String nameOfValidModel) {
		this.nameOfValidModel = nameOfValidModel;
	}

	public boolean isValidatorValidForHierarchyLevel(final HierarchyLevel currentHierarchyLevel) {
		if (metaInfoHierarchyLevel != currentHierarchyLevel) {
			return false; // this validator does not validate MetaInfo elements of this HierarchyLevel
		}
		
		return true;
	}

	public boolean isValidatorValidForModel(final String nameOfCurrentModel) {
		if (nameOfValidModel == null)  {
			return true;  // no model defined -> interpreted as valid for all models
		}

		if (! nameOfValidModel.equals(nameOfCurrentModel)) {
			return false; // this validator does not validate MetaInfo elements of this model
		}
		
		return true;
	}

	protected List<MetaInfo> filterForHierarchyLevel(final List<MetaInfo> metaInfoList) {
		final List<MetaInfo> toReturn = new ArrayList<MetaInfo>();
		for (final MetaInfo metaInfo : metaInfoList) {
			if (metaInfo.getHierarchyLevel() == metaInfoHierarchyLevel) {
				toReturn.add(metaInfo);
			}
		}
		return toReturn;
	}
	
	/**
	 * @param metaInfoList list of a single MetaModelObject, that means
	 *        all elements contains the same {@link HierarchyLevel} and
	 *        refer to the same model, class or attribute instance
	 *        
	 * @return true if no validation error occurred
	 */
	@Override
	public abstract boolean validate(final List<MetaInfo> metaInfoList);

}