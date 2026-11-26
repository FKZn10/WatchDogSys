package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.utils.*;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.easyexcel.EasyExcelData;
import com.jpmsoft.projectProducer.enums.ExpertTypeEnum;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.other.TaskMapper;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.*;
import com.jpmsoft.projectProducer.pojo.dto.system.DictDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.*;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertVacationVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.*;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecExpertImpl extends ServiceImpl<SecExpertMapper, SecExpert> implements ISecExpertService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;
    @Resource
    private ISecExpertCertificateService secExpertCertificateService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecDocumentService secDocumentService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private ISecFocusExpertService secFocusExpertService;
    @Resource
    private ISecExpertPauseService secExpertPauseService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ISecExpertAuditService secExpertAuditService;
    @Resource
    private ISecExpertChangeService secExpertChangeService;
    @Resource
    private ISecExpertDeleteService secExpertDeleteService;

    @Override
    public BaseResponse resetPlaintextNum() {
        QueryWrapper<SecExpert> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("Ex_Id_Number");
        wrapper.isNull("Ex_Plaintext_Num");
        List<SecExpert> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data = data.stream().peek(d -> {
                d.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber()));
            }).collect(Collectors.toList());
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpert(SecExpertModel request) {
        return createOrUpdate(request, false, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpertCurrent(SecExpertModel request) {
        return createOrUpdate(request, true, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpertVerify(SecExpertModel request) {
        return createOrUpdate(request, true, true);
    }


    public BaseResponse createOrUpdate(SecExpertModel request, boolean idCard, boolean only) {
        if (ValidateUtil.isNull(request.getYear())) {
            request.setYear(DateUtil.getTimeYear(new Date()));
        }
        if (ValidateUtil.isNotEmpty(request.getNeedAuditAuth()) && "01".equals(request.getNeedAuditAuth())) {
            LogUserInfoVo userInfo = UserUtils.getUserInfo();
            long count = secExpertAuditService.count(Wrappers.<SecExpertAudit>lambdaQuery()
                    .eq(SecExpertAudit::getYear, request.getYear())
                    .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                    .eq(SecExpertAudit::getSeaCid, userInfo.getCId())
            );
            if (count > 0) {
                return BaseResponse.fail("当前用户所在企业已提交专家审核申请，无法添加新的专家信息");
            }
        }
        SecExpert entity = new SecExpert();
        BeanUtils.copyProperties(request, entity);
        SecExpert model = null;
        if (idCard) {
            if (ValidateUtil.isAllEmpty(request.getExIdNumber(), request.getExId())) {
                //创建数据时 传入身份证以传入的为准 没传入身份证则根据用户信息查找身份证信息
                if (ValidateUtil.isNotEmpty(request.getuId())) {
                    SysUser user = sysUserService.getById(request.getuId());
                    entity.setExIdNumber(user.getUIdcard());
                } else entity.setExIdNumber(null);
            } else if (ValidateUtil.isNotEmpty(request.getExId())) {
                model = baseMapper.selectById(request.getExId());
                if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
                //编辑数据时如果不修改则取旧值
                if (ValidateUtil.isEmpty(entity.getExIdNumber()))
                    entity.setExIdNumber(model.getExIdNumber());
            }
        }
        if (ValidateUtil.isNotEmpty(entity.getExIdNumber()))
            entity.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
        if (only && expertExists(entity.getExPlaintextNum(), entity.getExWorkTime(),entity.getExApplicantId(), request.getExId(), request.getTeId(), request.getYear()))
            return BaseResponse.fail(ResMsg.recordExist);
        if (ValidateUtil.isEmpty(request.getExId())) {
            entity.setExId(GenerateUUID.getUUID());
            entity.setExCreateTime(new Date());
            entity.setExState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setExChangeStatus("04");
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), entity.getExId());
        } else {
            entity.setExCreateTime(model.getExCreateTime());
            entity.setExCreateUid(model.getExCreateUid());
            entity.setExCreateUname(model.getExCreateUname());
            entity.setExState(model.getExState());
            entity.setExStateTime(model.getExStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), Arrays.asList(request.getExCardFileIds(), request.getExDegreeIds(), request.getExPerformFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExAvoidFileIds(), request.getExOtherFileIds(), request.getFileIds()), entity.getExId());
        }
        secExpertCertificateService.createOrUpdateSecExpertCertificate(request.getSecExpertCertificates(), entity.getExId());
        return BaseResponse.ok(entity.getExId());
    }

    @Override
    public BaseResponse getSecExpertList(SecExpertModel request) {
        if (ValidateUtil.isEmpty(request.getAuthSql())) {
            request.setAuthSql(" where 1=1 ");
        }
        if (ValidateUtil.isNull(request.getYear())) {
            request.setYear(DateUtil.getTimeYear(new Date()));
        }
        QueryMap<SecExpertModel> params = this.getQueryMap(request);
        List<SecExpertModel> datas = baseMapper.getSecExpertList(params.getQuery());
        Integer total = baseMapper.getSecExpertListCount(params.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecExpertModel> data = datas.stream().peek(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            }).collect(Collectors.toList());
            return BaseResponse.ok(data, total);
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecExpertCjDzList(SecExpertModel request, String token) {
        // 查询角色
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        BaseResponse res = userProducerFeign.getUserRole(user.getId());
        if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
            List<String> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), String.class);
            if (ValidateUtil.isNotEmpty(data)) {
                // 角色校验
                if (data.contains("集团专家库管理员")) {
                    request.setExApplicantId1(null);
                    request.setExApplicantCId1(null);
                } else if (data.contains("企业专家库管理员")) {
                    request.setExApplicantId1(null);
                    request.setExApplicantCId1(user.getCId());
                } else {
                    request.setExApplicantId1(user.getId());
                    request.setExApplicantCId1(null);
                }
            }
        }
        // 权限校验
        String authSql;
        if (sysUserService.checkShowAllDataAuth(user.getId())) {
           authSql = " where 1=1 ";
           request.setExApplicantId1(null);
           request.setExApplicantCId1(null);
        } else {
           authSql = " where 1=1 " + sysUserService.ShowCancelData(user.getId(), "Ex_Create_Uid", "Ex_State");
        }
        request.setAuthSql(authSql);
        // 不是今年的数据可以查询全部的
        // if (!Objects.equals(request.getYear(), DateUtil.getTimeYear(new Date()))) {
        //     request.setExStatus(null);
        // }
        return getSecExpertList(request);
    }

    @Override
    public Workbook exportExcelSecExpertCjDzList(SecExpertModel request, String uId) {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();

        String role = sysConfigService.getConfigByCode("cjDzSecExpertAuthList");
        if (ValidateUtil.isNotEmpty(role)) {
            Boolean auth = false;
            List<String> roles = Arrays.asList(role.split(","));
            BaseResponse res = userProducerFeign.getUserRole(uId);
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
            if (auth) {
                request.setExApplicantId(null);
            }
            else {
                request.setExApplicantId(uId);
            }
        }

        QueryMap<SecExpertModel> params = this.getQueryMap(request);
        List<SecExpertModel> datas = baseMapper.getSecExpertList(params.getQuery());
        List<SecExpertModel> models = new ArrayList<>();
        if (!CollectionUtils.isEmpty(datas)) {
            models = datas.stream().peek(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            }).collect(Collectors.toList());
        }

        String[] rowTitle = new String[]{"姓名", "身份证号", "学历", "专业", "参加集团系统工作年月", "参加集团系统工作时间", "银行账号", "开户行", "专业技术职称", "通讯方式", "专家类型", "现工作单位", "专家申报专业1", "专家申报专业2", "本人认为需要回避的信息", "流程状态", "创建时间", "创建人"};

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
            for (SecExpertModel model : models) {
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

                cell = row.createCell(4);
                cell.setCellValue(null == model.getExWorkTime() ? "" : DateFormatUtil.dateToString(model.getExWorkTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH));
                cell.setCellStyle(style);

                //参加集团系统工作时间
                cell = row.createCell(5);
                cell.setCellValue(null == model.getExWorkTime() ? "" : String.valueOf(DateFormatUtil.getYears(DateFormatUtil.dateToString(model.getExWorkTime(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH) + "-01", DateFormatUtil.dateToString(new Date(), DateFormatUtil.FORMAT_PATTERN_OF_MONTH) + "-01")) + "年");
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(model.getExBankAccount());
                cell.setCellStyle(style);


                cell = row.createCell(7);
                cell.setCellValue(model.getExBank());
                cell.setCellStyle(style);

                //专业技术职称
                cell = row.createCell(8);
                cell.setCellValue(StringUtils.isBlank(model.getExTitle()) ? "" : DictionaryUtils.getTitle("JPMsoft_ZYJSZC", model.getExTitle()));
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(model.getExHomePhone());
                cell.setCellStyle(style);

                //专家类型
                cell = row.createCell(10);
                cell.setCellValue(StringUtils.isBlank(model.getExCategory()) ? "" : DictionaryUtils.getTitle("JPMsoft_ZJLB", model.getExCategory()));
                cell.setCellStyle(style);

                //现工作单位
                cell = row.createCell(11);
                cell.setCellValue(model.getExApplicantCName());
                cell.setCellStyle(style);

                //专家申报专业1
                cell = row.createCell(12);
                cell.setCellValue(StringUtils.isBlank(model.getExDeclareMajorOne()) ? "" : DictionaryUtils.getTitle("JPMsoft_SBZYYI", model.getExDeclareMajorOne()));
                cell.setCellStyle(style);

                //专家申报专业2
                cell = row.createCell(13);
                cell.setCellValue(StringUtils.isBlank(model.getExDeclareMajorTwo()) ? "" : DictionaryUtils.getTitle("JPMsoft_SBZYER", model.getExDeclareMajorTwo()));
                cell.setCellStyle(style);

                cell = row.createCell(14);
                cell.setCellValue(model.getExAvoid());
                cell.setCellStyle(style);

                //流程状态
                cell = row.createCell(15);
                cell.setCellValue(null == model.getExState() ? "" : DictionaryUtils.getTitle("JPMsoft_LCZT", String.valueOf(model.getExState())));
                cell.setCellStyle(style);

                cell = row.createCell(16);
                cell.setCellValue(null == model.getExCreateTime() ? "" : DateFormatUtil.dateToString(model.getExCreateTime(), DateFormatUtil.FORMAT_PATTERN_OF_DAY));
                cell.setCellStyle(style);

                cell = row.createCell(17);
                cell.setCellValue(model.getExCreateUname());
                cell.setCellStyle(style);

            }
        }
        return workbook;
    }

    @Override
    public BaseResponse getSecExpertById(String exId) {
        SecExpertModel data = baseMapper.getSecExpertById(exId);
        if (data != null) {
            if (ValidateUtil.isNotEmpty(data.getExIdNumber())) {
                String idCard = apiUtilsService.SelfSm2Decrypt(data.getExIdNumber());
                if (ValidateUtil.isNotEmpty(idCard)) {
                    data.setExAge(IdcardUtil.getAgeByIdCard(idCard));
                    data.setExIdNumber(VoInfoUtil.handleUserIdCard(idCard));
                }
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecExpertById(String exId) {
        SecExpert model = baseMapper.selectById(exId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (secSelectionRecordDtlService.getExpertCount(exId) > 0) return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(exId);
        sysFileService.deleteFileById(exId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse extractExpertInfo(ExtractExpertDto request, String teId) {
        if (request.getEsTechnologyNum() + request.getEsEconomicsNum() == 0) return BaseResponse.ok();
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
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())){
            avoidCompanyIds = Arrays.asList(request.getAvoidCompanyId().split(","));
        }
        if (StringUtils.isNotBlank(excludeCpy)){
            avoidCompanyIds.add(excludeCpy);
        }
        //排除后所有专家
        Integer year;
        if (ValidateUtil.isNotNull(request.getSrBidTime())) {
            year = DateUtil.getTimeYear(request.getSrBidTime());
        } else {
            year = DateUtil.getTimeYear(new Date());
        }
        List<SecExpertModel> records = baseMapper.getExtractExpertList(excludeIds, excludeCpy, teId, null, null, null, avoidCompanyIds, null, year);
        if (!CollectionUtils.isEmpty(records)) {
            List<SecExpertModel> datas = new ArrayList<>();
            //技术类专家
            List<SecExpertModel> technologies = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey())) {
                        if (!CollectionUtils.isEmpty(request.getExTechTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (request.getExTechTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(datas, technologies, request.getEsTechnologyNum());
            records.removeAll(datas);
            //经济类专家
            List<SecExpertModel> economics = records.stream().filter(n -> {
                if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                    if (n.getExCategory().equals(ExpertTypeEnum.Economics.getkey())) {
                        if (!CollectionUtils.isEmpty(request.getExEconTypes())) {
                            String[] types = n.getExType().split(",");
                            for (String t : types)
                                if (request.getExEconTypes().contains(t)) return true;
                        } else return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            extractExpert(datas, economics, request.getEsEconomicsNum());
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse extractExpert(ExtractExpertDto request, String teId) {
        if (request.getEsTechnologyNum() + request.getEsEconomicsNum() == 0) {
            return BaseResponse.ok();
        }
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
        if (StringUtils.isNotBlank(request.getAvoidCompanyId())){
            avoidCompanyIds = new ArrayList<>(Arrays.asList(request.getAvoidCompanyId().split("[,;；]")));
        }
        if (StringUtils.isNotBlank(excludeCpy)){
            avoidCompanyIds.add(excludeCpy);
        }


        String secExpertAvoidedUnitCode = "secExpertAvoidedUnit";
        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        //配置的需要回避企业。
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertAvoidedUnitCode, secExpertLockPersonnelCode));
        String secExpertAvoidedUnit = (String) configMap.get(secExpertAvoidedUnitCode);
        if(StrUtil.isNotBlank(secExpertAvoidedUnit) && ValidateUtil.isNotNull(excludeCpy)){
            List<SecExpertAvoidedUnitDto> secExpertAvoidedUnitDtoList=JacksonUtils.parseArray(secExpertAvoidedUnit, SecExpertAvoidedUnitDto.class);
            if(!CollectionUtils.isEmpty(secExpertAvoidedUnitDtoList)){
                for (SecExpertAvoidedUnitDto secExpertAvoidedUnitDto : secExpertAvoidedUnitDtoList) {
                     if(secExpertAvoidedUnitDto.getUnit().contains(excludeCpy)){
                         avoidCompanyIds.addAll(Arrays.asList(secExpertAvoidedUnitDto.getAvoidedUnit().split(",")));
                     }
                }
            }
        }
        // 查询该标段的重点专家申请表是否存在并已结束
        long count = 0;
        if (ValidateUtil.isEmpty(request.getKeyExpertSelection())) {
            count = secFocusExpertService.count(Wrappers.<SecFocusExpert>lambdaQuery()
                    .eq(SecFocusExpert::getSecId, request.getSecId())
                    .eq(SecFocusExpert::getState, ApprovalStatusEnum.Already_Over.getIndex())
            );
        } else if ("1".equals(request.getKeyExpertSelection())) {
            count = 1;
        }

        Integer year;
        if (ValidateUtil.isNotNull(request.getSrBidTime())) {
            year = DateUtil.getTimeYear(request.getSrBidTime());
        } else {
            year = DateUtil.getTimeYear(new Date());
        }

        //所有专家
        List<SecExpertModel> records = baseMapper.getExtractExpertList(
                excludeIds,
                excludeCpy,
                teId,
                DateUtil.firstDayOfMonth("yyyy-MM-dd"),
                DateUtil.Date2String(new Date(), "yyyy-MM-dd"),
                conf.getMonthExNum(),
                avoidCompanyIds,
                count > 0 ? "1" : null,
                year
        );

        //同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        //【进一步优化逻辑】：专家若被标段A抽中，则专家在该日期将不再被抽中，即使专家确认不参加标段A也不可再被该日期开标的其他标段抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if(StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)){
            SecSelectionRecordDtlDto dto = new SecSelectionRecordDtlDto();
            Date srBidTime = request.getSrBidTime();
            if(srBidTime!=null){
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }
            if(StrUtil.isNotBlank(request.getSecId())){
                dto.setNotSecIdList(Arrays.asList(request.getSecId()));
            }
            //同一天在其他标段的专家。
            List<SecSelectionRecordDtlModel> selectionRecordDtlList = secSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            //同一天已经在其他标段的专家ids(确认不参加的不算)
            //List<String> collect = selectionRecordDtlList.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend()!=null &&  e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
            //同一天已经在其他标段的专家ids
            List<String> collect = selectionRecordDtlList.stream().map(SecSelectionRecordDtlModel::getExId).collect(Collectors.toList());
            //剔除同一天在其他标段的专家
            records=records.stream().filter(e->!collect.contains(e.getExId())).collect(Collectors.toList());
        }
        log.info("排除后可供抽取的专家：{}", JSON.toJSON(records));
        List<SecExpertModel> data = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(records) && records.size() >= request.getEsTechnologyNum() + request.getEsEconomicsNum()) {
            List<SecExpertModel> finalRecords = records;
            if (ValidateUtil.isEmpty(request.getKeyExpertSelection())) {
                if (!CollectionUtils.isEmpty(request.getExTechTypes())) {
                    //region抽取技术类专家
                    request.getExTechTypes().forEach(t -> {
                        List<String> types = Arrays.asList(t.split(","));
                        List<SecExpertModel> technologies = finalRecords.stream().filter(n -> {
                            if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                                if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey()) && !data.contains(n)) {
                                    if (ValidateUtil.isNotEmpty(n.getExDeclareMajorTwo()) && types.contains(n.getExDeclareMajorTwo())) {
                                        return true;//申报专业2满足
                                    }
                                    return types.contains(n.getExDeclareMajorOne());//申报专业1满足
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(technologies)) {
                            //随机抽取其中一位
                            if (technologies.size() == 1) {
                                data.add(technologies.get(0));
                            } else {
                                int index = MathUtil.getRandomNumberInRange(0, technologies.size() - 1);
                                data.add(technologies.get(index));
                            }
                        } else {
                            throw new JPMException(ResMsg.extractTechError);
                        }
                    });
                    //endregion
                }

                //region 抽取经济类专家
                //经济类专家
                List<SecExpertModel> economics = finalRecords.stream().filter(n -> {
                    if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                        return n.getExCategory().equals(ExpertTypeEnum.Economics.getkey());
                    }
                    return false;
                }).collect(Collectors.toList());
                List<SecExpertModel> jjData = new ArrayList<>();
                extractExpert(jjData, economics, request.getEsEconomicsNum());
                if (jjData.size() != request.getEsEconomicsNum()) {
                    throw new JPMException(ResMsg.extractEcoError);
                }
                data.addAll(jjData);
                //endregion
                data.forEach(d-> d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber()))));
            } else {
                List<SecExpertModel> pool = new ArrayList<>(finalRecords);
                if (pool.size() < request.getEsTechnologyNum()) {
                    return BaseResponse.fail("专家数量不足！");
                }
                while (data.size() < request.getEsTechnologyNum()) {
                    int index = (pool.size() == 1) ? 0 : MathUtil.getRandomNumberInRange(0, pool.size() - 1);
                    data.add(pool.get(index));
                    pool.remove(index);
                }
            }
        } else {
            return BaseResponse.fail(ResMsg.noExpert2Extract);
        }
        return BaseResponse.ok(data);
    }


    private void extractExpert(List<SecExpertModel> datas, List<SecExpertModel> allExpert, int num) {
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
    public boolean expertExists(String idNum, String id, String teId) {
        idNum = apiUtilsService.SelfSm2Decrypt(idNum);
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<SecExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Plaintext_Num", idNum);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    /**
     * 申请人ID唯一
     * 校验身份证年龄
     *
     * @return
     */
    public boolean expertExists(String idNum, String uid, String id, String teId) {
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<SecExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Applicant_Id", uid);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    public boolean expertExists(String idNum,Date exWorkTime ,String uid, String id, String teId, Integer year) {
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        /*Integer exSysWorkYearLimit = conf.getExSysWorkYear();
        if(exSysWorkYearLimit!=-1){//-1不限制
            Integer workYear = getWorkYear(exWorkTime);
            if (workYear < exSysWorkYearLimit) throw new JPMException("参加集团系统工作时间最小为"+exSysWorkYearLimit+"年");
        }*/
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<SecExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Applicant_Id", uid);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.eq(ValidateUtil.isNotNull(year), "year", year);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    /**
     * 获取工作年限
     * @param workTime 工作时间
     * @return
     */
    private Integer getWorkYear(Date workTime){
        // 入职日期
        LocalDate hireDate = LocalDate.parse(cn.hutool.core.date.DateUtil.format(workTime,"yyyy-MM-dd"));
        // 当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算工作年限
        Period experience = Period.between(hireDate, currentDate);
        int years = experience.getYears();
        return years;
    }

    @Override
    public void updateSecExpertStatus(String exId, Integer status) {
        baseMapper.updateSecExpertStatus(exId, status);
    }

    @Override
    public BaseResponse getSecExpertWhenVacation(SecExpertVacationDTO request, String uId) {
        return getSecExpertWhenVacation(request, uId, true);
    }

    @Override
    public BaseResponse getSecExpertWhenVacationNoId(SecExpertVacationDTO request, String uId) {
        return getSecExpertWhenVacation(request, uId, false);
    }


    public BaseResponse getSecExpertWhenVacation(SecExpertVacationDTO request, String uId, boolean idCard) {
        if (ValidateUtil.isEmpty(request.getSecId()) || ValidateUtil.isNull(request.getSevTime()))
            return BaseResponse.fail(ResMsg.notReqParam);
        //region 获取专家信息以及选取信息
        Date bidTime = secDocumentService.getBidTime(request.getSecId());
        if (bidTime == null) return BaseResponse.fail(ResMsg.secDocumentNoBidTime);
        SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
        Date latestTime = DateUtil.DateChangeHour(DateUtil.str2date(DateUtil.Date2String(bidTime, "yyyy-MM-dd 00:00:00")), -1 * conf.getVacationHours());
        if (latestTime.getTime() < request.getSevTime().getTime()) return BaseResponse.fail(ResMsg.vacationLatestTime);
        if (idCard) {
            SysUser user = sysUserService.getById(uId);
            if (ValidateUtil.isEmpty(user.getUPlaintextNum())) return BaseResponse.fail(ResMsg.userNotPlaintextNum);
            SecExpertVacationVO data = baseMapper.getSecExpertWhenVacation(user.getUPlaintextNum(), request.getSecId());
            if (data == null) return BaseResponse.fail(ResMsg.unExpertSelect2Vacation);
            data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
            return BaseResponse.ok(data);
        } else {
            List<SecExpertVacationVO> data = baseMapper.getAllSecExpertWhenVacation(request.getSecId());
            data.forEach(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });
            return BaseResponse.ok(data);
        }
        //endregion
    }

    @Override
    public BaseResponse getSecExpertByCurrent(String uId) {
        SysUser user = sysUserService.getById(uId);
        QueryWrapper<SecExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Plaintext_Num", user.getUPlaintextNum());
        List<SecExpert> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            SecExpertModel model = new SecExpertModel();
            BeanUtils.copyProperties(data.get(0), model);
            model.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(model.getExIdNumber())));
            return BaseResponse.ok(model);
        }
        return BaseResponse.ok();
    }

    @Override
    public void updateExpertStateBath(List<String> ids, Integer state) {
        if (CollectionUtils.isEmpty(ids) || ValidateUtil.isNull(state)) return;
        List<SecExpert> entities = baseMapper.selectBatchIds(ids);
        if (!CollectionUtils.isEmpty(entities)) {
            Date now = new Date();
            entities.forEach(e -> {
                e.setExState(state);
                e.setExStateTime(now);
            });
            this.updateBatchById(entities);
            sysFileService.updateFileStateBatchIds(entities.stream().map(SecExpert::getExId).collect(Collectors.toList()), state);
        }
    }

    @Override
    public List<SecExpertModel> getExpertListByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        return baseMapper.getExpertListByIds(ids);
    }

    @Override
    public List<SecExpertModel> getExtractExpertList(String teId) {
        Integer year = DateUtil.getTimeYear(new Date());
        return baseMapper.getExtractExpertList(null, null, teId, null, null, null, null, null, year);
    }

    @Override
    public List<SecExpertModel> getExtractExpertList(List<String> avoidUser, List<String> avoidUnit) {
        return baseMapper.getOutExtractExpertList(avoidUser,avoidUnit);
    }

    @Override
    public void checkExpertWorkTime(List<String> exIdList) {
        List<SecExpertModel> expertModelList = baseMapper.getExpertListByIds(exIdList);
        if(CollectionUtil.isNotEmpty(expertModelList)){
            SecExpertLogicalConfDTO conf = sysConfigService.getSecExpertLogicalConf();
            Integer exSysWorkYearLimit = conf.getExSysWorkYear();
            if(ValidateUtil.isNotNull(exSysWorkYearLimit) && exSysWorkYearLimit!=-1){//-1不限制
                for (SecExpertModel secExpertModel : expertModelList) {
                    // Integer workYear = getWorkYear(secExpertModel.getExWorkTime());
                    Integer workYear = secExpertModel.getExWorkYear();
                    if (ValidateUtil.isNotNull(workYear)) {
                        if (workYear < exSysWorkYearLimit) throw new JPMException("存在参加集团系统工作时间不足"+exSysWorkYearLimit+"年的专家，请确认！");
                    }
                }
            }
        }

    }

    @Override
    public BaseResponse updatePersonnel(SecExpertModel request) {
        if(ValidateUtil.isNotNull(request)){
            if(ValidateUtil.isEmpty(request.getExId())) return  BaseResponse.fail("缺少必要参数");
            SecExpert secExpert = new SecExpert();
            secExpert.setExId(request.getExId());
            secExpert.setPersonnelState(request.getPersonnelState());
            secExpert.setPersonnelTimeStr(request.getPersonnelTimeStr());
            secExpert.setPersonnelTimeEnd(request.getPersonnelTimeEnd());
            baseMapper.updateById(secExpert);
        }
        return BaseResponse.ok();
    }

    /**
     * TODO 导入专家库信息
     *
     * @param file
     * @param userInfoByToken
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importExcelSecExpertCjDzList(MultipartFile file, LogUserInfoVo userInfoByToken) {
        List<SecExpertExcelDTO> datas = new EasyExcelData<SecExpertExcelDTO>().getExcelData(file, SecExpertExcelDTO.class, 1);
        if (ValidateUtil.isEmpty(datas)) {
            return BaseResponse.fail(ResMsg.noData);
        }
        // 构建专家库信息所需字典
        List<DictDto> jpMsoftSbzyyi = DictionaryUtils.getDictList("JPMsoft_SBZYYI");
        List<DictDto> jpMsoftSbzyer = DictionaryUtils.getDictList("JPMsoft_SBZYER");
        List<DictDto> jpMsoftZyjszc = DictionaryUtils.getDictList("JPMsoft_ZYJSZC");
        List<DictDto> jpMsoftZjlb = DictionaryUtils.getDictList("JPMsoft_ZJLB");
        // 字典转换
        Map<String, String> sbzyyiMap = jpMsoftSbzyyi.stream().collect(Collectors.toMap(DictDto::getName, DictDto::getCode));
        Map<String, String> sbzyerMap = jpMsoftSbzyer.stream().collect(Collectors.toMap(DictDto::getName, DictDto::getCode));
        Map<String, String> zyjszcMap = jpMsoftZyjszc.stream().collect(Collectors.toMap(DictDto::getName, DictDto::getCode));
        Map<String, String> zjlbMap = jpMsoftZjlb.stream().collect(Collectors.toMap(DictDto::getName, DictDto::getCode));
        // 处理的专家库id
        List<String> handleExpertIds = new ArrayList<>();
        // 更新的专家库信息
        List<SecExpert> updateExpertList = new ArrayList<>();
        // 新增的专家库信息
        List<SecExpert> addExpertList = new ArrayList<>();
        // 临时
        String tCode = "SecExpert01";
        String tId = "dc153121-96ff-441d-9cdc-6ef6aec9db2c";
        for (SecExpertExcelDTO dto : datas) {
            // 查询用户信息
            SysUser user = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUName, dto.getExName()).eq(SysUser::getUMtel, dto.getExHomePhone()));
            if (ValidateUtil.isNull(user)) {
                return BaseResponse.fail("未查询到专家【" + dto.getExName() + "】的用户信息，请确认后重新导入");
            }
            // 先查询该专家是否已经存在
            SecExpert expert = this.getOne(Wrappers.<SecExpert>lambdaQuery()
                    .eq(SecExpert::getCId, user.getCId())
                    .eq(SecExpert::getExName, dto.getExName())
                    .eq(SecExpert::getExHomePhone, dto.getExHomePhone())
                    .ne(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())
                    .last(" limit 1 ")
            );
            if (ValidateUtil.isNotNull(expert)) {
                // 更新专家信息
                if (ValidateUtil.isNotEmpty(dto.getExDeclareMajorOne())) {
                    expert.setExDeclareMajorOne(sbzyyiMap.get(dto.getExDeclareMajorOne()));
                }
                if (ValidateUtil.isNotEmpty(dto.getExDeclareMajorTwo())) {
                    expert.setExDeclareMajorTwo(sbzyerMap.get(dto.getExDeclareMajorTwo()));
                }
                if (ValidateUtil.isNotNull(dto.getExSysWorkYear())) {
                    expert.setExSysWorkYear(dto.getExSysWorkYear());
                }
                if (ValidateUtil.isNotEmpty(dto.getExTitle())) {
                    expert.setExTitle(zyjszcMap.get(dto.getExTitle()));
                }
                if (ValidateUtil.isNotEmpty(dto.getExCategory())) {
                    expert.setExCategory(zjlbMap.get(dto.getExCategory()));
                }
                if (ValidateUtil.isNotEmpty(user.getUIdcard()) && ValidateUtil.isEmpty(expert.getExIdNumber())) {
                    expert.setExIdNumber(user.getUIdcard());
                    expert.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(user.getUPlaintextNum()));
                }
                expert.setExState(ApprovalStatusEnum.Already_Over.getIndex());
                expert.setExStateTime(new Date());
                expert.setCId(user.getCId());
                expert.setExApplicantId(user.getUId());
                // file信息
                SysFile sysFile = sysFileService.getById(expert.getExId());
                sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
                handleExpertIds.add(expert.getExId());
                sysFileService.saveFile(expert.getExId(), tId, expert.getExState() + "", expert.getExCreateUid(), expert.getExCreateUname(), expert.getExCreateTime(), null, sysFile.getFiletitle(), tCode, "update", sysFile.getFileRelationId(), null, tCode, null);
                updateExpertList.add(expert);
            } else {
                // 新增专家信息
                SecExpert secExpert = new SecExpert();
                secExpert.setExId(GenerateUUID.getUUID());
                secExpert.setCId(user.getCId());
                secExpert.setExApplicantId(user.getUId());
                secExpert.setExName(dto.getExName());
                secExpert.setExTitle(zyjszcMap.get(dto.getExTitle()));
                secExpert.setExHomePhone(dto.getExHomePhone());
                secExpert.setExCreateUid(user.getUId());
                secExpert.setExCreateUname(user.getUName());
                secExpert.setExCreateTime(new Date());
                secExpert.setExState(ApprovalStatusEnum.Already_Over.getIndex());
                secExpert.setExStateTime(new Date());
                secExpert.setExCategory(zjlbMap.get(dto.getExCategory()));
                secExpert.setExStatus(1);
                secExpert.setExSysWorkYear(dto.getExSysWorkYear());
                String fileRelationId = GenerateUUID.getUUID();
                secExpert.setExCardFile(GenerateUUID.getUUID());
                secExpert.setExDegree(GenerateUUID.getUUID());
                secExpert.setExPerformFile(GenerateUUID.getUUID());
                secExpert.setExTitleFile(GenerateUUID.getUUID());
                secExpert.setExPromiseFile(GenerateUUID.getUUID());
                secExpert.setExAvoidFile(GenerateUUID.getUUID());
                secExpert.setExOtherFile(GenerateUUID.getUUID());
                if (ValidateUtil.isNotEmpty(user.getUIdcard()) && ValidateUtil.isEmpty(secExpert.getExIdNumber())) {
                    secExpert.setExIdNumber(user.getUIdcard());
                    secExpert.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(user.getUPlaintextNum()));
                }

                handleExpertIds.add(secExpert.getExId());
                addExpertList.add(secExpert);

                sysFileService.saveFile(secExpert.getExId(), tId, secExpert.getExState() + "", secExpert.getExCreateUid(), secExpert.getExCreateUname(), secExpert.getExCreateTime(), null, "专家信息备案", tCode, "create", fileRelationId, null, tCode, null);
                sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(secExpert.getExCardFile(), secExpert.getExDegree(), secExpert.getExPerformFile(), secExpert.getExTitleFile(), secExpert.getExPromiseFile(), secExpert.getExAvoidFile(), secExpert.getExOtherFile(), fileRelationId), secExpert.getExId());
            }
        }
        // 其他的专家作废
        if (ValidateUtil.isNotEmpty(handleExpertIds)) {
            List<SecExpert> list = this.list(Wrappers.<SecExpert>lambdaQuery().notIn(SecExpert::getExId, handleExpertIds));
            if (ValidateUtil.isNotEmpty(list)) {
                List<SysFile> sysFiles = sysFileService.listByIds(list.stream().map(SecExpert::getExId).collect(Collectors.toList()));
                sysFiles.forEach(sysFile -> sysFile.setFileflowstatus(ApprovalStatusEnum.Invalid.getIndex().toString()));
                sysFileService.updateBatchById(sysFiles);
                list.forEach(expert -> {
                    expert.setExState(ApprovalStatusEnum.Invalid.getIndex());
                    expert.setExStateTime(new Date());
                });
                this.updateBatchById(list);
            }
        }
        if (ValidateUtil.isNotEmpty(updateExpertList)) {
            this.updateBatchById(updateExpertList);
        }
        if (ValidateUtil.isNotEmpty(addExpertList)) {
            this.saveBatch(addExpertList);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecExpertByuId(String uId, String tokenUId) {
        if (ValidateUtil.isAllEmpty(uId, tokenUId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(uId)) {
            uId = tokenUId;
        }
        SecExpertModel model = baseMapper.getSecExpertByuId(uId);
        if (ValidateUtil.isNull(model)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 查询用户今年的暂停总天数
        if (ValidateUtil.isNotNull(model.getYear())) {
            Integer totalVacationDay = secExpertPauseService.getSepDaysYear(model.getExId(), model.getYear());
            model.setSepDaysYear(totalVacationDay);
        } else {
            model.setSepDaysYear(0);
        }
        return BaseResponse.ok(model);
    }

    /**
     * 专家库多条件查询(城建定制专家鉴权逻辑)
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse getSecExpertCjDzListClass(SecExpertModel request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isNull(request.getYear())) {
            request.setYear(DateUtil.getTimeYear(new Date()));
        }
        // 查询角色
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        BaseResponse res = userProducerFeign.getUserRole(user.getId());
        if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
            List<String> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), String.class);
            if (ValidateUtil.isNotEmpty(data)) {
                // 角色校验
                if (data.contains("集团专家库管理员")) {
                    request.setExApplicantId1(null);
                    request.setExApplicantCId1(null);
                } else if (data.contains("企业专家库管理员")) {
                    request.setExApplicantId1(null);
                    request.setExApplicantCId1(user.getCId());
                } else {
                    request.setExApplicantId1(user.getId());
                    request.setExApplicantCId1(null);
                }
            }
        }
        // 权限校验
        String authSql;
        if (sysUserService.checkShowAllDataAuth(user.getId())) {
            authSql = " where 1=1 ";
            request.setExApplicantId1(null);
            request.setExApplicantCId1(null);
        } else {
            authSql = " where 1=1 " + sysUserService.ShowCancelData(user.getId(), "Ex_Create_Uid", "Ex_State");
        }
        request.setAuthSql(authSql);
        // 查询
        QueryMap<SecExpertModel> params = this.getQueryMap(request);
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecExpertModel.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, params.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecExpertCjDzListClass error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    /**
     * 筛选条件构建
     *
     * @param request
     * @return
     */
    private QueryMap<SecExpertModel> getQueryMap(SecExpertModel request) {
        QueryMap<SecExpertModel> params = new QueryMap<>(request);
        params.put("cId").put("cName").put("cNameHistory").put("exName").put("seaId")
                .put("exSex").put("exIdType").put("exIdNumber").put("exLocation").put("notSeaId")
                .put("exPolitics").put("exEducation").put("exMajor").put("exSchool").put("keyExperts")
                .put("exWorkStat").put("exWorkUnit").put("exPost").put("exTitle").put("authSql")
                .put("exType").put("exTitleNumber").put("exProfessionalCategory").put("exApplicantCId")
                .put("exIndustry").put("exCellphoneNumber").put("exState").put("exStatus")
                .put("exCreateUname").put("exCategory").put("exPracticeCode").put("exApplicantId")
                .put("year").put("exNames").put("exTitles").put("exStates").put("exStatusList")
                .put("exWorkYears").put("cNames").put("exAges").put("exRanks").put("exChangeStatusList")
                .put("exApplicantId1").put("exApplicantCId1").put("excludeInvalid").put("exChangeStatus");
        return params;
    }

    /**
     * 评标专家申请驳回到发起人
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isNotEmpty(request.getType())) {
            if ("02".equalsIgnoreCase(request.getType())) {
                // 查询原来的数据
                SecExpertChange entity = secExpertChangeService.getById(request.getFileId());
                if (ValidateUtil.isNull(entity)) {
                    return BaseResponse.fail(ResMsg.infoNotExist);
                }
                entity.setExState(ApprovalStatusEnum.Returned_Initiator.getIndex());
                secExpertChangeService.updateById(entity);

                String entityId = entity.getId();
                SysFile sysFile = sysFileService.getById(entityId);
                sysFileService.saveFile(entityId, sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getExCreateUid(), entity.getExCreateUname()
                        , entity.getExCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
                        "update", sysFile.getFileRelationId(), sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
                // 生成一条已办
                // 获取当前节点
                FlowActivity flowActivity = taskProcessinMapper.getCurrentNode(sysFile.getFlowId());
                LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
                TaskProcessin his = new TaskProcessin();
                his.setTpId(GenerateUUID.getUUID());
                his.setActname(flowActivity.getActname());
                his.setTpDotime(new Date());
                his.setTpExplain(request.getOpinion());
                his.setTpFlowid(sysFile.getFlowId());
                his.setTpProject(sysFile.getFileproject());
                his.setTpId(GenerateUUID.getUUID());
                his.setTpResult("退回");
                his.setTpEntityid(entityId);
                his.setTpName(sysFile.getFiletitle());
                his.setTpUser(userInfoByToken.getId());
                his.setTpType("退回");
                his.setTpActtype("userTask");
                his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
                his.setTpFrom(userInfoByToken.getId());
                his.setTpFileid(entityId);
                his.setTpSend(entity.getExCreateUid());
                his.setTpTemplatecodeo(sysFile.gettCode());
                his.setTpActid(flowActivity.getActid());
                his.setTpSigntime(new Date());
                his.setTpDotime(new Date());
                if (!"All".equals(sysFile.getTeId())) {
                    his.setTeId(sysFile.getTeId());
                }
                taskProcessinMapper.insert(his);

                // 生成一条待办
                String uid = redisUtils.getUid(token);
                BatchhandlingTaskDto batchhandlingTaskDto = new BatchhandlingTaskDto();
                BatchhandlingTaskListDto batchhandlingTaskListDto = new BatchhandlingTaskListDto();
                batchhandlingTaskListDto.setFileId(entityId);
                batchhandlingTaskListDto.setProcessed(entity.getExCreateUid());
                // batchhandlingTaskListDto.setOpinion(request.getOpinion());
                batchhandlingTaskListDto.setUId(uid);
                batchhandlingTaskListDto.setOperator(uid);
                batchhandlingTaskDto.settCode(sysFile.gettCode());
                batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
                batchhandlingTaskDto.setTeId("All");
                batchhandlingTaskDto.setCurrentNode("returnStartNode");
                batchhandlingTaskDto.setOnlineConditions("false");
                batchhandlingTaskDto.setNotGenerateTask("1");
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        bpmFeign.batchSendTask(batchhandlingTaskDto, token);
                    }
                });
                this.updateChangeStatus(entity.getExId(), "04");
                return BaseResponse.ok(entityId);
            } else if ("03".equalsIgnoreCase(request.getType())) {
                // 查询原来的数据
                SecExpertDelete entity = secExpertDeleteService.getById(request.getFileId());
                if (ValidateUtil.isNull(entity)) {
                    return BaseResponse.fail(ResMsg.infoNotExist);
                }
                entity.setExState(ApprovalStatusEnum.Returned_Initiator.getIndex());
                secExpertDeleteService.updateById(entity);

                String entityId = entity.getId();
                SysFile sysFile = sysFileService.getById(entityId);
                sysFileService.saveFile(entityId, sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getExCreateUid(), entity.getExCreateUname()
                        , entity.getExCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
                        "update", sysFile.getFileRelationId(), sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
                // 生成一条已办
                // 获取当前节点
                FlowActivity flowActivity = taskProcessinMapper.getCurrentNode(sysFile.getFlowId());
                LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
                TaskProcessin his = new TaskProcessin();
                his.setTpId(GenerateUUID.getUUID());
                his.setActname(flowActivity.getActname());
                his.setTpDotime(new Date());
                his.setTpExplain(request.getOpinion());
                his.setTpFlowid(sysFile.getFlowId());
                his.setTpProject(sysFile.getFileproject());
                his.setTpId(GenerateUUID.getUUID());
                his.setTpResult("退回");
                his.setTpEntityid(entityId);
                his.setTpName(sysFile.getFiletitle());
                his.setTpUser(userInfoByToken.getId());
                his.setTpType("退回");
                his.setTpActtype("userTask");
                his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
                his.setTpFrom(userInfoByToken.getId());
                his.setTpFileid(entityId);
                his.setTpSend(entity.getExCreateUid());
                his.setTpTemplatecodeo(sysFile.gettCode());
                his.setTpActid(flowActivity.getActid());
                his.setTpSigntime(new Date());
                his.setTpDotime(new Date());
                if (!"All".equals(sysFile.getTeId())) {
                    his.setTeId(sysFile.getTeId());
                }
                taskProcessinMapper.insert(his);

                // 生成一条待办
                String uid = redisUtils.getUid(token);
                BatchhandlingTaskDto batchhandlingTaskDto = new BatchhandlingTaskDto();
                BatchhandlingTaskListDto batchhandlingTaskListDto = new BatchhandlingTaskListDto();
                batchhandlingTaskListDto.setFileId(entityId);
                batchhandlingTaskListDto.setProcessed(entity.getExCreateUid());
                // batchhandlingTaskListDto.setOpinion(request.getOpinion());
                batchhandlingTaskListDto.setUId(uid);
                batchhandlingTaskListDto.setOperator(uid);
                batchhandlingTaskDto.settCode(sysFile.gettCode());
                batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
                batchhandlingTaskDto.setTeId("All");
                batchhandlingTaskDto.setCurrentNode("returnStartNode");
                batchhandlingTaskDto.setOnlineConditions("false");
                batchhandlingTaskDto.setNotGenerateTask("1");
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        bpmFeign.batchSendTask(batchhandlingTaskDto, token);
                    }
                });
                this.updateChangeStatus(entity.getExId(), "04");
                return BaseResponse.ok(entityId);
            }
        }
        // 查询原来的数据
        SecExpert entity = this.getById(request.getFileId());
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setExState(ApprovalStatusEnum.Returned_Initiator.getIndex());
        this.updateById(entity);

        String entityId = entity.getExId();
        SysFile sysFile = sysFileService.getById(entityId);
        sysFileService.saveFile(entityId, sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getExCreateUid(), entity.getExCreateUname()
                , entity.getExCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
                "update", sysFile.getFileRelationId(), sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
        // 生成一条已办
        // 获取当前节点
        FlowActivity flowActivity = taskProcessinMapper.getCurrentNode(sysFile.getFlowId());
        LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
        TaskProcessin his = new TaskProcessin();
        his.setTpId(GenerateUUID.getUUID());
        his.setActname(flowActivity.getActname());
        his.setTpDotime(new Date());
        his.setTpExplain(request.getOpinion());
        his.setTpFlowid(sysFile.getFlowId());
        his.setTpProject(sysFile.getFileproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("退回");
        his.setTpEntityid(entityId);
        his.setTpName(sysFile.getFiletitle());
        his.setTpUser(userInfoByToken.getId());
        his.setTpType("退回");
        his.setTpActtype("userTask");
        his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
        his.setTpFrom(userInfoByToken.getId());
        his.setTpFileid(entityId);
        his.setTpSend(entity.getExCreateUid());
        his.setTpTemplatecodeo(sysFile.gettCode());
        his.setTpActid(flowActivity.getActid());
        his.setTpSigntime(new Date());
        his.setTpDotime(new Date());
        if (!"All".equals(sysFile.getTeId())) {
            his.setTeId(sysFile.getTeId());
        }
        taskProcessinMapper.insert(his);

        // 生成一条待办
        String uid = redisUtils.getUid(token);
        BatchhandlingTaskDto batchhandlingTaskDto = new BatchhandlingTaskDto();
        BatchhandlingTaskListDto batchhandlingTaskListDto = new BatchhandlingTaskListDto();
        batchhandlingTaskListDto.setFileId(entityId);
        batchhandlingTaskListDto.setProcessed(entity.getExCreateUid());
        // batchhandlingTaskListDto.setOpinion(request.getOpinion());
        batchhandlingTaskListDto.setUId(uid);
        batchhandlingTaskListDto.setOperator(uid);
        batchhandlingTaskDto.settCode(sysFile.gettCode());
        batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
        batchhandlingTaskDto.setTeId("All");
        batchhandlingTaskDto.setCurrentNode("returnStartNode");
        batchhandlingTaskDto.setOnlineConditions("false");
        batchhandlingTaskDto.setNotGenerateTask("1");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                bpmFeign.batchSendTask(batchhandlingTaskDto, token);
            }
        });
        this.updateChangeStatus(entity.getExId(), "04");
        return BaseResponse.ok(entityId);
    }

    /**
     * 新的一年复制专家库
     *
     * @param year
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copyAllSecExpert(Integer year) {
        if (ValidateUtil.isNull(year)) {
            year = DateUtil.getTimeYear(new Date());
        }
        // 如果今年已经有数据了，则不复制
        if (this.count(Wrappers.<SecExpert>lambdaQuery().eq(SecExpert::getYear, year)
                .ne(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())) > 0) {
            return BaseResponse.ok("今年已存在专家数据");
        }
        // 查询上一年的专家库信息
        List<SecExpert> lastYearExperts = this.list(Wrappers.<SecExpert>lambdaQuery()
                .eq(SecExpert::getYear, year - 1)
                .eq(SecExpert::getExState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(SecExpert::getExStatus, 1)
        );
        if (ValidateUtil.isEmpty(lastYearExperts)) {
            return BaseResponse.ok("上一年没有专家库数据，无需复制");
        }
        List<SecExpert> newExperts = new ArrayList<>();
        // List<String> list = lastYearExperts.stream().map(SecExpert::getExId).collect(Collectors.toList());
        for (SecExpert entity : lastYearExperts) {
            SysFile file = sysFileService.getById(entity.getExId());
            entity.setExId(GenerateUUID.getUUID());
            entity.setYear(year);
            entity.setExState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setExStateTime(new Date());
            entity.setExCreateTime(new Date());
            // 处理文件
            String exCardFile = GenerateUUID.getUUID();
            String exDegree = GenerateUUID.getUUID();
            String exPerformFile = GenerateUUID.getUUID();
            String exTitleFile = GenerateUUID.getUUID();
            String exPromiseFile = GenerateUUID.getUUID();
            String exAvoidFile = GenerateUUID.getUUID();
            String exOtherFile = GenerateUUID.getUUID();
            String fileRelationId = GenerateUUID.getUUID();

            sysFileService.saveFile(entity.getExId(), file.gettId(), entity.getExState() + "", entity.getExCreateUid(),
                    entity.getExCreateUname(), entity.getExCreateTime(), null, file.getFiletitle(), file.getFilecode(),
                    "create", fileRelationId, null, file.gettCode(), null);
            sysFileAnnexService.copyFileAnnex2NewRelation(
                    Arrays.asList(entity.getExCardFile(), entity.getExDegree(), entity.getExPerformFile(), entity.getExTitleFile(), entity.getExPromiseFile(), entity.getExAvoidFile(), entity.getExOtherFile(), file.getFileRelationId()),
                    Arrays.asList(exCardFile, exDegree, exPerformFile, exTitleFile, exPromiseFile, exAvoidFile, exOtherFile, fileRelationId),
                    entity.getExId(), true);

            entity.setExCardFile(exCardFile);
            entity.setExDegree(exDegree);
            entity.setExPerformFile(exPerformFile);
            entity.setExTitleFile(exTitleFile);
            entity.setExPromiseFile(exPromiseFile);
            entity.setExAvoidFile(exAvoidFile);
            entity.setExOtherFile(exOtherFile);
            entity.setExModifyReason(null);

            // 工作年限+1
            if (ValidateUtil.isNotNull(entity.getExSysWorkYear())) {
                entity.setExSysWorkYear(entity.getExSysWorkYear() + 1);
            }

            // 计算年龄
            if (ValidateUtil.isNotEmpty(entity.getExIdNumber())) {
                if (ValidateUtil.isNotEmpty(entity.getExPlaintextNum())) {
                    entity.setExAge(IdcardUtil.getAgeByIdCard(entity.getExPlaintextNum()));
                } else {
                    entity.setExAge(IdcardUtil.getAgeByIdCard(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber())));
                }
            }

            // 修改状态在复制后为未提交
            entity.setExChangeStatus("04");
            newExperts.add(entity);
        }
        this.saveBatch(newExperts);
        // this.update(Wrappers.<SecExpert>lambdaUpdate().in(SecExpert::getExId, list).set(SecExpert::getExStatus, 0));
        return BaseResponse.ok(newExperts.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse invalidProcessData(String exCid) {
        List<SecExpert> list = this.list(Wrappers.<SecExpert>lambdaQuery()
                .eq(SecExpert::getCId, exCid)
                .ne(SecExpert::getExState, ApprovalStatusEnum.Already_Over.getIndex())
                .ne(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok();
        }
        List<String> ids = list.stream().map(SecExpert::getExId).collect(Collectors.toList());
        if (ValidateUtil.isNotEmpty(ids)) {
            this.update(Wrappers.<SecExpert>lambdaUpdate()
                    .set(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())
                    .in(SecExpert::getExId, ids));
            // 作废
            sysFileService.update(Wrappers.<SysFile>lambdaUpdate()
                    .set(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                    .in(SysFile::getFileid, ids)
            );
            // 删除待办
            taskMapper.delete(Wrappers.<Task>lambdaQuery()
                    .in(Task::getTaskentityid, ids)
            );
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateChangeStatus(String exId, String changeStatus) {
        if (ValidateUtil.isAllEmpty(exId, changeStatus)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpert entity = this.getById(exId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(entity.getExState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        entity.setExChangeStatus(changeStatus);
        this.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecExpertChangeStatus(String exId) {
        if (ValidateUtil.isEmpty(exId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpert entity = this.getById(exId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        // 专家主流程未结束 => 04(未提交)
        if (!JudgeUtil.isApprovedState(entity.getExState())) {
            return BaseResponse.ok("04");
        }

        // 查询最新变更流程
        SecExpertChange change = secExpertChangeService.getOne(
                Wrappers.<SecExpertChange>lambdaQuery()
                        .eq(SecExpertChange::getExId, exId)
                        .orderByDesc(SecExpertChange::getExCreateTime)
                        .last(" limit 1 ")
        );
        // 查询最新删除流程
        SecExpertDelete delete = secExpertDeleteService.getOne(
                Wrappers.<SecExpertDelete>lambdaQuery()
                        .eq(SecExpertDelete::getExId, exId)
                        .orderByDesc(SecExpertDelete::getExCreateTime)
                        .last(" limit 1 ")
        );

        boolean hasChange = ValidateUtil.isNotNull(change);
        boolean hasDelete = ValidateUtil.isNotNull(delete);
        boolean changeApproved = hasChange && JudgeUtil.isApprovedState(change.getExState());
        boolean deleteApproved = hasDelete && JudgeUtil.isApprovedState(delete.getExState());

        // 04 未提交
        // 有流程记录但未结束 => 04
        if ((hasChange && !changeApproved) || (hasDelete && !deleteApproved)) {
            return BaseResponse.ok("04");
        }
        // 删除优先级最高：删除流程结束 => 03
        if (deleteApproved) {
            return BaseResponse.ok("03");
        }
        // 变更流程结束 + changed=00 => 00(未修改)
        if (changeApproved && "00".equalsIgnoreCase(change.getChanged())) {
            return BaseResponse.ok("00");
        }
        // 变更流程结束 + changed=01 => 02(变更)
        if (changeApproved && "01".equalsIgnoreCase(change.getChanged())) {
            return BaseResponse.ok("02");
        }
        // 其余 => 01(新增)
        return BaseResponse.ok("01");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse handleDisableSecExpert() {
        // 查询所有专家
        List<SecExpert> experts = this.list(Wrappers.<SecExpert>lambdaQuery()
                .ne(SecExpert::getExState, ApprovalStatusEnum.Invalid.getIndex())
        );
        if (ValidateUtil.isEmpty(experts)) {
            return BaseResponse.ok();
        }
        Set<String> userIds = experts.stream().map(SecExpert::getExApplicantId).collect(Collectors.toSet());
        // 查询用户表
        List<SysUser> users = sysUserService.list(Wrappers.<SysUser>lambdaQuery().in(SysUser::getUId, userIds));
        // 判断需要禁用或启用的专家
        List<SecExpert> updateList = new ArrayList<>();
        for (SecExpert expert : experts) {
            SysUser user = users.stream().filter(u -> u.getUId().equals(expert.getExApplicantId())).findFirst().orElse(null);
            if (ValidateUtil.isNull(user)) {
                continue;
            }
            if ("0".equals(user.getUState()) && expert.getExStatus() == 1) {
                expert.setExStatus(0);
                updateList.add(expert);
            } else if ("1".equals(user.getUState()) && expert.getExStatus() == 0) {
                expert.setExStatus(1);
                updateList.add(expert);
            }
        }
        if (ValidateUtil.isNotEmpty(updateList)) {
            this.updateBatchById(updateList);
        }
        return BaseResponse.ok();
    }

    /**
     * 获取可以抽取的专家数量
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getExtractSecExpertCount(ExtractExpertDto request, String teId) {
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("technology", 0);
        data.put("economics", 0);
        if (request.getEsTechnologyNum() + request.getEsEconomicsNum() == 0) {
            return BaseResponse.ok(data);
        }
        List<String> excludeIds = null;
        if (!CollectionUtils.isEmpty(request.getSecExpertShields())) {
            excludeIds = request.getSecExpertShields().stream().map(SecExpertShieldDto::getExId).collect(Collectors.toList());
        }

        // 是否需要排除指定单位下的专家
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

        String secExpertAvoidedUnitCode = "secExpertAvoidedUnit";
        String secExpertLockPersonnelCode = "secExpertLockPersonnel";
        // 配置的需要回避企业。
        Map<String, Object> configMap = sysConfigService.getConfigByCodes(Arrays.asList(secExpertAvoidedUnitCode, secExpertLockPersonnelCode));
        String secExpertAvoidedUnit = (String) configMap.get(secExpertAvoidedUnitCode);
        if (StrUtil.isNotBlank(secExpertAvoidedUnit) && ValidateUtil.isNotNull(excludeCpy)) {
            List<SecExpertAvoidedUnitDto> secExpertAvoidedUnitDtoList = JacksonUtils.parseArray(secExpertAvoidedUnit, SecExpertAvoidedUnitDto.class);
            if (!CollectionUtils.isEmpty(secExpertAvoidedUnitDtoList)) {
                for (SecExpertAvoidedUnitDto secExpertAvoidedUnitDto : secExpertAvoidedUnitDtoList) {
                    if (secExpertAvoidedUnitDto.getUnit().contains(excludeCpy)) {
                        avoidCompanyIds.addAll(Arrays.asList(secExpertAvoidedUnitDto.getAvoidedUnit().split(",")));
                    }
                }
            }
        }
        // 查询该标段的重点专家申请表是否存在并已结束
        long count = 0;
        if (ValidateUtil.isEmpty(request.getKeyExpertSelection())) {
            count = secFocusExpertService.count(Wrappers.<SecFocusExpert>lambdaQuery()
                    .eq(SecFocusExpert::getSecId, request.getSecId())
                    .eq(SecFocusExpert::getState, ApprovalStatusEnum.Already_Over.getIndex())
            );
        } else if ("1".equals(request.getKeyExpertSelection())) {
            count = 1;
        }

        Integer year;
        if (ValidateUtil.isNotNull(request.getSrBidTime())) {
            year = DateUtil.getTimeYear(request.getSrBidTime());
        } else {
            year = DateUtil.getTimeYear(new Date());
        }

        // 所有专家
        List<SecExpertModel> records = baseMapper.getExtractExpertList(
                excludeIds,
                excludeCpy,
                teId,
                DateUtil.firstDayOfMonth("yyyy-MM-dd"),
                DateUtil.Date2String(new Date(), "yyyy-MM-dd"),
                conf.getMonthExNum(),
                avoidCompanyIds,
                count > 0 ? "1" : null,
                year
        );

        // 同一时段（同一天）开标的不同两个标段A和B，若专家甲被标段A抽中，则专家甲在该时段被锁定，不能被标段B抽中；直至专家甲确认不参加标段A，则解锁，可以被标段B抽中。
        //【进一步优化逻辑】：专家若被标段A抽中，则专家在该日期将不再被抽中，即使专家确认不参加标段A也不可再被该日期开标的其他标段抽中。
        String secExpertLockPersonnel = (String) configMap.get(secExpertLockPersonnelCode);
        if (StrUtil.isNotBlank(secExpertLockPersonnel) && "1".equals(secExpertLockPersonnel)) {
            SecSelectionRecordDtlDto dto = new SecSelectionRecordDtlDto();
            Date srBidTime = request.getSrBidTime();
            if (srBidTime != null) {
                dto.setSameBidTime(DateUtil.Date2String(srBidTime, "yyyy-MM-dd"));
            }
            if (StrUtil.isNotBlank(request.getSecId())) {
                dto.setNotSecIdList(Arrays.asList(request.getSecId()));
            }
            // 同一天在其他标段的专家。
            List<SecSelectionRecordDtlModel> selectionRecordDtlList = secSelectionRecordDtlService.getSelectionRecordDtlList(dto);
            // 同一天已经在其他标段的专家ids(确认不参加的不算)
            // List<String> collect = selectionRecordDtlList.stream().filter(e -> e.getSrdAttend() == null || (e.getSrdAttend()!=null &&  e.getSrdAttend() == 1)).map(e -> e.getExId()).collect(Collectors.toList());
            // 同一天已经在其他标段的专家ids
            List<String> collect = selectionRecordDtlList.stream().map(SecSelectionRecordDtlModel::getExId).collect(Collectors.toList());
            // 剔除同一天在其他标段的专家
            records = records.stream().filter(e -> !collect.contains(e.getExId())).collect(Collectors.toList());
        }
        if (ValidateUtil.isNotEmpty(records)) {
            List<SecExpertModel> finalRecords = records;
            if (ValidateUtil.isEmpty(request.getKeyExpertSelection())) {
                Set<String> technologyIds = new HashSet<>();
                if (!CollectionUtils.isEmpty(request.getExTechTypes())) {
                    // region技术类专家
                    request.getExTechTypes().forEach(t -> {
                        List<String> types = Arrays.asList(t.split(","));
                        List<SecExpertModel> technologies = finalRecords.stream().filter(n -> {
                            if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                                if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey()) && !technologyIds.contains(n.getExId())) {
                                    if (ValidateUtil.isNotEmpty(n.getExDeclareMajorTwo()) && types.contains(n.getExDeclareMajorTwo())) {
                                        return true;// 申报专业2满足
                                    }
                                    return types.contains(n.getExDeclareMajorOne());// 申报专业1满足
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                        technologyIds.addAll(technologies.stream().map(SecExpertModel::getExId).collect(Collectors.toList()));
                    });
                    // endregion
                } else {
                    technologyIds.addAll(finalRecords.stream().filter(n -> {
                        if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                            return n.getExCategory().equals(ExpertTypeEnum.Technology.getkey());
                        }
                        return false;
                    }).map(SecExpertModel::getExId).collect(Collectors.toList()));
                }
                // 经济类专家
                List<SecExpertModel> economics = finalRecords.stream().filter(n -> {
                    if (ValidateUtil.isNotEmpty(n.getExCategory())) {
                        return n.getExCategory().equals(ExpertTypeEnum.Economics.getkey());
                    }
                    return false;
                }).collect(Collectors.toList());
                data.put("technology", technologyIds.size());
                data.put("economics", economics.size());
            } else {
                data.put("technology", finalRecords.size());
            }
        } else {
            return BaseResponse.ok(data);
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncCompanyToSecExpert(String uId) {
        if (ValidateUtil.isEmail(uId)) {
            return BaseResponse.ok();
        }
        SysUser user = sysUserService.getById(uId);
        if (ValidateUtil.isNull(user)) {
            return BaseResponse.fail("用户不存在");
        }
        List<SecExpert> secExperts = this.list(Wrappers.<SecExpert>lambdaQuery()
                .eq(SecExpert::getExCreateUid, user.getUId())
        );
        if (ValidateUtil.isEmpty(secExperts)) {
            return BaseResponse.fail("专家不存在");
        }
        secExperts.forEach(expert -> expert.setCId(user.getCId()));
        this.updateBatchById(secExperts);
        return BaseResponse.ok();
    }
}
