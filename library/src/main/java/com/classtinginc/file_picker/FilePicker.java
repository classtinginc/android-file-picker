package com.classtinginc.file_picker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.classtinginc.file_picker.consts.Extra;
import com.classtinginc.file_picker.consts.TranslationKey;
import com.classtinginc.library.file_picker.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * Created by classting on 02/07/2019.
 */

public class FilePicker {

    private Activity activity;
    private int maxFilesCount = Extra.DEFAULT_FILES_COUNT;
    private int availableFilesCount = Extra.DEFAULT_AVAILABLE_FILES_COUNT;
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

    public FilePicker availableFilesCount(int availableFilesCount) {
        this.availableFilesCount = availableFilesCount;
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
        checkPermission(requestCode);
    }

    @TargetApi(16)
    private void checkPermission(final int requestCode) {
        RxPermissions.getInstance(activity)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            Intent intent = new Intent(activity, FileActivity.class);
                            intent.putExtra(Extra.STYLE, style);
                            intent.putExtra(Extra.MAX_FILES_COUNT, maxFilesCount);
                            intent.putExtra(Extra.AVAILABLE_FILES_COUNT, availableFilesCount);
                            intent.putExtra(Extra.MAX_FILE_SIZE, maxFileSize);
                            intent.putExtra(Extra.ALLOW_MULTIPLE, allowMultiple);
                            intent.putExtra(Extra.TRANSLATIONS, translations);
                            activity.startActivityForResult(intent, requestCode);
                        } else {
                            Toast.makeText(
                                    activity,
                                    activity.getString(R.string.alert_device_permission_denied),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }
}
