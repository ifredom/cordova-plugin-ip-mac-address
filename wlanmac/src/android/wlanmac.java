package com.ifredom.wlanmac;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * This class echoes a string called from JavaScript.
 */
public class wlanmac extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.showToast(message, callbackContext);
            return true;
        }
        if (action.equals("getMACAddress")) {
            String macAddress = this.getMacAddr();
            Toast.makeText(this.cordova.getActivity(), macAddress, Toast.LENGTH_SHORT).show();
            callbackContext.success(macAddress);
            return true;
        }
        return false;
    }

    /**
     * 高于等于android7.0版本
     */
    private String getMacAddr() {

        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }

    /**
     * 低于等于android6.0版本，获取mac
     */

    /*
     * 获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，
     * 不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址
     * 这是googel官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。
     */
    private String getMacAddrBelowAndroid6(CallbackContext callbackContext) {
        String macAddress = "02:00:00:00:00:02";

        // callbackCtx = callbackContext;
        // ctx = this.cordova.getActivity().getApplicationContext();
        // activity = this.cordova.getActivity();
        // WifiManager wifiManager = (WifiManager) ctx.getSystemService(WIFI_SERVICE);
        // macAddress = wifiManager.getConnectionInfo().getMacAddress();

        // callbackCtx.success(macAddress);
        // result.setKeepCallback(true);
        // callbackCtx.sendPluginResult(result);

        return macAddress;
    }

    /** 获得IP地址，分为两种情况，一是wifi下，二是移动网络下，得到的ip地址是不一样的 */
    // public static String getIPAddress() {
    //     Context context = MyApp.getContext();
    //     NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
    //             .getActiveNetworkInfo();
    //     if (info != null && info.isConnected()) {
    //         if (info.getType() == ConnectivityManager.TYPE_MOBILE) {// 当前使用2G/3G/4G网络
    //             try {
    //                 // Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
    //                 for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
    //                         .hasMoreElements();) {
    //                     NetworkInterface intf = en.nextElement();
    //                     for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
    //                             .hasMoreElements();) {
    //                         InetAddress inetAddress = enumIpAddr.nextElement();
    //                         if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
    //                             return inetAddress.getHostAddress();
    //                         }
    //                     }
    //                 }
    //             } catch (SocketException e) {
    //                 e.printStackTrace();
    //             }

    //         } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {// 当前使用无线网络
    //             WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    //             WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    //             // 调用方法将int转换为地址字符串
    //             String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());// 得到IPV4地址
    //             return ipAddress;
    //         }
    //     } else {
    //         // 当前无网络连接,请在设置中打开网络
    //     }
    //     return null;
    // }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
    }

    /** 获取手机的IMEI号码 */
    // public static String getPhoneIMEI() {
    // TelephonyManager mTm = (TelephonyManager)
    // MyApp.getContext().getSystemService(Context.TELEPHONY_SERVICE);
    // String imei = mTm.getDeviceId();
    // String imsi = mTm.getSubscriberId();
    // String mtype = android.os.Build.MODEL; // 手机型号
    // String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
    // return imei;
    // }

    private void showToast(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
