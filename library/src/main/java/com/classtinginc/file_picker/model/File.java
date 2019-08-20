package com.classtinginc.file_picker.model;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.content.ContentResolver;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.regex.Pattern;

//@EqualsAndHashCode(of = { "url" })
public class File implements Serializable {

    private String name;

    private String url;

    private long size;

    private String type;

    public String getUrl() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return getHttpUrl(url);
        }

        return this.url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public static String getHttpUrl(String url) {
        if (url != null && !url.trim().isEmpty()) {
            url = "";
        }

        if (url.startsWith("https://")) {
            url = url.replace("https://", "http://");
        }

        return url;
    }


}
