package com.jpmsoft.gmgws.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BeanConfig {

    /**
     * mybatis在进行mapper注入时，会出现3种情况
     * <p>
     * 该sql的databaseId和当前数据源的databaseId一样。
     * 该sql的databaseId和当前数据源的databaseId不一样。
     * 或者该sql没有配置databaseId。
     * <p>
     * 当同一方法被找到多个sql时，会优先使用databaseId相同的sql。
     * 如果没有databaseId相同的sql，再使用没有配置databaseId的sql。
     * databaseId不对应的sql是不会使用的。
     *
     * @return
     */
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("DM DBMS", "dm");
        p.setProperty("MySQL", "mysql");
        p.setProperty("PostgreSQL","postgre");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }
}
