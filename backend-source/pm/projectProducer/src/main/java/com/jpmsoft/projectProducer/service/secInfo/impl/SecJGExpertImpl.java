package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.ExpertTypeEnum;
import com.jpmsoft.projectProducer.feign.DictionaryFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.*;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpert;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGAgencySelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertCertificateService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGSelectionRecordDtlService;
import com.jpmsoft.projectProducer.utils.*;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecJGExpertImpl extends ServiceImpl<SecJGExpertMapper, SecJGExpert> implements ISecJGExpertService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private ISecJGExpertCertificateService secJGExpertCertificateService;
    @Resource
    private SecInfoMapper secInfoMapper;
    @Resource
    private ISecJGSelectionRecordDtlService secJGSelectionRecordDtlService;
    @Resource
    private ISysDictionaryService sysDictionaryService;
    @Resource
    private DictionaryFeign dictionaryFeign;
    @Resource
    private ISecJGAgencySelectionRecordDtlService secJGAgencySelectionRecordDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecJGExpert(SecJGExpertModel request) {
        return createOrUpdate(request, false, false);
    }

    @Override
    public BaseResponse createOrUpdateSecJGExpertCurrent(SecJGExpertModel request) {
        return createOrUpdate(request, true, false);
    }

    @Override
    public BaseResponse createOrUpdateSecJGExpertVerify(SecJGExpertModel request) {
        return createOrUpdate(request, true, true);
    }

    @Override
    public BaseResponse resetJGPlaintextNum() {
        QueryWrapper<SecJGExpert> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("Ex_Id_Number");
        wrapper.isNull("Ex_Plaintext_Num");
        List<SecJGExpert> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data = data.stream().peek(d -> {
                d.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber()));
            }).collect(Collectors.toList());
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecJGExpertList(SecJGExpertModel request) {
        QueryMap<SecJGExpertModel> params = new QueryMap<>(request);
        params.put("cId").put("cName").put("cNameHistory").put("exName").put("exInsideType")
                .put("exEntry").put("exSex").put("exIdType").put("exIdNumber").put("exLocation")
                .put("exPolitics").put("exEducation").put("exMajor").put("exSchool")
                .put("exWorkState").put("exWorkUnit").put("exPost").put("exTitle")
                .put("exType").put("exTitleNumber").put("exProfessionalCategory").put("exApplicantCId")
                .put("exIndustry").put("exCellphoneNumber").put("exState").put("exStatus")
                .put("exCreateUname").put("exCategory").put("exPracticeCode").put("exApplicantId");
        IPage<SecJGExpertModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecJGExpertModel> datas = baseMapper.getSecExpertList(params.getQuery(), page);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecJGExpertModel> data = datas.stream().peek(d -> {
                String plaintextIdNumber = apiUtilsService.SelfSm2Decrypt(d.getExIdNumber());
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(plaintextIdNumber));
                String nowDate = cn.hutool.core.date.DateUtil.format(new Date(), "yyyy-MM-dd");
                DateTime date = cn.hutool.core.date.DateUtil.parseDate(nowDate);
                Integer age = null;
                try {
                    age = IdcardUtil.getAgeByIdCard(plaintextIdNumber, date);
                } catch (Exception e) {
                    age = null;
                }
                d.setExAge(age);
                d.setExHomePhone(VoInfoUtil.phoneHandle(d.getExHomePhone()));
            }).collect(Collectors.toList());
            return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
        }
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getSecJGExpertCjDzList(SecJGExpertModel request, String uid) {
        Integer exInsideType = request.getExInsideType();
        if (exInsideType == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String role = sysConfigService.getConfigByCode("cjDzSecJGExpertAuthList");
        if (ValidateUtil.isNotEmpty(role)) {
            Boolean auth = false;
            List<String> roles = Arrays.asList(role.split(","));
            BaseResponse res = userProducerFeign.getUserRole(uid);
            if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
                List<String> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), String.class);
                if (!CollectionUtils.isEmpty(data)) {
                    for (String r : data) {
                        if (roles.contains(r)) {
                            auth = true;
                            break;
                        }
                    }
                }
            }
            if (auth)
                request.setExApplicantId(null);
            else
                request.setExApplicantId(uid);
        }
        return getSecJGExpertList(request);
    }

    @Override
    public Workbook exportExcelSecJGExpertCjDzList(SecJGExpertModel request, String uid) {
        request.setPage(1);
        request.setSize(-1);
        BaseResponse baseResponse = getSecJGExpertCjDzList(request, uid);
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        if (baseResponse.getSuccess()) {
            List<SecJGExpertModel> models = (List<SecJGExpertModel>) baseResponse.getData();

            String[] rowTitle = new String[]{"姓名", "身份证号", "学历", "专业", "是否进场", "参加集团系统工作年月", "参加集团系统工作时间", "银行账号", "开户行", "专业技术职称", "通讯方式", "专家类型", "现工作单位", "专家申报专业1", "专家申报专业2", "本人认为需要回避的信息", "流程状态", "创建时间", "创建人"};

            // 创建一个工作表(sheet)
            Sheet sheet = workbook.createSheet("专家库信息");
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

            if (!CollectionUtils.isEmpty(models)) {
                for (SecJGExpertModel model : models) {
                    row = sheet.createRow(rowNum++);

                    cell = row.createCell(0);
                    cell.setCellValue(model.getExName());
                    cell.setCellStyle(style);

                    cell = row.createCell(1);
                    cell.setCellValue(model.getExIdNumber());
                    cell.setCellStyle(style);

                    cell = row.createCell(2);
                    cell.setCellValue(StringUtils.isBlank(model.getExEducation()) ? "" : DictionaryUtils.getTitle("JPMsoft_ZGXL", model.getExEducation()));
                    cell.setCellStyle(style);

                    //专业
                    cell = row.createCell(3);
                    cell.setCellValue(DictionaryUtils.getTitle("JPMsoft_ZJZYZD", model.getExDiplomaMajor()));
                    cell.setCellStyle(style);

                    //是否进场
                    cell = row.createCell(4);
                    cell.setCellValue(model.getExEntry() == null ? "" : model.getExEntry() == 1 ? "进场" : "非进场");
                    cell.setCellStyle(style);

                    cell = row.createCell(5);
                    cell.setCellValue(null == model.getExWorkTime() ? "" : DateFormatUtil.dateToString(model.getExWorkTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                    cell.setCellStyle(style);

                    //参加集团系统工作时间
                    cell = row.createCell(6);
                    cell.setCellValue(null == model.getExWorkTime() ? "" : String.valueOf(DateFormatUtil.getYears(DateFormatUtil.dateToString(model.getExWorkTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH) + "-01", DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH) + "-01")) + "年");
                    cell.setCellStyle(style);

                    cell = row.createCell(7);
                    cell.setCellValue(model.getExBankAccount());
                    cell.setCellStyle(style);


                    cell = row.createCell(8);
                    cell.setCellValue(model.getExBank());
                    cell.setCellStyle(style);

                    //专业技术职称
                    cell = row.createCell(9);
                    cell.setCellValue(StringUtils.isBlank(model.getExTitle()) ? "" : DictionaryUtils.getTitle("JPMsoft_ZYJSZC", model.getExTitle()));
                    cell.setCellStyle(style);

                    cell = row.createCell(10);
                    cell.setCellValue(model.getExHomePhone());
                    cell.setCellStyle(style);

                    //专家类型
                    cell = row.createCell(11);
                    cell.setCellValue(StringUtils.isBlank(model.getExCategory()) ? "" : DictionaryUtils.getTitle("JPMsoft_ZJLB", model.getExCategory()));
                    cell.setCellStyle(style);

                    //现工作单位
                    cell = row.createCell(12);
                    cell.setCellValue(model.getExApplicantCName());
                    cell.setCellStyle(style);

                    //专家申报专业1
                    cell = row.createCell(13);
                    cell.setCellValue(StringUtils.isBlank(model.getExDeclareMajorOne()) ? "" : DictionaryUtils.getTitle("JPMsoft_SBZYYI", model.getExDeclareMajorOne()));
                    cell.setCellStyle(style);

                    //专家申报专业2
                    cell = row.createCell(14);
                    cell.setCellValue(StringUtils.isBlank(model.getExDeclareMajorTwo()) ? "" : DictionaryUtils.getTitle("JPMsoft_SBZYER", model.getExDeclareMajorTwo()));
                    cell.setCellStyle(style);

                    cell = row.createCell(15);
                    cell.setCellValue(model.getExAvoid());
                    cell.setCellStyle(style);

                    //流程状态
                    cell = row.createCell(16);
                    cell.setCellValue(null == model.getExState() ? "" : DictionaryUtils.getTitle("JPMsoft_LCZT", String.valueOf(model.getExState())));
                    cell.setCellStyle(style);

                    cell = row.createCell(17);
                    cell.setCellValue(null == model.getExCreateTime() ? "" : DateFormatUtil.dateToString(model.getExCreateTime(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                    cell.setCellStyle(style);

                    cell = row.createCell(18);
                    cell.setCellValue(model.getExCreateUname());
                    cell.setCellStyle(style);

                }
            }
        }
        return workbook;
    }

    @Override
    public Workbook excelSecJGExpertTemplate(SecJGExpertModel request, String uid) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();
        String[] rowTitle = new String[]{"姓名", "身份证号", "手机号", "专业技术职称", "专家类别", "技术类专家申报专业1", "技术类专家申报专业2", "经济类专家申报专业1"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("外部专家库模板");
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 设置背景填充模式
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));//设置文本格式

        CellStyle style1 = workbook.createCellStyle();
        style1.setDataFormat(format.getFormat("@"));//设置文本格式


        for (int i = 0; i < rowTitle.length; i++) {
            sheet.setColumnWidth(i, 6000);
            sheet.setDefaultColumnStyle(i, style1);
        }


        Row rowHead = sheet.createRow(0);
        rowHead.setHeight((short) 800);
        Cell cellHead = rowHead.createCell(0);
        cellHead.setCellValue("外部专家导入模板");
        cellHead.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        //设置下拉列表
        Map<String, Object> map = dictDataConversionMap("title", "value");
        Map<String, String> exTitleMap = (Map<String, String>) map.get("exTitleMap");
        Map<String, String> exCategoryMap = (Map<String, String>) map.get("exCategoryMap");
        Map<String, String> exDeclareMajorOneMap = (Map<String, String>) map.get("exDeclareMajorOneMap");
        Map<String, String> exEconomicMajorOneMap = (Map<String, String>) map.get("exEconomicMajorOneMap");


        List<String> exTitleDict = exTitleMap.keySet().stream().collect(Collectors.toList());
        List<String> exCategoryDict = exCategoryMap.keySet().stream().collect(Collectors.toList());
        List<String> exDeclareMajorOneDict = exDeclareMajorOneMap.keySet().stream().collect(Collectors.toList());
        List<String> exEconomicMajorOneDict = exEconomicMajorOneMap.keySet().stream().collect(Collectors.toList());

        //专业技术职称
        PoiUtil.setDropDownValidation(sheet, exTitleDict, 2, 65533, 3, 3);
        //专家类别
        PoiUtil.setDropDownValidation(sheet, exCategoryDict, 2, 65533, 4, 4);
        //技术类专家申报专业1
        PoiUtil.setDropDownValidation(sheet, exDeclareMajorOneDict, 2, 65533, 5, 5);
        //技术类专家申报专业2
        PoiUtil.setDropDownValidation(sheet, exDeclareMajorOneDict, 2, 65533, 6, 6);
        //经济类专家申报专业1
        PoiUtil.setDropDownValidation(sheet, exEconomicMajorOneDict, 2, 65533, 7, 7);

        int rowNum = 1;
        Row row = sheet.createRow(rowNum);
        row.setHeight((short) 500);
        Cell cell;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }

        return workbook;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importSecJGExpert(MultipartFile file, LogUserInfoVo user) {
        InputStream inputStream = null;
        ExcelReader reader = null;
        try {
            inputStream = file.getInputStream();
            reader = ExcelUtil.getReader(inputStream);
        } catch (Exception e) {
            throw new JPMException("请按模板上传数据");
        }

        String createUid = user.getId();
        String createUName = user.getName();

        List<List<Object>> resultList = reader.read();

        //execl标题与Bean字段名对应
        reader.addHeaderAlias("专家名称", "exName");
        reader.addHeaderAlias("身份证号", "exIdNumber");
        reader.addHeaderAlias("日目标产量", "dailytarget");

        List<SecJGExpert> secJGExpertList1 = reader.readAll(SecJGExpert.class);
        if (CollectionUtil.isEmpty(resultList) || resultList.size() < 2) {
            throw new JPMException("请按模板上传数据");
        }

        Map<String, Object> map = dictDataConversionMap("title", "value");

        Map<String, String> exTitleMap = (Map<String, String>) map.get("exTitleMap");
        Map<String, String> exCategoryMap = (Map<String, String>) map.get("exCategoryMap");
        Map<String, String> exDeclareMajorOneMap = (Map<String, String>) map.get("exDeclareMajorOneMap");
        Map<String, String> exEconomicMajorOneMap = (Map<String, String>) map.get("exEconomicMajorOneMap");

        Date date = new Date();
        List<String> exIdNumberList = new ArrayList<>();
        for (int i = 2; i < resultList.size(); i++) {
            List<Object> objects = resultList.get(i);
            //证件号
            String exIdNumber = objects.get(1).toString();
            if (StrUtil.isNotBlank(exIdNumber) && !exIdNumberList.contains(exIdNumber)) {
                exIdNumberList.add(exIdNumber);
            }
        }
        List<SecJGExpert> exIdNumberSecJgExpert = baseMapper.getByExIdNumberSecJgExpert(exIdNumberList);
        Map<String, List<SecJGExpert>> exIdNumberMap = exIdNumberSecJgExpert.stream().collect(Collectors.groupingBy(SecJGExpert::getExPlaintextNum));

        List<SecJGExpert> secJGExpertList = new ArrayList<>();
        exIdNumberList = new ArrayList<>();
        for (int i = 2; i < resultList.size(); i++) {
            SecJGExpert jgExpertExcelDTO = new SecJGExpert();
            List<Object> objects = resultList.get(i);
            String exName = objects.get(0).toString();
            if (StrUtil.isEmpty(exName)) {
                checkExcelExpert(i + 1, 0, "专家姓名不能为空");
            }
            jgExpertExcelDTO.setExName(exName);

            //证件号
            String exIdNumber = objects.get(1).toString();
            if (StrUtil.isEmpty(exIdNumber)) {
                checkExcelExpert(i + 1, 1, "身份证号不能为空");
            }
            boolean valid = IdcardUtil.isValidCard(exIdNumber);
            if (!valid) {
                checkExcelExpert(i + 1, 1, "身份证号格式不正确");
            }
            //校验身份证号是否存在
            if (exIdNumberMap.containsKey(exIdNumber)) {
                checkExcelExpert(i + 1, 1, "身份证号在库中已存在");
            }
            jgExpertExcelDTO.setExPlaintextNum(exIdNumber);
            if (exIdNumberList.contains(exIdNumber)) {
                checkExcelExpert(i + 1, 1, "excel中身份证号已存在");
            }
            exIdNumberList.add(exIdNumber);
            //加密
            String encryExIdNumber = apiUtilsService.Sm2EncryptionFront(exIdNumber, null);
            jgExpertExcelDTO.setExIdNumber(encryExIdNumber);


            //手机号
            String exHomePhone = objects.get(2).toString();
            if (StrUtil.isEmpty(exHomePhone)) {
                checkExcelExpert(i + 1, 2, "手机号不能为空");
            }
            boolean phoneFlag = CommonUtil.validatePhoneNumber(exHomePhone);
            if (!phoneFlag) {
                checkExcelExpert(i + 1, 2, "手机号格式不正确");
            }
            jgExpertExcelDTO.setExHomePhone(exHomePhone);

            //专业技术职称 JPMsoft_ZYJSZC
            String exTitle = "";
            try {
                exTitle = objects.get(3).toString();
            } catch (Exception e) {
                exTitle = "";
            }
            if (StrUtil.isNotBlank(exTitle)) {
                if (!exTitleMap.containsKey(exTitle)) {
                    checkExcelExpert(i + 1, 3, "专业技术职称的值不在字典范围");
                }
                jgExpertExcelDTO.setExTitle(exTitleMap.get(exTitle));
            }

            //专家类别   JPMsoft_JGZJLX
            String exCategory = objects.get(4).toString();
            if (StrUtil.isEmpty(exCategory)) {
                checkExcelExpert(i + 1, 4, "专家类别不能为空");
            }
            if (!exCategoryMap.containsKey(exCategory)) {
                checkExcelExpert(i + 1, 4, "专家类别的值不在字典范围");
            }
            jgExpertExcelDTO.setExCategory(exCategoryMap.get(exCategory));

            //技术类专家申报专业1  JPMsoft_JGZJSBZYA
            if ("技术类专家".equals(exCategory)) {
                String exDeclareMajorOne = "";
                try {
                    exDeclareMajorOne = objects.get(5).toString();
                } catch (Exception e) {
                    exDeclareMajorOne = "";
                }
                if (StrUtil.isNotBlank(exDeclareMajorOne)) {
                    if (!exDeclareMajorOneMap.containsKey(exDeclareMajorOne)) {
                        checkExcelExpert(i + 1, 5, "技术类专家申报专业1的值不在字典范围");
                    }
                    jgExpertExcelDTO.setExDeclareMajorOne(exDeclareMajorOneMap.get(exDeclareMajorOne));
                }

                //技术类专家申报专业2 JPMsoft_JGZJSBZYA、
                String exDeclareMajorTwo = "";
                try {
                    exDeclareMajorTwo = objects.get(6).toString();
                } catch (Exception e) {
                    exDeclareMajorTwo = "";
                }
                if (StrUtil.isNotBlank(exDeclareMajorTwo)) {
                    if (!exDeclareMajorOneMap.containsKey(exDeclareMajorTwo)) {
                        checkExcelExpert(i + 1, 6, "技术类专家申报专业2的值不在字典范围");
                    }
                    jgExpertExcelDTO.setExDeclareMajorTwo(exDeclareMajorOneMap.get(exDeclareMajorTwo));
                }
            }

            if ("经济类专家".equals(exCategory)) {
                //经济类专家申报专业1 JPMsoft_JGZJSBZYB
                String exEconomicMajorOne = objects.get(7).toString();
                if (StrUtil.isEmpty(exEconomicMajorOne)) {
                    checkExcelExpert(i + 1, 7, "经济类专家申报专业1不能为空");
                }
                if (!exEconomicMajorOneMap.containsKey(exEconomicMajorOne)) {
                    checkExcelExpert(i + 1, 7, "经济类专家申报专业1的值不在字典范围");
                }
                jgExpertExcelDTO.setExEconomicMajorOne(exEconomicMajorOneMap.get(exEconomicMajorOne));
            }
            jgExpertExcelDTO.setId(GenerateUUID.getUUID());
            jgExpertExcelDTO.setExInsideType(0);//外部
            jgExpertExcelDTO.setExState(0);
            jgExpertExcelDTO.setExStatus(0);
            jgExpertExcelDTO.setExCreateTime(date);
            jgExpertExcelDTO.setExCreateUid(createUid);
            jgExpertExcelDTO.setExCreateUname(createUName);

            secJGExpertList.add(jgExpertExcelDTO);
        }

        Integer count = 0;
        if (CollectionUtil.isNotEmpty(secJGExpertList)) {
            this.saveBatch(secJGExpertList);
            count = secJGExpertList.size();
        }

        // 生成file信息
        FileTemplateTree jg = sysFileService.getFileTemplateByTCode("Expertdatabase_JG");
        secJGExpertList.forEach(scJGExpert -> sysFileService.saveFile(scJGExpert.getId(), jg.gettId(), scJGExpert.getExState().toString(), scJGExpert.getExCreateUid(), scJGExpert.getExCreateUname(),
                scJGExpert.getExCreateTime(), null, jg.gettName(), jg.gettCode(), "create", GenerateUUID.getUUID(), null, jg.gettCode(), null));

        return BaseResponse.ok("操作成功!成功导入" + count + "条数据", new ArrayList<>());
    }


    public Map<String, Object> dictDataConversionMap(String mKey, String mValue) {
        if (StrUtil.isBlank(mKey)) {
            mKey = "title";
        }
        if (StrUtil.isBlank(mValue)) {
            mKey = "value";
        }
        //专业技术职称 ;
        String exTitleCode = "JPMsoft_ZYJSZC";
        //专家类别
        String exCategoryCode = "JPMsoft_JGZJLX";
        //技术类专家申报专业1   ;
        String exDeclareMajorOneCode = "JPMsoft_JGZJSBZYA";
        //经济类专家申报专业1
        String exEconomicMajorOneCode = "JPMsoft_JGZJSBZYB";

        SelectDto selectDto = new SelectDto();
        selectDto.setCodes(Arrays.asList(exTitleCode, exCategoryCode, exDeclareMajorOneCode, exEconomicMajorOneCode));
        BaseResponse baseResponse = dictionaryFeign.getDictionaryTreeByCodes(selectDto);

        Map<String, Object> map = new HashMap<>(16);
        if (baseResponse.getSuccess()) {
            LinkedHashMap originalData = (LinkedHashMap) baseResponse.getData();
            List<LinkedHashMap> exTitleList = (List<LinkedHashMap>) originalData.get(exTitleCode);
            List<LinkedHashMap> exCategoryList = (List<LinkedHashMap>) originalData.get(exCategoryCode);
            List<LinkedHashMap> exDeclareMajorOneList = (List<LinkedHashMap>) originalData.get(exDeclareMajorOneCode);
            List<LinkedHashMap> exEconomicMajorOneList = (List<LinkedHashMap>) originalData.get(exEconomicMajorOneCode);
            //每一个字典单独一个map，避免在不同的字典里面配置相同的子项(字典值、字典名称)

            Map<String, String> exTitleMap = getDictToMap(exTitleList, mKey, mValue);

            Map<String, String> exCategoryMap = getDictToMap(exCategoryList, mKey, mValue);

            Map<String, String> exDeclareMajorOneMap = getDictToMap(exDeclareMajorOneList, mKey, mValue);

            Map<String, String> exEconomicMajorOneMap = getDictToMap(exEconomicMajorOneList, mKey, mValue);


            map.put("exTitleMap", exTitleMap);
            map.put("exCategoryMap", exCategoryMap);
            map.put("exDeclareMajorOneMap", exDeclareMajorOneMap);
            map.put("exEconomicMajorOneMap", exEconomicMajorOneMap);
        }

        return map;
    }


    @Override
    public BaseResponse getSecJGExpertById(String exId) {
        SecJGExpertModel data = baseMapper.getSecJGExpertById(exId);
        if (data != null) {
            if (ValidateUtil.isNotEmpty(data.getExIdNumber())) {
                String plaintextIdNumber = apiUtilsService.SelfSm2Decrypt(data.getExIdNumber());
                data.setExIdNumber(VoInfoUtil.handleUserIdCard(plaintextIdNumber));
                String nowDate = cn.hutool.core.date.DateUtil.format(new Date(), "yyyy-MM-dd");
                DateTime date = cn.hutool.core.date.DateUtil.parseDate(nowDate);
                int age = IdcardUtil.getAgeByIdCard(plaintextIdNumber, date);
                data.setExAge(age);
            }
            if (ValidateUtil.isNotEmpty(data.getExHomePhone())) {
                data.setExHomePhoneEncrypt(VoInfoUtil.phoneHandle(data.getExHomePhone()));
            }
        }

        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deleteSecJGExpertById(String exId) {
        SecJGExpert model = baseMapper.selectById(exId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        //if (secSelectionRecordDtlService.getExpertCount(exId) > 0) return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(exId);
        sysFileService.deleteFileById(exId);
        return BaseResponse.ok();
    }

    @Override
    public boolean expertJGExists(String idNum, String id, String teId) {
        idNum = apiUtilsService.SelfSm2Decrypt(idNum);
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<SecJGExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Plaintext_Num", idNum);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse extractJGExpertInfo(ExtractJGExpertDto request, String teId) {
        String secId = request.getSecId();
        Date srBidTime = request.getSrBidTime();
        String secBiddingType = request.getSecBiddingType();
        if (ValidateUtil.isEmpty(secId) || ValidateUtil.isEmpty(srBidTime))
            return BaseResponse.fail("获取标段信息失败");
        if (StrUtil.isBlank(secBiddingType)) {
            SecInfoResponse secInfo = secInfoMapper.getSecInfoById(secId);
            if (secInfo != null) {
                secBiddingType = secInfo.getSecBiddingType();
            }
        }
        JGExpertDto insideExpert = request.getInsideExpert();
        JGExpertDto outsideExpert = request.getOutsideExpert();
        if (ValidateUtil.isEmpty(insideExpert) || ValidateUtil.isEmpty(outsideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        //需要排除的专家id
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        //是否需要排除指定单位下的专家
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        String excludeCpy = null;
        if (ValidateUtil.isNotNull(conf) && conf.getExcludeCpy()) {
            excludeCpy = request.getExcludeCpy();
        }
        List<String> avoidCompanyIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())) {
            avoidCompanyIds = new ArrayList<>(Arrays.asList(request.getAvoidCompanyId().split(",")));
        }
        if (StringUtils.isNotBlank(excludeCpy)) {
            avoidCompanyIds.add(excludeCpy);
        }


        //排除后所有专家
        List<SecJGExpertModel> records = baseMapper.getExtractExpertList(null, excludeIds, excludeCpy, teId, null,
                null, null, avoidCompanyIds);


        /*******************************抽取内部专家***************************/

        //抽取内部专家（公开招标：从内部-进场专家中抽取；其他类型从内部-不进场专家抽取）
        List<SecJGExpertModel> insideExpertList = records.stream().filter(e -> e.getExInsideType() == 1).collect(Collectors.toList());
        if (StrUtil.isNotBlank(secBiddingType) && "01".equals(secBiddingType)) {
            //是否进场0：非进场 1、进场
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 1).collect(Collectors.toList());
        } else {
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 0).collect(Collectors.toList());
        }
        //抽取的内部专家
        List<SecJGExpertModel> insideData = new ArrayList<>();
        if (!CollectionUtils.isEmpty(insideExpertList)) {
            //抽取技术类专家
            //技术类专家
            List<SecJGExpertModel> technologies = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey())) {
                        if (!CollectionUtils.isEmpty(insideExpert.getExTechTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (insideExpert.getExTechTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(insideData, technologies, insideExpert.getEsTechnologyNum());
            records.removeAll(insideData);

            //抽取经济类专家
            List<SecJGExpertModel> economics = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Economics.getkey())) {
                        if (!CollectionUtils.isEmpty(insideExpert.getExEconTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (insideExpert.getExEconTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(insideData, economics, insideExpert.getEsEconomicsNum());

            //endregion
            insideData.forEach(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });

        } else {
            return BaseResponse.fail("没有可用于抽取的内部专家");
        }


        /*******************************抽取的外部专家***************************/
        List<SecJGExpertModel> outsideData = new ArrayList<>();
        //抽取外部专家
        List<SecJGExpertModel> outsideExpertList = records.stream().filter(e -> e.getExInsideType() == 0).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(outsideExpertList)) {
            //技术类专家
            List<SecJGExpertModel> technologies = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey())) {
                        if (!CollectionUtils.isEmpty(outsideExpert.getExTechTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (outsideExpert.getExTechTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(outsideData, technologies, outsideExpert.getEsTechnologyNum());
            records.removeAll(outsideData);

            //抽取经济类专家
            List<SecJGExpertModel> economics = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Economics.getkey())) {
                        if (!CollectionUtils.isEmpty(outsideExpert.getExEconTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (outsideExpert.getExEconTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(outsideData, economics, outsideExpert.getEsEconomicsNum());

            //endregion
            outsideData.forEach(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });

        } else {
            return BaseResponse.fail("没有可用于抽取的外部专家");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("insideData", insideData);
        map.put("outsideData", outsideData);

        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse extractJGExpert(ExtractJGExpertDto request, String teId) {
        String secId = request.getSecId();
        Date srBidTime = request.getSrBidTime();
        String secBiddingType = request.getSecBiddingType();
        if (ValidateUtil.isEmpty(secId) || ValidateUtil.isEmpty(srBidTime))
            return BaseResponse.fail("获取标段信息失败");
        if (StrUtil.isBlank(secBiddingType)) {
            SecInfoResponse secInfo = secInfoMapper.getSecInfoById(secId);
            if (secInfo != null) {
                secBiddingType = secInfo.getSecBiddingType();
            }
        }
        JGExpertDto insideExpert = request.getInsideExpert();
        JGExpertDto outsideExpert = request.getOutsideExpert();
        if (ValidateUtil.isEmpty(insideExpert) || ValidateUtil.isEmpty(outsideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        //需要排除的专家id
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        //是否需要排除指定单位下的专家
        SecExpertLogicalConfDTO conf = sysConfigService.getSecJGExpertLogicalConf();
        String excludeCpy = null;
        if (ValidateUtil.isNotNull(conf) && conf.getExcludeCpy()) {
            excludeCpy = request.getExcludeCpy();
        }
        List<String> avoidCompanyIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())) {
            avoidCompanyIds = new ArrayList<>(Arrays.asList(request.getAvoidCompanyId().split(",")));
        }
        if (StringUtils.isNotBlank(excludeCpy)) {
            avoidCompanyIds.add(excludeCpy);
        }


        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertLockPersonnelCode));


        //排除后所有专家
        List<SecJGExpertModel> records = baseMapper.getExtractExpertList(null, excludeIds, excludeCpy, teId, DateUtil.firstDayOfMonth("yyyy-MM-dd"),
                DateUtil.Date2String(new Date(), "yyyy-MM-dd"), conf.getMonthExNum(), avoidCompanyIds);
        //同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if (StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)) {
            SecJGSelectionRecordDtlDto dto = new SecJGSelectionRecordDtlDto();
            if (srBidTime != null) {
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }
            /*if(StrUtil.isNotBlank(request.getSecId())){
                dto.setNotSecIdList(Arrays.asList(request.getSecId()));
            }*/
            //需要排除被招标代理抽取的专家
            SecJGAgencySelectionRecordDtlDto requestDto = new SecJGAgencySelectionRecordDtlDto();
            requestDto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            List<SecJGAgencySelectionRecordDtlModel> selectionAgencyRecordDtlList = secJGAgencySelectionRecordDtlService.getSelectionRecordDtlList(requestDto);
            List<String> agencyCollect = selectionAgencyRecordDtlList.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
            //剔除同一天在其他标段的专家
            records = records.stream().filter(e -> !agencyCollect.contains(e.getId())).collect(Collectors.toList());


            //同一天在其他标段的专家。
            List<SecJGSelectionRecordDtlModel> selectionRecordDtlList = secJGSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            if (CollectionUtil.isNotEmpty(selectionRecordDtlList)) {
                Map<String, List<SecJGSelectionRecordDtlModel>> secExpertMap = selectionRecordDtlList.stream().collect(Collectors.groupingBy(SecJGSelectionRecordDtlModel::getSecId));
                if (!secExpertMap.isEmpty()) {
                    for (Map.Entry<String, List<SecJGSelectionRecordDtlModel>> item : secExpertMap.entrySet()) {
                        String secIdKey = item.getKey();
                        List<SecJGSelectionRecordDtlModel> secJGSelectionRecordDtlModels = item.getValue();
                        if (secId.equals(secIdKey)) {//同一个标段抽取过的专家都不能再抽到
                            List<String> collect = secJGSelectionRecordDtlModels.stream().map(e -> e.getExId()).collect(Collectors.toList());
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        } else {
                            //同一天已经在其他标段的专家ids(确认不参加的不算)
                            List<String> collect = secJGSelectionRecordDtlModels.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
                            //剔除同一天在其他标段的专家
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        }
                    }
                }

            }

        }


        /*******************************抽取内部专家***************************/

        //抽取内部专家（公开招标：从内部-进场专家中抽取；其他类型从内部-不进场专家抽取）
        List<SecJGExpertModel> insideExpertList = records.stream().filter(e -> e.getExInsideType() == 1).collect(Collectors.toList());
        if (StrUtil.isNotBlank(secBiddingType) && "01".equals(secBiddingType)) {
            //是否进场0：非进场 1、进场
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 1).collect(Collectors.toList());
        } else {
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 0).collect(Collectors.toList());
        }
        //抽取的内部专家
        List<SecJGExpertModel> insideData = new ArrayList<>();
        if (insideExpert.getEsEconomicsNum() != 0 || insideExpert.getEsTechnologyNum() != 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                //抽取技术类专家
                List<String> exTechTypes = insideExpert.getExTechTypes();
                extractTechExpert("内部专家", exTechTypes, insideData, insideExpertList);

                //抽取经济类专家
                extractEconomicsExpert("内部专家", insideData, insideExpertList, insideExpert.getEsEconomicsNum());

                //endregion
                insideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });

            } else {
                return BaseResponse.fail("没有可用于抽取的内部专家");
            }
        }


        /*******************************抽取的外部专家***************************/
        List<SecJGExpertModel> outsideData = new ArrayList<>();
        //抽取外部专家
        List<SecJGExpertModel> outsideExpertList = records.stream().filter(e -> e.getExInsideType() == 0).collect(Collectors.toList());
        if (outsideExpert.getEsEconomicsNum() != 0 || outsideExpert.getEsTechnologyNum() != 0) {
            if (!CollectionUtils.isEmpty(outsideExpertList)) {
                //抽取技术类专家
                List<String> exTechTypes = outsideExpert.getExTechTypes();
                extractTechExpert("外部专家", exTechTypes, outsideData, outsideExpertList);

                //抽取经济类专家
                extractEconomicsExpert("外部专家", outsideData, outsideExpertList, outsideExpert.getEsEconomicsNum());

                //endregion
                outsideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的外部专家");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("insideData", insideData);
        map.put("outsideData", outsideData);

        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse extractJGExpertCj(ExtractJGExpertDto request, String teId) {
        String secId = request.getSecId();  //可能是多个，也可能是单个

        String[] secIds = secId.split(","); //应对多选
        Map<String, String> idMap = new HashMap<>();
        for (String id : secIds) {
            idMap.put(id, id);
        }
        Date srBidTime = request.getSrBidTime();
        String secBiddingType = request.getSecBiddingType();
        if (ValidateUtil.isEmpty(secIds) || ValidateUtil.isEmpty(srBidTime))
            return BaseResponse.fail("获取标段信息失败");
        if (StrUtil.isBlank(secBiddingType)) {
            SecInfoResponse secInforeq = secInfoMapper.getSecInfoById(secIds[0]);
            if(secInforeq!=null){
                secBiddingType = secInforeq.getSecBiddingType();
            }
        }
        JGExpertDto insideExpert = request.getInsideExpert();
        JGExpertDto outsideExpert = request.getOutsideExpert();
        if (ValidateUtil.isEmpty(insideExpert) || ValidateUtil.isEmpty(outsideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        //需要排除的专家id
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        //是否需要排除指定单位下的专家
        SecExpertLogicalConfDTO conf = sysConfigService.getSecJGExpertLogicalConf();
        String excludeCpy = null;
        if (ValidateUtil.isNotNull(conf) && conf.getExcludeCpy()) {
            excludeCpy = request.getExcludeCpy();
        }
        List<String> avoidCompanyIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())) {
            avoidCompanyIds = new ArrayList<>(Arrays.asList(request.getAvoidCompanyId().split(",")));
        }
        if (StringUtils.isNotBlank(excludeCpy)) {
            avoidCompanyIds.add(excludeCpy);
        }


        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertLockPersonnelCode));


        //排除后所有专家
        List<SecJGExpertModel> records = baseMapper.getExtractExpertList(null, excludeIds, excludeCpy, teId, DateUtil.firstDayOfMonth("yyyy-MM-dd"),
                DateUtil.Date2String(new Date(), "yyyy-MM-dd"), conf.getMonthExNum(), avoidCompanyIds);
        //同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if (StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)) {
            SecJGSelectionRecordDtlDto dto = new SecJGSelectionRecordDtlDto();
            if (srBidTime != null) {
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }
            /*if(StrUtil.isNotBlank(request.getSecId())){
                dto.setNotSecIdList(Arrays.asList(request.getSecId()));
            }*/
            //需要排除被招标代理抽取的专家
            SecJGAgencySelectionRecordDtlDto requestDto = new SecJGAgencySelectionRecordDtlDto();
            requestDto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            List<SecJGAgencySelectionRecordDtlModel> selectionAgencyRecordDtlList = secJGAgencySelectionRecordDtlService.getSelectionRecordDtlList(requestDto);
            List<String> agencyCollect = selectionAgencyRecordDtlList.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
            //剔除同一天在其他标段的专家
            records = records.stream().filter(e -> !agencyCollect.contains(e.getId())).collect(Collectors.toList());


            //同一天在其他标段的专家。
            List<SecJGSelectionRecordDtlModel> selectionRecordDtlList = secJGSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            if (CollectionUtil.isNotEmpty(selectionRecordDtlList)) {
                Map<String, List<SecJGSelectionRecordDtlModel>> secExpertMap = selectionRecordDtlList.stream().collect(Collectors.groupingBy(SecJGSelectionRecordDtlModel::getSecId));
                if (!secExpertMap.isEmpty()) {
                    for (Map.Entry<String, List<SecJGSelectionRecordDtlModel>> item : secExpertMap.entrySet()) {
                        String secIdKey = item.getKey();
                        List<SecJGSelectionRecordDtlModel> secJGSelectionRecordDtlModels = item.getValue();
                        if (idMap.containsKey(secIdKey)) {//同一个标段抽取过的专家都不能再抽到
                            List<String> collect = secJGSelectionRecordDtlModels.stream().map(e -> e.getExId()).collect(Collectors.toList());
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        } else {
                            //同一天已经在其他标段的专家ids(确认不参加的不算)
                            List<String> collect = secJGSelectionRecordDtlModels.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
                            //剔除同一天在其他标段的专家
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        }
                    }
                }

            }

        }


        /*******************************抽取内部专家***************************/

        //抽取内部专家（公开招标：从内部-进场专家中抽取；其他类型从内部-不进场专家抽取）
        List<SecJGExpertModel> insideExpertList = records.stream().filter(e -> e.getExInsideType() == 1).collect(Collectors.toList());
        if (StrUtil.isNotBlank(secBiddingType) && "01".equals(secBiddingType)) {
            //是否进场0：非进场 1、进场
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 1).collect(Collectors.toList());
        } else {
            insideExpertList = insideExpertList.stream().filter(e -> e.getExEntry() == 0).collect(Collectors.toList());
        }
        //抽取的内部专家
        List<SecJGExpertModel> insideData = new ArrayList<>();
        if (insideExpert.getEsEconomicsNum() != 0 || insideExpert.getEsTechnologyNum() != 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                //抽取技术类专家
                List<String> exTechTypes = insideExpert.getExTechTypes();
                extractTechExpert("内部专家", exTechTypes, insideData, insideExpertList);

                //抽取经济类专家
                extractEconomicsExpert("内部专家", insideData, insideExpertList, insideExpert.getEsEconomicsNum());

                //endregion
                insideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });

            } else {
                return BaseResponse.fail("没有可用于抽取的内部专家");
            }
        }


        /*******************************抽取的外部专家***************************/
        List<SecJGExpertModel> outsideData = new ArrayList<>();
        //抽取外部专家
        List<SecJGExpertModel> outsideExpertList = records.stream().filter(e -> e.getExInsideType() == 0).collect(Collectors.toList());
        if (outsideExpert.getEsEconomicsNum() != 0 || outsideExpert.getEsTechnologyNum() != 0) {
            if (!CollectionUtils.isEmpty(outsideExpertList)) {
                //抽取技术类专家
                List<String> exTechTypes = outsideExpert.getExTechTypes();
                extractTechExpert("外部专家", exTechTypes, outsideData, outsideExpertList);

                //抽取经济类专家
                extractEconomicsExpert("外部专家", outsideData, outsideExpertList, outsideExpert.getEsEconomicsNum());

                //endregion
                outsideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的外部专家");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("insideData", insideData);
        map.put("outsideData", outsideData);

        return BaseResponse.ok(map);
    }

    /**
     * 建管定标评委抽取， 与建管专家抽取一致
     * 不区分专业，抽取内部进场和外部专家
     */
    @Override
    public BaseResponse extractJGExpertJudge(ExtractJGExpertDto request, String teId) {
        String secId = request.getSecId();
        Date srBidTime = request.getSrBidTime();
        String secBiddingType = request.getSecBiddingType();
        if (ValidateUtil.isEmpty(secId) || ValidateUtil.isEmpty(srBidTime))
            return BaseResponse.fail("获取标段信息失败");
        if (StrUtil.isBlank(secBiddingType)) {
            SecInfoResponse secInforeq = secInfoMapper.getSecInfoById(secId);
            if(secInforeq!=null){
                secBiddingType = secInforeq.getSecBiddingType();
            }
        }
        JGExpertDto insideExpert = request.getInsideExpert();
        JGExpertDto outsideExpert = request.getOutsideExpert();
        if (ValidateUtil.isEmpty(insideExpert) || ValidateUtil.isEmpty(outsideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        //需要排除的专家id
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        //是否需要排除指定单位下的专家
        SecExpertLogicalConfDTO conf = sysConfigService.getSecJGExpertLogicalConf();
        String excludeCpy = null;
        if (ValidateUtil.isNotNull(conf) && conf.getExcludeCpy()) {
            excludeCpy = request.getExcludeCpy();
        }
        List<String> avoidCompanyIds = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())) {
            avoidCompanyIds = new ArrayList<>(Arrays.asList(request.getAvoidCompanyId().split(",")));
        }
        if (StringUtils.isNotBlank(excludeCpy)) {
            avoidCompanyIds.add(excludeCpy);
        }


        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertLockPersonnelCode));


        //排除后所有专家
        List<SecJGExpertModel> records = baseMapper.getExtractExpertList(null, excludeIds, excludeCpy, teId, DateUtil.firstDayOfMonth("yyyy-MM-dd"),
                DateUtil.Date2String(new Date(), "yyyy-MM-dd"), conf.getMonthExNum(), avoidCompanyIds);
        //同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if (StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)) {
            SecJGSelectionRecordDtlDto dto = new SecJGSelectionRecordDtlDto();
            if (srBidTime != null) {
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }
            /*if(StrUtil.isNotBlank(request.getSecId())){
                dto.setNotSecIdList(Arrays.asList(request.getSecId()));
            }*/
            //需要排除被招标代理抽取的专家
            SecJGAgencySelectionRecordDtlDto requestDto = new SecJGAgencySelectionRecordDtlDto();
            requestDto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            List<SecJGAgencySelectionRecordDtlModel> selectionAgencyRecordDtlList = secJGAgencySelectionRecordDtlService.getSelectionRecordDtlList(requestDto);
            List<String> agencyCollect = selectionAgencyRecordDtlList.stream()
                    .filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1))
                    .map(e -> e.getExId()).collect(Collectors.toList());
            //剔除同一天在其他标段的专家
            records = records.stream().filter(e -> !agencyCollect.contains(e.getId())).collect(Collectors.toList());

            //同一天在其他标段的专家。
            List<SecJGSelectionRecordDtlModel> selectionRecordDtlList = secJGSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            if (CollectionUtil.isNotEmpty(selectionRecordDtlList)) {
                Map<String, List<SecJGSelectionRecordDtlModel>> secExpertMap = selectionRecordDtlList.stream().collect(Collectors.groupingBy(SecJGSelectionRecordDtlModel::getSecId));
                if (!secExpertMap.isEmpty()) {
                    for (Map.Entry<String, List<SecJGSelectionRecordDtlModel>> item : secExpertMap.entrySet()) {
                        String secIdKey = item.getKey();
                        List<SecJGSelectionRecordDtlModel> secJGSelectionRecordDtlModels = item.getValue();
                        if (secId.equals(secIdKey)) {//同一个标段抽取过的专家都不能再抽到
                            List<String> collect = secJGSelectionRecordDtlModels.stream().map(e -> e.getExId()).collect(Collectors.toList());
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        } else {
                            //同一天已经在其他标段的专家ids(确认不参加的不算)
                            List<String> collect = secJGSelectionRecordDtlModels.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
                            //剔除同一天在其他标段的专家
                            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());
                        }
                    }
                }

            }

        }

        /*******************************抽取的内部专家***************************/
        List<SecJGExpertModel> insideData = new ArrayList<>();

        List<SecJGExpertModel> insideExpertList = records.stream().filter(e -> e.getExInsideType() == 1 && e.getExEntry() == 1).collect(Collectors.toList());

        if (insideExpert.getExtractNum() != 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                extractInsideExpert("内部定标评委", insideData, insideExpertList, insideExpert.getExtractNum());
                insideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的内部定标评委");
            }
        }


        /*******************************抽取的外部专家***************************/
        List<SecJGExpertModel> outsideData = new ArrayList<>();
        //抽取外部专家
        List<SecJGExpertModel> outsideExpertList = records.stream().filter(e -> e.getExInsideType() == 0).collect(Collectors.toList());
        if (outsideExpert.getExtractNum() != 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                extractInsideExpert("外部定标评委", outsideData, outsideExpertList, outsideExpert.getExtractNum());
                outsideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的外部定标评委");
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("insideData", insideData);
        map.put("outsideData", outsideData);

        return BaseResponse.ok(map);
    }

    /**
     * 纯招标代理的业务」仅抽“外部专家”，；外部专家抽取逻辑跟建管外部专家抽取是一致的
     * 添加内部专家抽取
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse extractJGAgencyExpert(ExtractJGAgencyExpertDto request, String teId) {
        String secId = request.getSecId();//这里是标段名称。不会是标段库的。
        Date srBidTime = request.getSrBidTime();
        if (ValidateUtil.isEmpty(secId) || ValidateUtil.isEmpty(srBidTime))
            return BaseResponse.fail("获取标段信息失败");
        JGExpertDto outsideExpert = request.getOutsideExpert();
        if (ValidateUtil.isEmpty(outsideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        // 内部专家

        JGExpertDto insideExpert = request.getInsideExpert();
        if (ValidateUtil.isEmpty(insideExpert)) {
            return BaseResponse.fail("请选择抽取的专家信息");
        }

        //需要排除的专家id
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        //是否需要排除指定单位下的专家
        SecExpertLogicalConfDTO conf = sysConfigService.getSecJGAgencyExpertLogicalConf();
        String excludeCpy = null;
        if (ValidateUtil.isNotNull(conf) && conf.getExcludeCpy()) {
            excludeCpy = request.getExcludeCpy();
        }
        List<String> avoidCompanyIds = new ArrayList<>();
        if (StringUtils.isNotBlank(excludeCpy)) {
            avoidCompanyIds.add(excludeCpy);
        }

        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertLockPersonnelCode));


        //排除后所有专家
        List<SecJGExpertModel> records = baseMapper.getExtractExpertList(null, excludeIds, excludeCpy, teId, null,
                null, null, avoidCompanyIds);
        //同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if (StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)) {
            SecJGSelectionRecordDtlDto dto = new SecJGSelectionRecordDtlDto();
            SecJGAgencySelectionRecordDtlDto requestDto = new SecJGAgencySelectionRecordDtlDto();
            if (srBidTime != null) {
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
                requestDto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }

            //步骤一：需要排除同一天被建管专家库抽取的专家
            //同一天在其他标段的专家。
            List<SecJGSelectionRecordDtlModel> selectionRecordDtlList = secJGSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            //同一天已经在其他标段的专家ids(确认不参加的不算)
            List<String> collect = selectionRecordDtlList.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
            //剔除同一天在其他标段的专家
            records = records.stream().filter(e -> !collect.contains(e.getId())).collect(Collectors.toList());

            //步骤二：排除同一天代理招标抽取的专家
            List<SecJGAgencySelectionRecordDtlModel> selectionAgencyRecordDtlList = secJGAgencySelectionRecordDtlService.getSelectionRecordDtlList(requestDto);
            if (CollectionUtil.isNotEmpty(selectionAgencyRecordDtlList)) {
                Map<String, List<SecJGAgencySelectionRecordDtlModel>> secExpertMap = selectionAgencyRecordDtlList.stream().collect(Collectors.groupingBy(SecJGAgencySelectionRecordDtlModel::getSecId));
                if (!secExpertMap.isEmpty()) {
                    for (Map.Entry<String, List<SecJGAgencySelectionRecordDtlModel>> item : secExpertMap.entrySet()) {
                        String secIdKey = item.getKey();
                        List<SecJGAgencySelectionRecordDtlModel> secJGAgencySelectionRecordDtlModels = item.getValue();
                        if (secId.equals(secIdKey)) {//同一个标段抽取过的专家都不能再抽到
                            if (StrUtil.isNotBlank(request.getSrId())) {//编辑的时候，排除已经抽取的专家
                                secJGAgencySelectionRecordDtlModels = secJGAgencySelectionRecordDtlModels.stream().filter(e -> !e.getSrId().equals(request.getSrId())).collect(Collectors.toList());
                            }
                            List<String> collectAgency = secJGAgencySelectionRecordDtlModels.stream().map(e -> e.getExId()).collect(Collectors.toList());
                            records = records.stream().filter(e -> !collectAgency.contains(e.getId())).collect(Collectors.toList());
                        } else {
                            //同一天已经在其他标段的专家ids(确认不参加的不算)
                            List<String> collectAgency = secJGAgencySelectionRecordDtlModels.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend() != null && e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
                            //剔除同一天在其他标段的专家
                            records = records.stream().filter(e -> !collectAgency.contains(e.getId())).collect(Collectors.toList());
                        }
                    }
                }
            }

        }


        /*******************************抽取的外部专家***************************/
        List<SecJGExpertModel> outsideData = new ArrayList<>();
        //抽取外部专家
        List<SecJGExpertModel> outsideExpertList = records.stream().filter(e -> e.getExInsideType() == 0).collect(Collectors.toList());
        if (outsideExpert.getEsEconomicsNum() != 0 || outsideExpert.getEsTechnologyNum() != 0) {
            if (!CollectionUtils.isEmpty(outsideExpertList)) {
                //抽取技术类专家
                List<String> exTechTypes = outsideExpert.getExTechTypes();
                extractTechExpert("外部专家", exTechTypes, outsideData, outsideExpertList);

                //抽取经济类专家
                extractEconomicsExpert("外部专家", outsideData, outsideExpertList, outsideExpert.getEsEconomicsNum());

                //endregion
                outsideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的外部专家");
            }
        }

        /*******************************抽取的内部专家***************************/
        List<SecJGExpertModel> insideData = new ArrayList<>();

        List<SecJGExpertModel> insideExpertList = records.stream().filter(e -> e.getExInsideType() == 1 && e.getExEntry() == 0).collect(Collectors.toList());

        if (insideExpert.getExtractNum() != 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                extractInsideExpert("内部专家", insideData, insideExpertList, insideExpert.getExtractNum());
                insideData.forEach(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                });
            } else {
                return BaseResponse.fail("没有可用于抽取的内部专家");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("insideData", insideData);
        map.put("outsideData", outsideData);

        return BaseResponse.ok(map);

    }

    private void extractInsideExpert(String typeName, List<SecJGExpertModel> insideData, List<SecJGExpertModel> insideExpertList, int extractNum) {
        if (extractNum > 0) {
            if (!CollectionUtils.isEmpty(insideExpertList)) {
                // 随机抽取指定数量的专家
                List<SecJGExpertModel> extractedExperts = new ArrayList<>();
                extractExpert(extractedExperts, insideExpertList, extractNum);

                if (extractedExperts.size() != extractNum) {
                    throw new JPMException(typeName + "不满足抽取抽取需求 (" + typeName + ")");
                }

                insideData.addAll(extractedExperts);
            }
        }
    }

    /**
     * 经济类专家的抽取
     *
     * @param insideData       抽取到的专家信息
     * @param insideExpertList 所有专家
     * @param esEconomicsNum   需要抽取的个数
     */
    private void extractEconomicsExpert(String typeName, List<SecJGExpertModel> insideData, List<SecJGExpertModel> insideExpertList, int esEconomicsNum) {

        List<SecJGExpertModel> economics = insideExpertList.stream().filter(n -> {
            if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                if (n.getExCategory().contains(ExpertTypeEnum.Economics.getkey()))
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<SecJGExpertModel> jjData = new ArrayList<>();
        extractExpert(jjData, economics, esEconomicsNum);
        if (jjData.size() != esEconomicsNum)
            throw new JPMException(ResMsg.extractEcoError + "(" + typeName + ")");
        insideData.addAll(jjData);

    }

    /**
     * 技术类专家的抽取
     *
     * @param exTechTypes
     * @param insideData       抽取到的专家信息
     * @param insideExpertList 所有专家信息
     */
    private void extractTechExpert(String typeName, List<String> exTechTypes, List<SecJGExpertModel> insideData, List<SecJGExpertModel> insideExpertList) {
        if (!CollectionUtils.isEmpty(exTechTypes)) {
            List<SecJGExpertModel> finalInsideExpertList = insideExpertList;
            exTechTypes.forEach(t -> {
                List<String> types = Arrays.asList(t.split(","));
                List<SecJGExpertModel> technologies = finalInsideExpertList.stream().filter(n -> {
                    if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                        if (n.getExCategory().contains(ExpertTypeEnum.Technology.getkey()) && !insideData.contains(n)) {
                            if (ValidateUtil.isNotEmpty(n.getExDeclareMajorTwo()) && types.contains(n.getExDeclareMajorTwo()))
                                return true;//申报专业2满足
                            if (types.contains(n.getExDeclareMajorOne())) return true;//申报专业1满足
                        }
                    }
                    return false;
                }).collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(technologies)) {
                    //随机抽取其中一位
                    if (technologies.size() == 1)
                        insideData.add(technologies.get(0));
                    else {
                        int index = MathUtil.getRandomNumberInRange(0, technologies.size() - 1);
                        insideData.add(technologies.get(index));
                    }
                } else
                    throw new JPMException(ResMsg.extractTechError + "(" + typeName + ")");
            });
        }

    }

    private void extractExpert(List<SecJGExpertModel> datas, List<SecJGExpertModel> allExpert, int num) {
        if (num >= allExpert.size()) {
            datas.addAll(allExpert);
        } else {
            if (num > 0) {
                int index = MathUtil.getRandomNumberInRange(0, allExpert.size() - 1);
                datas.add(allExpert.get(index));
                allExpert.remove(index);
                extractExpert(datas, allExpert, num - 1);
            }
        }
    }

    @Override
    public BaseResponse getSecJGExpertWhenVacation(SecJGExpertVacationDTO request, String uid) {
        return null;
    }

    @Override
    public BaseResponse getSecJGExpertWhenVacationNoId(SecJGExpertVacationDTO request, String uid) {
        return null;
    }

    @Override
    public BaseResponse getSecJGExpertByCurrent(String uid) {
        return null;
    }

    @Override
    public List<SecJGExpertModel> getExpertListByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        return baseMapper.getExpertListByIds(ids);
    }

    @Override
    public void updateExpertStateBath(List<String> ids, Integer state) {
        if (CollectionUtils.isEmpty(ids) || ValidateUtil.isNull(state)) return;
        List<SecJGExpert> entities = baseMapper.selectBatchIds(ids);
        if (!CollectionUtils.isEmpty(entities)) {
            Date now = new Date();
            entities.forEach(e -> {
                e.setExState(state);
                e.setExStateTime(now);
            });
            this.updateBatchById(entities);
            sysFileService.updateFileStateBatchIds(entities.stream().map(SecJGExpert::getId).collect(Collectors.toList()), state);
        }
    }

    private Map<String, String> getDictToMap(List<LinkedHashMap> originalList, String mKey, String mValue) {
        Map<String, String> resultMap = new HashMap<>(16);
        if (CollectionUtil.isNotEmpty(originalList)) {
            for (LinkedHashMap linkedHashMap : originalList) {
                resultMap.put((String) linkedHashMap.get(mKey), (String) linkedHashMap.get(mValue));
            }
        }
        return resultMap;
    }


    private void checkExcelExpert(int row, int column, String msg) {
        String cellRef = CellReference.convertNumToColString(column);
        throw new JPMException("第" + row + "行第" + cellRef + "列，" + msg);
    }


    private BaseResponse createOrUpdate(SecJGExpertModel request, boolean idCard, boolean only) {
        Integer exInsideType = request.getExInsideType();
        if (exInsideType == null) return BaseResponse.fail(ResMsg.notReqParam);
        SecJGExpert entity = new SecJGExpert();
        BeanUtils.copyProperties(request, entity);
        SecJGExpert model = null;
        if (idCard) {
            if (ValidateUtil.isAllEmpty(request.getExIdNumber(), request.getId())) {
                if (exInsideType == 1) {
                    //创建数据时 传入身份证以传入的为准 没传入身份证则根据用户信息查找身份证信息
                    if (ValidateUtil.isNotEmpty(request.getuId())) {
                        SysUser user = sysUserService.getById(request.getuId());
                        entity.setExIdNumber(user.getUIdcard());
                    } else entity.setExIdNumber(null);
                } else {//外部用户
                    return BaseResponse.fail("身份证号不能为空");
                }
            } else if (ValidateUtil.isNotEmpty(request.getId())) {
                model = baseMapper.selectById(request.getId());
                if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
                //编辑数据时如果不修改则取旧值
                if (ValidateUtil.isEmpty(entity.getExIdNumber()))
                    entity.setExIdNumber(model.getExIdNumber());
            }
        }
        if (ValidateUtil.isNotEmpty(entity.getExIdNumber())) {
            entity.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
        }
        if (only && expertExists(exInsideType, entity.getExPlaintextNum(), entity.getExApplicantId(), request.getId(), request.getTeId()))
            return BaseResponse.fail(ResMsg.recordExist);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setExCreateTime(new Date());
            entity.setExState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(),
                    request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), entity.getId());
        } else {
            entity.setExCreateTime(model.getExCreateTime());
            entity.setExCreateUid(model.getExCreateUid());
            entity.setExCreateUname(model.getExCreateUname());
            entity.setExState(model.getExState());
            entity.setExStateTime(model.getExStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()),
                    Arrays.asList(request.getExCardFileIds(), request.getExDegreeIds(), request.getExPerformFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExAvoidFileIds(), request.getExOtherFileIds(), request.getFileIds()), entity.getId());
        }
        secJGExpertCertificateService.createOrUpdateSecExpertCertificate(request.getSecJGExpertCertificates(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 申请人ID唯一
     * 校验身份证年龄
     *
     * @return
     */
    public boolean expertExists(Integer exInsideType, String idNum, String uid, String id, String teId) {
        SecExpertLogicalConfDTO conf = sysConfigService.getSecJGExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<SecJGExpert> wrapper = new QueryWrapper<>();
        if (exInsideType == 1) {//内部专家才有申请人字段
            wrapper.eq("Ex_Applicant_Id", uid);
        } else {//外部通过身份证号
            wrapper.eq("Ex_Plaintext_Num", idNum);
        }
        //wrapper.eq("Ex_Inside_Type", exInsideType);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

}
