package com.jpmsoft.jpbpm.service;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 跨服务调用
 * 单据附件处理
 */
@Service
public interface SysFileAnnexService {

    /**
     * 修改表单时 刷新表单附件
     *
     * @param data
     * @return
     */
    BaseResponse refreshRelationFile(List<Map<String, String>> data,String  token);


    /**
     * 重载封装调用refreshRelationFile
     *
     * @param fileRelationId
     * @param fileIds
     * @return
     */
    BaseResponse refreshRelationFile(String fileRelationId, String fileIds,String  token);

    /**
     * 新增表单时 刷新表单附件
     *
     * @param ids
     * @return
     */
    BaseResponse addFormRefreshRelationFile(List<String> ids,String  token);

    /**
     * 重载封装调用addFormRefreshRelationFile
     *
     * @param relationId
     * @return
     */
    BaseResponse addFormRefreshRelationFile(String relationId,String  token);


    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    List<FileResponse> getFileByFileRelationId(String fileRelationId,String  token);
}
