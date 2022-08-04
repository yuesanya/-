package com.chinasofti.ordersys.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/alipay")
public class AlipayController {


    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public final String APP_ID = "2021000121634237";

    // 商户私钥，您的PKCS8格式RSA2私钥，在本地计算机上存储
    public final String APP_PRIVATE_KEY = "\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNOy8izcpnCw5nAbYYpbspq53iYIe7r8eaA+9YRDXAVm6pwd++met/2wGjmkWd0gkO0pmcZY3PSvsifDSDyv5QiietmXf8MHVZnadZf6H9lM3qXynats3Vgp4ZaypYDp+YbxtH9k9hhJyKcOzGCliH+vWa4cJJqWJ78ypalD/BdqsZ9AkSTEveNVIaAmFXHOFUCfd4qFmAp7BlDLeH7YJduHHZML1NEEfT4qnybf29rnekHL+JtBcBZx2k3i29vsDMYKFdcCVJ38ip6K+tYmPui8oNh90O0sA9csQI1Hrw4/YaBpdhGuDSDev9W6RXtWxTl/bLayyeWRaz9ipRMArFAgMBAAECggEAW5SSNy5c3j60IyVf8FGfsSDA66eOvKz6cKu2i3UHqeYiMmAud0kWBb19LZp/JByrlPoJc/oCX/YOLUl/QCrkiFNd8VtAC79ciTUK7KUv5OCWOjFlUZcSmC3v2Sm9toYdBVqexSY6SVEVtUwOrUjOKbcZhkp4kw2MeDWxNDVHtmoEENAPDSsXScNWljELIvEIW8WSm2szAAuh5irFhw2+8fLQ6b41Ri0woYIVeISASi0DekM59AOxze5iCLb8LebcT/xAjnRVEQ7lozzCar/pXvH8xThjOYbqQuyM+lrDIvwFHzDrDXn5WTq6fnGhUwIivD9y5xtezs/RX05cc/T2IQKBgQDgE8qbXaxnHhWBZ7xYbLV9UWA/lZ0jF4Qv2BojX5ZR8JotHeCCoBxYwPmP1KpDp2YdIthaMgvaC0R7255jIlX+5XprXWOBBJrjdLP+wUrzsFZGabUko8RtgncMgghuAEjl2kquGtX4OWMti5/DfQhR3JkhCYng4uSjubSiXKU7WQKBgQChWfRMTrFAeNtsU/0mJmU57BXpitqxbjBgLEuSjNxUo1jpmp1ZByoxW3VxNPywybp3pzhJUtP9v5NAATShMvc0YEdOy+szFxVsIa4HFffWB9c3ib6V7PAGIXnY2Ho5Ze7vMbpkSAEAaGcPeLDZkUDic6g1qVAPDUZEtPIqu42ZTQKBgGO6a0IavtBmI0wkzz7LozrTLPMfs4Aax0pD894qfMybOQM7eOCqQfN+b5QEIoNZzajKCWCbRYsV2LUHWcblUUDRAVsnhVG2qvjwx+tqO+GZacyM34IfIR1cI3/9n6LVnZwvO22ho/mGEQ4ERmbLDxjgBYcep67fTMNRs4NPmHqBAoGAdm012AQyKSYLtzj2J1grGBZmBuI5nd1IeG5Kkaf918mE6wDm2H9fwpZkXtELXx7RVB1SPM8kdax1AZdg76getTytWfJmETuyJ4mY1+6R13At0L+zOQuNKJLNghFxtEtS0hFnw07uHUSD0GE/PJFWVF+baLd64XFWz/sXe7HpPkkCgYBezDN8wbyZgoQwDmkFOvfXCMaySLWc+rjCe9H3lkYcmbKn6zFR4fTcB4EGA4Ro0OiwIuhHnauSFFsOgkGo5W04Gw2bs0eORKEELZrPt/Phq5jrhASKeutVDMtoIhHD92KjydvjTCj8Uuem5ClmFLdAR0RSgGwmCZOlqI26TjZVng==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjzyStIfSJsreMvofsQaI76rmJTuzhTjhCBX+meR/KbDjrsYyAtC6q0tBueSv4YYbKz8yiLIY2+EBU9baledhDgkTo4nAnZUvoCJYQ9wWc7vgfIm5joWg2dVRE3i96gSypgcIE1UgpyCmiJ3vFZyoEyv9bqHQcnw1pTp2MnhHnDK82VS3JanHOFqw+IGLr+IRxAzIrGCjUtGN5l7eaUYrcrO+zi8exhtStPlfeuRqak0Z800gFfbbPA1vdEunEergLkc81bscMoPyAYPGzuNGr+RWdhSLpZ/73rtDTGIHSRWW52USktcaGZh10lYn20RHPCK4EOo0Qx9EZjV9TNarZQIDAQAB";


    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL = "http://efayv2.natappfree/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String RETURN_URL = "http://efayv2.natappfree/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public final String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public final String CHARSET = "utf-8";

    // 支付宝网关
    public final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    //
    private final String FORMAT = "JSON";
    // 支付宝网关
    public final String log_path = "C:\\";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    @RequestMapping("/alipay")
    public void alipay(HttpServletResponse httpResponse) throws IOException {

        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String outTradeNo = UUID.randomUUID().toString();
        //付款金额，必填
        String totalAmount = Integer.toString(14999);
        //订单名称，必填
        String subject = "MacBook Pro";
        //商品描述，可空
        String body = "VIP有很多特权哦";
        request.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


    @RequestMapping(value = "/alipayReturnNotice", method = RequestMethod.GET)
    @ResponseBody
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        /*System.out.println("=================================同步回调=====================================");*/

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        //查看参数都有哪些
        /*System.out.println(params);*/
        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
        //验证签名通过
        if (signVerified) {
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + outTradeNo);
            System.out.println("支付宝交易号=" + tradeNo);
            System.out.println("付款金额=" + totalAmount);
            //跳转付款成功页面
            return "ok";
        } else {
            //跳转付款失败页面
            return "no";
        }
    }


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
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
