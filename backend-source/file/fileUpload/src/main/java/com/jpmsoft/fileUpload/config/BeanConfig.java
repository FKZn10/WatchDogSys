package com.jpmsoft.fileUpload.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 用于开启xml内databaseId属性的支持
 */
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
    public DatabaseIdProvider getDatabaseIdProvider(
            @Value("${mybatis.database-id.postgresql:postgre}") String postgresqlDatabaseId) {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("DM DBMS", "dm");
        p.setProperty("MySQL", "mysql");
        p.setProperty("PostgreSQL", postgresqlDatabaseId);
        p.setProperty("KingbaseES","mysql");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }
}
