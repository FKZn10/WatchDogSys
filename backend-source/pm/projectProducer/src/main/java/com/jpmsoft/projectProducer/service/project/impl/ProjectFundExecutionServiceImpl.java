package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.projectProducer.mapper.investment.InvBudgetDeclareMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectFundExecutionMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundExecutionDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO.ContractDetail;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO.PaidAmount;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectFundExecutionVO.SubjectPayment;
import com.jpmsoft.projectProducer.service.project.ProjectFundExecutionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 按概算科目树汇总当前项目的合同金额、招标控制价及自筹、国拨已支付费用。
 */
@Service
public class ProjectFundExecutionServiceImpl implements ProjectFundExecutionService {
    @Resource
    private PreProjectMapper preProjectMapper;
    @Resource
    private InvBudgetDeclareMapper invBudgetDeclareMapper;
    @Resource
    private ProjectFundExecutionMapper projectFundExecutionMapper;

    /**
     * 使用项目唯一一份流程结束的概算，按科目归集合同、自筹和国拨支付。
     * 自筹取 Pay_Source=02、Pay_State=7 的 Pay_Payable，以 Pay_Application_Date 分年；
     * 国拨取 Pay_Source=01 或为空且 Pay_State=7 的支付，金额和年份口径与自筹相同。
     * 未填日期单列并计入各自小计。仅纳入关联当前概算的本项目合同，不递归查询子项目。
     * F列通过合同标段取最新创建的招标文件 D_Amount，同一标段的多合同只计一次。
     * S列金额与合同金额一致，报表生成时间采用 Asia/Shanghai 当前时间。
     *
     * @param pId 项目 ID
     * @return 固定三类科目树、动态年度支付子表及项目合计；概算不唯一或树结构异常时失败
     */
    @Override
    public BaseResponse getProjectFundExecutionByPId(String pId) {
        if (StrUtil.isBlank(pId)) {
            return BaseResponse.fail("项目ID不能为空");
        }
        PreProject project = preProjectMapper.selectById(pId.trim());
        if (project == null || Boolean.TRUE.equals(project.getDeleted())) {
            return BaseResponse.fail("项目不存在或已删除");
        }
        return buildProjectFundExecution(project);
    }

    /**
     * 按 DTO 的单个项目或项目 ID 列表筛选未删除且审批结束的项目，在数据库分页。
     * 分页项目行复用单项目统计口径，不再次按 ID 查询项目。
     *
     * @param request 分页与项目条件；pId 与 projectIds 同时指定时取交集，项目条件不传或列表为空时不限制项目
     * @return 当前页项目统计数组及项目总数；任何项目统计失败时返回该项目的错误信息
     */
    @Override
    public BaseResponse getProjectFundExecutionList(ProjectFundExecutionDTO request) {
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
        query.select("P_Id", "P_Name", "P_Calculate_Roughly_Amount", "P_Construction_Area")
                .eq("State", ApprovalStatusEnum.Already_Over.getIndex())
                .and(condition -> condition.eq("deleted", false).or().isNull("deleted"))
                .eq(StrUtil.isNotBlank(request.getPId()), "P_Id", StrUtil.trim(request.getPId()))
                .in(!projectIds.isEmpty(), "P_Id", projectIds)
                .orderByDesc("P_Create_Time").orderByAsc("P_Id");
        Page<PreProject> projectPage = preProjectMapper.selectPage(new Page<>(page, size), query);
        List<ProjectFundExecutionVO> records = new ArrayList<>();
        for (PreProject project : projectPage.getRecords()) {
            BaseResponse response = buildProjectFundExecution(project);
            if (!response.isSuccess()) {
                return BaseResponse.fail("项目[" + project.getpId() + "]统计失败：" + response.getMessage());
            }
            records.add((ProjectFundExecutionVO) response.getData());
        }
        return BaseResponse.ok(records, projectPage.getTotal());
    }

    /**
     * 使用已读取的项目数据，计算 H2 可研单方造价并组装三类科目树及各项金额统计。
     * H2 直接以元除以平方米，四舍五入保留两位小数；缺少输入或面积无效时为空。
     *
     * @param project 当前项目，已确认存在且未删除
     * @return 单项目完整统计，概算不唯一或科目结构异常时返回失败响应
     */
    private BaseResponse buildProjectFundExecution(PreProject project) {
        ProjectFundExecutionVO result = new ProjectFundExecutionVO();
        result.setReportTime(new Date());
        result.setPId(project.getpId());
        result.setPName(project.getpName());
        result.setPCalculateRoughlyAmount(project.getpCalculateRoughlyAmount());
        result.setPConstructionArea(project.getpConstructionArea());
        Float constructionArea = project.getpConstructionArea();
        if (project.getpCalculateRoughlyAmount() != null && constructionArea != null
                && Float.isFinite(constructionArea) && constructionArea > 0) {
            result.setFeasibilityUnitCost(project.getpCalculateRoughlyAmount().divide(
                    new BigDecimal(Float.toString(constructionArea)), 2, RoundingMode.HALF_UP));
        }

        List<ContractDetail> groups = createGroups();
        result.setContractDtls(groups);
        List<InvBudgetDeclare> budgets = invBudgetDeclareMapper.selectList(
                new LambdaQueryWrapper<InvBudgetDeclare>()
                        .eq(InvBudgetDeclare::getPId, project.getpId())
                        .eq(InvBudgetDeclare::getBdState, ApprovalStatusEnum.Already_Over.getIndex()));
        if (budgets.size() > 1) {
            return BaseResponse.fail("项目存在多份流程结束的概算，无法确定统计概算");
        }
        if (budgets.isEmpty()) {
            finishResult(result, groups);
            return BaseResponse.ok(result);
        }
        String bdId = budgets.get(0).getBdId();
        List<ContractDetail> subjects = projectFundExecutionMapper.selectSubjects(bdId);
        Map<String, ContractDetail> nodes = new LinkedHashMap<>();
        for (ContractDetail subject : subjects) {
            nodes.put(subject.getBddId(), subject);
        }
        Set<String> assigned = new HashSet<>();
        for (ContractDetail subject : subjects) {
            if (StrUtil.isBlank(subject.getBddParentId()) || "0".equals(subject.getBddParentId())) {
                int index = categoryIndex(subject.getBddName());
                if (index < 0) {
                    return BaseResponse.fail("概算外层科目无法匹配固定三类：" + subject.getBddName());
                }
                ContractDetail group = groups.get(index);
                if (group.getBddId() != null) {
                    return BaseResponse.fail("概算存在重复外层分类：" + group.getBddName());
                }
                group.setBddId(subject.getBddId());
                group.setBddParentId(subject.getBddParentId());
                group.setBddCode(subject.getBddCode());
                group.setBddNumber(subject.getBddNumber());
                group.setBddTotal(subject.getBddTotal());
                nodes.put(subject.getBddId(), group);
            }
        }
        for (ContractDetail subject : subjects) {
            if (StrUtil.isNotBlank(subject.getBddParentId()) && !"0".equals(subject.getBddParentId())) {
                ContractDetail parent = nodes.get(subject.getBddParentId());
                if (parent == null) {
                    return BaseResponse.fail("概算科目缺少上级：" + subject.getBddName());
                }
                parent.getChildNodes().add(subject);
            }
        }
        // 在金额组装前检查循环和不可达科目，避免漏计或重复计入。
        for (ContractDetail group : groups) {
            if (!validateTree(group, new HashSet<>(), assigned)) {
                return BaseResponse.fail("概算科目存在循环或重复关联");
            }
        }
        if (assigned.size() != subjects.size()) {
            return BaseResponse.fail("概算科目存在无法归入固定三类的节点");
        }
        for (ContractDetail amount : projectFundExecutionMapper.selectContractAmounts(project.getpId(), bdId)) {
            ContractDetail subject = nodes.get(amount.getBddId());
            if (subject != null) {
                subject.setConMoneyTotal(zeroIfNull(amount.getConMoneyTotal()));
            }
        }
        for (ContractDetail amount : projectFundExecutionMapper.selectTenderControlAmounts(project.getpId(), bdId)) {
            ContractDetail subject = nodes.get(amount.getBddId());
            if (subject != null) {
                subject.setTenderControlPrice(zeroIfNull(amount.getTenderControlPrice()));
            }
        }
        Map<String, Map<Integer, BigDecimal>> paidBySubject = new HashMap<>();
        for (SubjectPayment payment : projectFundExecutionMapper.selectSelfPaidAmounts(project.getpId(), bdId)) {
            paidBySubject.computeIfAbsent(payment.getBddId(), key -> new HashMap<>())
                    .merge(payment.getYear(), zeroIfNull(payment.getAmount()), BigDecimal::add);
        }
        Map<String, Map<Integer, BigDecimal>> governmentPaidBySubject = new HashMap<>();
        for (SubjectPayment payment : projectFundExecutionMapper.selectGovernmentPaidAmounts(project.getpId(), bdId)) {
            governmentPaidBySubject.computeIfAbsent(payment.getBddId(), key -> new HashMap<>())
                    .merge(payment.getYear(), zeroIfNull(payment.getAmount()), BigDecimal::add);
        }
        for (ContractDetail subject : nodes.values()) {
            subject.setGovernmentPaidDtls(toPaidRows(governmentPaidBySubject.getOrDefault(
                    subject.getBddId(), Collections.emptyMap())));
            subject.setSelfPaidDtls(toPaidRows(paidBySubject.getOrDefault(subject.getBddId(), Collections.emptyMap())));
        }
        for (ContractDetail group : groups) {
            rollup(group);
        }
        finishResult(result, groups);
        return BaseResponse.ok(result);
    }

    /**
     * 建立固定顺序的三类，即使没有概算或支付数据也保留分类。
     *
     * @return 三个空分类
     */
    private List<ContractDetail> createGroups() {
        List<ContractDetail> groups = new ArrayList<>();
        String[] names = {"工程费用", "工程建设其他费", "基本预备费"};
        String[] codes = {"ENGINEERING", "OTHER", "RESERVE"};
        for (int i = 0; i < names.length; i++) {
            ContractDetail group = new ContractDetail();
            group.setBddName(names[i]);
            group.setCategory(codes[i]);
            groups.add(group);
        }
        return groups;
    }

    /**
     * 兼容概算和 Excel 对固定三类的常见命名差异，不按排序号猜测分类。
     *
     * @param name 概算外层名称
     * @return 分类位置，无法识别时为 -1
     */
    private int categoryIndex(String name) {
        String normalized = name == null ? "" : name.replaceAll("\\s+", "").replace("其它", "其他");
        if ("工程费用".equals(normalized) || "工程费".equals(normalized)) {
            return 0;
        }
        if ("工程建设其他费".equals(normalized) || "工程建设其他费用".equals(normalized)
                || "建设工程其他费用".equals(normalized) || "建设工程其他费".equals(normalized)) {
            return 1;
        }
        if ("基本预备费".equals(normalized) || "预备费".equals(normalized)) {
            return 2;
        }
        return -1;
    }

    /**
     * 检查节点只能被访问一次，检测循环、重复挂接，并记录可达科目。
     *
     * @param node    当前科目或固定分类
     * @param path    当前祖先路径
     * @param visited 已访问科目 ID
     * @return 是否为合法树
     */
    private boolean validateTree(ContractDetail node, Set<String> path, Set<String> visited) {
        String id = node.getBddId();
        if (id != null && (!path.add(id) || !visited.add(id))) {
            return false;
        }
        for (ContractDetail child : node.getChildNodes()) {
            if (!validateTree(child, path, visited)) {
                return false;
            }
        }
        if (id != null) {
            path.remove(id);
        }
        return true;
    }

    /**
     * 自下而上汇总本级直接发生额和子级汇总额，父子金额只计入一次。
     *
     * @param node 当前概算科目
     */
    private void rollup(ContractDetail node) {
        Map<Integer, BigDecimal> paid = readPaidRows(node.getSelfPaidDtls());
        Map<Integer, BigDecimal> governmentPaid = readPaidRows(node.getGovernmentPaidDtls());
        BigDecimal contract = zeroIfNull(node.getConMoneyTotal());
        BigDecimal tenderControl = zeroIfNull(node.getTenderControlPrice());
        for (ContractDetail child : node.getChildNodes()) {
            rollup(child);
            contract = contract.add(child.getConMoneyTotal());
            tenderControl = tenderControl.add(child.getTenderControlPrice());
            readPaidRows(child.getSelfPaidDtls()).forEach((year, amount) ->
                    paid.merge(year, amount, BigDecimal::add));
            readPaidRows(child.getGovernmentPaidDtls()).forEach((year, amount) ->
                    governmentPaid.merge(year, amount, BigDecimal::add));
        }
        node.setConMoneyTotal(contract);
        node.setTenderControlPrice(tenderControl);
        node.setContractOrEstimateAmount(contract);
        node.setSelfPaidDtls(toPaidRows(paid));
        node.setGovernmentPaidDtls(toPaidRows(governmentPaid));
    }

    /**
     * 汇总三类金额到项目层；无数据也返回金额为 0 的小计行。
     *
     * @param result 项目结果
     * @param groups 三个已完成汇总的分类
     */
    private void finishResult(ProjectFundExecutionVO result, List<ContractDetail> groups) {
        Map<Integer, BigDecimal> paid = new HashMap<>();
        Map<Integer, BigDecimal> governmentPaid = new HashMap<>();
        BigDecimal contract = BigDecimal.ZERO;
        BigDecimal tenderControl = BigDecimal.ZERO;
        for (ContractDetail group : groups) {
            if (group.getSelfPaidDtls().isEmpty()) {
                group.setSelfPaidDtls(toPaidRows(Collections.emptyMap()));
            }
            if (group.getGovernmentPaidDtls().isEmpty()) {
                group.setGovernmentPaidDtls(toPaidRows(Collections.emptyMap()));
            }
            contract = contract.add(group.getConMoneyTotal());
            tenderControl = tenderControl.add(group.getTenderControlPrice());
            readPaidRows(group.getSelfPaidDtls()).forEach((year, amount) ->
                    paid.merge(year, amount, BigDecimal::add));
            readPaidRows(group.getGovernmentPaidDtls()).forEach((year, amount) ->
                    governmentPaid.merge(year, amount, BigDecimal::add));
        }
        result.setConMoneyTotal(contract);
        result.setTenderControlPrice(tenderControl);
        result.setContractOrEstimateAmount(contract);
        result.setSelfPaidDtls(toPaidRows(paid));
        result.setGovernmentPaidDtls(toPaidRows(governmentPaid));
    }

    /**
     * 提取年度与未填日期金额，忽略小计行以免再次累计。
     *
     * @param rows 自筹或国拨支付子表
     * @return 年度金额映射，空键代表未填申请日期
     */
    private Map<Integer, BigDecimal> readPaidRows(List<PaidAmount> rows) {
        Map<Integer, BigDecimal> paid = new HashMap<>();
        for (PaidAmount row : rows) {
            if (!"SUBTOTAL".equals(row.getType())) {
                paid.merge(row.getYear(), row.getAmount(), BigDecimal::add);
            }
        }
        return paid;
    }

    /**
     * 生成一条小计、升序实际年份和可选的未填申请日期行，不补无数据年份。
     *
     * @param amounts 年度金额映射，允许空键
     * @return 自筹或国拨支付子表
     */
    private List<PaidAmount> toPaidRows(Map<Integer, BigDecimal> amounts) {
        List<PaidAmount> rows = new ArrayList<>();
        BigDecimal total = amounts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        rows.add(paidRow("SUBTOTAL", null, "小计", total));
        amounts.keySet().stream().filter(Objects::nonNull).sorted().forEach(year ->
                rows.add(paidRow("YEAR", year, String.valueOf(year), amounts.get(year))));
        if (amounts.containsKey(null)) {
            rows.add(paidRow("UNDATED", null, "未填申请日期", amounts.get(null)));
        }
        return rows;
    }

    /**
     * 创建一条带类型的支付金额行。
     *
     * @param type   行类型
     * @param year   实际年份或空
     * @param label  行名称
     * @param amount 汇总金额
     * @return 支付金额行
     */
    private PaidAmount paidRow(String type, Integer year, String label, BigDecimal amount) {
        PaidAmount row = new PaidAmount();
        row.setType(type);
        row.setYear(year);
        row.setLabel(label);
        row.setAmount(amount);
        return row;
    }

    /**
     * 将空金额视为 0，便于科目金额汇总。
     *
     * @param amount 原始金额
     * @return 非空金额
     */
    private BigDecimal zeroIfNull(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
