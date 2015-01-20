package com.iksgmbh.moglicc.provider.model.standard.metainfo;

import java.util.ArrayList;
import java.util.List;

import com.iksgmbh.utils.StringUtil;

public abstract class MetaModelObject implements MetaInfoSupport {

	protected List<MetaInfo> metaInfoList;

	public void addMetaInfo(final MetaInfo metaInfo) {
		this.metaInfoList.add(metaInfo);
	}

	@Override
	public List<MetaInfo> getMetaInfosWithNameStartingWith(final String prefix)
	{
		final List<MetaInfo> toReturn = new ArrayList<MetaInfo>();
		for (final MetaInfo metaInfo : metaInfoList) 
		{
			if (metaInfo.getName().startsWith(prefix))
			{
				toReturn.add(metaInfo);
			}
		}
		return toReturn;
	}
	
	@Override
	public String getMetaInfoValueFor(final String metaInfoName) {
		final String convertedNameToSearch = StringUtil.firstToLowerCase(metaInfoName);
		for (MetaInfo element : metaInfoList) {
			final String elementName = StringUtil.firstToLowerCase(element.getName());
			if (elementName.equals(convertedNameToSearch)) {  // ignore case of first letter
				return element.getValue();
			}
		}
		return META_INFO_NOT_FOUND.replaceFirst("#", metaInfoName);
	}

	@Override
	public List<String> getAllMetaInfoValuesFor(final String metaInfoName) {
		final List<String> toReturn = new ArrayList<String>();
		for (final MetaInfo element : metaInfoList) {
			if (element.getName().equals(metaInfoName)) {
				toReturn.add(element.getValue());
			}
		}
		return toReturn;
	}

	@Override
	public List<MetaInfo> getMetaInfoList() {
		return metaInfoList;
	}


	@Override
	public boolean doesHaveMetaInfo(final String metaInfoName, final String value) {
		for (final MetaInfo element : metaInfoList) {
			if (element.getName().equals(metaInfoName)
				&& element.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doesHaveAnyMetaInfosWithName(final String metaInfoName) {
		for (final MetaInfo element : metaInfoList) {
			if (element.getName().equals(metaInfoName)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public String getCommaSeparatedListOfAllMetaInfoValuesFor(final String metaInfoName) {
		final StringBuffer sb = new StringBuffer();
		final List<String> metaInfoValues = getAllMetaInfoValuesFor(metaInfoName);
		for (final String name : metaInfoValues) {
			sb.append(name);
			sb.append(", ");
		}
		return getCommaSeparatedListAsStringFromStringBuffer(sb);
	}

	protected String getCommaSeparatedListAsStringFromStringBuffer(final StringBuffer sb) {
		final String s = sb.toString();
		if (s.length() < 3) {
			return "";
		}
		return s.substring(0, s.length()-2);
	}


	protected String getCommaSeparatedListOfMetaInfoNames() {
		final StringBuffer sb = new StringBuffer();
		for (final MetaInfo element : metaInfoList) {
			sb.append(element.getName());
			sb.append(", ");
		}
		return getCommaSeparatedListAsStringFromStringBuffer(sb);
	}

	@Override
	public boolean isValueAvailable(final String metaInfoValue) {
		if (metaInfoValue == null) {
			return false;
		}
		return ! (metaInfoValue.startsWith(META_INFO_NOT_FOUND_START) && metaInfoValue.endsWith(META_INFO_NOT_FOUND_END));
	}
	
	@Override
	public boolean isValueNotAvailable(final String metaInfoValue) {
		return ! isValueAvailable(metaInfoValue);
	}
	

}
