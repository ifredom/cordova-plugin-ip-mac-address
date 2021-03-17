# cordova插件 - wlanmac

≥ android 7.0 获取设备Mac地址

## 如何使用

```json
// 添加插件
cordova plugin add wlanmac
```

```js
// 调用
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
  WlanmacPlugin.getMACAddress('', success, error);
}

function success(params) {
    alert(`success callback: ${params}`)
}
function error(params) {
    alert("error callback")
}
```
