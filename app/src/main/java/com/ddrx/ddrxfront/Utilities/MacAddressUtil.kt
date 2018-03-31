package com.ddrx.ddrxfront.Utilities

import android.content.Context
import android.net.wifi.WifiManager
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


/**
 * Created by dokym on 2018/3/24.
 */
class MacAddressUtil(var context: Context) {

    var macAddress: String? = null
        get() {
            return getMacAddr()
        }

    fun getMacAddr(): String {
        var mac = ""
        try {
            val process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(process.inputStream)
            val input = LineNumberReader(ir)
            var str = ""
            while (str != null) {
                str = input.readLine()
                if (str != null) {
                    mac = str.trim()
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mac
    }

    private fun getMacAddrUnder6(): String {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifiManager.connectionInfo.macAddress
        } catch (e: Exception) {
            return ""
        }
    }

}