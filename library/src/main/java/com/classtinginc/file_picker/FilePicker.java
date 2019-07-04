package com.classtinginc.file_picker;

import android.app.Activity;
import android.content.Intent;

import com.classtinginc.file_picker.consts.Extra;
import com.classtinginc.file_picker.consts.TranslationKey;

import java.util.HashMap;

/**
 * Created by classting on 02/07/2019.
 */

public class FilePicker {

    private Activity activity;
    private int maxFilesCount = Extra.DEFAULT_FILES_COUNT;
    private long maxFileSize = Extra.DEFAULT_FILE_SIZE;
    private boolean allowMultiple = Extra.DEFAULT_ALLOW_MULTIPLE;
    private int style;
    private HashMap<TranslationKey, String> translations = new HashMap<>();

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

    public FilePicker style(int style) {
        this.style = style;
        return this;
    }

    public FilePicker translations(HashMap<TranslationKey, String> translations) {
        this.translations = translations;
        return this;
    }

    public void startActivityForResult(int requestCode) {
        Intent intent = new Intent(activity, FileActivity.class);
        intent.putExtra(Extra.STYLE, style);
        intent.putExtra(Extra.MAX_FILES_COUNT, maxFilesCount);
        intent.putExtra(Extra.MAX_FILE_SIZE, maxFileSize);
        intent.putExtra(Extra.ALLOW_MULTIPLE, allowMultiple);
        intent.putExtra(Extra.TRANSLATIONS, translations);
        activity.startActivityForResult(intent, requestCode);
    }
}
