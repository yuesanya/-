package com.chinasofti.ordersys.controller.kitchen;

import com.chinasofti.util.web.serverpush.MessageHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class GetPushMsgHandler {
	public abstract MessageHandler getHandler(final HttpServletRequest request,
			final HttpServletResponse response);

	public void initService(final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session) {

	}
}
