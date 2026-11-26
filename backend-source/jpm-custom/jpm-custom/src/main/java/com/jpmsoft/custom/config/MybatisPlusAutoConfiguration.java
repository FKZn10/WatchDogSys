package com.jpmsoft.custom.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        //region 动态表名
//        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
//        dynamicTableNameInnerInterceptor.setTableNameHandler(new BpmTableNameHandler());
//        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
//        //endregion
        return interceptor;
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    public CommonMetaObjectHandler commonMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }

    /**
     * id生成器
     *
     * @return
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator();
    }
}
