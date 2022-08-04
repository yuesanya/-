package com.chinasofti.ordersys.controller.admin;

import com.chinasofti.ordersys.service.login.waiters.OrderService;
import com.chinasofti.ordersys.vo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GetOperateDateController {
	@Autowired
	OrderService service;

	public OrderService getService() {
		return service;
	}

	public void setService(OrderService service) {
		this.service = service;
	}

	@RequestMapping("/getoperatedate")
	public void getOperateDate(HttpServletRequest request,
			HttpServletResponse response) {
		// 设置返回的MIME类型为xml
		response.setContentType("text/xml");
		// 尝试创建运行数据结果XML
		try {

			// 创建日期格式化工具
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// 获取开始时间
			Date begin = sdf.parse(request.getParameter("bt"));
			// 获取结束时间
			Date end = sdf.parse(request.getParameter("et"));
			// 查询结单时间在开始时间与结束时间之间的所有订单信息
			List<OrderInfo> list = service.getOrderInfoBetweenDate(begin,
					end);
			// 创建XML DOM树
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// 创建XML根节点
			Element root = doc.createElement("orders");
			// 将根节点加入DOM树
			doc.appendChild(root);
			// 循环遍历结果集合中的订单信息
			for (OrderInfo info : list) {
				// 获取每个订单的总价
				float sumPrice = service.getSumPriceByOrderId(new Integer(info
						.getOrderId()));
				// // 每一个订单构建一个订单标签节点
				Element order = doc.createElement("order");
				// 创建订单id标签
				Element orderId = doc.createElement("orderId");
				// 设置订单id标签文本内容
				orderId.setTextContent(info.getOrderId() + "");
				// 将订单id标签设置为订单标签的子标签
				order.appendChild(orderId);
				// 创建桌号标签
				Element tableId = doc.createElement("tableId");
				// 设置桌号标签文本内容
				tableId.setTextContent(info.getTableId() + "");
				// 将桌号标签设置为订单标签子标签
				order.appendChild(tableId);
				// 创建总价标签
				Element sumPriceElement = doc.createElement("sumPrice");
				// 设置总价标签文本内容
				sumPriceElement.setTextContent(sumPrice + "");
				// 将总价标签设置为订单标签子标签
				order.appendChild(sumPriceElement);
				// 创建点餐服务员用户名标签
				Element userAccount = doc.createElement("userAccount");
				// 设置点餐服务员用户名标签文本内容
				userAccount.setTextContent(info.getUserAccount());
				// 将点餐服务员用户名标签设置为订单标签子标签
				order.appendChild(userAccount);
				// 创建订单结单时间标签
				Element orderEndDate = doc.createElement("orderEndDate");
				// 创建时间、日期格式化工具
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 设置结单时间标签内容为格式化后的时间字符串
				orderEndDate.setTextContent(sdf.format(info.getOrderEndDate()));
				// 将结单时间标签设置为订单标签子标签
				order.appendChild(orderEndDate);
				// 将订单标签设置为根标签子标签
				root.appendChild(order);

			}
			// 将完整的DOM树转换为XML文档结构字符串输出到客户端
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(doc),
							new StreamResult(response.getOutputStream()));

			// 捕获查询、转换过程中的异常信息
		} catch (Exception ex) {
			// 输出异常信息
			ex.printStackTrace();
		}

	}
}
