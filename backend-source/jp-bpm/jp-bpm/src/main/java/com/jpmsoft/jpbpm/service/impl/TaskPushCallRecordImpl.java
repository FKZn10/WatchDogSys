package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.common.model.third.AgencyModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.TaskPushCallRecordMapper;
import com.jpmsoft.jpbpm.pojo.entity.TaskPushCallRecord;
import com.jpmsoft.jpbpm.pojo.dto.TaskPushCallRecordDto;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import com.jpmsoft.jpbpm.service.SysPersonalBpmAuthorizeService;
import com.jpmsoft.jpbpm.service.SysUserService;
import com.jpmsoft.jpbpm.service.TaskPushCallRecordService;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
@Transactional
public class TaskPushCallRecordImpl extends ServiceImpl<TaskPushCallRecordMapper, TaskPushCallRecord> implements TaskPushCallRecordService {
    @Value("${bpm.sendtodourl}")
    public String senToDoUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    SysConfigEntityService sysConfigEntityService;

    @Override
    public Integer createTaskPushCallRecord(String center, String state,String customerId) {
        TaskPushCallRecord obj = new TaskPushCallRecord();
        obj.setTpcrCenter(center);
        obj.setTpcrId(GenerateUUID.getUUID());
        obj.setTpcrCreateTime(new Date());
        obj.setTpcrState(state);
        if (!"All".equals(customerId)) {
            obj.setTeId(customerId);
        }
        int num = baseMapper.insert(obj);
        return num;
    }


    //获取推送记录列表信息
    @Override
    public BaseResponse getTaskPushCallRecordList(TaskPushCallRecordDto request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "Tpcr_Create_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "DESC");
        params.put("tpcrCreateTime", !ValidateUtil.isEmpty(request.getTpcrCreateTime()) ? request.getTpcrCreateTime() : null);
        params.put("tpcrCreateTimeStart", !ValidateUtil.isEmpty(request.getTpcrCreateTimeStart()) ? request.getTpcrCreateTimeStart() : null);
        params.put("tpcrCreateTimeEnd", !ValidateUtil.isEmpty(request.getTpcrCreateTimeEnd()) ? request.getTpcrCreateTimeEnd() : null);
        params.put("tpcrState", !ValidateUtil.isEmpty(request.getTpcrState()) ? request.getTpcrState() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        List<TaskPushCallRecord> datas = baseMapper.getTaskPushCallRecordList(params);
        Integer total = baseMapper.getTaskPushCallRecordCount(params);
        return new BaseResponse("获取成功", datas, total);
    }

    //执行推送信息
    @Override
    public BaseResponse pushTodoTask(String id,String customerId,String  token) {
        List<String> codeList = new ArrayList<>();
        codeList.add("senToDoUrl");//待办推送地址
        codeList.add("openSenToDo");//是否开启待办推送
        Map<String, Object> smsTemplateList = sysConfigEntityService.getConfigEntity(codeList,customerId);
        if (ValidateUtil.isEmpty(senToDoUrl)) {
            Object senToDoUrlStr = smsTemplateList.get("senToDoUrl");
            if (senToDoUrlStr != null) {
                senToDoUrl = senToDoUrlStr.toString();
            }
        }
        if (!ValidateUtil.isEmpty(senToDoUrl)) {
            TaskPushCallRecord obj = baseMapper.selectById(id);
            if (obj != null) {
                try {

                    HttpHeaders headers = new HttpHeaders();
                    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                    headers.setContentType(type);
                    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                    headers.add("token", token);
                    AgencyModel newObj=JacksonUtils.parseObject(obj.getTpcrCenter(), new TypeReference<AgencyModel>() {});
                    HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(newObj), headers);
                    BaseResponse baseResponse = restTemplate.postForObject(senToDoUrl, formEntity, BaseResponse.class);
                    if (baseResponse.getSuccess() == true) {
                        obj.setTpcrState("1");
                        baseMapper.updateById(obj);
                    }
                    return baseResponse;
                } catch (Exception ex) {
                    return new BaseResponse(ex.getMessage());
                }
            } else {
                return new BaseResponse("未找到该条推送记录！");
            }
        } else {
            return new BaseResponse("未设置待办推送服务地址！");
        }
    }

}
