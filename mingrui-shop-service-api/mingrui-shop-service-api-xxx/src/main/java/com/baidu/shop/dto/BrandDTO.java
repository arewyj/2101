package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * @ClassName BrandDTO
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/22
 * @Version V1.0
 **/
@ApiModel(value = "品牌DTO")
@Data
public class BrandDTO extends BaseDTO {

    @Id
    @ApiModelProperty(value = "品牌主键",example = "1")
    @NotNull(message = "品牌主键不能为空",groups = {MingruiOperation.Update.class})  // 用于Integer类型
    private Integer id;

    @ApiModelProperty(value = "品牌名称")
    @NotEmpty(message = "品牌名称不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class}) // 支持的是字符串类型字段
    private String name;

    @ApiModelProperty(value = "品牌logo")
    private String image;

    @ApiModelProperty(value = "品牌首字母")
    private Character letter;

    @ApiModelProperty(value = "品牌id集合")
    @NotEmpty(message = "品牌分类信息不能为空",groups = {MingruiOperation.Add.class})
    private String categories;
}
