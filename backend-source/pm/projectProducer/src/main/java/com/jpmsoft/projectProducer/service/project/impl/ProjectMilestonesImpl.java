package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.milestones.ProjectMilestoneRequest;
import com.jpmsoft.common.model.request.milestones.ProjectMilestonesRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.milestones.ProjectMilestoneResponse;
import com.jpmsoft.common.model.response.milestones.ProjectMilestonesResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectMilestonesMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestone;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectMilestones;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IProjectMilestoneService;
import com.jpmsoft.projectProducer.service.project.IProjectMilestonesService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProjectMilestonesImpl extends ServiceImpl<ProjectMilestonesMapper, ProjectMilestones> implements IProjectMilestonesService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IProjectMilestoneService milestoneService;

    @Override
    public BaseResponse createOrUpdateProjectMilestones(ProjectMilestonesRequest request) {
        ProjectMilestones entity = new ProjectMilestones();
        BeanUtils.copyProperties(request, entity);
        List<ProjectMilestoneRequest> milestones = request.getMilestones();
        if (ValidateUtil.isEmpty(request.getPmsId())) {
            if (baseMapper.findCountByPId(request.getpId()) > 0)
                return BaseResponse.fail(ResMsg.projHaveMilestones);

            entity.setCreateTime(new Date());
            entity.setPmsId(GenerateUUID.getUUID());
            baseMapper.insert(entity);

            List<ProjectMilestone> list = new ArrayList<>();
            for (int i = 0; i < milestones.size(); i++) {
                ProjectMilestone pojo = new ProjectMilestone();
                BeanUtils.copyProperties(milestones.get(i), pojo);
                pojo.setSort(i + 1);
                pojo.setCreateTime(new Date());
                pojo.setStatus(0);
                pojo.setPmId(GenerateUUID.getUUID());
                pojo.setPmsId(entity.getPmsId());
                list.add(pojo);
            }
            milestoneService.saveBatch(list);
        } else {
            List<ProjectMilestone> milestoneList = milestoneService.findAllByPmsId(request.getPmsId());
            List<ProjectMilestone> list = new ArrayList<>();
            for (int i = 0; i < milestones.size(); i++) {
                ProjectMilestone pojo = new ProjectMilestone();
                BeanUtils.copyProperties(milestones.get(i), pojo);
                pojo.setSort(i + 1);
                if (ValidateUtil.isEmpty(milestones.get(i).getPmId())) {
                    pojo.setStatus(0);
                    pojo.setPmId(GenerateUUID.getUUID());
                    pojo.setCreateTime(new Date());
                }
                pojo.setPmsId(entity.getPmsId());

                if (!milestoneList.contains(pojo))
                    list.add(pojo);
            }
            milestoneService.saveOrUpdateBatch(list);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectMilestonesList(ProjectMilestonesRequest request, String uid) {
        String authSql = sysUserService.getAuthSql("Project_Milestones", uid, "create_uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("uId", ValidateUtil.isEmpty(uid) ? null : uid);
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        Integer total = baseMapper.getProjectMilestonesListCount(params);
        List<ProjectMilestonesResponse> datas = baseMapper.getProjectMilestonesList(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getProjectMilestonesByPmsId(String pmsId) {
        ProjectMilestonesResponse data = baseMapper.getProjectMilestonesByPmsId(pmsId);
        for (ProjectMilestoneResponse milestone : data.getMilestones()) {
            if (!ValidateUtil.isEmpty(milestone.getFileRelationId())) {
                List<FileResponse> files = sysFileAnnexService.getFileByFileRelationId(milestone.getFileRelationId());
                milestone.setFiles(files == null ? new ArrayList<>() : files);
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateProjectMilestone(ProjectMilestoneRequest request) {
        return milestoneService.updateProjectMilestone(request);
    }

    @Override
    public BaseResponse getProjectMilestoneByPmId(String pmId) {
        return milestoneService.getProjectMilestoneByPmId(pmId);
    }
}
