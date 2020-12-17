package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** mybatisplus自定义填充公共字段 ,即没有传的字段自动填充*/
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作 自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("**********插入操作 满足填充条件**********");
        LocalDateTime nowTime = LocalDateTime.now();
        setFieldValByName("createTime",nowTime, metaObject);
        setFieldValByName("updateTime", nowTime, metaObject);
        setFieldValByName("isDelete",false,metaObject);
    }

    /**
     * 修改操作 自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("**********插入操作 满足填充条件**********");
        LocalDateTime nowTime = LocalDateTime.now();
        setFieldValByName("updateTime", nowTime, metaObject);
    }

}
