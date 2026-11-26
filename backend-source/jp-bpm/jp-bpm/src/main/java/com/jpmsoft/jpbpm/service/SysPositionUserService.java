package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionUser;
import com.jpmsoft.jpbpm.pojo.vo.userCVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SysPositionUserService extends IService<SysPositionUser> {

    List<SysPositionUser> getPositionUserAll(String customerId);
    List<userCVo> getPositionUserIdbyuid(String uId, String customerId);
    List<userCVo> getPositionUserIdbydIds(List<String> dIds);
    List<String> getPositionUserIdbydids(String dIdsql,String customerId);
    List<Map<String,Object>> selectUidByUid(String dname, List<String> uids,String customerId);
    List<userCVo> getUserIdByPid(String pId,String customerId);
    List<SysPositionUser> getPositionUserIdByDids(List<String> dIds,String customerId);
    List<String>  getDidByUid(String uId);
    List<String> getDeptIdList(String userId, String customerId);
}
