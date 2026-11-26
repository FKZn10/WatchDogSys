package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.other.SysPositionUserPMapper;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.DepartmentPDto;
import com.jpmsoft.projectProducer.pojo.dto.other.UserPositionPDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysPositionUserP;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.other.ISysPositionUserPService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/18 13:30
 */
@Service
public class SysPositionUserPImpl extends ServiceImpl<SysPositionUserPMapper, SysPositionUserP> implements ISysPositionUserPService {
    @Resource
    private UserProducerFeign userProducerFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncProjectPositionPUser(PreProject project, List<PpOwnerUnit> ownerUnits, DepartmentPCjConfDTO conf) {
        //项目组织机构内对应编码岗位信息
        List<UserPositionPDTO> positions = baseMapper.selectPositionP(
                project.getpId(), conf.getJtLd(), conf.getJtJbr(), conf.getGldwXmjl(), conf.getGldwFgld(), conf.getXmfzoCode(), conf.getXmfgoCode(), conf.getXmfztCode(), conf.getXmfgtCode()
        );
        //项目组织机构内对应岗位编码的已有用户信息
        List<UserPositionPDTO> positionUsers = baseMapper.selectPositionPUser(
                project.getpId(), conf.getJtLd(), conf.getJtJbr(), conf.getGldwXmjl(), conf.getGldwFgld(), conf.getXmfzoCode(), conf.getXmfgoCode(), conf.getXmfztCode(), conf.getXmfgtCode()
        );

        // 删除业主单位
        if (ownerUnits != null) {
            if ("007".equals(project.getpBuildModel()) && ownerUnits.isEmpty()) {
                DepartmentPDto dto = new DepartmentPDto();
                dto.setpId(project.getpId());
                dto.setType(conf.getYzdwoCode());
                userProducerFeign.deleteOwnerDepartmentP(dto);
                dto.setType(conf.getYzdwtCode());
                userProducerFeign.deleteOwnerDepartmentP(dto);
            } else if ("007".equals(project.getpBuildModel()) && ownerUnits.size() == 1) {
                DepartmentPDto dto = new DepartmentPDto();
                dto.setpId(project.getpId());
                dto.setType(conf.getYzdwtCode());
                userProducerFeign.deleteOwnerDepartmentP(dto);
            }
        } else {
            DepartmentPDto dto = new DepartmentPDto();
            dto.setpId(project.getpId());
            dto.setType(conf.getYzdwoCode());
            userProducerFeign.deleteOwnerDepartmentP(dto);
            dto.setType(conf.getYzdwtCode());
            userProducerFeign.deleteOwnerDepartmentP(dto);
        }

        List<SysPositionUserP> entities = new ArrayList<>();
        setPositionUserP(entities, positions, positionUsers, conf.getJtCode(), conf.getJtLd(), project.getpEngineerLeader(), 1);
        setPositionUserP(entities, positions, positionUsers, conf.getJtCode(), conf.getJtJbr(), project.getpEngineer(), 2);
        setPositionUserP(entities, positions, positionUsers, conf.getGldwCode(), conf.getGldwFgld(), project.getpProjectLeader(), 1);
        setPositionUserP(entities, positions, positionUsers, conf.getGldwCode(), conf.getGldwXmjl(), project.getpProjectManager(), 2);
        // 业主单位组织机构处理
        if (!CollectionUtils.isEmpty(ownerUnits) && ownerUnits.size() <= 2 && "007".equals(project.getpBuildModel())) {
            AtomicInteger index = new AtomicInteger(0);
            ownerUnits.forEach(ownerUnit -> {
                int i = index.getAndIncrement();
                // 部门编码
                String pdCode = null;
                // 岗位编码
                String projectMangerCode = null;
                String projectLeaderCode = null;
                if (i == 0) {
                    pdCode = conf.getYzdwoCode();
                    projectMangerCode = conf.getXmfzoCode();
                    projectLeaderCode = conf.getXmfgoCode();
                } else if (i == 1) {
                    pdCode = conf.getYzdwtCode();
                    projectMangerCode = conf.getXmfztCode();
                    projectLeaderCode = conf.getXmfgtCode();
                }

                // 项目负责人
                setPositionUserP(entities, positions, positionUsers, pdCode, projectMangerCode, ownerUnit.getProjectManger(), i + 1);
                // 项目分管领导
                setPositionUserP(entities, positions, positionUsers, pdCode, projectLeaderCode, ownerUnit.getProjectLeader(), i + 1);

                // 同步业主单位对应的单位id
                userProducerFeign.syncPpOwnerUnit(project.getpId(), pdCode, ownerUnit.getOwnerUnit());
            });
        }
        if (!CollectionUtils.isEmpty(entities)) saveOrUpdateBatch(entities);
    }

    private void setPositionUserP(List<SysPositionUserP> entities, List<UserPositionPDTO> positions, List<UserPositionPDTO> positionUsers, String pdCode, String ppCode, String userId, Integer index) {
        if (ValidateUtil.isEmpty(userId)) {
            return;
        }
        // 检查用户是否存在
        List<UserPositionPDTO> matchedUsers = positionUsers.stream()
                .filter(f -> pdCode.equals(f.getPdCode()) && ppCode.equals(f.getPpCode()))
                .collect(Collectors.toList());

        // 如果没有匹配的用户，新建
        if (matchedUsers.isEmpty()) {
            List<UserPositionPDTO> matchedPositions = positions.stream()
                    .filter(f -> pdCode.equals(f.getPdCode()) && ppCode.equals(f.getPpCode()))
                    .collect(Collectors.toList());
            if (!matchedPositions.isEmpty()) {
                SysPositionUserP p = new SysPositionUserP();
                p.setPupId(GenerateUUID.getUUID());
                p.setPpId(matchedPositions.get(0).getPpId());
                p.setuId(userId);
                p.setPupSort(index);
                entities.add(p);
            }
            return;
        }

        // 如果有匹配的用户，更新
        if (matchedUsers.size() == 1 && !userId.equals(matchedUsers.get(0).getUid())) {
            String ppId = matchedUsers.get(0).getPpId();
            List<SysPositionUserP> userList = lambdaQuery()
                    .eq(SysPositionUserP::getPpId, ppId)
                    .ne(SysPositionUserP::getuId, userId)
                    .list();
            if (userList.size() == 1) {
                SysPositionUserP existing = userList.get(0);
                existing.setuId(userId);
                entities.add(existing);
            }
        }
    }

}
