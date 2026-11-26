package com.jpmsoft.projectProducer.pojo.request.thirdDock;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/5/22 11:17
 * @Description:
 * @Version: 1.0
 */
@Data
public class ThirdUserRequest implements Serializable {

    private static final long serialVersionUID = 4057506549955924717L;

    //申请人
    private String userId;

    //申请部门
    private String departmentId;

    //申请单位
    private String subCompanyId;


}
