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
package com.iksgmbh.moglicc.provider.engine.velocity.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iksgmbh.data.Annotation;
import com.iksgmbh.helper.AnnotationParser;
import com.iksgmbh.moglicc.data.BuildUpGeneratorResultData;
import com.iksgmbh.moglicc.exceptions.MOGLiPluginException;
import com.iksgmbh.utils.StringUtil;

public class MergeResultAnalyser {

	public static final String IDENTIFICATOR_WHITESPACE_MARKER = "'";
	private static final AnnotationParser annotationParser = AnnotationParser.getInstance();
	private static final String commentSymbolLineIdentifier = annotationParser.getCommentIdentificator() +
			                                                  annotationParser.getCommentIdentificator() +
			                                                  annotationParser.getCommentIdentificator();

	private BuildUpGeneratorResultData velocityResultData;
	private String artefactType;
	private boolean headerEndReached = false;

	public static BuildUpGeneratorResultData doYourJob(final String mergeResult,
			                                           final String artefactType) throws MOGLiPluginException {
		final MergeResultAnalyser analyser = new MergeResultAnalyser(artefactType);
		try {
			return analyser.analyseGeneratorResult(mergeResult);
		} catch (IOException e) {
			throw new MOGLiPluginException(e);
		}
	}

	private MergeResultAnalyser(final String artefactType) {
		this.artefactType = artefactType;
	}

	private BuildUpGeneratorResultData analyseGeneratorResult(final String mergeResult)
	         throws IOException, MOGLiPluginException {
		velocityResultData = new BuildUpGeneratorResultData();
		final String resultFileContent = parseMergeResult(mergeResult);
		velocityResultData.setGeneratedContent(resultFileContent);
		return velocityResultData;
	}

	protected void removeTrailingEmptyLines(final List<String> newLines) {
		final List<Integer> list = new ArrayList<Integer>();
		for (int i = newLines.size()-1; i == 0; i--) {
			if (! StringUtils.isEmpty(newLines.get(i))) {
				break;
			} else {
				list.add(i);
			}
		}
		for (Integer index : list) {
			newLines.remove(index);
		}
	}

	protected String removeWhitespaceMarker(final String line) {
		if (line.startsWith(IDENTIFICATOR_WHITESPACE_MARKER)) {
			return line.substring(IDENTIFICATOR_WHITESPACE_MARKER.length());
		}
		return line;
	}

	/**
	 * extracts template properties via  annotations
	 * @param originalFileContent
	 * @return filecontent without annotation lines
	 * @throws MOGLiPluginException
	 */
	protected String parseMergeResult(final String originalFileContent) throws MOGLiPluginException {

//		The following implementation does not work on all machines.
//		In case of error FileUtil.getSystemLineSeparator() is \r\n, but originalFileContent uses \n.
//		Reason unkown. Probably a hidden line.separator property used by velocity.
//		final String[] oldLines = originalFileContent.split(FileUtil.getSystemLineSeparator());
		final String[] oldLines = originalFileContent.replaceAll("\r", "").split("\n");

		final List<String> newLines = new ArrayList<String>();
		for (int i = 0; i < oldLines.length; i++) {
			final String line = oldLines[i].trim();
			parseLine(newLines, line, i+1);
		}
		removeTrailingEmptyLines(newLines);
		return StringUtil.concat(newLines);
	}

	protected void parseLine(final List<String> newLines, String line, final int lineNo) throws MOGLiPluginException {
		line = StringUtil.cutUnwantedLeadingControlChars(line);
		if (! headerEndReached && annotationParser.hasCorrectPrefix(line)) {
			final Annotation annotation = annotationParser.doYourJob(line);
			if (annotation.getName() == null) {
				throw new MOGLiPluginException("Header attribute '" + line + "' in line " + lineNo
						                        + " of main template of artefact '" + artefactType
						                        + "' is not parseble!");
			}
			if (annotation.getAdditionalInfo() == null) {
				throw new MOGLiPluginException("Header attribute '" + annotation.getName() + "' in line " + lineNo
						                       + " of main template of artefact '" + artefactType
						                       + "' needs additional information.");
			}
			velocityResultData.addProperty(annotation.getName(), annotation.getAdditionalInfo());
		} else if (annotationParser.isCommentLine(line)) {
			if (line.startsWith(commentSymbolLineIdentifier)) {
				// do not remove whole line that consists of separator symbols
				newLines.add(removeWhitespaceMarker(line));
			}
			// ignore this line
		} else {
			if (line.trim().length() > 0) {
				String s = removeWhitespaceMarker(line);
				newLines.add(s);
				headerEndReached = true;
			}
		}
	}

}