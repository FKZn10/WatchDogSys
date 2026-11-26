package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.RepaymentApplicationDtl;

import java.util.List;

public interface RepaymentApplicationDtlService   extends IService<RepaymentApplicationDtl> {
    List<RepaymentApplicationDtl> getDeductionAmt(List<String> ids);
}
