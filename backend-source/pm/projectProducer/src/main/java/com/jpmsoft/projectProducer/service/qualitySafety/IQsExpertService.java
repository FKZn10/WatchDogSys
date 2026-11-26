package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsExpert;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface IQsExpertService extends IService<QsExpert> {

    BaseResponse resetPlaintextNum();

    BaseResponse createOrUpdateQsExpert(QsExpertModel request);

    /**
     * 身份证取当前创建人
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateQsExpertCurrent(QsExpertModel request);

    /**
     * 身份证取当前创建人
     * 并验证数据唯一
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateQsExpertVerify(QsExpertModel request);


    BaseResponse getQsExpertList(QsExpertModel request);


    BaseResponse getQsExpertById(String exId);

    BaseResponse deleteQsExpertById(String exId);

    boolean expertExists(String idNum, String id, String teId);

    /**
     * 修改专家状态
     *
     * @param exId
     * @param status
     */
    void updateQsExpertStatus(String exId, Integer status);

}
