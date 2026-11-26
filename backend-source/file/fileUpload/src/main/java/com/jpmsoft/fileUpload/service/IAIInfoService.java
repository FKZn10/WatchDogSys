package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.AIInfo;

public interface IAIInfoService extends IService<AIInfo> {


    AIInfo selectByType(String type);
}
