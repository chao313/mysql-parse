package demo.spring.boot.demospringboot.framework;

public interface Code {

    interface System {
        String OK = "0";
        String FAIL = "1";
        String SERVER_SUCCESS = "000000";
        String SERVER_SUCCESS_MSG = "服务正常!";
        String SESSION_TIMEOUT = "1000000";
        String SESSION_TIMEOUT_MSG = "登陆超时!";
        String PARAMS_INVALID = "1000001";
        String PARAMS_INVALID_MSG = "参数无效!";
        String PERMISSION_DENIED = "1000002";
        String PERMISSION_DENIED_MSG = "权限不足!";
        String CONNECTION_TIME_OUT = "1000005";
        String CONNECTION_TIME_OUT_MSG = "连接服务超时!";
        String NO_REQUEST_MATCH = "1000006";
        String NO_REQUEST_MATCH_MSG = "没有找到资源!";
        String SYSTEM_ERROR_CODE = "1000007";
        String SYSTEM_ERROR_CODE_MSG = "系统错误，请联系管理员!";
        String NO_PERMISSIONS = "1000008";
        String NO_PERMISSIONS_MSG = "没有权限!";
        String OPERATE_TIME_OUT = "1000009";
        String OPERATE_TIME_OUT_MSG = "操作超时";

    }

    interface SystemError {
        String SERVER_INTERNAL_ERROR = "500001";
        String SERVER_INTERNAL_ERROR_MSG = "( >﹏<。)～ 系统有点累，请稍候再试!";

    }


    interface AuthenticationError {

        String UNAUTHORIZED = "400001";
        String UNAUTHORIZED_MSG = "未授权资源";
        String UNKNOWN_CREDENTIAL = "400002";
        String UNKNOWN_CREDENTIAL_MSG = "未知的凭证";
        String TOKEN_EXPIRED = "400003";
        String TOKEN_EXPIRED_MSG = "Token已失效";
        String INVALID_TOKEN = "400004";
        String INVALID_TOKEN_MSG = "无效的Token";
        String INVALID_CREDENTIAL = "400005";
        String INVALID_CREDENTIAL_MSG = "用户名或密码错误";
        String TOKEN_NOT_FOUND = "400006";
        String TOKEN_NOT_FOUND_MSG = "Token未填写";
    }

    interface ResourceError {
        String CAN_NOT_ACCESS = "410001";
        String $CAN_NOT_ACCESS_MSG = "资源不可访问";
    }

    interface ApiError {
        String PARAMETER_VALIDATION_ERROR = "600001";
        String PARAMETER_VALIDATION_ERROR_MSG = "参数验证失败";
        String RPC_CALL_VALIDATION_ERROR = "600002";
        String RPC_CALL_VALIDATION_ERROR_MSG = "调用RPC接口参数验证失败";
        String NO_RESPONSE_ERROR = "600003";
        String NO_RESPONSE_ERROR_MSG = "调用RPC接口无响应";
        String NO_RESPONSE_STATUS_ERROR = "600004";
        String NO_RESPONSE_STATUS_ERROR_MSG = "调用RPC接口无响应状态码";
        String NO_RESPONSE_ERROR_ERROR = "600005";
        String NO_RESPONSE_ERROR_ERROR_MSG = "调用RPC接口无响应错误码";
        String INVALID_SIGNATURE = "600006";
        String INVALID_SIGNATURE_MSG = "签名验证失败";
        String REQUEST_TOO_BUSY = "600007";
        String REQUEST_TOO_BUSY_MSG = "请求太频繁";
        String DENIED_BY_RULE = "600008";
        String DENIED_BY_RULE_MSG = "请求被拒";
        String ERROR = "6100000";
        String ERROR_MSG = "服务异常，请稍后再试！";
        String LOGIC_ERROR = "6100001";
        String LOGIC_ERROR_MSG = "业务逻辑错误";
        String SYSTEM_PARAM_ERROR = "6100002";
        String SYSTEM_PARAM_ERROR_MSG = "系统参数异常";
        String API_ERROR = "6100003";
        String API_ERROR_MSG = "后台接口服务异常，请联系系统管理员！";
        String INVALID_PARAMETER = "6100004";
        String INVALID_PARAMETER_MSG = "参数无效！";
        String INVALID_BUCKET = "6100005";
        String INVALID_BUCKET_MSG = "BUCKET没有配置";
        String INVALID_BASE64_DECODE_FILE = "6100006";
        String INVALID_BASE64_DECODE_FILE_MSG = "文件解码出错!";
        String DATA_NOT_FUND = "6100007";
        String DATA_NOT_FUND_MSG = "没有相关信息";
        String FILE_EIXSTS = "6100008";
        String FILE_EIXSTS_MSG = "文件已经存在！";
        String INVALID_CHANNEL_API = "6100017";
        String INVALID_CHANNEL_API_MSG = "渠道接口没有配置";
    }

    interface UserErrors {

        String USER_NOT_LOGIN = "700001";
        String USER_NOT_LOGIN_MSG = "用户未登录";
        String USER_NOT_FOUND = "700004";
        String USER_NOT_FOUND_MSG = "用户不存在";
        String USER_CERT_NOT_COMPLETE = "700006";
        String USER_CERT_NOT_COMPLETE_MSG = "请先完成实名认证";
        String INVALID_CELLPHONE = "700009";
        String INVALID_CELLPHONE_MSG = "手机号码格式不正确";
        String VERIFY_CAPTCHA_FAILED = "700012";
        String VERIFY_CAPTCHA_FAILED_MSG = "验证短信验证码失败";
        String NOT_WIFI_USER = "700016";
        String NOT_WIFI_USER_MSG = "非法用户";
        String INVALID_BANKNO = "700017";
        String INVALID_BANKNO_MSG = "银行卡位数不正确";
        String REG_TIME_SHORT = "700018";
        String REG_TIME_SHORT_MSG = "请不要连续提交";


    }

}
