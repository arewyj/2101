package com.baidu.shop.entity;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * @ClassName CategoryEntity
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/20
 * @Version V1.0
 **/
@ApiModel(value = "分类实体类")
@Data
@Table(name="tb_category")
public class CategoryEntity {
    @Id
    @ApiModelProperty(value ="分类主键", example = "1")
    @NotNull(message = "ID不能空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value ="分类名称")
    @NotEmpty(message = "分类名称不能为空",groups = {MingruiOperation.Add.class, MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value ="父类目id,顶级类目填0", example = "1")
    private Integer parentId;

    @ApiModelProperty(value ="是否为父节点，0为否，1为是", example = "1")
    private Integer isParent;

    @ApiModelProperty(value ="排序指数，越小越靠前", example = "1")
    private Integer sort;

}
