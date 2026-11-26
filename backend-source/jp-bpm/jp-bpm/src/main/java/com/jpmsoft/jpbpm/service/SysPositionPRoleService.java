package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionPRole;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SysPositionPRoleService extends IService<SysPositionPRole> {
    List<SysPositionPRoleAndUidVo> getUidByRuleIdAndPid(String pId, List<String> rIdList,String customerId);
    List<String> getDisByuId(String uId);
}
