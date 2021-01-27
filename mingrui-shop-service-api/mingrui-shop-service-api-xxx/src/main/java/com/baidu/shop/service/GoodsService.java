package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/27
 * @Version V1.0
 **/
@Api(tags = "商品接口")
public interface GoodsService {
    @ApiOperation(value = "获取spu信息")
    @GetMapping(value = "goods/getSpuInfo")
    Result<List<SpuDTO>> getSupInfo(SpuDTO spuDTO);
}