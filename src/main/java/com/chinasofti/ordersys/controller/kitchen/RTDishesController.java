package com.chinasofti.ordersys.controller.kitchen;

import com.chinasofti.util.web.serverpush.Message;
import com.chinasofti.util.web.serverpush.MessageHandler;
import com.chinasofti.util.web.serverpush.MessageProducer;
import com.chinasofti.util.web.serverpush.ServerPushKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;

@Controller
public class RTDishesController {

	@Autowired
	GetPushMsgTemplate template;

	public GetPushMsgTemplate getTemplate() {
		return template;
	}

	public void setTemplate(GetPushMsgTemplate template) {
		this.template = template;
	}

	public static ArrayList<String> disheses = new ArrayList<String>();
	public static ArrayList<String> kitchens = new ArrayList<String>();

	@RequestMapping("/dishesdone")
	public void dishesDone(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置响应结果集
		response.setCharacterEncoding("utf-8");
		// 获取菜品对应的桌号
		String tableId = request.getParameter("tableId");
		// 获取菜品名
		String dishesName = request.getParameter("dishesName");
		// 由于使用ajax提交，因此需要转码
		try {
			dishesName = new String(dishesName.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建消息生产者
		MessageProducer producer = new MessageProducer();
		// 获取服务员等待列表
		ArrayList<String> list = disheses;
		// 遍历服务员等待列表
		for (int i = list.size() - 1; i >= 0; i--) {
			// 获取特定的服务员SessionID
			String id = list.get(i);
			// 对该服务员生产菜品完成等待传菜的消息
			producer.sendMessage(id, "rtdishes", "桌号[" + tableId + "]的菜品["
					+ dishesName + "]已经烹制完成，请传菜！");
			// 从等待列表中删除该服务员
			list.remove(id);
		}

	}

	@RequestMapping("/getrtdishes")
	public void getRTDishes(HttpServletRequest request,
			HttpServletResponse response) {

		GetPushMsgHandler handler = new GetPushMsgHandler() {

			@Override
			public MessageHandler getHandler(HttpServletRequest request,
					HttpServletResponse response) {
				// TODO Auto-generated method stub
				// 设置请求字符集
				response.setCharacterEncoding("utf-8");
				// TODO Auto-generated method stub
				// 尝试处理实时消息
				try {
					// 获取针对客户端的文本输出流
					final PrintWriter out = response.getWriter();
					// 创建消息处理器
					MessageHandler handler = new MessageHandler() {
						// 实时消息处理回调方法
						@Override
						public void handle(
								Hashtable<ServerPushKey, Message> messageQueue,
								ServerPushKey key, Message msg) {
							// 将消息的文本内容直接发送给客户端
							out.print(msg.getMsg());
							// TODO Auto-generated method stub

						}
					};
					// 返回创建好的消息处理器
					return handler;
					// 捕获创建消息处理器时产生的异常
				} catch (Exception ex) {
					// 输出异常信息
					ex.printStackTrace();
					// 输出异常信息
					return null;
				}
			}

			@Override
			public void initService(HttpServletRequest request,
					HttpServletResponse response, HttpSession session) {
				// TODO Auto-generated method stub
				// 将当前会话加入到实时消息等待列表
				disheses.add(session.getId());
			}

		};

		template.getMsg(request, response, handler);
	}

	@RequestMapping("/getrtorder")
	public void getRTOrder(HttpServletRequest request,
			HttpServletResponse response) {

		GetPushMsgHandler handler = new GetPushMsgHandler() {

			/**
			 * 获取实时消息处理器的回调
			 * 
			 * @param request
			 *            请求对象
			 * @param response
			 *            响应对象
			 * @return 本Servlet使用的实时消息处理器
			 * */
			@Override
			public MessageHandler getHandler(HttpServletRequest request,
					HttpServletResponse response) {
				// TODO Auto-generated method stub
				// 设置请求字符集
				response.setCharacterEncoding("utf-8");
				// 尝试处理实时消息
				try {
					// 获取针对客户端的文本输出流
					final PrintWriter out = response.getWriter();
					// 创建消息处理器
					MessageHandler handler = new MessageHandler() {

						@Override
						public void handle(
								Hashtable<ServerPushKey, Message> messageQueue,
								ServerPushKey key, Message msg) {
							// 将消息的文本内容直接发送给客户端
							out.print(msg.getMsg());
							// TODO Auto-generated method stub

						}
					};
					// 返回创建好的消息处理器
					return handler;
					// 捕获创建消息处理器时产生的异常
				} catch (Exception ex) {
					// 输出异常信息
					ex.printStackTrace();
					// 返回空的消息处理器
					return null;
				}
			}

			/**
			 * 初始化实时消息获取服务的方法
			 * 
			 * @param request
			 *            请求信息
			 * @param response
			 *            响应对象
			 * @param session
			 *            会话跟踪对象
			 * */
			@Override
			public void initService(HttpServletRequest request,
					HttpServletResponse response, HttpSession session) {
				// TODO Auto-generated method stub
				// 将当前会话加入到实时消息等待列表
				kitchens.add(session.getId());
			}
		};

		template.getMsg(request, response, handler);

	}

}
