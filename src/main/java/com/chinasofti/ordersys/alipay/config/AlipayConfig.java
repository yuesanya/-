package com.chinasofti.ordersys.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000121634237";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNOy8izcpnCw5nAbYYpbspq53iYIe7r8eaA+9YRDXAVm6pwd++met/2wGjmkWd0gkO0pmcZY3PSvsifDSDyv5QiietmXf8MHVZnadZf6H9lM3qXynats3Vgp4ZaypYDp+YbxtH9k9hhJyKcOzGCliH+vWa4cJJqWJ78ypalD/BdqsZ9AkSTEveNVIaAmFXHOFUCfd4qFmAp7BlDLeH7YJduHHZML1NEEfT4qnybf29rnekHL+JtBcBZx2k3i29vsDMYKFdcCVJ38ip6K+tYmPui8oNh90O0sA9csQI1Hrw4/YaBpdhGuDSDev9W6RXtWxTl/bLayyeWRaz9ipRMArFAgMBAAECggEAW5SSNy5c3j60IyVf8FGfsSDA66eOvKz6cKu2i3UHqeYiMmAud0kWBb19LZp/JByrlPoJc/oCX/YOLUl/QCrkiFNd8VtAC79ciTUK7KUv5OCWOjFlUZcSmC3v2Sm9toYdBVqexSY6SVEVtUwOrUjOKbcZhkp4kw2MeDWxNDVHtmoEENAPDSsXScNWljELIvEIW8WSm2szAAuh5irFhw2+8fLQ6b41Ri0woYIVeISASi0DekM59AOxze5iCLb8LebcT/xAjnRVEQ7lozzCar/pXvH8xThjOYbqQuyM+lrDIvwFHzDrDXn5WTq6fnGhUwIivD9y5xtezs/RX05cc/T2IQKBgQDgE8qbXaxnHhWBZ7xYbLV9UWA/lZ0jF4Qv2BojX5ZR8JotHeCCoBxYwPmP1KpDp2YdIthaMgvaC0R7255jIlX+5XprXWOBBJrjdLP+wUrzsFZGabUko8RtgncMgghuAEjl2kquGtX4OWMti5/DfQhR3JkhCYng4uSjubSiXKU7WQKBgQChWfRMTrFAeNtsU/0mJmU57BXpitqxbjBgLEuSjNxUo1jpmp1ZByoxW3VxNPywybp3pzhJUtP9v5NAATShMvc0YEdOy+szFxVsIa4HFffWB9c3ib6V7PAGIXnY2Ho5Ze7vMbpkSAEAaGcPeLDZkUDic6g1qVAPDUZEtPIqu42ZTQKBgGO6a0IavtBmI0wkzz7LozrTLPMfs4Aax0pD894qfMybOQM7eOCqQfN+b5QEIoNZzajKCWCbRYsV2LUHWcblUUDRAVsnhVG2qvjwx+tqO+GZacyM34IfIR1cI3/9n6LVnZwvO22ho/mGEQ4ERmbLDxjgBYcep67fTMNRs4NPmHqBAoGAdm012AQyKSYLtzj2J1grGBZmBuI5nd1IeG5Kkaf918mE6wDm2H9fwpZkXtELXx7RVB1SPM8kdax1AZdg76getTytWfJmETuyJ4mY1+6R13At0L+zOQuNKJLNghFxtEtS0hFnw07uHUSD0GE/PJFWVF+baLd64XFWz/sXe7HpPkkCgYBezDN8wbyZgoQwDmkFOvfXCMaySLWc+rjCe9H3lkYcmbKn6zFR4fTcB4EGA4Ro0OiwIuhHnauSFFsOgkGo5W04Gw2bs0eORKEELZrPt/Phq5jrhASKeutVDMtoIhHD92KjydvjTCj8Uuem5ClmFLdAR0RSgGwmCZOlqI26TjZVng==";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjzyStIfSJsreMvofsQaI76rmJTuzhTjhCBX+meR/KbDjrsYyAtC6q0tBueSv4YYbKz8yiLIY2+EBU9baledhDgkTo4nAnZUvoCJYQ9wWc7vgfIm5joWg2dVRE3i96gSypgcIE1UgpyCmiJ3vFZyoEyv9bqHQcnw1pTp2MnhHnDK82VS3JanHOFqw+IGLr+IRxAzIrGCjUtGN5l7eaUYrcrO+zi8exhtStPlfeuRqak0Z800gFfbbPA1vdEunEergLkc81bscMoPyAYPGzuNGr+RWdhSLpZ/73rtDTGIHSRWW52USktcaGZh10lYn20RHPCK4EOo0Qx9EZjV9TNarZQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://efayv2.natappfree/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://efayv2.natappfree/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

