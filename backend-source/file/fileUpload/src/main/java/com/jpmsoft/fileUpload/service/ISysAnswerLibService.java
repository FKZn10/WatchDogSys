package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysAnswerLib;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 9:55
 */
public interface ISysAnswerLibService extends IService<SysAnswerLib> {


    BaseResponse createOrUpdateSysAnswerLib(SysAnswerLibDTO request,String token);

    BaseResponse getSysAnswerLibList(SysAnswerLibDTO request);

    BaseResponse getSysAnswerLibById(String id);

    /**
     * 检索问题
     *
     * @param requset
     * @return
     */
    BaseResponse searchProblem(SysAnswerLibDTO requset);
}
