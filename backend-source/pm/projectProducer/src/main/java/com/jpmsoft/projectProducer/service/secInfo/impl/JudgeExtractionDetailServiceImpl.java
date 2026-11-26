package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.JudgeExtractionDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtractionDetail;
import com.jpmsoft.projectProducer.service.secInfo.JudgeExtractionDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【judge_extraction_detail(评委抽取详情表)】的数据库操作Service实现
 * @createDate 2025-04-24 18:08:19
 */
@Service
public class JudgeExtractionDetailServiceImpl extends ServiceImpl<JudgeExtractionDetailMapper, JudgeExtractionDetail>
        implements JudgeExtractionDetailService {

    @Override
    public void saveInsideExpert(List<InsExpertRequest> insExperts, String id) {
        deleteByExId(id);
        if (!CollectionUtils.isEmpty(insExperts)) {
            List<JudgeExtractionDetail> collect = insExperts.stream().map(insExpert -> {
                JudgeExtractionDetail judgeExtractionDetail = new JudgeExtractionDetail();
                BeanUtils.copyProperties(insExpert, judgeExtractionDetail);
                judgeExtractionDetail.setId(GenerateUUID.getUUID());
                judgeExtractionDetail.setExtractionId(id);
                return judgeExtractionDetail;
            }).collect(Collectors.toList());
            saveBatch(collect);
        }
    }


    public void deleteByExId(String exId) {
        LambdaQueryWrapper<JudgeExtractionDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JudgeExtractionDetail::getExtractionId, exId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<InsExpertRequest> selectByExId(String exId) {
        LambdaQueryWrapper<JudgeExtractionDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JudgeExtractionDetail::getExtractionId, exId);
        List<JudgeExtractionDetail> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                InsExpertRequest r = new InsExpertRequest();
                BeanUtils.copyProperties(d, r);
                return r;
            }).collect(Collectors.toList());
        return Collections.emptyList();
    }
}




