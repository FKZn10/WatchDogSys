package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecAnswerQuestionRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnswerQuestion;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecAnswerQuestionService extends IService<SecAnswerQuestion> {

    BaseResponse createOrUpdateSecAnswerQuestion(SecAnswerQuestionRequest request);

    BaseResponse deleteSecAnswerQuestionByaqId(String aqId);

    BaseResponse getSecAnswerQuestionList(SecAnswerQuestionRequest request,String uid);

    BaseResponse getSecAnswerQuestionByaqId(String aqId);

    BaseResponse approveSyncDocumentBidTimeAndLocation(String aqId);

    /**
     * 查看是否有已审批结束需要修改开标时间的答疑数据
     *
     * @param secId
     * @return
     */
    Boolean havingApprovedAnswerQuestion(String secId);


    /**
     * 分页查询答疑
     * 默认数据为一个项目对应多个标段
     * 不存在一个标段同属于多个项目
     * 项目信息转单字段(原始分页为子集合)
     * 默认只能根据项目排序
     *
     * @param request
     * @return
     */
    BaseResponse getSecAnswerQuestionListOne2Many(SecAnswerQuestionRequest request,String uid);

    BaseResponse getSecAnswerQuestionListClassInfo(SecAnswerQuestionRequest request, LogUserInfoVo user);

    BaseResponse getSecAnswerQuestionListClass(SecAnswerQuestionRequest request, LogUserInfoVo user);
}
