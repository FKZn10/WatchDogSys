package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkRecordRequest;
import com.jpmsoft.common.model.response.proCp.ProCpWorkRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpWorkRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpWorkCommentService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpWorkRecordService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectCpWorkRecordImpl extends ServiceImpl<ProjectCpWorkRecordMapper, ProjectCpWorkRecord> implements IProjectCpWorkRecordService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProjectCpDtlService projectCpDtlService;
    @Resource
    private IProjectCpWorkCommentService projectCpWorkCommentService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private UserProducerFeign userProducerFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProCpWorkRecord(ProjectCpWorkRecordRequest request) {
        ProjectCpWorkRecord entity = new ProjectCpWorkRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getCpwrId())) {
            if (ValidateUtil.isEmpty(entity.getCpdId()))
                return BaseResponse.fail(ResMsg.notReqParam);
            entity.setCpwrId(GenerateUUID.getUUID());
            //entity.setCpwrCreateTime(new Date());
            baseMapper.insert(entity);
            projectCpDtlService.updateProCpDtlAllParentState(null, entity.getCpdId(), ApprovalStatusEnum.Under_Approval.getIndex(),null);
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.getCpId(), request.gettCode());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getCpwrId());
            return BaseResponse.ok();
        } else {
            ProjectCpWorkRecord model = baseMapper.selectById(entity.getCpwrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            //entity.setCpwrCreateTime(model.getCpwrCreateTime());
            entity.setCpwrCreateUname(model.getCpwrCreateUname());
            entity.setCpwrCreateUid(model.getCpwrCreateUid());
            baseMapper.updateById(entity);
            return BaseResponse.ok();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCpWorkRecordById(String cpwrId) {
        ProjectCpWorkRecord model = baseMapper.selectById(cpwrId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        projectCpWorkCommentService.deleteByCpwrId(cpwrId);
        baseMapper.deleteById(cpwrId);
        sysFileService.deleteFileById(cpwrId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProCpWorkRecordList(ProjectCpWorkRecordRequest request, LogUserInfoVo user) {
        QueryMap<ProjectCpWorkRecordRequest> params = new QueryMap<>(request);
        params.put("cpdId").put("pName").put("pId").put("cpdName")
                .put("cpwrCreateUname").put("cpwrHomePush").put("cpwrRemark")
                .put(request.getCpwrCreateTimes(), "cpwrCreateTimeStart", "cpwrCreateTimeEnd")
                .put(request.getCpdCreateTimes(), "cpdCreateTimeStart", "cpdCreateTimeEnd")
                .put(request.getCpwrFillingTimes(), "cfTimeStart", "cfTimeEnd");
        String role = sysConfigService.getConfigByCode("workRecordAuthority");
        if (ValidateUtil.isNotEmpty(role)) {
            Boolean auth = false;
            List<String> roles = Arrays.asList(role.split(","));
            BaseResponse res = userProducerFeign.getUserRole(user.getId());
            if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
                List<String> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), String.class);
                if (!CollectionUtils.isEmpty(data)) {
                    for (String r : data) {
                        if (roles.contains(r)) {
                            auth = true;
                            break;
                        }
                    }
                }
            }
            if (!auth) {
                params.put("auth", Boolean.TRUE);
                params.put("authId", user.getId());
            }
        }
        IPage<ProCpWorkRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProCpWorkRecordResponse> datas = baseMapper.getProCpWorkRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public Integer getCpWorkRecordCountByCpdId(String cpdId) {
        QueryWrapper<ProjectCpWorkRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpd_Id", cpdId);
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public Integer getCpWorkRecordCountByCpId(String cpId) {
        if (ValidateUtil.isEmpty(cpId))
            return 0;
        return baseMapper.getCpWorkRecordCountByCpId(cpId);
    }
}
