package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.InvestmentSecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoAmountDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlan;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;

import java.util.Collection;
import java.util.List;

public interface ISecInfoService extends IService<SecInfo> {

    BaseResponse createOrUpdateSecInfo(SecInfoRequest request);

    /**
     * 招标计划批量创建标段信息
     * 暂不生成标段Sys_File信息
     *
     * @param datas
     * @param pIds
     * @param bp
     * @return
     */
    BaseResponse createOrUpdateSecInfo(List<SecInfoRequest> datas, List<String> pIds, List<SecProjectRequest> secProjectList, SecBiddingPlan bp);

    BaseResponse getSecInfoList(SecInfoRequest request, String uId);

    BaseResponse getSecInfoListNoAuth(SecInfoRequest request);

    BaseResponse deleteSecInfoById(String secId);

    BaseResponse getSecInfoByPId(SecInfoRequest request, String uId);


    /**
     * 城建定制
     * 合同：选标段过滤，需要中标通知书审批结束才能选到，
     * 若招标方式（secBiddingType）是直接委托的，也能选到
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getCjSecInfoByPId(SecInfoRequest request, String uId);

    BaseResponse getSecInfoById(String secId);


    /**
     * 根据招标计划批量生成Sys_File
     *
     * @param request
     */
    void createSecInfoSysFileByBath(SecInfoRequest request);


    BaseResponse getSecInfoListGroupByProject(SecInfoRequest request, String uId);

    BaseResponse updateSecBiddingTypeById(SecInfoRequest request);

    /**
     * 分页查询标段项目
     *
     * @param request
     * @return
     */
    BaseResponse getSecProjectList(SecProjectModel request, String uId);

    BaseResponse getSecProjectStatisticsList(SecProjectModel request, String uId);

    /**
     * 分页查询标段
     * 默认数据为一个项目对应多个标段
     * 不存在一个标段同属于多个项目
     * 项目信息转单字段(原始分页为子集合)
     * 默认只能根据项目排序
     *
     * @param request
     * @return
     */
    BaseResponse getSecInfoListOne2Many(SecInfoRequest request);


    /**
     * 批量ID查询标段信息
     *
     * @param secIds
     * @return
     */
    List<SecInfoDto> getSecInfoBySecInfoIds(Collection<String> secIds);

    BaseResponse updateApproveStatusSecInfo(SecInfoRequest request);

    /**
     * 判断标段是否可以办理（有在审核的变更时不可办理）
     *
     * @param secId
     * @return
     */
    BaseResponse secInfoCanHandle(String secId);


    /**
     * 获取项目、招采概算树
     *
     * @param planAmountDto
     * @return
     */
    BaseResponse getSecInfoAmountTree(SecInfoAmountDto planAmountDto);

    /**
     * 获取筛选字段列表
     *
     * @param projectIds
     * @param uid
     * @return
     */
    BaseResponse getSecInfoTZClassInfo(List<String> projectIds, String uid);

    /**
     * 通过概算标段名称获取标段信息
     *
     * @param request
     * @return
     */
    BaseResponse getInvestmentSecInfo(InvestmentSecInfoRequest request);


    List<SecInfo> getSecInfoByBpId(String bpId);

    Boolean existDuplicateName(String name, String secId);



    BaseResponse getSecBiddingTypeByIds(String secIds);


    BaseResponse getSecInfoGDGY(SecInfoRequest request);

    BaseResponse getConSecInfoGgById(String secId);

    BaseResponse createOrUpdateSecInfoFromOld(List<SecInfoRequest> secInfos, List<String> strings, List<SecProjectRequest> secProjectList, SecBiddingPlan sec);
}
