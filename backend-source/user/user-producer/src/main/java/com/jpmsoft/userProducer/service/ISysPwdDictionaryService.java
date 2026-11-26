package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPwdDictionaryDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPwdDictionary;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 11:47
 */
public interface ISysPwdDictionaryService extends IService<SysPwdDictionary> {


    BaseResponse createOrUpdatePwdDictionary(SysPwdDictionaryDTO request);

    BaseResponse getPwdDictionaryList(SysPwdDictionaryDTO request);

    BaseResponse deletePwdDictionaryById(String id);

    boolean  pwdExist(String pwd);
}
