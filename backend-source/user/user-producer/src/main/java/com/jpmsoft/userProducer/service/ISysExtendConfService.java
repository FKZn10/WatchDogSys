package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.userProducer.pojo.entity.SysExtendConf;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:12
 */
public interface ISysExtendConfService extends IService<SysExtendConf> {

    void createOrUpdateSysExtendConf(List<SysExtendConfDTO> extendConfs, String tId);

    BaseResponse getSysExtendConfByTid(String tId);

    BaseResponse getSysExtendConfByTCode(String code,String token);

    BaseResponse getSysExtendConfByTCodes(List<String> codes);

    List<SysExtendConfDTO> getAllSysExtendConf();

    void deleteSysExtendConfByTid(String tId);
}
