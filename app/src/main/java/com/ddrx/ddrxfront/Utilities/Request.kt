package com.ddrx.ddrxfront.Utilities

import android.content.Context
import android.os.Handler
import android.os.Message
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper.Companion.context

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

    private val MESSAGE_SUCCESS = 0
    private val MESSAGE_FAILURE = 1
    private var builder: okhttp3.Request.Builder = okhttp3.Request.Builder()
    private lateinit var callback: Callback

    private val handler: Handler = Handler({ msg: Message? ->
        when (msg?.what) {
            MESSAGE_SUCCESS -> {
                val response = msg.obj as String
                val `object` = JSONObject(response)
                if (!`object`.has("code") || !`object`.has("msg"))
                    throw JSONException("Wrong Response Format:No Code Field")
                val rmsg = `object`.getString("msg")
                when (`object`.getInt("code")) {
                    600 -> callback.onUsernameNotExist(rmsg)
                    601 -> callback.onWrongPassword(rmsg)
                    1000 -> callback.onServerInternalError(rmsg)
                    1001 -> callback.onWrongParamaters(rmsg)
                    1003 -> callback.onWrongCookies(rmsg)
                    1004 -> callback.onWrongMacAddresss(rmsg)
                    0 -> callback.onSuccess(context, `object`.getJSONArray("body"), rmsg)
                }
                true
            }
            MESSAGE_FAILURE -> {
                val e = msg.obj as IOException
                callback.onFailure(e)
                true
            }
            else -> true
        }
    })

    interface Callback {
        fun onFailure(exception: IOException)

        fun onSuccess(context: Context, data: JSONArray, message: String)

        fun onUsernameNotExist(message: String)

        fun onWrongPassword(message: String)

        fun onServerInternalError(message: String)

        fun onWrongCookies(message: String)

        fun onWrongMacAddresss(message: String)

        fun onWrongParamaters(message: String)
    }

    abstract class DefaultCallback(val context: Context) : Callback {

        override fun onFailure(exception: IOException) {
            prompt(context, "网络环境错误 " + exception.message)
            onAnyFailure("")
        }

        override fun onUsernameNotExist(message: String) {
            prompt(context, "用户名不存在 $message")
            onAnyFailure(message)
        }

        override fun onWrongPassword(message: String) {
            prompt(context, "密码错误 $message")
            onAnyFailure(message)
        }

        override fun onServerInternalError(message: String) {
            prompt(context, "服务器内部错误 $message")
            onAnyFailure(message)
        }

        override fun onWrongCookies(message: String) {
            prompt(context, "无效的Cookies $message")
            onAnyFailure(message)
        }

        override fun onWrongMacAddresss(message: String) {
            prompt(context, "Mac地址错误 $message")
            onAnyFailure(message)
        }

        override fun onWrongParamaters(message: String) {
            prompt(context, "参数错误 $message")
            onAnyFailure(message)
        }

        abstract fun onAnyFailure(message: String)
    }

    abstract class SuccessfulCallback(context: Context) : DefaultCallback(context) {
        override fun onAnyFailure(message: String) {

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
                        val message = Message()
                        message.what = MESSAGE_FAILURE
                        message.obj = e
                        handler.sendMessage(message)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val message = Message()
                        message.what = MESSAGE_SUCCESS
                        message.obj = response.body()?.string()
                        handler.sendMessage(message)
                    }
                })
    }

    fun enqueue(callback: Callback): Request {
        this.callback = callback
        return this
    }
}
