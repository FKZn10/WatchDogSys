package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysPositionUserMapper;
import com.jpmsoft.jpbpm.pojo.dto.DepartmentDto;
import com.jpmsoft.jpbpm.pojo.entity.Department;
import com.jpmsoft.jpbpm.pojo.entity.SysPosition;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionUser;
import com.jpmsoft.jpbpm.pojo.vo.userCVo;
import com.jpmsoft.jpbpm.service.DepartmentService;
import com.jpmsoft.jpbpm.service.SysPositionService;
import com.jpmsoft.jpbpm.service.SysPositionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysPositionUserImpl extends ServiceImpl<SysPositionUserMapper, SysPositionUser> implements SysPositionUserService {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private SysPositionService sysPositionService;

    @Override
    public List<SysPositionUser> getPositionUserAll(String customerId) {
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        return baseMapper.selectList(wrapper);
    }


    @Override
    public List<userCVo> getPositionUserIdbyuid(String uId, String customerId) {
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        return baseMapper.getUserIdByDid(uId, customerId);
    }
    @Override
    public List<userCVo> getPositionUserIdbydIds(List<String> dIds) {
        return baseMapper.getUserObjByDids(dIds);
    }
    @Override
    public List<userCVo> getUserIdByPid(String pId, String customerId) {
        return baseMapper.getUserIdByPid(pId, customerId);
    }


    @Override
    public List<String> getPositionUserIdbydids(String dIdsql, String customerId) {
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        return baseMapper.getUserIdByDids(dIdsql, customerId);
    }

    @Override
    public List<Map<String, Object>> selectUidByUid(String dname, List<String> uids, String customerId) {
        if (!ValidateUtil.isEmpty(dname) && uids.size() > 0) {
            String userstr = uids.stream().map(String::valueOf).collect(Collectors.joining("','"));
            Map<String, Object> params = new HashMap<>();
            params.put("dname", dname);
            params.put("userid", userstr);
            return baseMapper.selectUidByUid(params);
        } else {
            return null;
        }
    }


    @Override
    public List<SysPositionUser> getPositionUserIdByDids(List<String> dIds, String customerId) {
        QueryWrapper<SysPositionUser> Positionwrapper = new QueryWrapper<>();
        for (Integer i = 0; i < dIds.size(); i++) {
            if (i == 0) {
                Positionwrapper.eq("P_ID", dIds.get(i));
            } else {
                Positionwrapper.or().eq("P_ID", dIds.get(i));
            }
        }
        return baseMapper.selectList(Positionwrapper);
    }


    @Override
    public List<String> getDidByUid(String uId) {
        return baseMapper.getDidByDid(uId);
    }


    //判断当前用户是那个部门的管理员账号
    @Override
    public List<String> getDeptIdList(String userId, String customerId) {
        List<String> deptIdList = new ArrayList<>();
        //获取所有的
        List<Department> dpList = departmentService.getDepartmentAll(customerId);
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        wrapper.eq("U_ID", userId);
        List<SysPositionUser> spuList = baseMapper.selectList(wrapper);
        //获取所有管理员岗位（管理员）
        QueryWrapper<SysPosition> wrapperP = new QueryWrapper<>();
        wrapperP.eq("P_Name", "管理员");
        List<SysPosition> spList = sysPositionService.list(wrapperP);
        if (!CollectionUtils.isEmpty(spuList)) {
            List<String> pIds = spuList.stream().map(n -> n.getpId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(spList)) {
                List<SysPosition> sps = spList.stream().filter(n -> pIds.contains(n.getpId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sps)) {
                    List<String> dIds = sps.stream().map(n -> n.getdId()).collect(Collectors.toList());
                    List<Department> dlist = dpList.stream().filter(n -> dIds.contains(n.getdId())).collect(Collectors.toList());
                    List<Department> dlist1 = dpList.stream().filter(n -> dIds.contains(n.getdId()) && "0".equals(n.getdParentid())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dlist1)) {
                        deptIdList.add("ALL");
                        //顶层管理员，全部账号权限
                    } else {
                        //非顶层管理员，返回所有部门
                        List<String> dids = dlist.stream().map(n -> n.getdId()).collect(Collectors.toList());
                        deptIdList.addAll(dids);
                        deptIdList.addAll(getDidList(dpList, dids));
                    }
                }
            }
        }
        return deptIdList;
    }


    public List<String> getDidList(List<Department> dpList, List<String> dIds) {
        List<String> didList = new ArrayList<>();
        for (String dId : dIds) {
            List<Department> dps = dpList.stream().filter(n -> n.getdParentid().equals(dId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dps)) {
                List<String> didls = dps.stream().map(n -> n.getdId()).collect(Collectors.toList());
                didList.addAll(didls);
                didList.addAll(getDidList(dpList, didls));
            }
        }
        return didList;
    }



}
