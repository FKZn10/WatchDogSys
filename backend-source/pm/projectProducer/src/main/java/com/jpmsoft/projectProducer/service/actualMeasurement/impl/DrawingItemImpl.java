package com.jpmsoft.projectProducer.service.actualMeasurement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingItemRequest;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.common.model.response.ActualMeasurement.DrawingItemResponse;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.DrawingItemMapper;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingItemService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionCheckService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingManagementCatalogueService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import groovy.transform.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrawingItemImpl extends ServiceImpl<DrawingItemMapper, DrawingItem> implements IDrawingItemService {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IPreProjectService projectService;

    @Autowired
    private IDrawingManagementCatalogueService catalogueService;

    @Resource
    private ITaskDistributionService taskDistributionService;

    @Override
    @Transactional
    public BaseResponse createOrUpdateDrawingItem(DrawingItemRequest request, String token) {
        String fail = checkParam(request);
        if (fail != null) {
            return BaseResponse.fail(fail);
        }
        DrawingItem entity = BeanUtils.copyObject(request, DrawingItem.class);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);


        if (ValidateUtil.isEmpty(request.getDiId())) {
            entity.setDiId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            entity.setStateTime(null);
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getDiId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getDiDrawing(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getDiDrawing(), entity.getDiId());

        } else {
            DrawingItem model = baseMapper.selectById(request.getDiId());
            if (ValidateUtil.isEmpty(model))
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getDiId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getDiDrawing(), request.getDiDrawingIds(), entity.getDiId());
        }
        return BaseResponse.ok(entity.getDiId());
    }


    public String checkParam(DrawingItemRequest request) {
        if (ValidateUtil.isEmpty(request.getPId())) {
            return ResMsg.notReqParam;
        }
        if (ValidateUtil.isEmpty(request.getDiRegionGroup())) {
            return ResMsg.notReqParam;
        }
        if (ValidateUtil.isEmpty(request.getDiRegionName())) {
            return ResMsg.notReqParam;
        }
        if (ValidateUtil.isEmpty(request.getDiRegionCode())) {
            return ResMsg.notReqParam;
        }
        if (ValidateUtil.isEmpty(request.getDiDrawing())) {
            return ResMsg.notReqParam;
        }
        if (ValidateUtil.isEmpty(request.getDiOrder())) {
            return ResMsg.notReqParam;
        }
        return null;
    }


    @Override
    public BaseResponse getDrawingItemList(DrawingItemRequest request, String token) {

        QueryMap<DrawingItemRequest> params = new QueryMap<>(request);
        params.put("diRegionName").put("diRegionCode").put("pId").put("state");
        List<String> diRegionGroups = new ArrayList<>();
        List<DrawingManagementCatalogue> catalogueList;
        if (ValidateUtil.isNotEmpty(request.getDiRegionGroup())) {
            catalogueList = catalogueService.list();
            List<DrawingManagementCatalogue> relatedCatalogues = getAllRelatedCatalogues(request.getDiRegionGroup(), catalogueList);
            diRegionGroups = relatedCatalogues.stream().map(DrawingManagementCatalogue::getDmId).collect(Collectors.toList());
            diRegionGroups.add(request.getDiRegionGroup());
            if (ValidateUtil.isNotEmpty(diRegionGroups)) {
                params.put("diRegionGroups", diRegionGroups);
            }
        }
        IPage<DrawingItemResponse> page = new Page<>(request.getPage(), request.getSize());
        List<DrawingItemResponse> datas = baseMapper.getDrawingItemList(page, params.getQuery());
        return BaseResponse.ok(datas,(int) page.getTotal());
    }

    /**
     * 获取所有相关的目录项（递归）
     *
     * @param parentId      起始父节点ID
     * @param catalogueList 目录项列表
     * @return 所有相关的目录项列表
     */
    private List<DrawingManagementCatalogue> getAllRelatedCatalogues(String parentId, List<DrawingManagementCatalogue> catalogueList) {
        List<DrawingManagementCatalogue> result = new ArrayList<>();

        findChildCataloguesRecursive(parentId, result, catalogueList);

        return result;
    }

    /**
     * 递归查找所有子目录项
     *
     * @param parentId      父节点ID
     * @param result        存储结果的列表
     * @param catalogueList 目录项列表
     */
    private void findChildCataloguesRecursive(String parentId, List<DrawingManagementCatalogue> result, List<DrawingManagementCatalogue> catalogueList) {


        List<DrawingManagementCatalogue> children = catalogueList.stream()
                .filter(catalogue -> catalogue.getDmParentId().equals(parentId))
                .collect(Collectors.toList());

        for (DrawingManagementCatalogue child : children) {
            result.add(child);
            findChildCataloguesRecursive(child.getDmId(), result, catalogueList);
        }
    }

    @Override
    public BaseResponse getDrawingItemById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        DrawingItem model = baseMapper.selectById(id);
        if (model == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        DrawingItemResponse response = new DrawingItemResponse();
        BeanUtils.copyProperties(model, response);

        if (ValidateUtil.isNotEmpty(model.getPId())) {
            PreProject project = projectService.getById(model.getPId());
            if (project != null) {
                response.setPName(project.getpName());
            }
        }
        response.setTaskDistributionCheckModels(taskDistributionService.getTaskDistributionCheckByDiId(id));
        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getDrawingItemListByDmId(String dmId) {

        if (ValidateUtil.isEmpty(dmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        LambdaQueryWrapper<DrawingItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrawingItem::getDiRegionGroup, dmId);
        queryWrapper.eq(DrawingItem::getState, ApprovalStatusEnum.Already_Over.getIndex());
        List<DrawingItemResponse> responses = baseMapper.selectList(queryWrapper).stream()
                .map(item -> BeanUtils.copyObject(item, DrawingItemResponse.class)).collect(Collectors.toList());
        return BaseResponse.ok(responses);
    }


}
