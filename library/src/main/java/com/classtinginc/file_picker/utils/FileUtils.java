/* 
 * Copyright (C) 2007-2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.classtinginc.file_picker.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

	private static final String HIDDEN_PREFIX = ".";

	public static String getFileName(String filePath) {
		if (filePath == null) {
			return "";
		}
		
		String[] splitFilePath = filePath.split("/");
		return splitFilePath[splitFilePath.length - 1];
	}

	public static File getFile(String absolutePath) {
		return new File(absolutePath);
	}

	public static boolean isPhotoType(String filePath) {
		Pattern pat= Pattern.compile(".((?i:png|jpg|jpeg|gif))");
	    Matcher matcher = pat.matcher(filePath);

	    return matcher.find();
	}

	public static boolean isDocumentType(String filePath) {
		Pattern pat= Pattern.compile(".((?i:doc|hwp|xls|ppt|docx|xlsx|pptx|gul|txt|pdf|zip|bmp|jpeg|jpg|png|gif|flv|mp3|mp4|avi|mpg|wav|asf|mkv|ogg|wmv|m4a|show|hpt|hsdt|htheme|cell|nxl|hcdt|nxt))");
	    Matcher matcher = pat.matcher(filePath);

	    return matcher.find();
	}

	public static boolean isMimeTypeEitherPhotoOrDocument(File file) {
		return isPhotoType(file.getAbsolutePath()) || isDocumentType(file.getAbsolutePath());
	}

	/**
	 * Get a list of Files in the give path
	 *
	 * @param path
	 * @return Collection of files in give directory

	 * @author paulburke
	 */
	public static List<File> getFileList(String path) {
		ArrayList<File> list = new ArrayList<File>();

		// Current directory File instance
		final File pathDir = new File(path);

		// List file in this directory with the directory filter
		final File[] dirs = pathDir.listFiles(mDirFilter);
		if (dirs != null) {
			// Sort the folders alphabetically
			Arrays.sort(dirs, mComparator);
			// Add each folder to the File list for the list adapter
			for (File dir : dirs) list.add(dir);
		}

		// List file in this directory with the file filter
		final File[] files = pathDir.listFiles(mFileFilter);
		if (files != null) {
			// Sort the files alphabetically
			Arrays.sort(files, mComparator);
			// Add each file to the File list for the list adapter
			for (File file : files) list.add(file);
		}

		return list;
	}


	/**
	 * File (not directories) filter.
	 *
	 * @author paulburke
	 */
	private static FileFilter mFileFilter = new FileFilter() {
		public boolean accept(File file) {
			final String fileName = file.getName();
			// Return files only (not directories) and skip hidden files
			return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
		}
	};

	/**
	 * Folder (directories) filter.
	 *
	 * @author paulburke
	 */
	private static FileFilter mDirFilter = new FileFilter() {
		public boolean accept(File file) {
			final String fileName = file.getName();
			// Return directories only and skip hidden directories
			return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
		}
	};

	/**
	 * File and folder comparator.
	 * TODO Expose sorting option method
	 *
	 * @author paulburke
	 */
	private static Comparator<File> mComparator = new Comparator<File>() {
		public int compare(File f1, File f2) {
			// Sort alphabetically by lower case, which is much cleaner
			return f1.getName().toLowerCase().compareTo(
					f2.getName().toLowerCase());
		}
	};



}
