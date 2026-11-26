package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPTSpotCheckRecordRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPTSpotCheckRecordMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheckRecord;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPTSpotCheckRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QsPTSpotCheckRecordImpl extends ServiceImpl<QsPTSpotCheckRecordMapper, QsPTSpotCheckRecord> implements IQsPTSpotCheckRecordService {

    @Resource
    private QsRecordMapper qsRecordMapper;

    @Override
    public void createOrUpdateQsPTSpotCheckRecord(List<QsPTSpotCheckRecordDto> qsPTSpotCheckRecordList, String qsscId) {
        delByQsscId(qsscId);
        if(CollectionUtil.isNotEmpty(qsPTSpotCheckRecordList)){
            List<QsPTSpotCheckRecord> list=new ArrayList<>();
            for (QsPTSpotCheckRecordDto qsPTSpotCheckRecordDto : qsPTSpotCheckRecordList) {
                QsPTSpotCheckRecord qsPTSpotCheckRecord = new QsPTSpotCheckRecord();
                BeanUtil.copyProperties(qsPTSpotCheckRecordDto, qsPTSpotCheckRecord);
                qsPTSpotCheckRecord.setId(GenerateUUID.getUUID());
                qsPTSpotCheckRecord.setQsscId(qsscId);
                qsPTSpotCheckRecord.setCreateTime(new Date());
                list.add(qsPTSpotCheckRecord);
            }

            if(CollectionUtil.isNotEmpty(list)){
                saveBatch(list);
            }
        }
    }

    @Override
    public BaseResponse updateSpotCheckRectifyPic(QsPTSpotCheckRecordRequest request) {
        QsPTSpotCheckRecord model = baseMapper.selectById(request.getId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        model.setRectifyFinishTime(request.getRectifyFinishTime());
        model.setNewQsrRectifyStatus(request.getNewQsrRectifyStatus());
        model.setRectifyRealUname(request.getRectifyRealUname());
        model.setRectifyReply(request.getRectifyReply());
        baseMapper.updateById(model);
        //修改原数据整改状态
        qsRecordMapper.updateRectifyStatus(Arrays.asList(request.getQsrId()),request.getNewQsrRectifyStatus());
        return BaseResponse.ok();
    }

    @Override
    public void updateRectifyStatus(List<QsPTSpotCheckRecordDto> qsPTSpotCheckRecordList, String rectifyStatus) {
        if(CollectionUtil.isNotEmpty(qsPTSpotCheckRecordList)){
            List<String> collect = qsPTSpotCheckRecordList.stream().map(e -> e.getQsrId()).collect(Collectors.toList());
            qsRecordMapper.updateRectifyStatus(collect,rectifyStatus);
        }
    }

    @Override
    public List<QsPTSpotCheckRecord> getSpotCheckRecordByQsscId(String qsscId) {
        if(StrUtil.isBlank(qsscId))
            return new ArrayList<>();
        QueryWrapper<QsPTSpotCheckRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("qssc_id",qsscId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void updateRectifyStatusByIds(List<QsPTSpotCheckRecord> qsPTSpotCheckRecordList) {
        if(CollectionUtil.isNotEmpty(qsPTSpotCheckRecordList)){
            List<QsRecord> qsRecordList = new ArrayList<>();
            for (QsPTSpotCheckRecord qsPTSpotCheckRecord : qsPTSpotCheckRecordList) {
                QsRecord qsRecord = new QsRecord();
                qsRecord.setQsrId(qsPTSpotCheckRecord.getQsrId());
                qsRecord.setQsrRectifyStatus(qsPTSpotCheckRecord.getQsrRectifyStatus());
                qsRecordList.add(qsRecord);
            }
            if(CollectionUtil.isNotEmpty(qsRecordList)){
                qsRecordMapper.updateRectifyStatusByIds(qsRecordList);
            }
        }
    }

    private void delByQsscId(String qsscId) {
        QueryWrapper<QsPTSpotCheckRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("qssc_id", qsscId);
        baseMapper.delete(wrapper);
    }


}
