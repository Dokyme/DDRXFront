package com.ddrx.ddrxfront.Utilities

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 * Created by dokym on 2018/3/31.
 */
class OKHttpClientWrapper private constructor() {
    companion object {
        lateinit var context: Context

        fun getInstance(context: Context): OkHttpClient {
            this.context = context
            return Inner.instance
        }
    }

    private object Inner {
        val instance = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .pingInterval(5, TimeUnit.SECONDS)
                .cookieJar(PreferenceCookieJar(context))
                .build()!!
    }

    class PreferenceCookieJar(context: Context) : CookieJar {

        var cookies: List<SerializableOkHttpCookie> by Preference(context, "cookies", emptyList())

        override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
            if (cookies != null) {
                this.cookies = cookies.map { cookie: Cookie -> SerializableOkHttpCookie(cookie) }
            }
        }

        override fun loadForRequest(url: HttpUrl?): List<Cookie> {
            return cookies.map { serializableOkHttpCookie -> serializableOkHttpCookie.cookie }
        }
    }
}

class SerializableOkHttpCookie(var cookie: Cookie) : Serializable {

    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie.name())
        out.writeObject(cookie.value())
        out.writeLong(cookie.expiresAt())
        out.writeObject(cookie.domain())
        out.writeObject(cookie.path())
        out.writeBoolean(cookie.secure())
        out.writeBoolean(cookie.httpOnly())
        out.writeBoolean(cookie.hostOnly())
        out.writeBoolean(cookie.persistent())
    }

    private fun readObject(in_: ObjectInputStream) {
        val name = in_.readObject() as String
        val value = in_.readObject() as String
        val expireAt = in_.readLong()
        val domain = in_.readObject() as String
        val path = in_.readObject() as String
        val secure = in_.readBoolean()
        val httpOnly = in_.readBoolean()
        val hostOnly = in_.readBoolean()
        val persistent = in_.readBoolean()
        val builder = Cookie.Builder()
                .name(name)
                .value(value)
                .expiresAt(expireAt)
                .path(path)
        if (secure)
            builder.secure()
        if (httpOnly)
            builder.httpOnly()
        if (hostOnly)
            builder.hostOnlyDomain(domain)
        else
            builder.domain(domain)
        cookie = builder.build()
    }
}