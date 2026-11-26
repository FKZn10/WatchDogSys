package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.custom.pojo.entity.SysUser;

import java.util.List;

public interface ISysUserService extends IService<SysUser> {


    /**
     * 获取鉴权SQL
     * 查询时表单别名mn
     *
     * @param tableName
     * @param uId
     * @param dbCreateFiled 默认表单别名mn 如果别名不为mn则当前值为 表别名.表创建人字段
     * @param userAuth
     * @return
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth,String conAlise);

    String ShowCancelData(String uId, String dbCreateFiled, String stateFile);

    /**
     * 获取鉴权SQL
     * 查询时表单别名mn
     *
     * @param tableName     当前表单名称
     * @param uId           用户ID
     * @param dbCreateFiled 创建人ID字段名（后期会统一成同名字段）（默认表单别名mn 如果别名不为mn则当前值为 表别名.表创建人字段）
     * @param userAuth      鉴权类型
     * @param stateFile     流程状态字段名（后期会统一成同名字段，表别名与dbCreateFiled一致）
     * @return
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile,String conAlise);

    /**
     * 仅限内部用户以及乙方单位
     * 年度合同专属鉴权
     *
     * @return
     */
    String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile);

    /**
     * 重载getAuthSql方法 增加pKey（项目表外键名）入参
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey,String conAlise);

    /**
     * 仅根据项目组织机构鉴权
     * 查询时表单别名mn
     *
     * @param tableName
     * @param uId
     * @param dbCreateFiled 创建人ID字段名（后期会统一成同名字段）
     * @param stateFile     流程状态字段名 （后期会统一成同名字段）
     * @return
     */
    String onlyProOrganizationAuth(String tableName, String uId, String dbCreateFiled, String stateFile);


    String onlyProOrganizationAuth(String tableName, String uId);


    /**
     * 查看用户是否是外部用户
     * 内部不鉴权 外部用户无权限
     *
     * @param uId
     * @return
     */
    Boolean checkIsOutUser(String uId);

    /**
     * 固定逻辑
     * 根据创建人鉴权
     * 当前操作人可查看所有自己组织机构和下级组织机构人员创建的数据
     *
     * @param uId            当前操作人ID
     * @param createUidFiled 表单创建人字段
     * @param tCode          文档模板编码
     * @param primaryKey     主键字段
     * @return
     */
    String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey);


    String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey, String stateFile);

    /**
     * 手机号批量查询用户
     *
     * @param phones
     * @return
     */
    List<SysUser> getUserInfoByPhone(List<String> phones);

    /**
     * 针对特定用户或角色的授权配置
     * 因为鉴权sql中left join的部分在部分sql中会有引用，且部分sql在使用时会有直接替换sql的情况，所有鉴权sql的部分会保留，只是添加一个1=1 or的条件，来跳过鉴权逻辑
     *
     * @param uId
     * @param dbCreateFiled
     * @param authSql
     * @return
     */
    String showAllDataAuth(String uId, String dbCreateFiled, String authSql);

    /**
     * 检查当前用户是否具有显示所有数据的权限
     *
     * @param uId 当前用户ID
     * @return true表示具有权限，false表示不具有权限
     */
    boolean checkShowAllDataAuth(String uId);
}
