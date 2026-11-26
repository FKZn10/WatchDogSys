package com.jpmsoft.projectProducer.service.blxw.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Blxw.BlxwTypeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.blxw.BlxwTypeMapper;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwType;
import com.jpmsoft.projectProducer.service.blxw.IBlxwInfoService;
import com.jpmsoft.projectProducer.service.blxw.IBlxwTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IBlxwTypeServiceImpl extends ServiceImpl<BlxwTypeMapper, BlxwType> implements IBlxwTypeService {
    @Autowired
    private IBlxwInfoService blxwInfoService;

    @Override
    public BaseResponse createOrUpdateBlxwType(BlxwTypeModel request) {
        BlxwType entity = new BlxwType();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getId())){
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            if (ValidateUtil.isEmpty(entity.getBlxwParentId()))
                entity.setBlxwParentId("0");
            QueryWrapper<BlxwType> listWrapper = new QueryWrapper<>();
            listWrapper.eq("Blxw_Parent_Id", request.getBlxwParentId());
            Integer count = baseMapper.selectCount(listWrapper).intValue();
            List<BlxwType> list = baseMapper.selectList(listWrapper);
            if (ValidateUtil.isNotEmpty(list)) {
                String parentPath = list.get(0).getBlxwPath();
                if(StrUtil.isNotEmpty(parentPath)){
                    entity.setBlxwPath(parentPath + "/" + entity.getId());
                }else{
                    entity.setBlxwPath(entity.getId());
                }
            }
            entity.setBlxwSort(count + 1);
            baseMapper.insert(entity);
        }else {
            BlxwType model = baseMapper.selectById(entity.getId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (model.getId().equals(request.getBlxwParentId()))
                return BaseResponse.fail(ResMsg.parentNodeError);
            if (ValidateUtil.isEmpty(entity.getBlxwParentId()))
                entity.setBlxwParentId("0");
            QueryWrapper<BlxwType> listWrapper = new QueryWrapper<>();
            listWrapper.eq("Blxw_Parent_Id", request.getBlxwParentId());
            Integer count = baseMapper.selectCount(listWrapper).intValue();
            List<BlxwType> list = baseMapper.selectList(listWrapper);
            if (ValidateUtil.isNotEmpty(list)) {
                String parentPath = list.get(0).getBlxwPath();
                if(StrUtil.isNotEmpty(parentPath)){
                    entity.setBlxwPath(parentPath + "/" + entity.getId());
                }else{
                    entity.setBlxwPath(entity.getId());
                }
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse deleteBlxwTypeById(String id) {
        BlxwType model = baseMapper.selectById(id);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<BlxwType> wrapper = new QueryWrapper<>();
        wrapper.eq("Blxw_Parent_Id", id);
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        if (!blxwInfoService.canToDeleteBlxwType(id))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(id);
        List<BlxwType> sameLevelData = baseMapper.selectSameLevelBySort(model.getBlxwParentId(), model.getBlxwSort());
        if (!CollectionUtils.isEmpty(sameLevelData)) {
            List<BlxwType> updateSorts = new ArrayList<>();
            sameLevelData.forEach(entity -> {
                entity.setBlxwSort(entity.getBlxwSort() - 1);
                updateSorts.add(entity);
            });
            if (!CollectionUtils.isEmpty(updateSorts))
                updateBatchById(updateSorts);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getBlxwTypeTreeList(BlxwTypeModel request,String uid) {
        QueryWrapper<BlxwType> wrapper = new QueryWrapper<>();
        wrapper.eq("State", 1);
        wrapper.orderByAsc("Blxw_Sort");
        List<BlxwType> list = baseMapper.selectList(wrapper);
        List<BlxwType> rootNodes;
        rootNodes = list.stream().filter(n -> n.getBlxwParentId().equals("0")).collect(Collectors.toList());
        List<BlxwTypeModel> trees = new ArrayList<>();
        rootNodes.forEach(node -> {
            BlxwTypeModel tree = new BlxwTypeModel();
            BeanUtils.copyProperties(node, tree);
            List<BlxwTypeModel> childNodes = new ArrayList<>();
            List<BlxwType> childs = list.stream().filter(n -> n.getBlxwParentId().equals(node.getId())).collect(Collectors.toList());
            if (!childs.isEmpty())
                getBlxwTypeChildNodes(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
        return BaseResponse.ok(trees);
    }
    private void getBlxwTypeChildNodes(List<BlxwTypeModel> trees, List<BlxwType> list, List<BlxwType> allList) {
        list.forEach(node -> {
            BlxwTypeModel tree = new BlxwTypeModel();
            BeanUtils.copyProperties(node, tree);
            List<BlxwTypeModel> childNodes = new ArrayList<>();
            List<BlxwType> childs = allList.stream().filter(n -> n.getBlxwParentId().equals(node.getId())).collect(Collectors.toList());
            if (!childs.isEmpty())
                getBlxwTypeChildNodes(childNodes, childs, allList);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
    }
    @Override
    public BaseResponse upOrDownBlxwType(String id,String action) {
        BlxwType entity = baseMapper.selectById(id);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<BlxwType> wrapper = new QueryWrapper<>();
        wrapper.eq("Blxw_Parent_Id", entity.getBlxwParentId());
        wrapper.orderByAsc("Blxw_Sort");
        List<BlxwType> list = baseMapper.selectList(wrapper);
        if (list.size() > 1) {
            BlxwType entity1;
            int index = list.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == list.size())
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getBlxwSort();
            entity.setBlxwSort(entity1.getBlxwSort());
            entity1.setBlxwSort(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else {
            return BaseResponse.fail(ResMsg.actError);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getBlxwTypeByParentId(String parentId) {
        QueryWrapper<BlxwType> wrapper = new QueryWrapper<>();
        wrapper.eq("Blxw_Parent_Id", parentId);
        wrapper.orderByAsc("Blxw_Sort");
        List<BlxwType> list = baseMapper.selectList(wrapper);
        List<BlxwTypeModel> datas = new ArrayList<>();
        if (list != null && list.size() > 0) {
            list.forEach(data -> {
                BlxwTypeModel model = new BlxwTypeModel();
                BeanUtils.copyProperties(data, model);
                datas.add(model);
            });
        }
        return BaseResponse.ok(datas);
    }
}