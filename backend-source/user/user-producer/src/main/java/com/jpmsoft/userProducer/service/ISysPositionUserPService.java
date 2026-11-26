package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.userProducer.pojo.dto.PositionUserPDto;
import com.jpmsoft.userProducer.pojo.dto.UserPositionPDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUserP;

import java.util.List;


public interface ISysPositionUserPService extends IService<SysPositionUserP> {

    /**
     * 城建查询项目组织机构 集团本部人员或项目管理单位人员
     * 基础配置决定查询部门
     *
     * @param pId
     * @param bbOrJt
     * @return
     */
    BaseResponse getProjectPositionUser(String pId, String bbOrJt);

    BaseResponse CreateOrDeletePP_U(PositionUserPDto request);

    BaseResponse updatePositionUserP(PositionUserPDto request);


    /**
     * 上移下移序号
     *
     * @param pupId
     * @param action
     * @return
     */
    BaseResponse updateSortUpOrDown(String pupId, String pdId, String action);


    List<SysPositionUserP> getPositionUserPByPPId(String ppId);

    BaseResponse createOrDeletePUPByBath(List<ReportedPersonnelDtlModel> datas);

    UserPositionPDTO getProjectPositionUserGlManager(String pId);
}
