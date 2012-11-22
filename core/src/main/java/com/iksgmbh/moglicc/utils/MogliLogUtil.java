package com.iksgmbh.moglicc.utils;

import java.io.File;
import java.io.IOException;

import com.iksgmbh.moglicc.exceptions.MogliCoreException;
import com.iksgmbh.utils.FileUtil;

public class MogliLogUtil {

	private static File coreLogfile;
	
	/**
	 * FOR TEST PURPOSE ONLY
	 */
	public static void setCoreLogfile(final File lf) {
		coreLogfile = lf;
	}

	public static File getCoreLogfile() {
		return coreLogfile;
	}

	public static File createNewLogfile(final File file) {
		coreLogfile = file;
		if (coreLogfile.exists()) {
			if (! coreLogfile.delete()) {
				throw new MogliCoreException("Error deleting " + coreLogfile.getAbsolutePath());
			}
		}
		try {
			coreLogfile.createNewFile();
		} catch (IOException e) {
			throw new MogliCoreException("Error creating " + coreLogfile.getAbsolutePath() + coreLogfile.exists(), e);
		}
		System.out.println("Logfile created!");	
		return coreLogfile;
	}
	
	public static void logInfo(final String message) {
		logInfo(coreLogfile, message);
	}

	public static void logInfo(final File currentLogfile, final String message) {
		if (currentLogfile == null) {
			throw new MogliCoreException("Method createNewLogfile not called!");
		}
		try {
			FileUtil.appendToFile(currentLogfile, message);
		} catch (IOException e) {
			throw new MogliCoreException("Error writer to logfile " + currentLogfile.getAbsolutePath(), e);
		}
		System.out.println(message);
	}
	
	public static void logWarning(final String message) {
		logWarning(coreLogfile, message);
	}
	
	public static void logWarning(final File currentLogfile, final String message) {
		logInfo(currentLogfile, "Warning: " + message);
	}
	

	public static void logError(final String message) {
		logError(coreLogfile, message);
	}
	
	public static void logError(final File currentLogfile, final String message) {
		logInfo(currentLogfile, "ERROR: " + message);
	}

}
