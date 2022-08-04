/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * <p>
 * Title: GetImgUploadStateServlet
 * </p>
 * <p>
 * Description: 上传需要预览图像时，用于向客户端推送上传完成信息的Servlet
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
public class GetImgUploadStateServlet extends BaseGetPushMsgServlet {

	@Override
	public MessageHandler getHandler(final HttpServletRequest request,
			final HttpServletResponse response) {
		// TODO Auto-generated method stub
		MessageHandler handler = new MessageHandler() {
			// 实现当获取消息后对消息进行实际处理的回调方法、
			// 回调方法的messageQueue参数描述了当前系统使用的消息等待序列
			// 回调方法的key参数保存了当前获取到的消息发送的目标sessionid和消息名称
			// 回调方法的msg参数保存了实际的消息数据
			@Override
			public void handle(Hashtable<ServerPushKey, Message> messageQueue,
					ServerPushKey key, Message msg) {

				try {
					// 获取针对客户端的字符输出流
					PrintWriter pw = response.getWriter();
					// 将消息字符串直接发送给客户端浏览器
					pw.print(msg.getMsg());
				} catch (Exception ex) {
					// 如果遇到异常则输出异常信息
					ex.printStackTrace();
				}
			}
		};
		return handler;
	}

}
