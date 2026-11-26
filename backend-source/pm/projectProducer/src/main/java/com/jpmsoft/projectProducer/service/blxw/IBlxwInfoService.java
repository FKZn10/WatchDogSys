package com.jpmsoft.projectProducer.service.blxw;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeCloseInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwNoticeInfoModel;
import com.jpmsoft.projectProducer.pojo.dto.blxw.BlxwInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoVo;

public interface IBlxwInfoService extends IService<BlxwInfo> {
    BaseResponse saveOrUpdateBlxwInfo(BlxwInfoDto dto) ;
    /***
     * 审核汇总结果
     * @param request
     * @return
     */
    BaseResponse approvalData(BlxwInfoRequest request);

    /**
     * 获取汇总信息列表
     * @param request
     * @param token
     * @return
     */
    BaseResponse getBlxwInfoList(BlxwInfoRequest request,String token);

    /***
     * 获取汇总信息详情
     * @param id
     * @return
     */
    BaseResponse getBlxwInfoDetail(String id);
    /**
     * 删除
     * @param id
     * @return
     */
    boolean canToDeleteBlxwType(String id);

    BaseResponse getBlxwInfoNoticeDetail(String id);

    BaseResponse getBlxwInfoNoticeList(BlxwInfoRequest request);

    BaseResponse getBlxwInfoPrintDetail(String id);

    BaseResponse approvalNoticeData(BlxwNoticeInfoModel request);

    void generateBlxwNoticeInfo(BlxwInfoVo entity);

    BaseResponse getBlxwInfoNoticeCloseList(BlxwInfoRequest request);

    BaseResponse getBlxwInfoNoticeCloseDetail(String id);

    BaseResponse approvalNoticeCloseData(BlxwNoticeCloseInfoModel request);

    void generateNoticeInfo();

    BaseResponse fresh(BlxwNoticeCloseInfoModel request);
}