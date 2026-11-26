package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.LoanApplication;

import java.util.List;

public interface LoanApplicationService   extends IService<LoanApplication> {
    List<LoanApplication> getLoan(String uid);
}
