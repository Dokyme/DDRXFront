package com.ddrx.ddrxfront.Utilities

import android.content.Context

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.Call
import okhttp3.RequestBody
import okhttp3.Response

import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import org.json.JSONArray

/**
 * Created by dokym on 2018/3/28.
 */

class Request {

    private var builder: okhttp3.Request.Builder = okhttp3.Request.Builder()
    private lateinit var callback: Callback

    interface Callback {
        fun onFailure(exception: IOException)

        fun onSuccess(context: Context, data: JSONArray, message: String)

        fun onUsernameNotExist(message: String)

        fun onWrongPassword(message: String)

        fun onServerInternalError(message: String)

        fun onWrongCookies(message: String)

        fun onWrongMacAddresss(message: String)
    }

    abstract class DefaultCallback(private val context: Context) : Callback {

        override fun onFailure(exception: IOException) {
            prompt(context, "网络环境错误 " + exception.message, true)
        }

        override fun onUsernameNotExist(message: String) {
            prompt(context, "用户名不存在 $message", true)
        }

        override fun onWrongPassword(message: String) {
            prompt(context, "密码错误 $message", true)
        }

        override fun onServerInternalError(message: String) {
            prompt(context, "服务器内部错误 $message", true)
        }

        override fun onWrongCookies(message: String) {
            prompt(context, "无效的Cookies $message", true)
        }

        override fun onWrongMacAddresss(message: String) {
            prompt(context, "Mac地址错误 $message", true)
        }
    }

    class Builder {

        private val request: Request

        init {
            request = Request()
        }

        fun build(): Request {
            return request
        }

        fun get(): Builder {
            request.builder = request.builder.get()
            return this
        }

        fun post(body: RequestBody): Builder {
            request.builder = request.builder.post(body)
            return this
        }

        fun url(url: String): Builder {
            request.builder = request.builder.url(HOSTNAME + url)
            return this
        }

        companion object {

            private val HOSTNAME = ""
        }
    }

    fun execute(context: Context) {
        OKHttpClientWrapper.getInstance(context)
                .newCall(builder.build())
                .enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(e)
                    }

                    @Throws(IOException::class, JSONException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val `object` = JSONObject(response.body()!!.string())
                        if (!`object`.has("code") || !`object`.has("msg"))
                            throw JSONException("Wrong Response Format:No Code Field")
                        val msg = `object`.getString("msg")
                        when (`object`.getInt("code")) {
                            600 -> callback.onUsernameNotExist(msg)
                            601 -> callback.onWrongPassword(msg)
                            1000 -> callback.onServerInternalError(msg)
                            1003 -> callback.onWrongCookies(msg)
                            1004 -> callback.onWrongMacAddresss(msg)
                            0 -> callback.onSuccess(context, `object`.getJSONArray("body"), msg)
                        }
                    }
                })
    }

    fun enqueue(callback: Callback): Request {
        this.callback = callback
        return this
    }
}
