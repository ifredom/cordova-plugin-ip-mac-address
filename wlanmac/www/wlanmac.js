var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'wlanmac', 'coolMethod', [arg0]);

    // cordova.exec(successCallback, errorCallback, "AddressImpl", action, [message]);
};
exports.getMACAddress = function (arg0, success, error) {
    exec(success, error, 'wlanmac', 'getMACAddress', [arg0]);
};

// /*global cordova, module*/
// module.exports = {
//     coolMethod: function (action, message, success, error) {
//         exec(success, error, 'wlanmac', 'getMACAddress', [arg0]);
//     },
//     getMACAddress: function (action, message, success, error) {
//         exec(success, error, 'wlanmac', 'getMACAddress', [arg0]);
//     }
// };
