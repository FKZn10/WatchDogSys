package com.jpmsoft.projectProducer.service.maintenance.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceRecordRequest;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceRecordResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDepartment;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceRecordService;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.Color;
import java.util.*;
import java.util.List;

@Slf4j
@Service
public class MaintenanceRecordImpl extends ServiceImpl<MaintenanceRecordMapper, MaintenanceRecord> implements IMaintenanceRecordService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private com.jpmsoft.projectProducer.service.other.IApiUtilsService apiUtilsService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ISysDepartmentService sysDepartmentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMaintenanceRecord(MaintenanceRecordRequest request) {
        MaintenanceRecord entity = new MaintenanceRecord();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getMrId())) {
            entity.setMrId(GenerateUUID.getUUID());
            entity.setMrCreateTime(new Date());
            entity.setMrStateTime(null);
            entity.setMrState(request.getMrState() == null ? ApprovalStatusEnum.Not_Started.getIndex() : request.getMrState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getMrId(), request.gettId(), entity.getMrState() + "", entity.getMrCreateUid(), entity.getMrCreateUname()
                    , entity.getMrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMrPicAfter(), request.getMrPicBefore()), entity.getMrId());

        } else {
            MaintenanceRecord model = baseMapper.selectById(request.getMrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getMrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setMrCreateTime(model.getMrCreateTime());
            entity.setMrCreateUid(model.getMrCreateUid());
            entity.setMrCreateUname(model.getMrCreateUname());
            entity.setMrState(model.getMrState());
            entity.setMrStateTime(model.getMrStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getMrId(), request.gettId(), entity.getMrState() + "", entity.getMrCreateUid(), entity.getMrCreateUname()
                    , entity.getMrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMrPicAfter(), request.getMrPicBefore()),
                    Arrays.asList(request.getFileIds(), request.getMrPicAfterIds(), request.getMrPicBeforeIds()), entity.getMrId());

        }
        return BaseResponse.ok(entity.getMrId());

    }

    @Override
    public BaseResponse getMaintenanceRecordList(MaintenanceRecordRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Mr_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Mr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MaintenanceRecordRequest> params = getFilter(request, authSql);
        IPage<MaintenanceRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordResponse> datas = baseMapper.getMaintenanceRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMaintenanceRecordListNoAuth(MaintenanceRecordRequest request) {
        QueryMap<MaintenanceRecordRequest> params = getFilter(request, null);
        IPage<MaintenanceRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordResponse> datas = baseMapper.getMaintenanceRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMaintenanceRecordGgList(MaintenanceRecordRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(user.getUType())) return BaseResponse.fail(ResMsg.unknowUserType);

        String noAuthList = sysConfigService.getConfigByCode("LAB_REPAIR_NO_AUTH_LIST");
        boolean isNoAuth = false;
        
        if (noAuthList != null && !noAuthList.trim().isEmpty()) {
            String[] noAuthIds = noAuthList.split(",");
            
            // 预先构建用户部门及其所有上级部门的集合（用于快速匹配）
            Set<String> userDepAndParentsSet = buildUserDepartmentAndParentsSet(user.getDepartmentIds());
            
            for (String id : noAuthIds) {
                String trimmedId = id.trim();
                // 判断配置的是用户ID还是部门ID
                if (trimmedId.equals(user.getId())) {
                    // 配置的是用户ID，直接匹配
                    isNoAuth = true;
                    log.info("用户 {} 在零星维修实验室不鉴权名单中（用户ID匹配），跳过鉴权", user.getId());
                    break;
                } else {
                    // 配置的是部门ID，检查该部门是否在用户部门或其上级部门中
                    if (userDepAndParentsSet != null && userDepAndParentsSet.contains(trimmedId)) {
                        isNoAuth = true;
                        log.info("用户 {} 所在部门或其上级部门包含配置部门 {}，跳过鉴权", user.getId(), trimmedId);
                        break;
                    }
                }
            }
        }
        
        String authSql = null;
        if (!isNoAuth && user.getUType().equals(UserTypeEnum.Out_User.getIndex().toString())) {
            authSql = " where ";
        }
        QueryMap<MaintenanceRecordRequest> params = getFilter(request, authSql);
        if (ValidateUtil.isNotEmpty(authSql)) {
            params.put("ggCid", user.getCId());
            params.put("ggUid", user.getId());
        }
        params.put("ggUid", user.getId());
        if (isNoAuth) {
            params.put("checkAuthority", null);
        }

        IPage<MaintenanceRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceRecordResponse> datas = baseMapper.getMaintenanceRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    /**
     * 构建用户部门及其所有上级部门的集合
     * @param userDepartmentIds 用户所在的部门ID列表
     * @return 包含用户部门及其所有上级部门的Set
     */
    private Set<String> buildUserDepartmentAndParentsSet(List<String> userDepartmentIds) {
        if (userDepartmentIds == null || userDepartmentIds.isEmpty()) {
            return null;
        }
        
        // 一次性查询全量部门数据（部门数据量小，适合内存处理）
        List<SysDepartment> allDepartments = sysDepartmentService.list();
        if (allDepartments == null || allDepartments.isEmpty()) {
            return null;
        }
        
        // 构建部门ID -> 父部门ID 的映射，方便快速查找
        Map<String, String> depToParentMap = new HashMap<>();
        for (SysDepartment dept : allDepartments) {
            if (dept.getdId() != null && dept.getdParentid() != null) {
                depToParentMap.put(dept.getdId(), dept.getdParentid());
            }
        }
        
        Set<String> result = new HashSet<>();
        
        for (String depId : userDepartmentIds) {
            // 添加当前部门
            result.add(depId);
            
            // 递归添加所有上级部门
            addParentDepartmentsFast(depId, depToParentMap, result);
        }
        
        return result;
    }

    /**
     * 快速递归添加部门的所有上级部门到集合中（基于内存Map）
     * @param departmentId 当前部门ID
     * @param depToParentMap 部门ID -> 父部门ID 的映射
     * @param result 结果集合
     */
    private void addParentDepartmentsFast(String departmentId, Map<String, String> depToParentMap, Set<String> result) {
        String parentId = depToParentMap.get(departmentId);
        // 如果父部门存在、不是根部门、且未添加过
        if (parentId != null && !"0".equals(parentId) && !result.contains(parentId)) {
            result.add(parentId);
            // 递归查询更上级的部门
            addParentDepartmentsFast(parentId, depToParentMap, result);
        }
    }

    private QueryMap<MaintenanceRecordRequest> getFilter(MaintenanceRecordRequest request, String authSql) {
        QueryMap<MaintenanceRecordRequest> params = new QueryMap<>(request);
        params.put("pName").put("conName").put("mrCId").put("secId").put("mrReportUname")
                .put("mrReportTel").put("mrState").put("metName").put("metId").put("mrPosition")
                .put("mrCreateUname").put("authSql", authSql).put("pId").put("mrRepairState")
                .put(request.getMrCreateTimes(), "mrCreateTimeStart", "mrCreateTimeEnd")
                .put(request.getMrReportTimes(), "mrReportTimeStart", "mrReportTimeEnd")
                .put(request.getMrCosts(), "mrCostStart", "mrCostEnd").put("mrReportTime").put("mrSerialNumber")
                .put("mrConstructionId").put("mrConstructionName").put("mrCampus").put("mfCode")
                .put("piecemealSettlementFlowFlag").put("jinfeihao").put("mrCategory").put("checkAuthority")
                .put("mrIssuingUnit");
        return params;
    }

    @Override
    public BaseResponse getMaintenanceRecordById(String mrId) {
        MaintenanceRecordResponse data = baseMapper.getMaintenanceRecordById(mrId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getRecordsByRepairState(int pageNum, int size) {
        // 创建分页对象
        Page<MaintenanceRecord> page = new Page<>(pageNum, size);
        // 构建查询条件
        LambdaQueryWrapper<MaintenanceRecord> queryWrappe = new LambdaQueryWrapper<>();
        queryWrappe.and(wrapper -> wrapper
                .isNull(MaintenanceRecord::getMrRepairState)
                .or()
                .eq(MaintenanceRecord::getMrRepairState, 0)
                .eq(MaintenanceRecord::getMrState, 7)
                .or()
                .eq(MaintenanceRecord::getMrRepairState, "")
        );
        // 执行分页查询
        Page<MaintenanceRecord> resultPage = this.page(page, queryWrappe);
        return BaseResponse.ok((Object) resultPage.getRecords(), (int) resultPage.getTotal());
    }

    @Override
    public Workbook exportMaintenanceRecordGgExcel(MaintenanceRecordRequest request, LogUserInfoVo user) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        if (ValidateUtil.isEmpty(user.getUType())) {
            throw new JPMException(ResMsg.unknowUserType);
        }
        String authSql = null;
        if (user.getUType().equals(UserTypeEnum.Out_User.getIndex().toString())) {//内部不鉴权，外部施工单位审批节点人同单位或者为审批人
            authSql = " where ";
        }
        QueryMap<MaintenanceRecordRequest> params = getFilter(request, authSql);
        if (ValidateUtil.isNotEmpty(authSql)) {
            params.put("ggCid", user.getCId());
            params.put("ggUid", user.getId());
        }
        IPage<MaintenanceRecordResponse> page = new Page<>(1, Integer.MAX_VALUE);
        List<MaintenanceRecordResponse> datas = baseMapper.getMaintenanceRecordList(page, params.getQuery());
        if (ValidateUtil.isEmpty(datas)) {
            return workbook;
        }

        String[] rowTitle = new String[]{"序号", "项目名称", "流水号", "校区", "报修时间", "完工时间", "经费号", "施工单位", "工程费预估", "维修状态"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("零星维修申报表");
        // 宽列的索引
        List<Integer> wideColumns = Arrays.asList(1, 5, 6);
        List<Integer> mediumColumns = Arrays.asList(2, 3);
        for (int i = 0; i < rowTitle.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2000);
            }else if (mediumColumns.contains(i)) {
                sheet.setColumnWidth(i, 6000);
            } else if (wideColumns.contains(i)) {
                sheet.setColumnWidth(i, 10000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateStyle.setBorderTop(BorderStyle.MEDIUM);
        dateStyle.setBorderBottom(BorderStyle.MEDIUM);
        dateStyle.setBorderLeft(BorderStyle.MEDIUM);
        dateStyle.setBorderRight(BorderStyle.MEDIUM);
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DateFormatUtil.FORMAT_PATTERN_OF_DAY_1));

        CellStyle styleState1 = createColoredStyle(workbook, style, "#CC6633");
        CellStyle styleState2 = createColoredStyle(workbook, style, "#FF3300");
        CellStyle styleState3 = createColoredStyle(workbook, style, "#0066FF");
        CellStyle styleState4 = createColoredStyle(workbook, style, "#009900");
        CellStyle styleState5 = createColoredStyle(workbook, style, "#FFFF00");
        CellStyle styleState6 = createColoredStyle(workbook, style, "#00FF7F");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }
        if (!CollectionUtils.isEmpty(datas)) {
            JSONArray WXZT = DictionaryUtils.dictList("JPMsoft_WXZT");
            JSONArray XMSSXQ = DictionaryUtils.dictList("JPMsoft_XMSSXQ");
            int count = 1;
            for (MaintenanceRecordResponse data : datas) {
                row = sheet.createRow(rowNum++);
                int index = 0;

                cell = row.createCell(index++);
                cell.setCellValue(count++);
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getPName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getMrSerialNumber()) ? "/" : data.getMrSerialNumber());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getMrCampus()) ? "/" : DictionaryUtils.processDictList(XMSSXQ, data.getMrCampus()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                if (data.getMrReportTime() != null) {
                    cell.setCellValue(data.getMrReportTime());
                } else {
                    cell.setCellValue("/");
                }
                cell.setCellStyle(dateStyle);

                cell = row.createCell(index++);
                if (data.getMrRepairState() != null && data.getMrStateTime() != null && "5".equals(data.getMrRepairState()) && data.getMrState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                    cell.setCellValue(data.getMrStateTime());
                } else {
                    cell.setCellValue("/");
                }
                cell.setCellStyle(dateStyle);

                cell = row.createCell(index++);
                if (ValidateUtil.isNotEmpty(data.getMaintenanceFinanceList())) {
                    cell.setCellValue(data.getMaintenanceFinanceList().get(0).getMfCode());
                } else {
                    cell.setCellValue("/");
                }
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getMrConstructionName()) ? "/" : data.getMrConstructionName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getMrBudgetAmount() != null ? data.getMrBudgetAmount().doubleValue() : 0);
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                String repairState = data.getMrRepairState();
                cell.setCellValue(ValidateUtil.isEmpty(repairState) ? "/" : DictionaryUtils.processDictList(WXZT, repairState));
                if (ValidateUtil.isNotEmpty(repairState)) {
                    switch (repairState) {
                        case "0":
                            cell.setCellStyle(style);
                            break;
                        case "1":
                            cell.setCellStyle(styleState1);
                            break;
                        case "2":
                            cell.setCellStyle(styleState2);
                            break;
                        case "3":
                            cell.setCellStyle(styleState3);
                            break;
                        case "4":
                            cell.setCellStyle(styleState4);
                            break;
                        case "5":
                            cell.setCellStyle(styleState5);
                            break;
                        case "6":
                            cell.setCellStyle(styleState6);
                            break;
                        default:
                            cell.setCellStyle(style);
                            break;
                    }
                } else {
                    cell.setCellStyle(style);
                }
            }
        }
        return workbook;
    }

    private XSSFCellStyle createColoredStyle(XSSFWorkbook workbook, CellStyle baseStyle, String hexColor) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.cloneStyleFrom(baseStyle);
        // 创建一个新的字体对象
        XSSFFont font = workbook.createFont();
        // 解析十六进制颜色字符串
        Color color = Color.decode(hexColor);
        // 创建XSSFColor对象
        XSSFColor xssfColor = new XSSFColor(color, null);
        // 设置字体颜色
        font.setColor(xssfColor);
        // 将字体应用到样式中
        style.setFont(font);
        return style;
    }

    /**
     * 根据流程节点刷新单条广工零星维修记录的维修状态。
     * 审批状态为3时直接将维修状态同步为7，不查询流程节点。
     *
     * @param mrId 维修记录ID
     * @return 刷新后的维修状态，参数、记录、审批状态或配置不符合要求时返回失败
     */
    @Override
    public BaseResponse refreshRepairStateGg(String mrId) {
        if (ValidateUtil.isEmpty(mrId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        MaintenanceRecord entity = this.getById(mrId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (Integer.valueOf(0).equals(entity.getMrState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        if (Integer.valueOf(3).equals(entity.getMrState())) {
            return refreshRepairStateGg(entity, Collections.emptyMap());
        }

        String configByCode = sysConfigService.getConfigByCode("repairDeclarationActId");
        if (ValidateUtil.isEmpty(configByCode)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        return refreshRepairStateGg(entity, buildRepairNodeToStateMap(configByCode));
    }

    /**
     * 将维修状态对应的节点配置转换为节点到维修状态的映射。
     *
     * @param configByCode repairDeclarationActId 配置内容
     * @return 流程节点到维修状态的映射
     */
    private Map<String, String> buildRepairNodeToStateMap(String configByCode) {
        Map<String, String> configMap = JSONUtil.toBean(configByCode, Map.class);
        Map<String, String> nodeToStateMap = new HashMap<>();
        configMap.forEach((state, nodes) -> {
            for (String node : nodes.split(",")) {
                nodeToStateMap.put(node, state);
            }
        });

        return nodeToStateMap;
    }

    /**
     * 按待办节点或流程结束后的最后一个已办节点更新维修状态。
     * 审批状态为3时优先将维修状态同步为7。
     *
     * @param entity         待刷新的维修记录
     * @param nodeToStateMap 流程节点到维修状态的映射
     * @return 刷新后的维修状态
     */
    private BaseResponse refreshRepairStateGg(MaintenanceRecord entity, Map<String, String> nodeToStateMap) {
        if (Integer.valueOf(3).equals(entity.getMrState())) {
            if (!"7".equals(entity.getMrRepairState())) {
                entity.setMrRepairState("7");
                this.updateById(entity);
            }
            return BaseResponse.ok(entity.getMrRepairState());
        }
        String mrId = entity.getMrId();
        TaskResponse task = baseMapper.getTask(mrId);
        // 如果有待办任务，根据待办任务节点更新状态
        if (ValidateUtil.isNotNull(task) && ValidateUtil.isNotNull(task.getTaskcurrentactid())) {
            String currentState = nodeToStateMap.get(task.getTaskcurrentactid());
            if (currentState != null && !currentState.equals(entity.getMrRepairState())) {
                entity.setMrRepairState(currentState);
                this.updateById(entity);
            }
            return BaseResponse.ok(entity.getMrRepairState());
        }

        // 如果没有待办任务（流程已结束），根据最后一个已办任务更新状态
        if (ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getMrState())) {
            TaskProcessin taskProcessin = baseMapper.getTaskProcessin(mrId);
            if (ValidateUtil.isNotNull(taskProcessin) && ValidateUtil.isNotNull(taskProcessin.getTpActid())) {
                String lastState = nodeToStateMap.get(taskProcessin.getTpActid());
                // 如果最后一个节点是“已派单”，则最终状态为“已完工”，否则为“转其他审批”
                String finalState = "2".equals(lastState) ? "3" : "5";
                if (!finalState.equals(entity.getMrRepairState())) {
                    entity.setMrRepairState(finalState);
                    this.updateById(entity);
                }
            }
        }

        return BaseResponse.ok(entity.getMrRepairState());
    }

    @Override
    public BaseResponse getRepairPersonByMrId(String mrId) {

        if (ValidateUtil.isEmpty(mrId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        String actCode = sysConfigService.getConfigByCode("ACT_CODE");
        if (actCode == null || actCode.trim().isEmpty()) {
            log.warn("未配置 ACT_CODE，默认使用 fz1");
            actCode = "fz1";
        }

        String repairPerson = baseMapper.getRepairPersonByMrId(mrId, actCode);

        SysUser sysUser = sysUserService.getById(repairPerson);

        return BaseResponse.ok(sysUser);
    }

    /**
     * 分页全量刷新广工零星维修状态。
     * 跳过审批状态为0的记录，审批状态为3时直接将维修状态同步为7。
     * 已作废的维修记录保持不变；已完工记录再按结算单同步为已结算4、结算中8或已完工3。
     * 结算完成后，按支付单同步为已支付6、支付中9，支付单作废则恢复已结算4。
     * 配置仅解析一次，维修流程状态推导规则与单条刷新一致。
     *
     * @return 全量刷新结果，未配置节点映射时返回配置错误
     */
    @Override
    public BaseResponse refreshRepairStateGgAll() {
        String configByCode = sysConfigService.getConfigByCode("repairDeclarationActId");
        if (ValidateUtil.isEmpty(configByCode)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        Map<String, String> nodeToStateMap = buildRepairNodeToStateMap(configByCode);
        int pageNum = 1;
        int pageSize = 500;
        while (true) {
            Page<MaintenanceRecord> page = new Page<>(pageNum, pageSize, false);
            LambdaQueryWrapper<MaintenanceRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(MaintenanceRecord::getMrId, MaintenanceRecord::getMrState,
                            MaintenanceRecord::getMrRepairState)
                    .ne(MaintenanceRecord::getMrState, 0)
                    .orderByAsc(MaintenanceRecord::getMrId);
            List<MaintenanceRecord> records = this.page(page, wrapper).getRecords();
            List<String> completedMrIds = new ArrayList<>();
            for (MaintenanceRecord record : records) {
                if ("7".equals(record.getMrRepairState())) {
                    continue;
                }
                refreshRepairStateGg(record, nodeToStateMap);
                if ("3".equals(record.getMrRepairState())) {
                    completedMrIds.add(record.getMrId());
                }
            }
            refreshSettlementRepairStates(completedMrIds);
            if (records.size() < pageSize) {
                break;
            }
            pageNum++;
        }
        return BaseResponse.ok();
    }

    /**
     * 按结算主表状态同步已完工记录：7为已结算4，非0、3、7为结算中8，3恢复已完工3。
     * 同一维修关联多张结算单时，已结算优先于结算中和作废单；维修状态为7时禁止覆盖。
     * 已结算记录继续按支付单状态同步。
     *
     * @param completedMrIds 按维修流程判定为已完工的记录ID列表
     */
    private void refreshSettlementRepairStates(List<String> completedMrIds) {
        if (completedMrIds.isEmpty()) {
            return;
        }
        List<MaintenanceRecord> settlementStates = baseMapper.getSettlementRepairStates(completedMrIds);
        List<String> settledMrIds = new ArrayList<>();
        for (MaintenanceRecord settlementState : settlementStates) {
            this.lambdaUpdate()
                    .eq(MaintenanceRecord::getMrId, settlementState.getMrId())
                    .eq(MaintenanceRecord::getMrRepairState, "3")
                    .set(MaintenanceRecord::getMrRepairState, settlementState.getMrRepairState())
                    .update();
            if ("4".equals(settlementState.getMrRepairState())) {
                settledMrIds.add(settlementState.getMrId());
            }
        }
        refreshPaymentRepairStates(settledMrIds);
    }

    /**
     * 按支付单状态同步已结算维修记录：7为已支付6，非0、3、7为支付中9，3恢复已结算4。
     * 同一维修关联多张支付单时，已支付优先于支付中和作废单；仅更新当前已结算4的记录。
     *
     * @param settledMrIds 结算审批通过的维修记录ID列表
     */
    private void refreshPaymentRepairStates(List<String> settledMrIds) {
        if (settledMrIds.isEmpty()) {
            return;
        }
        List<MaintenanceRecord> paymentStates = baseMapper.getPaymentRepairStates(settledMrIds);
        for (MaintenanceRecord paymentState : paymentStates) {
            this.lambdaUpdate()
                    .eq(MaintenanceRecord::getMrId, paymentState.getMrId())
                    .eq(MaintenanceRecord::getMrRepairState, "4")
                    .set(MaintenanceRecord::getMrRepairState, paymentState.getMrRepairState())
                    .update();
        }
    }
}
