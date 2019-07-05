/* 
 * Copyright (C) 2013 Paul Burke
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.classtinginc.file_picker.consts.Extra;
import com.classtinginc.file_picker.consts.TranslationKey;
import com.classtinginc.file_picker.utils.ActivityUtils;
import com.classtinginc.file_picker.utils.FileUtils;
import com.classtinginc.file_picker.utils.TranslationUtils;
import com.classtinginc.library.file_picker.R;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


/**
 * Main Activity that handles the FileListFragments 
 */
public class FileActivity extends AppCompatActivity implements OnBackStackChangedListener {

    public static final String PATH = "path";
	public static final String EXTERNAL_BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	private FragmentManager fragmentManager;
	private LinkedHashMap<String, String> selectedFiles;
	private int maxFilesCount;
	private long maxFileSize;
    public  boolean allowMultiple;
    private String path;

    private LinearLayout selectionContainer;
	private Button btnCancel;
	private Button btnSelect;
	
	private BroadcastReceiver mStorageListener = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, TranslationUtils.getToastRemovedStorage(context), Toast.LENGTH_LONG).show();
			onFileSelected(null);
		}
	};
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(getIntent().getIntExtra(Extra.STYLE, R.style.AppTheme_NoActionBar));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
		Intent intent = getIntent();

		fragmentManager = getSupportFragmentManager();
		fragmentManager.addOnBackStackChangedListener(this);

		TranslationUtils.setTranslations(
				(HashMap< TranslationKey, String>) getIntent().getSerializableExtra(Extra.TRANSLATIONS));

		if (savedInstanceState == null) {
			path = EXTERNAL_BASE_PATH;
			selectedFiles = new LinkedHashMap<>();
			maxFilesCount = intent.getIntExtra(Extra.MAX_FILES_COUNT, Extra.DEFAULT_FILES_COUNT);
            maxFileSize = intent.getLongExtra(Extra.MAX_FILE_SIZE, Extra.DEFAULT_FILE_SIZE);
            allowMultiple = intent.getBooleanExtra(Extra.ALLOW_MULTIPLE, Extra.DEFAULT_ALLOW_MULTIPLE);

			addFragment();
		} else {
			path = savedInstanceState.getString(PATH);
			selectedFiles = (LinkedHashMap<String, String>)savedInstanceState.getSerializable(Extra.DATA);
			maxFilesCount = savedInstanceState.getInt(Extra.MAX_FILES_COUNT);
            maxFileSize = savedInstanceState.getLong(Extra.MAX_FILE_SIZE);
            allowMultiple = intent.getBooleanExtra(Extra.ALLOW_MULTIPLE, Extra.DEFAULT_ALLOW_MULTIPLE);
		}

        ActivityUtils.setNavigation(getSupportActionBar(), path);

		selectionContainer = findViewById(R.id.selection_container);
		btnCancel = findViewById(R.id.btn_cancel);
		btnSelect = findViewById(R.id.btn_select);

		btnCancel.setText(TranslationUtils.getButtonCancel(this));
		btnSelect.setText(TranslationUtils.getButtonSelect(this));

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onFileSelected(null);
			}
		});
		btnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onFileSelected();
			}
		});

        if (allowMultiple) {
			showStatusOfSelectionMenu();
			selectionContainer.setVisibility(View.VISIBLE);
		} else {
			selectionContainer.setVisibility(View.GONE);
		}
	}

    @Override
    protected void onResume() {
        super.onResume();
        registerStorageListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterStorageListener();
    }
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(PATH, path);
		outState.putSerializable(Extra.DATA, selectedFiles);
		outState.putInt(Extra.MAX_FILES_COUNT, maxFilesCount);
		outState.putLong(Extra.MAX_FILE_SIZE, maxFileSize);
		outState.putBoolean(Extra.ALLOW_MULTIPLE, allowMultiple);
	}

	@Override
	public void onBackStackChanged() {
		
		int count = fragmentManager.getBackStackEntryCount();
		if (count > 0) {
            BackStackEntry fragment = fragmentManager.getBackStackEntryAt(count - 1);
            path = fragment.getName();
		} else {
		    path = EXTERNAL_BASE_PATH;
		}
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(path);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

	public void putSelectedFile(int position, File file) {
		selectedFiles.put(file.getName() + String.valueOf(position), file.getAbsolutePath());
	}
	
	public void removeSelectedFile(int position, File file) {
		selectedFiles.remove(file.getName() + String.valueOf(position));
	}
	
	public HashMap<String, String> getSelectedFiles() {
		return this.selectedFiles;
	}

	public boolean isExistFile(File file) {
		for (Object o : selectedFiles.entrySet()) {
			Entry pairs = (Entry) o;
			if (pairs.getValue().equals(file.getAbsolutePath())) {
				return true;
			}
		}

		return false;
	}

	public void showStatusOfSelectionMenu() {
		selectionContainer.setVisibility(View.VISIBLE);

		int selectedFilesNumber = getSelectedFiles().size();
		
		if (selectedFilesNumber == 0) {
			btnSelect.setEnabled(false);
			btnSelect.setText(TranslationUtils.getButtonSelect(this));
		} else if (selectedFilesNumber == 1) {
			btnSelect.setEnabled(true);
			String selectionMsg = TranslationUtils.getMsgSelectItem(this, selectedFilesNumber);
			btnSelect.setText(selectionMsg);
		} else {
			btnSelect.setEnabled(true);
			String selectionMsg = TranslationUtils.getMsgSelectItemPl(this, selectedFilesNumber);
			btnSelect.setText(selectionMsg);
		}
	}

	private FileListFragment newFragment() {
        return FileListFragment.newInstance(path, maxFilesCount, maxFileSize, allowMultiple);
    }

	/**
	 * Add the initial Fragment with given path.
	 */
	private void addFragment() {
		FileListFragment fragment = newFragment();
		fragmentManager.beginTransaction()
				.add(R.id.explorer_fragment, fragment).commit();
	}

	/**
	 * "Replace" the existing Fragment with a new one using given path.
	 * We're really adding a Fragment to the back stack.
	 * 
	 * @param file The file (directory) to display.
	 */
	private void replaceFragment(File file) {
        path = file.getAbsolutePath();

        FileListFragment fragment = newFragment();
		fragmentManager.beginTransaction()
				.replace(R.id.explorer_fragment, fragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.addToBackStack(path).commit();
	}

	/**
	 * Finish this Activity with a result code and URI of the selected file.
	 * 
	 * @param file The file selected.
	 */
	public void finishWithResult(File file) {
		if (file != null) {
            com.classtinginc.file_picker.model.File temp = new com.classtinginc.file_picker.model.File();
            temp.setUrl(file.getAbsolutePath());
            temp.setName(FileUtils.getFileName(file.getAbsolutePath()));
            temp.setSize(file.length());

			ArrayList<com.classtinginc.file_picker.model.File> files = new ArrayList<>();
            files.add(temp);

			setResult(RESULT_OK, new Intent().putExtra(Extra.DATA, new Gson().toJson(files)));
			finish();
		} else {
			setResult(RESULT_CANCELED);	
			finish();
		}
	}
	
	public void finishWithResult() {
		if (selectedFiles != null) {
			ArrayList<com.classtinginc.file_picker.model.File> files = new ArrayList<>();

            for (Entry<String, String> pairs : selectedFiles.entrySet()) {
                File file = FileUtils.getFile(pairs.getValue());
                com.classtinginc.file_picker.model.File item = new com.classtinginc.file_picker.model.File();
                item.setUrl(pairs.getValue());
                item.setName(FileUtils.getFileName(pairs.getValue()));
                item.setSize(file.length());
                files.add(item);
            }
			
			setResult(RESULT_OK, new Intent().putExtra(Extra.DATA, new Gson().toJson(files)));
			finish();
		} else {
			setResult(RESULT_CANCELED);	
			finish();
		}
	}

	protected void moveToDirectory(File directory) {
        replaceFragment(directory);
    }
	
	/**
	 * Called when the user selects a File
	 * 
	 * @param file The file that was selected
	 */
	protected void onFileSelected(File file) {
        if (file == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        finishWithResult(file);
	}
	
	protected void onMultipleSelected(int position, File file) {
		if (file == null) {
			Toast.makeText(FileActivity.this, TranslationUtils.getToastGuideErrorOnFile(this), Toast.LENGTH_SHORT).show();
            return;
		}

        if (!selectedFiles.containsKey(file.getName() + String.valueOf(position))) {
			putSelectedFile(position, file);
			showStatusOfSelectionMenu();
		} else {
			removeSelectedFile(position, file);
			showStatusOfSelectionMenu();
		}
	}
	
	protected void onFileSelected() {
		if (selectedFiles != null) {
			finishWithResult();
		} else {
			Toast.makeText(FileActivity.this, TranslationUtils.getToastGuideErrorOnFile(this), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Register the external storage BroadcastReceiver.
	 */
	private void registerStorageListener() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
		registerReceiver(mStorageListener, filter);
	}

	private void unregisterStorageListener() {
		unregisterReceiver(mStorageListener);
	}
}
