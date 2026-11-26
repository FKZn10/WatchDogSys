package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysCarbonCopyRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserCarbonCopyRequest;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.dto.SysCcDto;
import com.jpmsoft.jpbpm.pojo.dto.SysCcSendDto;
import com.jpmsoft.jpbpm.pojo.entity.CarbonCopy;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CarbonCopyService extends IService<CarbonCopy> {

    void GetCarbonCopyList(FlowActivity act, String user, String entityID, String title, String processID, List<String> cc_users, String type,String customerId, String token, String pId,String tCode,String year);

    /**
     * 抄送补充意见
     * 抄送流程
     *
     * @param request
     * @return
     */
    BaseResponse Cctransaction(SysCcDto request,String customerId);

    BaseResponse CctransactionSend(SysCcSendDto request,String customerId);

    BaseResponse CctransactionRead(SysCcDto requst,String customerId);

    BaseResponse Cclist(TaskRequest request,String customerId);

    BaseResponse getUserCarbonCopyListByfileid(FilerecordRequest requst,String customerId);
    /**
     * 我的事务(PM)
     * 抄送流程
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserCarbonCopyList(String userId, UserCarbonCopyRequest request,String customerId);

    /**
     * 流程管理
     * 抄送记录
     * @param request
     * @return
     */
    BaseResponse getCarbonCopyList(SysCarbonCopyRequest request,String customerId);
    List<CarbonCopy>   GetCarbonCopyPojoByFileid(String Fileid,String customerId);


    /**
     * 获取用户待阅数量
     * @param userId
     * @return
     */
    Integer getUserCarbonCopyCount(String userId,String customerId);

    List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request,String customerId);


    List<Map> getDataToSql(String sqlStr);



    BaseResponse FreeCC(SysCcSendDto request, String customerId);

    /**
     * 根据entityId删除抄送记录
     *
     * @param request
     * @return
     */
    BaseResponse deleteCarbonCopyByEntityId(SysCcSendDto request);

    /**
     * 发送监理现场用表抄送
     *
     * @param request
     * @param uId
     * @param customerId
     * @return
     */
    BaseResponse sendSupervisionCarbonCopy(SysCcSendDto request, String uId, String customerId);
}
