package com.classtinginc.file_picker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker.with(MainActivity.this)
                        .style(R.style.FilePickerStyle)
                        .maxCount(2)
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
