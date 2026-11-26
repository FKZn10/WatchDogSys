package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysTDepartmentMapper;
import com.jpmsoft.userProducer.pojo.dto.ImportTDepartmentDto;
import com.jpmsoft.userProducer.pojo.dto.TDepartmentDto;
import com.jpmsoft.userProducer.pojo.entity.*;
import com.jpmsoft.userProducer.pojo.vo.VueTDepartmentTreeVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysTDepartmentImpl extends ServiceImpl<SysTDepartmentMapper, SysTDepartment> implements ISysTDepartmentService {

    @Resource
    private ISysTPositionService sysTPositionService;
    @Resource
    private ISysTPositionRoleService sysTPositionRoleService;
    @Resource
    private ISysDepartmentPService sysDepartmentPService;
    @Resource
    private ISysPositionPService sysPositionPService;
    @Resource
    private ISysPositionPRoleService sysPositionPRoleService;

    @Override
    public BaseResponse getT_Department_TreeList(String cId, String toId) {
        List<VueTDepartmentTreeVo> trees = new ArrayList<>();
        List<SysTDepartment> models;
        if (ValidateUtil.isEmpty(cId)) {
            QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(toId))
                wrapper.eq("To_Id", toId);
            wrapper.orderByAsc("TD_Sort");
            models = baseMapper.selectList(wrapper);
        } else
            models = baseMapper.getTDepartmentTreeList(cId);
        getT_Department_TreeList(trees, "0", models);
        return BaseResponse.ok(trees);
    }

    @Override
    public BaseResponse CUDT_Department(TDepartmentDto request) {
        if (ValidateUtil.isEmpty(request.getType()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        String type = request.getType().toUpperCase(Locale.ENGLISH);
        if (type.equals("CREATE") || type.equals("CREATE2") || type.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
            wrapper.eq("Td_Parentid", request.getTdParentId());
            if (ValidateUtil.isNotEmpty(request.getToId()))
                wrapper.eq("To_Id", request.getToId());
            if (type.equals("UPDATE"))
                wrapper.ne("TD_ID", request.getTdId());
            wrapper.and(wrp -> wrp.eq("TD_Code", request.getTdCode())
                    .or().eq("TD_Name", request.getTdName()));
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(BaseMsg.infoIsExist);
        }
        if (type.equals("CREATE") || type.equals("CREATE2")) {
            SysTDepartment pojo = new SysTDepartment();
            pojo.setTdId(GenerateUUID.getUUID());
            pojo.setTdSort(ValidateUtil.isEmpty(request.getTdSort()) ? null : Integer.parseInt(request.getTdSort()));
            pojo.setTdCode(request.getTdCode());
            pojo.setTdSolution(request.getTdSolution());
            pojo.setTdName(request.getTdName());
            pojo.setTdParentid(request.getTdParentId());
            pojo.setToId(request.getToId());
            pojo.setTdType(request.getTdType());
            baseMapper.insert(pojo);
            return BaseResponse.ok();
        } else if (type.equals("UPDATE")) {
            SysTDepartment pojo = baseMapper.selectById(request.getTdId());
            if (pojo != null) {
                pojo.setTdSort(ValidateUtil.isEmpty(request.getTdSort()) ? null : Integer.parseInt(request.getTdSort()));
                pojo.setTdCode(request.getTdCode());
                pojo.setTdSolution(request.getTdSolution());
                pojo.setTdName(request.getTdName());
                pojo.setTdParentid(request.getTdParentId());
                pojo.setTdType(request.getTdType());
                //pojo.setToId(request.getToId());
                baseMapper.updateById(pojo);
                return BaseResponse.ok();
            }
        } else if (type.equals("DELETE")) {
            SysTDepartment pojo = baseMapper.selectById(request.getTdId());
            if (pojo != null) {
                Integer chilCount = getSysTDepartmentCount(pojo.getTdId());
                if (pojo.getTdParentid().equals("0")) {
                    Integer rootCount = getRootTDepartmentCount(pojo.getTdId());
                    if (rootCount == 0)
                        return BaseResponse.fail(BaseMsg.onlyRootNode);
                    else if (chilCount > 0)
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    else {
                        baseMapper.deleteById(request.getTdId());
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        baseMapper.deleteById(request.getTdId());
                        return BaseResponse.ok();
                    }
                }
            }
        } else
            return BaseResponse.fail(BaseMsg.unknownAction);
        return BaseResponse.fail();
    }

    @Override
    public BaseResponse OnDropT_Department(TDepartmentDto request) {
        if (CheckNullUtil.checkString(request.getTdId(), request.getTdSort(), request.getNewtdId(), request.getNewtdSort()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        SysTDepartment entity = baseMapper.selectById(request.getTdId());
        SysTDepartment entity1 = baseMapper.selectById(request.getNewtdId());
        entity.setTdSort(Integer.parseInt(request.getNewtdSort()));
        entity1.setTdSort(Integer.parseInt(request.getTdSort()));
        baseMapper.updateById(entity);
        baseMapper.updateById(entity1);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized BaseResponse ImportT_Department(ImportTDepartmentDto request) {
        if (ValidateUtil.isEmpty(request.getPId()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        if (sysDepartmentPService.havingRecordByPid(request.getPId()))
            return BaseResponse.fail(BaseMsg.infoIsExist);
        List<SysTDepartment> list;
        if (ValidateUtil.isEmpty(request.getCId())) {
            QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
            wrapper.eq("TD_ParentID", "0");
            if (ValidateUtil.isNotEmpty(request.getToId()))
                wrapper.eq("To_Id", request.getToId());
            wrapper.orderByAsc("TD_Sort");
            list = baseMapper.selectList(wrapper);
        } else
            list = baseMapper.getTDepartmentParentList(request.getCId());
        for (SysTDepartment items : list) {
            SysDepartmentP entity1 = new SysDepartmentP();
            entity1.setPdId(GenerateUUID.getUUID());
            entity1.setpId(request.getPId());
            entity1.setPdParentid(items.getTdParentid());
            entity1.setPdName(items.getTdName());
            entity1.setPdCode(items.getTdCode());
            entity1.setPdSort(items.getTdSort());
            entity1.setPdSolution(items.getTdSolution());
            entity1.setPdStatus("1");
            entity1.setPdCreatetime(new Date());
            sysDepartmentPService.insert(entity1);
            List<SysTPosition> list2 = sysTPositionService.getTPositionByTdId(items.getTdId());
            for (SysTPosition item1 : list2) {
                SysPositionP entity2 = new SysPositionP();
                entity2.setPpId(GenerateUUID.getUUID());
                entity2.setPdId(entity1.getPdId());
                entity2.setPpName(item1.getTpName());
                entity2.setPpCode(item1.getTpCode());
                entity2.setPpSort(item1.getTpSort());
                entity2.setPpRemark(item1.getTpRemark());
                entity2.setPpStatus("1");
                entity2.setPpCreatetime(new Date());
                sysPositionPService.insert(entity2);
                List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                for (SysTPositionRole item2 : list3) {
                    SysPositionPRole entity3 = new SysPositionPRole();
                    entity3.setPpRId(GenerateUUID.getUUID());
                    entity3.setPpId(entity2.getPpId());
                    entity3.setRoleId(item2.getRoleId());
                    sysPositionPRoleService.insert(entity3);
                }
            }
            ImportChildren(request.getPId(), items.getTdId(), entity1.getPdId());
        }
        return BaseResponse.ok();
    }

    @Override
    public synchronized BaseResponse syncTDepartment(ImportTDepartmentDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getPId(), request.getToId()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("TD_ParentID", "0");
        wrapper.eq("To_Id", request.getToId());
        wrapper.orderByAsc("TD_Sort");
        List<SysTDepartment> list = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) return BaseResponse.ok();
        List<SysDepartmentP> olds = sysDepartmentPService.selectByPid(request.getPId(), "0");
        for (SysTDepartment items : list) {
            SysDepartmentP entity1;
            Boolean bmNew = false;//标记部门是否新建
            if (CollectionUtils.isEmpty(olds.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPdCode()) && f.getPdCode().equals(items.getTdCode())).collect(Collectors.toList()))) {
                //不存在相同编码项目部门 创建新的
                entity1 = new SysDepartmentP();
                entity1.setPdId(GenerateUUID.getUUID());
                entity1.setpId(request.getPId());
                entity1.setPdParentid("0");
                entity1.setPdName(items.getTdName());
                entity1.setPdCode(items.getTdCode());
                entity1.setPdSort(items.getTdSort());
                entity1.setPdSolution(items.getTdSolution());
                entity1.setPdStatus("1");
                entity1.setPdCreatetime(new Date());
                sysDepartmentPService.insert(entity1);
                olds.add(entity1);
                bmNew = true;
            } else {  //存在相同编码项目部门
                entity1 = olds.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPdCode()) && f.getPdCode().equals(items.getTdCode())).findFirst().get();
            }
            List<SysTPosition> list2 = sysTPositionService.getTPositionByTdId(items.getTdId());
            List<SysPositionP> positionPS = new ArrayList<>();
            if (!bmNew) positionPS = sysPositionPService.selectPositionPByPdId(entity1.getPdId());
            if (bmNew || (!bmNew && CollectionUtils.isEmpty(positionPS))) { //新建部门或者部门下没有岗位数据
                for (SysTPosition item1 : list2) {
                    SysPositionP entity2 = new SysPositionP();
                    entity2.setPpId(GenerateUUID.getUUID());
                    entity2.setPdId(entity1.getPdId());
                    entity2.setPpName(item1.getTpName());
                    entity2.setPpCode(item1.getTpCode());
                    entity2.setPpSort(item1.getTpSort());
                    entity2.setPpRemark(item1.getTpRemark());
                    entity2.setPpStatus("1");
                    entity2.setPpCreatetime(new Date());
                    sysPositionPService.insert(entity2);
                    List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                    for (SysTPositionRole item2 : list3) {
                        SysPositionPRole entity3 = new SysPositionPRole();
                        entity3.setPpRId(GenerateUUID.getUUID());
                        entity3.setPpId(entity2.getPpId());
                        entity3.setRoleId(item2.getRoleId());
                        sysPositionPRoleService.insert(entity3);
                    }
                }
            } else {
                for (SysTPosition item1 : list2) {
                    if (CollectionUtils.isEmpty(positionPS.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPpCode()) && f.getPpCode().equals(item1.getTpCode())).collect(Collectors.toList()))) {
                        //部门已存在且部门下已有岗位数据  但是当前岗位编码不存在则创建岗位
                        SysPositionP entity2 = new SysPositionP();
                        entity2.setPpId(GenerateUUID.getUUID());
                        entity2.setPdId(entity1.getPdId());
                        entity2.setPpName(item1.getTpName());
                        entity2.setPpCode(item1.getTpCode());
                        entity2.setPpSort(item1.getTpSort());
                        entity2.setPpRemark(item1.getTpRemark());
                        entity2.setPpStatus("1");
                        entity2.setPpCreatetime(new Date());
                        sysPositionPService.insert(entity2);
                        List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                        for (SysTPositionRole item2 : list3) {
                            SysPositionPRole entity3 = new SysPositionPRole();
                            entity3.setPpRId(GenerateUUID.getUUID());
                            entity3.setPpId(entity2.getPpId());
                            entity3.setRoleId(item2.getRoleId());
                            sysPositionPRoleService.insert(entity3);
                        }
                    }
                }
            }
            if (bmNew) ImportChildren(request.getPId(), items.getTdId(), entity1.getPdId());
            else syncChildDepartmentP(request.getPId(), items.getTdId(), entity1.getPdId());
        }
        return BaseResponse.ok();
    }

    /**
     * 上级部门为历史数据 同步下级数据
     */
    private void syncChildDepartmentP(String pId, String tdId, String pdId) {
        QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("TD_ParentID", tdId);
        wrapper.orderByAsc("TD_Sort");
        List<SysTDepartment> list = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) return;
        List<SysDepartmentP> olds = sysDepartmentPService.selectByPid(pId, pdId);
        for (SysTDepartment items : list) {
            SysDepartmentP entity1;
            Boolean bmNew = false;//标记部门是否新建
            if (CollectionUtils.isEmpty(olds.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPdCode()) && f.getPdCode().equals(items.getTdCode())).collect(Collectors.toList()))) {
                //不存在相同编码项目部门 创建新的
                entity1 = new SysDepartmentP();
                entity1.setPdId(GenerateUUID.getUUID());
                entity1.setpId(pId);
                entity1.setPdParentid(pdId);
                entity1.setPdName(items.getTdName());
                entity1.setPdCode(items.getTdCode());
                entity1.setPdSort(items.getTdSort());
                entity1.setPdSolution(items.getTdSolution());
                entity1.setPdStatus("1");
                entity1.setPdCreatetime(new Date());
                sysDepartmentPService.insert(entity1);
                olds.add(entity1);
                bmNew = true;
            } else {  //存在相同编码项目部门
                entity1 = olds.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPdCode()) && f.getPdCode().equals(items.getTdCode())).findFirst().get();
            }
            List<SysTPosition> list2 = sysTPositionService.getTPositionByTdId(items.getTdId());
            List<SysPositionP> positionPS = new ArrayList<>();
            if (!bmNew) positionPS = sysPositionPService.selectPositionPByPdId(entity1.getPdId());
            if (bmNew || (!bmNew && CollectionUtils.isEmpty(positionPS))) { //新建部门或者部门下没有岗位数据
                for (SysTPosition item1 : list2) {
                    SysPositionP entity2 = new SysPositionP();
                    entity2.setPpId(GenerateUUID.getUUID());
                    entity2.setPdId(entity1.getPdId());
                    entity2.setPpName(item1.getTpName());
                    entity2.setPpCode(item1.getTpCode());
                    entity2.setPpSort(item1.getTpSort());
                    entity2.setPpRemark(item1.getTpRemark());
                    entity2.setPpStatus("1");
                    entity2.setPpCreatetime(new Date());
                    sysPositionPService.insert(entity2);
                    List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                    for (SysTPositionRole item2 : list3) {
                        SysPositionPRole entity3 = new SysPositionPRole();
                        entity3.setPpRId(GenerateUUID.getUUID());
                        entity3.setPpId(entity2.getPpId());
                        entity3.setRoleId(item2.getRoleId());
                        sysPositionPRoleService.insert(entity3);
                    }
                }
            } else {
                for (SysTPosition item1 : list2) {
                    if (CollectionUtils.isEmpty(positionPS.stream().filter(f -> ValidateUtil.isNotEmpty(f.getPpCode()) && f.getPpCode().equals(item1.getTpCode())).collect(Collectors.toList()))) {
                        //部门已存在且部门下已有岗位数据  但是当前岗位编码不存在则创建岗位
                        SysPositionP entity2 = new SysPositionP();
                        entity2.setPpId(GenerateUUID.getUUID());
                        entity2.setPdId(entity1.getPdId());
                        entity2.setPpName(item1.getTpName());
                        entity2.setPpCode(item1.getTpCode());
                        entity2.setPpSort(item1.getTpSort());
                        entity2.setPpRemark(item1.getTpRemark());
                        entity2.setPpStatus("1");
                        entity2.setPpCreatetime(new Date());
                        sysPositionPService.insert(entity2);
                        List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                        for (SysTPositionRole item2 : list3) {
                            SysPositionPRole entity3 = new SysPositionPRole();
                            entity3.setPpRId(GenerateUUID.getUUID());
                            entity3.setPpId(entity2.getPpId());
                            entity3.setRoleId(item2.getRoleId());
                            sysPositionPRoleService.insert(entity3);
                        }
                    }
                }
            }
            if (bmNew) ImportChildren(pId, items.getTdId(), entity1.getPdId());
            else syncChildDepartmentP(pId, items.getTdId(), entity1.getPdId());
        }
    }

    @Override
    public boolean haveInfoWithTOrgan(String toId) {
        QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("To_Id", toId);
        return baseMapper.exists(wrapper);
    }

    private void ImportChildren(String P_ID, String PID, String ID) {
        if (CheckNullUtil.checkString(P_ID, PID, ID))
            return;
        QueryWrapper<SysTDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("TD_ParentID", PID);
        wrapper.orderByAsc("TD_Sort");
        List<SysTDepartment> list = baseMapper.selectList(wrapper);
        for (SysTDepartment items : list) {
            SysDepartmentP entity1 = new SysDepartmentP();
            entity1.setPdId(GenerateUUID.getUUID());
            entity1.setpId(P_ID);
            entity1.setPdParentid(ID);
            entity1.setPdName(items.getTdName());
            entity1.setPdCode(items.getTdCode());
            entity1.setPdSort(items.getTdSort());
            entity1.setPdSolution(items.getTdSolution());
            entity1.setPdStatus("1");
            entity1.setPdCreatetime(new Date());
            sysDepartmentPService.insert(entity1);
            List<SysTPosition> list2 = sysTPositionService.getTPositionByTdId(items.getTdId());
            for (SysTPosition item1 : list2) {
                SysPositionP entity2 = new SysPositionP();
                entity2.setPpId(GenerateUUID.getUUID());
                entity2.setPdId(entity1.getPdId());
                entity2.setPpName(item1.getTpName());
                entity2.setPpCode(item1.getTpCode());
                entity2.setPpSort(item1.getTpSort());
                entity2.setPpRemark(item1.getTpRemark());
                entity2.setPpStatus("1");
                entity2.setPpCreatetime(new Date());
                sysPositionPService.insert(entity2);
                List<SysTPositionRole> list3 = sysTPositionRoleService.getTPositionRoleByTpId(item1.getTpId());
                for (SysTPositionRole item2 : list3) {
                    SysPositionPRole entity3 = new SysPositionPRole();
                    entity3.setPpRId(GenerateUUID.getUUID());
                    entity3.setPpId(entity2.getPpId());
                    entity3.setRoleId(item2.getRoleId());
                    sysPositionPRoleService.insert(entity3);
                }
            }
            ImportChildren(P_ID, items.getTdId(), entity1.getPdId());
        }
    }


    private void getT_Department_TreeList(List<VueTDepartmentTreeVo> trees, String parentId, List<SysTDepartment> models) {
        for (SysTDepartment td : models) {
            if (td.getTdParentid().equals(parentId)) {
                VueTDepartmentTreeVo tree = new VueTDepartmentTreeVo();
                tree.setKey(td.getTdId());
                tree.setPid(td.getTdParentid());
                tree.setTdCode(td.getTdCode());
                tree.setTdSolution(td.getTdSolution());
                tree.setTdSort(td.getTdSort());
                tree.setValue(td.getTdId());
                tree.setTitle(td.getTdName());
                tree.setTdType(td.getTdType());
                List<VueTDepartmentTreeVo> departmentTrees = new ArrayList<>();
                getT_Department_TreeList(departmentTrees, td.getTdId(), models);
                tree.setChildren(departmentTrees);
                trees.add(tree);
            }
        }
    }


    private Integer getSysTDepartmentCount(String parentId) {
        QueryWrapper<SysTDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TD_ParentID ", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    private Integer getRootTDepartmentCount(String dId) {
        QueryWrapper<SysTDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TD_ParentID", '0');
        queryWrapper.ne("TD_ID", dId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    /**
     * 同步部分项目部门模板到指定模板
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncTDepartmentToOrganization(ImportTDepartmentDto request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isAnObjEmpty(request.getTdId(), request.getToId())) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }

        SysTDepartment root = this.getById(request.getTdId());
        if (ValidateUtil.isNull(root)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        List<SysTDepartment> departments = listChildrenDepartment(root);
        if (ValidateUtil.isEmpty(departments)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        List<SysTDepartment> targetDepartments = this.list(Wrappers.<SysTDepartment>lambdaQuery()
                .eq(SysTDepartment::getToId, request.getToId())
        );

        Map<String, List<SysTDepartment>> targetDepartmentMap = targetDepartments.stream()
                .collect(Collectors.groupingBy(SysTDepartment::getTdParentid));
        Map<String, Integer> nextSortMap = targetDepartmentMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .map(SysTDepartment::getTdSort)
                        .filter(Objects::nonNull)
                        .max(Integer::compareTo)
                        .orElse(0)));

        Map<String, String> departmentIdMap = new HashMap<>();
        List<SysTDepartment> newDepartments = new ArrayList<>();
        List<SysTDepartment> updateDepartments = new ArrayList<>();
        for (SysTDepartment department : departments) {
            String parentId = request.getTdId().equals(department.getTdId())
                    ? "0"
                    : departmentIdMap.get(department.getTdParentid());
            if (ValidateUtil.isEmpty(parentId)) {
                return BaseResponse.fail(BaseMsg.selectSuperiorError);
            }
            List<SysTDepartment> sameParentDepartments = targetDepartmentMap.getOrDefault(parentId, new ArrayList<>());
            SysTDepartment targetDepartment = findSameTDepartment(sameParentDepartments, department);
            if (targetDepartment != null) {
                updateTDepartment(targetDepartment, department);
                updateDepartments.add(targetDepartment);
                departmentIdMap.put(department.getTdId(), targetDepartment.getTdId());
                continue;
            }

            SysTDepartment newDepartment = new SysTDepartment();
            newDepartment.setTdId(GenerateUUID.getUUID());
            newDepartment.setTdName(department.getTdName());
            newDepartment.setTdCode(department.getTdCode());
            newDepartment.setTdParentid(parentId);
            newDepartment.setTdSort(nextTDepartmentSort(nextSortMap, parentId));
            newDepartment.setTdSolution(department.getTdSolution());
            newDepartment.setToId(request.getToId());
            newDepartment.setTdType(department.getTdType());
            this.save(newDepartment);

            departmentIdMap.put(department.getTdId(), newDepartment.getTdId());
            newDepartments.add(newDepartment);
            targetDepartmentMap.computeIfAbsent(parentId, key -> new ArrayList<>()).add(newDepartment);
        }
        if (ValidateUtil.isNotEmpty(updateDepartments)) {
            this.updateBatchById(updateDepartments);
        }

        List<String> departmentIds = departments.stream().map(SysTDepartment::getTdId).collect(Collectors.toList());
        List<SysTPosition> positions = sysTPositionService.list(Wrappers.<SysTPosition>lambdaQuery()
                .in(SysTPosition::getTdId, departmentIds)
        );
        if (ValidateUtil.isEmpty(positions)) {
            return BaseResponse.ok(newDepartments);
        }

        Map<String, List<SysTPosition>> positionMap = positions.stream().collect(Collectors.groupingBy(SysTPosition::getTdId));
        List<SysTPosition> targetPositions = sysTPositionService.list(Wrappers.<SysTPosition>lambdaQuery()
                .in(SysTPosition::getTdId, departmentIdMap.values())
        );
        Map<String, List<SysTPosition>> targetPositionMap = targetPositions.stream()
                .collect(Collectors.groupingBy(SysTPosition::getTdId));
        List<SysTPosition> newPositions = new ArrayList<>();
        List<SysTPosition> updatePositions = new ArrayList<>();
        Map<String, String> positionIdMap = new HashMap<>();
        for (SysTDepartment department : departments) {
            List<SysTPosition> departmentPositions = positionMap.get(department.getTdId());
            if (ValidateUtil.isEmpty(departmentPositions)) {
                continue;
            }
            String targetTdId = departmentIdMap.get(department.getTdId());
            List<SysTPosition> sameDepartmentPositions = targetPositionMap.getOrDefault(targetTdId, new ArrayList<>());
            for (SysTPosition position : departmentPositions) {
                SysTPosition targetPosition = findSameTPosition(sameDepartmentPositions, position);
                if (targetPosition != null) {
                    updateTPosition(targetPosition, position);
                    updatePositions.add(targetPosition);
                    positionIdMap.put(position.getTpId(), targetPosition.getTpId());
                    continue;
                }
                SysTPosition newPosition = new SysTPosition();
                newPosition.setTpId(GenerateUUID.getUUID());
                newPosition.setTdId(targetTdId);
                newPosition.setTpName(position.getTpName());
                newPosition.setTpCode(position.getTpCode());
                newPosition.setTpRemark(position.getTpRemark());
                newPosition.setTpSort(position.getTpSort());
                newPosition.setTpStatus(position.getTpStatus());
                newPositions.add(newPosition);
                positionIdMap.put(position.getTpId(), newPosition.getTpId());
                targetPositionMap.computeIfAbsent(targetTdId, key -> new ArrayList<>()).add(newPosition);
            }
        }
        if (ValidateUtil.isNotEmpty(newPositions)) {
            sysTPositionService.saveBatch(newPositions);
        }
        if (ValidateUtil.isNotEmpty(updatePositions)) {
            sysTPositionService.updateBatchById(updatePositions);
        }
        saveTPositionRoles(positionIdMap);
        return BaseResponse.ok(newDepartments);
    }

    /**
     * 保存岗位角色关联关系。
     * 根据提供的岗位ID映射表，对对应的岗位角色进行复制或转移操作，并保存到目标岗位中。
     *
     * @param positionIdMap 包含原岗位ID与目标岗位ID映射关系的Map集合，键为原岗位ID，值为目标岗位ID。
     *                      如果映射关系为空，将退出操作。
     */
    private void saveTPositionRoles(Map<String, String> positionIdMap) {
        if (ValidateUtil.isEmpty(positionIdMap)) {
            return;
        }
        List<SysTPositionRole> roles = sysTPositionRoleService.list(Wrappers.<SysTPositionRole>lambdaQuery()
                .in(SysTPositionRole::getTpId, positionIdMap.keySet())
        );
        sysTPositionRoleService.remove(Wrappers.<SysTPositionRole>lambdaQuery()
                .in(SysTPositionRole::getTpId, positionIdMap.values())
        );
        if (ValidateUtil.isEmpty(roles)) {
            return;
        }
        List<SysTPositionRole> newRoles = new ArrayList<>();
        for (SysTPositionRole role : roles) {
            String targetTpId = positionIdMap.get(role.getTpId());
            if (ValidateUtil.isEmpty(targetTpId)) {
                continue;
            }
            SysTPositionRole newRole = new SysTPositionRole();
            newRole.setTprId(GenerateUUID.getUUID());
            newRole.setTpId(targetTpId);
            newRole.setRoleId(role.getRoleId());
            newRoles.add(newRole);
        }
        if (ValidateUtil.isNotEmpty(newRoles)) {
            sysTPositionRoleService.saveBatch(newRoles);
        }
    }

    /**
     * 根据根部门，获取其所有子部门列表
     * 通过递归方式，收集所有子部门，并返回包含所有子部门的列表
     *
     * @param root 根部门对象，用于获取其子部门列表。
     * @return 包含所有子部门的列表。
     */
    private List<SysTDepartment> listChildrenDepartment(SysTDepartment root) {
        List<SysTDepartment> allDepartments = this.list(Wrappers.<SysTDepartment>lambdaQuery()
                .eq(SysTDepartment::getToId, root.getToId())
                .orderByAsc(SysTDepartment::getTdSort)
        );
        Map<String, List<SysTDepartment>> childrenMap = allDepartments.stream()
                .collect(Collectors.groupingBy(SysTDepartment::getTdParentid));
        List<SysTDepartment> departments = new ArrayList<>();
        collectChildrenDepartment(root, childrenMap, departments);
        return departments;
    }

    /**
     * 递归收集子部门，将子部门添加到部门列表中
     *
     * @param department 当前部门对象，用于获取其子部门列表。
     * @param childrenMap 包含部门ID与子部门列表映射关系的Map集合，键为部门ID，值为子部门列表。
     * @param departments 包含所有子部门的列表，用于收集子部门。
     */
    private void collectChildrenDepartment(SysTDepartment department, Map<String, List<SysTDepartment>> childrenMap, List<SysTDepartment> departments) {
        departments.add(department);
        List<SysTDepartment> children = childrenMap.get(department.getTdId());
        if (ValidateUtil.isEmpty(children)) {
            return;
        }
        for (SysTDepartment child : children) {
            collectChildrenDepartment(child, childrenMap, departments);
        }
    }

    /**
     * 比较两个部门对象是否相同，根据部门编码或名称进行比较
     *
     * @param targets 目标部门对象。
     * @param source 源部门对象。
     * @return 如果两个部门对象相同，则返回true；否则返回false。
     */
    private SysTDepartment findSameTDepartment(List<SysTDepartment> targets, SysTDepartment source) {
        if (ValidateUtil.isEmpty(source.getTdCode())) {
            return null;
        }
        return targets.stream()
                .filter(target -> source.getTdCode().equals(target.getTdCode()))
                .findFirst()
                .orElse(null);
    }

    private void updateTDepartment(SysTDepartment target, SysTDepartment source) {
        target.setTdName(source.getTdName());
        target.setTdCode(source.getTdCode());
        target.setTdSolution(source.getTdSolution());
        target.setTdType(source.getTdType());
    }

    private SysTPosition findSameTPosition(List<SysTPosition> targets, SysTPosition source) {
        if (ValidateUtil.isEmpty(source.getTpCode())) {
            return null;
        }
        return targets.stream()
                .filter(target -> source.getTpCode().equals(target.getTpCode()))
                .findFirst()
                .orElse(null);
    }

    private void updateTPosition(SysTPosition target, SysTPosition source) {
        target.setTpName(source.getTpName());
        target.setTpCode(source.getTpCode());
        target.setTpRemark(source.getTpRemark());
        target.setTpSort(source.getTpSort());
        target.setTpStatus(source.getTpStatus());
    }

    /**
     * 根据父部门ID，获取其下一个可用的排序值
     *
     * @param nextSortMap 包含部门ID与下一个排序值映射关系的Map集合，键为部门ID，值为下一个排序值。
     * @param parentId 父部门ID，用于获取其下一个可用的排序值。
     * @return 下一个可用的排序值。
     */
    private Integer nextTDepartmentSort(Map<String, Integer> nextSortMap, String parentId) {
        Integer sort = nextSortMap.getOrDefault(parentId, 0) + 1;
        nextSortMap.put(parentId, sort);
        return sort;
    }
}
