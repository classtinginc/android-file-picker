/* 
 * Copyright (C) 2012 Paul Burke
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

package com.classtinginc.file_picker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * List adapter for Files.
 */
public class FileListAdapter extends BaseAdapter {

	private List<File> files = new ArrayList<File>();
	private Context context;
	private boolean allowMultiple;

	public FileListAdapter(Context context, boolean allowMultiple) {
		this.context = context;
		this.allowMultiple = allowMultiple;
	}

	public void setListItems(List<File> files) {
		this.files = files;
		notifyDataSetChanged();
	}

	@Override
    public int getCount() {
		return files.size();
	}

	public void add(File file) {
		files.add(file);
		notifyDataSetChanged();
	}

	public void clear() {
		files.clear();
		notifyDataSetChanged();
	}

	@Override
    public File getItem(int position) {
		return files.get(position);
	}

	@Override
    public long getItemId(int position) {
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		ItemFile view = (ItemFile) convertView;

		if (convertView == null) {
			view = new ItemFile(context);
		}

		File file = getItem(position);
		view.bind(file, isSelected(file, position), allowMultiple);

		return view;
	}

	private boolean isSelected(File file, int position) {
		HashMap<String, String> selectedFiles = ((FileActivity) context).getSelectedFiles();
		return selectedFiles.containsKey(file.getName() + String.valueOf(position));
	}
}
