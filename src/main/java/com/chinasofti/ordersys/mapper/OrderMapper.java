package com.chinasofti.ordersys.mapper;

import com.chinasofti.ordersys.vo.Cart;
import com.chinasofti.ordersys.vo.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

	@Insert("insert into orderinfo(orderBeginDate,waiterId,tableId) values(#{info.orderBeginDate},#{info.waiterId},#{info.tableId})")
	@Options(useGeneratedKeys = true, keyProperty = "info.orderId")
	public void addOrder(@Param("info") OrderInfo info);

	@Insert("insert into orderdishes(orderReference,dishes,num) values(#{key},#{unit.dishesId},#{unit.num})")
	public void addOrderDishesMap(@Param("unit") Cart.CartUnit unit,
                                  @Param("key") int key);

	@Select("select * from orderinfo,userInfo where orderState=#{state} and userInfo.userId=orderinfo.waiterId limit #{first},#{max}")
	public List<OrderInfo> getNeedPayOrdersByPage(@Param("first") int first,
                                                  @Param("max") int max, @Param("state") int state);

	@Select("select count(*) from orderinfo where orderState=#{state}")
	public Long getMaxPage(@Param("state") int state);

	@Select("select * from orderinfo,userInfo where orderState=#{state} and userInfo.userId=orderinfo.waiterId")
	public List<OrderInfo> getNeedPayOrders(@Param("state") int state);

	@Update("update orderinfo set orderState=1,orderEndDate=#{now} where orderId=#{orderId}")
	public void requestPay(@Param("orderId") Integer orderId,
                           @Param("now") Date now);

	@Select("select * from orderinfo,userinfo where orderId=#{orderId} and orderinfo.waiterId=userinfo.userId")
	public OrderInfo getOrderById(@Param("orderId") Integer orderId);

	@Select("SELECT SUM(d.dishesPrice*od.num) FROM orderinfo a,dishesinfo d,orderdishes od WHERE a.orderId=od.orderReference AND od.dishes=d.dishesId AND a.orderId=#{orderId}")
	public Double getSumPriceByOrderId(@Param("orderId") Integer orderId);

	@Select("SELECT * FROM orderinfo o,userinfo u,orderdishes od,dishesinfo d WHERE orderId=#{orderId} AND o.waiterId=u.userId AND od.orderReference=o.orderId AND d.dishesId=od.dishes")
	public List<OrderInfo> getOrderDetailById(@Param("orderId") Integer orderId);

	@Update("update orderinfo set orderState=#{state} where orderId=#{orderId}")
	public void changeState(@Param("orderId") Integer orderId,
                            @Param("state") int state);

	@Select("select * from orderinfo,userInfo where orderState=2 and userInfo.userId=orderinfo.waiterId and orderinfo.orderEndDate between #{bd} and #{ed}")
	public List<OrderInfo> getOrderInfoBetweenDate(@Param("bd") Date beginDate,
                                                   @Param("ed") Date endDate);

}
