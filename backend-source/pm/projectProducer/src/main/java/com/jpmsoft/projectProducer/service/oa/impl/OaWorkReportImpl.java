package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Oa.OaWorkReportRequest;
import com.jpmsoft.common.model.response.Oa.OaWorkReportResponse;
import com.jpmsoft.common.model.response.Oa.OaWorkReportStandingbook;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaWorkReportMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.HolidayJudgeTimeDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkReport;
import com.jpmsoft.projectProducer.service.oa.IOaProjectReportService;
import com.jpmsoft.projectProducer.service.oa.IOaWorkReportService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OaWorkReportImpl extends ServiceImpl<OaWorkReportMapper, OaWorkReport> implements IOaWorkReportService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IOaProjectReportService oaProjectReportService;

    public static final String FORMAT_PATTERN_OF_DAY = "yyyy-MM-dd";

    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_OF_DAY);

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "wrId", codeParam = "wrCode", method = "setWrCode", codeRule = "OaWorkReport", beanClass = OaWorkReportRequest.class)
    public BaseResponse createOrUpdateOaWorkReport(OaWorkReportRequest request) {
        OaWorkReport entity = new OaWorkReport();
        if (ValidateUtil.isEmpty(request.getWrCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getWrId())) {
            //限制重复提交
            if ("04010101".equals(entity.getWrType())) {
                List<OaWorkReport> oaWorkReports = this.baseMapper.selectoaWorkReportList(entity);
                if (oaWorkReports != null && oaWorkReports.size() > 0) {
                    return BaseResponse.fail("该天已提交！");
                }
            }

            entity.setWrCreateTime(new Date());
            entity.setWrId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            oaProjectReportService.createOrUpdateOaProjectReport(request.getOaProReports(), entity.getWrId(), entity.getWrYear(), entity.getWrMonth(),entity.getTeId());
            sysFileService.saveFile(entity.getWrId(), request.gettId(), entity.getWrState() + "", entity.getWrCreateUid(), entity.getWrCreateUname()
                    , entity.getWrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getWrId());

        } else {
            OaWorkReport model = baseMapper.selectById(request.getWrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setWrState(model.getWrState());
            entity.setWrStateTime(model.getWrStateTime());
            entity.setWrCreateTime(model.getWrCreateTime());
            entity.setWrCreateUid(model.getWrCreateUid());
            entity.setWrCreateUname(model.getWrCreateUname());
            baseMapper.updateById(entity);
            oaProjectReportService.createOrUpdateOaProjectReport(request.getOaProReports(), entity.getWrId(), entity.getWrYear(), entity.getWrMonth(),entity.getTeId());
            sysFileService.saveFile(entity.getWrId(), request.gettId(), entity.getWrState() + "", entity.getWrCreateUid(), entity.getWrCreateUname()
                    , entity.getWrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getWrId());

        }
        return BaseResponse.ok(entity.getWrId());
    }

    @Override
    public BaseResponse getOaWorkReportList(OaWorkReportRequest request, String uId) {
        return getOaWorkReportList(getAuthSql(uId), request);
    }

    @Override
    public BaseResponse getOaWorkReportSumList(OaWorkReportRequest request, String uId) {
        return getOaWorkReportSumList(getAuthSql(uId), request);
    }

    @Override
    public BaseResponse getOaWorkReportListOnlyDep(OaWorkReportRequest request, String uId) {
        if (ValidateUtil.isEmpty(request.gettCode()))
            return BaseResponse.fail(ResMsg.notReqParam);
        String authSql = sysUserService.getAuthSqlOnlyCheckUserDepartment(uId, "Wr_Create_Uid", request.gettCode(), "Wr_Id", "Wr_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return getOaWorkReportList(authSql, request);
    }


    @Override
    public BaseResponse getOaWorkReportListNoAuth(OaWorkReportRequest request) {
        return getOaWorkReportList(null, request);
    }

    private BaseResponse getOaWorkReportList(String authSql, OaWorkReportRequest request) {
        if (ValidateUtil.isNotNull(request.getWrDate()))
            request.setWrDate(DateUtil.dateFormatToDate(request.getWrDate()));
        QueryMap<OaWorkReportRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("wrStartTime").put("wrEndTime").put("userDid").put("wrDId")
                .put("pName").put("conName").put("wrType").put("wrTitle").put("wrYear").put("wrDName")
                .put("wrQuarter").put("wrDate").put("wrMonth").put("wrCreateUid").put("pId").put("wrState")
                .put("wrCreateUname").put(request.getWrDates(), "wrDateStart", "wrDateEnd")
                .put(request.getWrStartTimes(), "wrStartTimeStart", "wrStartTimeEnd")
                .put(request.getWrEndTimes(), "wrEndTimeStart", "wrEndTimeEnd");
        IPage<OaWorkReportResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaWorkReportResponse> datas = baseMapper.getOaWorkReportList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    /**
     * 工作报告列表（含子表 Wrd_Number 数量合计）。
     * 查询条件与 {@link #getOaWorkReportList(String, OaWorkReportRequest)} 保持一致，
     * 仅 Mapper 语句不同（多一个子表聚合 join）。
     */
    private BaseResponse getOaWorkReportSumList(String authSql, OaWorkReportRequest request) {
        if (ValidateUtil.isNotNull(request.getWrDate()))
            request.setWrDate(DateUtil.dateFormatToDate(request.getWrDate()));
        QueryMap<OaWorkReportRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("wrStartTime").put("wrEndTime").put("userDid").put("wrDId")
                .put("pName").put("conName").put("wrType").put("wrTitle").put("wrYear").put("wrDName")
                .put("wrQuarter").put("wrDate").put("wrMonth").put("wrCreateUid").put("pId").put("wrState")
                .put("wrCreateUname").put(request.getWrDates(), "wrDateStart", "wrDateEnd")
                .put(request.getWrStartTimes(), "wrStartTimeStart", "wrStartTimeEnd")
                .put(request.getWrEndTimes(), "wrEndTimeStart", "wrEndTimeEnd");
        IPage<OaWorkReportResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaWorkReportResponse> datas = baseMapper.getOaWorkReportSumList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse getOaWorkReportById(String wrId) {
        OaWorkReportResponse data = baseMapper.getOaWorkReportById(wrId);
        return BaseResponse.ok(data);
    }

    /*****************施工日志台账*************************/

    public BaseResponse getOaWorkReportStandingbook(RequestBean requestBean, String uId) {
        Page page = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), Page.class);
        if (ValidateUtil.isNull(page)) {
            page = new Page();
        }
        Map<String, String> map = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        QueryWrapper<OaWorkReportStandingbook> oaWorkReportStandingbookQueryWrapper = new QueryWrapper<>();
        IPage<OaWorkReportStandingbook> oaWorkReportStandingbook = baseMapper.getOaWorkReportStandingbook(page, oaWorkReportStandingbookQueryWrapper, getAuthSql(uId), map.get("pName"), map.get("pId"));
        List<OaWorkReportStandingbook> records = oaWorkReportStandingbook.getRecords();
        for (OaWorkReportStandingbook record : records) {
            HolidayJudgeTimeDto mapperHolidayJudgeTime = baseMapper.getHolidayJudgeTime(record.getProjectId(), record.getFunctionCode());
            if (Objects.nonNull(mapperHolidayJudgeTime)) {
                //配置得开始到截至时间内总数
                Long aLong = baseMapper.getholidayJudgeCount(record.getProjectId(), mapperHolidayJudgeTime.getIsWork(), record.getFunctionCode());
                //配置得开始到截至时间内未提交数
                Long noSub = aLong - Long.parseLong(record.getAlreadySubmissions());
                record.setAllSubmissions(String.valueOf(aLong));
                record.setNeverSubmissions(String.valueOf(noSub));

                //截至当前时间内总数
                Long nowTimeNum = baseMapper.getholidayJudgeCountNowTime(record.getProjectId(), mapperHolidayJudgeTime.getIsWork(), record.getFunctionCode());
                record.setAllSubmitNowTime(String.valueOf(nowTimeNum));
                //截至当前时间内总已提交数
                record.setNeverSubmitNowTime(String.valueOf(nowTimeNum - Long.parseLong(record.getAlreadySubmissions())));
            } else {
                record.setAllSubmissions("0");
                record.setNeverSubmissions("0");
                record.setAllSubmitNowTime(String.valueOf(0));
                record.setNeverSubmitNowTime(String.valueOf(0));
            }
        }
        return BaseResponse.ok(oaWorkReportStandingbook);
    }

    /**
     * 查询该项目未提交的日期
     */
    public BaseResponse getOaWorkReportNeverSub(RequestBean requestBean) {
        Map<String, String> map = (LinkedHashMap) requestBean.getInfo();
        if (ValidateUtil.isEmpty(map.get("projectId"))) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        HolidayJudgeTimeDto mapperHolidayJudgeTime = baseMapper.getHolidayJudgeTime(map.get("projectId"), map.get("functionCode"));
        if (ValidateUtil.isEmpty(mapperHolidayJudgeTime.getStartTime()) || ValidateUtil.isEmpty(mapperHolidayJudgeTime.getEndTime())) {
            return BaseResponse.fail(ResMsg.confError);
        }
        List<String> oaWorkReportSub = baseMapper.getOaWorkReportNeverSub(mapperHolidayJudgeTime.getStartTime(), mapperHolidayJudgeTime.getEndTime(), map.get("projectId"), mapperHolidayJudgeTime.getIsWork());
        return BaseResponse.ok(oaWorkReportSub);
    }

    @Override
    public BaseResponse countOaWorkReport(List<String> bizIds, String type) {

        String endTime = sdf.format(new Date());
        String startTime = sdf.format(DateUtil.DateChangeDay(new Date(), -7));
        long num = this.baseMapper.countOaWorkReport(bizIds, startTime, endTime, type);
        return BaseResponse.ok(num);
    }

    /**
     * 获取鉴权SQL
     *
     * @param uId
     * @return
     */
    private String getAuthSql(String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Wr_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Wr_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        return authSql;
    }

//    /**
//     * 获取鉴权SQL,function_time_config表关联
//     *
//     * @param uId,tableName
//     * @return
//     */
//    private String getAuthSqlNew(String uId, String tableName) {
//        String authSql = sysUserService.getAuthSql(tableName, uId, "b.create_id", OutUserAuthEnum.Organization.getIndex(), "Wr_State");
//        if (ValidateUtil.isEmpty(authSql))
//            throw new JPMException(ResMsg.noPermission);
//        return authSql;
//    }
}
