package com.jpmsoft.projectProducer.service.actualMeasurement.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.ActualMeasurement.DrawingManagementCatalogueRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.common.model.response.ActualMeasurement.DrawingManagementCatalogueResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.DrawingManagementCatalogueMapper;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistribution;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingItemService;
import com.jpmsoft.projectProducer.service.actualMeasurement.IDrawingManagementCatalogueService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import groovy.transform.Synchronized;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class DrawingManagementCatalogueImpl extends ServiceImpl<DrawingManagementCatalogueMapper, DrawingManagementCatalogue> implements IDrawingManagementCatalogueService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IDrawingItemService drawingItemService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ITaskDistributionService taskDistributionService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDrawingManagementCatalogue(DrawingManagementCatalogueRequest request, String token) {
        String fail = checkParam(request);
        if (fail != null) {
            return BaseResponse.fail(fail);
        }
        DrawingManagementCatalogue entity = BeanUtils.copyObject(request, DrawingManagementCatalogue.class);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (ValidateUtil.isEmpty(request.getDmId())) {
            entity.setDmId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setDmOrder(getMaxOrder(entity.getDmParentId()) +1);
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getDmDrawing(), entity.getDmId());
        } else {
            DrawingManagementCatalogue model = baseMapper.selectById(request.getDmId());
            if (ValidateUtil.isEmpty(model)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(request.getDmDrawing(), request.getDmDrawingIds(), entity.getDmId());
        }
        return BaseResponse.ok(entity.getDmId());
    }

    @Synchronized
    private Integer getMaxOrder(String dmParentId) {
        LambdaQueryWrapper<DrawingManagementCatalogue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrawingManagementCatalogue::getDmParentId, dmParentId);
        queryWrapper.orderByDesc(DrawingManagementCatalogue::getDmOrder);
        queryWrapper.and(wrapper -> wrapper.ne(DrawingManagementCatalogue::getDelFlag, "02")
                .or()
                .isNull(DrawingManagementCatalogue::getDelFlag));

        queryWrapper.last("limit 1");
        DrawingManagementCatalogue model = baseMapper.selectOne(queryWrapper);
        if (model == null) {
            return 0;
        }
        return model.getDmOrder();
    }

    public String checkParam(DrawingManagementCatalogueRequest request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return ResMsg.notReqParam;
        if (ValidateUtil.isEmpty(request.getDmRegionName()))
            return ResMsg.notReqParam;
        if (ValidateUtil.isEmpty(request.getDmParentId())) {
            request.setDmParentId("0");
        }
        if (ValidateUtil.isEmpty(request.getDmCode())) {
            return ResMsg.notReqParam;
        }
        return null;
    }

    @Override
    public BaseResponse getDrawingManagementCatalogueTree(String pId, String token) {
        String uId = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        LambdaQueryWrapper<DrawingManagementCatalogue> queryWrapper =
                new QueryWrapper<DrawingManagementCatalogue>().lambda();
        queryWrapper.eq(DrawingManagementCatalogue::getPId, pId);
        if (ValidateUtil.isNotEmpty(redisUtils.getTeId(token))) {
            queryWrapper.eq(DrawingManagementCatalogue::getTeId, redisUtils.getTeId(token));
        }
        queryWrapper.and(wrapper -> wrapper.ne(DrawingManagementCatalogue::getDelFlag, "02")
                .or()
                .isNull(DrawingManagementCatalogue::getDelFlag));
        List<DrawingManagementCatalogue> alls = baseMapper.selectList(queryWrapper);
        List<DrawingManagementCatalogueResponse> allResponse =
                BeanUtils.copyListData(alls, DrawingManagementCatalogueResponse.class);

        List<Map<String, Object>> statistics = baseMapper.getCatalogueStatisticsByUid(pId, uId);

        Map<String, DrawingStats> statMap = new HashMap<>();
        for (Map<String, Object> row : statistics) {
            String dmId = (String) row.get("dm_id");
            Integer hfHgCount = row.get("hfHgCount") != null ? ((Number) row.get("hfHgCount")).intValue() : 0;
            Integer measuredCount = row.get("measuredCount") != null ? ((Number) row.get("measuredCount")).intValue() : 0;
            statMap.put(dmId, new DrawingStats(hfHgCount, measuredCount));
        }

        for (DrawingManagementCatalogueResponse node : allResponse) {
            DrawingStats stats = statMap.getOrDefault(node.getDmId(), new DrawingStats(0, 0));
            node.setHfHgCount(stats.getHfHgCount());
            node.setMeasuredCount(stats.getMeasuredCount());
        }

        List<DrawingManagementCatalogueResponse> data = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(allResponse)) {
            data = allResponse.stream()
                    .filter(n -> n != null && "0".equals(n.getDmParentId()))
                    .sorted(Comparator.comparingInt(DrawingManagementCatalogueResponse::getDmOrder))
                    .collect(Collectors.toList());
            setChildNodesAndAggregate(allResponse, data);
        }

        return BaseResponse.ok(data);
    }

    /**
     * 设置子节点并进行汇总
     */
    private void setChildNodesAndAggregate(List<DrawingManagementCatalogueResponse> all,
                                           List<DrawingManagementCatalogueResponse> parents) {
        for (DrawingManagementCatalogueResponse parent : parents) {
            List<DrawingManagementCatalogueResponse> children = all.stream()
                    .filter(n -> parent.getDmId().equals(n.getDmParentId()))
                    .sorted(Comparator.comparingInt(DrawingManagementCatalogueResponse::getDmOrder))
                    .collect(Collectors.toList());

            if (ValidateUtil.isNotEmpty(children)) {
                setChildNodesAndAggregate(all, children);
                parent.setChildNodes(children);

                // 汇总子节点数据
                int totalHfHg = parent.getHfHgCount();
                int totalMeasured = parent.getMeasuredCount();
                for (DrawingManagementCatalogueResponse child : children) {
                    totalHfHg += child.getHfHgCount();
                    totalMeasured += child.getMeasuredCount();
                }
                parent.setHfHgCount(totalHfHg);
                parent.setMeasuredCount(totalMeasured);
            }
        }
    }

    /**
     * 辅助类存储统计值
     */
    @Data
    @AllArgsConstructor
    static class DrawingStats {
        private Integer hfHgCount;
        private Integer measuredCount;
    }




    private void setChildNodes(List<DrawingManagementCatalogueResponse> alls, List<DrawingManagementCatalogueResponse> data) {
        data.forEach(node -> {
            List<DrawingManagementCatalogueResponse> childs = alls.stream()
                    .filter(n -> n.getDmParentId().equals(node.getDmId()))
                    .sorted(Comparator.comparingInt(DrawingManagementCatalogueResponse::getDmOrder)).collect(Collectors.toList());
            setChildNodes(alls, childs);
            node.setChildNodes(childs);
        });
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteDrawingManagementCatalogueById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        DrawingManagementCatalogue drawingManagementCatalogue = getById(id);
        if (drawingManagementCatalogue == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        LambdaQueryWrapper<DrawingManagementCatalogue> queryWrapper = new LambdaQueryWrapper<DrawingManagementCatalogue>()
                .eq(DrawingManagementCatalogue::getDmParentId, id)
                .and(wrapper -> wrapper.ne(DrawingManagementCatalogue::getDelFlag, "02")
                .or()
                .isNull(DrawingManagementCatalogue::getDelFlag));;
        List<DrawingManagementCatalogue> child = baseMapper.selectList(queryWrapper);
        if (ValidateUtil.isNotEmpty(child)) {
            return BaseResponse.fail(ResMsg.delDownNodeFirst);
        }
        LambdaQueryWrapper<TaskDistribution> catalogueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        catalogueLambdaQueryWrapper.eq(TaskDistribution::getDmId, id);
        catalogueLambdaQueryWrapper.ne(TaskDistribution::getState, ApprovalStatusEnum.Invalid.getIndex());
        int taskDistributionCount = Math.toIntExact(taskDistributionService.getBaseMapper().selectCount(catalogueLambdaQueryWrapper));
        if (taskDistributionCount > 0) {
            return BaseResponse.fail("存在关联任务，无法删除");
        }
        drawingManagementCatalogue.setDelFlag("02");
        updateById(drawingManagementCatalogue);

        return BaseResponse.ok(id);
    }


//        LambdaQueryWrapper<DrawingItem> queryWrapper1 = new LambdaQueryWrapper<DrawingItem>().eq(DrawingItem::getDiRegionGroup, id);
//        List<DrawingItem> drawingItems = drawingItemService.getBaseMapper().selectList(queryWrapper1);
//        if (ValidateUtil.isNotEmpty(drawingItems)) {
//            return BaseResponse.fail("存在关联数据，无法删除");
//        }

    @Override
    public BaseResponse getDrawingManagementCatalogueById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return BaseResponse.ok(baseMapper.selectById(id));
    }

    @Override
    public BaseResponse getDrawingManagementCatalogueTreeByUser(DrawingManagementCatalogueRequest request) {
        QueryMap<DrawingManagementCatalogueRequest> queryMap = new QueryMap<>(request);
        queryMap.put("userTaskId").put("pId").put("dmRegionName")
                .put("dmParentId").put("dmCode").put("dmDrawing");
        IPage<DrawingManagementCatalogueResponse> page = new Page<>(request.getPage(), request.getSize());
        List<DrawingManagementCatalogueResponse> data = baseMapper.getDrawingManagementCatalogueTreeByUser(page, queryMap.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse upOrDownDrawingManagementCatalogue(String id, String action) {
        if(ValidateUtil.isEmpty(id)||ValidateUtil.isEmpty(action)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        DrawingManagementCatalogue entity = baseMapper.selectById(id);
        if (ValidateUtil.isEmpty(entity))
            return BaseResponse.fail(ResMsg.infoNotExist);
        LambdaQueryWrapper<DrawingManagementCatalogue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DrawingManagementCatalogue::getDmParentId, entity.getDmParentId());
        wrapper.and(queryWrapper -> queryWrapper.ne(DrawingManagementCatalogue::getDelFlag, "02")
                .or()
                .isNull(DrawingManagementCatalogue::getDelFlag));
        wrapper.orderByAsc(DrawingManagementCatalogue::getDmOrder);
        List<DrawingManagementCatalogue> sameLevels = baseMapper.selectList(wrapper);
        if (sameLevels != null && sameLevels.size() > 1) {
            DrawingManagementCatalogue entity1;
            int index = sameLevels.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == (sameLevels.size() - 1))
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getDmOrder();
            entity.setDmOrder(entity1.getDmOrder());
            entity1.setDmOrder(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else
            return BaseResponse.fail(ResMsg.actError);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse onDragDrawingManagementCatalogue(OnDragDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getMoveId(), request.getTargetId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        DrawingManagementCatalogue move = baseMapper.selectById(request.getMoveId());
        DrawingManagementCatalogue target = baseMapper.selectById(request.getTargetId());
        if (ValidateUtil.isEmpty(move) || ValidateUtil.isEmpty(target) ||
                ("02".equals(move.getDelFlag())) || ("02".equals(target.getDelFlag()))) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        if (!move.getDmParentId().equals(target.getDmParentId())) {
            return BaseResponse.fail(ResMsg.onDropOut);
        }
        if(!Objects.equals(move.getDmOrder(), request.getMoveSort())||!Objects.equals(target.getDmOrder(), request.getTargetSort())){
            return BaseResponse.fail(ResMsg.canNotMove);
        }
        move.setDmOrder(request.getTargetSort());
        target.setDmOrder(request.getMoveSort());
        updateBatchById(Arrays.asList(move, target));
        return BaseResponse.ok();
    }

}
