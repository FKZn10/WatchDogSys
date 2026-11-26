package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.UserDepartmentPRequest;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.common.model.requestAndResponse.UserDepartmentPDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.*;
import com.jpmsoft.userProducer.pojo.dto.DepartmentPDto;
import com.jpmsoft.userProducer.pojo.entity.*;
import com.jpmsoft.userProducer.pojo.vo.SysTPositionVo;
import com.jpmsoft.userProducer.pojo.vo.VueDepartmentPTreeVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysDepartmentPImpl extends ServiceImpl<SysDepartmentPMapper, SysDepartmentP> implements ISysDepartmentPService {
    @Resource
    private SysTPositionMapper sysTPositionMapper;
    @Resource
    private SysTPositionRoleMapper sysTPositionRoleMapper;

    @Resource
    private ISysPositionPService sysPositionPService;
    @Resource
    private ISysPositionPRoleService sysPositionPRoleService;
    @Resource
    private ISysPositionRolePService sysPositionRolePService;
    @Resource
    private ISysPositionUserPService sysPositionUserPService;
    @Resource
    private SysPositionUserPMapper sysPositionUserPMapper;
    @Resource
    private SysPositionPMapper sysPositionPMapper;
    @Resource
    private SysDepartmentPMapper sysDepartmentPMapper;
    @Resource
    private PreProjectMapper preProjectMapper;
    @Resource
    private SysPositionPRoleMapper sysPositionPRoleMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SafeResponsibilityPostMapper safeResponsibilityPostMapper;
    @Resource
    private SafeResponsibilityLevelMapper safeResponsibilityLevelMapper;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigBaseService sysConfigBaseService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public List<SysDepartmentP> selectByPid(String pid, String parentId) {
        LambdaQueryWrapper<SysDepartmentP> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDepartmentP::getPdParentid, parentId);
        wrapper.eq(SysDepartmentP::getpId, pid);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BaseResponse getDepartment_PTreeList(DepartmentPDto request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return new BaseResponse(BaseMsg.notReqParam);
        List<VueDepartmentPTreeVo> vueTrees = new ArrayList<>();
        QueryWrapper<SysDepartmentP> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", request.getpId());
        wrapper.orderByAsc("PD_Sort");
        List<SysDepartmentP> models = baseMapper.selectList(wrapper);
        getDepartmentPTreeListTree("0", vueTrees, models, request.getPdType());
        return BaseResponse.ok(vueTrees);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CUDDepartment_P(DepartmentPDto request) {
        String type = request.getType().toUpperCase(Locale.ENGLISH);

        if (type.equals("CREATE") || type.equals("CREATE2") || type.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<SysDepartmentP> wrapper = new QueryWrapper<>();
            wrapper.eq("Pd_parentid", request.getPdParentid());
            wrapper.eq("P_ID", request.getpId());
            if (type.equals("UPDATE"))
                wrapper.ne("PD_ID", request.getPdId());
            wrapper.and(wrp -> wrp.eq("PD_Code", request.getPdCode())
                    .or().eq("PD_Name", request.getPdName()));
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(BaseMsg.infoIsExist);
        }

        if (type.equals("CREATE") || type.equals("CREATE2")) {
            SysDepartmentP pojo = new SysDepartmentP();
            pojo.setPdCode(request.getPdCode());
            pojo.setpId(request.getpId());
            pojo.setPdType(request.getPdType());
            pojo.setPdStatus(request.getPdStatus());
            pojo.setPdSort(ValidateUtil.isEmpty(request.getPdSort()) ? null : Integer.parseInt(request.getPdSort()));
            pojo.setPdSolution(request.getPdSolution());
            pojo.setPdRemark(request.getPdRemark());
            pojo.setPdPath(request.getPdPath());
            pojo.setPdParentid(request.getPdParentid());
            pojo.setPdName(request.getPdName());
            pojo.setPdMdmNo(request.getPdMdmNo());
            pojo.setPdId(request.getPdId());
            pojo.setPdCreateuName(request.getPdCreateuName());
            pojo.setPdCreateuId(request.getPdCreateuID());
            pojo.setPdCreatetime(new Date());
            baseMapper.insert(pojo);
            return BaseResponse.ok();
        } else if (type.equals("UPDATE")) {
            SysDepartmentP pojo = baseMapper.selectById(request.getPdId());
            if (pojo != null) {
                if (pojo.getPdId().equals(request.getPdParentid()))
                    return BaseResponse.fail(BaseMsg.selectSuperiorError);
                pojo.setPdCode(request.getPdCode());
                pojo.setpId(request.getpId());
                pojo.setPdType(request.getPdType());
                pojo.setPdStatus(request.getPdStatus());
                pojo.setPdSort(ValidateUtil.isEmpty(request.getPdSort()) ? null : Integer.parseInt(request.getPdSort()));
                pojo.setPdSolution(request.getPdSolution());
                pojo.setPdRemark(request.getPdRemark());
                pojo.setPdPath(request.getPdPath());
                pojo.setPdParentid(request.getPdParentid());
                pojo.setPdName(request.getPdName());
                pojo.setPdMdmNo(request.getPdMdmNo());
                baseMapper.updateById(pojo);
                return BaseResponse.ok();
            }
        } else if (type.equals("DELETE")) {
            SysDepartmentP pojo = baseMapper.selectById(request.getPdId());
            if (pojo != null) {
                Integer chilCount = getSysDepartmentPCount(pojo.getPdId());
                if (pojo.getPdParentid().equals("0")) {
                    Integer rootCount = getRootDepartmentPCount(pojo.getPdId());
                    if (rootCount == 0)
                        return BaseResponse.fail(BaseMsg.onlyRootNode);
                    else if (chilCount > 0)
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    else {
                        baseMapper.deleteById(request.getPdId());
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        baseMapper.deleteById(request.getPdId());
                        return BaseResponse.ok();
                    }
                }
            }
        } else
            return BaseResponse.fail(BaseMsg.noOperationTypeSelected);
        return BaseResponse.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse OnDropDepartment_P(DepartmentPDto request) {
        if (CheckNullUtil.checkString(request.getPdId(), request.getPdSort(), request.getNewPdId(), request.getNewPdId()))
            return new BaseResponse(BaseMsg.notReqParam);
        SysDepartmentP entity = baseMapper.selectById(request.getPdId());
        SysDepartmentP entity1 = baseMapper.selectById(request.getNewPdId());
        if (!CheckNullUtil.checkObject(entity, entity1)) {
            entity.setPdSort(Integer.parseInt(request.getNewPdSort()));
            entity1.setPdSort(Integer.parseInt(request.getPdSort()));
            baseMapper.updateById(entity);
            baseMapper.updateById(entity1);
        }
        return BaseResponse.ok();
    }

    @Override
    public void insert(SysDepartmentP sysDepartmentP) {
        baseMapper.insert(sysDepartmentP);
    }

    @Override
    public Boolean havingRecordByPid(String pId) {
        QueryWrapper<SysDepartmentP> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized BaseResponse copyProjectDepartmentP(String copyFrom, String copyTo) {
        if (havingRecordByPid(copyTo))
            return BaseResponse.fail(BaseMsg.infoIsExist);
        QueryWrapper<SysDepartmentP> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", copyFrom);
        List<SysDepartmentP> dpList = baseMapper.selectList(wrapper);
        List<String> oldPdId = new ArrayList<>();
        List<String> newPdId = new ArrayList<>();
        List<SysDepartmentP> depList = new ArrayList<>();
        List<SysPositionP> ppList = new ArrayList<>();
        List<SysPositionPRole> pprList = new ArrayList<>();
        List<SysPositionUserP> pupList = new ArrayList<>();
        if (dpList != null && dpList.size() > 0) {
            for (SysDepartmentP dp : dpList) {
                List<SysPositionP> ppNodeList = sysPositionPService.getPositionPByPdId(dp.getPdId());
                String pdId = GenerateUUID.getUUID();
                oldPdId.add(dp.getPdId());
                newPdId.add(pdId);
                dp.setpId(copyTo);
                dp.setPdId(pdId);
//                dp.setPdParentid(newPdId.get(oldPdId.indexOf(dp.getPdParentid())));
                if (ppNodeList != null && ppNodeList.size() > 0) {
                    for (SysPositionP pp : ppNodeList) {
                        List<SysPositionPRole> pprL = sysPositionPRoleService.getPositionPRoleByPpId(pp.getPpId());
                        List<SysPositionUserP> pupL = sysPositionUserPService.getPositionUserPByPPId(pp.getPpId());
                        String newPpId = GenerateUUID.getUUID();
                        pp.setPpId(newPpId);
                        pp.setPdId(pdId);
                        ppList.add(pp);
                        if (pprL != null && pprL.size() > 0) {
                            for (SysPositionPRole ppr : pprL) {
                                ppr.setPpId(newPpId);
                                ppr.setPpRId(GenerateUUID.getUUID());
                                pprList.add(ppr);
                            }
                        }
                        if (pupL != null && pupL.size() > 0) {
                            for (SysPositionUserP pup : pupL) {
                                pup.setPpId(newPpId);
                                pup.setPupId(GenerateUUID.getUUID());
                                pupList.add(pup);
                            }
                        }
                    }
                }
                depList.add(dp);
            }

            List<SysDepartmentP> list = new ArrayList<>();
            for (SysDepartmentP dep : depList) {
                if (!ValidateUtil.isEmpty(dep.getPdParentid()) && !dep.getPdParentid().equals("0")) {
                    dep.setPdParentid(newPdId.get(oldPdId.indexOf(dep.getPdParentid())));
                }
                list.add(dep);
            }
            saveBatch(list);
            if (ppList != null && ppList.size() > 0)
                sysPositionPService.saveBatch(ppList);
            if (pprList != null && pprList.size() > 0)
                sysPositionPRoleService.saveBatch(pprList);
            if (pupList != null && pupList.size() > 0)
                sysPositionUserPService.saveBatch(pupList);
//            oldPdId.clear();
//            newPdId.clear();
//            dpList.clear();
        }
        return BaseResponse.ok();
    }

    @Override
    public List<String> getUserAllSysDepPProject(String uId) {
        return baseMapper.getUserAllSysDepPProject(uId);
    }

    @Override
    public BaseResponse AddUserDepartmentP(UserDepartmentPRequest request) {
        if (request.getProjectId() == null)
            return BaseResponse.fail(BaseMsg.notReqParam);
        List<UserDepartmentPDto> list = new ArrayList<>();

        PreProject project = preProjectMapper.selectById(request.getProjectId());
        String pEngineer = project.getPEngineer();
        String pEngineerLeader = project.getPEngineerLeader();
        String pProjectManager = project.getPProjectManager();
        String pProjectLeader = project.getPProjectLeader();
        UserDepartmentPDto dto1 = new UserDepartmentPDto();
        dto1.setUId(pEngineer);
        dto1.setPpName("工程部经办人");
        dto1.setRoleCode("SysRole-000026");
        dto1.setRoleName("项目管理（集团）");
        dto1.setProjectId(request.getProjectId());
        list.add(dto1);
        UserDepartmentPDto dto2 = new UserDepartmentPDto();
        dto2.setUId(pEngineerLeader);
        dto2.setPpName("工程部分管部长");
        dto2.setRoleCode("SysRole-000048");
        dto2.setRoleName("工程部分管部长");
        dto2.setProjectId(request.getProjectId());
        list.add(dto2);
        UserDepartmentPDto dto3 = new UserDepartmentPDto();
        dto3.setUId(pProjectManager);
        dto3.setPpName("项目经理");
        dto3.setRoleCode("SysRole-000005");
        dto3.setRoleName("项目经理");
        dto3.setProjectId(request.getProjectId());
        list.add(dto3);
        UserDepartmentPDto dto4 = new UserDepartmentPDto();
        dto4.setUId(pProjectLeader);
        dto4.setPpName("项目分管领导");
        dto4.setRoleCode("SysRole-000023");
        dto4.setRoleName("项目分管");
        dto4.setProjectId(request.getProjectId());
        list.add(dto4);
        for (UserDepartmentPDto dto : list) {
            SysPositionUserP entity = new SysPositionUserP();
            String ppName = dto.getPpName();
            entity.setPupId(GenerateUUID.getUUID());
            entity.setuId(dto.getUId());
            QueryWrapper<SysDepartmentP> wrapper = new QueryWrapper<>();
            wrapper.eq("P_ID", dto.getProjectId()).eq("PD_Name", "项目管理单位");
            List<SysDepartmentP> models = baseMapper.selectList(wrapper);
            SysRole sysRole;
            QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
            sysRoleQueryWrapper.eq("Role_Name", dto.getRoleName());
            sysRole = sysRoleMapper.selectOne(sysRoleQueryWrapper);
            if (sysRole == null) {
                return BaseResponse.fail(BaseMsg.notRole.replace("{role}", dto.getRoleName()));
            }
            if (!models.isEmpty()) {
                SysDepartmentP sysDepartmentP = models.get(0);
                if (sysDepartmentP != null) {
                    //获取该项目下的所有职位
                    List<SysPositionP> SysPositionPModels = sysPositionPService.getPositionPByPdId(sysDepartmentP.getPdId());
                    if (!SysPositionPModels.isEmpty()) {
                        //判断是否存在该职位
                        SysPositionP sysPositionP = SysPositionPModels.stream().filter(n -> n.getPpName().equals(ppName)).findFirst().orElse(null);
                        //若存在职位则直接添加用户
                        if (sysPositionP != null) {
                            SysPositionUserP sysPositionUserP = sysPositionUserPMapper.selectOne(new QueryWrapper<SysPositionUserP>().eq("u_Id", dto.getUId()).eq("pp_Id", sysPositionP.getPpId()));
                            if (sysPositionUserP == null) {
                                entity.setPpId(sysPositionP.getPpId());
                                //获取该职位下最高排序
                                Integer maxSort = sysPositionUserPMapper.getMaxPupSort(sysPositionP.getPpId());
                                entity.setPupSort(maxSort == null ? 1 : maxSort + 1);
                                sysPositionUserPMapper.insert(entity);
                            }
                        } else {
                            //创建职位
                            SysPositionP sysPositionPObj = new SysPositionP();
                            sysPositionPObj.setPpId(GenerateUUID.getUUID());
                            sysPositionPObj.setPpName(ppName);
                            sysPositionPObj.setPdId(sysDepartmentP.getPdId());
                            //获取该部门下最高职位排序
                            Integer maxSort = sysPositionPMapper.getMaxPpSort(sysDepartmentP.getPdId());
                            sysPositionPObj.setPpSort(maxSort == null ? 1 : maxSort + 1);
                            sysPositionPObj.setPpStatus("1");
                            sysPositionPObj.setPpCreatetime(new Date());
                            sysPositionPService.insert(sysPositionPObj);
                            createPositionPRole(sysPositionPObj.getPpId(), sysRole.getRoleId());
                            entity.setPpId(sysPositionPObj.getPpId());
                            entity.setPupSort(1);
                            sysPositionUserPMapper.insert(entity);
                        }
                    } else {
                        SysPositionP sysPositionPObj = new SysPositionP();
                        sysPositionPObj.setPpId(GenerateUUID.getUUID());
                        sysPositionPObj.setPpName(ppName);
                        sysPositionPObj.setPdId(sysDepartmentP.getPdId());
                        //获取该部门下最高职位排序
                        sysPositionPObj.setPpSort(1);
                        sysPositionPObj.setPpStatus("1");
                        sysPositionPObj.setPpCreatetime(new Date());
                        sysPositionPService.insert(sysPositionPObj);
                        createPositionPRole(sysPositionPObj.getPpId(), sysRole.getRoleId());
                        entity.setPpId(sysPositionPObj.getPpId());
                        entity.setPupSort(1);
                        sysPositionUserPMapper.insert(entity);
                    }
                } else {
                    sysDepartmentP = new SysDepartmentP();
                    generateDepartment(dto, sysDepartmentP);
                    Integer maxSort = sysDepartmentPMapper.getMaxSort(dto.getProjectId());
                    sysDepartmentP.setPdSort(maxSort == null ? 1 : maxSort + 1);
                    sysDepartmentPMapper.insert(sysDepartmentP);
                    SysPositionP sysPositionPObj = new SysPositionP();
                    sysPositionPObj.setPpId(GenerateUUID.getUUID());
                    sysPositionPObj.setPpName(ppName);
                    sysPositionPObj.setPdId(sysDepartmentP.getPdId());
                    //获取该部门下最高职位排序
                    sysPositionPObj.setPpSort(1);
                    sysPositionPObj.setPpStatus("1");
                    sysPositionPObj.setPpCreatetime(new Date());
                    sysPositionPService.insert(sysPositionPObj);
                    createPositionPRole(sysPositionPObj.getPpId(), sysRole.getRoleId());
                    entity.setPpId(sysPositionPObj.getPpId());
                    entity.setPupSort(1);
                    sysPositionUserPMapper.insert(entity);
                }
            } else {
                SysDepartmentP sysDepartmentP = new SysDepartmentP();
                generateDepartment(dto, sysDepartmentP);
                sysDepartmentP.setPdSort(1);
                sysDepartmentPMapper.insert(sysDepartmentP);
                SysPositionP sysPositionPObj = new SysPositionP();
                sysPositionPObj.setPpId(GenerateUUID.getUUID());
                sysPositionPObj.setPpName(ppName);
                sysPositionPObj.setPdId(sysDepartmentP.getPdId());
                //获取该部门下最高职位排序
                sysPositionPObj.setPpSort(1);
                sysPositionPObj.setPpStatus("1");
                sysPositionPObj.setPpCreatetime(new Date());
                sysPositionPService.insert(sysPositionPObj);
                createPositionPRole(sysPositionPObj.getPpId(), sysRole.getRoleId());
                entity.setPpId(sysPositionPObj.getPpId());
                entity.setPupSort(1);
                sysPositionUserPMapper.insert(entity);
            }
        }
        return BaseResponse.ok();
    }

    private void generateDepartment(UserDepartmentPDto dto, SysDepartmentP sysDepartmentP) {
        sysDepartmentP.setPdId(GenerateUUID.getUUID());
        sysDepartmentP.setPdName("项目管理单位");
        sysDepartmentP.setPdStatus("1");
        sysDepartmentP.setPdType("1");
        sysDepartmentP.setpId(dto.getProjectId());
        sysDepartmentP.setPdCreatetime(new Date());
        sysDepartmentP.setPdCode("GLDW");
        sysDepartmentP.setPdPath("");
        sysDepartmentP.setPdParentid("0");
        sysDepartmentP.setPdSolution("");
        sysDepartmentP.setPdRemark("");
        sysDepartmentP.setPdCreateuId(dto.getUId());
    }

    public void createPositionPRole(String ppId, String roleIds) {
        QueryWrapper<SysPositionPRole> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", ppId);
        sysPositionPRoleMapper.delete(wrapper);
        if (!ValidateUtil.isEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            for (String id : ids) {
                SysPositionPRole entity = new SysPositionPRole();
                entity.setPpId(ppId);
                entity.setRoleId(id);
                entity.setPpRId(GenerateUUID.getUUID());
                sysPositionPRoleMapper.insert(entity);
            }
        }
    }

    private boolean getDepartmentPTreeListTree(String parentId, List<VueDepartmentPTreeVo> trees, List<SysDepartmentP> models,
                                               String pdType) {
        boolean hasMatched = false;
        for (SysDepartmentP model : models) {
            if (model.getPdParentid().equals(parentId)) {
                VueDepartmentPTreeVo tree = new VueDepartmentPTreeVo();
                tree.setKey(model.getPdId());
                tree.setTitle(model.getPdName());
                tree.setValue(model.getPdId());
                tree.setPid(model.getPdParentid());
                tree.setPdCode(model.getPdCode());
                tree.setpId(model.getpId());
                tree.setPdMdmNo(model.getPdMdmNo());
                tree.setPdRemark(model.getPdRemark());
                tree.setPdPath(model.getPdPath());
                tree.setPdSolution(model.getPdSolution());
                tree.setPdSort(model.getPdSort());
                tree.setPdStatus(model.getPdStatus());
                tree.setPdType(model.getPdType());
                List<VueDepartmentPTreeVo> childs = new ArrayList<>();
                boolean childMatched = getDepartmentPTreeListTree(model.getPdId(), childs, models, pdType);
                boolean selfMatched = ValidateUtil.isEmpty(pdType) || pdType.equals(model.getPdType());
                if (!selfMatched && !childMatched) {
                    continue;
                }
                tree.setChildren(childs);
                trees.add(tree);
                hasMatched = true;
            }
        }
        return hasMatched;
    }

    private Integer getSysDepartmentPCount(String parentId) {
        QueryWrapper<SysDepartmentP> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PD_ParentID ", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    private Integer getRootDepartmentPCount(String dId) {
        QueryWrapper<SysDepartmentP> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PD_ParentID", '0');
        queryWrapper.ne("PD_ID", dId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    /**
     * 删除指定业主单位
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOwnerDepartmentP(DepartmentPDto request) {
        LambdaQueryWrapper<SysDepartmentP> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDepartmentP::getpId, request.getpId());
        List<SysDepartmentP> list = this.list(queryWrapper);

        // 筛选出pdCode为指定内容的项目的pdId
        List<String> pdIds = list.stream()
                .filter(item -> item.getPdCode().startsWith(request.getType()))
                .map(SysDepartmentP::getPdId)
                .collect(Collectors.toList());

        // 根据pdId删除对应的记录
        if (!pdIds.isEmpty()) {
            if (this.removeBatchByIds(pdIds)) {
                return BaseResponse.ok();
            } else {
                return BaseResponse.fail("删除失败");
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 同步业主单位
     *
     * @param pId
     * @param pdCode
     * @param ownerUnit
     * @return
     */
    @Override
    public BaseResponse syncPpOwnerUnit(String pId, String pdCode, String ownerUnit) {
        LambdaQueryWrapper<SysDepartmentP> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDepartmentP::getpId, pId)
                .eq(SysDepartmentP::getPdCode, pdCode);
        SysDepartmentP departmentP = getOne(wrapper);
        if (BeanUtil.isEmpty(departmentP)) {
            return BaseResponse.fail("未找到对应的业主单位");
        }
        departmentP.setOwnerUnit(ownerUnit);
        if (updateById(departmentP)) {
            return BaseResponse.ok();
        }
        return BaseResponse.fail("同步失败");
    }

    /**
     * 同步部分项目部门到所有项目
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncDepartmentToProject(DepartmentPDto request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getPdId())) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }
        SysDepartmentP root = this.getById(request.getPdId());
        if (ValidateUtil.isNull(root)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }

        List<SysDepartmentP> sourceDepartments = listChildrenDepartmentP(root);
        if (ValidateUtil.isEmpty(sourceDepartments)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        List<String> sourcePdIds = sourceDepartments.stream().map(SysDepartmentP::getPdId).collect(Collectors.toList());
        List<SysPositionP> sourcePositions = sysPositionPService.list(new LambdaQueryWrapper<SysPositionP>()
                .in(SysPositionP::getPdId, sourcePdIds)
        );
        Map<String, List<SysPositionP>> sourcePositionMap = sourcePositions.stream()
                .collect(Collectors.groupingBy(SysPositionP::getPdId));

        List<String> sourcePpIds = sourcePositions.stream().map(SysPositionP::getPpId).collect(Collectors.toList());
        List<SysPositionRoleP> sourceRoles = ValidateUtil.isEmpty(sourcePpIds)
                ? new ArrayList<>()
                : sysPositionRolePService.list(new LambdaQueryWrapper<SysPositionRoleP>()
                .in(SysPositionRoleP::getPpId, sourcePpIds)
        );
        Map<String, List<SysPositionRoleP>> sourceRoleMap = sourceRoles.stream()
                .collect(Collectors.groupingBy(SysPositionRoleP::getPpId));

        List<PreProject> projects = preProjectMapper.selectList(new LambdaQueryWrapper<PreProject>()
                .eq(PreProject::getPStatus, "1")
        );
        if (ValidateUtil.isEmpty(projects)) {
            return BaseResponse.ok();
        }

        List<SysDepartmentP> newDepartments = new ArrayList<>();
        List<SysDepartmentP> updateDepartments = new ArrayList<>();
        List<SysPositionP> newPositions = new ArrayList<>();
        List<SysPositionP> updatePositions = new ArrayList<>();
        List<SysPositionRoleP> newRoles = new ArrayList<>();
        List<String> roleTargetPpIds = new ArrayList<>();
        for (PreProject project : projects) {
            if (root.getpId().equals(project.getPId())) {
                continue;
            }
            copyDepartmentPToProject(project.getPId(), request.getPdId(), sourceDepartments, sourcePositionMap,
                    sourceRoleMap, newDepartments, updateDepartments, newPositions, updatePositions, newRoles, roleTargetPpIds);
        }

        if (!ValidateUtil.isEmpty(newDepartments)) {
            this.saveBatch(newDepartments);
        }
        if (!ValidateUtil.isEmpty(updateDepartments)) {
            this.updateBatchById(updateDepartments);
        }
        if (!ValidateUtil.isEmpty(newPositions)) {
            sysPositionPService.saveBatch(newPositions);
        }
        if (!ValidateUtil.isEmpty(updatePositions)) {
            sysPositionPService.updateBatchById(updatePositions);
        }
        if (!ValidateUtil.isEmpty(roleTargetPpIds)) {
            sysPositionRolePService.remove(new LambdaQueryWrapper<SysPositionRoleP>()
                    .in(SysPositionRoleP::getPpId, roleTargetPpIds)
            );
        }
        if (!ValidateUtil.isEmpty(newRoles)) {
            sysPositionRolePService.saveBatch(newRoles);
        }
        return BaseResponse.ok();
    }

    /**
     * 获取子部门列表
     *
     * @param root
     * @return
     */
    private List<SysDepartmentP> listChildrenDepartmentP(SysDepartmentP root) {
        List<SysDepartmentP> allDepartments = this.list(new LambdaQueryWrapper<SysDepartmentP>()
                .eq(SysDepartmentP::getpId, root.getpId())
                .orderByAsc(SysDepartmentP::getPdSort)
        );
        Map<String, List<SysDepartmentP>> childrenMap = allDepartments.stream()
                .collect(Collectors.groupingBy(SysDepartmentP::getPdParentid));
        List<SysDepartmentP> departments = new ArrayList<>();
        collectChildrenDepartmentP(root, childrenMap, departments);
        return departments;
    }

    /**
     * 递归收集子部门
     *
     * @param department
     * @param childrenMap
     * @param departments
     */
    private void collectChildrenDepartmentP(SysDepartmentP department, Map<String, List<SysDepartmentP>> childrenMap,
                                            List<SysDepartmentP> departments) {
        departments.add(department);
        List<SysDepartmentP> children = childrenMap.get(department.getPdId());
        if (ValidateUtil.isEmpty(children)) {
            return;
        }
        for (SysDepartmentP child : children) {
            collectChildrenDepartmentP(child, childrenMap, departments);
        }
    }

    /**
     * 复制部门到项目
     *
     * @param targetPId
     * @param rootPdId
     * @param sourceDepartments
     * @param sourcePositionMap
     * @param sourceRoleMap
     * @param newDepartments
     * @param newPositions
     * @param newRoles
     */
    private void copyDepartmentPToProject(String targetPId, String rootPdId, List<SysDepartmentP> sourceDepartments,
                                          Map<String, List<SysPositionP>> sourcePositionMap,
                                          Map<String, List<SysPositionRoleP>> sourceRoleMap,
                                          List<SysDepartmentP> newDepartments, List<SysDepartmentP> updateDepartments,
                                          List<SysPositionP> newPositions, List<SysPositionP> updatePositions,
                                          List<SysPositionRoleP> newRoles, List<String> roleTargetPpIds) {
        List<SysDepartmentP> targetDepartments = this.list(new LambdaQueryWrapper<SysDepartmentP>()
                .eq(SysDepartmentP::getpId, targetPId)
        );
        Map<String, List<SysDepartmentP>> targetDepartmentMap = targetDepartments.stream()
                .collect(Collectors.groupingBy(SysDepartmentP::getPdParentid));
        Map<String, Integer> nextSortMap = targetDepartmentMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .map(SysDepartmentP::getPdSort)
                        .filter(sort -> sort != null)
                        .max(Integer::compareTo)
                        .orElse(0)));

        Map<String, String> departmentIdMap = new HashMap<>();
        Map<String, String> positionIdMap = new HashMap<>();
        List<String> targetPdIds = targetDepartments.stream().map(SysDepartmentP::getPdId).collect(Collectors.toList());
        List<SysPositionP> targetPositions = ValidateUtil.isEmpty(targetPdIds)
                ? new ArrayList<>()
                : sysPositionPService.list(new LambdaQueryWrapper<SysPositionP>()
                .in(SysPositionP::getPdId, targetPdIds)
        );
        Map<String, List<SysPositionP>> targetPositionMap = targetPositions.stream()
                .collect(Collectors.groupingBy(SysPositionP::getPdId));
        for (SysDepartmentP sourceDepartment : sourceDepartments) {
            String targetParentId = rootPdId.equals(sourceDepartment.getPdId())
                    ? "0"
                    : departmentIdMap.get(sourceDepartment.getPdParentid());
            if (ValidateUtil.isEmpty(targetParentId)) {
                continue;
            }
            List<SysDepartmentP> sameParentDepartments = targetDepartmentMap.getOrDefault(targetParentId, new ArrayList<>());
            SysDepartmentP targetDepartment = findSameDepartmentP(sameParentDepartments, sourceDepartment);
            if (targetDepartment != null) {
                updateDepartmentP(targetDepartment, sourceDepartment);
                updateDepartments.add(targetDepartment);
                departmentIdMap.put(sourceDepartment.getPdId(), targetDepartment.getPdId());
            } else {
                targetDepartment = copyDepartmentP(sourceDepartment, targetPId, targetParentId,
                        nextDepartmentPSort(nextSortMap, targetParentId));
                departmentIdMap.put(sourceDepartment.getPdId(), targetDepartment.getPdId());
                newDepartments.add(targetDepartment);
                targetDepartmentMap.computeIfAbsent(targetParentId, key -> new ArrayList<>()).add(targetDepartment);
            }

            List<SysPositionP> positions = sourcePositionMap.get(sourceDepartment.getPdId());
            if (ValidateUtil.isEmpty(positions)) {
                continue;
            }
            List<SysPositionP> sameDepartmentPositions = targetPositionMap.getOrDefault(targetDepartment.getPdId(), new ArrayList<>());
            for (SysPositionP sourcePosition : positions) {
                SysPositionP targetPosition = findSamePositionP(sameDepartmentPositions, sourcePosition);
                if (targetPosition != null) {
                    updatePositionP(targetPosition, sourcePosition);
                    updatePositions.add(targetPosition);
                    positionIdMap.put(sourcePosition.getPpId(), targetPosition.getPpId());
                    roleTargetPpIds.add(targetPosition.getPpId());
                    continue;
                }
                targetPosition = copyPositionP(sourcePosition, targetDepartment.getPdId());
                positionIdMap.put(sourcePosition.getPpId(), targetPosition.getPpId());
                roleTargetPpIds.add(targetPosition.getPpId());
                newPositions.add(targetPosition);
                targetPositionMap.computeIfAbsent(targetDepartment.getPdId(), key -> new ArrayList<>()).add(targetPosition);
            }
        }

        for (Map.Entry<String, String> entry : positionIdMap.entrySet()) {
            List<SysPositionRoleP> roles = sourceRoleMap.get(entry.getKey());
            if (ValidateUtil.isEmpty(roles)) {
                continue;
            }
            for (SysPositionRoleP role : roles) {
                SysPositionRoleP newRole = new SysPositionRoleP();
                newRole.setPpRId(GenerateUUID.getUUID());
                newRole.setPpId(entry.getValue());
                newRole.setRoleId(role.getRoleId());
                newRoles.add(newRole);
            }
        }
    }

    /**
     * 复制部门到项目
     *
     * @param source
     * @param targetPId
     * @param targetParentId
     * @param sort
     * @return
     */
    private SysDepartmentP copyDepartmentP(SysDepartmentP source, String targetPId, String targetParentId, Integer sort) {
        SysDepartmentP department = new SysDepartmentP();
        department.setPdId(GenerateUUID.getUUID());
        department.setpId(targetPId);
        department.setPdName(source.getPdName());
        department.setPdCode(source.getPdCode());
        department.setPdStatus(source.getPdStatus());
        department.setPdParentid(targetParentId);
        department.setPdPath(source.getPdPath());
        department.setPdType(source.getPdType());
        department.setPdMdmNo(source.getPdMdmNo());
        department.setPdRemark(source.getPdRemark());
        department.setPdSort(sort);
        department.setPdSolution(source.getPdSolution());
        department.setPdCreatetime(new Date());
        department.setPdCreateuId(source.getPdCreateuId());
        department.setPdCreateuName(source.getPdCreateuName());
        department.setOwnerUnit(source.getOwnerUnit());
        return department;
    }

    private SysDepartmentP findSameDepartmentP(List<SysDepartmentP> targets, SysDepartmentP source) {
        if (ValidateUtil.isEmpty(source.getPdCode())) {
            return null;
        }
        return targets.stream()
                .filter(target -> source.getPdCode().equals(target.getPdCode()))
                .findFirst()
                .orElse(null);
    }

    private void updateDepartmentP(SysDepartmentP target, SysDepartmentP source) {
        target.setPdName(source.getPdName());
        target.setPdCode(source.getPdCode());
        target.setPdStatus(source.getPdStatus());
        target.setPdPath(source.getPdPath());
        target.setPdType(source.getPdType());
        target.setPdMdmNo(source.getPdMdmNo());
        target.setPdRemark(source.getPdRemark());
        target.setPdSolution(source.getPdSolution());
        target.setPdCreateuId(source.getPdCreateuId());
        target.setPdCreateuName(source.getPdCreateuName());
        target.setOwnerUnit(source.getOwnerUnit());
    }

    /**
     * 复制岗位到项目
     *
     * @param source
     * @param targetPdId
     * @return
     */
    private SysPositionP copyPositionP(SysPositionP source, String targetPdId) {
        SysPositionP position = new SysPositionP();
        position.setPpId(GenerateUUID.getUUID());
        position.setPdId(targetPdId);
        position.setPpName(source.getPpName());
        position.setPpCode(source.getPpCode());
        position.setPpRemark(source.getPpRemark());
        position.setPpSort(source.getPpSort());
        position.setPpStatus(source.getPpStatus());
        position.setPpMdmNo(source.getPpMdmNo());
        position.setPpCreatetime(new Date());
        position.setPpCreateuId(source.getPpCreateuId());
        position.setPpCreateuName(source.getPpCreateuName());
        return position;
    }

    private SysPositionP findSamePositionP(List<SysPositionP> targets, SysPositionP source) {
        if (ValidateUtil.isEmpty(source.getPpCode())) {
            return null;
        }
        return targets.stream()
                .filter(target -> source.getPpCode().equals(target.getPpCode()))
                .findFirst()
                .orElse(null);
    }

    private void updatePositionP(SysPositionP target, SysPositionP source) {
        target.setPpName(source.getPpName());
        target.setPpCode(source.getPpCode());
        target.setPpRemark(source.getPpRemark());
        target.setPpSort(source.getPpSort());
        target.setPpStatus(source.getPpStatus());
        target.setPpMdmNo(source.getPpMdmNo());
        target.setPpCreateuId(source.getPpCreateuId());
        target.setPpCreateuName(source.getPpCreateuName());
    }

    /**
     * 获取下一个部门排序
     *
     * @param nextSortMap
     * @param parentId
     * @return
     */
    private Integer nextDepartmentPSort(Map<String, Integer> nextSortMap, String parentId) {
        Integer sort = nextSortMap.getOrDefault(parentId, 0) + 1;
        nextSortMap.put(parentId, sort);
        return sort;
    }

    /**
     * 根据项目ID和单位IDs获取项目部门编码
     *
     * @param pId
     * @param cIds
     * @return
     */
    @Override
    public BaseResponse getPdCodeByCIds(String pId, String cIds) {
        if (ValidateUtil.isAnObjEmpty(pId, cIds)) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }
        Set<String> cIdList = new HashSet<>(Arrays.asList(cIds.split(",")));
        Set<String> pdCodes = baseMapper.getPdCodeByCIds(pId, cIdList);
        return BaseResponse.ok(pdCodes);
    }

    /**
     * 根据岗位的完整责任层级链复用项目部门，并在最长匹配链后补建缺失部门。
     * 在末级部门内复用同名岗位或创建岗位，新增部门及岗位均按同级末尾排序。
     * 编码采用大写拼音首字母，部门在项目内查重、岗位在所属部门内查重。
     * 新岗位按配置模板中的同名岗位关联角色，并复用或创建用户后建立岗位人员关联。
     *
     * @param request 包含项目ID和岗位ID的安全责任岗位人员信息
     * @return 部门、岗位及人员同步成功结果；参数或层级链异常时返回失败，创建或角色配置异常时回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncSafeResponsibilityPostPerson(SafeResponsibilityPostPersonDTO request) {
        if (request == null || ValidateUtil.isAnObjEmpty(request.getPostId(), request.getPId())) {
            return BaseResponse.fail(BaseMsg.notReqParam);
        }
        SafeResponsibilityPost post = safeResponsibilityPostMapper.selectById(request.getPostId());
        if (post == null) {
            return BaseResponse.fail("安全责任岗位不存在");
        }
        if (ValidateUtil.isEmpty(post.getPostName()) || post.getPostName().trim().isEmpty()) {
            return BaseResponse.fail("安全责任岗位名称不能为空");
        }
        String levelId = post.getLevelId();
        if (ValidateUtil.isEmpty(levelId) || "0".equals(levelId)) {
            return BaseResponse.fail("安全责任岗位未关联有效层级");
        }

        List<SafeResponsibilityLevel> levels = new ArrayList<>();
        Set<String> visitedLevelIds = new HashSet<>();
        while (!ValidateUtil.isEmpty(levelId) && !"0".equals(levelId)) {
            if (!visitedLevelIds.add(levelId)) {
                return BaseResponse.fail("安全责任层级存在循环引用：" + levelId);
            }
            SafeResponsibilityLevel level = safeResponsibilityLevelMapper.selectById(levelId);
            if (level == null) {
                return BaseResponse.fail("安全责任层级不存在：" + levelId);
            }
            if (ValidateUtil.isEmpty(level.getLevelName()) || level.getLevelName().trim().isEmpty()) {
                return BaseResponse.fail("安全责任层级名称不能为空：" + levelId);
            }
            if (!levels.isEmpty() && !Objects.equals(levels.get(0).getSystemId(), level.getSystemId())) {
                return BaseResponse.fail("安全责任上级层级不属于当前责任体系：" + levelId);
            }
            levels.add(level);
            levelId = level.getParentId();
        }
        // 父级先于子级，保留完整实体及父子关联，供后续匹配或生成 DepartmentP 树使用。
        Collections.reverse(levels);

        // 所属项目
        String pId = request.getPId();
        // 复用已有部门结构，补建缺失层级，返回末级部门供后续岗位人员关联使用。
        SysDepartmentP departmentP = ensureResponsibilityDepartmentStructure(pId, levels);
        // 岗位归属当前责任层级对应的末级部门，重复同步时复用该部门下的同名岗位。
        SysPositionP positionP = ensureResponsibilityPosition(departmentP.getPdId(), post.getPostName());

        // 创建人员信息
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getuAccount, request.getPhone())
                        .orderByDesc(SysUser::getuCreatetime)
                , false);
        if (ValidateUtil.isNotNull(sysUser)) {
            // 创建人员关联
            ensureResponsibilityPositionUser(positionP.getPpId(), sysUser.getuId());
        } else {
            SysUser user = new SysUser();
            user.setuId(GenerateUUID.getUUID());
            user.setuName(request.getName());
            user.setuAccount(request.getPhone());
            user.setuMtel(request.getPhone());
            user.setuPhone(request.getPhone());
            user.setcId(request.getUnitId());
            user.setuState("1");
            user.setuCreatetime(new Date());
            String config = sysConfigEntityService.getSysConfigByCode("dndxSafe");
            if (ValidateUtil.isEmpty(config) || config.trim().isEmpty()) {
                throw new JPMException("安全责任岗位配置dndxSafe不存在或为空");
            }
            String userType;
            try {
                userType = JSONUtil.parseObj(config).getStr("userType");
            } catch (RuntimeException exception) {
                throw new JPMException("安全责任岗位角色模板配置格式错误，应为包含userType的JSON对象");
            }
            if (ValidateUtil.isEmpty(userType) || userType.trim().isEmpty()) {
                throw new JPMException("安全责任岗位缺少userType配置");
            }
            user.setuType(userType);
            user.setuSalt(GenerateUUID.getUUID());
            user.setuFirstLogin(1);
            user.setuPassUpdateTime(new Date());
            user.setuSignature(GenerateUUID.getUUID());
            user.setuQualification(GenerateUUID.getUUID());
            user.setuCheckSms(0);
            // 密码
            String newPwd = apiUtilsService.PasswrodEncryptionResult("Dndx@2026", user.getuSalt(), user.getuAccount());
            user.setuPassword(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), newPwd));
            sysUserService.save(user);
            ensureResponsibilityPositionUser(positionP.getPpId(), user.getuId());
        }
        return BaseResponse.ok();
    }

    /**
     * 为项目岗位关联指定用户；同一岗位已关联该用户时直接复用，新增时排在该岗位人员末尾。
     *
     * @param ppId 项目岗位ID
     * @param uId  系统用户ID，不能为空
     * @return 已有或新建的岗位人员关联
     */
    private SysPositionUserP ensureResponsibilityPositionUser(String ppId, String uId) {
        if (ValidateUtil.isAnObjEmpty(ppId, uId)) {
            throw new JPMException("关联岗位人员时岗位ID和用户ID不能为空");
        }
        List<SysPositionUserP> relations = sysPositionUserPMapper.selectList(new LambdaQueryWrapper<SysPositionUserP>()
                .eq(SysPositionUserP::getPpId, ppId));
        for (SysPositionUserP relation : relations) {
            if (Objects.equals(relation.getuId(), uId)) {
                return relation;
            }
        }
        int nextSort = relations.stream()
                .map(SysPositionUserP::getPupSort)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
        SysPositionUserP relation = new SysPositionUserP();
        relation.setPupId(GenerateUUID.getUUID());
        relation.setPpId(ppId);
        relation.setuId(uId);
        relation.setPupSort(nextSort);
        if (sysPositionUserPMapper.insert(relation) != 1) {
            throw new JPMException("创建安全责任岗位人员关联失败");
        }
        return relation;
    }

    /**
     * 在指定部门内复用同名岗位，不存在时创建并追加到该部门的岗位末尾。
     * 新岗位编码按大写拼音首字母生成，在部门内忽略大小写查重并追加01、02等序号。
     * 仅新建岗位时复制配置模板内同名岗位的角色，复用岗位时保留现有角色关联。
     *
     * @param pdId     当前责任层级对应的项目部门ID
     * @param postName 安全责任岗位名称，不能为空
     * @return 复用或新建的项目岗位，供后续关联岗位人员使用
     */
    private SysPositionP ensureResponsibilityPosition(String pdId, String postName) {
        List<SysPositionP> positions = sysPositionPMapper.selectList(new LambdaQueryWrapper<SysPositionP>()
                .eq(SysPositionP::getPdId, pdId)
                .orderByAsc(SysPositionP::getPpSort)
                .orderByAsc(SysPositionP::getPpId));
        for (SysPositionP position : positions) {
            if (Objects.equals(position.getPpName(), postName)) {
                return position;
            }
        }
        Set<String> usedCodes = positions.stream()
                .map(SysPositionP::getPpCode)
                .filter(code -> !ValidateUtil.isEmpty(code))
                .map(code -> code.toUpperCase(Locale.ROOT))
                .collect(Collectors.toSet());
        int nextSort = positions.stream()
                .map(SysPositionP::getPpSort)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
        SysPositionP position = new SysPositionP();
        position.setPpId(GenerateUUID.getUUID());
        position.setPdId(pdId);
        position.setPpName(postName);
        position.setPpCode(nextResponsibilityCode(postName, usedCodes, "GW"));
        position.setPpSort(nextSort);
        position.setPpStatus("1");
        position.setPpCreatetime(new Date());
        if (sysPositionPMapper.insert(position) != 1) {
            throw new JPMException("创建安全责任项目岗位失败：" + postName);
        }
        createResponsibilityPositionTemplateRoles(position);
        return position;
    }

    /**
     * 从配置的toId查询模板下全部岗位，忽略部门层级，按名称精确匹配并复制去重后的角色关联。
     * 配置编码暂空时跳过；编码确定后，配置缺失、格式错误或toId为空时抛出异常回滚创建。
     * 模板没有同名岗位或同名岗位没有角色时不建立角色关联。
     *
     * @param position 本次新建的项目岗位，不可传入已有岗位以免覆盖其角色
     */
    private void createResponsibilityPositionTemplateRoles(SysPositionP position) {
        String config = sysConfigEntityService.getSysConfigByCode("dndxSafe");
        if (ValidateUtil.isEmpty(config) || config.trim().isEmpty()) {
            throw new JPMException("安全责任岗位配置dndxSafe不存在或为空");
        }
        String toId;
        try {
            toId = JSONUtil.parseObj(config).getStr("toId");
        } catch (RuntimeException exception) {
            throw new JPMException("安全责任岗位角色模板配置格式错误，应为包含toId的JSON对象");
        }
        if (ValidateUtil.isEmpty(toId) || toId.trim().isEmpty()) {
            throw new JPMException("安全责任岗位角色模板配置缺少toId");
        }
        // 不传部门及名称条件、不分页，限定toId获取模板全部岗位后精确匹配名称。
        List<SysTPositionVo> templatePositions = sysTPositionMapper.GetT_PositionList(null, null, null, null, toId.trim());
        Set<String> templatePositionIds = templatePositions.stream()
                .filter(template -> Objects.equals(position.getPpName(), template.getTpName()))
                .map(SysTPositionVo::getTpId)
                .collect(Collectors.toSet());
        if (templatePositionIds.isEmpty()) {
            return;
        }
        List<SysTPositionRole> templateRoles = sysTPositionRoleMapper.selectList(new LambdaQueryWrapper<SysTPositionRole>()
                .in(SysTPositionRole::getTpId, templatePositionIds));
        String roleIds = templateRoles.stream()
                .map(SysTPositionRole::getRoleId)
                .filter(roleId -> !ValidateUtil.isEmpty(roleId) && !roleId.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
        if (!roleIds.isEmpty()) {
            sysPositionPRoleService.CreatePositionPRole(position.getPpId(), roleIds);
        }
    }

    /**
     * 从根部门逐级匹配名称和父子关系，复用最长已有前缀，并补建剩余层级。
     * 多条分支匹配深度相同时，按部门排序号及主键顺序选择第一条；完整匹配时不新增。
     * 新部门的编码在项目内唯一，排序号为同级最大值加一，根部门的父ID为0。
     *
     * @param pId    项目ID，不能为空
     * @param levels 按根层级到当前层级排列的非空责任层级链
     * @return 对应当前责任层级的已有或新建末级部门
     */
    private SysDepartmentP ensureResponsibilityDepartmentStructure(String pId, List<SafeResponsibilityLevel> levels) {
        List<SysDepartmentP> departments = this.list(new LambdaQueryWrapper<SysDepartmentP>()
                .eq(SysDepartmentP::getpId, pId)
                .orderByAsc(SysDepartmentP::getPdSort)
                .orderByAsc(SysDepartmentP::getPdId));
        Map<String, Integer> nextSortMap = new HashMap<>();
        Set<String> usedCodes = new HashSet<>();
        for (SysDepartmentP department : departments) {
            String parentId = ValidateUtil.isEmpty(department.getPdParentid()) ? "0" : department.getPdParentid();
            if (department.getPdSort() != null) {
                nextSortMap.merge(parentId, department.getPdSort(), Math::max);
            }
            if (!ValidateUtil.isEmpty(department.getPdCode())) {
                usedCodes.add(department.getPdCode().toUpperCase(Locale.ROOT));
            }
        }

        Set<String> parentIds = new HashSet<>(Collections.singletonList("0"));
        SysDepartmentP lastMatched = null;
        int matchedDepth = 0;
        for (SafeResponsibilityLevel level : levels) {
            List<SysDepartmentP> matches = departments.stream()
                    .filter(department -> parentIds.contains(ValidateUtil.isEmpty(department.getPdParentid())
                            ? "0" : department.getPdParentid()))
                    .filter(department -> Objects.equals(department.getPdName(), level.getLevelName()))
                    .collect(Collectors.toList());
            if (matches.isEmpty()) {
                break;
            }
            lastMatched = matches.get(0);
            matchedDepth++;
            parentIds.clear();
            matches.forEach(department -> parentIds.add(department.getPdId()));
        }

        String parentId = lastMatched == null ? "0" : lastMatched.getPdId();
        for (int index = matchedDepth; index < levels.size(); index++) {
            SysDepartmentP department = new SysDepartmentP();
            department.setPdId(GenerateUUID.getUUID());
            department.setpId(pId);
            department.setPdName(levels.get(index).getLevelName());
            department.setPdCode(nextResponsibilityCode(department.getPdName(), usedCodes, "BM"));
            department.setPdParentid(parentId);
            department.setPdStatus("1");
            department.setPdType("1");
            department.setPdSort(nextDepartmentPSort(nextSortMap, parentId));
            department.setPdCreatetime(new Date());
            if (baseMapper.insert(department) != 1) {
                throw new JPMException("创建安全责任项目部门失败：" + department.getPdName());
            }
            lastMatched = department;
            parentId = department.getPdId();
        }
        return lastMatched;
    }

    /**
     * 生成部门或岗位的大写拼音首字母编码，并将新编码加入本次同步的编码集合。
     * 汉字取首个读音的首字母，英文字母和数字保留，其他字符忽略；无可用字符时使用指定默认编码。
     * 重复编码依次追加01、02等至少两位序号，比较时忽略大小写。
     *
     * @param name         部门或岗位名称
     * @param usedCodes    查重范围内已使用的大写编码集合，包含本次新增编码
     * @param fallbackCode 无可用拼音或字母数字时的默认编码，部门为BM、岗位为GW
     * @return 查重范围内尚未使用的编码
     */
    private String nextResponsibilityCode(String name, Set<String> usedCodes, String fallbackCode) {
        StringBuilder initials = new StringBuilder();
        for (char character : name.toCharArray()) {
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(character);
            if (pinyin != null && pinyin.length > 0) {
                initials.append(pinyin[0].charAt(0));
            } else if ((character >= 'a' && character <= 'z')
                    || (character >= 'A' && character <= 'Z')
                    || (character >= '0' && character <= '9')) {
                initials.append(character);
            }
        }
        String baseCode = initials.length() == 0 ? fallbackCode : initials.toString().toUpperCase(Locale.ROOT);
        String code = baseCode;
        int suffix = 1;
        while (!usedCodes.add(code)) {
            code = baseCode + String.format(Locale.ROOT, "%02d", suffix++);
        }
        return code;
    }
}
