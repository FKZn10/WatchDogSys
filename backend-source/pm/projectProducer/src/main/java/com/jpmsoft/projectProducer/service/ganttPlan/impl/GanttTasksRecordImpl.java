package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Plan.GanttTasksChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksRecord;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 18:19
 */
@Service
public class GanttTasksRecordImpl extends ServiceImpl<GanttTasksRecordMapper, GanttTasksRecord> implements IGanttTasksRecordService {
    @Override
    public void createGanttTasksRecord(List<GanttTasksChangeDTO> records, String planId) {
        if (!CollectionUtils.isEmpty(records)) {
            Map<String, String> keys = new HashMap<>();
            for (GanttTasksChangeDTO record : records) {
                keys.put(record.getTaskId(), GenerateUUID.getUUID());
            }
            List<GanttTasksRecord> data = records.stream().map(r -> {
                GanttTasksRecord gtr = new GanttTasksRecord();
                BeanUtils.copyProperties(r, gtr);
                if (ValidateUtil.isEmpty(gtr.getTaskParent()) || gtr.getTaskParent().equals("0"))
                    gtr.setTaskParent("0");
                else gtr.setTaskParent(keys.get(r.getTaskParent()));
                gtr.setTaskId(keys.get(r.getTaskId()));
                gtr.setPlanId(planId);
                return gtr;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) saveBatch(data);
        }
    }

    @Override
    public List<GanttTasksChangeVO> getGanttTasksRecordByPlanId(String planId) {
        QueryWrapper<GanttTasksRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        List<GanttTasksRecord> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().map(entity -> {
                GanttTasksChangeVO vo = new GanttTasksChangeVO();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
