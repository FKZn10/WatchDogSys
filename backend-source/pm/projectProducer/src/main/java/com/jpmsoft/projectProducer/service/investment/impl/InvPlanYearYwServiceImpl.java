package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearYwMapper;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYearYw;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFunds;
import com.jpmsoft.projectProducer.service.investment.IInvPlanYearService;
import com.jpmsoft.projectProducer.service.investment.InvMonthlyScheduleYwService;
import com.jpmsoft.projectProducer.service.investment.InvPlanYearYwService;
import com.jpmsoft.projectProducer.service.investment.ProjectPresetYearService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvPlanYearYwServiceImpl  extends ServiceImpl<InvPlanYearYwMapper, InvPlanYearYw> implements InvPlanYearYwService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private InvMonthlyScheduleYwService invMonthlyScheduleYwService;

    @Resource
    private ProjectPresetYearService projectPresetYearService;

    @Resource
    private IInvPlanYearService iInvPlanYearService;

    @Resource
    private IProSourcesFundsService iProSourcesFundsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvPlanYearYw(InvPlanYearRequest request) {
        InvPlanYear entity = new InvPlanYear();
        BeanUtils.copyProperties(request, entity);
        if (!checkProjectInvPlanYear(entity.getConId(), entity.getIpvDeclareYear(), entity.getIpvId()))
            return BaseResponse.fail("合同当前年度记录重复");

        entity.setIpvBudgetTotal(MathUtil.DecimalMultiplicationInt(entity.getIpvBudgetTotal(), 10000));
        entity.setIpvDoneLastYear(MathUtil.DecimalMultiplicationInt(entity.getIpvDoneLastYear(), 10000));
        entity.setIpvCarryOver(MathUtil.DecimalMultiplicationInt(entity.getIpvCarryOver(), 10000));
        entity.setIpvScheduledPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvScheduledPlan(), 10000));
        entity.setIpvReleasedPlan(MathUtil.DecimalMultiplicationInt(entity.getIpvReleasedPlan(), 10000));
        entity.setIpvTotalFunds(MathUtil.DecimalMultiplicationInt(entity.getIpvTotalFunds(), 10000));
        entity.setIpvPlannedInvestment(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedInvestment(), 10000));
        entity.setIpvPlannedPay(MathUtil.DecimalMultiplicationInt(entity.getIpvPlannedPay(), 10000));
        entity.setIpvCumulativePay(MathUtil.DecimalMultiplicationInt(entity.getIpvCumulativePay(), 10000));
        entity.setIpvAnnualInv(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualInv(), 10000));
        entity.setIpvAnnualPay(MathUtil.DecimalMultiplicationInt(entity.getIpvAnnualPay(), 10000));

        if (ValidateUtil.isEmpty(entity.getIpvId())) {
            entity.setIpvCreateTime(new Date());
            entity.setIpvId(GenerateUUID.getUUID());
            iInvPlanYearService.getBaseMapper().insert(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getIpvId());

        } else {
            InvPlanYear model = iInvPlanYearService.getBaseMapper().selectById(request.getIpvId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setIpvState(model.getIpvState());
            entity.setIpvStateTime(model.getIpvStateTime());
            entity.setIpvCreateTime(model.getIpvCreateTime());
            entity.setIpvCreateUid(model.getIpvCreateUid());
            entity.setIpvCreateUname(model.getIpvCreateUname());
            iInvPlanYearService.getBaseMapper().updateById(entity);
            sysFileService.saveFile(entity.getIpvId(), request.gettId(), entity.getIpvState() + "", entity.getIpvCreateUid(), entity.getIpvCreateUname()
                    , entity.getIpvCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getIpvId());

        }
        invMonthlyScheduleYwService.createOrUpdateInvMonthlyScheduleYw(request.getInvMonthlySchedules(), entity.getIpvId());
        return BaseResponse.ok(entity.getIpvId());
    }

    @Override
    public BaseResponse getInvPlanYearYwByIpvId(String ipvId) {
        InvPlanYearResponse invPlanYearYwByIpvId = baseMapper.getInvPlanYearYwByIpvId(ipvId);
        return BaseResponse.ok(invPlanYearYwByIpvId);
    }

    @Override
    public BaseResponse getInvPlanYearYwList(InvPlanYearRequest request, String token) {
        String uid = redisUtils.getUid(token);
        String authSql = sysUserService.getAuthSql(null, uid, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvPlanYearRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("ipvDId").put("ipvDeclareYear")
                .put("ipvConstStatus").put("ipvLibrary").put("pId").put("ipvState")
                .put("ipvNewly").put("ipvCompleted").put("ipvPType").put("pCompetentDepartment");
        IPage<InvPlanYearResponse> page = new Page<>(request.getPage(), request.getSize());
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    private boolean checkProjectInvPlanYear(String conId, String ipvDeclareYear, String ipvId) {
        QueryWrapper<InvPlanYear> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.eq("Ipv_Declare_Year", ipvDeclareYear);
        if (!ValidateUtil.isEmpty(ipvId))
            wrapper.ne("Ipv_Id", ipvId);
        wrapper.ne("Ipv_State", ApprovalStatusEnum.Invalid.getIndex());
        if (iInvPlanYearService.getBaseMapper().selectCount(wrapper) > 0)
            return false;
        else
            return true;
    }

    @Override
    public BaseResponse getInvPlanYearYwBypId(String pId, String year) {
        List<InvPlanYearResponse> data = baseMapper.getInvPlanYearYwBypId(pId, year);
        return BaseResponse.ok(data);
    }

    @Override
    public ResponseEntity<byte[]> getInvPlanYearYwListExcel(InvPlanYearRequest request, String token) throws UnsupportedEncodingException {
        String uid = redisUtils.getUid(token);
        String authSql = sysUserService.getAuthSql(null, uid, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql))
            return null;

        QueryMap<InvPlanYearRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("ipvDId").put("ipvDeclareYear")
                .put("ipvConstStatus").put("ipvLibrary").put("pId").put("ipvState")
                .put("ipvNewly").put("ipvCompleted").put("ipvPType").put("pCompetentDepartment");
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearYwListExcel(params.getQuery());
        List<String> ipvIds = datas.stream().map(e -> e.getIpvId()).collect(Collectors.toList());

        //出资方式
        List<ProSourcesFunds> fundsList = new ArrayList<>();
        List<String> pIds = datas.stream().map(e -> e.getpId()).collect(Collectors.toList());
        if(ValidateUtil.isNotEmpty(pIds)){
            fundsList  = iProSourcesFundsService.getProSourcesFundsPojoByPids(pIds);
        }

        if (ValidateUtil.isNotEmpty(ipvIds)) {
            List<InvMonthlyScheduleResponse> list = baseMapper.getInvMonthlyScheduleByIpvList(ipvIds);
            Map<String, List<InvMonthlyScheduleResponse>> ipvIdToMonthlySchedulesMap = list.stream()
                    .collect(Collectors.groupingBy(InvMonthlyScheduleResponse::getIpvId));
            datas = datas.stream()
                    .peek(data -> {
                        String ipvId = data.getIpvId();
                        List<InvMonthlyScheduleResponse> monthlySchedules = ipvIdToMonthlySchedulesMap.getOrDefault(ipvId, Collections.emptyList());
                        data.setInvMonthlySchedules(monthlySchedules);
                    })
                    .collect(Collectors.toList());
        }
        Map<String, List<InvPlanYearResponse>> projectGroup = datas.stream()
                .collect(Collectors.groupingBy(InvPlanYearResponse::getpId));

        Map<String, Map<String, List<InvPlanYearResponse>>> projectYearGroup = new HashMap<>();

        projectGroup.forEach((pId, responses) -> {
            Map<String, List<InvPlanYearResponse>> yearGroup = responses.stream()
                    .collect(Collectors.groupingBy(InvPlanYearResponse::getIpvDeclareYear));
            projectYearGroup.put(pId, yearGroup);
        });

        // 创建 ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter(true);
        List<String> headers = CollUtil.newArrayList(
                "序号", "项目名称", "建设单位", "管理单位", "出资方式",
                "出资比例", "是否有效投资","年度","合同名称","年度总投资（万元）",
                "年度总资金需求（万元）","年度总产值（万元）"
        );
        for (int i = 0; i < headers.size(); i++) {
            // 合并第0行和第1行的相同列，即纵向合并两行
            writer.merge(0, 1, i, i, headers.get(i), true);
        }
        org.apache.poi.ss.usermodel.Workbook workbook = writer.getWorkbook();

        // 添加月份列标题，并为每个月份添加一个空单元格
        for (int month = 1; month <= 12; month++) {
            headers.add(month + "月");
            headers.add("");  // 空单元格，用于后续合并
        }
        //第一个sheet的名字
         Sheet sheetOne = workbook.getSheetAt(0);
        workbook.setSheetName(workbook.getSheetIndex(sheetOne),"合同维度");


        writer.writeRow(headers);
        Sheet sheet = writer.getSheet();
        int rowIndex = 1;
        int projectIndex = 1;
        Row headerRow = sheet.getRow(0);
        // 创建并配置CellStyle，包括边框和居中对齐
        CellStyle styleWithBorder = workbook.createCellStyle();
        styleWithBorder.setAlignment(HorizontalAlignment.CENTER);
        styleWithBorder.setBorderTop(BorderStyle.THIN);
        styleWithBorder.setBorderBottom(BorderStyle.THIN);
        styleWithBorder.setBorderLeft(BorderStyle.THIN);
        styleWithBorder.setBorderRight(BorderStyle.THIN);

        for (int month = 1; month <= 12; month++) {
            int startCol = 12 + (month - 1) * 3;
            int endCol = startCol + 2;
            CellRangeAddress region = new CellRangeAddress(0, 0, startCol, endCol);
            sheet.addMergedRegion(region);
            // 设置合并区域中每个单元格的样式
            for (int col = startCol; col <= endCol; col++) {
                Cell cell = headerRow.createCell(col);
                if (col == startCol) {
                    cell.setCellValue(month + "月");
                }
                cell.setCellStyle(styleWithBorder);
            }
        }


        int l = 1;
        for (Map.Entry<String, Map<String, List<InvPlanYearResponse>>> pEntry : projectYearGroup.entrySet()) {
            String pId = pEntry.getKey();
            Map<String, List<InvPlanYearResponse>> yearGroup = pEntry.getValue();
            for (Map.Entry<String, List<InvPlanYearResponse>> entry : yearGroup.entrySet()) {
                List<InvPlanYearResponse> projectData = entry.getValue();
                // 记录每个项目的起始行号
                int startRow = rowIndex;  // 行号从当前行开始
                for (int i = 0; i < projectData.size(); i++) {
                    InvPlanYearResponse data = projectData.get(i);
                    if(ValidateUtil.isEmpty(data.getIpvLibrary())){
                        data.setIpvLibrary(false);
                    }
                    if(i == 0  && l > 0 ){
                        //特殊展示。
                        List<Object> emptyRow = CollUtil.newArrayList("", "", "","", "", "","","","","","","","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                                "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                                "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资"
                                , "资金需求", "产值","投资", "资金需求", "产值","投资");
                        writer.writeRow(emptyRow);  // 在空白行位置插入数据
                        l = 0;
                    }
                    List<Object> row = new ArrayList<>();

                    if(ValidateUtil.isNotNull(data)){
                        row = CollUtil.newArrayList(
                                projectIndex,  // 项目序号
                                data.getPName(),  // 项目名称
                                data.getPUseUnit(),  // 建设单位
                                data.getpCompetentDepartmentName(), // 管理单位
                                getSourcesFunds(data.getpId(), fundsList, 1),  // 出资方式
                                getSourcesFunds(data.getpId(), fundsList, 0),  // 出资比例
                                data.getIpvLibrary() ? "是" : "否",  // 是否有效投资项目（是/否）
                                data.getIpvDeclareYear(),  // 年度
                                data.getConName(),
                                MathUtil.decimalDivide(data.getIpvCumulativePay(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY),  // 年度总投资（万元）
                                MathUtil.decimalDivide(data.getIpvAnnualInv(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY),
                                MathUtil.decimalDivide(data.getIpvAnnualPay(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY)
                        );
                        // 添加每月资金、累计投资和累计支付数据
                        for (int month = 0; month <= 11; month++) {
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthlyFunding(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthlyCumulativeInv(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthImsCumulativePay(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                        }

                    }
                    writer.writeRow(row);
                    rowIndex++;
                }

                // 计算结束行号
                int endRow = rowIndex - 1;  // 因为 rowIndex 已经递增，所以减 1

                //合并
                if (projectData.size() > 1) {
                    boolean isSameProjectName = projectData.stream().allMatch(data -> data.getPName().equals(projectData.get(0).getPName()));
                    if (isSameProjectName && startRow < endRow) {
                        for (int i = 0; i <= 7; i++) {
                            mergeCells(sheet, startRow + 1, endRow + 1, i);  //如果是合并行给他起始结束都加1
                        }
                    }
                }

                projectIndex++;
            }
        }

        //特殊展示。
        if(projectYearGroup.entrySet().size() == 0){
            List<Object> emptyRow = CollUtil.newArrayList("", "", "","", "", "","","","","","","","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                    "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                    "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资"
                    , "资金需求", "产值","投资", "资金需求", "产值","投资");
            writer.writeRow(emptyRow);  // 在空白行位置插入数据
        }
        // 设置列宽自适应
        List<Integer> customColumnWidths = Arrays.asList(10,
                60,50,40,40,30,15,10,60,20,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20);

        // 设置每列的宽度
        setCustomColumnWidths(sheet, customColumnWidths);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.flush(baos);
        byte[] excelBytes = baos.toByteArray();
        String fileName = "logs.xlsx";
        // 设置响应头

        HttpHeaders headers11 = new HttpHeaders();
        headers11.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        headers11.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers11)
                .body(excelBytes);
    }


    //义乌年度投资控制项目维度
    @Override
    public ResponseEntity<byte[]> getInvPlanYearYwPreListExcel(InvPlanYearRequest request, String token)  throws UnsupportedEncodingException {
        String uid = redisUtils.getUid(token);
        String authSql = sysUserService.getAuthSql(null, uid, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql))
            return null;

        QueryMap<InvPlanYearRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("ipvDId").put("ipvDeclareYear")
                .put("ipvConstStatus").put("ipvLibrary").put("pId").put("ipvState")
                .put("ipvNewly").put("ipvCompleted").put("ipvPType").put("pCompetentDepartment");
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearYwListExcel(params.getQuery());
        List<String> ipvIds = datas.stream().map(e -> e.getIpvId()).collect(Collectors.toList());

        //出资方式
        List<ProSourcesFunds> fundsList = new ArrayList<>();
        List<String> pIds = datas.stream().map(e -> e.getpId()).collect(Collectors.toList());
        if(ValidateUtil.isNotEmpty(pIds)){
            fundsList  = iProSourcesFundsService.getProSourcesFundsPojoByPids(pIds);
        }

        if (ValidateUtil.isNotEmpty(ipvIds)) {
            List<InvMonthlyScheduleResponse> list = baseMapper.getInvMonthlyScheduleByIpvList(ipvIds);
            Map<String, List<InvMonthlyScheduleResponse>> ipvIdToMonthlySchedulesMap = list.stream()
                    .collect(Collectors.groupingBy(InvMonthlyScheduleResponse::getIpvId));
            datas = datas.stream()
                    .peek(data -> {
                        String ipvId = data.getIpvId();
                        List<InvMonthlyScheduleResponse> monthlySchedules = ipvIdToMonthlySchedulesMap.getOrDefault(ipvId, Collections.emptyList());
                        data.setInvMonthlySchedules(monthlySchedules);
                    })
                    .collect(Collectors.toList());
        }
        Map<String, List<InvPlanYearResponse>> projectGroup = datas.stream()
                .collect(Collectors.groupingBy(InvPlanYearResponse::getpId));

        Map<String, Map<String, List<InvPlanYearResponse>>> projectYearGroup = new HashMap<>();

        projectGroup.forEach((pId, responses) -> {
            Map<String, List<InvPlanYearResponse>> yearGroup = responses.stream()
                    .collect(Collectors.groupingBy(InvPlanYearResponse::getIpvDeclareYear));
            projectYearGroup.put(pId, yearGroup);
        });


        // 遍历并处理数据
        for (Map.Entry<String, Map<String, List<InvPlanYearResponse>>> projectIdEntry : projectYearGroup.entrySet()) {

            Map<String, List<InvPlanYearResponse>> yearMap = projectIdEntry.getValue();
            for (Map.Entry<String, List<InvPlanYearResponse>> yearEntry : yearMap.entrySet()) {
                String year = yearEntry.getKey();
                List<InvPlanYearResponse> responses = yearEntry.getValue();

                if (responses.size() > 1) { // 如果该年度有多于一个响应
                    InvPlanYearResponse mergedResponse = mergeResponses(responses);
                    // 替换原列表为仅包含合并后的单个对象
                    yearEntry.setValue(Arrays.asList(mergedResponse));
                }
            }
        }


        // 创建 ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter(true);
        List<String> headers = CollUtil.newArrayList(
                "序号", "项目名称", "建设单位", "管理单位", "出资方式",
                "出资比例", "是否有效投资","年度","年度总投资（万元）",
                "年度总资金需求（万元）","年度总产值（万元）","合同名称"
        );
        for (int i = 0; i < headers.size(); i++) {
            // 合并第0行和第1行的相同列，即纵向合并两行
            writer.merge(0, 1, i, i, headers.get(i), true);
        }
        org.apache.poi.ss.usermodel.Workbook workbook = writer.getWorkbook();

        // 添加月份列标题，并为每个月份添加一个空单元格
        for (int month = 1; month <= 12; month++) {
            headers.add(month + "月");
            headers.add("");  // 空单元格，用于后续合并
        }
        //第一个sheet的名字
        Sheet sheetOne = workbook.getSheetAt(0);
        workbook.setSheetName(workbook.getSheetIndex(sheetOne),"合同维度");


        writer.write(headers);
        Sheet sheet = writer.getSheet();
        int rowIndex = 1;
        int projectIndex = 1;
        Row headerRow = sheet.getRow(0);
        // 创建并配置CellStyle，包括边框和居中对齐
        CellStyle styleWithBorder = workbook.createCellStyle();
        styleWithBorder.setAlignment(HorizontalAlignment.CENTER);
        styleWithBorder.setBorderTop(BorderStyle.THIN);
        styleWithBorder.setBorderBottom(BorderStyle.THIN);
        styleWithBorder.setBorderLeft(BorderStyle.THIN);
        styleWithBorder.setBorderRight(BorderStyle.THIN);

        for (int month = 1; month <= 12; month++) {
            int startCol = 12 + (month - 1) * 3;
            int endCol = startCol + 2;
            CellRangeAddress region = new CellRangeAddress(0, 0, startCol, endCol);
            sheet.addMergedRegion(region);
            // 设置合并区域中每个单元格的样式
            for (int col = startCol; col <= endCol; col++) {
                Cell cell = headerRow.createCell(col);
                if (col == startCol) {
                    cell.setCellValue(month + "月");
                }
                cell.setCellStyle(styleWithBorder);
            }
        }


        int l = 1;
        for (Map.Entry<String, Map<String, List<InvPlanYearResponse>>> pEntry : projectYearGroup.entrySet()) {
            Map<String, List<InvPlanYearResponse>> yearGroup = pEntry.getValue();
            for (Map.Entry<String, List<InvPlanYearResponse>> entry : yearGroup.entrySet()) {
                List<InvPlanYearResponse> projectData = entry.getValue();
                // 记录每个项目的起始行号
                int startRow = rowIndex;  // 行号从当前行开始
                for (int i = 0; i < projectData.size(); i++) {
                    InvPlanYearResponse data = projectData.get(i);
                    if(ValidateUtil.isEmpty(data.getIpvLibrary())){
                        data.setIpvLibrary(false);
                    }
                    if(i == 0  && l > 0 ){
                        //特殊展示。
                        List<Object> emptyRow = CollUtil.newArrayList("", "", "","", "", "","","","","","","","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                                "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                                "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资"
                                , "资金需求", "产值","投资", "资金需求", "产值","投资");
                        writer.writeRow(emptyRow);  // 在空白行位置插入数据
                        l = 0;
                    }
                    List<Object> row = null;

                    if(ValidateUtil.isNotNull(data)){
                        row = CollUtil.newArrayList(
                                projectIndex,  // 项目序号
                                data.getPName(),  // 项目名称
                                data.getPUseUnit(),  // 建设单位
                                data.getpCompetentDepartmentName(), // 管理单位
                                getSourcesFunds(data.getpId(), fundsList, 1),  // 出资方式
                                getSourcesFunds(data.getpId(), fundsList, 0),  // 出资比例
                                data.getIpvLibrary() ? "是" : "否",  // 是否有效投资项目（是/否）
                                data.getIpvDeclareYear(),  // 年度
                                MathUtil.decimalDivide(data.getIpvCumulativePay(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY),  // 年度总投资（万元）
                                MathUtil.decimalDivide(data.getIpvAnnualInv(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY),
                                MathUtil.decimalDivide(data.getIpvAnnualPay(), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY),
                                data.getConName()
                        );
                        // 添加每月资金、累计投资和累计支付数据
                        for (int month = 0; month <= 11; month++) {
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthlyFunding(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthlyCumulativeInv(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                            row.add(MathUtil.decimalDivide((BigDecimal) getMonthImsCumulativePay(data.getInvMonthlySchedules(), month), new BigDecimal(10000), 6, RoundingMode.UNNECESSARY));
                        }

                    }else{
                        row = new ArrayList<>();
                    }
                    writer.write(row);
                    rowIndex++;
                    projectIndex++;
                }

                // 计算结束行号
                int endRow = rowIndex - 1;  // 因为 rowIndex 已经递增，所以减 1

                //合并
//                if (projectData.size() > 1) {
//                    boolean isSameProjectName = projectData.stream().allMatch(data -> data.getPName().equals(projectData.get(0).getPName()));
//                    if (isSameProjectName && startRow < endRow) {
//                        for (int i = 0; i <= 7; i++) {
//                            mergeCells(sheet, startRow + 1, endRow + 1, i);  //如果是合并行给他起始结束都加1
//                        }
//                    }
//                }
            }
        }

        //特殊展示。
        if(projectYearGroup.entrySet().size() == 0){
            List<Object> emptyRow = CollUtil.newArrayList("", "", "","", "", "","","","","","","","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                    "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求", "产值","投资", "资金需求",
                    "产值", "投资","资金需求", "产值","投资", "资金需求", "产值","投资"
                    , "资金需求", "产值","投资", "资金需求", "产值","投资");
            writer.writeRow(emptyRow);  // 在空白行位置插入数据
        }
        // 设置列宽自适应
        List<Integer> customColumnWidths = Arrays.asList(10,
                60,50,40,40,30,15,10,60,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20);

        // 设置每列的宽度
        setCustomColumnWidths(sheet, customColumnWidths);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.flush(baos);
        byte[] excelBytes = baos.toByteArray();
        String fileName = "logs.xlsx";
        // 设置响应头

        HttpHeaders headers11 = new HttpHeaders();
        headers11.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        headers11.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers11)
                .body(excelBytes);
    }


    private static InvPlanYearResponse mergeResponses(List<InvPlanYearResponse> responses) {
        BigDecimal cumulativePaySum = BigDecimal.ZERO;
        BigDecimal annualInvSum = BigDecimal.ZERO;
        BigDecimal annualPaySum = BigDecimal.ZERO;
        Map<Integer, InvMonthlyScheduleResponse> monthlySchedulesMap = new HashMap<>();

        for (InvPlanYearResponse response : responses) {
            cumulativePaySum = cumulativePaySum.add(response.getIpvCumulativePay() != null ? response.getIpvCumulativePay() : BigDecimal.ZERO);
            annualInvSum = annualInvSum.add(response.getIpvAnnualInv() != null ? response.getIpvAnnualInv() : BigDecimal.ZERO);
            annualPaySum = annualPaySum.add(response.getIpvAnnualPay() != null ? response.getIpvAnnualPay() : BigDecimal.ZERO);
            for (InvMonthlyScheduleResponse monthlySchedule : response.getInvMonthlySchedules()) {
                int month = monthlySchedule.getImsMonth(); // 假设有一个方法返回月份
                monthlySchedulesMap.merge(month, monthlySchedule, (existing, newSchedule) -> {
                    existing.setImsPlannedInv(existing.getImsPlannedInv().add(newSchedule.getImsPlannedInv()));
                    existing.setImsCumulativeInv(existing.getImsCumulativeInv().add(newSchedule.getImsCumulativeInv()));
                    existing.setImsCumulativePay(existing.getImsCumulativePay().add(newSchedule.getImsCumulativePay()));
                    return existing;
                });
            }
        }

        // 创建一个新的 InvPlanYearResponse 对象或选择第一个作为模板进行修改
        InvPlanYearResponse firstResponse = responses.get(0);
        InvPlanYearResponse mergedResponse = new InvPlanYearResponse();
        BeanUtils.copyProperties(firstResponse,mergedResponse);
        mergedResponse.setIpvCumulativePay(cumulativePaySum);
        mergedResponse.setIpvAnnualInv(annualInvSum);
        mergedResponse.setIpvAnnualPay(annualPaySum);
        mergedResponse.setInvMonthlySchedules(new ArrayList<>(monthlySchedulesMap.values()));

        return mergedResponse;
    }

    public  String  getSourcesFunds(String pId, List<ProSourcesFunds> fundsList, Integer is){
        String str  = "";
        if (is.equals(1)){
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_XMZJLY");
            selectDto.setCodes(codes);
            BaseResponse dictionaryCodes = userProducerFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
            if(!CollectionUtils.isEmpty(fundsList)){
                List<String> collect = fundsList.stream().filter(e -> e.getPId().equals(pId)).map(ProSourcesFunds::getFsCode).collect(Collectors.toList());
                StringBuilder strBuilder = new StringBuilder();
                for (String s : collect) {
                    if (strBuilder.length() > 0) {
                        strBuilder.append(", ");
                    }
                    strBuilder.append(getValueByTitleCli(originalData.get("JPMsoft_XMZJLY"), s));
                }
                str = strBuilder.toString();
            }else{
                str = "";
            }
        }else{
            str = fundsList.stream().filter(e -> e.getPId().equals(pId) && ValidateUtil.isNotNull(e.getFsScale())).map(ProSourcesFunds::getFsScale).map(f-> f.toString() + "%").collect(Collectors.joining(","));
        }

        return  str;


    }


    //获取每个月的资金资金需求
    private BigDecimal getMonthlyFunding(List<InvMonthlyScheduleResponse> schedules, int monthIndex) {
        if (schedules == null || monthIndex >= schedules.size()) {
            return BigDecimal.ZERO;
        }
        InvMonthlyScheduleResponse schedule = schedules.get(monthIndex);
        return schedule != null ? schedule.getImsPlannedInv() : BigDecimal.ZERO;
    }
    private BigDecimal getMonthlyCumulativeInv(List<InvMonthlyScheduleResponse> schedules, int monthIndex) {
        if (schedules == null || monthIndex >= schedules.size()) {
            return  BigDecimal.ZERO;
        }
        InvMonthlyScheduleResponse schedule = schedules.get(monthIndex);
        return schedule != null ? schedule.getImsCumulativeInv() :  BigDecimal.ZERO;
    }
    //获取每个月的投资
    private BigDecimal getMonthImsCumulativePay(List<InvMonthlyScheduleResponse> schedules, int monthIndex) {
        if (schedules == null || monthIndex >= schedules.size()) {
            return  BigDecimal.ZERO;
        }
        InvMonthlyScheduleResponse schedule = schedules.get(monthIndex);
        return schedule != null ? schedule.getImsCumulativePay() :  BigDecimal.ZERO;
    }

    private void setCustomColumnWidths(Sheet sheet, List<Integer> columnWidths) {
        // 遍历所有列宽设置
        for (int i = 0; i < columnWidths.size(); i++) {
            int width = columnWidths.get(i);

            // 设置每列的宽度
            sheet.setColumnWidth(i, width * 256);
        }
    }

    // 手动添加合并区域
    private void mergeCells(Sheet sheet, int startRow, int endRow, int colIndex) {
        CellRangeAddress cellRange = new CellRangeAddress(startRow, endRow, colIndex, colIndex);
        sheet.addMergedRegion(cellRange);
    }


    // 设置自适应列宽
    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            // 设置列宽为自适应
            sheet.autoSizeColumn(i);

            // 获取当前列的宽度
            int currentColumnWidth = sheet.getColumnWidth(i);

            // 设置最小宽度为 40 个字符（可以根据需要调整）
            int minimumWidth = 40 * 256;

            // 如果当前列宽小于最小宽度，则设置为最小宽度
            if (currentColumnWidth < minimumWidth) {
                sheet.setColumnWidth(i, minimumWidth);
            }

            // 设置最大宽度为 180 个字符（可以根据需要调整）
            int maximumWidth = 180 * 256;
            if (currentColumnWidth > maximumWidth) {
                sheet.setColumnWidth(i, maximumWidth);
            }
        }
    }


    @Override
    public BaseResponse getInvPlanYearSummaryYwList(InvPlanYearRequest request, String token) {
        String uid = redisUtils.getUid(token);
        String authSql = sysUserService.getAuthSql(null, uid, "Ipv_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Ipv_State");
        if (ValidateUtil.isEmpty(authSql)){
            return BaseResponse.fail(ResMsg.noPermission);
        }

        QueryMap<InvPlanYearRequest> params1 = new QueryMap<>(request);
        params1.put("authSql", authSql).put("pName").put("ipvDId").put("ipvDeclareYear")
                .put("ipvConstStatus").put("ipvLibrary").put("pId").put("ipvState")
                .put("ipvNewly").put("ipvCompleted").put("ipvPType").put("pCompetentDepartment").put("ipvCName");
        IPage<InvPlanYearResponse> page = new Page<>(request.getPage(),request.getSize());
        List<InvPlanYearResponse> datas = baseMapper.getInvPlanYearSummaryYwGroupById(page, params1.getQuery());
        List<String> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(datas)){
            ids = datas.stream().map(InvPlanYearResponse::getpId).collect(Collectors.toList());
        }
        List<Map<String, Object>> groupedData = null;
        if(!CollectionUtils.isEmpty(ids)){
            List<InvPlanYearResponse> dataList = baseMapper.getInvPlanYearSummaryYwList(params1.getQuery(),ids);
            if(!CollectionUtils.isEmpty(dataList)){
                groupedData = dataList.stream()
                        .collect(Collectors.groupingBy(
                                InvPlanYearResponse::getPName // 先按项目名称分组
                        ))
                        .entrySet().stream()
                        .map(entry -> {
                            String projectName = entry.getKey();
                            List<InvPlanYearResponse> projectData = entry.getValue();

                            // 按年度（year）分组
                            Map<String, List<InvPlanYearResponse>> yearGroup = projectData.stream()
                                    .collect(Collectors.groupingBy(InvPlanYearResponse::getIpvDeclareYear));

                            // 将每个年度的数据整理成所需的格式
                            List<Map<String, Object>> years = yearGroup.entrySet().stream()
                                    .map(yearEntry -> {
                                        String year = yearEntry.getKey();
                                        List<InvPlanYearResponse> yearData = yearEntry.getValue();

                                        // 提取每个年度的详细信息
                                        Map<String, Object> yearInfo = new HashMap<>();
                                        yearInfo.put("year", year);
                                        yearInfo.put("touzi", yearData.get(0).getPyInvestmentAmount());
                                        yearInfo.put("jieduan", yearData.get(0).getIpvConstructionStage());
                                        yearInfo.put("createTime", yearData.get(0).getIpvCreateTime());
                                        yearInfo.put("createUname", yearData.get(0).getIpvCreateUname());
                                        yearInfo.put("state", yearData.get(0).getIpvState());
                                        yearInfo.put("createUid", yearData.get(0).getIpvCreateUid());

                                        // 获取合同信息
                                        List<Map<String, Object>> contracts = yearData.stream()

                                                .map(contract -> {
                                                    Map<String, Object> contractInfo = new HashMap<>();
                                                    contractInfo.put("conName", contract.getConName());
                                                    contractInfo.put("jihuatouzi", contract.getIpvAnnualInv());
                                                    contractInfo.put("danwei", contract.getIpvCName());
                                                    contractInfo.put("ipvId", contract.getIpvId());
                                                    contractInfo.put("ipvAnnualPay",contract.getIpvAnnualPay());
                                                    return contractInfo;
                                                })
                                                .collect(Collectors.toList());

                                        yearInfo.put("hetong", contracts);

                                        return yearInfo;
                                    })
                                    .collect(Collectors.toList());

                            // 最终结果
                            Map<String, Object> projectInfo = new HashMap<>();
                            projectInfo.put("pName", projectName);
                            projectInfo.put("years", years);

                            return projectInfo;
                        })
                        .collect(Collectors.toList());
            }
        }


        return BaseResponse.ok(groupedData,(int)page.getTotal());
    }


    public static String getValueByTitleCli(List<LinkedHashMap<String, Object>> dataList, String title) {
        if (dataList != null) {
            for (LinkedHashMap<String, Object> item : dataList) {
                // 检查当前层级的 "value" 是否与要找的值相等
                if(ValidateUtil.isNotNull(title)){
                    if(ValidateUtil.isNotNull(item.get("value"))){
                        if (title.equals(item.get("value"))) {
                            return String.valueOf(item.get("title"));
                        }
                    }
                }
                // 如果当前项有 "children" 并且是 List 类型，则递归检查
                Object childrenObj = item.get("children");
                if (ValidateUtil.isNotNull(childrenObj) && childrenObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) childrenObj;
                    // 递归查找子项
                    String result = getValueByTitleCli(children, title);
                    if (result != null && !result.equals(title)) {
                        return result;
                    }
                }
            }
        }
        return title;
    }
}