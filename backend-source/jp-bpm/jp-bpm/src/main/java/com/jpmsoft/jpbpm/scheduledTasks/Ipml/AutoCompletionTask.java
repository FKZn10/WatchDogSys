package com.jpmsoft.jpbpm.scheduledTasks.Ipml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.pojo.dto.NextActivitiesDto;
import com.jpmsoft.jpbpm.pojo.dto.SubmitFlowRequstDto;
import com.jpmsoft.jpbpm.pojo.dto.userselectionDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.pojo.vo.AutomaticTasks;
import com.jpmsoft.jpbpm.pojo.vo.UserSelectionVo;
import com.jpmsoft.jpbpm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AutoCompletionTask {

    /** 匹配节点配置的动态自动完成触发字段：{字段名|date/day/hour/minute}（字段名与单位均大小写不敏感） */
    private static final Pattern AUTO_TRIGGER_PATTERN = Pattern.compile("^\\{([^{}|]+)\\|(date|day|hour|minute)}$", Pattern.CASE_INSENSITIVE);

    /** 兜底默认时长：2 天 = 48 小时 */
    private static final BigDecimal DEFAULT_HOURS = new BigDecimal("48");

    @Resource
    private SysConfigEntityService sysConfigEntityService;
    @Resource
    private TaskService taskService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private FileService fileService;
    @Resource
    private FileTemplateService fileTemplateService;
    @Resource
    private SysShortMessageService sysShortMessageService;
    @Resource
    private FlowActivityService flowActivityService;
    @Resource
    private PreProjectService preProjectService;
    @Resource
    private FlowService flowService;
    @Resource
    private SysTaskAutoCompleteLogService sysTaskAutoCompleteLogService;
    @Resource
    private ISysTaskFirstReadService sysTaskFirstReadService;

    /**
     * 执行超时待办自动完成任务，并在流程提交成功后记录待办首次阅读信息。
     */
    public void executionMethod() {
        String customerId="All";
        String token=null;

        //获取所有的客户信息

        List<String> codeList = new ArrayList<>();
        codeList.add("approvalTimeSetting");//审批时效设置
        String festivalHoliday = "1";
        String workTimeType = "ALL";
        String workShift = "";
        String closingTime = "";
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList,customerId);
        if (smsTemplateList.get("approvalTimeSetting") != null) {
            String data = smsTemplateList.get("approvalTimeSetting").toString();
            Object obj = JacksonUtils.parseObject(data, new TypeReference<Map<String, String>>() {
            });
            if (((Map) obj).get("festivalHoliday") != null) {
                festivalHoliday = ((Map) obj).get("festivalHoliday").toString();
            }
            if (((Map) obj).get("workShift") != null) {
                workShift = ((Map) obj).get("workShift").toString();
            }
            if (((Map) obj).get("closingTime") != null) {
                closingTime = ((Map) obj).get("closingTime").toString();
            }

        }
        if (ValidateUtil.isEmpty(workShift)) {
            workShift = "0:00";
        }
        if (ValidateUtil.isEmpty(closingTime)) {
            closingTime = "59:59";
        }
        if (workShift.equals("0:00") && closingTime.equals("59:59")) {
            workTimeType = "ALL";
        } else {
            workTimeType = "1";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("festivalHoliday", festivalHoliday);
        params.put("workTimeType", workTimeType);
        params.put("workShift", workShift);
        params.put("closingTime", closingTime);
        params.put("newDate", new Date());
        List<AutomaticTasks> list = taskService.getAutomaticTasks(params);
        if (list.size() > 0) {
            List<String> uIds = list.stream().map(n -> n.getTaskuser()).collect(Collectors.toList());
            List<String> fileIds = list.stream().map(n -> n.getTaskentityid()).collect(Collectors.toList());
            List<File> fileList = fileService.listByIds(fileIds);
            List<String> cUids = fileList.stream().map(n -> n.getFilecreateuser()).collect(Collectors.toList());
            List<String> tCodeList = fileList.stream().map(n -> n.gettId()).collect(Collectors.toList());
            uIds.addAll(cUids);
            List<SysUser> userList = sysUserService.getUserAllByUids(uIds,customerId);

            List<FileTemplate> ftList = fileTemplateService.listByIds(tCodeList);
            List<String> pIds = fileList.stream().map(n -> n.getFileproject()).collect(Collectors.toList());
            List<PreProject> pList = preProjectService.listByIds(pIds);
            List<SysTaskAutoCompleteLog> logList = new ArrayList<>();
            // 默认兜底时长只查一次（惰性：列表中存在动态触发待办时才读配置）
            boolean hasDynamic = list.stream().anyMatch(n -> ValidateUtil.isNotEmpty(n.getActAutoTriggerField()));
            BigDecimal defaultHours = hasDynamic ? getDefaultHours(customerId) : null;
            for (AutomaticTasks item : list) {
                SysTaskAutoCompleteLog logObj = new SysTaskAutoCompleteLog();
                logObj.setId(GenerateUUID.getUUID());
                logObj.setActId(item.getActid());
                logObj.setFileId(item.getTaskentityid());
                logObj.setTaclFlowId(item.getTaskflow());
                logObj.setTaclExecutionTime(new Date());
                logObj.setTaskId(item.getTaskid());
                try {
                    List<File> fobj = fileList.stream().filter(n -> n.getFileid().equals(item.getTaskentityid())).collect(Collectors.toList());
                    if (fobj.size() > 0) {
                        Map<String, Object> condition = new HashMap<>();
                        condition.put("pId", fobj.get(0).getFileproject());

                        List<FileTemplate> tobj = ftList.stream().filter(n -> n.gettId().equals(fobj.get(0).gettId())).collect(Collectors.toList());
                        Map<String, Object> dataStr = null;
                        if (tobj.size() > 0) {
                            dataStr = fileService.getData(tobj.get(0).gettTable(), tobj.get(0).gettPrimaryKey(), fobj.get(0).getFileid(), customerId);
                            if (dataStr != null) {
                                for (String key : dataStr.keySet()) {
                                    condition.put(toCamelCase(key), dataStr.get(key));
                                }
                            }
                        }
                        // 动态表单字段触发自动完成：轮询时现取表单值判断是否到点，未到点则跳过本次
                        if (ValidateUtil.isNotEmpty(item.getActAutoTriggerField())) {
                            if (!isDynamicDue(item, dataStr, defaultHours)) {
                                continue;
                            }
                        }
                        NextActivitiesDto nexRequst = new NextActivitiesDto();
                        nexRequst.setProjectid(fobj.get(0).getFileproject());
                        nexRequst.setTaskid(item.getTaskid());
                        nexRequst.setUser_id(item.getTaskuser());
                        nexRequst.setTemplatecode(fobj.get(0).gettCode());
                        nexRequst.setFilseId(fobj.get(0).getFileid());
                        nexRequst.setCondition(JacksonUtils.toJSONString(condition));
                        List<PreProject> pobj = pList.stream().filter(n -> n.getpId().equals(fobj.get(0).getFileproject())).collect(Collectors.toList());
                        if (pobj.size() > 0) {
                            nexRequst.setCompanyid(pobj.get(0).getpCompetentDepartment());
                        }
                        BaseResponse nextobj = flowActivityService.getNextActivities(nexRequst,customerId);
                        if (nextobj.getSuccess()) {
                            //获取下一步经办人成功
                            Map<String, Object> data = (Map<String, Object>) nextobj.getData();
                            String datasstus = (String) data.get("datasstus");
                            SubmitFlowRequstDto submintRequest = new SubmitFlowRequstDto();
                            submintRequest.setCondition(JacksonUtils.toJSONString(condition));
                            submintRequest.setTaskid(item.getTaskid());
                            submintRequest.setTaskname(item.getTaskname());
                            submintRequest.setTemplatecode(fobj.get(0).gettCode());
                            submintRequest.setFile_id(fobj.get(0).getFileid());
                            submintRequest.setProjectid(ValidateUtil.isEmpty(fobj.get(0).getFileproject()) ? "ALL" : fobj.get(0).getFileproject());
                            submintRequest.setFlowid(item.getTaskflow());
                            submintRequest.setOperationtype(datasstus);
                            submintRequest.setUser_id(item.getTaskuser());
                            submintRequest.setExplain(item.getActAutoDefaultOpinion());
                            boolean submitStatus = true;
                            if (datasstus == "next") {
                                List<userselectionDto> nextUserList = new ArrayList<>();
                                List<UserSelectionVo> returlist = (List<UserSelectionVo>) data.get("Array");
                                if (returlist.size() > 0) {
                                    String massage = "";
                                    for (UserSelectionVo sObj : returlist) {
                                        userselectionDto uobjs = new userselectionDto();
                                        uobjs.setActityid(sObj.getNodeid());
                                        uobjs.setSmsStatus(sObj.getSmsNotificationStatus());
                                        List<UserSelectionVo.Userinfo> nextUser = sObj.getUserlist();
                                        if (nextUser.size() == 1) {
                                            uobjs.setUserid(nextUser.get(0).getUserid());
                                        } else if (nextUser.size() > 1) {
                                            if ("1".equals(item.getActAutoMultiple())) {
                                                //优选获取第一个
                                                uobjs.setUserid(nextUser.get(0).getUserid());
                                            } else if ("-1".equals(item.getActAutoMultiple())) {
                                                submitStatus = false;
                                                massage =massage+ "下一步骤节点“" + sObj.getNodename() + "“获取到多个经办人，因流程图配置“多人时不执行自动完成”停止自动审批；";
                                            } else {
                                                uobjs.setUserid(nextUser.get(0).getUserid());
                                            }
                                        } else {
                                            submitStatus = false;
                                            massage =massage+"下一步步骤节点“" + sObj.getNodename() + "“未获取到经办人；";
                                        }
                                        nextUserList.add(uobjs);
                                    }
                                    if (!submitStatus) {
                                        logObj.setTaclResults("失败");
                                        logObj.setTaclNotes(massage);
                                    }
                                } else {
                                    //未获取到下一步经办人
                                    submitStatus = false;
                                    logObj.setTaclResults("失败");
                                    logObj.setTaclNotes("未获取到下一步活动节点信息");
                                }
                                submintRequest.setNextuser(nextUserList);
                                log.info("下一步经办人解析成功");
                            }
                            if (submitStatus) {
                                // 流程提交成功后原待办会被删除，提交前先保留待办信息用于创建首次阅读记录
                                Task currentTask = taskService.getById(item.getTaskid());
                                BaseResponse returnSub = flowService.submitFlow(submintRequest,customerId,token);
                                if (returnSub.getSuccess()) {
                                    logObj.setTaclResults("成功");
                                    logObj.setTpId(returnSub.getData().toString());
                                    sysTaskFirstReadService.createFirstRead(currentTask);
                                    List<SysUser> userObj = userList.stream().filter(n -> n.getuId().equals(item.getTaskuser())).collect(Collectors.toList());
                                    if (userObj.size() > 0) {
                                        if ("1".equals(item.getActAutoCompletionK())) {
                                            sysShortMessageService.generateSMS(fobj.get(0), item.getTaskname(), item.getActid(), item.getTaskuser(), userObj.get(0).getuName(), userObj.get(0).getuMtel(), item.getActAutoCompletionT(), item.getActname(), "通过", item.getActAutoDefaultOpinion(),customerId);
                                        }
                                    }
                                } else {
                                    logObj.setTaclResults("失败");
                                    logObj.setTaclNotes("流程提交失败！接口返回：" + returnSub.getMessage());
                                }
                            }

                        } else {
                            logObj.setTaclResults("失败");
                            logObj.setTaclNotes("获取下一步经办人失败！接口返回：" + nextobj.getMessage());
                        }

                    } else {
                        logObj.setTaclResults("失败");
                        logObj.setTaclNotes("未获取到file信息");
                    }

                } catch (Exception e) {
                    logObj.setTaclResults("失败");
                    logObj.setTaclNotes(e.getMessage());
                }
                logList.add(logObj);
            }
            sysTaskAutoCompleteLogService.saveBatch(logList);
        } else {
            log.info("没有满足条件的");
        }
        log.info("执行具体逻辑");

    }


    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 判断配置了动态触发字段的待办当前是否已到自动完成时间。
     * <ul>
     *   <li>{字段|date}：字段值为绝对截止时间，当前时间 >= 该时间 即触发</li>
     *   <li>{字段|day/hour/minute}：字段值为数字（可小数），按自然时长换算成小时，与已耗有效工时比较（沿用老逻辑 `>`）</li>
     * </ul>
     * 字段取不到、表达式非法、数值非法（非正数/NaN/Infinity）时，回退到默认 2 天（可用配置 autoCompleteDefaultTime 覆盖）。
     */
    private boolean isDynamicDue(AutomaticTasks item, Map<String, Object> dataStr, BigDecimal defaultHours) {
        String triggerField = item.getActAutoTriggerField() == null ? "" : item.getActAutoTriggerField().trim();
        Matcher m = AUTO_TRIGGER_PATTERN.matcher(triggerField);
        if (!m.matches()) {
            // 非法表达式：按默认时长兜底处理
            return compareElapsedByHours(item, defaultHours);
        }
        String fieldName = m.group(1).trim();
        String unit = m.group(2).trim().toLowerCase(Locale.ENGLISH);

        Object raw = getFieldValue(dataStr, fieldName);
        if (raw == null || raw.toString().trim().isEmpty()) {
            return compareElapsedByHours(item, defaultHours);
        }

        if ("date".equals(unit)) {
            // 时刻型：优先把 JDBC 日期对象直接当截止时间，字符串再按格式解析
            Date deadline = parseDateValue(raw);
            if (deadline == null) {
                return compareElapsedByHours(item, defaultHours);
            }
            return new Date().compareTo(deadline) >= 0;
        }

        // 时长型：字段值换算成小时，再与已耗有效工时比较
        String value = raw.toString().trim();
        try {
            BigDecimal num = new BigDecimal(value);
            if (num.compareTo(BigDecimal.ZERO) <= 0) {
                return compareElapsedByHours(item, defaultHours);
            }
            return compareElapsedByHours(item, toHours(num, unit));
        } catch (NumberFormatException e) {
            return compareElapsedByHours(item, defaultHours);
        }
    }

    /** 单位换算成小时：day×24、hour×1、minute÷60 */
    private BigDecimal toHours(BigDecimal num, String unit) {
        if ("day".equals(unit)) {
            return num.multiply(new BigDecimal("24"));
        } else if ("hour".equals(unit)) {
            return num;
        } else if ("minute".equals(unit)) {
            return num.divide(new BigDecimal("60"), 10, BigDecimal.ROUND_HALF_UP);
        }
        return num;
    }

    /** 已耗有效工时 vs 目标小时（沿用老逻辑的 `>` 比较） */
    private boolean compareElapsedByHours(AutomaticTasks item, BigDecimal targetHours) {
        if (item.getElapsedHours() == null || targetHours == null) {
            return false;
        }
        return new BigDecimal(item.getElapsedHours().toString()).compareTo(targetHours) > 0;
    }

    /** 从业务数据里按字段名取字段值（大小写不敏感，兼容下划线转驼峰） */
    private Object getFieldValue(Map<String, Object> dataStr, String fieldName) {
        if (dataStr == null || dataStr.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, Object> en : dataStr.entrySet()) {
            String key = en.getKey();
            if (key == null) {
                continue;
            }
            if (key.equalsIgnoreCase(fieldName)) {
                return en.getValue();
            }
            // 兼容数据库下划线列名与驼峰字段名
            String camel = toCamelCase(key);
            if (camel != null && camel.equalsIgnoreCase(fieldName)) {
                return en.getValue();
            }
        }
        return null;
    }

    /** 兜底默认时长（小时）：优先配置 autoCompleteDefaultTime，再兜底 2 天=48 小时。非法数值/单位统一回退 48 小时。 */
    private BigDecimal getDefaultHours(String customerId) {
        String confValue = null;
        try {
            List<String> codeList = new ArrayList<>();
            codeList.add("autoCompleteDefaultTime");
            Map<String, Object> conf = sysConfigEntityService.getConfigEntity(codeList, customerId);
            if (conf != null && conf.get("autoCompleteDefaultTime") != null) {
                confValue = conf.get("autoCompleteDefaultTime").toString();
            }
        } catch (Exception e) {
            log.warn("读取 autoCompleteDefaultTime 配置失败，使用兜底 2 天", e);
        }

        if (ValidateUtil.isEmpty(confValue)) {
            return DEFAULT_HOURS;
        }
        return parseDefaultHours(confValue.trim());
    }

    /** 解析默认配置：支持 "2"（天数）或 {"value":"2","unit":"day/hour/minute"}；非法值/单位统一回退 48 小时 */
    private BigDecimal parseDefaultHours(String confValue) {
        // 纯数字 = 天数
        try {
            return toValidatedHours(new BigDecimal(confValue), "day");
        } catch (NumberFormatException ignore) {
        }
        try {
            Map<String, Object> obj = JacksonUtils.parseObject(confValue, new TypeReference<Map<String, Object>>() {});
            if (obj == null) {
                return DEFAULT_HOURS;
            }
            Object vv = obj.get("value");
            Object uu = obj.get("unit");
            BigDecimal value = vv == null ? null : new BigDecimal(vv.toString());
            String unit = uu == null ? "day" : uu.toString().toLowerCase(Locale.ENGLISH);
            return toValidatedHours(value, unit);
        } catch (Exception e) {
            return DEFAULT_HOURS;
        }
    }

    /** 校验数值必须为正数、单位仅 day/hour/minute，非法则回退默认 */
    private BigDecimal toValidatedHours(BigDecimal value, String unit) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            return DEFAULT_HOURS;
        }
        if (!"day".equals(unit) && !"hour".equals(unit) && !"minute".equals(unit)) {
            return DEFAULT_HOURS;
        }
        return toHours(value, unit);
    }

    /** 解析日期字段的绝对截止时间。优先接受 JDBC 日期对象，字符串严格解析（拒绝尾随非法字符）。 */
    private Date parseDateValue(Object raw) {
        if (raw == null) {
            return null;
        }
        // 业务表日期字段可能映射为 java.sql.Date / java.sql.Timestamp / java.util.Date
        if (raw instanceof Date) {
            return (Date) raw;
        }
        // 部分驱动/框架把 datetime/date 映射为 java.time 类型（如 MySQL Connector/J 返回 LocalDateTime）
        if (raw instanceof LocalDateTime) {
            return Date.from(((LocalDateTime) raw).atZone(ZoneId.systemDefault()).toInstant());
        }
        if (raw instanceof LocalDate) {
            // 纯日期：截止到当天 23:59:59
            return Date.from(((LocalDate) raw).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        }
        if (raw instanceof OffsetDateTime) {
            return Date.from(((OffsetDateTime) raw).toInstant());
        }
        if (raw instanceof ZonedDateTime) {
            return Date.from(((ZonedDateTime) raw).toInstant());
        }

        String value = raw.toString().trim();
        // 兼容 ISO-8601 的 'T' 分隔符：2026-08-21T08:00 / 2026-08-21T08:00:00
        if (value.length() > 10 && value.charAt(10) == 'T') {
            value = value.substring(0, 10) + ' ' + value.substring(11);
        }
        // 去掉毫秒（.SSS）
        int dot = value.indexOf('.');
        if (dot > 0) {
            value = value.substring(0, dot);
        }
        // 去掉时区后缀（Z / +08:00 / -08:00）
        if (value.endsWith("Z")) {
            value = value.substring(0, value.length() - 1);
        } else {
            int tz = value.length() - 6;
            if (tz > 10 && (value.charAt(tz) == '+' || value.charAt(tz) == '-') && value.charAt(tz + 3) == ':') {
                value = value.substring(0, tz);
            }
        }
        value = value.trim();

        String[] formats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"};
        for (String f : formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(f);
            sdf.setLenient(false);
            java.text.ParsePosition pos = new java.text.ParsePosition(0);
            Date d = sdf.parse(value, pos);
            // 严格校验：必须完整消费整个字符串才算成功
            if (d != null && pos.getIndex() == value.length()) {
                if ("yyyy-MM-dd".equals(f)) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.set(Calendar.HOUR_OF_DAY, 23);
                    cal.set(Calendar.MINUTE, 59);
                    cal.set(Calendar.SECOND, 59);
                    return cal.getTime();
                }
                return d;
            }
        }
        return null;
    }
}
