var exec = require("cordova/exec");

exports.coolMethod = function (success, error, arg0 = "") {
  exec(success, error, "wlanmac", "coolMethod", [arg0]);
  //   cordova.exec(successCallback, errorCallback, "AddressImpl", action, [message]);
};
exports.getMACAddress = function (success, error, arg0 = "") {
  exec(success, error, "wlanmac", "getMACAddress", [arg0]);
};
