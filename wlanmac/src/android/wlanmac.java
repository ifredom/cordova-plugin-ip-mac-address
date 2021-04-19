package com.ifredom.wlanmac;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import android.os.Build;

/**
 * This class echoes a string called from JavaScript.
 */
public class wlanmac extends CordovaPlugin {
    CallbackContext callbackCtx;
    Context ctx;
    Activity activity;

    public wlanmac() {
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("getMACAddress")) {

            String macAddress = "02:00:00:00:00:00";
            callbackCtx = callbackContext;
            Context context = this.cordova.getActivity().getApplicationContext();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // this.showToast("这里", callbackContext);
                macAddress = getMacDefault(context);
            } else {
                // macAddress = getMacFromHardware();
            }

            callbackContext.success(macAddress);

            return true;
        }
        return false;
    }

    /**
     * * Android 5.0 （ version <= 5.0 ） ? Required permissions ! < uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE" />
     * 
     * ? 获取mac地址有一点需要注意的就是android 6.0版本后，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址
     * .这是googel官方为了加强权限管理而禁用了 getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。
     */

    private String getMacDefault(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String macAddress = winfo.getMacAddress();
        return macAddress;
    }

    /**
     * * Android 7.0（ version >= 7.0 ）
     */
    // private String getMacFromHardware() {

    //     String macAddress = null;
    //     StringBuffer buf = new StringBuffer();
    //     NetworkInterface networkInterface = null;
    //     try {
    //         networkInterface = NetworkInterface.getByName("eth1");
    //         if (networkInterface == null) {
    //             networkInterface = NetworkInterface.getByName("wlan0");
    //         }
    //         if (networkInterface == null) {
    //             return "02:00:00:00:00:02";
    //         }
    //         byte[] addr = networkInterface.getHardwareAddress();
    //         for (byte b : addr) {
    //             buf.append(String.format("%02X:", b));
    //         }
    //         if (buf.length() > 0) {
    //             buf.deleteCharAt(buf.length() - 1);
    //         }
    //         macAddress = buf.toString();
    //     } catch (SocketException e) {
    //         e.printStackTrace();
    //         return "02:00:00:00:00:02";
    //     }
    //     return macAddress;
    // }

    private void showToast(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
