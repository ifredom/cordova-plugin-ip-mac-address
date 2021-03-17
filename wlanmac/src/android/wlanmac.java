package com.ifredom.wlanmac;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import java.io.File;
import java.io.IOException;

import android.os.Build;

/**
 * This class echoes a string called from JavaScript.
 */
public class wlanmac extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        // String systemVersion = this.getSystemVersion();
        // int sdkVersion = this.getSdkVersion();

        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.showToast(message, callbackContext);
            return true;
        }
        if (action.equals("getMACAddress")) {

            String macAddress = "02:00:00:00:00:00";
            Context context = this.cordova.getActivity().getApplicationContext();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                // macAddress = this.getMacDefault(context);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                // macAddress = getMacFromFile();

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                macAddress = this.getMacFromHardware();
            }
            Toast.makeText(this.cordova.getActivity(), macAddress, Toast.LENGTH_SHORT).show();
            callbackContext.success(macAddress);
            return true;
        }
        return false;
    }

    /**
     * 高于等于android9.0版本
     */
    private String getMacFromHardware() {

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
     * Android 6.0 之前（不包括6.0），获取mac 必须权限 < uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE" />
     */

    /*
     * 获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，
     * 不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址
     * 这是googel官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。
     */
    private String getMacDefault(Context context) {
        String macAddress = "02:00:00:00:00:00";

        if (context == null) {
            return macAddress;
        }
        // WifiManager wifi = (WifiManager)
        // context.getSystemService(Context.WIFI_SERVICE);
        // if (wifi == null) {
        // return macAddress;
        // }
        // WifiInfo info = null;
        // try {
        // info = wifi.getConnectionInfo();
        // } catch (Exception e) {
        // }
        // if (info == null) {
        // return null;
        // }
        // macAddress = info.getMacAddress();
        // if (!TextUtils.isEmpty(macAddress)) {
        // macAddress = macAddress.toUpperCase(Locale.ENGLISH);
        // }
        return macAddress;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * 
     * @return
     */
    // private static String getMacFromFile() {
    // String WifiAddress = "02:00:00:00:00:00";
    // try {
    // WifiAddress = new BufferedReader(new FileReader(new
    // File("/sys/class/net/wlan0/address"))).readLine();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return WifiAddress;
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

    /**
     * 获取当前手机系统版本号
     * 
     * @return 系统版本号
     */
    public String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机SDK版本号
     * 
     * @return SDK版本号
     */
    public int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }
}
