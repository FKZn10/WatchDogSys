package com.jpmsoft.projectProducer.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;


public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间
     */
    private final String createTime = "createTime";
    /**
     * 修改时间
     */
    private final String updateTime = "updateTime";

    /**
     * 创建时间自动注入
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(createTime, new Date(), metaObject);
        this.setFieldValByName(updateTime, new Date(), metaObject);
    }

    /**
     * 更新时间自动注入
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(updateTime, new Date(), metaObject);
    }

}


