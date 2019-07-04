package com.classtinginc.file_picker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.classtinginc.file_picker.consts.TranslationKey;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HashMap<TranslationKey, String> translations = new HashMap<>();

        translations.put(TranslationKey.BTN_CANCEL, getString(R.string.btn_cancel));
        translations.put(TranslationKey.BTN_SELECT, getString(R.string.btn_select));
        translations.put(TranslationKey.TOAST_GUIDE_MAX_FILES_COUNT, getString(R.string.toast_write_post_attach_file_limit));
        translations.put(TranslationKey.TOAST_GUIDE_MAX_FILE_SIZE, getString(R.string.alert_write_post_file_size_limit_android));
        translations.put(TranslationKey.MSG_SELECT_ITEM, getString(R.string.count_device_storage_select_file));
        translations.put(TranslationKey.MSG_SELECT_ITEM_PL, getString(R.string.count_device_storage_select_file_pl));

        findViewById(R.id.multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker.with(MainActivity.this)
                        .style(R.style.FilePickerStyle)
                        .maxCount(2)
                        .translations(translations)
                        .allowMultiple(true)
                        .startActivityForResult(1);
            }
        });

        findViewById(R.id.single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker.with(MainActivity.this)
                        .maxCount(2)
                        .allowMultiple(false)
                        .startActivityForResult(1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Log.e("filePicker", data.toString());
        }
    }
}
