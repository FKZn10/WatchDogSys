package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPowerMapper;
import com.jpmsoft.userProducer.pojo.dto.PowerDto;
import com.jpmsoft.userProducer.pojo.entity.SysPower;
import com.jpmsoft.userProducer.pojo.vo.*;
import com.jpmsoft.userProducer.service.ISysPowerService;
import com.jpmsoft.userProducer.service.ISysRolePowerService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysPowerImpl extends ServiceImpl<SysPowerMapper, SysPower> implements ISysPowerService {


    //后台web父节点code
    private static final List<String> backManagePower = Arrays.asList("SystemManage", "OrganizationManage", "FlowManage", "ZZJK");
    @Resource
    private ISysRolePowerService sysRolePowerService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse getPowerTreeList(String title, String powerCode, String teId) {
        String treeKey = redisUtils.tenantKey("powerTreeList", teId);
        Object dictionaryTreeList = redisUtils.get(treeKey);
        if (dictionaryTreeList != null) {
            List<VuePowerTreeVo> returnData = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<VuePowerTreeVo>>() {
            });
            // 过滤
            List<VuePowerTreeVo> vuePowerTreeVos = this.powerTreeListFilter(returnData, title, powerCode);
            return BaseResponse.ok(vuePowerTreeVos);
        }
        List<SysPower> list = allPowerList(teId);
        List<VuePowerTreeVo> treeList = new ArrayList<>();
        list.forEach(model -> {
            if (model.getPowerParentid().equals("0")) {
                VuePowerTreeVo vueTree = new VuePowerTreeVo();
                BeanUtils.copyProperties(model, vueTree);
                vueTree.setKey(model.getPowerId());
                vueTree.setTitle(model.getPowerName());
                vueTree.setValue(model.getPowerId());
                vueTree.setPid(model.getPowerParentid());
                List<VuePowerTreeVo> vueTrees = new ArrayList<>();
                GetPowerTreeChildren(vueTrees, list.stream().filter(n -> n.getPowerParentid().equals(model.getPowerId())).collect(Collectors.toList()), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            }
        });
        redisUtils.set(treeKey, JacksonUtils.toJSONString(treeList));
        // 过滤
        List<VuePowerTreeVo> vuePowerTreeVos = this.powerTreeListFilter(treeList, title, powerCode);
        return BaseResponse.ok(vuePowerTreeVos);
    }

    /**
     * 过滤树形结构
     * <p>
     * 根据传入的tName和tCode进行过滤，返回包含匹配节点及其全部父级节点的树形结构
     * @param treeList
     * @param title
     * @param powerCode
     * @return
     */
    public List<VuePowerTreeVo> powerTreeListFilter(List<VuePowerTreeVo> treeList, String title, String powerCode) {
        if (CollectionUtils.isEmpty(treeList)) {
            return new ArrayList<>();
        }
        // 如果都没有查询条件，直接返回原列表
        if (ValidateUtil.isEmpty(title) && ValidateUtil.isEmpty(powerCode)) {
            return treeList;
        }

        String searchTitle = CheckNullUtil.checkString(title) ? null : title.toLowerCase(Locale.ROOT);
        String searchPowerCode = CheckNullUtil.checkString(powerCode) ? null : powerCode.toLowerCase(Locale.ROOT);
        List<VuePowerTreeVo> result = new ArrayList<>();
        for (VuePowerTreeVo node : treeList) {
            // 递归过滤子节点
            List<VuePowerTreeVo> validChildren = powerTreeListFilter(node.getChildren(), title, powerCode);

            // 检查当前节点是否匹配 (AND 逻辑: 如果传入了参数，必须匹配)
            boolean nameMatches = searchTitle == null || (node.getPowerName() != null && node.getPowerName().toLowerCase(Locale.ROOT).contains(searchTitle));
            boolean codeMatches = searchPowerCode == null || (node.getPowerCode() != null && node.getPowerCode().toLowerCase(Locale.ROOT).contains(searchPowerCode));

            boolean isSelfMatch = nameMatches && codeMatches;

            // 如果当前节点匹配，或者其子节点中有匹配项（即需要保留父级路径），则加入结果集
            if (isSelfMatch || !CollectionUtils.isEmpty(validChildren)) {
                node.setChildren(validChildren);
                result.add(node);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CUDPower(PowerDto request) {
        if (ValidateUtil.isEmpty(request.getType()))
            return new BaseResponse(HttpStatus.OK.value(), false, BaseMsg.notReqParam);
        String requestType = request.getType().toUpperCase(Locale.ENGLISH);

        if (requestType.equals("CREATE") || requestType.equals("CREATE2") || requestType.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<SysPower> wrapper = new QueryWrapper<>();
            wrapper.eq("Power_parentid", request.getPowerParentid());
            if (ValidateUtil.isNotEmpty(request.getTeId()))
                wrapper.eq("Te_Id", request.getTeId());
            if (requestType.equals("UPDATE"))
                wrapper.ne("Power_ID", request.getPowerId());
            wrapper.eq("Power_Code", request.getPowerCode());
//            wrapper.and(wrp -> wrp.eq("Power_Code", request.getPowerCode())
//                    .or().eq("Power_Name", request.getPowerName()));
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(BaseMsg.infoIsExist);
        }

        List<SysPower> alls = allPowerList(request.getTeId());
        String allTrue = redisUtils.tenantKey("powerAllStatusTrue", request.getTeId());
        String treeKey = redisUtils.tenantKey("powerTreeList", request.getTeId());
        String allKey = redisUtils.tenantKey("allPowerList", request.getTeId());
        String routeKey = redisUtils.tenantKey("VuePowerRoute", request.getTeId());
        if (requestType.equals("CREATE") || requestType.equals("CREATE2")) {
            SysPower entity = new SysPower();
            BeanUtils.copyProperties(request, entity);
            entity.setPowerId(GenerateUUID.getUUID());
            List<SysPower> sameLevels = alls.stream().filter(n -> n.getPowerParentid().equals(request.getPowerParentid())).collect(Collectors.toList());
            if (entity.getPowerSort() == null) {
                entity.setPowerSort(sameLevels.size() + 1);
            }
            entity.setPowerCreatetime(new Date());
            entity.setPowerAdmin(request.getPowerAdmin() != null && request.getPowerAdmin());
            baseMapper.insert(entity);
            sysRolePowerService.updatePositionRoleBath(request.getRoleIds(), entity.getPowerId());
            redisUtils.delete(allTrue, treeKey, allKey, routeKey);
            return BaseResponse.ok();
        } else if (requestType.equals("UPDATE")) {
            SysPower sysPower = baseMapper.selectById(request.getPowerId());
            if (sysPower != null) {
                if (sysPower.getPowerId().equals(request.getPowerParentid()))
                    return BaseResponse.fail(BaseMsg.selectSuperiorError);
                BeanUtils.copyProperties(request, sysPower);
                sysPower.setPowerSort(request.getPowerSort() == null ? 0 : request.getPowerSort());
                sysPower.setPowerAdmin(request.getPowerAdmin() != null && request.getPowerAdmin());
                if (request.getPowerParentid() != null && !sysPower.getPowerParentid().equals(request.getPowerParentid())) {
                    //修改父节点
                    //原同级节点下后续排序全部前进一级  新节点下当前节点排序为最后一级
                    List<SysPower> oldNodes = alls.stream().filter(n -> n.getPowerParentid().equals(sysPower.getPowerParentid()) && n.getPowerSort() > sysPower.getPowerSort()).collect(Collectors.toList());
                    List<SysPower> newNodes = alls.stream().filter(n -> n.getPowerParentid().equals(request.getPowerParentid())).collect(Collectors.toList());
                    sysPower.setPowerSort(newNodes.size() + 1);
                    sysPower.setPowerParentid(request.getPowerParentid());
                    List<SysPower> data = new ArrayList<>();
                    data.add(sysPower);
                    oldNodes.forEach(model -> {
                        model.setPowerSort(model.getPowerSort() - 1);
                        data.add(model);
                    });
                    updateBatchById(data);
                } else
                    baseMapper.updateById(sysPower);
                sysRolePowerService.updatePositionRoleBath(request.getRoleIds(), sysPower.getPowerId());
                redisUtils.delete(allTrue, treeKey, allKey, routeKey);
                return BaseResponse.ok();
            }
        } else if (requestType.equals("DELETE")) {
            SysPower pojo = baseMapper.selectById(request.getPowerId());
            if (pojo != null) {
                List<SysPower> childs = alls.stream().filter(n -> n.getPowerParentid().equals(pojo.getPowerId())).collect(Collectors.toList());
                Integer chilCount = childs.size();
                if (pojo.getPowerParentid().equals("0")) {
                    List<SysPower> roots = alls.stream().filter(n -> n.getPowerParentid().equals("0") && n.getPowerId() != pojo.getPowerId()).collect(Collectors.toList());
                    Integer rootCount = roots.size();
                    if (rootCount == 0)
                        return BaseResponse.fail(BaseMsg.onlyRootNode);
                    else if (chilCount > 0)
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    else {
                        delteToChangeOtherSort(pojo, alls);
                        baseMapper.deleteById(request.getPowerId());
                        redisUtils.delete(allTrue, treeKey, allKey, routeKey);
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        delteToChangeOtherSort(pojo, alls);
                        baseMapper.deleteById(request.getPowerId());
                        redisUtils.delete(allTrue, treeKey, allKey, routeKey);
                        return BaseResponse.ok();
                    }
                }
            }
        } else
            return BaseResponse.fail(BaseMsg.noOperationTypeSelected);
        return BaseResponse.fail();
    }

    @Override
    public BaseResponse getPowerTree(Boolean filterAdmin, String teId) {
        List<SysPower> list = getAllStatusTruePower(teId);
        List<PowerTreeVo> data = new ArrayList<>();
        getPowerTreeC(data, list.stream().filter(n -> n.getPowerParentid().equals("0")).collect(Collectors.toList()), list, filterAdmin);
        return BaseResponse.ok(data);
    }

    private void getPowerTreeC(List<PowerTreeVo> data, List<SysPower> models, List<SysPower> list, Boolean filterAdmin) {
        for (SysPower pojo : models) {
            if (!pojo.getPowerName().equals("主页")) {
                if (filterAdmin == null || !filterAdmin
                        || (ValidateUtil.isNull(pojo.getPowerAdmin()) || !pojo.getPowerAdmin())) {
                    PowerTreeVo tree = new PowerTreeVo();
                    tree.setKey(pojo.getPowerId());
                    tree.setTitle(pojo.getPowerName());
                    List<PowerTreeVo> treeList = new ArrayList<>();
                    getPowerTreeC(treeList, list.stream().filter(n -> n.getPowerParentid().equals(pojo.getPowerId())).collect(Collectors.toList()), list, filterAdmin);
                    tree.setChildren(treeList);
                    data.add(tree);
                }
            }
        }
    }

    private void GetPowerTreeChildren(List<VuePowerTreeVo> listTree, List<SysPower> models, List<SysPower> list) {
        models.forEach(model -> {
            VuePowerTreeVo vueTree = new VuePowerTreeVo();
            BeanUtils.copyProperties(model, vueTree);
            vueTree.setKey(model.getPowerId());
            vueTree.setTitle(model.getPowerName());
            vueTree.setValue(model.getPowerId());
            vueTree.setPid(model.getPowerParentid());
            List<VuePowerTreeVo> vueTrees = new ArrayList<>();
            GetPowerTreeChildren(vueTrees, list.stream().filter(n -> n.getPowerParentid().equals(model.getPowerId())).collect(Collectors.toList()), list);
            vueTree.setChildren(vueTrees);
            listTree.add(vueTree);
        });
    }

    private Integer getSysPowerCount(String parentId) {
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Power_Parentid", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    private Integer getRootPowerCount(String powerId) {
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Power_Parentid", '0');
        queryWrapper.ne("Power_Id", powerId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public Integer isParentPower(String powerId, String status) {
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Power_Parentid", powerId);
        queryWrapper.eq("Power_Status", status);
        return baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public List<SysPower> getAllPowerList(String teId) {
        List<SysPower> alls = allPowerList(teId);
        List<SysPower> datas = alls.stream().filter(n -> n.getPowerStatus() != null && n.getPowerStatus().equals("1")).collect(Collectors.toList());
        return datas;
    }

    @Override
    public BaseResponse getVueRoute(String powerCode, String teId) {
        String allKey = redisUtils.tenantKey("powerAllStatusTrue", teId);
        String routeKey = redisUtils.tenantKey("VuePowerRoute", teId);
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysPower> datas;
        if (dictionaryTreeList != null) {
            Object vueRoute = redisUtils.get(routeKey);
            if (ValidateUtil.isEmpty(powerCode) && vueRoute != null) {
                List<VueRouteVo> tree = new ArrayList<>();
                tree = JacksonUtils.parseObject(vueRoute.toString(), new TypeReference<List<VueRouteVo>>() {
                });
                return BaseResponse.ok(tree);
            }
            datas = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysPower>>() {
            });
        } else {
            datas = getEnablePower(teId);
            redisUtils.set(allKey, JacksonUtils.toJSONString(datas));
            redisUtils.delete(routeKey);
        }
        List<VueRouteVo> treeList = new ArrayList<>();
        List<RolePowerVo> roles = sysRolePowerService.getRoleNames(teId);
        VueRouteVo vueRoute = new VueRouteVo();
        vueRoute.setRouter("root");
        vueRoute.setName("首页");
        vueRoute.setIcon("");
        vueRoute.setAuthority(new AuthorityVo());
        List<VueRouteVo> child = new ArrayList<>();
        List<SysPower> mdoels;
        if (ValidateUtil.isEmpty(powerCode))
            mdoels = datas.stream().filter(n -> n.getPowerParentid().equals("0") && !n.getPowerType().equals("04")).collect(Collectors.toList());
        else
            mdoels = datas.stream().filter(n -> n.getPowerCode().equals(powerCode)).collect(Collectors.toList());
        getChildVueRoute(child, mdoels, datas, roles);
        if (!ValidateUtil.isEmpty(powerCode)) {
            List<VueRouteVo> newRoot = new ArrayList<>();
            child.forEach(node -> {
                node.setRouter("root");
                newRoot.add(node);
            });
            treeList = newRoot;
        } else {
            vueRoute.setChildren(child);
            treeList.add(vueRoute);
        }
        if (ValidateUtil.isEmpty(powerCode)) {
            redisUtils.set(routeKey, JacksonUtils.toJSONString(treeList));
        }
        return BaseResponse.ok(treeList);
    }

    @Override
    public BaseResponse getUserVueRoute(String powerCode, String token) {
        String teId = redisUtils.getTeId(token);
        List<SysPower> datas = getAllStatusTruePower(redisUtils.getTeId(token));
        List<VueRouteVo> treeList = new ArrayList<>();
        List<RolePowerVo> roles = sysRolePowerService.getRoleNames(teId);
        VueRouteVo vueRoute = new VueRouteVo();
        vueRoute.setRouter("root");
        vueRoute.setName("首页");
        vueRoute.setIcon("");
        vueRoute.setAuthority(new AuthorityVo());
        List<VueRouteVo> child = new ArrayList<>();
        List<SysPower> mdoels;
        if (ValidateUtil.isEmpty(powerCode))
            mdoels = datas.stream().filter(n -> n.getPowerParentid().equals("0") && !n.getPowerType().equals("04")
                    && !backManagePower.contains(n.getPowerCode())).collect(Collectors.toList());
        else
            mdoels = datas.stream().filter(n -> n.getPowerCode().equals(powerCode)).collect(Collectors.toList());
        getChildVueRoute(child, mdoels, datas, roles);
        if (!ValidateUtil.isEmpty(powerCode)) {
            List<VueRouteVo> newRoot = new ArrayList<>();
            child.forEach(node -> {
                node.setRouter("root");
                newRoot.add(node);
            });
            treeList = newRoot;
        } else {
            vueRoute.setChildren(child);
            treeList.add(vueRoute);
        }
        return BaseResponse.ok(treeList);
    }

    @Override
    public BaseResponse upOrDownPowerSort(String powerId, String action, String teId) {
        SysPower entity = baseMapper.selectById(powerId);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        QueryWrapper<SysPower> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Power_Parentid", entity.getPowerParentid());
        wrapper.orderByAsc("Power_Sort");
        List<SysPower> sameLevels = baseMapper.selectList(wrapper);
        Integer index = sameLevels.indexOf(sameLevels.stream().filter(f -> f.getPowerId().equals(powerId)).findFirst().get());
        Integer changeIndex;
        if (action.equals("up") && index > 0) {
            changeIndex = index - 1;
        } else if (action.equals("down") && index < sameLevels.size() - 1) {
            changeIndex = index + 1;
        } else
            return BaseResponse.fail(BaseMsg.notMove);
        SysPower temp = sameLevels.get(index);
        sameLevels.set(index, sameLevels.get(changeIndex));
        sameLevels.set(changeIndex, temp);
        int sort = 1;
        List<SysPower> updateData = new ArrayList<>();
        for (SysPower s : sameLevels) {
            s.setPowerSort(sort);
            sort++;
            updateData.add(s);
        }
        updateBatchById(updateData);
        redisUtils.delete(redisUtils.tenantKey("powerTreeList", teId), redisUtils.tenantKey("allPowerList", teId),
                redisUtils.tenantKey("powerAllStatusTrue", teId), redisUtils.tenantKey("VuePowerRoute", teId));
        return BaseResponse.ok();
    }

    private void getChildVueRoute(List<VueRouteVo> tree, List<SysPower> mdoels, List<SysPower> datas, List<RolePowerVo> roles) {
        for (SysPower data : mdoels) {
            VueRouteVo vueRoute = new VueRouteVo();
            vueRoute.setRouter(data.getPowerCode());
            vueRoute.setName(data.getPowerName());
            vueRoute.setType(data.getPowerType());
            vueRoute.setUrl(data.getPowerUrl());
            vueRoute.setIcon(data.getPowerIcon());
            vueRoute.setHomePage(data.getPowerHomePage());
            vueRoute.setRoute(data.getPowerRoute());
            vueRoute.setAlwaysShow(data.getPowerAlwaysShow());
            vueRoute.setAuthorizationCode(data.getPowerAuthorizationCode());
            vueRoute.setComponent(data.getPowerComponent());
            vueRoute.setHidden(data.getPowerHidden());
            vueRoute.setHighlight(data.getPowerHighlight());
            vueRoute.setAuthorizationPolicy(data.getPowerAuthorizationPolicy());
            vueRoute.setRedirect(data.getPowerRedirect());
            vueRoute.setView(data.getPowerView());
            vueRoute.setKeepAlive(data.getPowerKeepAlive());
            vueRoute.setInternalOrExternal(data.getPowerInternalOrExternal());
            vueRoute.setPowerPattern(data.getPowerPattern());
            vueRoute.setPowerParameter(data.getPowerParameter());
            AuthorityVo authority = new AuthorityVo();
            if (!data.getPowerName().equals("主页")) {
                List<String> role = roles.stream().filter(n -> n.getPowerId().equals(data.getPowerId())).map(RolePowerVo::getRoleName).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
                authority.setRole(role);
            }
            vueRoute.setAuthority(authority);
            List<VueRouteVo> child = new ArrayList<>();
            getChildVueRoute(child, datas.stream().filter(n -> n.getPowerParentid().equals(data.getPowerId()) && !n.getPowerType().equals("04") && !n.getPowerType().equals("1")).collect(Collectors.toList()), datas, roles);
            vueRoute.setChildren(child);
            tree.add(vueRoute);
        }
    }


    @Override
    public List<SysPower> allPowerList(String teId) {
        String allKey = redisUtils.tenantKey("allPowerList", teId);
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysPower> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysPower>>() {
            });
        } else {
            QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(teId))
                queryWrapper.eq("Te_Id", teId);
            queryWrapper.orderByAsc("Power_Sort");
            list = baseMapper.selectList(queryWrapper);
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        return list;
    }

    @Override
    public Boolean checkIsSubSystem(String pwCode) {
        QueryWrapper<SysPower> wrapper = new QueryWrapper<>();
        wrapper.eq("Power_Code", pwCode);
        wrapper.eq("Power_ParentID", "0");
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public BaseResponse getSystemPower(String teId) {
        Object dictionaryTreeList = redisUtils.get(redisUtils.tenantKey("allPowerList", teId));
        List<SysPower> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysPower>>() {
            });
            list = list.stream().filter(n -> !CheckNullUtil.checkString(n.getPowerType(), n.getPowerStatus()) && n.getPowerType().equals("04") && n.getPowerStatus().equals("1")).collect(Collectors.toList());
        } else {
            QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(teId))
                queryWrapper.eq("Te_Id", teId);
            queryWrapper.eq("Power_Type", "04");
            queryWrapper.eq("Power_Status", "1");
            queryWrapper.orderByAsc("Power_Sort");
            list = baseMapper.selectList(queryWrapper);
        }
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse getPowerCheckByRoleAndCode(String roleId, String code) {
        if (code.equals("0")) code = null;
        return BaseResponse.ok(baseMapper.getPowerCheckByRoleAndCode(roleId, code));
    }

    @Override
    public BaseResponse deleteByParentCode(String code) {
        List<SysPower> data = baseMapper.selectList(new QueryWrapper<>());
        if (!CollectionUtils.isEmpty(data)) {
            List<String> ids = new ArrayList<>();
            List<SysPower> root = data.stream().filter(f -> f.getPowerCode().equals(code)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(root)) {
                root.forEach(r -> {
                    ids.add(r.getPowerId());
                    getChildId(data, ids, r.getPowerId());
                });
            }
            if (!CollectionUtils.isEmpty(ids))
                baseMapper.deleteBatchIds(ids);
        }
        return BaseResponse.ok();
    }

    private void getChildId(List<SysPower> data, List<String> ids, String parentId) {
        List<SysPower> parents = data.stream().filter(f -> f.getPowerParentid().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(parents)) {
            parents.forEach(r -> {
                ids.add(r.getPowerId());
                getChildId(data, ids, r.getPowerId());
            });
        }
    }

    /**
     * 删除当前节点后 同级后续排序进1
     *
     * @param pojo
     */
    private void delteToChangeOtherSort(SysPower pojo, List<SysPower> datas) {
        List<SysPower> sameLevels = datas.stream().filter(n -> n.getPowerParentid().equals(pojo.getPowerParentid()) && n.getPowerSort() > pojo.getPowerSort()).collect(Collectors.toList());
        List<SysPower> changes = new ArrayList<>();
        sameLevels.forEach(model -> {
            model.setPowerSort(model.getPowerSort() - 1);
            changes.add(model);
        });
        if (!CollectionUtils.isEmpty(changes))
            updateBatchById(changes);
    }


    private List<SysPower> getEnablePower(String teId) {
        QueryWrapper<SysPower> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            queryWrapper.eq("Te_Id", teId);
        queryWrapper.eq("Power_Status", "1");
        queryWrapper.orderByAsc("Power_Sort");
        return baseMapper.selectList(queryWrapper);
    }

    private List<SysPower> getAllStatusTruePower(String teId) {
        String allTrue = redisUtils.tenantKey("powerAllStatusTrue", teId);
        Object dictionaryTreeList = redisUtils.get(allTrue);
        List<SysPower> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysPower>>() {
            });
        } else {
            list = getEnablePower(teId);
            redisUtils.set(allTrue, JacksonUtils.toJSONString(list));
        }
        return list;
    }

    @Override
    @Transactional
    public   BaseResponse  BatchSavePower(List<PowerDto>  request,String teId){
           if(CollectionUtils.isEmpty(request)){
               return BaseResponse.fail("请传入菜单资源信息");
           }else{
               List<SysPower> datas = new ArrayList<>();
               for (PowerDto pojo : request) {
                   SysPower entity = new SysPower();
                   BeanUtils.copyProperties(pojo, entity);
                   datas.add(entity);

               }

               //批量保存datas
               baseMapper.BatchSavePower(datas);
               String allTrue = redisUtils.tenantKey("powerAllStatusTrue", teId);
               String treeKey = redisUtils.tenantKey("powerTreeList", teId);
               String allKey = redisUtils.tenantKey("allPowerList", teId);
               String routeKey = redisUtils.tenantKey("VuePowerRoute", teId);
               redisUtils.delete(allTrue, treeKey, allKey, routeKey);
              return BaseResponse.ok("保存成功");

           }
    }


}
