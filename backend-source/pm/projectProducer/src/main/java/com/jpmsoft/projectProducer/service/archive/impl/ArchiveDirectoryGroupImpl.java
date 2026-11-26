package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryGroupDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveDirectoryGroupMapper;
import com.jpmsoft.projectProducer.pojo.dto.archive.DirectoryRoleDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.DirPowerRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectoryGroup;
import com.jpmsoft.projectProducer.pojo.vo.archive.ArchiveDirectoryGroupVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.archive.IArchiveDirectoryGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:18
 */
@Service
public class ArchiveDirectoryGroupImpl extends ServiceImpl<ArchiveDirectoryGroupMapper, ArchiveDirectoryGroup>
        implements IArchiveDirectoryGroupService {
    @Override
    public BaseResponse createOrUpdateArchiveDirectoryGroupBath(List<ArchiveDirectoryGroupDto> request, String teId) {
        if (!CollectionUtils.isEmpty(request)) {
            String adId = request.get(0).getAdId();
            if (ValidateUtil.isEmpty(adId)) return BaseResponse.fail(ResMsg.notReqParam);
            List<String> ids = baseMapper.getIdListByAdId(adId);
            List<String> newIds = request.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId()))
                    .map(ArchiveDirectoryGroupDto::getId).collect(Collectors.toList());
            List<String> delIds = ids.stream().filter(n -> !newIds.contains(n)).collect(Collectors.toList());
            List<ArchiveDirectoryGroup> adds = new ArrayList<>();
            List<ArchiveDirectoryGroup> ups = new ArrayList<>();
            Date now = new Date();
            for (ArchiveDirectoryGroupDto data : request) {
                ArchiveDirectoryGroup entity = new ArchiveDirectoryGroup();
                BeanUtils.copyProperties(data, entity);
                entity.setTeId(teId);
                if (ValidateUtil.isEmpty(entity.getId())) {
                    entity.setId(GenerateUUID.getUUID());
                    entity.setCreateTime(now);
                    adds.add(entity);
                } else
                    ups.add(entity);
            }
            if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
            if (!CollectionUtils.isEmpty(ups)) updateBatchById(ups);
            if (!CollectionUtils.isEmpty(delIds)) removeBatchByIds(delIds);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse createOrUpdateArchiveDirectoryGroup(ArchiveDirectoryGroupDto request) {
        ArchiveDirectoryGroup entity = new ArchiveDirectoryGroup();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(entity.getId()))
            baseMapper.updateById(entity);
        else {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getArchiveDirectoryGroupList(ArchiveDirectoryGroupDto request) {
        QueryMap<ArchiveDirectoryGroupDto> params = new QueryMap<>(request);
        params.put("adId").put("adgType").put("adgName");
        IPage<ArchiveDirectoryGroupVo> page = new Page<>(request.getPage(), request.getSize());
        List<ArchiveDirectoryGroupVo> datas = baseMapper.getArchiveDirectoryGroupList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getArchiveDirectoryGroupByAdId(String adId) {
        List<ArchiveDirectoryGroupVo> datas = baseMapper.getArchiveDirectoryGroupByAdId(adId);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getArchiveDirectoryGroupById(String id) {
        ArchiveDirectoryGroupVo data = baseMapper.getArchiveDirectoryGroupById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse delArchiveDirectoryGroupById(String id) {
        ArchiveDirectoryGroup entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public void delArchiveDirectoryGroupByAdIds(List<String> adIds) {
        QueryWrapper<ArchiveDirectoryGroup> wrapper = new QueryWrapper<>();
        wrapper.in("Ad_Id", adIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<DirectoryRoleDTO> getUserDirectoryPower(LogUserInfoVo user) {
        List<ArchiveDirectoryGroupVo> data = baseMapper.getUserDirectoryPower(user);
        if (!CollectionUtils.isEmpty(data)) {
            Map<String, Set<String>> map = new HashMap<>();
            //自定义角色
            List<String> adIds = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getAdgRoleType()) && d.getAdgRoleType().equals("2")).map(ArchiveDirectoryGroupVo::getAdId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(adIds)) {
                for (String adId : adIds) {
                    if (ValidateUtil.isNotEmpty(adId)) {
                        List<ArchiveDirectoryGroupVo> roles = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getAdgPower())
                                && ValidateUtil.isNotEmpty(d.getAdgRoleType()) && d.getAdgRoleType().equals("2")
                                && ValidateUtil.isNotEmpty(d.getAdId()) && d.getAdId().equals(adId)).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(roles)) {
                            Set<String> powerCodes = new HashSet<>();
                            roles.forEach(r -> {
                                powerCodes.addAll(Arrays.asList(r.getAdgPower().split(",")));
                            });
                            map.put(adId, powerCodes);
                        }
                    }
                }
            }
            //选择的Sys_Role角色 非自定义角色
            List<String> roleIds = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getAdgRoleType()) && d.getAdgRoleType().equals("1")).map(ArchiveDirectoryGroupVo::getAdgRole).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<DirPowerRoleDTO> roles = baseMapper.getDirPowerRole(roleIds);
                if (!CollectionUtils.isEmpty(roles)) {
                    List<ArchiveDirectoryGroupVo> roleADGs = data.stream().filter(d -> ValidateUtil.isNotEmpty(d.getAdgRoleType()) && d.getAdgRoleType().equals("1")).collect(Collectors.toList());
                    for (ArchiveDirectoryGroupVo adg : roleADGs) {
                        Set<String> powerCodes;
                        if (map.containsKey(adg.getAdId())) powerCodes = map.get(adg.getAdId());
                        else powerCodes = new HashSet<>();
                        List<String> powers = roles.stream().filter(n -> n.getRoleId().equals(adg.getAdgRole())).map(DirPowerRoleDTO::getPowerCode).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(powers)) powerCodes.addAll(powers);
                        map.put(adg.getAdId(), powerCodes);
                    }
                }
            }
            List<DirectoryRoleDTO> results = new ArrayList<>();
            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                DirectoryRoleDTO d = new DirectoryRoleDTO();
                d.setAdId(entry.getKey());
                d.setPowerCodes(new ArrayList<>(entry.getValue()));
                results.add(d);
            }
            return results;
        }
        return new ArrayList<>();
    }
}
