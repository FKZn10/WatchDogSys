package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaKnowledgeGroupMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeGroupDTO;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeRoleDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.DirPowerRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeGroup;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaKnowledgeGroupVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseService;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:51
 */
@Service
public class OaKnowledgeGroupImpl extends ServiceImpl<OaKnowledgeGroupMapper, OaKnowledgeGroup> implements IOaKnowledgeGroupService {

    @Resource
    private IOaKnowledgeBaseService oaKnowledgeBaseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaKnowledgeGroupBath(List<OaKnowledgeGroupDTO> request,String teId) {
        if (!CollectionUtils.isEmpty(request)) {
            String kbId = request.get(0).getKbId();
            if (ValidateUtil.isEmpty(kbId)) return BaseResponse.fail(ResMsg.notReqParam);
            List<OaKnowledgeGroup> all = baseMapper.getIdListByKbId(kbId);
            List<String> ids = all.stream().map(OaKnowledgeGroup::getId).collect(Collectors.toList());
            List<String> newIds = request.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId()))
                    .map(OaKnowledgeGroupDTO::getId).collect(Collectors.toList());
            List<String> delIds = ids.stream().filter(n -> !newIds.contains(n)).collect(Collectors.toList());
            List<OaKnowledgeGroup> adds = new ArrayList<>();
            List<OaKnowledgeGroup> ups = new ArrayList<>();
            Date now = new Date();
            List<String> groupIds = new ArrayList<>();
            for (OaKnowledgeGroupDTO data : request) {
                OaKnowledgeGroup entity = new OaKnowledgeGroup();
                BeanUtils.copyProperties(data, entity);
                entity.setKbId(kbId);
                entity.setTeId(teId);
                if (ValidateUtil.isEmpty(entity.getId())) {
                    entity.setId(GenerateUUID.getUUID());
                    entity.setCreateTime(now);
                    adds.add(entity);
                } else
                    ups.add(entity);
                groupIds.add(entity.getKbgGroupId());
            }
            //region 下级目录同步父级权限
            List<String> kbIds = oaKnowledgeBaseService.getAllChildsIds(kbId);
            kbIds.remove(kbId);
            //子目录需要删除的(部门ID和用户ID)
            if (!CollectionUtils.isEmpty(delIds))
                groupIds.addAll(all.stream().filter(n -> delIds.contains(n.getId())).map(OaKnowledgeGroup::getKbgGroupId).collect(Collectors.toList()));
            //重新创建子级目录权限
            for (String id : kbIds) {
                for (OaKnowledgeGroupDTO data : request) {
                    OaKnowledgeGroup entity = new OaKnowledgeGroup();
                    BeanUtils.copyProperties(data, entity);
                    entity.setKbId(id);
                    entity.setId(GenerateUUID.getUUID());
                    entity.setCreateTime(now);
                    entity.setTeId(teId);
                    adds.add(entity);
                }
            }
            //endregion
            //删除子目录内（仅删除相同组和用户的数据）
            deleteGroupByKbIdAndGroupId(kbIds, groupIds);
            if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
            if (!CollectionUtils.isEmpty(ups)) updateBatchById(ups);
            if (!CollectionUtils.isEmpty(delIds)) removeBatchByIds(delIds);
        }
        return BaseResponse.ok();
    }

    @Override
    public void copyParentKnowledgeBaseGroup(String parentKbId, String kbId) {
        if (ValidateUtil.isEmpty(parentKbId) || parentKbId.equals("0")) return;
        QueryWrapper<OaKnowledgeGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("Kb_Id", parentKbId);
        List<OaKnowledgeGroup> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data = data.stream().peek(e -> {
                e.setKbId(kbId);
                e.setId(GenerateUUID.getUUID());
            }).collect(Collectors.toList());
            saveBatch(data);
        }
    }

    @Override
    public BaseResponse getOaKnowledgeGroupByKbId(String kbId) {
        return BaseResponse.ok(baseMapper.getOaKnowledgeGroupByKbId(kbId));
    }

    @Override
    public void deleteGroupByKbId(String kbId) {
        QueryWrapper<OaKnowledgeGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("Kb_Id", kbId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteGroupByKbIdAndGroupId(List<String> kbIds, List<String> groupIds) {
        if (CollectionUtils.isEmpty(kbIds) || CollectionUtils.isEmpty(groupIds)) return;
        QueryWrapper<OaKnowledgeGroup> wrapper = new QueryWrapper<>();
        wrapper.in("Kbg_Group_Id", groupIds);
        wrapper.in("Kb_Id", kbIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<OaKnowledgeRoleDTO> getUserOaKnowledgePower(LogUserInfoVo user) {
        List<OaKnowledgeGroupVO> data = baseMapper.getUserOaKnowledgePower(user);
        if (!CollectionUtils.isEmpty(data)) {
            Map<String, Set<String>> map = new HashMap<>();
            //自定义角色
            List<String> adIds = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getKbgRoleType()) && d.getKbgRoleType().equals("2")).map(OaKnowledgeGroupVO::getKbId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(adIds)) {
                for (String adId : adIds) {
                    if (ValidateUtil.isNotEmpty(adId)) {
                        List<OaKnowledgeGroupVO> roles = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getKbgPower())
                                && ValidateUtil.isNotEmpty(d.getKbgRoleType()) && d.getKbgRoleType().equals("2")
                                && ValidateUtil.isNotEmpty(d.getKbId()) && d.getKbId().equals(adId)).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(roles)) {
                            Set<String> powerCodes = new HashSet<>();
                            roles.forEach(r -> {
                                powerCodes.addAll(Arrays.asList(r.getKbgPower().split(",")));
                            });
                            map.put(adId, powerCodes);
                        }
                    }
                }
            }
            //选择的Sys_Role角色 非自定义角色
            List<String> roleIds = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getKbgRoleType()) && d.getKbgRoleType().equals("1")).map(OaKnowledgeGroupVO::getKbgRole).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<DirPowerRoleDTO> roles = baseMapper.getDirPowerRole(roleIds);
                if (!CollectionUtils.isEmpty(roles)) {
                    List<OaKnowledgeGroupVO> roleADGs = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getKbgRoleType()) && d.getKbgRoleType().equals("1")).collect(Collectors.toList());
                    for (OaKnowledgeGroupVO adg : roleADGs) {
                        Set<String> powerCodes;
                        if (map.containsKey(adg.getKbId())) powerCodes = map.get(adg.getKbId());
                        else powerCodes = new HashSet<>();
                        List<String> powers = roles.stream().filter(n -> n.getRoleId().equals(adg.getKbgRole())).map(DirPowerRoleDTO::getPowerCode).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(powers)) powerCodes.addAll(powers);
                        map.put(adg.getKbId(), powerCodes);
                    }
                }
            }
            List<OaKnowledgeRoleDTO> results = new ArrayList<>();
            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                OaKnowledgeRoleDTO d = new OaKnowledgeRoleDTO();
                d.setKbId(entry.getKey());
                d.setPowerCodes(new ArrayList<>(entry.getValue()));
                results.add(d);
            }
            return results;
        }
        return new ArrayList<>();
    }
}
