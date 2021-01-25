package com.baidu.shop.utils;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName BaiduBeanUtil
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/22
 * @Version V1.0
 **/
public class BaiduBeanUtil<T> {
    public static <T> T copyProperties(Object source,Class<T> clazz){
       T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
