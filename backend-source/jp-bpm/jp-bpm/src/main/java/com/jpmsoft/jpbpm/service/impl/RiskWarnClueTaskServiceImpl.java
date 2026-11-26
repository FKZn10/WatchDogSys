package com.jpmsoft.jpbpm.service.impl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskDto;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskListDto;
import com.jpmsoft.jpbpm.pojo.entity.Task;
import com.jpmsoft.jpbpm.service.RiskWarnClueTaskService;
import com.jpmsoft.jpbpm.service.TaskService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Service
public class RiskWarnClueTaskServiceImpl implements RiskWarnClueTaskService {
    private final JdbcTemplate jdbc;
    private final TaskService taskService;
    public RiskWarnClueTaskServiceImpl(JdbcTemplate jdbc, TaskService taskService) {
        this.jdbc = jdbc; this.taskService = taskService;
    }
    @Override @Transactional(rollbackFor = Exception.class)
    public BaseResponse sync(String rwcId, String operator, String token) {
        List<Map<String,Object>> rows = jdbc.queryForList("select Rwc_Status,Rwc_Leader_Id,Te_Id from Risk_Warn_Clue where Rwc_Id=? for update", rwcId);
        if (rows.isEmpty()) return BaseResponse.fail("线索不存在");
        Map<String,Object> clue = rows.get(0);
        String state = String.valueOf(clue.get("Rwc_Status"));
        String customerId = clue.get("Te_Id") == null ? "All" : String.valueOf(clue.get("Te_Id"));
        List<Task> current = taskService.todolists(rwcId, customerId);
        if (!"pending_verify".equals(state)) {
            if (!current.isEmpty()) taskService.deleteTaskByTaskEntityID(rwcId, customerId);
            if (!taskService.todolists(rwcId, customerId).isEmpty()) {
                throw new IllegalStateException("部门待办未完整关闭");
            }
            jdbc.update("update Risk_Warn_Clue set Rwc_Todo_Status='synced',Rwc_Todo_Error=null,Rwc_Todo_Time=CURRENT_TIMESTAMP where Rwc_Id=? and Rwc_Todo_Status='pending'", rwcId);
            return BaseResponse.ok("当前状态无需部门待办");
        }
        String leaderId = clue.get("Rwc_Leader_Id") == null ? null : String.valueOf(clue.get("Rwc_Leader_Id"));
        if (!StringUtils.hasText(leaderId)) return BaseResponse.fail("部门负责人未配置");
        if (!current.stream().anyMatch(t -> leaderId.equals(t.getTaskuser()))) {
            // 与 PM 已保存的流程文件保持一致，兼容显式指定或调整过的线索模板编码。
            String templateCode = jdbc.queryForObject("select T_Code from SYS_File where FileId=?", String.class, rwcId);
            if (!StringUtils.hasText(templateCode)) {
                throw new IllegalStateException("线索流程文件未关联模板");
            }
            BatchhandlingTaskListDto item = new BatchhandlingTaskListDto();
            item.setFileId(rwcId);
            item.setuId(operator);
            item.setProcessed(leaderId);
            item.setOperator(operator);
            BatchhandlingTaskDto request = new BatchhandlingTaskDto();
            request.setCurrentNode("createNode");
            request.setOnlineConditions("false");
            request.settCode(templateCode);
            request.setTeId(customerId);
            request.setSendList(Collections.singletonList(item));
            BaseResponse sent = taskService.batchSendTask(request);
            if (sent == null || !Boolean.TRUE.equals(sent.getSuccess())) {
                return sent == null ? BaseResponse.fail("部门待办创建失败") : sent;
            }
        }
        jdbc.update("update Risk_Warn_Clue set Rwc_Todo_Status='synced',Rwc_Todo_Error=null,Rwc_Todo_Time=CURRENT_TIMESTAMP where Rwc_Id=? and Rwc_Todo_Status='pending'", rwcId);
        return BaseResponse.ok("部门待办已同步");
    }
}
