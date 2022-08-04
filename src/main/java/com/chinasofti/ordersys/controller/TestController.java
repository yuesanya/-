package com.chinasofti.ordersys.controller;

import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class TestController {
	@Autowired
	UserService service;

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	@RequestMapping("/test")
	public String test(HttpServletResponse response,
					   HttpServletRequest request, @RequestParam("arg1") int i, int j)
			throws IOException {
		System.out.println("TestAction");
		System.out.println(i + "        " + j);
		// response.getWriter().println(
		// "HelloSpringMVC" + request.getSession().getServletContext());

		return "pages/test.jsp";
	}

	@RequestMapping("/test1")
	public String test1() {
		List<UserInfo> list = service.getByPage(1, 5);
		for (UserInfo info : list) {
			System.out.println(info.getUserAccount() + "\t"
					+ info.getUserPass() + "\t" + info.getFaceimg());
		}

		UserInfo info = service.getUserById(new Integer(1));
		System.out.println("---" + info.getUserAccount());

		return "redirect:test.order?arg1=100&j=20";
	}

//
//	@GetMapping("/sendMs")
//	public String sendMs(HttpServletRequest request, String phoneNumber) {
//		if (phoneNumber != null && !phoneNumber.equals("")) {
//			String s = SMSUtil.sendSMS(request, phoneNumber);
//			return s;
//		} else {
//			return "error";
//		}
//
//
//	}
//
//	@GetMapping("/register")
//	public Object register(HttpServletRequest request, String Code) {
//		JSONObject json = (JSONObject)request.getSession().getAttribute("MsCode");
//		if(!json.getString("Code").equals(Code)){
//			return "验证码错误";
//		}
//		//我这里模拟了一分钟
//		if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 1){
//			return "验证码过期";
//		}
//		//将用户信息存入数据库、这里省略
//		return "success";
//	}


}
