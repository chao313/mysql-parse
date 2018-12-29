// var host = "http://www.chaohaiwang.cn:16000/docker";

var host = "/juan";



/**
 * 把json转为url参数
 * @param json
 * @returns {string}
 */
function url_deal(json) {
    var str = '';
    $.each(json, function (key, value) {
        str += key + "=" + value + "&";
    })
    return str;

}

function queryUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); // 匹配目标参数
    var result = window.location.search.substr(1).match(reg); // 对querystring匹配目标参数
    if (result != null) {
        return decodeURIComponent(result[2]);
    } else {
        return null;
    }
}