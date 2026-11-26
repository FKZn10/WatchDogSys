package com.jpmsoft.projectProducer.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return null;
    }
}
