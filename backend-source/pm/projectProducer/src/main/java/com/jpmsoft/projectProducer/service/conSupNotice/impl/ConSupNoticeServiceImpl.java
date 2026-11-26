package com.jpmsoft.projectProducer.service.conSupNotice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ConBop.ConSupNoticeRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.config.SnowflakeNew;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.conSupNotice.ConSupNoticeMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNotice;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeReply;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkReport;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeDwService;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.oa.IOaWorkReportService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.UserUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConSupNoticeServiceImpl extends ServiceImpl<ConSupNoticeMapper, ConSupNotice> implements IConSupNoticeService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    @Qualifier("mySnowFlake")
    private SnowflakeNew snowflakeNew;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ConSupNoticeReplyServiceImpl conSupNoticeReplyService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IConSupNoticeDwService conSupNoticeDwService;

    @Resource
    private IOaWorkReportService oaWorkReportService;

    @Resource
    private ISysCompanyService sysCompanyService;

    @Resource
    private IConContractService conContractService;

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private BpmFeign bpmFeign;

    @Override
    public BaseResponse createOrUpdateConSupNotice(RequestBean requestBean, String teId) {
        if (ValidateUtil.isNull(requestBean.getInfo())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConSupNotice info = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), ConSupNotice.class);
        boolean flag = false;
        String snState = info.getSnState();
        info.setTeId(teId);
        if (ValidateUtil.isNotEmpty(info.getSnId())) {
            this.updateById(info);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(info.getFileRelationId()), Arrays.asList(info.getRteFileIds()), info.getSnId());
        } else {
            info.setSnId(snowflakeNew.nextUUID());
            info.setSnCreateTime(DateUtil.dateFormatToDate(new Date()));
            this.save(info);
            flag = true;
            snState = "0";
            //新增 调用流程
//            sysFileService.saveFileOrUpdate(info.getSnId(), "通知单管理", snState, info.getSnCreateUid(), info.getSnCreateUname(), "consupnoticeservice", flag);
            sysFileService.saveFile(info.getSnId(),info.getTId(), snState, info.getSnCreateUid(),info.getSnCreateUname(), new Date(), null, "通知单管理", "consupnoticeservice","create", null, null, info.getTCode(),null);
            //附件处理
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(info.getFileRelationId()), info.getSnId());
        }
        conSupNoticeDwService.createOrUpdate(info.getConSupNoticeDws(),info.getSnId());
        return BaseResponse.ok(info.getSnId());
    }

    @Override
    public BaseResponse getConSupNoticeList(Page page, String teId,String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        if (ValidateUtil.isNull(page)) {
            page = new Page<>();
        }
        QueryWrapper<ConSupNoticeRequest> queryWrapper = new QueryWrapper<>();
        // TODO 添加查询条件
        Map<String, String> map = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        //项目名
        if (ValidateUtil.isNotEmpty(teId)) {
            queryWrapper.eq("teId", teId);
        }
        if (ValidateUtil.isNotEmpty(map.get("pId"))) {
            queryWrapper.like("pid", map.get("pId"));
        }
        if (ValidateUtil.isNotEmpty(map.get("pName"))) {
            queryWrapper.like("pName", map.get("pName"));
        }
        //合同名
        if (ValidateUtil.isNotEmpty(map.get("conName"))) {
            queryWrapper.like("conName", map.get("conName"));
        }
        //通知单编号
        if (ValidateUtil.isNotEmpty(map.get("snNo"))) {
            queryWrapper.like("snNo", map.get("snNo"));
        }
        //编号
        if (ValidateUtil.isNotEmpty(map.get("snCode"))) {
            queryWrapper.eq("snCode", map.get("snCode"));
        }
        //文件标题
        if (ValidateUtil.isNotEmpty(map.get("snReplyDeadline"))) {
            queryWrapper.like("snReplyDeadline", map.get("snReplyDeadline"));
        }
        if (ValidateUtil.isNotEmpty(map.get("snState"))) {
            queryWrapper.eq("snState", map.get("snState"));
        }
        //创建人名称
        if (ValidateUtil.isNotEmpty(map.get("snCreateUname"))) {
            queryWrapper.like("snCreateUname", map.get("snCreateUname"));
        }
        //发文单位
        if (ValidateUtil.isNotEmpty(map.get("snReceivingUnitName"))) {
            queryWrapper.like("snReceivingUnitName", map.get("snReceivingUnitName"));
        }
        //创建时间
        if (ValidateUtil.isNotEmpty(map.get("snCreateTime"))) {
            String startTime = map.get("snCreateTime").replace("T", " ");
            startTime = startTime.substring(0, 19);
            queryWrapper.apply(true, "TO_DAYS(" + "'" + startTime + "'" + ")-TO_DAYS(snCreateTime) = 0");
        }
        //更新时间查询
        if (ValidateUtil.isNotEmpty(map.get("sortFiled"))) {
            if (map.get("sortType").equals("1")) {
                queryWrapper.orderByDesc(map.get("sortFiled"));
            } else {
                queryWrapper.orderByAsc(map.get("sortFiled"));
            }
        } else {
            queryWrapper.orderByDesc("snCreateTime");
        }
        IPage page1 = baseMapper.getConSupNoticeList(page, queryWrapper,authSql);
        return BaseResponse.ok(page1);
    }

    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }

    public BaseResponse updateSnState(RequestBean requestBean) {
        String info = (String) requestBean.getInfo();
        if (ValidateUtil.isEmpty(info)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysFile byId = sysFileService.getById(info);
        ConSupNotice byId1 = this.getById(info);
        if (ValidateUtil.isNull(byId) || ValidateUtil.isNull(byId1)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        byId1.setSnState(byId.getFileflowstatus());
        this.updateById(byId1);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getConSupNoticeById(RequestBean requestBean) {
        if (ValidateUtil.isNull(requestBean.getInfo())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String info = (String) requestBean.getInfo();
        ConSupNotice byId = this.getById(info);
        QueryWrapper<ConSupNoticeReply> conSupNoticeReplyPojoQueryWrapper = new QueryWrapper<>();
        conSupNoticeReplyPojoQueryWrapper.eq("Sn_Id", byId.getSnId());
        ConSupNoticeReply byId1 = conSupNoticeReplyService.getOne(conSupNoticeReplyPojoQueryWrapper);
        if (ValidateUtil.isNotNull(byId1)) {
            byId.setNoticeReplyPojo(byId1);
        }
        byId.setConSupNoticeDws(conSupNoticeDwService.getBySnId(byId.getSnId()));
        return BaseResponse.ok(byId);
    }

    /**
     * 根据监理报表生成监理通知单并给报表提交人发送待办
     *
     * @param wrId  监理报表主键
     * @param token 登录token
     * @return 监理通知单主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createConSupNoticeByWorkReport(String wrId, String token) {
        if (ValidateUtil.isEmpty(wrId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OaWorkReport report = oaWorkReportService.getById(wrId);
        if (ValidateUtil.isNull(report)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 仅监理报表审批通过（状态7）后才允许生成通知单
        if (report.getWrState() == null || report.getWrState() != 7) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        LogUserInfoVo userInfo = UserUtils.getUserInfo();
        // 同一报表允许生成多张通知单，每次调用均新建
        ConSupNotice notice = new ConSupNotice();
        notice.setSnId(GenerateUUID.getUUID());
        notice.setSnCreateUid(userInfo.getId());
        notice.setSnCreateUname(userInfo.getName());
        notice.setSnCreateTime(new Date());
        notice.setSnState("0");
        // 与前端“选择监理报表后自动赋值”保持一致
        notice.setSupId(wrId);
        notice.setPId(report.getPId());
        notice.setConId(report.getConId());
        notice.setSupNumQuestion(baseMapper.getQuestionNumByWrId(wrId));
        // 发文单位取报表的施工单位（wr_c_id）
        notice.setSnIssuingUnitId(report.getWrCId());
        // 责任单位与发文单位同源，对应前端 cName = row.wrCName
        SysCompany company = ValidateUtil.isEmpty(report.getWrCId()) ? null : sysCompanyService.getComPanyName(report.getWrCId());
        if (company != null) {
            notice.setSnReceivingUnitName(company.getcName());
        }
        notice.setSupType(getSupTypeByWrSign(report.getWrSign()));
        notice.setSupSignTime(buildSupSignTime(report.getWrDate(), report.getWrSign()));
        // 项目名、合同名按项目id/合同id反查
        PreProject project = ValidateUtil.isEmpty(report.getPId()) ? null : preProjectService.getById(report.getPId());
        if (project != null) {
            notice.setPname(project.getpName());
        }
        ConContract contract = ValidateUtil.isEmpty(report.getConId()) ? null : conContractService.getById(report.getConId());
        if (contract != null) {
            notice.setConName(contract.getConName());
        }
        save(notice);
        sysFileService.saveFile(notice.getSnId(), null, notice.getSnState(), notice.getSnCreateUid(), notice.getSnCreateUname(),
                notice.getSnCreateTime(), notice.getPId(), "监理通知单", "consupnoticeservice", "create", null, null, "SupNotice", notice.getConId());
        // 待办需在事务提交后发送，否则bpm服务读不到刚写入的sys_file记录
        final String noticeId = notice.getSnId();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                sendSupNoticeTask(noticeId, report, userInfo, token);
            }
        });
        return BaseResponse.ok(noticeId);
    }

    /**
     * 根据报表签署类型取得通知单类型
     * 01 监理日报、02 监理周报、03 监理月报
     *
     * @param wrSign 报表签署类型
     * @return 通知单类型名称
     */
    private String getSupTypeByWrSign(String wrSign) {
        if ("01".equals(wrSign)) {
            return "监理日报";
        }
        if ("02".equals(wrSign)) {
            return "监理周报";
        }
        if ("03".equals(wrSign)) {
            return "监理月报";
        }
        return null;
    }

    /**
     * 根据报表日期与签署类型得到监理时间
     * 01 取 yyyy-MM-dd、02 取 yyyy年第NN周、03 取 yyyy-MM
     *
     * @param wrDate 报表日期
     * @param wrSign 报表签署类型
     * @return 监理时间文本
     */
    private String buildSupSignTime(Date wrDate, String wrSign) {
        if (wrDate == null) {
            return "";
        }
        // 与前端 JS 一致，按报表日期所在时区解析成本地日期
        LocalDate date = wrDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if ("01".equals(wrSign)) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if ("02".equals(wrSign)) {
            // ISO 周：周一为一周开始，包含周四的那一周为当年第一周
            WeekFields weekFields = WeekFields.ISO;
            int weekYear = date.get(weekFields.weekBasedYear());
            int week = date.get(weekFields.weekOfWeekBasedYear());
            return String.format("%04d年第%02d周", weekYear, week);
        }
        if ("03".equals(wrSign)) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        return "";
    }

    /**
     * 发送监理通知单待办给报表提交人
     *
     * @param noticeId 监理通知单主键
     * @param report   监理报表
     * @param userInfo 当前登录用户
     * @param token    登录token
     */
    private void sendSupNoticeTask(String noticeId, OaWorkReport report, LogUserInfoVo userInfo, String token) {
        if (ValidateUtil.isEmpty(report.getOwrSubmit())) {
            throw new JPMException(ResMsg.taskCreateFail);
        }
        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();
        BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
        tkl.setFileId(noticeId)
                .setUId(userInfo.getId())
                .setProcessed(report.getOwrSubmit())
                .setOperator(userInfo.getId());
        taskListDtos.add(tkl);

        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("createNode");
        task.setOnlineConditions(String.valueOf(false));
        task.settCode("SupNotice");
        task.setTeId(ValidateUtil.isEmpty(userInfo.getTeId()) ? "All" : userInfo.getTeId());
        task.setSendList(taskListDtos);
        BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
        if (taskRes == null || !taskRes.getSuccess()) {
            throw new JPMException(taskRes == null ? ResMsg.taskCreateFail : String.valueOf(taskRes.getMessage()));
        }
    }

}
