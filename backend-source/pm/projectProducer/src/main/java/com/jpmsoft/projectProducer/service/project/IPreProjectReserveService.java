package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserve;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:22
 */
public interface IPreProjectReserveService extends IService<PreProjectReserve> {

    BaseResponse createOrUpdateProjectReserve(PreProjectReserveDTO request);

    BaseResponse getProjectReserveList(PreProjectReserveDTO request,String uId);

    BaseResponse getProjectReserveById(String id);

    BaseResponse updateReserveStatus(String id,String status,String pId);

    BaseResponse deleteProjectReserveById(String id);

    BaseResponse queryReserveListClassInfo(PreProjectReserveDTO request,String uId);

    BaseResponse syncProjectReserveToProject(String id, String token);

    BaseResponse changeReserveCodeToProjectCode(String pId, String token);

    /**
     * 在建设模式变更时刷新项目储备编码
     *
     * @param id
     * @param oldPprModel
     * @return
     */
    BaseResponse refreshProjectReserveCode(String id, String oldPprModel);
}
