package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectContractCostControlMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectContractCostControlDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.project.ContractChangeSummaryVO;
import com.jpmsoft.projectProducer.pojo.vo.project.ContractChangeSummaryVO.ChangeRow;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectContractCostControlVO;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectContractCostControlVO.ContractRow;
import com.jpmsoft.projectProducer.service.project.ProjectContractCostControlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 以项目为维度的施工合同造价控制报表。
 */
@Service
public class ProjectContractCostControlServiceImpl implements ProjectContractCostControlService {
    @Resource
    private PreProjectMapper preProjectMapper;
    @Resource
    private ConContractMapper conContractMapper;
    @Resource
    private ProjectContractCostControlMapper projectContractCostControlMapper;

    /**
     * 获取未删除项目全部 Con_Approval_State=7 的合同；不额外限定合同类型。
     * 变更金额已由 SQL 按 Ec_State 聚合，暂列金不重复计入总价。
     *
     * @param pId 项目 ID，不能为空
     * @return 合同明细及合计；项目不存在或已删除时失败
     */
    @Override
    public BaseResponse getProjectContractCostControlByPId(String pId) {
        if (StrUtil.isBlank(pId)) {
            return BaseResponse.fail("项目ID不能为空");
        }
        PreProject project = preProjectMapper.selectById(pId.trim());
        if (project == null || Boolean.TRUE.equals(project.getDeleted())) {
            return BaseResponse.fail("项目不存在或已删除");
        }
        return buildProjectContractCostControl(project);
    }

    /**
     * 按 DTO 的单个项目或项目 ID 列表筛选未删除且审批结束的项目，在数据库分页。
     * 分页项目行复用单项目统计口径，不再次按 ID 查询项目。
     *
     * @param request 分页与项目条件；pId 与 projectIds 同时指定时取交集，项目条件不传或列表为空时不限制项目
     * @return 当前页项目统计数组及项目总数；任何项目统计失败时返回该项目的错误信息
     */
    @Override
    public BaseResponse getProjectContractCostControlList(ProjectContractCostControlDTO request) {
        if (request == null) {
            return BaseResponse.fail("请求参数不能为空");
        }
        int page = request.getPage();
        int size = request.getSize();
        if (page < 1 || size < 1 || size > 100) {
            return BaseResponse.fail("page必须大于等于1，size必须在1至100之间");
        }
        Set<String> projectIds = new LinkedHashSet<>();
        if (request.getProjectIds() != null) {
            for (String projectId : request.getProjectIds()) {
                if (StrUtil.isBlank(projectId)) {
                    return BaseResponse.fail("projectIds不能包含空项目ID");
                }
                projectIds.add(projectId.trim());
            }
        }
        QueryWrapper<PreProject> query = new QueryWrapper<>();
        query.select("P_Id", "P_Name")
                .eq("State", ApprovalStatusEnum.Already_Over.getIndex())
                .and(condition -> condition.eq("deleted", false).or().isNull("deleted"))
                .eq(StrUtil.isNotBlank(request.getPId()), "P_Id", StrUtil.trim(request.getPId()))
                .in(!projectIds.isEmpty(), "P_Id", projectIds)
                .orderByDesc("P_Create_Time").orderByAsc("P_Id");
        Page<PreProject> projectPage = preProjectMapper.selectPage(new Page<>(page, size), query);
        List<ProjectContractCostControlVO> records = new ArrayList<>();
        for (PreProject project : projectPage.getRecords()) {
            BaseResponse response = buildProjectContractCostControl(project);
            if (!response.isSuccess()) {
                return BaseResponse.fail("项目[" + project.getpId() + "]统计失败：" + response.getMessage());
            }
            records.add((ProjectContractCostControlVO) response.getData());
        }
        return BaseResponse.ok(records, projectPage.getTotal());
    }

    /**
     * 使用已查询的项目信息构建合同明细与合计，供单项目及分页接口共用。
     *
     * @param project 已确认存在且未删除的项目
     * @return 完整项目报表，无合同时保留零金额合计
     */
    private BaseResponse buildProjectContractCostControl(PreProject project) {
        ProjectContractCostControlVO result = new ProjectContractCostControlVO();
        result.setPId(project.getpId());
        result.setPName(project.getpName());
        result.setReportTime(new Date());
        List<ContractRow> rows = projectContractCostControlMapper.selectContractRows(project.getpId());
        ContractRow total = new ContractRow();
        total.setConName("合计");
        int sequence = 1;
        for (ContractRow row : rows) {
            row.setSequence(sequence++);
            row.setConMoneyTotal(zero(row.getConMoneyTotal()));
            row.setConMoneyTentative(zero(row.getConMoneyTentative()));
            row.setApprovedChangeAmount(zero(row.getApprovedChangeAmount()));
            row.setPendingChangeAmount(zero(row.getPendingChangeAmount()));
            calculate(row);
            total.setConMoneyTotal(total.getConMoneyTotal().add(row.getConMoneyTotal()));
            total.setConMoneyTentative(total.getConMoneyTentative().add(row.getConMoneyTentative()));
            total.setApprovedChangeAmount(total.getApprovedChangeAmount().add(row.getApprovedChangeAmount()));
            total.setPendingChangeAmount(total.getPendingChangeAmount().add(row.getPendingChangeAmount()));
        }
        calculate(total);
        result.setContractDtls(rows);
        result.setTotal(total);
        return BaseResponse.ok(result);
    }


    /**
     * 查询 Ec_State=7 的已审批变更明细及金额合计。
     *
     * @param conId 合同 ID
     * @return 已审批变更汇总表
     */
    @Override
    public BaseResponse getApprovedConEcSummaryByConId(String conId) {
        return getChangeSummary(conId, true);
    }

    /**
     * 查询 Ec_State 不为 7、3、0 的未完成审批变更，NULL 状态不计入。
     *
     * @param conId 合同 ID
     * @return 未完成审批变更汇总表
     */
    @Override
    public BaseResponse getPendingConEcSummaryByConId(String conId) {
        return getChangeSummary(conId, false);
    }

    /**
     * 按合同查询变更行，保留原始空金额，合计时将空金额按零处理。
     * 两类明细均按用户指定返回 ecStateTime，不用申请日期替代。
     *
     * @param conId    合同 ID
     * @param approved true 为已审批，false 为未完成审批
     * @return 合同信息、明细及金额合计
     */
    private BaseResponse getChangeSummary(String conId, boolean approved) {
        if (StrUtil.isBlank(conId)) {
            return BaseResponse.fail("合同ID不能为空");
        }
        ConContract contract = conContractMapper.selectById(conId.trim());
        if (contract == null) {
            return BaseResponse.fail("合同不存在");
        }
        List<ChangeRow> rows = projectContractCostControlMapper.selectChangeRows(contract.getConId(), approved);
        ChangeRow total = new ChangeRow();
        total.setChangeName("合计");
        BigDecimal partyBTotal = BigDecimal.ZERO;
        BigDecimal partyATotal = BigDecimal.ZERO;
        int sequence = 1;
        for (ChangeRow row : rows) {
            row.setSequence(sequence++);
            partyBTotal = partyBTotal.add(zero(row.getPartyBAmount()));
            partyATotal = partyATotal.add(zero(row.getPartyAAmount()));
        }
        total.setPartyBAmount(partyBTotal);
        total.setPartyAAmount(partyATotal);
        ContractChangeSummaryVO result = new ContractChangeSummaryVO();
        result.setConId(contract.getConId());
        result.setConName(contract.getConName());
        result.setReportTime(new Date());
        result.setDetails(rows);
        result.setTotal(total);
        return BaseResponse.ok(result);
    }

    /**
     * 计算 I、M、N列；预估列暂为空，仅合计已明确来源的金额。
     *
     * @param row 合同行或合计行
     */
    private void calculate(ContractRow row) {
        BigDecimal changes = row.getApprovedChangeAmount().add(row.getPendingChangeAmount());
        row.setChangeSubtotal(changes);
        row.setTotalAmount(row.getConMoneyTotal().add(changes));
        if (row.getConMoneyTotal().compareTo(BigDecimal.ZERO) == 0) {
            row.setChangeRatio(null);
        } else {
            row.setChangeRatio(changes.divide(row.getConMoneyTotal(), 6, RoundingMode.HALF_UP));
        }
    }

    /**
     * 将缺失金额作为零参与已明确字段的汇总。
     *
     * @param amount 原始金额
     * @return 非空金额
     */
    private BigDecimal zero(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
