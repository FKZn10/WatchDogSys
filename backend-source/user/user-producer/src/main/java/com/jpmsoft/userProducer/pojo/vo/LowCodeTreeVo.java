package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class LowCodeTreeVo implements Serializable {
    private static final long serialVersionUID = -4929335372063130273L;
    private String label;
    private String value;
    private List<LowCodeTreeVo> children;
}
