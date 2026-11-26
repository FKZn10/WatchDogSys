package com.jpmsoft.jpbpm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysPositionPRoleMapper;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionPRole;
import com.jpmsoft.jpbpm.service.SysPositionPRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysPositionPRoleImpl extends ServiceImpl<SysPositionPRoleMapper, SysPositionPRole> implements SysPositionPRoleService {

    @Override
    public List<SysPositionPRoleAndUidVo> getUidByRuleIdAndPid(String pId, List<String> rIdList, String customerId) {
        String whereSql = "";
        for (Integer i = 0; i < rIdList.size(); i++) {
            if (i == 0) {
                whereSql += " and ( pr.Role_Id='" + rIdList.get(i) + "' ";
            } else {
                whereSql += " or pr.Role_Id='" + rIdList.get(i) + "' ";
            }
        }
        if (whereSql.trim().length() > 0) {
            whereSql += " ) ";
        }
        Map<String, String> param = new HashMap<>();
        param.put("pId", ValidateUtil.isEmpty(pId) ? null : pId);
        param.put("whereSql", whereSql);
        return baseMapper.getUidByRuleIdAndPid(param);
    }


    @Override
    public List<String> getDisByuId(String uId) {
        List<String> dIds = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("whereSql", " and pup.U_ID='" + uId + "'");
        List<SysPositionPRoleAndUidVo> list = baseMapper.getUidByRuleIdAndPid(param);
        if (!CollectionUtils.isEmpty(list)) {
            dIds = list.stream().map(n -> n.getdId()).collect(Collectors.toList());
        }
        return dIds;
    }
}
