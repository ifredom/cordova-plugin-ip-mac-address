document.addEventListener("deviceready", onDeviceReady, false);

// genymotion 5.1版本设备 无法运行cordova
function onDeviceReady() {
  // console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
  var clickButton = document.getElementById("test-button");
  clickButton.addEventListener(
    "click",
    function (params) {
      // WlanmacPlugin.coolMethod(success, error, "Helloworld");
      WlanmacPlugin.getMACAddress(success, error);
      // cordova.plugins.wlanmac.coolMethod('Helloworld', success, error);
    },
    false
  );
}

function success(params) {
  console.log("success callback");
  alert(`success callback: ${params}`);
}
function error(params) {
  console.log("error callback");
  alert("error callback");
}
