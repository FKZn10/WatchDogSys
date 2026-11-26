package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.proCp.ProjectCpSubtaskRequest;
import com.jpmsoft.common.model.response.proCp.ProjectCpSubtaskResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpSubtaskMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpSubtask;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpSubtaskService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ProjectCpSubtaskImpl extends ServiceImpl<ProjectCpSubtaskMapper, ProjectCpSubtask> implements IProjectCpSubtaskService {
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectCpSubtask(ProjectCpSubtaskRequest request) {
        ProjectCpSubtask entity = new ProjectCpSubtask();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPcsId())) {
            if (ValidateUtil.isEmpty(entity.getCpdId()))
                return BaseResponse.fail(ResMsg.notReqParam);
            entity.setPcsId(GenerateUUID.getUUID());
            entity.setPcsCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPcsId(), request.gettId(), "7", request.getPcsCreateUid(), request.getPcsCreateUname(), request.getPcsCreateTime(), "", request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPcsProve()), entity.getPcsId());
        } else {
            ProjectCpSubtask model = baseMapper.selectById(entity.getPcsId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPcsCreateTime(model.getPcsCreateTime());
            entity.setPcsCreateUname(model.getPcsCreateUname());
            entity.setPcsCreateUid(model.getPcsCreateUid());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPcsProve()),
                    Arrays.asList(request.getFileIds(), request.getPcsProveIds()), entity.getPcsId());
        }
        return BaseResponse.ok(entity.getPcsId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProjectCpSubtaskById(String pcsId) {
        ProjectCpSubtask model = baseMapper.selectById(pcsId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(pcsId);
        sysFileService.deleteFileById(pcsId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectCpSubtaskById(String pcsId) {
        ProjectCpSubtaskResponse model = baseMapper.getProjectCpSubtaskByCpId(pcsId);
        return BaseResponse.ok(model, 1);
    }

    @Override
    public BaseResponse getProjectCpSubtaskList(ProjectCpSubtaskRequest request) {
        QueryMap<ProjectCpSubtaskRequest> params = new QueryMap<>(request);
        params.put("cpdId").put("pName").put("pId").put("cpdName").put("pcsTaskName")
                .put("pcsHandler").put("pcsCreateUname").put("pending");
        IPage<ProjectCpSubtaskResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpSubtaskResponse> datas = baseMapper.getProjectCpSubtaskList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

}
