package com.ddrx.ddrxfront.Utilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by vincentshaw on 2018/3/22.
 */

public class OKHttpClientWrapper {
    public static OkHttpClient getInstance() {
        if (client == null) {
            File cache = new File("OKHttpCache");

            if (!cache.exists())
                cache.mkdirs();
            int cacheSize = 10 * 1024 * 1024;
            client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .pingInterval(20, TimeUnit.SECONDS)
                    .cache(new Cache(cache.getAbsoluteFile(), cacheSize))
                    .build();
        }
        return client;
    }

    private static OkHttpClient client;

    private OKHttpClientWrapper() {
    }
}
