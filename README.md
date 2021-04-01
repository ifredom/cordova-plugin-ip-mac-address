# wlanmac plugin for Cordova / PhoneGap

- `wlanmac` is cordova plugin file (wlanmac 文件夹是插件，内含文档)
- `cordovaproject` is a test project（测试项目）

## How to use? (如何使用)

```js
cordova plugin add wlanmac
```

## npm 上传 cordova 插件

## 创建 cordova 插件

> 创建一个插件
>
> plugman create --name [pluginName] --plugin_id [pluginID] --plugin_version [version]

- pluginName: 插件名字;
- pluginID: 插件 id;
- plugin_version: 插件版本;
- directory:一个绝对或相对路径的目录，该目录将创建插件项目;

> 创建得插件名称为： wlanmac
> 在我本地得地址为： D:\mac-address-cordova-plugin\wlanmac

```json
// 0. 全局安装插件管理包: plugman
npm install -g plugman

// 1. 创建cordova插件 (注意：①插件名一定要小写，不要大小写混合,不要使用短横线.②插件id与包名要相互对应)
plugman create --name wlanmac --plugin_id com.ifredom.wlanmac --plugin_version 1.0.0

// 2. 切换目录到刚创建得插件文件夹 wlanmac 中
cd wlanmac

// 3. 为该插件添加android平台
plugman platform add --platform_name android

// 4. 初始化npm配置文件 ( 一直按enter回车键 )
npm init

// 5.修改src/android/wlanmac.java文件内容，完成自定义插件(默认不修改也可以运行)
```

## 创建一个 cordova 新项目

> 创建得新项目名称为： cordovaplugin
> 在我本地得地址为： D:\mac-address-cordova-plugin\cordovaproject

```json
// 1. 创建一个全新cordova项目,用于测试刚创建得插件
cordova  create  cordovaproject com.ifredom  （创建cordova工程  <工程文件名> <包名>）

// 2. 切换目录到刚创建得项目 cordovaplugin 中
cd cordovaproject

// 3. 为测试项目 cordovaplugin ，添加平台
cordova platform add android@8.1.0

// 4. 将新创建得插件，添加到测试项目test中 (使用本机绝对地址)
// （发布之后可以使用另一种加载方式: 例如：cordova plugin add wlanmac
cordova plugin add D:\mac-address-cordova-plugin\wlanmac

// 5. 运行cordova项目
cordova run android
```

---

> 其他： 删除插件 cordova plugin remove com.ifredom.wlanmac

> wlanmac.java

```java
package ipmac.address.ifredom;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

/**
 * This class echoes a string called from JavaScript.
 */
public class wlanmac extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
           String message = args.getString(0);
           this.coolMethod(message, callbackContext);
           return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}

```

## 自定义插件内容

上面两步，已经可以可以正常启动 app 了，自定义插件内容需要修改得地方

- 1.`wlanmac/src/andorid/wlanmac.java` , 插件核心文件用于定义原生方法，必须重写 `action`
- 2.`wlanmac/www/wlanmac.js`,必须自定义导出得 js 函数
- 3.`cordovaproject/www/index.html`， 前两步完成插件编写，添加到测试 cordorva 项目。在测试 cordorva 项目中，使用 html 进行测试，调用前面两步定义得原生方法
- 4. 添加插件`cordova platforms add android` , 接着运行项目`cordova run android`

> wlanmac.java

```java
package com.ifredom.wlanmac;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

/**
 * This class echoes a string called from JavaScript.
 */
public class wlanmac extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.showToast(message, callbackContext);

            // String message = args.getString(0);
            // this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
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

```

> 示例： wlanmac.js

```js
var exec = require("cordova/exec");
// "wlanmac" 为 plugin.xml 中配置的 feature 的name名
// "showToast" 为给 wlanmac.java判断的action名
exports.showToast = function (arg0, success, error) {
  exec(success, error, "wlanmac", "showToast", [arg0]);
};
```

> 示例：index.html

```html
<button id="test-button">测试按钮</button>
<script>
  document.addEventListener("deviceready", onDeviceReady, false);

  function onDeviceReady() {
    console.log(
      "Running cordova-" + cordova.platformId + "@" + cordova.version
    );
    document.getElementById("deviceready").classList.add("ready");

    var clickButton = document.getElementById("test-button");
    clickButton.addEventListener(
      "click",
      function (params) {
        alert(cordova.platformId);
        cordova.plugins.wlanmac.coolMethod("Helloworld", success, error);
      },
      false
    );
  }

  function success(params) {
    console.log("success callback");
    alert(`success callback: ${params}`);
  }
  function error(params) {
    alert(`error callback: ${params}`);
  }
</script>
```

### 优化调用原生方法

```js
// 调用优化后：
WlanmacPlugin.coolMethod("Helloworld", success, error);

// 调用优化前：
cordova.plugins.wlanmac.coolMethod("Helloworld", success, error);
```

```xml
<?xml version='1.0' encoding='utf-8'?>
<plugin id="com.ifredom.wlanmac" version="1.0.0"
  xmlns="http://apache.org/cordova/ns/plugins/1.0"
  xmlns:android="http://schemas.android.com/apk/res/android">
  <name>wlanmac</name>
  <js-module name="wlanmac" src="www/wlanmac.js">
    <!-- <clobbers target="cordova.plugins.wlanmac" /> -->
    <clobbers target="WlanmacPlugin" />
  </js-module>
  <platform name="android">
    <config-file parent="/*" target="res/xml/config.xml">
      <feature name="wlanmac">
        <param name="android-package" value="com.ifredom.wlanmac.wlanmac" />
      </feature>
    </config-file>
    <config-file parent="/*" target="AndroidManifest.xml"></config-file>
    <source-file src="src/android/wlanmac.java" target-dir="src/com/ifredom/wlanmac/wlanmac" />
  </platform>
</plugin>
```

### 参考资料

[cordova 官方文档](https://cordova.apache.org/docs/en/10.x/guide/cli/index.html)
[plugman 插件文档](https://cordova.apache.org/docs/en/latest/plugin_ref/plugman.html)
[Android~获取 WiFi MAC 地址和 IP 方法汇总](https://blog.csdn.net/Bluechalk/article/details/87877008)
[简化插件调用方法](https://www.cnblogs.com/VoiceOfDreams/p/11073447.html)
[android 各版版本兼容](https://www.jianshu.com/p/16d4ff4c4cbe)
