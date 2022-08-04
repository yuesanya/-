package com.chinasofti.ordersys.controller.duanxin;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/sendMs")
public class sendMessage {
    //点击发送验证码
    //点击发送验证码
    @PostMapping("/sendMs")
    @ResponseBody

    public static String sendMs(String phoneNumber){
        if(phoneNumber!=null&&!phoneNumber.equals("")){
            String s = SMSUtil.sendSMS(phoneNumber);
            System.out.println(s);
            return s;

        }else{
            return "error";
        }
    }

    //登录验证
    @PostMapping("/register")
    @ResponseBody
    public Object register(HttpServletRequest request, String Code) {
        JSONObject json = (JSONObject)request.getSession().getAttribute("MSCode");
        if(!json.getString("Code").equals(Code)){
            return "验证码错误";
        }
        //我这里模拟了一分钟
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 1){
            return "验证码过期";
        }
        //将用户信息存入数据库、这里省略
        return "success";
    }

}