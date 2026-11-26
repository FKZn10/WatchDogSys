package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.catalina.util.ContextName;

import java.math.BigDecimal;
import java.security.PrivateKey;

public class ConBG {
    @TableId(type = IdType.INPUT)
    private String id;
    private String code;
    private String type;
    private String pId;
    private String pname;
    private String conid;
    private String conname;
    private BigDecimal bl;
    private BigDecimal money;
    private BigDecimal truemoney;
    private BigDecimal truebl;

}
