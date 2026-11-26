package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaKnowledgeBaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.common.OnDragDto;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBase;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseFileService;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseService;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OaKnowledgeBaseImpl extends ServiceImpl<OaKnowledgeBaseMapper, OaKnowledgeBase> implements IOaKnowledgeBaseService {


    @Resource
    private IOaKnowledgeBaseFileService oaKnowledgeBaseFileService;
    @Resource
    private IOaKnowledgeGroupService oaKnowledgeGroupService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaKnowledgeBase(OaKnowledgeBaseModel request, String teId) {
        OaKnowledgeBase entity = new OaKnowledgeBase();
        BeanUtils.copyProperties(request, entity);
        entity.setTeId(teId);
        if (ValidateUtil.isEmpty(entity.getKbParentId()))
            entity.setKbParentId("0");
        if (ValidateUtil.isEmpty(entity.getKbId())) {
            entity.setKbId(GenerateUUID.getUUID());
            entity.setKbCreateTime(new Date());
            baseMapper.insert(entity);
            oaKnowledgeGroupService.copyParentKnowledgeBaseGroup(entity.getKbParentId(), entity.getKbId());
        } else {
            OaKnowledgeBase model = baseMapper.selectById(entity.getKbId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setKbCreateTime(model.getKbCreateTime());
            entity.setKbCreateUid(model.getKbCreateUid());
            entity.setKbCreateUname(model.getKbCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getKbId());
    }

    @Override
    public BaseResponse getOaKnowledgeBaseTree(OaKnowledgeBaseModel request, LogUserInfoVo user) {
        QueryWrapper<OaKnowledgeBase> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(user.getTeId()))
            wrapper.eq("Te_Id", user.getTeId());
        wrapper.orderByAsc("Kb_Sort");
        List<OaKnowledgeBase> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<OaKnowledgeBase> allDatas;
            List<OaKnowledgeRoleDTO> powers = oaKnowledgeGroupService.getUserOaKnowledgePower(user);
            if (!CollectionUtils.isEmpty(powers)) {
                allDatas = data.stream().peek(ad -> {
                    List<OaKnowledgeRoleDTO> dr = powers.stream().filter(n -> n.getKbId().equals(ad.getKbId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dr)) ad.setPowerCodes(dr.stream().findFirst().get().getPowerCodes());
                }).collect(Collectors.toList());
            } else allDatas = data;
            List<OaKnowledgeBaseModel> datas = new ArrayList<>();
            List<OaKnowledgeBase> nodes = allDatas.stream().filter(n -> n.getKbParentId().equals("0")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(nodes)) {
                nodes.forEach(node -> {
                    OaKnowledgeBaseModel model = new OaKnowledgeBaseModel();
                    BeanUtils.copyProperties(node, model);
                    List<OaKnowledgeBaseModel> child = new ArrayList<>();
                    makeChildNodes(allDatas, child, node.getKbId());
                    model.setChildNodes(child);
                    datas.add(model);
                });
            }
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok(null);
    }

    @Override
    public BaseResponse deleteOaKnowledgeBaseById(String kbId) {
        OaKnowledgeBase model = baseMapper.selectById(kbId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<OaKnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("Kb_Parent_Id", kbId);
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        if (oaKnowledgeBaseFileService.getOaKnowledgeBaseFileCountByKbId(kbId) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        oaKnowledgeGroupService.deleteGroupByKbId(kbId);
        baseMapper.deleteById(kbId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse onDragOaKnowledgeBase(OnDragDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getMoveId(), request.getTargetId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        OaKnowledgeBase move = baseMapper.selectById(request.getMoveId());
        OaKnowledgeBase target = baseMapper.selectById(request.getTargetId());
        if (move == null || target == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!move.getKbParentId().equals(target.getKbParentId())) return BaseResponse.fail(ResMsg.onDropOut);
        move.setKbSort(request.getTargetSort());
        target.setKbSort(request.getMoveSort());
        updateBatchById(Arrays.asList(move, target));
        return BaseResponse.ok();
    }

    @Override
    public List<OaKnowledgeBase> getAll() {
        QueryWrapper<OaKnowledgeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("Kb_State", "1");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<String> getAllChildsIds(String kbId) {
        List<String> ids = new ArrayList<>();
        ids.add(kbId);
        List<OaKnowledgeBase> datas = getAll();
        if (datas != null && datas.size() > 0) {
            List<OaKnowledgeBase> pojos = datas.stream().filter(n -> n.getKbParentId().equals(kbId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                for (OaKnowledgeBase pojo : pojos) {
                    ids.add(pojo.getKbId());
                    getAllChildsIds(ids, datas, pojo.getKbId());
                }
            }
        }
        return ids;
    }

    private void getAllChildsIds(List<String> ids, List<OaKnowledgeBase> datas, String kbId) {
        List<OaKnowledgeBase> pojos = datas.stream().filter(n -> n.getKbParentId().equals(kbId)).collect(Collectors.toList());
        if (pojos != null && pojos.size() > 0) {
            for (OaKnowledgeBase pojo : pojos) {
                ids.add(pojo.getKbId());
                getAllChildsIds(ids, datas, pojo.getKbId());
            }
        }
    }

    private void makeChildNodes(List<OaKnowledgeBase> allDatas, List<OaKnowledgeBaseModel> datas, String parentId) {
        List<OaKnowledgeBase> nodes = allDatas.stream().filter(n -> n.getKbParentId().equals(parentId)).collect(Collectors.toList());
        if (nodes != null && nodes.size() > 0) {
            nodes.forEach(node -> {
                OaKnowledgeBaseModel model = new OaKnowledgeBaseModel();
                BeanUtils.copyProperties(node, model);
                List<OaKnowledgeBaseModel> child = new ArrayList<>();
                makeChildNodes(allDatas, child, node.getKbId());
                model.setChildNodes(child);
                datas.add(model);
            });
        }
    }
}
