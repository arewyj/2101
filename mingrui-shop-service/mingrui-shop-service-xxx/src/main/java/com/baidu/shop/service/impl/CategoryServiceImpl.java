package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/20
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        //    System.out.println(1/0);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override    // 删除
    public Result<JsonObject> delCategory(Integer id) {

        // 效验id是否合法
        if(ObjectUtil.isNull(id) || id <=0 ) return this.setResultError("id不合法");

        //通过id查询当前节点信息
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isNull(categoryEntity)) return this.setResultError(HTTPStatus.OPERATION_ERROR,"数据不存在");

        // 当前节点是否为父节点
        if(categoryEntity.getIsParent() == 1 ) return this.setResultError(HTTPStatus.OPERATION_ERROR,"当前节点为父节点，不能删除");


        Example example = new Example(CategoryEntity.class);
        example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());

        List<CategoryEntity> list = categoryMapper.selectByExample(example);
        //如果size <= 1 --> 如果当前节点被删除的话 当前节点的父节点下没有节点了 --> 将当前节点的父节点状态改为叶子节点
        if (list.size() <= 1){
            CategoryEntity updateCategoryEntity = new CategoryEntity();
            updateCategoryEntity.setIsParent(0);
            updateCategoryEntity.setId(categoryEntity.getParentId());

            categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);
        }

        categoryMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    @Transactional
    @Override  // 修改
    public Result<JsonObject> editCategory(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        List<CategoryEntity> list = categoryMapper.getCategoryByBrandId(brandId);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override   // 添加
    public Result<JsonObject> saveCategory(CategoryEntity categoryEntity) {
        CategoryEntity parentCategoryEntity  = new CategoryEntity();
        parentCategoryEntity.setId(categoryEntity.getParentId());
        parentCategoryEntity.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(parentCategoryEntity);

        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

}
