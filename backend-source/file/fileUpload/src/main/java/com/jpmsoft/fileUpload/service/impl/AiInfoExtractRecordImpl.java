package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.AiInfoExtractRecordMapper;
import com.jpmsoft.fileUpload.pojo.entity.AiInfoExtractRecord;
import com.jpmsoft.fileUpload.service.IAiInfoExtractRecordService;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AiInfoExtractRecordImpl extends ServiceImpl<AiInfoExtractRecordMapper, AiInfoExtractRecord> implements IAiInfoExtractRecordService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void insertOne(String fileKey, String questionKey, String type, String result, String tCode, String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        AiInfoExtractRecord record = new AiInfoExtractRecord();
        record.setId(GenerateUUID.getUUID());
        record.setFileKey(fileKey);
        record.setQuestionKey(questionKey);
        record.setAiType(type);
        record.setAiResult(result);
        record.setTempCode(tCode);
        record.setCreateTime(new Date());
        record.setCreateUid(user.getId());
        record.setCreateUname(user.getName());
        record.setStatus("1");
        save(record);
    }

    @Override
    public String getAiResult(String type, String fileKey, String questionKey, String tCode) {
        if (ValidateUtil.isAnObjEmpty(fileKey, questionKey, type, tCode)) return null;
        LambdaQueryWrapper<AiInfoExtractRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AiInfoExtractRecord::getAiType, type)
                .eq(AiInfoExtractRecord::getFileKey, fileKey)
                .eq(AiInfoExtractRecord::getQuestionKey, questionKey)
                .eq(AiInfoExtractRecord::getTempCode, tCode)
                .eq(AiInfoExtractRecord::getStatus, "1");
        List<AiInfoExtractRecord> recordList = list(wrapper);
        if (!CollectionUtils.isEmpty(recordList)) {
            return recordList.get(0).getAiResult();
        }
        return null;
    }
}
