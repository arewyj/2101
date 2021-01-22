package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.service.BrandService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName BrandServiceImpl
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/22
 * @Version V1.0
 **/
@RestController
public class BrandServiceImpl extends BaseApiService implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO) {

        // 用于分页
        PageHelper.startPage(brandDTO.getPage(),brandDTO.getRows());

        // 排序
        if (!StringUtils.isEmpty(brandDTO.getSort())) PageHelper.orderBy(brandDTO.getOrderBy());


        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);

        Example example = new Example(BrandEntity.class);
        example.createCriteria().andLike("name", "%" + brandEntity.getName() + "%");

        List<BrandEntity> brandEntities = brandMapper.selectByExample(example);
        PageInfo<BrandEntity> pageInfo = new PageInfo<>(brandEntities);

        return this.setResultSuccess(pageInfo);

    }


    @Transactional
    @Override
    public Result<JsonObject> save(BrandDTO brandDTO) {
        try {
            BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);

            brandMapper.insertSelective(brandEntity);

            if(StringUtils.isEmpty(brandDTO.getCategories())){
                return this.setResultError("分类数据不能为空");
            }
            if(brandDTO.getCategories().contains(",")){
                List<CategoryBrandEntity> list = new ArrayList<>();
                String[] categoryArr = brandDTO.getCategories().split(",");
                Arrays.asList(categoryArr).stream().forEach( arr ->{

                    CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();

                    categoryBrandEntity.setBrandId(brandEntity.getId());

                    categoryBrandEntity.setCategoryId(Integer.parseInt(arr));

                    list.add(categoryBrandEntity);
                });

                categoryBrandMapper.insertList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.setResultSuccess();
    }


}
