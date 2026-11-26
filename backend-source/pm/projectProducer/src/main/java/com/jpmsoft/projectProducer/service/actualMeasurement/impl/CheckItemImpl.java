package com.jpmsoft.projectProducer.service.actualMeasurement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.CheckItemRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.actualMeasurement.CheckItemMapper;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.TaskDistributionCheck;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectory;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBase;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.actualMeasurement.ICheckItemService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionCheckService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckItemImpl extends ServiceImpl<CheckItemMapper, CheckItem> implements ICheckItemService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ITaskDistributionCheckService iTaskDistributionCheckService;

    @Autowired
    private ITaskDistributionService iTaskDistributionService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateCheckItem(CheckItemRequest request, String token) {

        if(ValidateUtil.isEmpty(request)){
            return BaseResponse.fail(ResMsg.notReqParam);

        }

        if(ValidateUtil.isEmpty(request.getCiParent())){
            request.setCiParent("0");
        }
        CheckItem entity = BeanUtils.copyObject(request, CheckItem.class);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);


        if (ValidateUtil.isEmpty(request.getCiId())) {
            //检查编码是否重复
            CheckItem repetition = baseMapper.selectOne(new LambdaQueryWrapper<CheckItem>().eq(CheckItem::getCiCode, request.getCiCode()));
            if(ValidateUtil.isNotNull(repetition)){
                return BaseResponse.fail("检查项编码重复");
            }
            entity.setCiId(GenerateUUID.getUUID());
            LambdaQueryWrapper<CheckItem> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CheckItem::getCiParent, entity.getCiParent());
            wrapper.orderByAsc(CheckItem::getCiOrder);
            List<CheckItem> sameLevels = baseMapper.selectList(wrapper);
            int index =1;
            if(ValidateUtil.isNotEmpty(sameLevels)){
                index= sameLevels.get(sameLevels.size() - 1).getCiOrder()+1;
            }
            entity.setCiOrder(index);
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            baseMapper.insert(entity);

        } else {
            CheckItem model = baseMapper.selectById(request.getCiId());
            if (ValidateUtil.isEmpty(model))
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            baseMapper.updateById(entity);
        }
        List<String> fileIds = Arrays.asList(request.getCiFileIds().split(","));
        if(ValidateUtil.isNotEmpty(fileIds)){
            baseMapper.refreshFileAnexState(fileIds);
        }

        return BaseResponse.ok(entity.getCiId());

    }


    @Override
    public BaseResponse deleteCheckItem(String id) {
        if(ValidateUtil.isEmpty(id)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<CheckItem> list = baseMapper.selectList(new LambdaQueryWrapper<CheckItem>().eq(CheckItem::getCiParent, id));
        if(ValidateUtil.isNotEmpty(list)){
            return BaseResponse.fail("子节点不为空，无法删除该节点");
        }
        List<TaskDistributionCheck> taskDistributionCheckByCiId = iTaskDistributionCheckService.getTaskDistributionCheckByCiId(id);
        if(ValidateUtil.isNotEmpty(taskDistributionCheckByCiId)){
            return BaseResponse.fail("该检查项已分配任务，请先取消分配任务");
        }

        return BaseResponse.ok(baseMapper.deleteById(id));

    }

    @Override
    public BaseResponse getCheckItemById(String id) {
        if(ValidateUtil.isEmpty(id)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        CheckItem entity = baseMapper.selectById(id);
        if(ValidateUtil.isEmpty(entity)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        CheckItemResponse response = BeanUtils.copyObject(entity, CheckItemResponse.class);
        response.setTaskDistributionCheck(iTaskDistributionService.getTaskDistributionCheckByDiId(id));

        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getCheckItemTree(String token) {

        String teId = redisUtils.getTeId(token);
/*        LambdaQueryWrapper<CheckItem> wrapper = new LambdaQueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) {
            wrapper.eq(CheckItem::getTeId, teId);
        }*/
        List<CheckItemResponse> allResponse = baseMapper.getCheckItemList(teId);

        List<CheckItemResponse> data = new ArrayList<>();
        if(ValidateUtil.isNotEmpty(allResponse)){
            data = allResponse.stream().filter(n -> n.getCiParent().equals("0")).sorted(Comparator.comparing(CheckItemResponse::getCiOrder)).collect(Collectors.toList());
            setChildNodes(allResponse, data);
        }
        return BaseResponse.ok(data);
    }


    private void setChildNodes(List<CheckItemResponse> alls, List<CheckItemResponse> data) {
        data.forEach(node -> {
            List<CheckItemResponse> childs = alls.stream().filter(n -> n.getCiParent().equals(node.getCiId())).sorted(Comparator.comparing(CheckItemResponse::getCiOrder)).collect(Collectors.toList());
            setChildNodes(alls, childs);
            node.setChildNodes(childs);
        });
    }

    @Override
    public BaseResponse upOrDownCheckItem(String ciId, String action) {
        if(ValidateUtil.isEmpty(ciId)||ValidateUtil.isEmpty(action)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        CheckItem entity = baseMapper.selectById(ciId);
        if (ValidateUtil.isEmpty(entity))
            return BaseResponse.fail(ResMsg.infoNotExist);
        LambdaQueryWrapper<CheckItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckItem::getCiParent, entity.getCiParent());
        wrapper.orderByAsc(CheckItem::getCiOrder);
        List<CheckItem> sameLevels = baseMapper.selectList(wrapper);
        if (sameLevels != null && sameLevels.size() > 1) {
            CheckItem entity1;
            int index = sameLevels.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == (sameLevels.size()-1))
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getCiOrder();
            entity.setCiOrder(entity1.getCiOrder());
            entity1.setCiOrder(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else
            return BaseResponse.fail(ResMsg.actError);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse selectAll(String token) {
        String teId = redisUtils.getTeId(token);
        LambdaQueryWrapper<CheckItem> wrapper = new LambdaQueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) {
            wrapper.eq(CheckItem::getTeId, teId);
        }
        List<CheckItem> list = baseMapper.selectList(wrapper);
        return BaseResponse.ok(list, list.size());
    }

    @Override
    public BaseResponse onDragCheckItem(OnDragDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getMoveId(), request.getTargetId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        CheckItem move = baseMapper.selectById(request.getMoveId());
        CheckItem target = baseMapper.selectById(request.getTargetId());
        if (ValidateUtil.isEmpty(move) || ValidateUtil.isEmpty(target)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!move.getCiParent().equals(target.getCiParent())) {
            return BaseResponse.fail(ResMsg.onDropOut);
        }
        if(!Objects.equals(move.getCiOrder(), request.getMoveSort())||!Objects.equals(target.getCiOrder(), request.getTargetSort())){
            return BaseResponse.fail(ResMsg.canNotMove);
        }
        move.setCiOrder(request.getTargetSort());
        target.setCiOrder(request.getMoveSort());
        updateBatchById(Arrays.asList(move, target));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getCheckItemList(CheckItemRequest request) {
        LambdaQueryWrapper<CheckItem> queryWrapper = new LambdaQueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getCiNodeType())) {
            queryWrapper.eq(CheckItem::getCiNodeType, request.getCiNodeType());
        }
        if (ValidateUtil.isNotEmpty(request.getCiName())) {
            queryWrapper.like(CheckItem::getCiName, request.getCiName());
        }
        if (ValidateUtil.isNotEmpty(request.getCiCode())) {
            queryWrapper.like(CheckItem::getCiCode, request.getCiCode());
        }
        if (ValidateUtil.isNotEmpty(request.getTeId())) {
            queryWrapper.eq(CheckItem::getTeId, request.getTeId());
        }
        return BaseResponse.ok(baseMapper.selectList(queryWrapper));
    }
}
