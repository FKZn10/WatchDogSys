package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.PreProject;
import com.jpmsoft.gmgws.pojo.entity.RepaymentApplication;

import java.util.List;

public interface RepaymentApplicationService  extends IService<RepaymentApplication> {
    List<RepaymentApplication> paymentHistory(List<String> cIds);
}
