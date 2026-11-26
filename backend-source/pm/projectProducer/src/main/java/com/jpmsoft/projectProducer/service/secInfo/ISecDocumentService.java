package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDocumentRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.Date;
import java.util.List;

public interface ISecDocumentService extends IService<SecDocument> {


    BaseResponse createOrUpdateSecDocument(SecDocumentRequest request);

    BaseResponse deleteSecDocumentBydId(String dId);

    BaseResponse getSecDocumentListClassInfo(SecDocumentRequest request, LogUserInfoVo user);

    BaseResponse getSecDocumentListClass(SecDocumentRequest request, LogUserInfoVo user);

    BaseResponse getSecDocumentList(SecDocumentRequest request);

    /**
     * 城建需求 ：针对开标日历中的【开标日期】有个新的需求:「先取文件办理节点的开标日期，待该项目的评标专家抽取需求审核完毕后再取专家抽取界面的【开标日期】
     * @param request
     * @return
     */
    BaseResponse getSecDocumentListNJCJ(SecDocumentRequest request);

    BaseResponse getSecDocumentBydId(String dId);

    BaseResponse getSecDocumentBySecId(String secId);

    BaseResponse updateSecDocumentById(SecDocumentRequest request);


    /**
     * 查询招标文件 默认返回查询结果集第一个结果
     *
     * @param secId 标段ID
     * @param state 审核状态
     * @return
     */
    SecDocument getSecDocumentBySecIdAndState(String secId, Integer state);


    /**
     * 以项目为主体分组查询招标文件
     * 分页
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getSecDocumentGroupByProject(SecDocumentRequest request, String uId);


    /**
     * 分页查询招标文件
     * 默认数据为一个项目对应多个标段
     * 不存在一个标段同属于多个项目
     * 项目信息转单字段(原始分页为子集合)
     * 默认只能根据项目排序
     *
     * @param request
     * @return
     */
    BaseResponse getSecDocumentListOne2Many(SecDocumentRequest request);

    /**
     * 判断当前招标业务办理是否可办理招标文件
     * 城建定制需求
     *
     * @param secId
     * @return
     */
    BaseResponse can2CreateSecDocument(String secId);

    Date getBidTime(String secId);


    BaseResponse getpBusinessUnit(List<String> pIds);

    /**
     * 获取招标文件的费率，根据审批时间取最新
     *
     * @param secId
     * @return
     */
    BaseResponse getDocumentRate(String secId);
}
