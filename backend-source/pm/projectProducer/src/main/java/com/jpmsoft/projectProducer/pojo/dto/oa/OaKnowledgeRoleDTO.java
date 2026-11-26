package com.jpmsoft.projectProducer.pojo.dto.oa;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前操作人具备的知识库目录以及对应的权限编码
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 14:46
 */
@Data
public class OaKnowledgeRoleDTO implements Serializable {

    private String kbId;
    private List<String> powerCodes;
}
