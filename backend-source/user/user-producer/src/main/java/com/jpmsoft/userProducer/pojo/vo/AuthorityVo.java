package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 仅角色鉴权
 */
@Data
public class AuthorityVo implements Serializable {

    private static final long serialVersionUID = -8529503229782326591L;
    private List<String> role;
}
