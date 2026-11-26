package com.jpmsoft.projectProducer.service.xz.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.xz.ProjectApplicationMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.FileStatusDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApplication;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.xz.ProjectApplicationService;
import com.jpmsoft.projectProducer.utils.BeanUtilsUpgrade;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:12
 * @Description:
 * @Version: 1.0
 */
@Service
public class ProjectApplicationServiceImpl extends ServiceImpl<ProjectApplicationMapper, ProjectApplication> implements ProjectApplicationService {

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private IApiUtilsService apiUtilsService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Resource
    private BpmFeign bpmFeign;

    @Resource
    private TaskProcessinMapper taskProcessinMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ProjectApplicationVO> page(ProjectApplicationQueryRequest request) {
        PageResult<ProjectApplicationVO> result = new PageResult<>();
        IPage<ProjectApplicationVO> page = new Page<>(request.getPageNum(), request.getPageSize());

        List<String> taskStatusList = new ArrayList<>();
        String approveStatus = null;
        //项目申报页面，登录人能看到自己创建的数据以及，所在部门审批的数据，财务部门能看到所有数据
        boolean financeFlag = false;
        List<String> createDepartIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getPageType()) && "01".equals(request.getPageType())) {
            if (StringUtils.isNotBlank(request.getCreateDepartId())){
                String[] createDepartIdArr = request.getCreateDepartId().split(",");
                createDepartIdList = Arrays.asList(createDepartIdArr);
            }
            //获取财务部门id配置项
            String configDepartIds = sysConfigService.getConfigByCode("XzProjectFinanceDepart");
            if (StringUtils.isNotBlank(configDepartIds) && StringUtils.isNotBlank(request.getCreateDepartId())){
                String[] configDepartIdArr = configDepartIds.split(",");
                String[] createDepartIdArr = request.getCreateDepartId().split(",");
                Set<String> configDepartIdSet = new HashSet<>(Arrays.asList(configDepartIdArr));
                financeFlag = Arrays.stream(createDepartIdArr).anyMatch(configDepartIdSet::contains);
            }
        }
        //项目评审页面显示待评审、已评审数据; 流程已结束的
        if (StringUtils.isNotBlank(request.getPageType()) && "02".equals(request.getPageType())) {
            approveStatus = "7";
            taskStatusList = Arrays.asList("relevant", "finance");
        }
        //项目储备页面显示入库数据; 流程已结束的
        if (StringUtils.isNotBlank(request.getPageType()) && "03".equals(request.getPageType())) {
            approveStatus = "7";
            taskStatusList = Arrays.asList("confirm");
        }
        //资金立项通过
        if (StringUtils.isNotBlank(request.getPageType()) && "04".equals(request.getPageType())) {
            taskStatusList = Arrays.asList("fundApplication");
        }

        if (StringUtils.isNotBlank(request.getSortFiled())) {
            request.setSortFiled(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getSortFiled()));
        }

        List<ProjectApplicationVO> vos = this.baseMapper.queryPage(page, request.getSortFiled(), request.getSortType(), request.getProjectName(), request.getApplicationCompanyId(), request.getApplicationCompanyName(), request.getPlanYear(), request.getProjectType(), request.getProjectCategory(), request.getProjectManager(), request.getApplicationNum(),request.getTaskStatus(), taskStatusList, request.getPageType(), request.getCreateId(), financeFlag, createDepartIdList, approveStatus);
        result.setTotal(page.getTotal());
        result.setRows(vos);
        return result;
    }

    @Override
    public Workbook exportExcel(ProjectApplicationQueryRequest request) {
        PageResult<ProjectApplicationVO> page = page(request);
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        String[] rowTitle = new String[]{"计划年度","项目名称", "申报编号", "项目申报单位",  "项目类别", "项目类型", "项目总预算(万元)", "政府采购预算(万元)",  "非政府采购预算(万元)", "项目负责人", "计划开始年月", "计划结束年月", "填报人", "填报时间",  "任务状态", "归口部门", "初审评分", "终审评分"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("项目申报");
        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        if (!CollectionUtils.isEmpty(page.getRows())) {
            Map<String, String> taskStatusMap = new HashMap<>();
            taskStatusMap.put("save", "未启动");
            taskStatusMap.put("start", "待初审");
            taskStatusMap.put("relevant", "待评审");
            taskStatusMap.put("finance", "已评审");
            taskStatusMap.put("confirm", "已入库");
            taskStatusMap.put("back", "退回");
            taskStatusMap.put("fundPass", "资金立项申报已通过");
            taskStatusMap.put("fundApproval", "资金批复已通过");

            for (ProjectApplicationVO model : page.getRows()) {
                row = sheet.createRow(rowNum++);

                cell = row.createCell(0);
                cell.setCellValue(model.getPlanYear());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(model.getProjectName());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(model.getApplicationNum());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(model.getApplicationCompanyName());
                cell.setCellStyle(style);

                cell = row.createCell(4);
                String projectCategory = "";
                if (StringUtils.isNotBlank(model.getProjectCategory())){
                    projectCategory = DictionaryUtils.getTitle("JPMsoft_XZ_XMLB", model.getProjectCategory());
                }
                cell.setCellValue(projectCategory);
                cell.setCellStyle(style);

                cell = row.createCell(5);
                String projectType = "";
                if (StringUtils.isNotBlank(model.getProjectCategory())){
                    projectType = DictionaryUtils.getTitle("JPMsoft_XZ_XMLB", model.getProjectType());
                }
                cell.setCellValue(projectType);
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(null != model.getGeneralBudget()?MathUtil.DecimalDivisionInt(model.getGeneralBudget(), 10000).doubleValue(): 0d);
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(null != model.getGovernmentBudget()?MathUtil.DecimalDivisionInt(model.getGovernmentBudget(), 10000).doubleValue(): 0d);
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(null != model.getOtherBudget()?MathUtil.DecimalDivisionInt(model.getOtherBudget(), 10000).doubleValue(): 0d);
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(model.getProjectManager());
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(null == model.getPlanStartDate() ? "" : DateFormatUtil.dateToString(model.getPlanStartDate(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(11);
                cell.setCellValue(null == model.getPlanEndDate() ? "" : DateFormatUtil.dateToString(model.getPlanEndDate(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                cell = row.createCell(12);
                cell.setCellValue(model.getCreateName());
                cell.setCellStyle(style);

                cell = row.createCell(13);
                cell.setCellValue(null == model.getCreateTime() ? "" : DateFormatUtil.dateToString(model.getCreateTime(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                cell.setCellStyle(style);

                cell = row.createCell(14);
                cell.setCellValue(StringUtils.isBlank(model.getTaskStatus()) ? "" : taskStatusMap.get(model.getTaskStatus()));
                cell.setCellStyle(style);

                cell = row.createCell(15);
                cell.setCellValue(model.getRelevantDepart());
                cell.setCellStyle(style);

                cell = row.createCell(16);
                cell.setCellValue(null == model.getPreApproveScore()? "":model.getPreApproveScore().toString());
                cell.setCellStyle(style);

                cell = row.createCell(17);
                cell.setCellValue(null == model.getFinalApproveScore()? "":model.getFinalApproveScore().toString());
                cell.setCellStyle(style);

            }
        }
        return workbook;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ProjectApplicationCreateRequest request) {
        ProjectApplication entity = BeanUtilsUpgrade.copyObject(request, ProjectApplication.class);
        entity.setApproveStatus("0");
        entity.setTaskStatus("save");

        //自动编码
        if (apiUtilsService.sequenceRestEnable("ProjectApplicationNumCode")) {
            entity.setApplicationNum(apiUtilsService.generateCodeByCustomized("ProjectApplicationNumCode", null, null, null, entity));
            apiUtilsService.refershAutoNoByCustomized("ProjectApplicationNumCode", null, null, null, entity);
        }

        this.save(entity);

        //流程创建
        sysFileService.saveFileOrUpdate(entity.getId(), "项目申报", "0", request.getCreateId(), request.getCreateName(), "ProjectApplication", true, request.getTId(), request.getTCode());

        //附件
        if (ValidateUtil.isNotEmpty(entity.getApplicationFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getApplicationFileRelationId(), request.getApplicationFileIds(), entity.getId());
        }
        if (ValidateUtil.isNotEmpty(entity.getApproveFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getApproveFileRelationId(), request.getApproveFileIds(), entity.getId());
        }
        if (ValidateUtil.isNotEmpty(entity.getFinalApproveFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFinalApproveFileRelationId(), request.getFinalApproveFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(ProjectApplicationCreateRequest request) {
        ProjectApplication entity = BeanUtilsUpgrade.copyObject(request, ProjectApplication.class);
        entity.setCreateId(null);
        entity.setCreateName(null);
        this.updateById(entity);

        //附件
        if (ValidateUtil.isNotEmpty(entity.getApplicationFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getApplicationFileRelationId(), request.getApplicationFileIds(), entity.getId());
        }
        if (ValidateUtil.isNotEmpty(entity.getApproveFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getApproveFileRelationId(), request.getApproveFileIds(), entity.getId());
        }
        if (ValidateUtil.isNotEmpty(entity.getFinalApproveFileRelationId())) {
            sysFileAnnexService.refreshRelationFile(entity.getFinalApproveFileRelationId(), request.getFinalApproveFileIds(), entity.getId());
        }

        return entity.getId();
    }

    @Override
    public ProjectApplicationVO detail(ProjectApplicationCreateRequest request) {
        return this.baseMapper.queryDetailById(request.getId());
    }

    @Override
    public List<ProjectApplicationVO> listProjectApplicationByIds(List<String> ids) {
        return this.baseMapper.queryListDetailByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncApproveState(ProjectApplicationCreateRequest request) {
        SysFile sysFilePojo = sysFileService.getById(request.getId());
        ProjectApplication entity = this.getById(request.getId());
        if (sysFilePojo == null || entity == null) {
            return;
        }
        entity.setApproveStatus(sysFilePojo.getFileflowstatus());
        entity.setApproveTime(new Date());
        //更新任务状态
        if (StringUtils.isNotBlank(request.getActCode())) {
            entity.setTaskStatus(request.getActCode());
        }
        //退回发起人 已停止 已退回
        if ("2".equals(sysFilePojo.getFileflowstatus()) || "3".equals(sysFilePojo.getFileflowstatus()) || "9".equals(sysFilePojo.getFileflowstatus())) {
            entity.setTaskStatus("save");
        }

        this.updateById(entity);
    }

    @Override
    public void taskStatus(ProjectApplicationCreateRequest request) {
        ProjectApplication entity = new ProjectApplication();
        entity.setId(request.getId());
        entity.setTaskStatus(request.getTaskStatus());
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(ProjectApplicationCreateRequest request, String token) {
        FileStatusDto fileStatusDto = new FileStatusDto();
        fileStatusDto.setFileid(request.getId());
        fileStatusDto.setFilestatus(3);
        BaseResponse response = bpmFeign.Settingapprovalstatus(fileStatusDto, token);
        if (!response.getSuccess()) {
            throw new JPMException("删除失败");
        }
        // 已办删除
        taskProcessinMapper.delete(Wrappers.<TaskProcessin>lambdaQuery().eq(TaskProcessin::getTpEntityid, request.getId()));
        sysFileService.removeById(request.getId());
        this.removeById(request.getId());
    }
}
