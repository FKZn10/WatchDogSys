package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpExperience;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfo;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollect;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChangeName;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;

import java.util.Date;
import java.util.List;

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
     * 判断是否已存在文档数据
     *
     * @param id
     * @return
     */
    Boolean havingSysFile(String id);


    void deleteFileById(String id);

    @Deprecated
    Boolean FileHelper(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode);

    Boolean saveFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId);

    Boolean saveFileTeId(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId,String teId);

    SysFile structureFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String relationId, String sectionId, String tCode);

    void saveFileOrUpdate(String uuid, String title, String status, String userid, String userName, String fileCode, boolean flag, String tId, String tCode);

    @Deprecated
    Boolean FileHelper(PreProject entity, String tId, String title, String code, String status, String relationId, String tCode);

    @Deprecated
    Boolean FileHelper(PreProjectRegister entity, String tId, String title, String code, String status, String relationId, String tCode);

    @Deprecated
    Boolean FileHelper(PreProjectChangeName entity, String tId, String title, String code, String status, String relationId, String tCode);

    @Deprecated
    Boolean FileHelper(PeInfo entity, String tId, String title, String code, String status, String relationId, String tCode);

    Boolean FileHelper(ProjectCpExperience entity, String tId, String title, String code, String status, String relationId, String cpId, String tCode);

    Boolean FileHelper(ProjectCpWorkRecord entity, String tId, String title, String code, String status, String relationId, String cpId, String tCode);

    void FileHelper(PeInfoCollect entity, String tId, String title, String code, String status, String fileRelationId, String tCode);

    void FileHelper(ConContract entity, String tId, String title, String code, String status, String fileRelationId, String tCode);

    void deleteByFileIds(List<String> ids);

    void onlyUpdateState(Integer state, String fileId);

    void onlyUpdateState(Integer state, List<String> fileId);

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

    <T,D extends BaseFileDto> boolean  FileHelper(T t,D request,String action,String fileRelationId) ;

    void  updatePId(SysFile sysFile);

    /**
     * 如果流程里的FileRelationId为null，给它赋值并返回
     *
     * @param id
     */
    String fixFileRelationId(String id);

    FileTemplateTree getFileTemplateByTCode(String tCode);
}
