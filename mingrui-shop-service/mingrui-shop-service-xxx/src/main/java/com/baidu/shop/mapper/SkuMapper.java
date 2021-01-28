package com.baidu.shop.mapper;
import com.baidu.shop.entity.SkuEntity;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
import java.util.List;
/**
 * @ClassName SkuMapper
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/28
 * @Version V1.0
 **/
public interface SkuMapper extends Mapper<SkuEntity>, InsertListMapper<SkuEntity>, DeleteByIdListMapper<SkuEntity,Long> {
}
