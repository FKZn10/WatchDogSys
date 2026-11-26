package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectManagementOutlineMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectManagementOutlineDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectManagementOutline;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectManagementOutlineVo;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IProjectManagementOutlineService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectManagementOutlineServiceImpl extends FileImpl<ProjectManagementOutlineMapper, ProjectManagementOutline, ProjectManagementOutlineDto> implements IProjectManagementOutlineService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateProjectManagementOutline(ProjectManagementOutlineDto request) {
        String supportingDocument = request.getSupportingDocument();
        String reviewOpinionForm = request.getReviewOpinionForm();
        if (StrUtil.isEmpty(supportingDocument) || StrUtil.isEmpty(reviewOpinionForm)) {
            return BaseResponse.fail(ResMsg.fileNotFoundError);
        }
        if (ValidateUtil.isEmpty(request.getpId())) {
            request.setpId(request.getProjectId());
        }
        ProjectManagementOutline entity = new ProjectManagementOutline();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProjectManagementOutlineById(String id) {
        ProjectManagementOutlineVo projectManagementOutline = baseMapper.getProjectManagementOutlineById(id);
        return BaseResponse.ok(projectManagementOutline);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProjectManagementOutlineById(String id) {
        ProjectManagementOutline entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Not_Started.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        sysFileService.deleteFileById(id);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectManagementOutlineList(ProjectManagementOutlineDto request, String uId) {
        QueryMap<ProjectManagementOutlineDto> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("authSql",sysUserService.getAuthSqlInnerUserDepAndOutOrg(uId,"ProjectManagementOutline","Project_Management_Outline"))
                .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                .put("pNameList").put("createUnameList").put("stateList").put("createTime");
        IPage<ProjectManagementOutlineVo> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectManagementOutlineVo> datas = baseMapper.getProjectManagementOutlineList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProjectManagementOutlineClassInfo(ProjectManagementOutlineDto request, String uId) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        int i = 1;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ProjectManagementOutlineDto.class.getName());
            QueryMap<ProjectManagementOutlineDto> params = new QueryMap<>(request);
            params.put("pName").put("pId").put("authSql",sysUserService.getAuthSqlInnerUserDepAndOutOrg(uId,"ProjectManagementOutline","Project_Management_Outline"))
                    .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                    .put("pNameList").put("createUnameList").put("stateList").put("createTimeList");
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        List<Object> dictionaryValues = baseMapper.getValueByCode(classInfoAnnotation.getDbValue(), params.getQuery());
                        dictionaryValues = dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        classInfoAnnotation.setNo(i++);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getInvestmentControlClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return BaseResponse.ok(classInfoAnnotationList);
    }
}
