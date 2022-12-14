package com.chinasofti.ordersys.mapper;

import com.chinasofti.ordersys.vo.DishesInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishesInfoMapper {

	@Select("select * from dishesinfo order by recommend desc,dishesId limit #{first},#{max}")
	public List<DishesInfo> getDishesInfoByPage(@Param("first") int first,
                                                @Param("max") int max);

	@Select("select count(*) from dishesinfo")
	public Long getMaxPage();

	@Delete("delete from dishesinfo where dishesId=#{dishesId}")
	public void deleteDishesById(@Param("dishesId") Integer dishesId);

	@Insert("insert into dishesinfo(dishesName,dishesDiscript,dishesTxt,dishesImg,recommend,dishesPrice) values(#{info.dishesName},#{info.dishesDiscript},#{info.dishesTxt},#{info.dishesImg},#{info.recommend},#{info.dishesPrice})")
	public void addDishes(@Param("info") DishesInfo info);

	@Select("select * from dishesinfo where dishesId=#{dishesId}")
	public DishesInfo getDishesById(@Param("dishesId") Integer dishesId);

	@Update("update dishesinfo set dishesName=#{info.dishesName},dishesDiscript=#{info.dishesDiscript},dishesTxt=#{info.dishesTxt},dishesImg=#{info.dishesImg},recommend=#{info.recommend},dishesPrice=#{info.dishesPrice} where dishesId=#{info.dishesId}")
	public void modifyDishes(@Param("info") DishesInfo info);

	@Select("select * from dishesinfo where recommend=1 order by dishesId")
	public List<DishesInfo> getTop4RecommendDishes();

}
