package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.DepPUserBatchSelectDTO;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.SysUserVo;
import com.jpmsoft.userProducer.pojo.vo.UserPDPVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 分页查询用户信息
     *
     * @param params
     * @return
     */
    List<SysUserVo> GetUserList(@Param("params") Map<String, Object> params);

    /**
     * 分页总数
     *
     * @return
     */
    Integer GetUserListCount(@Param("params") Map<String, Object> params);

    List<SysUserVo> getUserListByUnitPid(@Param("params") Map<String, Object> params);

    Integer getUserListByUnitPidCount(@Param("params") Map<String, Object> params);

    List<SysUserVo> GetUserListByDId(@Param("page") IPage<SysUserVo> page, @Param("params") Map<String, Object> params);

    List<UserPDPVo> GetUserListByPDP(@Param("page") IPage<UserPDPVo> page, @Param("params") Map<String, Object> params);


    /**
     * 根据项目部门id获取人员信息
     *
     * @return
     */
    List<SysUserVo> GetUserListByDPId(@Param("params") Map<String, Object> params, @Param("page") IPage<SysUserVo> page);

    List<SysUserVo> GetUserListByDPIdDistinct(@Param("params") Map<String, Object> params, @Param("page") IPage<SysUserVo> page);

    List<SysUserVo> getDepBatchUser(@Param("params") DepPUserBatchSelectDTO params);

    SysUserVo getUserInfoById(@Param("uId") String uId);


    List<SysUserVo> getUesrInfoByIds(@Param("uIds") List<String> uIds);


    List<SysUserVo> getUserListByMoreCondition(@Param("params") Map<String, Object> params);

    Integer getUserListByMoreConditionCount(@Param("params") Map<String, Object> params);


    /**
     * 查询所有包含Con_Id字段的表名
     * 用于外部用户鉴权
     *
     * @return
     */
    List<String> getAllHaveContractTable();


    int havingPmUserInProjectDep(String pId, List<String> positionName);


    @Update("update SYS_User set U_State=#{state} where U_Id=#{uId}")
    Integer disableUser(@Param("uId") String uId, @Param("state") String state);

    /**
     * 刷新用户首次登录是否越过校验超期禁用标识
     *
     * @param uId          用户ID
     * @param uAutoDisable 是否越过校验超期禁用标识 true 越过校验  false不越过校验
     * @return
     */
    @Update("update SYS_User set U_Auto_Disable=#{uAutoDisable} where U_Id=#{uId}")
    Integer refreshAuthDisableTag(@Param("uId") String uId, @Param("uAutoDisable") Boolean uAutoDisable);


    SysUser getPmUserInProjectDep(String pId, List<String> positionName);


    List<SysUserVo> getProjectDepUserByPpName(@Param("projectIds") List<String> projectIds
            ,@Param("positionName") List<String> positionName
            ,@Param("teId") String teId);

    /**
     * 根据岗位名称批量获取用户信息
     *
     * @param positionNames
     * @return
     */
    List<SysUserVo> getUserInfoByPositionNames(@Param("positionNames") List<String> positionNames);
}
