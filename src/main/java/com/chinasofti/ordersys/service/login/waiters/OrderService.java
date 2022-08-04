/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service.login.waiters;

import com.chinasofti.ordersys.mapper.OrderMapper;
import com.chinasofti.ordersys.vo.Cart;
import com.chinasofti.ordersys.vo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Title: OrderService
 * </p>
 * <p>
 * Description: ��������������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
@Service
public class OrderService {
	@Autowired
	OrderMapper mapper;

	public OrderMapper getMapper() {
		return mapper;
	}

	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * ���Ӷ����ķŷ�
	 * 
	 * @param waiterId
	 *            ������͵ķ���Աid
	 * @param tableId
	 *            ������Ӧ������
	 * @return ��ӳɹ��Ķ�����Ӧ������ֵ(Long��)
	 * */
	public Object addOrder(int waiterId, int tableId) {

		OrderInfo info = new OrderInfo();
		info.setWaiterId(waiterId);
		info.setTableId(tableId);
		// ��ȡ�������ӳص����ݿ�ģ��������߶���

		// ��ȡ��Ӷ���ʱ��ʱ��
		Date now = new Date();
		info.setOrderBeginDate(new java.sql.Date(now.getTime()));
		mapper.addOrder(info);
		// ���ڶ�����ֻ�е�����������˽���һ�����ɵ�����ֵ����
		return new Long(info.getOrderId());
	}

	/**
	 * ��Ӷ�����Ʒ��ϸ��Ϣ�ķ���
	 * 
	 * @param unit
	 *            ������Ʒ����
	 * @param key
	 *            ��Ӧ�Ķ���Id
	 * */
	public void addOrderDishesMap(Cart.CartUnit unit, int key) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		mapper.addOrderDishesMap(unit, key);

	}

	/**
	 * �Է�ҳ��ʽ��ȡ��֧ͬ��״̬������Ϣ�ķ���
	 * 
	 * @param page
	 *            ��Ҫ��ȡ��ҳ����
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @param state
	 *            ��Ҫ��ѯ��֧��״̬��Ϣ
	 * @return ��ѯ����б�
	 * */
	public List<OrderInfo> getNeedPayOrdersByPage(int page, int pageSize,
			int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���

		// ArrayList<OrderInfo> list = helper
		// .preparedForPageList(
		// "select orderId,orderBeginDate,orderEndDate,waiterId,orderState,dishes,num from orderinfo,orderdishes where orderinfo.orderId=orderdishes.orderReference and orderinfo.orderState=0",
		// new Object[] {}, page, pageSize, OrderInfo.class);

		int first = (page - 1) * pageSize;
		// ���в�ѯ����
		List<OrderInfo> list = mapper.getNeedPayOrdersByPage(first, pageSize,
				state);
		// ���ز�ѯ�Ľ��
		return list;

	}

	/**
	 * ��ȡ�ض�֧��״̬��������ҳ��
	 * 
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @param state
	 *            ����֧��״̬
	 * @return ��ҳ��
	 * */
	public int getMaxPage(int pageSize, int state) {

		// ��ѯ��������������Ŀ��
		Long rows = mapper.getMaxPage(state);
		// ������ҳ��������
		return (int) ((rows.longValue() - 1) / pageSize + 1);
	}

	/**
	 * ��ȡ��֧ͬ��״̬������Ϣ�ķ���
	 * 
	 * @param state
	 *            ��Ҫ��ѯ��֧��״̬��Ϣ
	 * @return ������Ϣ����
	 */
	public List<OrderInfo> getNeedPayOrders(int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���

		// ���ز�ѯ���
		return mapper.getNeedPayOrders(state);

	}

	/**
	 * ����֧�������ķ���
	 * 
	 * @param orderId
	 *            ����֧�������Ķ�����
	 */
	public void requestPay(Integer orderId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
		mapper.requestPay(orderId, now);

	}

	/**
	 * ���ݶ����Ż�ȡ��������ķ���
	 * 
	 * @param orderId
	 *            ��Ҫ��ȡ����Ķ�����
	 * @return ��ѯ���Ķ�����ϸ��Ϣ
	 * */
	public OrderInfo getOrderById(Integer orderId) {

		// ִ�в�ѯ�����ؽ��
		return mapper.getOrderById(orderId);

	}

	/**
	 * ��ȡ��һ�������ܼ�
	 * 
	 * @param orderId
	 * @return ��ѯ�����ܼ�
	 * */
	public float getSumPriceByOrderId(Integer orderId) {

		// ��ѯ�ܼ�
		Double sum = mapper.getSumPriceByOrderId(orderId);
		System.out.println(orderId + "-------------------------" + sum);
		// �����ܼ�
		return sum.floatValue();
	}

	/**
	 * ���ݶ����Ż�ȡ��������
	 * 
	 * @param orderId
	 * @return ���������б�
	 * */
	public List<OrderInfo> getOrderDetailById(Integer orderId) {

		// ��ѯ�����ض��������б�
		return mapper.getOrderDetailById(orderId);

	}

	/**
	 * �޸Ķ���֧��״̬�ķ���
	 * 
	 * @param orderId
	 *            Ҫ�޸�״̬�Ķ�����
	 * @param state
	 *            Ŀ��״ֵ̬
	 * */
	public void changeState(Integer orderId, int state) {
		mapper.changeState(orderId, state);

	}

	/**
	 * ���ݽᵥʱ��β�ѯ������Ϣ�ķ���
	 * 
	 * @param beginDate
	 *            ��ѯ�Ŀ�ʼʱ��
	 * @param endDate
	 *            ��ѯ�Ľ���ʱ��
	 * @return �ᵥʱ���ڿ�ʼʱ��ͽ���ʱ��֮������ж����б�
	 * */
	public List<OrderInfo> getOrderInfoBetweenDate(Date beginDate, Date endDate) {

		return mapper.getOrderInfoBetweenDate(
				new java.sql.Date(beginDate.getTime()), new java.sql.Date(
						endDate.getTime()));
	}

}
