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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.classtinginc.file_picker.consts.Extra;
import com.classtinginc.file_picker.utils.FileUtils;
import com.classtinginc.file_picker.utils.TranslationUtils;
import com.classtinginc.library.file_picker.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays a list of Files in a given path.
 */
public class FileListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<File>> {

	private static final int LOADER_ID = 0;

	private FileListAdapter adapter;
	private String path;
	private int maxFilesCount;
	private long maxFileSize;
	private boolean allowMultiple;
	private FileActivity fileActivity;

	/**
	 * Create a new instance with the given file path.
	 * 
	 * @param path The absolute path of the file (directory) to display.
	 * @return A new Fragment with the given file path. 
	 */
	public static FileListFragment newInstance(String path, int maxFilesCount, long maxFileSize, boolean allowMultiple) {
		FileListFragment fragment = new FileListFragment();
		Bundle args = new Bundle();
		args.putString(FileActivity.PATH, path);
		args.putInt(Extra.MAX_FILES_COUNT, maxFilesCount);
		args.putLong(Extra.MAX_FILE_SIZE, maxFileSize);
		args.putBoolean(Extra.ALLOW_MULTIPLE, allowMultiple);

		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			path = getArguments().getString(FileActivity.PATH);
			maxFilesCount = getArguments().getInt(Extra.MAX_FILES_COUNT);
			maxFileSize = getArguments().getLong(Extra.MAX_FILE_SIZE);
			allowMultiple = getArguments().getBoolean(Extra.ALLOW_MULTIPLE);
		} else {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
			maxFilesCount = Extra.DEFAULT_FILES_COUNT;
			maxFileSize = Extra.DEFAULT_FILE_SIZE;
			allowMultiple = Extra.DEFAULT_ALLOW_MULTIPLE;
		}

		adapter = new FileListAdapter(getActivity(), allowMultiple);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setEmptyText(TranslationUtils.getMsgEmptyDir(getActivity()));
		
		final ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setScrollingCacheEnabled(false);

        if (allowMultiple) {
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		} else {
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		setListAdapter(adapter);
		setListShown(false);

        getListView().setDivider(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.grey_300)));
        getListView().setDividerHeight(1);
		
		LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof FileActivity) {
			fileActivity = (FileActivity) context;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FileListAdapter adapter = (FileListAdapter) l.getAdapter();
		if (adapter == null || fileActivity == null) {
			return;
		}

		File file = adapter.getItem(position);

		if (file.isDirectory()) {
			fileActivity.moveToDirectory(file);
			return;
		}

		if (file.length() == 0) {
			Toast.makeText(getActivity(), TranslationUtils.getToastGuideIncorrectFileFormat(getActivity()), Toast.LENGTH_SHORT).show();
			return;
		} else if (file.length() > maxFileSize) {
			Toast.makeText(getActivity(), TranslationUtils.getToastGuideMaxFileSize(getActivity(), (int) (maxFileSize / (1024 * 1024))), Toast.LENGTH_SHORT).show();
			return;
		}

		if (!allowMultiple) {
			path = file.getAbsolutePath();
			fileActivity.onFileSelected(file);
		} else if (fileActivity.getSelectedFiles().size() == maxFilesCount && !fileActivity.isExistFile(file)) {
			Toast.makeText(getActivity(), TranslationUtils.getToastGuideMaxFilesCount(getActivity(), maxFilesCount), Toast.LENGTH_SHORT).show();
		} else {
			fileActivity.onMultipleSelected(position, file);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
    @NonNull
	public Loader<List<File>> onCreateLoader(int id, Bundle args) {
		return new FileLoader(getActivity(), path);
	}

	@Override
	public void onLoadFinished(@NonNull Loader<List<File>> loader, List<File> data) {
		List<File> mData = new ArrayList<File>();
		
		for (File file : data) {
			if (file.isDirectory() || FileUtils.isMimeTypeEitherPhotoOrDocument(file)) {
				mData.add(file);
			}
		}
		
		adapter.setListItems(mData);

		if (isResumed()) {
			setListShown(true);
		}
		else {
			setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(@NonNull Loader<List<File>> loader) {
		adapter.clear();
	}
}