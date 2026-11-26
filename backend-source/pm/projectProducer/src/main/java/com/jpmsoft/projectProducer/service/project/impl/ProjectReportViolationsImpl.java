package com.jpmsoft.projectProducer.service.project.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectReportViolationsMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectReportViolationsDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectReportViolations;
import com.jpmsoft.projectProducer.service.project.IProjectReportViolationsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class ProjectReportViolationsImpl extends FileImpl<ProjectReportViolationsMapper, ProjectReportViolations, ProjectReportViolationsDTO> implements IProjectReportViolationsService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse createOrUpdateProjectReportViolations(ProjectReportViolationsDTO request) {
        ProjectReportViolations entity = new ProjectReportViolations();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getPrvId());
    }

    @Override
    public BaseResponse createOrUpdateProjectReportViolationsBySms(ProjectReportViolationsDTO request) {
        if (!checkSmsCode(request.getPrvPhone(), "submit", request.getSmsCode())) {
            return BaseResponse.fail(ResMsg.smsCodeError);
        }
        return createOrUpdateProjectReportViolations(request);
    }

    @Override
    public BaseResponse getProjectReportViolationsById(String id) {
        return BaseResponse.ok(baseMapper.getProjectReportViolationsById(id));
    }

    @Override
    public BaseResponse getProjectReportViolationsList(ProjectReportViolationsDTO request) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<ProjectReportViolationsDTO> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<ProjectReportViolationsDTO> params = new QueryMap<>(request);
        //添加查询字段
        params.put("pId").put("prvCompany").put("prvPhone").put("pName");
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<ProjectReportViolationsDTO> projectReportViolationsList = baseMapper.getProjectReportViolationsList(params.getQuery(), page);
        log.info("ProjectReportViolations:{}",JacksonUtils.toJSONString(projectReportViolationsList));
        return BaseResponse.ok(projectReportViolationsList,Long.valueOf(page.getTotal()).intValue());
    }

    public boolean checkSmsCode(String phoneNumber, String type, String code) {
        //redis 短信验证码 key
        String key = "Sms_" + phoneNumber + "_" + type + "_" + code;
        //redis 手机号锁定
        String locked = "Sms_Lock_" + phoneNumber + "_" + type;
        if (redisUtils.get(key) != null) {
            //校验通过删除缓存
            redisUtils.delete(key, locked);
            return true;
        }
        return false;
    }
}
