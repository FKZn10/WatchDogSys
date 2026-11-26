package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.gmgws.pojo.entity.SysFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISysFileService extends IService<SysFile> {

    /**
     * 获取项目信息列表
     *
     * @param id
     * @return
     */
    @Deprecated
    SysFile getfile(String id);

    /**
     * 自定义SQL插入语句
     *
     * @param sql
     * @return
     */
    Integer sql2Insert(String sql);

    /**
     * 批量插入语句执行
     *
     * @param inserts
     * @return
     */
    Integer insertBath(List<String> inserts);

    /**
     * 自定义SQL修改语句
     *
     * @param sql
     * @return
     */
    Integer sql2Upate(String sql);

    /**
     * 批量删除
     *
     * @param deletes
     * @return
     */
    Integer deleteBath(List<String> deletes);

    /**
     * 自定义SQL查询语句
     *
     * @param sql
     * @return
     */
    Map<String, Object> querySql(String sql);

    /**
     * 多记录查询
     *
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryList(String sql);

    /**
     * 自定义SQL分页查询
     *
     * @param sql 列表查询SQL
     * @param sumSql  统计查询SQL
     * @param request 请求入参
     * @return
     */
    BaseResponse queryPage(String sql,String sumSql, Map<String, Object> request);

    /**
     * 判断是否已存在文档数据
     *
     * @param id
     * @return
     */
    Boolean havingSysFile(String id);


    void deleteFileById(String id);

    Boolean saveFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId);

    SysFile structureFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String relationId, String sectionId, String tCode);

    void deleteByFileIds(List<String> ids);

    void onlyUpdateState(Integer state, String fileId);

    /**
     * 招标计划变更审批通过批量操作标段
     *
     * @param secInfos
     */
    void createSecInfoFileByBath(List<SecInfoChangeRes> secInfos, String pId, String tId, String title, String code, String tCode, String relationId);

    /**
     * zhangl
     *
     * @param uuid
     * @param title
     * @param status
     * @param userid
     * @param userName
     * @param fileCode
     * @param flag
     */
    void saveFileOrUpdate(String uuid, String title, String status, String userid, String userName, String fileCode, boolean flag);

    /**
     * 批量更新流程状态
     *
     * @param fileIds
     * @param state
     */
    void updateFileStateBatchIds(List<String> fileIds, Integer state);
}
