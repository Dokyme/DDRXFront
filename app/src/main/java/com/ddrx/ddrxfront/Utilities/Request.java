package com.ddrx.ddrxfront.Utilities;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ddrx.ddrxfront.Utilities.ToastUtil.prompt

/**
 * Created by dokym on 2018/3/28.
 */

public class Request {

    private okhttp3.Request.Builder builder;
    private Callback callback;

    public interface Callback {
        void onFailure(IOException exception);

        void onSuccess(Context context, JSONObject data, String message);

        void onUsernameNotExist(String message);

        void onWrongPassword(String message);

        void onServerInternalError(String message);

        void onWrongCookies(String message);

        void onWrongMacAddresss(String message);
    }

    public abstract class DefaultCallback implements Callback {

        private Context context;

        public DefaultCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onFailure(IOException exception) {
            prompt(context, "网络环境错误 " + exception.getMessage());
        }

        @Override
        public void onUsernameNotExist(String message) {
            prompt(context, "用户名不存在 " + message);
        }

        @Override
        public void onWrongPassword(String message) {
            prompt(context, "密码错误 " + message);
        }

        @Override
        public void onServerInternalError(String message) {
            prompt(context, "服务器内部错误 " + message);
        }

        @Override
        public void onWrongCookies(String message) {
            prompt(context, "无效的Cookies " + message);
        }

        @Override
        public void onWrongMacAddresss(String message) {
            prompt(context, "Mac地址错误 " + message);
        }
    }

    public static class Builder {

        private static final String HOST = "";

        private Request request;

        public Builder() {
            request = new Request();
        }

        public Request build() {
            return request;
        }

        public Builder get() {
            request.builder = request.builder.get();
            return this;
        }

        public Builder post(RequestBody body) {
            request.builder = request.builder.post(body);
            return this;
        }

        public Builder url(String url) {
            request.builder = request.builder.url(HOST + url);
            return this;
        }
    }

    public void execute(Context context) {
        OKHttpClientWrapper.getInstance(context)
                .newCall(builder.build())
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, JSONException {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (!object.has("code"))
                            throw new JSONException("Wrong Response Format:No Code Field");
                        switch ((int) object.get("code")) {
                            case 600:

                        }
                    }
                });
    }

    public Request enqueue(Callback callback) {
        this.callback = callback;
    }
}
