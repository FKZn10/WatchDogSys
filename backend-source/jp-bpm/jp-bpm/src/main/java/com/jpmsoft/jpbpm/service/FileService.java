package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.jpbpm.pojo.dto.FileSecidDto;
import com.jpmsoft.jpbpm.pojo.dto.FileStatusDto;
import com.jpmsoft.jpbpm.pojo.dto.GetFlowDto;
import com.jpmsoft.jpbpm.pojo.dto.WorkFlowFileDto;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.vo.FileSecidVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface FileService extends IService<File> {

    File getFileById(String fileId,String tCode,String year);

    List<File>  getListByIds(List<String> fileIds,String tCode,String year);

    List<File> getList(QueryWrapper<File> queryWrapper,String tCode,String year);
    List<File> getList(LambdaQueryWrapper<File> queryWrapper,String tCode,String year);
    List<File> getList(QueryWrapper<File> queryWrapper,List<String> tCodes,String year);
    List<File> getList(LambdaQueryWrapper<File> queryWrapper,List<String> tCodes,String year);
    boolean  updateFileById(File file,String tCode,String year);
    boolean saveFileBatch(List<File>  files,String tCode,String year);
    boolean  insertFile(File file,String tCode,String year);
    BaseResponse updatefile(String tCode, String file_id, String style, String flow_id, String customerId, String token,String year);
    BaseResponse updateFileBath(List<File> fileList, String style, String token, String year);

    /**
     * 流程管理
     * 获取流程实例
     *
     * @param request
     * @return
     */
    BaseResponse getFileList(SysFileRequest request, String customerId);

    BaseResponse Settingapprovalstatus(FileStatusDto requst, String customerId, String token, LogUserInfoVo user);


    BaseResponse fileentitycreate(WorkFlowFileDto requst, String customerId, String token);

    BaseResponse filerelationlist(String fileid,String tCode, String customerId,String year);


    BaseResponse getFilebySecid(FileSecidDto requst, String customerId);

    List<FileSecidVo> getSectionFileDetails(FileSecidDto request, String customerId);

    BaseResponse getFilebySecids(FileSecidDto request, String customerId);

    /**
     * 危大工程业务办理节点数据查询
     * @param requst
     * @param customerId
     * @return
     */
    BaseResponse getDangerousProjectFileBySecId(FileSecidDto requst, String customerId);

    /**
     * 同步更新File表和业务表流程状态
     * 原触发器逻辑
     * <p>
     * 前期先使用文档模板配置 业务表的状态字段
     * 后期所有表状态字段同意一后移除文档模板配置（流程字段和流程时间字段移除）
     *
     * @param tCode  模板编码
     * @param id     业务主键ID 即 Sys_File表ID
     * @param state  流程状态
     * @param flowId
     * @return
     */
    BaseResponse syncFileAndBusinessState(String tCode, String id, Integer state, String flowId, String customerId, String token,String year);


    BaseResponse getBiddingProcedures(List<String> secIds, String customerId,String year,String businessCode);

    BaseResponse getBidProgress(List<GetFlowDto> getFlowDtos, String tCode,String customerId,String year);

    BaseResponse updateFileProjectByFileId(String fileId, String pId, String customerId,String tCode,String year);

    BaseResponse getFileRelationByFileIds(List<String> fileIds,String  tCode, String customerId,String year);

    List<Map<String, Object>> getFileByCode(List<String> tCodes, String uId, String customerId,String year);

    BaseResponse getFileByFileId(String fileId, String customerId,String tCode,String year);

    BaseResponse getMinTimeAndMaxTime(String customerId,String tCode,String year);

    BaseResponse insertSysFile(File request, String customerId);

    Map<String, Object> getData(String table, String key, String id, String customerId);

    List<Map<String, Object>> getData(String table, String key, List<String> ids, String customerId);

    BaseResponse getFileSecauthority(String id, String tCode, String uId, String customerId,String year);

    Integer updateBusinessStateAndStateTimeBath(String table, String primaryKey, String stateKey, String timeKey,
                                                String ids, Integer state, Date time);

    Integer updateBusinessStateBath(String table, String primaryKey, String stateKey,
                                    String ids, Integer state);

    Integer updateFileFlowStatusByFileIds(Integer FileFlowStatus, String FileIds, String FlowID,String tCodes,String year);

    BaseResponse judgeNodeApproved(String fileId, String actCode, String customerId,String year);

    BaseResponse getBiddingProceduresCode(List<String> secIds, String customerId, String year,String businessCode);

    BaseResponse updatePageCodeInFile(String fileId, String pageCode, String tCode, String year);

    BaseResponse getApprovalProgressByFileIds(List<String> ids, String actCode, String year);

}
