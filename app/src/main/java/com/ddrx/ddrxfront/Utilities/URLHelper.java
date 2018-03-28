package com.ddrx.ddrxfront.Utilities;

/**
 * Created by dokym on 2018/3/24.
 */

public class URLHelper {

    private static final String HOSTNAME = "";

    public static final String MAC = "_MAC";
    public static final String USER_ID = "_USER_ID";
    public static final String USERNAME = "_USER_NAME";
    public static final String PASSWORD = "_PASSWORD";
    public static final String NICK_NAME = "_NICK_NAME";
    public static final String SEX = "_SEX";
    public static final String BIRTHDAY = "_BIRTH_DAY";
    public static final String CITY = "_CITY";
    public static final String BRIEF = "_BRIEF";
    public static final String PASSWORD_OLD = "_PASSWORD_O";
    public static final String PASSWORD_NEW = "_PASSWORD_N";

    private String url;

    public URLHelper() {
        url = HOSTNAME;
    }

    public URLHelper(String path) {
        url = HOSTNAME + path;
    }

    public URLHelper append(String subpath) {
        if (!subpath.startsWith("/"))
            subpath = "/" + subpath;
        url = url + subpath;
        return this;
    }

    public String build() {
        return url;
    }
}
