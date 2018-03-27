package com.ddrx.ddrxfront.Utilities

import android.content.Context
import java.net.InetAddress
import java.net.NetworkInterface


/**
 * Created by dokym on 2018/3/24.
 */
class MacAddressUtil(context: Context) {
    var preMacAddress: String by Preference(context, "mac", "")

    var macAddress: String? = null
        get() {
            if (preMacAddress == "")
                preMacAddress = getMacAddr()
            return preMacAddress
        }

    fun getMacAddr(): String {
        var strMacAddr = ""
        try {
            val ip = getLocalInetAddr()
            val bytes = NetworkInterface.getByInetAddress(ip).hardwareAddress
            val buffer = StringBuffer()
            for (i in 0..bytes.size - 1) {
                if (i != 0)
                    buffer.append(":")
                val str = Integer.toHexString(bytes[i].toInt())
                if (str.isEmpty())
                    buffer.append("0$str")
                else
                    buffer.append(str)
            }
            strMacAddr = buffer.toString().toUpperCase()
            return strMacAddr
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strMacAddr
    }

    private fun getLocalInetAddr(): InetAddress? {
        var ip: InetAddress? = null
        try {
            val netInterfaces = NetworkInterface.getNetworkInterfaces()
            while (netInterfaces.hasMoreElements()) {
                val ni = netInterfaces.nextElement() as NetworkInterface
                val ips = ni.inetAddresses
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement()
                    if (!ip.isLoopbackAddress && ip.hostAddress.indexOf(":") == -1)
                        break
                    else
                        ip = null
                }
                if (ip != null)
                    break
            }
            return ip
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ip
    }

}