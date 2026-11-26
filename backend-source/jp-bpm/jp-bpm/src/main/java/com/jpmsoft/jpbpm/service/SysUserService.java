package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysUser;
import com.jpmsoft.jpbpm.pojo.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SysUserService extends IService<SysUser> {


    List<SysUser> getUserAllByUids(List<String> uIds,String customerId);

    List<SysUser> getAllUser(String customerId);

    List<Map<String, Object>> getSomeFieldsUser(String uId);

    /**
     * 查询用户当前部门和下级部门ID
     *
     * @param uId
     * @return
     */
    List<String> getAllChildDepartment(String uId,String customerId,String token);



    List<UserInfoVo> getPersonnelSettings(List<String> uIds);


    List<Map> getUidByUidInfo(Map<String, Object> params);

    /**
     * 检查当前用户是否具有显示所有数据的权限
     *
     * @param uId 当前用户ID
     * @return true表示具有权限，false表示不具有权限
     */
    boolean checkShowAllDataAuth(String uId);
}
