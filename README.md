# IpMacAddress plugin for Cordova / PhoneGap

> 创建一个插件
>
> plugman create --name [pluginName] --plugin_id [pluginID] --plugin_version [version]

- pluginName: 插件名字;
- pluginID: 插件 id;
- plugin_version: 插件版本;
- directory:一个绝对或相对路径的目录，该目录将创建插件项目;

> 创建得插件名称为： ipmacaddress
> 在我本地得地址为： D:\mac-address-cordova-plugin\ipmacaddress

```json
// 0. 全局安装插件管理包: plugman
npm install -g plugman

// 1. 创建cordova插件 (注意：①插件名一定要小写，不要大小写混合,不要使用短横线.②插件id与包名要相互对应)
plugman create --name ipmacaddress --plugin_id com.ipmacaddress --plugin_version 1.0.0

// 2. 切换目录到刚创建得插件文件夹 cordovapluginipmacaddress 中
cd ipmacaddress

// 3. 为该插件添加android平台
plugman platform add --platform_name android

// 4. 初始化npm配置文件 ( 一直按enter回车键 )
npm init

// 5.修改src/android/cordovaipmacaddress.java文件内容，完成自定义插件(默认不修改也可以运行)
```

## 创建一个cordova新项目

> 创建得新项目名称为： cordovaplugin
> 在我本地得地址为： D:\mac-address-cordova-plugin\cordovaproject

```json
// 1. 创建一个全新cordova项目,用于测试刚创建得插件
cordova  create  cordovaproject com.ifredom  （创建cordova工程  <工程文件名> <包名>）

// 2. 切换目录到刚创建得项目 cordovaplugin 中
cd cordovaproject

// 3. 为测试项目 cordovaplugin ，添加平台
cordova platforms add android

// 4. 将新创建得插件，添加到测试项目test中 (使用本机绝对地址)
// （发布之后可以使用另一种加载方式: 例如：cordova plugin add ipmacaddress
cordova plugin add D:\mac-address-cordova-plugin\ipmacaddress

// 5. 运行cordova项目
cordova run android
```

> ipmacaddress.java

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
public class IpMacAddress extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            Toast.makeText(cordova.getActivity(), "你成功了", Toast.LENGTH_SHORT).show();
            callbackContext.success("success");
            return true;
//            String message = args.getString(0);
//            this.coolMethod(message, callbackContext);
//            return true;
        }
        callbackContext.error("error");
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
