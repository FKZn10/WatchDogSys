package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.fileUpload.pojo.dto.ConBoqDtlDto;
import com.jpmsoft.fileUpload.pojo.dto.ConMearDeclareDto;
import com.jpmsoft.fileUpload.pojo.dto.InvBudgetDeclareDtlDto;
import com.jpmsoft.fileUpload.pojo.dto.VueTreeDto;

import java.util.List;

public interface IApiUtilsService {

    /**
     * 根据url发起get请求
     * 参数拼接到url上
     * 返回BaseResponse
     *
     * @param url
     * @return
     */
    BaseResponse getBaseResponseByUrl(String url);


    BaseResponse getApproveList(String fileId);

    BaseResponse getGanttTasksProcessExport(GanttPlanExportRequest request);

    /**
     * 项目ID查询审批结束的概算申报清单列表
     *
     * @param pId
     * @return
     */
    List<InvBudgetDeclareDtlDto> getInvBudgetDeclareDtlListByPid(String pId);

    /**
     * 工程报告月报导入时查询使用
     *
     * @param request
     * @param uId
     * @return
     */
    List<PreProjectResponse> getAllProjectList(PreProjectRequest request, String uId);

    List<ConBoqDtlDto> getConBoqDtlByConId(String conId);

    /**
     * 审批通过的历史计量审批计量单
     * （上次审批通过的计量单的累计申报即本次的上期累计申报）
     *
     * @param conId
     * @return
     */
    List<ConMearDeclareDto> getConMearDeclareByConId(String conId);

    /**
     * 获取合同工程量清单类别树
     *
     * @return
     */
    List<VueTreeDto>  getDictionaryQDLBTree();

    String SelfSm2Decrypt(String encDataHex);



    /**
     * 签名
     *
     * @param ied    基础配置
     * @param sign   是否生成签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @return
     */
    String sign(InterfaceEncDecDTO ied, Boolean sign, Object object);

    /**
     * 签名验证
     *
     * @param ied    基础配置
     * @param verify 是否验证签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @param sign   签名密文
     * @return
     */
    Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, Object object, String sign);
}
