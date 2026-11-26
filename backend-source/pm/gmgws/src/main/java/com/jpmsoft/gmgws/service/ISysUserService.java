package com.jpmsoft.gmgws.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.SysUser;

import java.util.List;

public interface ISysUserService extends IService<SysUser> {

    List<SysUser> getUser(List<String> collect);

    SysUser getUserCreator(String uid);

    /**
     * 仅限内部用户以及乙方单位
     * 年度合同专属鉴权
     *
     * @return
     */
    String onlyInnerAndPartyB(String tableName, String uId, String dbCreateFiled, String stateFile);


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




    String onlyCreateorAndApprover(String tableName, String uId, String dbCreateFiled, Integer userAuth,String stateField, String primaryKey);

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
     * 重载getAuthSql方法 增加pKey（项目表外键名）入参
     */
    String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String stateFile, String pKey);

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
}
