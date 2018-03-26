package com.ddrx.ddrxfront.Utilities;

/**
 * Created by dokym on 2018/3/24.
 */

public class URLBuilder {

    private static final String HOSTNAME = "";

    private String url;

    public URLBuilder() {
        url = HOSTNAME;
    }

    public URLBuilder(String path) {
        url = HOSTNAME + path;
    }

    public URLBuilder append(String subpath) {
        if (!subpath.startsWith("/"))
            subpath = "/" + subpath;
        url = url + subpath;
        return this;
    }

    public String build() {
        return url;
    }
}
