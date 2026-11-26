package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.BuildQualitySafetyLogMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.BuildQualitySafetyLogDto;
import com.jpmsoft.projectProducer.pojo.entity.construction.BuildQualitySafetyLog;
import com.jpmsoft.projectProducer.pojo.vo.construction.BuildQualitySafetyLogVo;
import com.jpmsoft.projectProducer.service.construction.IBuildQualitySafetyLogService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 17:48
 */
@Service
public class BuildQualitySafetyLogServiceImpl extends ServiceImpl<BuildQualitySafetyLogMapper, BuildQualitySafetyLog>
        implements IBuildQualitySafetyLogService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createBuildQualitySafetyLog(BuildQualitySafetyLogDto request) {
        BuildQualitySafetyLog entity = new BuildQualitySafetyLog();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        entity.setCreateTime(new Date());
        baseMapper.insert(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), null);
        sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateBuildQualitySafetyLog(BuildQualitySafetyLogDto request) {
        BuildQualitySafetyLog entity = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), entity.getSecId(), request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getBuildQualitySafetyLogList(BuildQualitySafetyLogDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Build_Quality_Safety_Log", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "state");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<BuildQualitySafetyLogDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("secName").put("state").put("createUname");
        IPage<BuildQualitySafetyLogVo> page = new Page<>(request.getPage(), request.getSize());
        List<BuildQualitySafetyLogVo> datas = baseMapper.getBuildQualitySafetyLogList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getBuildQualitySafetyLogById(String id) {
        BuildQualitySafetyLogVo data = baseMapper.getBuildQualitySafetyLogById(id);
        return BaseResponse.ok(data);
    }
}




