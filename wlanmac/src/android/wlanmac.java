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
import java.util.Collections;
import java.util.List;

import java.io.*;

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
                try {
                    macAddress = getMacDefault(context);
                } catch (Exception e) {
                }

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                // try {
                // macAddress = getMacFromFile();
                // } catch (Exception e) {
                // }
                macAddress = getMacFromHardware();

                
            callbackContext.success(macAddress);

            return true;
        }
        return false;
    }

    /**
     * * Android 5.0 （ version <= 5.0 ） ? Required permissions ! < uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE" />
     * 
     * ? 获取mac地址有一点需要注意的就是android
     * 6.0版本后，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址
     * .这是googel官方为了加强权限管理而禁用了 getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。
     */

    private String getMacDefault(Context context) throws Exception {
        String macAddress = "02:00:00:00:00:00";

        if (context == null) {
        return macAddress;
        }
            WifiManager wifi = (WifiManager)
        context.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) { return macAddress;
        }
            WifiInfo info = null;
        try {
        info = wifi.getConnectionInfo();
        } cat
            }


    }

     Android 6.0（ version == 6.0 ） ? 由于Android底层基于Linux系统 可以根据shell获取
     * 
     * @return {WifiAddress}
     */
    private String getMacFromFile() throws Exception {
        String str = "";
        String macSerial = "";
        try {

            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                macSerial = getMacFromHardware();
            }
        }
        return macSerial;
    }

    /**
     * * Android 7.0（ version >= 7.0 ）
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

    private String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
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

    private void showToast(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
