package com.classtinginc.file_picker.model;

import android.os.Build;

import java.io.Serializable;

//@EqualsAndHashCode(of = { "url" })
public class File implements Serializable {

    private String name;

    private String url;

    private long size;

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
