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
    private String getMacAddr( ) {

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
