package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.AiInfoExtractRecord;

public interface IAiInfoExtractRecordService extends IService<AiInfoExtractRecord> {

    /**
     *  记录新增
     * @param fileKey 附件MD5
     * @param type   操作类型
     * @param result 结果
     * @param token  访问令牌
     */
    void insertOne(String fileKey,String questionKey,String type,String result,String tCode,String token);

    /**
     * 查询上次同样的识别结果
     * @param type   操作类型
     * @param fileKey 附件MD5
     * @param questionKey  问题MD5
     * @return
     */
    String getAiResult(String type, String fileKey,String questionKey,String tCode);
}
