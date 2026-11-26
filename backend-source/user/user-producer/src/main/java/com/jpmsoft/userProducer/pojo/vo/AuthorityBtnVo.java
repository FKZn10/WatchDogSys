package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色+按钮鉴权
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/3 15:57
 */
@Data
public class AuthorityBtnVo implements Serializable {

    private static final long serialVersionUID = 1785327111381395721L;
    private String id;
    private List<String> operation;
}
