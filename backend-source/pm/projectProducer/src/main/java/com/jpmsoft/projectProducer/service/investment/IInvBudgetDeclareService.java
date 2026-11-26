package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface IInvBudgetDeclareService extends IService<InvBudgetDeclare> {

    BaseResponse createOrUpdateInvBudgetDeclare(InvBudgetDeclareRequest request);

    BaseResponse getInvBudgetDeclareList(InvBudgetDeclareRequest request, String uId);

    BaseResponse getInvBudgetDeclareByBdId(String bdId);

    BaseResponse deleteInvBudgetDeclareByBdId(String bdId);

    BaseResponse getInvBudgetDeclareDtlByPIds(List<String> pIds);

    BaseResponse getInvBudgetDeclareByPId(String pId);

    BaseResponse getInvBudgetDeclareAndDtlByPid(String pId);

    /**
     * 项目ID查询审批通过的概算编制单位、概算审核单位
     *
     * @param pId
     * @return
     */
    BaseResponse getInvBudegetCompanyByPid(String pId);

    BaseResponse approve2PApproveBudget(String bdId);

    /**
     * 查询概算余额
     * 概算金额-项目下所有合同金额合计
     *
     * @param pId
     * @return
     */
    BaseResponse getProjectBudgetBalance(String pId);

    /**
     * 查询已使用概算金额百分比
     * 含本次合同金额
     *
     * @param pId
     * @param conMoney
     * @return
     */
    BaseResponse getConBudgetRate(String pId, BigDecimal conMoney);

    /**
     * 校验项目ID是否已有概算
     *
     * @param pId
     * @return
     */
    boolean havingBudgetWithPid(String pId,String type);


    /**
     * 查询该项目的前置数据
     *
     * @param pId
     * @param type
     * @return
     */
    BaseResponse getPreStageListByPIdAndType(String pId, String type);



    /**
     * 审批
     *
     * @param request
     * @return
     */
     BaseResponse approvalInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request);


    /**
     * 概算清单列表  //Budget：概算、Estimate：估算 、Rough：匡算
     * @param pId
     * @return
     */
    List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByPid(String pId);


    /**
     * 概算清单树结构 //Budget：概算、Estimate：估算 、Rough：匡算
     * @param pId
     * @return
     */
    List<InvBudgetDeclareDtlModel> getInvTypeBudgetDeclareDtlTreeByPid(String pId);

    /**
     * 获取概算清单详细数据
     *
     * @param id
     * @return
     */
    BaseResponse getInvBudgetDeclareDtoById(String id);

    /**
     * 按项目ID获取概算详情（含明细清单树、建安费/其他费/土地费/贷款利息/预备费）
     *
     * 项目下“已审批结束”的概算必须正好一条：没有概算返回提示，多条时直接报错不返回详情。
     *
     * @param pId 项目ID
     * @return 概算详情
     */
    BaseResponse getInvBudgetDeclareDetailByPId(String pId);

}
