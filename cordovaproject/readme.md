# cordova 项目

> 测试 wlanmac 插件

1. 修改插件

2. 添加插件到测试项目 cordova plugin add D:\mac-address-cordova-plugin\wlanmac

3. 运行测试项目（在 genymotion 设备中） `cordova run android`


## qi

广告V5 一旦添加插件，无法运行

## 插件测试

```json

cordova plugin add D:\mac-address-cordova-plugin\wlanmac

cordova plugin remove com.ifredom.wlanmac

cordova run android

```

### 环境

```json
>> node -v
v12.14.1

>> cordova --version
9.0.0 (cordova-lib@9.0.1)

>> cordova platform version android
Installed platforms:
  android 8.1.0
Available platforms:
  browser ^6.0.0
  electron ^1.0.0
  ios ^5.0.0
  osx ^5.0.0
  windows ^7.0.0
```