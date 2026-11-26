package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjecDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjecDatasourceResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjecDatasourceMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjecDatasource;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjecDatasourceService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectLibService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineProjecDatasourceImpl extends ServiceImpl<OutlineProjecDatasourceMapper, OutlineProjecDatasource> implements IOutlineProjecDatasourceService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;

    /**
     * 根据项目大纲id查询项目节点数据源
     *
     * @param oplIds
     * @return
     */
    @Override
    public List<OutlineProjecDatasourceResponse> getOutlineProjecDatasourceByOplIds(List<String> oplIds) {
        if (ValidateUtil.isNotEmpty(oplIds)) {
            List<OutlineProjecDatasourceResponse> data = baseMapper.getOutlineProjecDatasourceByOplIds(oplIds);
            if (ValidateUtil.isNotEmpty(data)) {
                return data;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 新增项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse createProjectDatasource(OutlineProjecDatasourceRequest request, String token) {
        if (datasourceExist(request.getOplId(), request.getOdId())) {
            return BaseResponse.fail(ResMsg.recordExist);
        }
        OutlineProjecDatasource entity = new OutlineProjecDatasource();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        baseMapper.insert(entity);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除项目管理大纲体系节点数据源配置
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public BaseResponse deleteProjectDatasourceById(String id, String token) {
        OutlineProjecDatasource datasource = this.getById(id);
        if (ValidateUtil.isNull(datasource)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(datasource.getOplId(), user);
        return BaseResponse.ok();
    }

    /**
     * 根据odId判断项目管理大纲体系节点数据源配置是否存在
     *
     * @param oplId
     * @param odId
     * @return
     */
    @Override
    public Boolean datasourceExist(String oplId, String odId) {
        LambdaQueryWrapper<OutlineProjecDatasource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutlineProjecDatasource::getOplId, oplId);
        wrapper.eq(OutlineProjecDatasource::getOdId, odId);
        return baseMapper.exists(wrapper);
    }

    /**
     * 修改项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse updateProjectDatasource(OutlineProjecDatasourceRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !datasourceExist(request.getOplId(), request.getOdId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjecDatasource entity = new OutlineProjecDatasource();
        BeanUtils.copyProperties(request, entity);
        baseMapper.insert(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 获取项目管理大纲体系节点数据源配置
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getProjectDatasourceById(String id) {
        OutlineProjecDatasourceResponse response = baseMapper.getProjectDatasourceById(id);
        return BaseResponse.ok(response);
    }

    /**
     * 分页查询项目管理大纲体系节点数据源配置
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getProjectDatasourcePage(OutlineProjecDatasourceRequest request) {
        Page<OutlineProjecDatasourceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineProjecDatasourceResponse> list = baseMapper.getProjectDatasourcePage(page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据tCode和oplId查询数据源id列表
     *
     * @param tCode
     * @param oplId
     * @return
     */
    @Override
    public List<String> getOdIdsByTCodeAndOplId(String tCode, String oplId) {
        // 一个oplId下可能有多个数据源，一个数据源可能有多个tCode，需要从数据源所属的tCode中匹配上传入的
        if (ValidateUtil.isAnObjEmpty(tCode, oplId)) {
            return Collections.emptyList();
        }
        List<OutlineProjecDatasourceResponse> datasource = baseMapper.getOutlineProjecDatasourceByOplIds(Collections.singletonList(oplId));
        if (ValidateUtil.isEmpty(datasource)) {
            return Collections.emptyList();
        }
        return datasource.stream()
                .filter(ds -> ds.getTCodeList() != null && ds.getTCodeList().contains(tCode))
                .map(OutlineProjecDatasourceResponse::getOdId)
                .collect(Collectors.toList());
    }

    /**
     * 根据oplId和odId查询数据源
     *
     * @param oplId
     * @param odId
     * @return
     */
    @Override
    public OutlineProjecDatasourceResponse getProjectDatasourceByOplIdAndOdId(String oplId, String odId) {
        if (ValidateUtil.isAnObjEmpty(oplId, oplId)) {
            return null;
        }
        return baseMapper.getProjectDatasourceByOplIdAndOdId(oplId, odId);
    }
}
