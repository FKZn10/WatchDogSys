package com.jpmsoft.projectProducer.service.cockpit.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.cockpit.ProjectPanoramaOverviewMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.CarouselImageDTO;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.ProjectPanoramaOverview;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.service.cockpit.IProjectPanoramaOverviewService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectPanoramaOverviewImpl extends ServiceImpl<ProjectPanoramaOverviewMapper, ProjectPanoramaOverview>
        implements IProjectPanoramaOverviewService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private FileFeign fileFeign;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileService sysFileService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectPanoramaOverview(ProjectPanoramaOverviewModel request) {
        ProjectPanoramaOverview entity = new ProjectPanoramaOverview();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPpoId())) {
            entity.setPpoId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getPpoFile(), entity.getPpoId());
        } else {
            ProjectPanoramaOverview model = baseMapper.selectById(entity.getPpoId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(request.getPpoFile(), request.getPpoFileIds(), entity.getPpoId());
        }
        return BaseResponse.ok(entity.getPpoId());
    }

    @Override
    public BaseResponse getProjectPanoramaOverviewList(ProjectPanoramaOverviewModel request, String uId) {
        QueryMap<ProjectPanoramaOverviewModel> params = new QueryMap<>(request);
        params.put("ppoType").put("pId").put("pName").put("pCode")
                .put("ppoFileType").put("ppoState").put("ppoCreateUname");
        String authSql = sysUserService.getAuthSql("Project_Panorama_Overview", uId, "ppo_create_uid", OutUserAuthEnum.Organization.getIndex());
        params.put("authSql", authSql);
        IPage<ProjectPanoramaOverviewModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectPanoramaOverviewModel> datas = baseMapper.getProjectPanoramaOverviewList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProjectPanoramaList(String teId) {
        return BaseResponse.ok(baseMapper.getProjectPanoramaList(teId), 1);
    }

    @Override
    public BaseResponse getProjectPanoramaOverviewById(String ppoId) {
        ProjectPanoramaOverviewModel model = baseMapper.getProjectPanoramaOverviewById(ppoId);
        return BaseResponse.ok(model, 1);
    }

    @Override
    public BaseResponse deleteProjectPanoramaOverviewById(String ppoId) {
        ProjectPanoramaOverview entity = baseMapper.selectById(ppoId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(ppoId);
        return BaseResponse.ok();
    }

    /**
     * 获取图片地址用于轮播图
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getProjectPanoramaOverviewFile(ProjectPanoramaOverviewModel request) {
        List<ProjectPanoramaOverviewModel> list = baseMapper.getProjectPanoramaOverview(request);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        // file的id
        List<String> fileRelationIdList = list.stream()
                .map(ProjectPanoramaOverviewModel::getPpoFile)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        Map<String, String> pIdNameMap = list.stream().collect(Collectors.toMap(ProjectPanoramaOverviewModel::getPpoId, ProjectPanoramaOverviewModel::getPName, (existing, replacement) -> existing));
        if (ValidateUtil.isEmpty(fileRelationIdList)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        // 校验文件
        List<SysFileannex> sysFileannexList = sysFileAnnexService.list(Wrappers.<SysFileannex>lambdaQuery().in(SysFileannex::getFileRelationId, fileRelationIdList));
        BaseResponse fileValidationResult = fileFeign.validateFileExist(sysFileannexList, "picture");
        if (!fileValidationResult.isSuccess()) {
            return fileValidationResult;
        }
        // 读取返回值
        if (fileValidationResult.getData() == null) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<SysFileannex> validFileIds = JSONUtil.toList(
                JSONUtil.parseArray(JSONUtil.toJsonStr(fileValidationResult.getData())), SysFileannex.class);
        if (ValidateUtil.isEmpty(validFileIds)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        // 构建返回体
        String downloadUrl = sysConfigService.getConfigByCode("downloadUrl");
        List<CarouselImageDTO> result = validFileIds.stream()
                .map(file -> new CarouselImageDTO(downloadUrl + "/jpm/downloadAloneOpenFile/" + file.getFileId(), pIdNameMap.getOrDefault(file.getFileEntityId(), null)))
                .collect(Collectors.toList());
        return BaseResponse.ok(result);
    }
}
