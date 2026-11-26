package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.milestones.ProjectMilestoneResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectMilestoneMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestone;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IProjectMilestoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMilestoneImpl extends ServiceImpl<ProjectMilestoneMapper, ProjectMilestone> implements IProjectMilestoneService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public List<ProjectMilestone> findAllByPmsId(String pmsId) {
        return baseMapper.findAllByPmsId(pmsId);
    }

    @Override
    public BaseResponse getProjectMilestoneByPmId(String pmId) {
        ProjectMilestoneResponse entity = baseMapper.getProjectMilestoneByPmId(pmId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (!ValidateUtil.isEmpty(entity.getFileRelationId())) {
            List<FileResponse> files = sysFileAnnexService.getFileByFileRelationId(entity.getFileRelationId());
            entity.setFiles(files == null ? new ArrayList<>() : files);
        }
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse updateProjectMilestone(ProjectMilestoneRequest request) {
        if (!ValidateUtil.isEmpty(request.getPmsId())) {
            ProjectMilestone entity = new ProjectMilestone();
            BeanUtils.copyProperties(request, entity);
            entity.setStatus(1);
            baseMapper.updateById(entity);

            sysFileService.FileHelper(entity.getPmId(), request.gettId(), entity.getStatus() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), null, request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode());

            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPmId());


            return BaseResponse.ok();
        } else {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
    }
}
