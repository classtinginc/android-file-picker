package com.classtinginc.file_picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtinginc.library.file_picker.R;

import java.io.File;

public class ItemFile extends RelativeLayout {

    ImageView icon;
    TextView name;
    CheckBox checkBox;

    public ItemFile(Context context) {
        super(context);
        init();
    }

    public ItemFile(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ItemFile(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        LinearLayout.inflate(getContext(), R.layout.item_file, this);

        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        checkBox = findViewById(R.id.checkbox);

        checkBox.setClickable(false);
        checkBox.setFocusable(false);
    }

    public void bind(File file, boolean isChecked, boolean allowMultiple) {
        name.setText(file.getName());
        icon.setImageResource(file.isDirectory() ? R.drawable.finder_folder : R.drawable.ic_attached_file);

        if (file.isDirectory() || !allowMultiple) {
            checkBox.setVisibility(View.GONE);
        } else {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(isChecked);
        }
    }
}
