package com.ddrx.ddrxfront.Utilities;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by vincentshaw on 2018/3/22.
 */

public class OKHttpClientWrapper {

    private static Context context;

    private static OkHttpClient getInstance() {
        if (client == null) {
            File cache = new File("OKHttpCache");

            if (!cache.exists())
                cache.mkdirs();
            int cacheSize = 10 * 1024 * 1024;
            client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .pingInterval(20, TimeUnit.SECONDS)
                    .cookieJar(new PersistentCookieJar())
                    .cache(new Cache(cache.getAbsoluteFile(), cacheSize))
                    .build();
        }
        return client;
    }

    public static OkHttpClient getInstance(Context context) {
        setContext(context);
        return getInstance();
    }

    private static List<Cookie> cookieList;

    private static class PersistentCookieJar implements CookieJar {
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieList = cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return cookieList;
        }
    }

    public static void setContext(Context context) {
        OKHttpClientWrapper.context = context;
        cookieList = new CookiesPreference(context).getCookieList();
    }

    private static OkHttpClient client;

    private OKHttpClientWrapper() {
    }
}
