package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPlanTaskQsRecordTypeRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.ProSafetyLedgerDtlDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPlanTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskExportDTO;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPlanTaskService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordDutyService;
import com.jpmsoft.projectProducer.utils.ExcelExportMyStyler;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 15:13
 */
@Slf4j
@RestController
@RequestMapping("/v1/qsPlanTask")
public class QsPlanTaskController {
    @Resource
    private IQsPlanTaskService qsPlanTaskService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IQsRecordDutyService qsRecordDutyService;

    @Resource
    private ISysDictionaryService dictionaryService;

    /**
     * 新增修改
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateQsPlanTask")
    public BaseResponse createOrUpdateQsPlanTask(@RequestBody QsPlanTaskDto request) {
        log.info("createOrUpdateQsPlanTask");
        return qsPlanTaskService.createOrUpdateQsPlanTask(request);
    }

    /**
     * 维护部分指定信息
     *
     * @param request
     * @return
     */
    @PostMapping("/updateQsPlanTaskPartialInfo")
    public BaseResponse updateQsPlanTaskPartialInfo(@RequestBody QsPlanTaskDto request) {
        log.info("updateQsPlanTaskPartialInfo");
        return qsPlanTaskService.updateQsPlanTaskPartialInfo(request);
    }

    @PostMapping("/updateQsPlanTaskRequireTime")
    public BaseResponse updateQsPlanTaskRequireTime(@RequestBody QsPlanTaskDto request) {
        log.info("updateQsPlanTaskRequireTime");
        return qsPlanTaskService.updateQsPlanTaskRequireTime(request);
    }

    @TenantId
    @PostMapping("/getQsPlanTaskList")
    public BaseResponse getQsPlanTaskList(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskList");
        return qsPlanTaskService.getQsPlanTaskList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getQsPlanTaskListExport")
    public BaseResponse getQsPlanTaskListExport(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskListExport");
        return qsPlanTaskService.getQsPlanTaskListExport(request, redisUtils.getUid(token));
    }

    @PostMapping("/export")
    public void export(@RequestBody QsPlanTaskDto request, @RequestHeader String token, HttpServletResponse httpServletResponse) throws IOException {
        request.setSize(-1);
        List<QsPlanTaskVo> qsPlanTaskVoList = (List<QsPlanTaskVo>) qsPlanTaskService.getQsPlanTaskList(request, redisUtils.getUid(token)).getData();
        List<SysDictionary> flowStateDictionaryList = dictionaryService.getSysDictionaryByCode("JPMsoft_LCZT");
        List<SysDictionary> checkTypeDictionaryList = dictionaryService.getSysDictionaryByCode("JPMsoft_XJLX");
        Map<String, String> checkTypeMap = checkTypeDictionaryList.stream().collect(Collectors.toMap(e -> e.getdCode(), e -> e.getdName()));
        Map<String, String> flowStateMap = flowStateDictionaryList.stream().collect(Collectors.toMap(e -> e.getdCode(), e -> e.getdName()));
        List<QsPlanTaskExportDTO> exportDTOList = qsPlanTaskVoList.stream().map(e -> {
            QsPlanTaskExportDTO qsPlanTaskExportDTO = new QsPlanTaskExportDTO();
            BeanUtil.copyProperties(e, qsPlanTaskExportDTO);
            qsPlanTaskExportDTO.setPtTypeStr(checkTypeMap.get(e.getPtType()));
            qsPlanTaskExportDTO.setStateStr(flowStateMap.get(e.getState() + ""));
            if (e.getQsFinishRate() != null) {
                qsPlanTaskExportDTO.setQsFinishRate(NumberUtil.mul(e.getQsFinishRate(), 100));
            }
            if (e.getQsReplyRate() != null) {
                qsPlanTaskExportDTO.setQsReplyRate(NumberUtil.mul(e.getQsReplyRate(), 100));
            }
            return qsPlanTaskExportDTO;
        }).collect(Collectors.toList());
        ExportParams exportParams = new ExportParams();
        exportParams.setStyle(ExcelExportMyStyler.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, QsPlanTaskExportDTO.class, exportDTOList);
        workbook.write(httpServletResponse.getOutputStream());
        workbook.close();
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String fileName = System.currentTimeMillis() + "-export.xlsx";
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @TenantId
    @PostMapping("/getQsPlanTaskListNoAuth")
    public BaseResponse getQsPlanTaskListNoAuth(@RequestBody QsPlanTaskDto request) {
        log.info("getQsPlanTaskListNoAuth");
        return qsPlanTaskService.getQsPlanTaskListNoAuth(request);
    }


    /**
     * 第三方巡检
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getQsPlanTaskPage")
    public BaseResponse getQsPlanTaskPage(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskPage");
        return qsPlanTaskService.getQsPlanTaskPage(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getProSafetyLedgerPage")
    public BaseResponse getProSafetyLedgerPage(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPage");
        return qsPlanTaskService.getProSafetyLedgerPage(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getProSafetyLedgerPageYW")
    public BaseResponse getProSafetyLedgerPageYW(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYW");
        return qsPlanTaskService.getProSafetyLedgerPageYW(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getProSafetyLedgerPageYWExport")
    public List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExport(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYWExport");
        return qsPlanTaskService.getProSafetyLedgerPageYWExport(request, redisUtils.getUid(token));
    }

    @GetMapping("/getQsPlanTaskById")
    public BaseResponse getQsPlanTaskById(@RequestParam String id) {
        log.info("getQsPlanTaskById");
        return qsPlanTaskService.getQsPlanTaskById(id);
    }


    @PostMapping("/getQsRecordDutyByQsrIds")
    public List<QsRecordDutyDTO> getQsRecordDutyByQsrIds(@RequestBody List<String> ids) {
        return qsRecordDutyService.getQsRecordDutyByQsrIds(ids);
    }


    @PostMapping("/qualitySafetyStatistics")
    public BaseResponse qualitySafetyStatistics(@RequestBody QsRecordDto request, @RequestHeader String token) {
        return qsPlanTaskService.qualitySafetyStatistics(request, redisUtils.getUid(token));
    }


    @PostMapping("/getHighQsRecordList")
    public BaseResponse getHighQsRecordList(@RequestBody QsRecordDto request, @RequestHeader String token) {
        return qsPlanTaskService.getHighQsRecordList(request, redisUtils.getUid(token));
    }

    @PostMapping("/updateQsPlanTaskQsRecordType")
    public BaseResponse updateQsPlanTaskQsRecordType(@RequestBody QsPlanTaskQsRecordTypeRequest request) {
        log.info("updateQsPlanTaskQsRecordType");
        return qsPlanTaskService.updateQsPlanTaskQsRecordType(request);
    }

    /**
     * @param qstId 主表id
     * @param qsrId 子表id
     * @return
     */
    @GetMapping("/copyHighQsRecord")
    public BaseResponse copyHighQsRecord(@RequestParam String qstId, @RequestParam String qsrId, @RequestParam String ptId, @RequestHeader String token) {
        return qsPlanTaskService.copyHighQsRecord(qstId, qsrId, ptId, redisUtils.getUid(token));
    }

    @GetMapping("/verifyqsPlanTask")
    public BaseResponse verifyqsPlanTask(@RequestParam String pId, @RequestParam String ptType, @RequestHeader String token) {
        return qsPlanTaskService.verifyqsPlanTask(pId, ptType, token);

    }

    @PostMapping("/getProSafetyLedgerPageYWNew")
    public BaseResponse getProSafetyLedgerPageYWNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYWNew");
        return qsPlanTaskService.getProSafetyLedgerPageYWNew(request, token);
    }

    @PostMapping("/getProSafetyLedgerPageYWExportNew")
    public List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExportNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYWExportNew");
        return qsPlanTaskService.getProSafetyLedgerPageYWExportNew(request, redisUtils.getUid(token));
    }

    @PostMapping("/approveData")
    public BaseResponse approveData(@RequestBody QsPlanTaskDto request) {
        log.info("approveData");
        return qsPlanTaskService.approveData(request);
    }

    @PostMapping("/refreshQsPlanTask")
    public BaseResponse refreshQsPlanTask(@RequestBody QsPlanTaskDto request) {
        log.info("refreshQsPlanTask");
        return qsPlanTaskService.refreshQsPlanTask(request);
    }

    @GetMapping("/timingRefreshOutTimeStatus")
    public BaseResponse timingRefreshOutTimeStatus() {
        log.info("timingRefreshOutTimeStatus");
        return qsPlanTaskService.timingRefreshOutTimeStatus();
    }

    /**
     * 传递主表id ，获取子表信息 ，生成file表记录，给 子表责任人 发送待办
     */
//    @TenantId
    @PostMapping("/generateSubFileRecordsAndNotifyAssignees")
    public BaseResponse generateSubFileRecordsAndNotifyAssignees(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("generateSubFileRecordsAndNotifyAssignees");
        return qsPlanTaskService.generateSubFileRecordsAndNotifyAssignees(request, token);
    }

    /**
     * 传递主表的id和用户信息，删除除该用户外所有的待办
     * 还有是根据主表id找到所有的子表，将子表的抄送人去重，拿到所有的抄送人
     * 给这些抄送人发送主表的抄送通知
     * @param id
     * @param token
     */
/*    @GetMapping("/delegateTodoAndNotifyCcUserstoUsers")
    public BaseResponse delegateTodoAndNotifyCcUserstoUsers(@RequestParam String id, @RequestHeader String token) {
        log.info("delegateTodoAndNotifyCcUsers");
        String uid = redisUtils.getUid(token);
        return qsPlanTaskService.delegateTodoAndNotifyCcUsers(id, uid);
    }*/

    /**
     * 根据QsPlanTask表，同步质量安全子表Qs_Record的状态
     * @param id
     */
    @GetMapping("/syncQsRecordState")
    public BaseResponse syncQsRecordState(@RequestParam String id) {
        log.info("syncQsRecordStatus");
        return qsPlanTaskService.syncQsRecordState(id);
    }

    @PostMapping("/getQsPlanTaskListSH")
    public BaseResponse getQsPlanTaskListSH(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskListSH");
        return qsPlanTaskService.getQsPlanTaskListSH(request, redisUtils.getUid(token));
    }
}