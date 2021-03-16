document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');

    var clickButton = document.getElementById('test-button');
    clickButton.addEventListener("click", function (params) {
        alert(cordova.platformId)
        WlanmacPlugin.coolMethod('Helloworld', success, error);
        // cordova.plugins.wlanmac.coolMethod('Helloworld', success, error);
    }, false);

}

function success(params) {
    console.log("success callback");
    alert(`success callback: ${params}`)
}
function error(params) {
    console.log("error callback");
    alert("error callback")
}