package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;

import java.util.List;

public interface ISysUserService extends IService<SysUser> {

    boolean existsAccount(String account);

    /**
     * 校验租户是否满足规则
     *
     * @param id   用户ID
     * @param rule user:创建用户 project:创建项目  date:租户是否到期
     */
    void checkTenantRuleByUid(String id, String rule);

    /**
     * 校验租户是否满足规则
     *
     * @param token 用户token
     * @param rule  user:创建用户 project:创建项目  date:租户是否到期
     */
    void checkTenantRuleByToken(String token, String rule);

    /**
     * 根据当前表单的部门字段鉴权
     * 单纯的当前部门以及下级部门可查看
     *
     * @param uId
     * @param currentDep    部门字段
     * @param dbCreateFiled
     * @param stateFile
     * @return
     */
    String currentDepAuth(String uId, String currentDep, String dbCreateFiled, String stateFile);

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
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth);

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
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile);

    /**
     * 逻辑同上（getAuthSql）
     * 却别 LogUserInfo信息为根据用户id到user服务查询而非根据redis缓存取出
     */
    String getAuthSqlSelect(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile);

    /**
     * 仅限内部用户以及乙方单位
     * 年度合同专属鉴权
     *
     * @return
     */
    String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile);

    /**
     * 重载getAuthSql方法 增加pKey（项目表外键名）入参
     * <p>
     * 在userAuth为Approver_Creator，也就是仅限创建人与审批人查看时，pKey为主键字段
     * <p>
     * 仅限创建人与审批人查看时也只能使用这个方法
     *
     * @param tableName
     * @param uId
     * @param dbCreateFiled
     * @param userAuth
     * @param stateFile
     * @param pKey
     * @return
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey);

    /**
     * 重载getAuthSql方法 增加pKey（项目表外键名）入参和userType入参
     * <p>
     * 在userAuth为Approver_Creator，也就是仅限创建人与审批人查看时，pKey为主键字段
     * <p>
     * 仅限创建人与审批人查看时也只能使用这个方法
     * <p>
     * 实际上userType已经没有使用了
     *
     * @param tableName
     * @param uId
     * @param dbCreateFiled
     * @param userAuth
     * @param stateFile
     * @param pKey
     * @param userType
     * @return
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey, String userType);

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

    String onlyShowCancel(String uId, String dbCreateFiled, String stateFile);

    String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey, String stateFile);

    /**
     * 城建需求内部用户按部门鉴权  集团看所有的  企业单位看本单位部门的
     * 外部用户组织机构鉴权
     *
     * @param uId  用户id
     * @param tCode 文档模板编码
     * @param tableName 表名
     * @param primaryKey 主键字段
     * @param createUidFiled 创建人uid字段
     * @param stateFile 审批状态字段
     * @return
     */
    String getAuthSqlInnerUserDepAndOutOrg(String uId, String tCode, String tableName, String primaryKey,  String createUidFiled,String stateFile);

    /**
     * 重载 字段参数为通用参数 主表SQL别名固定为mn
     *
     * @param uId  用户id
     * @param tCode 文档模板编码
     * @param tableName 表名
     * @return
     */
    String getAuthSqlInnerUserDepAndOutOrg(String uId, String tCode, String tableName);

    /**
     * 手机号批量查询用户
     *
     * @param phones
     * @return
     */
    List<SysUser> getUserInfoByPhone(List<String> phones);

    /**
     * 仅审批人与创建人可见
     * 根据基础配置走该鉴权逻辑
     * 城建定制逻辑
     *
     * @return
     */
    String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth, String primaryKey);

    String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth,String stateField, String primaryKey);

    String onlyCreateorAndApproverSQL(String uId, String dbCreateFiled, String primaryKey,String stateField);

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

    List<SysUserVo> getComPanyUser(List<String> dName, List<String> pName);

    String getMonthAuthSql(String invCompleteMonth, String uId, String icmCreateUid, List<String> outhList, String transferStart);

    /**
     * 上海对外经贸的质量检查使用
     *
     * @param uId
     * @return
     */
    boolean checkShowAllDataAuthSH(String uId);
}
