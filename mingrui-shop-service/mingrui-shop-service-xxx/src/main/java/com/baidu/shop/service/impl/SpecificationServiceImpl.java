package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecificationService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SpecificationServiceImpl
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/25
 * @Version V1.0
 **/
@RestController
public class SpecificationServiceImpl extends BaseApiService implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;

    @Override
    public Result<List<SpecGroupEntity>> getSpecGroupInfo(SpecGroupDTO specGroupDTO) {
        Example example = new Example(SpecGroupEntity.class);
        if(ObjectUtil.isNotNull(specGroupDTO.getCid())){
            example.createCriteria().andEqualTo("cid",
                    BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class).getCid());
        }

        List<SpecGroupEntity> list = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override
    public Result<JSONObject> save(SpecGroupDTO specGroupDTO) {
        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> editSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    /*
     * 删除规格组之前 先判断一下当前规格组下是否有规格参数
     * true ：有  不能删除
     * false : 可以删除
     */
    @Transactional
    @Override
    public Result<JSONObject> deleteSpecGroupInfo(Integer id) {

        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",id);
        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);
        if(specParamEntities.size() != 0)
            return this.setResultError(HTTPStatus.OPERATION_ERROR,"绑定有规格组， 不能被删除");

        specGroupMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }




    @Override
    public Result<List<SpecParamEntity>> getSepcGroupInfo(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        Example example = new Example(SpecParamEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(specParamEntity.getGroupId()))
            criteria .andEqualTo("groupId",specParamEntity.getGroupId());

        if(ObjectUtil.isNotNull(specParamEntity.getCid()))
            criteria.andEqualTo("cid",specParamEntity.getCid());

       // example.createCriteria().andEqualTo("groupId",specParamEntity.getGroupId());
        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);
        return this.setResultSuccess(specParamEntities);
    }

    @Transactional
    @Override
    public Result<JSONObject> save(SpecParamDTO specParamDTO) {

        specParamMapper.insertSelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));
        return this.setResultSuccess();
    }


    @Transactional
    @Override
    public Result<JSONObject> editSpecParam(SpecParamDTO specParamDTO) {
        specParamMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> deleteSpecParamInfo(Integer id) {
        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

}
