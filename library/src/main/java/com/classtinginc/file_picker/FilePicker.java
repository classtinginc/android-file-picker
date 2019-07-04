package com.classtinginc.file_picker;

import android.app.Activity;
import android.content.Intent;

import com.classtinginc.file_picker.consts.Extra;

/**
 * Created by classting on 02/07/2019.
 */

public class FilePicker {

    private Activity activity;
    private int maxFilesCount = Extra.DEFAULT_FILES_COUNT;
    private long maxFileSize = Extra.DEFAULT_FILE_SIZE;
    private boolean allowMultiple = Extra.DEFAULT_ALLOW_MULTIPLE;

    public FilePicker(Activity activity) {
        this.activity = activity;
    }

    public static FilePicker with(Activity context) {
        return new FilePicker(context);
    }

    public FilePicker maxCount(int maxFilesCount) {
        this.maxFilesCount = maxFilesCount;
        return this;
    }

    public FilePicker maxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    public FilePicker allowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
        return this;
    }

    public void startActivityForResult(int requestCode) {
        Intent intent = new Intent(activity, FileActivity.class);
        intent.putExtra(Extra.MAX_FILES_COUNT, maxFilesCount);
        intent.putExtra(Extra.MAX_FILE_SIZE, maxFileSize);
        intent.putExtra(Extra.ALLOW_MULTIPLE, allowMultiple);
        activity.startActivityForResult(intent, requestCode);
    }
}