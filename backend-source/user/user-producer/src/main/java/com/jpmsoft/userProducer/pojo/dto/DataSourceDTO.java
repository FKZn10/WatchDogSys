package com.jpmsoft.userProducer.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 9:23
 */
@Data
public class DataSourceDTO implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "连接池名称", example = "db1")
    private String poolName;
    @NotBlank
    @ApiModelProperty(value = "JDBC driver", example = "com.mysql.cj.jdbc.Driver")
    private String driverClassName;
    @NotBlank
    @ApiModelProperty(value = "JDBC url 地址", example = "jdbc:mysql://x.x.x.x:3306/x?useUnicode=true&characterEncoding=utf-8")
    private String url;
    @NotBlank
    @ApiModelProperty(value = "JDBC 用户名", example = "sa")
    private String username;
    @ApiModelProperty(value = "JDBC 密码")
    private String password;
}
