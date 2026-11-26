package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveFilingApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFilingApply;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.archive.IArchiveFilingApplyService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArchiveFilingApplyImpl extends FileImpl<ArchiveFilingApplyMapper, ArchiveFilingApply, ArchiveFilingApplyModel> implements IArchiveFilingApplyService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateArchiveFilingApply(ArchiveFilingApplyModel request) {
        ArchiveFilingApply entity = new ArchiveFilingApply();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getFaId());
    }

    @Override
    public BaseResponse getArchiveFilingApplyList(ArchiveFilingApplyModel request, String uId) {
        //String authSql = sysUserService.onlyProOrganizationAuth(null, uId, "Fa_Create_Uid", "Fa_State");
        String authSql = sysUserService.getAuthSql("Archive_Filing_Apply", uId, "Fa_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Fa_State");
        QueryMap<ArchiveFilingApplyModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("faManageUnitName").put("faPmName")
                .put("faAgentConstructionUnitName").put("faConstructionUnitName").put("faSurveyUnitName")
                .put("faDesignUnitName").put("faConsultUnitName").put("faSupervisorUnitName")
                .put("faType").put("faSourceFunds").put("faState").put("pId");
        IPage<ArchiveFilingApplyModel> page = new Page<>(request.getPage(), request.getSize());
        List<ArchiveFilingApplyModel> datas = baseMapper.getArchiveFilingApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getArchiveFilingApplyById(String faId) {
        ArchiveFilingApplyModel data = baseMapper.getArchiveFilingApplyById(faId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse synchronizeProject(String id) {
        ArchiveFilingApply request = baseMapper.selectById(id);
        if (request.getFaState() == 7) {
            PreProject projectPojo = preProjectService.getById(request.getPId());
            if (projectPojo != null) {
                projectPojo.setpActualStartTime(request.getFaStartDate());
                projectPojo.setpActualCompleteDate(request.getFaEndDate());
                //项目数据完整性验证
                InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
                projectPojo.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), projectPojo));
                preProjectService.updateById(projectPojo);
                return BaseResponse.ok("同步成功");
            } else {
                return BaseResponse.fail("未获取到项目信息");
            }
        } else {
            return BaseResponse.ok();
        }

    }
}
