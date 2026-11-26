package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.mapper.PreProjectMapper;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.service.IApiUtilsService;
import com.jpmsoft.fileUpload.service.IPreProjectService;
import com.jpmsoft.fileUpload.service.ISysUserService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PreProjectImpl extends ServiceImpl<PreProjectMapper, PreProject> implements IPreProjectService {

    /**
     * 省试点项目-项目类型 字典父编码
     */
    private static final String DICT_PROJECT_TYPE = "JPMsoft_ZPXMLX";

    /**
     * 省试点项目-所在区 字典父编码
     */
    private static final String DICT_DISTRICT = "JPMsoft_ZPSZQ";

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IApiUtilsService apiUtilsService;

    @Autowired
    private PmFeign pmFeign;

    @Autowired
    private UserFeign userFeign;

    @Resource
    private com.jpmsoft.fileUpload.service.ISysDictionaryService dictService;


    @Override
    public List<Map> getPreProjectByIds(List<SysTableColumn> columns, List<String> ids) {
        return getTableDataByIds(columns,ids,"Pre_Project","P_Id");
    }

    @Override
    public List<Map> getContractByIds(List<SysTableColumn> columns, List<String> ids) {
        return getTableDataByIds(columns,ids,"Con_Contract","Con_Id");
    }


    public List<Map> getTableDataByIds(List<SysTableColumn> columns, List<String> ids,String tableName,String pk) {
        if (CollectionUtils.isEmpty(ids)) return null;
        List<SysTableColumn> projectCm = columns.stream().filter(f -> ValidateUtil.isNotEmpty(f.getAssocTableCode())
                && f.getAssocTableCode().equalsIgnoreCase(tableName)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(projectCm)) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        List<String> sqlCm = new ArrayList<>();
        for (SysTableColumn c : projectCm) {
            if (ValidateUtil.isNotEmpty(c.getAssocColumnCode()) && !sqlCm.contains(c.getAssocColumnCode())) {
                sb.append(c.getAssocColumnCode().toUpperCase(Locale.ENGLISH)).append(",");
                sqlCm.add(c.getAssocColumnCode());
            }
            if (ValidateUtil.isNotEmpty(c.getAssocShowCode()) && !sqlCm.contains(c.getAssocShowCode())) {
                sb.append(c.getAssocShowCode().toUpperCase(Locale.ENGLISH)).append(",");
                sqlCm.add(c.getAssocColumnCode());
            }
        }
        sb.append("1").append(" from ").append(tableName).append(" where ").append(pk).append(" in ");
        return baseMapper.getPreProjectByIds(sb.toString(), ids);
    }

    @Override
    public List<PreProject> getPreProjectByCodes(List<String> codes) {
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Code", codes.get(0));
        if (codes.size() > 1) {
            for (int i = 1; i < codes.size(); i++) {
                wrapper.or().eq("P_Code", codes.get(i));
            }
        }
        List<PreProject> pojos = baseMapper.selectList(wrapper);
        return pojos;
    }

    @Override
    public List<PreProject> getApprovaledPreProjectByCodes(List<String> codes) {
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.eq("State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.and(wr -> {
            wr.eq("P_Code", codes.get(0));
            if (codes.size() > 1) {
                for (int i = 1; i < codes.size(); i++) {
                    wr.or().eq("P_Code", codes.get(i));
                }
            }
        });
        List<PreProject> pojos = baseMapper.selectList(wrapper);
        return pojos;
    }


    /**
     * @param uId
     * @param type 1周报 2月报 3年度计划
     * @return
     */
    private List<PreProjectResponse> getAllProjectList(String uId, Integer type) {
        PreProjectRequest request = new PreProjectRequest();
        request.setState(ApprovalStatusEnum.Already_Over.getIndex());
        if (type != 3) {
            request.setpProjectStage("02");
            request.setConstructionFilter(Boolean.TRUE);
        }
        String json = JacksonUtils.toJSONString(apiUtilsService.getAllProjectList(request, uId));
        List<PreProjectResponse> datas = JacksonUtils.parseArray(json, PreProjectResponse.class);
        return datas;
    }

    @Override
    public BaseResponse assignOaProReportPid(List<OaProjectReportModel> datas, String uId, Integer type) {
        List<String> codes = new ArrayList<>();
        datas.forEach(data -> {
            codes.add(data.getpId());
        });
        if (codes.size() > 0) {
            if (type == 1) {
                List<PreProject> pros = getApprovaledPreProjectByCodes(codes);
                SysUser user = sysUserService.getById(uId);
                List<OaProjectReportModel> models = new ArrayList<>();
                for (OaProjectReportModel data : datas) {
                    if (!ValidateUtil.isEmpty(data.getpId())) {
                        List<PreProject> pro = pros.stream().filter(n -> n.getpCode().equals(data.getpId())).collect(Collectors.toList());
                        if (pro != null && pro.size() > 0) {
                            PreProject p = pro.stream().findFirst().get();
                            data.setpId(p.getpId());
                            data.setPName(p.getpName());
                            data.setPrCreateUid(uId);
                            data.setPrCreateUname(user.getuName());
                            data.setPCode(p.getpCode());
                            models.add(data);
                        }
                    }
                }
                return new BaseResponse(true, FileMsg.actSuccess, models);
            } else {
                List<String> pIds = datas.stream().map(OaProjectReportModel::getpId).distinct().collect(Collectors.toList());
                if (CollectionUtils.isEmpty(pIds))
                    return BaseResponse.fail(FileMsg.lackProInfo);
                List<PreProjectResponse> pros = getAllProjectList(uId, type);
                SysUser user = sysUserService.getById(uId);
                List<OaProjectReportModel> models = new ArrayList<>();
                for (OaProjectReportModel data : datas) {
                    if (!ValidateUtil.isEmpty(data.getpId())) {
                        List<PreProjectResponse> pro = pros.stream().filter(n -> ValidateUtil.isNotEmpty(n.getpCode())
                                && n.getpCode().equals(data.getpId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(pro)) {
                            PreProjectResponse p = pro.stream().findFirst().get();
                            data.setpId(p.getpId());
                            data.setPName(p.getpName());
                            data.setPrCreateUid(uId);
                            data.setPrCreateUname(user.getuName());
                            data.setPCode(p.getpCode());
                            models.add(data);
                        }
                    }
                }
                return new BaseResponse(true, FileMsg.actSuccess, models);
            }
        }
        return new BaseResponse(true, FileMsg.codeIsEmpty);
    }

    @Override
    public List<ProProjectCodeDto> getPidByProjectCodes(List<String> pProjectCodes, List<String> pCodes) {
        if (CollectionUtils.isEmpty(pCodes) && CollectionUtils.isEmpty(pProjectCodes)) return null;
        return baseMapper.getPidByProjectCodes(CollectionUtils.isEmpty(pProjectCodes) ? null : pProjectCodes, CollectionUtils.isEmpty(pCodes) ? null : pCodes);
    }

    @Override
    public Boolean havingOaQuarterlyReport(Integer year, Integer quarter) {
        Integer count = baseMapper.havingOaQuarterlyReport(year, quarter);
        if (count != null && count > 0) return true;
        return false;
    }

    @Override
    public ResponseEntity<byte[]> getProjectListExcel(PreProjectRequest request, String token, HttpServletResponse response) {
        try {
            List<PreProjectResponse> preList = pmFeign.getProjectListExcel(request, token);
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_XMLB");  //项目类别
            codes.add("JPMsoft_XMJD");  //项目阶段
            codes.add("JPMsoft_XMZJLY"); //资金来源
            codes.add("JPMsoft_LCZT"); //流程状态
            codes.add("JPMsoft_XMStatus"); //项目进展
            selectDto.setCodes(codes);
             BaseResponse dictionaryCodes = userFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
            List<preProjectDTO> list =  preList.stream().map(info ->{
                preProjectDTO dto = new preProjectDTO();
                BeanUtils.copyProperties(info, dto);
               String startTime =  DateUtil.format(dto.getPConstructionStart(),"yyyy-MM");
                String EndTime =  DateUtil.format(dto.getPConstructionEnd(),"yyyy-MM");
                if(ValidateUtil.isNull(startTime)){
                    startTime = "";
                }
                if(ValidateUtil.isNull(EndTime)){
                    EndTime = "";
                }
                if(ValidateUtil.isEmpty(startTime) && ValidateUtil.isEmpty(EndTime)){
                    dto.setPConstructionTime("");
                }else{
                    dto.setPConstructionTime(startTime + "——" + EndTime);
                }

                if(ValidateUtil.isNotNull(dto.getPIsProjectApproval()) && dto.getPIsProjectApproval()){
                    dto.setPIsProjectApprovalxs("是");
                }else{
                    dto.setPIsProjectApprovalxs("否");
                }
                dto.setPProjectStage(getValueByTitleCli(originalData.get("JPMsoft_XMJD"),dto.getPProjectStage()));
                dto.setPType(getValueByTitleCli(originalData.get("JPMsoft_XMLB"),dto.getPType()));
                dto.setPSourcesFunds(getValueByTitleCli(originalData.get("JPMsoft_XMZJLY"),dto.getPSourcesFunds()));
                dto.setStateName(getValueByTitleCli(originalData.get("JPMsoft_LCZT"),String.valueOf(dto.getState())));
                if(ValidateUtil.isNotNull(dto.getPProjectStatus())){
                    dto.setPProjectStatus(getValueByTitleCli(originalData.get("JPMsoft_XMStatus"),String.valueOf(dto.getPProjectStatus())));
                }else{
                    dto.setPProjectStatus("");
                }
                return dto;
            }).collect(Collectors.toList());


            ByteArrayOutputStream out = new ByteArrayOutputStream();

            WriteCellStyle headStyle = new WriteCellStyle();
//            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
//            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, preProjectDTO.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                    .sheet("preProjectDTO")
                    .doWrite(list);


            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=xmbs.xlsx");

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<byte[]> getContractListExcel(ConContractRequest request, String token, HttpServletResponse response) {
        try {
//            List<PreProjectResponse> preList = pmFeign.getContractListExcel(request, token);
            List<ConContractResponse> conList = pmFeign.getContractListExcel(request, token);

//            List<ConContractResponse>  conList =JSONUtil.toList(JSONUtil.parseArray(contractListExcel.getData()),ConContractResponse.class);
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_HTLX");  //合同类型
            codes.add("JPMsoft_LCZT"); //流程状态
            selectDto.setCodes(codes);
            BaseResponse dictionaryCodes = userFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
            if(ValidateUtil.isEmpty(request.getConCategory())){
                List<conTractSumDTO> list =  conList.stream().map(info ->{
                    conTractSumDTO dto = new conTractSumDTO();
                    BeanUtils.copyProperties(info, dto);
                    dto.setConDateTime(DateUtil.format(dto.getConDate(),"yyyy-MM-dd"));
                    dto.setConType(getValueByTitleCli(originalData.get("JPMsoft_HTLX"),dto.getConType()));
                    dto.setState(getValueByTitleCli(originalData.get("JPMsoft_LCZT"),String.valueOf(dto.getConApprovalState())));
                    return dto;
                }).collect(Collectors.toList());

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                WriteCellStyle headStyle = new WriteCellStyle();
//            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

                WriteFont headFont = new WriteFont();
                headFont.setFontHeightInPoints((short) 12);
                headFont.setFontName("Arial");
                headStyle.setWriteFont(headFont);

                WriteCellStyle contentStyle = new WriteCellStyle();
//            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

                WriteFont contentFont = new WriteFont();
                contentFont.setFontHeightInPoints((short) 10);
                contentFont.setFontName("Arial");
                contentStyle.setWriteFont(contentFont);

                HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);
                // 1用conTractDTO   1用conTractDTO
                EasyExcel.write(out, conTractSumDTO.class)
                        .registerWriteHandler(styleStrategy)
                        .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                        .sheet("conTractDTO")
                        .doWrite(list);

                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=ht.xlsx");

                response.getOutputStream().write(out.toByteArray());
                response.getOutputStream().flush();
                response.getOutputStream().close();

                return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
            }else{
                List<conTractDTO> list =  conList.stream().map(info ->{
                    conTractDTO dto = new conTractDTO();
                    BeanUtils.copyProperties(info, dto);
                    dto.setConDateTime(DateUtil.format(dto.getConDate(),"yyyy-MM-dd"));
                    dto.setConType(getValueByTitleCli(originalData.get("JPMsoft_HTLX"),dto.getConType()));
                    dto.setState(getValueByTitleCli(originalData.get("JPMsoft_LCZT"),String.valueOf(dto.getConApprovalState())));
                    return dto;
                }).collect(Collectors.toList());

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                WriteCellStyle headStyle = new WriteCellStyle();
//            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

                WriteFont headFont = new WriteFont();
                headFont.setFontHeightInPoints((short) 12);
                headFont.setFontName("Arial");
                headStyle.setWriteFont(headFont);

                WriteCellStyle contentStyle = new WriteCellStyle();
//            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

                WriteFont contentFont = new WriteFont();
                contentFont.setFontHeightInPoints((short) 10);
                contentFont.setFontName("Arial");
                contentStyle.setWriteFont(contentFont);

                HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);
                // 1用conTractDTO   1用conTractDTO
                EasyExcel.write(out, conTractDTO.class)
                        .registerWriteHandler(styleStrategy)
                        .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                        .sheet("conTractDTO")
                        .doWrite(list);

                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=ht.xlsx");

                response.getOutputStream().write(out.toByteArray());
                response.getOutputStream().flush();
                response.getOutputStream().close();

                return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static String getValueByTitle(List<LinkedHashMap<String, Object>> dataList, String title) {
        if (dataList != null) {
            for (LinkedHashMap<String, Object> item : dataList) {
                if (item.get("value").equals(title)) {
                    return String.valueOf(item.get("title"));
                }
            }
        }
        return title;
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

    public static String insertNewlineEveryNChars(String input, int length) {
        if (input == null || input.isEmpty() || length <= 0) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < input.length()) {
            // 确保不会超过字符串的长度
            int end = Math.min(index + length, input.length());
            result.append(input, index, end);
            index = end;

            // 如果不是最后一部分，添加换行符
            if (index < input.length()) {
                result.append(System.lineSeparator()); // 使用系统默认的换行符
            }
        }

        return result.toString();
    }


    @Override
    public ResponseEntity<byte[]> getProvPilotProjectExcel(ProvPilotProjectModel request, String token, HttpServletResponse response) {
        try {
            List<ProvPilotProjectModel> dataList = pmFeign.getProvPilotProjectExportList(request, token);
            if (ValidateUtil.isEmpty(dataList)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 代码拼装导出：第1行标题(合并A1:G1)、第2行表头，数据从第3行开始
            // 列：序号、年度、批次、项目名称、项目类型、所在区、申报单位、关联市项目
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("省试点项目导出");
                // 列宽（1/256 字符宽）
                sheet.setColumnWidth(0, poiWidth(5.75));
                sheet.setColumnWidth(1, poiWidth(12.75));
                sheet.setColumnWidth(2, poiWidth(12.75));
                sheet.setColumnWidth(3, poiWidth(38.625));
                sheet.setColumnWidth(4, poiWidth(14.625));
                sheet.setColumnWidth(5, poiWidth(14));
                sheet.setColumnWidth(6, poiWidth(43.375));
                sheet.setColumnWidth(7, poiWidth(40.75));
                // 冻结前2行
                sheet.createFreezePane(0, 2);

                CellStyle headStyle = buildProvPilotCellStyle(workbook, true);
                CellStyle dataStyle = buildProvPilotCellStyle(workbook, false);

                // 第1行：标题（合并 A1:G1）
                Row titleRow = sheet.createRow(0);
                setProvPilotCell(titleRow, 0, "省试点项目导出", headStyle);
                for (int i = 1; i < 8; i++) {
                    setProvPilotCell(titleRow, i, "", headStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

                // 第2行：表头
                String[] headers = {"序号", "年度", "批次", "项目名称", "项目类型", "所在区", "申报单位", "关联市项目"};
                Row headRow = sheet.createRow(1);
                for (int i = 0; i < headers.length; i++) {
                    setProvPilotCell(headRow, i, headers[i], headStyle);
                }

                // 字典：项目类型、所在区 D_Code -> 名称
                Map<String, String> projectTypeName = buildCodeNameMap(DICT_PROJECT_TYPE);
                Map<String, String> districtName = buildCodeNameMap(DICT_DISTRICT);

                // 第3行起：数据
                int rowIndex = 2;
                int seq = 1;
                for (ProvPilotProjectModel info : dataList) {
                    Row row = sheet.createRow(rowIndex++);
                    setProvPilotCell(row, 0, String.valueOf(seq++), dataStyle);
                    setProvPilotCell(row, 1, info.getAnnualYear() == null ? "" : String.valueOf(info.getAnnualYear()), dataStyle);
                    setProvPilotCell(row, 2, info.getBatch(), dataStyle);
                    setProvPilotCell(row, 3, info.getProjectName(), dataStyle);
                    setProvPilotCell(row, 4, projectTypeName.getOrDefault(info.getProjectType(), info.getProjectType()), dataStyle);
                    setProvPilotCell(row, 5, districtName.getOrDefault(info.getDistrict(), info.getDistrict()), dataStyle);
                    setProvPilotCell(row, 6, info.getDeclareUnit(), dataStyle);
                    setProvPilotCell(row, 7, info.getRelatedCityProjectName(), dataStyle);
                }
                workbook.write(out);
            }

            String fileName = URLEncoder.encode("省试点项目导出.xlsx", "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("省试点项目导出失败", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getProvPilotEnterpriseExcel(ProvPilotEnterpriseModel request, String token, HttpServletResponse response) {
        try {
            List<ProvPilotEnterpriseModel> dataList = pmFeign.getProvPilotEnterpriseExportList(request, token);
            if (ValidateUtil.isEmpty(dataList)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 6 列：序号、年度、批次、企业名称、企业类型、所在区
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("省试点企业导出");
                sheet.setColumnWidth(0, poiWidth(5.75));
                sheet.setColumnWidth(1, poiWidth(12.75));
                sheet.setColumnWidth(2, poiWidth(12.75));
                sheet.setColumnWidth(3, poiWidth(38.625));
                sheet.setColumnWidth(4, poiWidth(14.625));
                sheet.setColumnWidth(5, poiWidth(14));
                sheet.createFreezePane(0, 2);

                CellStyle headStyle = buildProvPilotCellStyle(workbook, true);
                CellStyle dataStyle = buildProvPilotCellStyle(workbook, false);

                Row titleRow = sheet.createRow(0);
                setProvPilotCell(titleRow, 0, "省试点企业导出", headStyle);
                for (int i = 1; i < 6; i++) {
                    setProvPilotCell(titleRow, i, "", headStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

                String[] headers = {"序号", "年度", "批次", "企业名称", "企业类型", "所在区"};
                Row headRow = sheet.createRow(1);
                for (int i = 0; i < headers.length; i++) {
                    setProvPilotCell(headRow, i, headers[i], headStyle);
                }

                Map<String, String> typeName = buildCodeNameMap("JPMsoft_ZPSBZY");
                Map<String, String> districtName = buildCodeNameMap("JPMsoft_ZPSZQ");

                int rowIndex = 2;
                int seq = 1;
                for (ProvPilotEnterpriseModel info : dataList) {
                    Row row = sheet.createRow(rowIndex++);
                    setProvPilotCell(row, 0, String.valueOf(seq++), dataStyle);
                    setProvPilotCell(row, 1, info.getAnnualYear() == null ? "" : String.valueOf(info.getAnnualYear()), dataStyle);
                    setProvPilotCell(row, 2, info.getBatch(), dataStyle);
                    setProvPilotCell(row, 3, info.getEnterpriseName(), dataStyle);
                    setProvPilotCell(row, 4, typeName.getOrDefault(info.getEnterpriseType(), info.getEnterpriseType()), dataStyle);
                    setProvPilotCell(row, 5, districtName.getOrDefault(info.getDistrict(), info.getDistrict()), dataStyle);
                }
                workbook.write(out);
            }

            String fileName = URLEncoder.encode("省试点企业导出.xlsx", "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("省试点企业导出失败", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 省试点项目导出 列宽换算（字符宽 -> POI 的 1/256 单位）
     */
    private int poiWidth(double charWidth) {
        return (int) (charWidth * 256);
    }

    /**
     * 字典：D_Code -> 名称（用于导出时把字典值转回名称）
     */
    private Map<String, String> buildCodeNameMap(String code) {
        Map<String, String> map = new HashMap<>();
        List<SysDictionary> list = dictService.getDictionaryByParentCode(code);
        if (list != null) {
            for (SysDictionary d : list) {
                if (d.getDName() != null && d.getDCode() != null) {
                    map.put(d.getDCode(), d.getDName());
                }
            }
        }
        return map;
    }

    /**
     * 省试点项目导出 单元格样式（细边框、微软雅黑、左对齐、垂直居中）
     *
     * @param bold 是否加粗（表头加粗、数据不加粗）
     */
    private CellStyle buildProvPilotCellStyle(Workbook workbook, boolean bold) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setBold(bold);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        return style;
    }

    /**
     * 省试点项目导出 写单元格
     */
    private void setProvPilotCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(ValidateUtil.isEmpty(value) ? "" : value);
        cell.setCellStyle(style);
    }

    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(Short fontHeightInPoints) {
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);

        // 配置字体
        WriteFont contentWriteFont = new WriteFont();
        // 字体
        contentWriteFont.setFontName("宋体");
        // 字体大小
        contentWriteFont.setFontHeightInPoints(fontHeightInPoints);
        // 设置加粗
        contentWriteFont.setBold(false);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 设置文字左右居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置文字上下居中
//        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.LEFT);
        // 设置 自动换行
        contentWriteCellStyle.setWrapped(true);

        // 样式策略
        return new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
    }

}
