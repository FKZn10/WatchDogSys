package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.enums.DepartmentAuthEnum;
import com.jpmsoft.userProducer.mapper.SysDepartmentMapper;
import com.jpmsoft.userProducer.pojo.dto.DepartmentDto;
import com.jpmsoft.userProducer.pojo.dto.DepartmentSelectConfDTO;
import com.jpmsoft.userProducer.pojo.dto.SysDepartmentDto;
import com.jpmsoft.userProducer.pojo.entity.SysDepartment;
import com.jpmsoft.userProducer.pojo.vo.*;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDepartmentImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysPositionService sysPositionService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    public BaseResponse getDepartmentTreeList(DepartmentDto request) {
        List<VueTreeVo> vueTrees = new ArrayList<>();
        QueryMap<DepartmentDto> params = new QueryMap<>(request);
        params.put("dStatus").put("dCode").put("dType");
        if (request.getHideDep()) {
            DepartmentSelectConfDTO conf = sysConfigEntityService.getDepartmentSelectConf();
            List<String> hideCodes = null;
            if (ValidateUtil.isNotNull(conf) && ValidateUtil.isNotEmpty(conf.getHideDep()))
                hideCodes = Arrays.asList(conf.getHideDep().split(","));
            params.put("hideCodes", hideCodes);
        }
        List<SysDepartmentDto> list = baseMapper.getDepartmentTreeList(params.getQuery());
        if (ValidateUtil.isNotEmpty(request.getdCompanyId())) {
            List<SysDepartmentDto> cDeps = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdCompanyId()) && n.getdCompanyId().equals(request.getdCompanyId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cDeps)) {
                SysDepartmentDto cDep = cDeps.stream().findFirst().get();
                getTreeByCpy(vueTrees, cDep, list);
            } else return BaseResponse.fail(BaseMsg.unknownCpyDep);
        } else GetTreeChildren(vueTrees, "0", list);
        return BaseResponse.ok(vueTrees);
    }

    @Override
    public BaseResponse getBjDepartmentTreeList(DepartmentDto request) {
        List<VueTreeVo> vueTrees = new ArrayList<>();
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getdStatus())) wrapper.eq("D_Status", request.getdStatus());
        if (!ValidateUtil.isNull(request.getdHierarchy())) wrapper.eq("D_Hierarchy", request.getdHierarchy());
        if (!ValidateUtil.isNull(request.getdCode())) wrapper.eq("D_Code", request.getdCode());
        if (!ValidateUtil.isNull(request.getdType())) wrapper.eq("D_Type", request.getdType());
        if (ValidateUtil.isNotEmpty(request.getTeId())) wrapper.eq("Te_Id", request.getTeId());
        wrapper.orderByAsc("D_Sort");
        List<SysDepartment> list = baseMapper.selectList(wrapper);
        List<VueTreeVo> childTree = new ArrayList<>();
        for (SysDepartment pojo : list) {
            VueTreeVo vueTree = new VueTreeVo();
            vueTree.setKey(pojo.getdId());
            vueTree.setTitle(pojo.getdName());
            vueTree.setValue(pojo.getdId());
            vueTree.setPid(pojo.getdParentid());
            vueTree.setdCode(pojo.getdCode());
            vueTree.setdMdmNo(pojo.getdMdmNo());
            vueTree.setdRemark(pojo.getdRemark());
            vueTree.setdPath(pojo.getdPath());
            vueTree.setdSolution(pojo.getdSolution());
            vueTree.setdSort(pojo.getdSort());
            vueTree.setdStatus(pojo.getdStatus());
            vueTree.setdType(pojo.getdType());
            vueTree.setdProjectAuth(pojo.getdProjectAuth());
            vueTree.setdCompanyId(pojo.getdCompanyId());
            vueTree.setChildren(childTree);
            vueTrees.add(vueTree);
        }
        return BaseResponse.ok(vueTrees);
    }

    @Override
    public BaseResponse CUDDepartment(DepartmentDto request) {
        String type = request.getType().toUpperCase(Locale.ENGLISH);
        if (type.equals("CREATE") || type.equals("CREATE2") || type.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            wrapper.eq("d_parentid", request.getdParentid());
            if (type.equals("UPDATE")) wrapper.ne("D_ID", request.getdId());
            wrapper.and(wrp -> {
                wrp.eq("D_Code", request.getdCode()).or().eq("D_Name", request.getdName());
                if (request.getdType().equals("1")) wrp.or().eq("D_Company_Id", request.getdCompanyId());
            });
            if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(BaseMsg.infoIsExist);
        }

        if (type.equals("CREATE") || type.equals("CREATE2")) {
            SysDepartment pojo = new SysDepartment();
            pojo.setdId(request.getdId());
            pojo.setdCode(request.getdCode());
            pojo.setdType(request.dType);
            pojo.setdStatus(request.getdStatus());
            pojo.setdSort(ValidateUtil.isEmpty(request.getdSort()) ? null : Integer.parseInt(request.getdSort()));
            pojo.setdSolution(request.dSolution);
            pojo.setdRemark(request.dRemark);
            pojo.setdPath(request.dPath);
            pojo.setdParentid(request.getdParentid());
            pojo.setdName(request.getdName());
            pojo.setdMdmNo(request.getdMdmNo());
            pojo.setdCreateuName(request.getdCreateuName());
            pojo.setdCreateuId(request.getdCreateuId());
            pojo.setdCreatetime(new Date());
            pojo.setdProjectAuth(request.getdProjectAuth());
            pojo.setdHierarchy(request.getdHierarchy());
            pojo.setdCompanyId(request.getdCompanyId());
            pojo.setTeId(request.getTeId());
            baseMapper.insert(pojo);
            if (type.equals("CREATE")) return BaseResponse.ok("同级字典新增成功");
            else return BaseResponse.ok("下级字典新增成功");

        } else if (type.equals("UPDATE")) {
            SysDepartment pojo = baseMapper.selectById(request.getdId());
            if (pojo != null) {
                if (pojo.getdId().equals(request.getdParentid())) return BaseResponse.ok("上级节点选择错误!");
                pojo.setdId(request.getdId());
                pojo.setdCode(request.getdCode());
                pojo.setdType(request.dType);
                pojo.setdStatus(request.getdStatus());
                pojo.setdSort(ValidateUtil.isEmpty(request.getdSort()) ? null : Integer.parseInt(request.getdSort()));
                pojo.setdSolution(request.dSolution);
                pojo.setdRemark(request.dRemark);
                pojo.setdPath(request.dPath);
                pojo.setdParentid(request.getdParentid());
                pojo.setdName(request.getdName());
                pojo.setdMdmNo(request.getdMdmNo());
                pojo.setdHierarchy(request.getdHierarchy());
                pojo.setdProjectAuth(request.getdProjectAuth());
                pojo.setdCompanyId(request.getdCompanyId());
                pojo.setTeId(request.getTeId());
                baseMapper.updateById(pojo);
                return BaseResponse.ok();
            }
        } else if (type.equals("DELETE")) {
            SysDepartment pojo = baseMapper.selectById(request.getdId());
            if (pojo != null) {
                Integer chilCount = getSysDepartmentCount(pojo.getdId());
                if (pojo.getdParentid().equals("0")) {
                    Integer rootCount = getRootDepartmentCount(pojo.getdId());
                    if (rootCount == 0) return BaseResponse.fail(BaseMsg.onlyRootNode);
                    else if (chilCount > 0) return BaseResponse.fail(BaseMsg.delSubFirst);
                    else {
                        deleteDepById(request.getdId());
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        deleteDepById(request.getdId());
                        return BaseResponse.ok();
                    }
                }
            }
        } else return BaseResponse.fail(BaseMsg.noOperationTypeSelected);
        return BaseResponse.fail();
    }

    private void deleteDepById(String dId) {
        if (sysPositionService.departmentHavPosition(dId)) throw new JPMException(BaseMsg.delPositionFirst);
        baseMapper.deleteById(dId);
    }

    @Override
    public BaseResponse OnDropDepartment(DepartmentDto request) {
        if (ValidateUtil.isEmpty(request.getdId()) || ValidateUtil.isEmpty(request.getNewdId()) || ValidateUtil.isEmpty(request.getdSort()) || ValidateUtil.isEmpty(request.getNewdSort()))
            return new BaseResponse(BaseMsg.notReqParam);

        SysDepartment entity = baseMapper.selectById(request.getdId());
        SysDepartment entity1 = baseMapper.selectById(request.getNewdId());
        if (entity != null && entity1 != null) {
            entity.setdSort(Integer.parseInt(request.getNewdSort()));
            entity1.setdSort(Integer.parseInt(request.getdSort()));
            baseMapper.updateById(entity);
            baseMapper.updateById(entity1);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<SysDepartment> getDepartmentList(String teId) {
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        List<SysDepartment> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<String> getUserDepartment(LogUserInfoVo user) {

        List<String> dIds = baseMapper.getUserDepartment(user.getId());
        user.setDepartmentIds(dIds);
        user.setDutyDepartmentIds(baseMapper.getUserDutyDepartment(user.getId()));
        //region  城建需求 根据配置区别部门用户 具有所有权限的部门
//        Object cjAllRoleDepConfig = redisUtils.get("Config_cjAllRoleDep");
//        if (ValidateUtil.isNotNull(cjAllRoleDepConfig)) {//所有权限
//            List<String> allRoleDeps = Arrays.asList(cjAllRoleDepConfig.toString().split(","));
//            for (String dId : dIds) {
//                if (allRoleDeps.contains(dId))
//                    return null;
//            }
//        }
        //endregion
        List<SysDepartment> alls = getDepartmentList(user.isEnableTenant() ? user.getTeId() : null);
        HashSet<String> data = new HashSet<>();
        if (dIds != null && dIds.size() > 0) {
            for (String dId : dIds) {
                data.add(dId);
                List<SysDepartment> currentD = alls.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
                if (currentD != null && currentD.size() > 0) {
                    SysDepartment model = currentD.stream().findFirst().get();
                    //当前部门仅看项目组织机构
                    if (ValidateUtil.isEmpty(model.getdProjectAuth()) || model.getdProjectAuth().equals(DepartmentAuthEnum.Project_Department.getkey())
                            || model.getdProjectAuth().equals(DepartmentAuthEnum.Department_Duty.getkey())) {//组机构鉴权或者负责人
                        data.remove(dId);
                        continue;
                    }
                    if (!ValidateUtil.isEmpty(model.getdProjectAuth()) && model.getdProjectAuth().equals(DepartmentAuthEnum.ALL_PROJECT.getkey()))
                        return null;

                    //所有下级
                    List<SysDepartment> list = alls.stream().filter(n -> n.getdParentid().equals(dId)).collect(Collectors.toList());
                    if (list != null && list.size() > 0) {
                        if (!ValidateUtil.isEmpty(model.getdProjectAuth()) && model.getdProjectAuth().equals(DepartmentAuthEnum.Main_Department.getkey())) {
                            getUserDepartmentChildIds(list, data, alls);
                        }
                    }

                    //region 开启单位部门按主管单位授权，穿透平级
                    if (sysConfigEntityService.enableCompanyDepPenetrate()) {
                        //授权模式为按主管部门授权
                        if (ValidateUtil.isNotEmpty(model.getdProjectAuth()) && model.getdProjectAuth().equals(DepartmentAuthEnum.Main_Department.getkey())) {
                            //当前为单位部门或当前为单位部门的下级部门
                            if (companyDepChild(alls, model)) {
                                //获取所在部门的所有平级部门以及平级部门的下级部门
                                List<SysDepartment> pjDeps = alls.stream().filter(n -> n.getdParentid().equals(model.getdParentid())).collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(pjDeps)) {
                                    for (SysDepartment pj : pjDeps) {
                                        data.add(pj.getdId());
                                        //平级的下级部门
                                        List<SysDepartment> pjChilds = alls.stream().filter(n -> n.getdParentid().equals(pj.getdId())).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(pjChilds))
                                            getUserDepartmentChildIds(pjChilds, data, alls);
                                    }
                                }
                            }
                        }
                    }
                    //endregion

                    //所有上级
                    if (!model.getdParentid().equals("0")) {
                        String excludeDep = sysConfigEntityService.getSysConfigByCode("excludeSuperiorDepRole");
                        List<SysDepartment> pList = alls.stream().filter(n -> n.getdId().equals(model.getdParentid())).collect(Collectors.toList());
                        if (pList != null && pList.size() > 0) {
                            if (!ValidateUtil.isEmpty(model.getdProjectAuth()) && model.getdProjectAuth().equals(DepartmentAuthEnum.Main_Department.getkey())) {
                                //开启配置且当前部门非单位部门
                                if (ValidateUtil.isEmpty(excludeDep) || !excludeDep.equals("1")) {   //未开启配置
                                    getUserDepartmentParentIds(pList, data, alls, excludeDep);
                                } else if (ValidateUtil.isNotEmpty(model.getdType()) && !model.getdType().equals("1")) {   //开启配置当前部门非单位部门
                                    getUserDepartmentParentIds(pList, data, alls, excludeDep);
                                }
                            }
                        }
                    }

                }
            }
        }
        //region 城建需求 根据配置区别部门用户 可以看同级的部门
//        Object cjPeerRoleDepConfig = redisUtils.get("Config_cjPeerRoleDep");
//        if (ValidateUtil.isNotNull(cjPeerRoleDepConfig)) {
//            //追加同级部门
//            List<String> peerRoleDeps = Arrays.asList(cjPeerRoleDepConfig.toString().split(","));
//            for (String dId : dIds) {
//                if (peerRoleDeps.contains(dId)) {
//                    SysDepartment dep = alls.stream().filter(n -> n.getdId().equals(dId)).findFirst().get();
//                    List<String> peerDepIds = alls.stream().filter(n -> n.getdParentid().equals(dep.getdParentid())).map(SysDepartment::getdId).collect(Collectors.toList());
//                    if (ValidateUtil.isNotEmpty(peerDepIds))
//                        data.addAll(peerDepIds);
//                }
//            }
//        }
        //endregion
        if (data != null && data.size() > 0) return new ArrayList<>(data);
        else return new ArrayList<>();
    }

    private Boolean companyDepChild(List<SysDepartment> alls, SysDepartment current) {
        //当前部门即为单位部门
        if (ValidateUtil.isNotEmpty(current.getdType()) && current.getdType().equals("1")) return true;
        //上级已经是顶层
        if (ValidateUtil.isNotEmpty(current.getdParentid()) && current.getdParentid().equals("0")) return false;
        List<SysDepartment> parents = alls.stream().filter(f -> f.getdId().equals(current.getdParentid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(parents))
            return companyDepChild(alls, parents.stream().findFirst().get());
        else return false;
    }

    @Override
    public BaseResponse getUserDepartmentSelect(String uId) {
        List<Object> data = getUserDepartmentSelectByUid(uId);
        return BaseResponse.ok(data);
    }

    @Override
    public List<Object> getUserDepartmentSelectByUid(String uId) {
        List<String> dIds = baseMapper.getUserDepartment(uId);
        if (dIds == null || dIds.size() < 1) return new ArrayList<>();
        List<SysDepartment> alls = getDepartmentList(sysUserService.getTeIdByUserId(uId));
        List<Object> data = new ArrayList<>();
        dIds.forEach(dId -> {
            List<SysDepartment> currentD = alls.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(currentD)) {
                Map<String, String> map = new HashMap<>();
                SysDepartment model = currentD.stream().findFirst().get();
                map.put("id", model.getdId());
                map.put("name", model.getdName());
                if (!data.contains(map)) data.add(map);
            }
        });
        return data;
    }

    @Override
    public List<String> getAllChildDepartment(String uId) {
        List<String> dIds = baseMapper.getUserDepartment(uId);
        //region  城建需求 根据配置区别部门用户 具有所有权限的部门
        Object cjAllRoleDepConfig = redisUtils.get("Config_cjAllRoleDep");
        if (ValidateUtil.isNotNull(cjAllRoleDepConfig)) {//所有权限
            List<String> allRoleDeps = Arrays.asList(cjAllRoleDepConfig.toString().split(","));
            for (String dId : dIds) {
                if (allRoleDeps.contains(dId)) return new ArrayList<>();
            }
        }
        //endregion
        List<SysDepartment> alls = getDepartmentList(redisUtils.getTeIdByUid(uId));
        if (dIds != null && dIds.size() > 0) {
            HashSet<String> data = new HashSet<>();
            for (String dId : dIds) {
                data.add(dId);
                List<SysDepartment> currentD = alls.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
                if (currentD != null && currentD.size() > 0) {
                    SysDepartment model = currentD.stream().findFirst().get();
                    //所有下级
                    List<SysDepartment> list = alls.stream().filter(n -> n.getdParentid().equals(dId)).collect(Collectors.toList());
                    if (list != null && list.size() > 0) {
                        getUserDepartmentChildIds(list, data, alls);
                    }
                }
            }

            //region 城建需求 根据配置区别部门用户 可以看同级的部门
            Object cjPeerRoleDepConfig = redisUtils.get("Config_cjPeerRoleDep");
            if (ValidateUtil.isNotNull(cjPeerRoleDepConfig)) {
                //追加同级部门
                List<String> peerRoleDeps = Arrays.asList(cjPeerRoleDepConfig.toString().split(","));
                for (String dId : dIds) {
                    if (peerRoleDeps.contains(dId)) {
                        SysDepartment dep = alls.stream().filter(n -> n.getdId().equals(dId)).findFirst().get();
                        List<String> peerDepIds = alls.stream().filter(n -> n.getdParentid().equals(dep.getdParentid())).map(SysDepartment::getdId).collect(Collectors.toList());
                        if (ValidateUtil.isNotEmpty(peerDepIds)) data.addAll(peerDepIds);
                    }
                }
            }
            //endregion
            return new ArrayList<String>(data);
        }
        return null;
    }

    @Override
    public List<String> getAllChildDepCompany(String uId) {
        List<String> allDepIds = getAllChildDepartment(uId);
        if (CollectionUtils.isEmpty(allDepIds)) return null;
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        wrapper.in("D_ID", allDepIds);
        //单位部门
        wrapper.eq("D_Type", "1");
        wrapper.eq("D_Status", "1");
        wrapper.isNotNull("D_Company_ID");
        List<SysDepartment> deps = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(deps)) return null;
        return deps.stream().map(SysDepartment::getdCompanyId).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllChildDepartmentByCid(String cId) {
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("D_Status", "1");
        List<SysDepartment> list = baseMapper.selectList(wrapper);
        Set<String> datas = new HashSet<>();
        List<SysDepartment> cDeps = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdCompanyId()) && n.getdCompanyId().equals(cId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(cDeps)) {
            for (SysDepartment cDep : cDeps) {
                datas.add(cDep.getdId());
                getTreeChildrenId(datas, cDep.getdId(), list);
            }
        }
        return new ArrayList<>(datas);
    }

    @Override
    public BaseResponse getUserDepartmentByUid(String uId) {
        List<SysDepartmentDto> deps = baseMapper.getUserDepartmentByUid(uId);
        if (ValidateUtil.isNotEmpty(deps)) {
            List<SysUserDepartmentVo> datas = deps.stream().map(dep -> {
                SysUserDepartmentVo ud = new SysUserDepartmentVo();
                BeanUtils.copyProperties(dep, ud);
                return ud;
            }).collect(Collectors.toList());
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok(deps);
    }

    @Override
    public BaseResponse getUserDepIdsByUid(String uId) {
        List<String> depIds = baseMapper.getUserDepIdsByUid(uId);
        return BaseResponse.ok(depIds);
    }

    private void getUserDepartmentParentIds(List<SysDepartment> list, HashSet<String> data, List<SysDepartment> alls, String excludeDep) {
        for (SysDepartment d : list) {
            data.add(d.getdId());
            if (!d.getdParentid().equals("0")) {
                List<SysDepartment> pList = alls.stream().filter(n -> n.getdId().equals(d.getdParentid())).collect(Collectors.toList());
                if (list != null && list.size() > 0) {
                    //开启配置且当前部门非单位部门
                    if (ValidateUtil.isEmpty(excludeDep) || !excludeDep.equals("1")) {   //未开启配置
                        getUserDepartmentParentIds(pList, data, alls, excludeDep);
                    } else if (ValidateUtil.isNotEmpty(d.getdType()) && !d.getdType().equals("1")) {   //开启配置当前部门非单位部门
                        getUserDepartmentParentIds(pList, data, alls, excludeDep);
                    }
                }
            }
        }
    }

    private void getUserDepartmentChildIds(List<SysDepartment> list, HashSet<String> data, List<SysDepartment> alls) {
        for (SysDepartment d : list) {
            data.add(d.getdId());
            List<SysDepartment> childs = alls.stream().filter(n -> n.getdParentid().equals(d.getdId())).collect(Collectors.toList());
            if (list != null && list.size() > 0) {
                getUserDepartmentChildIds(childs, data, alls);
            }
        }
    }

    public void getTreeByCpy(List<VueTreeVo> trees, SysDepartmentDto cDep, List<SysDepartmentDto> list) {
        VueTreeVo vueTree = new VueTreeVo();
        vueTree.setKey(cDep.getdId());
        vueTree.setTitle(cDep.getdName());
        vueTree.setValue(cDep.getdId());
        vueTree.setPid(cDep.getdParentid());
        vueTree.setdCode(cDep.getdCode());
        vueTree.setdMdmNo(cDep.getdMdmNo());
        vueTree.setdRemark(cDep.getdRemark());
        vueTree.setdPath(cDep.getdPath());
        vueTree.setdSolution(cDep.getdSolution());
        vueTree.setdSort(cDep.getdSort());
        vueTree.setdStatus(cDep.getdStatus());
        vueTree.setdType(cDep.getdType());
        vueTree.setdProjectAuth(cDep.getdProjectAuth());
        vueTree.setdCompanyId(cDep.getdCompanyId());
        vueTree.setdCompanyName(cDep.getdCompanyName());
        List<VueTreeVo> newTrees = new ArrayList<>();
        GetTreeChildren(newTrees, cDep.getdId(), list);
        vueTree.setChildren(newTrees);
        trees.add(vueTree);
    }

    public void GetTreeChildren(List<VueTreeVo> trees, String parentId, List<SysDepartmentDto> list) {
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdParentid().equals(parentId)) {
                VueTreeVo vueTree = new VueTreeVo();
                vueTree.setKey(pojo.getdId());
                vueTree.setTitle(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                vueTree.setPid(pojo.getdParentid());
                vueTree.setdCode(pojo.getdCode());
                vueTree.setdMdmNo(pojo.getdMdmNo());
                vueTree.setdRemark(pojo.getdRemark());
                vueTree.setdPath(pojo.getdPath());
                vueTree.setdSolution(pojo.getdSolution());
                vueTree.setdSort(pojo.getdSort());
                vueTree.setdStatus(pojo.getdStatus());
                vueTree.setdType(pojo.getdType());
                vueTree.setdProjectAuth(pojo.getdProjectAuth());
                vueTree.setdCompanyId(pojo.getdCompanyId());
                vueTree.setdCompanyName(pojo.getdCompanyName());
                List<VueTreeVo> newTrees = new ArrayList<>();
                GetTreeChildren(newTrees, pojo.getdId(), list);
                vueTree.setChildren(newTrees);
                trees.add(vueTree);
            }
        }
    }

    public void getTreeChildrenId(Set<String> datas, String parentId, List<SysDepartment> list) {
        for (SysDepartment pojo : list) {
            if (pojo.getdParentid().equals(parentId)) {
                datas.add(pojo.getdId());
                getTreeChildrenId(datas, pojo.getdId(), list);
            }
        }
    }

    private Integer getSysDepartmentCount(String parentId) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("D_ParentID", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    private Integer getRootDepartmentCount(String dId) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("D_ParentID", '0');
        queryWrapper.ne("D_ID", dId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public BaseResponse getDepartmentCidByPid(String pId) {
        String dId = preProjectService.getDidByPid(pId);
        return BaseResponse.ok(dId);
    }

    @Override
    public BaseResponse getLowCodeDepartmentTreeList(DepartmentDto request) {
        List<LowCodeTreeVo> vueTrees = getDepartmentTree(request);
        return BaseResponse.ok(vueTrees);
    }

    @Override
    public BaseResponse getLowCodeDepartmentTreeListByLevel(DepartmentDto request) {
        List<LowCodeTreeVo> vueTrees = new ArrayList<>();
        QueryMap<DepartmentDto> params = new QueryMap<>(request);
        params.put("dStatus");
        List<SysDepartmentDto> list = baseMapper.getDepartmentTreeList(params.getQuery());
        SysDepartmentDto dto = baseMapper.getRootDepartment();
        int level = 1;
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdId().equals(dto.getdId())) {
                LowCodeTreeVo vueTree = new LowCodeTreeVo();
                vueTree.setLabel(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                List<LowCodeTreeVo> newTrees = new ArrayList<>();
                GetLowCodeTreeChildrenLevel(newTrees, pojo.getdId(), list, request, level);
                vueTree.setChildren(newTrees);
                vueTrees.add(vueTree);
            }
        }
        return BaseResponse.ok(vueTrees);
    }

    @Override
    public BaseResponse getLowCodeDepartmentList(DepartmentDto request) {
        List<LowCodeDepartmentVo> vueTrees = new ArrayList<>();
        QueryMap<DepartmentDto> params = new QueryMap<>(request);
        params.put("dStatus");
        List<SysDepartmentDto> list = baseMapper.getDepartmentTreeList(params.getQuery());
        SysDepartmentDto dto = baseMapper.getDepartmentByCode(request.getdCode());
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdParentid().equals(dto.getdId())) {
                LowCodeDepartmentVo vueTree = new LowCodeDepartmentVo();
                vueTree.setLabel(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                vueTrees.add(vueTree);
            }
        }
        return BaseResponse.ok(vueTrees);
    }

    @Override
    public BaseResponse getUserCompanyDep(List<String> departmentIds, String token) {
        if (CollectionUtils.isEmpty(departmentIds)) return null;
        List<SysDepartment> deps = getDepartmentList(redisUtils.getTeId(token));
        List<String> compayDids = new ArrayList<>();
        for (String departmentId : departmentIds) {
            List<SysDepartment> dep = deps.stream().filter(f -> f.getdId().equals(departmentId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dep)) {
                if (dep.stream().findFirst().get().getdType().equals("1"))
                    compayDids.add(dep.stream().findFirst().get().getdId());
                else
                    setCompanyDids(compayDids, deps, dep.stream().findFirst().get().getdParentid());
            }
        }
        return BaseResponse.ok(compayDids);
    }

    private void setCompanyDids(List<String> compayDids, List<SysDepartment> deps, String parentId) {
        if (ValidateUtil.isNotEmpty(parentId) && !parentId.equals("0")) {
            List<SysDepartment> dep = deps.stream().filter(f -> f.getdId().equals(parentId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dep)) {
                if (dep.stream().findFirst().get().getdType().equals("1"))
                    compayDids.add(dep.stream().findFirst().get().getdId());
                else
                    setCompanyDids(compayDids, deps, dep.stream().findFirst().get().getdParentid());
            }
        }
    }

    public void GetLowCodeTreeChildrenLevel(List<LowCodeTreeVo> trees, String parentId, List<SysDepartmentDto> list, DepartmentDto request, int level) {
        level++;
        int currentLevel = level;
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdParentid().equals(parentId)) {
                LowCodeTreeVo vueTree = new LowCodeTreeVo();
                vueTree.setLabel(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                if (currentLevel <= request.getLevel() && "XMGLDW".equals(pojo.getdCode())) {
                    List<LowCodeTreeVo> newTrees = new ArrayList<>();
                    GetLowCodeTreeChildrenLevel(newTrees, pojo.getdId(), list, request, level);
                    vueTree.setChildren(newTrees);
                }
                trees.add(vueTree);
            }
        }
    }

    public List<LowCodeTreeVo> getDepartmentTree(DepartmentDto request) {
        List<LowCodeTreeVo> vueTrees = new ArrayList<>();
        QueryMap<DepartmentDto> params = new QueryMap<>(request);
        params.put("dStatus").put("dCode").put("dType");
        List<SysDepartmentDto> list = baseMapper.getDepartmentTreeList(params.getQuery());
        if (ValidateUtil.isNotEmpty(request.getdId())) {
            GetLowCodeTree(vueTrees, request.getdId(), list, request);
        } else {
            SysDepartmentDto dto = baseMapper.getRootDepartment();
            GetLowCodeTree(vueTrees, dto.getdId(), list, request);
        }
        return vueTrees;
    }

    public void GetLowCodeTree(List<LowCodeTreeVo> trees, String parentId, List<SysDepartmentDto> list, DepartmentDto request) {
        int level = 1;
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdId().equals(parentId)) {
                LowCodeTreeVo vueTree = new LowCodeTreeVo();
                vueTree.setLabel(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                List<LowCodeTreeVo> newTrees = new ArrayList<>();
                GetLowCodeTreeChildren(newTrees, pojo.getdId(), list, request, level);
                vueTree.setChildren(newTrees);
                trees.add(vueTree);
            }
        }
    }

    public void GetLowCodeTreeChildren(List<LowCodeTreeVo> trees, String parentId, List<SysDepartmentDto> list, DepartmentDto request, int level) {
        level++;
        int currentLevel = level;
        for (SysDepartmentDto pojo : list) {
            if (pojo.getdParentid().equals(parentId)) {
                LowCodeTreeVo vueTree = new LowCodeTreeVo();
                vueTree.setLabel(pojo.getdName());
                vueTree.setValue(pojo.getdId());
                List<LowCodeTreeVo> newTrees = new ArrayList<>();
                if (request.getLevel() != null && currentLevel <= request.getLevel()) {
                    GetLowCodeTreeChildren(newTrees, pojo.getdId(), list, request, level);
                }
                vueTree.setChildren(newTrees);
                trees.add(vueTree);
            }
        }
    }

    private String getdCompanyIdByDid(List<SysDepartment> dList, String dId) {
        List<SysDepartment> newList = dList.stream().filter(n -> n.getdId().equals(dId)).collect(Collectors.toList());
        if (newList.size() > 0) {
            String id = newList.get(0).getdCompanyId();
            if (ValidateUtil.isNotEmpty(id)) {
                return id;
            } else {
                return getdCompanyIdByDid(dList, newList.get(0).getdParentid());
            }
        } else {
            return "";
        }
    }

}
