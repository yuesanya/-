package com.chinasofti.ordersys.controller.youxiang;



public class htmlText {
		//  返回页面Html携带的6位随机码
		public static String html(String code) {
			
			String html = "Email地址验证<br/>"+ 
			"这封邮件是由【月三】发送的。<br/>"+
			"你收到这封邮件是【月三】进行新用户注册或者用户修改Email使用这个地址。<br/>"+
			"账号激活声明<br/>"+
			"请将下面的验证码输入到提示框即可：<h3 style='color:red;'>" + code + "</h3><br/>";
			return html;
		}
	}
