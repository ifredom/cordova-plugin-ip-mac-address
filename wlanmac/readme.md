# wlanmac

≥ Get mac address of Android device (获取 android 设备的 Mac 地址)

## How to use? (如何使用)

```json
// add plugin (添加插件)
cordova plugin add wlanmac
```

```js
// call (调用)
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
  WlanmacPlugin.getMACAddress("", success, error);
}

function success(params) {
  alert(`success callback: ${params}`);
}
function error(params) {
  alert("error callback");
}
```

## 已知问题

gemonition android 7 可以获取
