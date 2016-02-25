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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.iksgmbh.utils.FileUtil;
import com.iksgmbh.utils.OSUtil;

/**
 * Data object to access files and subfolders of a given rootDir.
 *  
 * @author Reik Oberrath
 */
public class FolderContent {
	
	private File rootDir;
	private List<String> filesToIgnore;
	private List<File> directories = new ArrayList<File>();
	private HashMap<File, List<File>> content = new HashMap<File, List<File>>();
	
	/**
	 * @param rootDir
	 * @param filesToIgnore names of files and subFolders to ignore (must equals exactly)
	 */
	public FolderContent(final File rootDir, final List<String> filesToIgnore) {
		if (rootDir == null) {
			throw new RuntimeException("Argument 'mainDir' not set!");
		}
		if (! rootDir.exists()) {
			throw new RuntimeException("Folder '" + rootDir + "'  does not exist!");
		}
		
		this.rootDir = rootDir;
		
		if (filesToIgnore == null) {
			this.filesToIgnore = new ArrayList<String>();
		} else {
			this.filesToIgnore = filesToIgnore;
		}
		
		analyseContent(this.rootDir);
	}
	
	public void refresh() {
		directories.clear();
		content.clear();
		analyseContent(this.rootDir);
	}

	private void analyseContent(final File dir) {
		final List<File> subFolders = new ArrayList<File>();
		final List<File> files = new ArrayList<File>();
		
		final File[] listFiles = dir.listFiles();
		for (final File file : listFiles) {
			if (! isFileToIgnore(file)) {
				if (file.isDirectory()) {
					subFolders.add(file);
				} else {
					files.add(file);
				}
			}
		}
		
		content.put(dir, files);
		directories.add(dir);
		
		for (final File subFolder : subFolders) {
			analyseContent(subFolder);
		}		
	}

	protected boolean isFileToIgnore(final File file) {
		for (final String ignore : filesToIgnore) {
			if (file.getName().equals(ignore)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches for files that match the endingPattern.
	 * @param endingPattern last characters of the filename. May include only ending, 
	 *        ending part of filename or path to the file. If null, all files are returned.
	 * @return all files of all subFolders and rootDir with the given file extension
	 */
	public List<File> getFilesWithEndingPattern(String endingPattern) {
		if (OSUtil.isWindows() && endingPattern != null) {
			endingPattern = endingPattern.replace('/', '\\'); 
		}
		final List<File> toReturn = new ArrayList<File>();
		for (final File dir : directories) {
			final List<File> list = content.get(dir);
			for (final File file : list) {
				if (endingPattern == null || file.getAbsolutePath().endsWith(endingPattern)) {
					toReturn.add(file);
				}
			}
		}
		return toReturn;
	}

	/**
	 * @return all folders including rootDir
	 */
	public List<File> getFolders() {
		final List<File> toReturn = new ArrayList<File>();
		for (final File dir : directories) {
			toReturn.add(dir);
		}
		Collections.sort(toReturn);
		return toReturn;
	}

	/**
	 * @return all files of all subFolders and rootDir
	 */
	public List<File> getFiles() {
		return getFilesWithEndingPattern(null);
	}

	public List<File> getFilesFor(final File folder) {
		return content.get(folder);
	}

	public File getFolder(String pathEnding) 
	{
		if (OSUtil.isWindows() && pathEnding != null) {			
			pathEnding = pathEnding.replace('/', '\\'); 
		}
		final List<File> matches = new ArrayList<File>();
		
		for (final File dir : directories) {
			if (dir.getAbsolutePath().endsWith(pathEnding)) {
				matches.add(dir);
			}
		}
		
		if (matches.size() == 0) {
			return null;
		}
		
		if (matches.size() > 1) {
			String errorMessage = "Ambiguous path ending: " + matches.size() + " matches for '" + pathEnding + "':";
			for (File file : matches) {
				errorMessage += FileUtil.getSystemLineSeparator() + file.getAbsolutePath();
			}
			throw new RuntimeException(errorMessage);
		}
		
		return matches.get(0);
	}

	public List<String> getFilesToIgnore() {
		return filesToIgnore;
	}

}