package com.baidu.shop.global;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.status.HTTPStatus;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GlobalException
 * @Description: TODO
 * @Author wyj
 * @Date 2021/1/21
 * @Version V1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = RuntimeException.class)
    public Result<JsonObject> testException(RuntimeException e){
        log.error("code : {}, message : {}", HTTPStatus.ERROR,e.getMessage());
        return new Result<JsonObject>(HTTPStatus.ERROR,e.getMessage(),null);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public HashMap<String, Object> methodValid(MethodArgumentNotValidException exception) throws Exception{
        //== ===区别？？
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",HTTPStatus.PARAMS_VALIDATE_ERROR);

        List<String> msgList = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().stream().forEach(error ->{
            msgList.add("Field -->" + error.getField() + ":" + error.getDefaultMessage());
            log.error("Field -->" + error.getField() + ":" + error.getDefaultMessage());
        });
        String message = msgList.parallelStream().collect(Collectors.joining(","));

        map.put("massage",message);

        return map;
    }

}
