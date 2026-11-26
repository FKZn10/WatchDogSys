package com.jpmsoft.projectProducer.service.other.impl;


import cn.hutool.core.lang.Validator;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.TrainRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.TrainStaffDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.TrainVideoDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.TrainStaff;
import com.jpmsoft.projectProducer.service.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainRecordImpl  implements ITrainRecordService {
    @Autowired
    private TrainRecordMapper trainRecordMapper;

    @Override
    public BaseResponse getTrainRecord(TrainStaff trainStaff) {
        if(ValidateUtil.isEmpty(trainStaff)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<TrainStaffDTO> trainStaffDTOS = trainRecordMapper.getTrainStaffAllList(trainStaff);
        List<TrainVideoDTO> trainVideoDTOS = trainRecordMapper.trainStatistical();
        List<TrainVideoDTO> trainVideoAllList = trainRecordMapper.getTrainVideoAllList(7);

        List<Map<String,Object>> list = new ArrayList<>();

        // 先处理所有数据
        for (TrainStaffDTO trainStaffDTO : trainStaffDTOS) {
            // 添加空值检查
            if (trainStaffDTO == null) {
                continue; // 跳过null元素
            }

            // 安全地计算完成数量，处理null值情况
            final String staffId = trainStaffDTO.getId();
            int finishCount = 0;
            if (staffId != null && trainVideoDTOS != null) {
                finishCount = (int) trainVideoDTOS.stream()
                        .filter(item -> item != null && staffId.equals(item.getStaffId()))
                        .count();
            }

            // 安全地计算完成率
            double finishRate = 0.0;
            if (trainVideoAllList != null && !trainVideoAllList.isEmpty()) {
                finishRate = trainVideoAllList.size() == 0 ? 0 :
                        Math.round((double) finishCount * 100 / trainVideoAllList.size());
            }
            trainStaffDTO.setFinishRate(finishRate);

            Map<String,Object> map = new HashMap<>();
            map.put("id",trainStaffDTO.getId());
            map.put("region",trainStaffDTO.getRegion());
            map.put("name",trainStaffDTO.getName());
            map.put("unit",trainStaffDTO.getUnit());
            map.put("job",trainStaffDTO.getJob());
            map.put("phone",trainStaffDTO.getPhone());
            map.put("finishRate",trainStaffDTO.getFinishRate());
            map.put("createTime",trainStaffDTO.getCreateTime());

            // 确保列表不为null
            List<TrainVideoDTO> safeTrainVideoAllList = (trainVideoAllList == null) ?
                    new ArrayList<>() : trainVideoAllList;

            List<TrainVideoDTO> safeTrainVideoDTOS = (trainVideoDTOS == null) ?
                    new ArrayList<>() : trainVideoDTOS;

            for (TrainVideoDTO trainVideoDTO : safeTrainVideoAllList) {
                // 跳过null元素
                if (trainVideoDTO == null) continue;
                final String videoId = trainVideoDTO.getId();
                // 只有当staffId和videoId都不为null时才进行计算
                long count = 0;
                if (staffId != null && videoId != null && safeTrainVideoDTOS != null) {
                    count = safeTrainVideoDTOS.stream()
                            .filter(item -> item != null &&
                                    staffId.equals(item.getStaffId()) &&
                                    videoId.equals(item.getId()))
                            .count();
                }
                map.put(String.valueOf(videoId), count);
            }
            list.add(map);
        }

        // 合并相同人员的数据
        List<Map<String,Object>> mergedList = mergeSamePersonData(list);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("staffs", mergedList);
        resultMap.put("videos",trainVideoAllList);
        return BaseResponse.ok(resultMap);
    }

    /**
     * 合并相同人员的数据
     * @param originalList 原始数据列表
     * @return 合并后的数据列表
     */
    private List<Map<String,Object>> mergeSamePersonData(List<Map<String,Object>> originalList) {
        Map<String, Map<String,Object>> mergedMap = new LinkedHashMap<>();

        for (Map<String,Object> personData : originalList) {
            // 构建唯一标识符：unit + phone + name + job + region
            String unit = (String) personData.get("unit");
            String phone = (String) personData.get("phone");
            String name = (String) personData.get("name");
            String job = (String) personData.get("job");
            String region = (String) personData.get("region");

            // 创建合并键
            String mergeKey = (unit == null ? "" : unit) + "|" +
                    (phone == null ? "" : phone) + "|" +
                    (name == null ? "" : name) + "|" +
                    (job == null ? "" : job) + "|" +
                    (region == null ? "" : region);

            if (mergedMap.containsKey(mergeKey)) {
                // 如果已存在，则合并数据
                Map<String,Object> existingData = mergedMap.get(mergeKey);

                // 合并视频观看数据，取最大值（其中一个为1就是1）
                for (Map.Entry<String, Object> entry : personData.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    // 跳过基本信息字段
                    if ("id".equals(key) || "region".equals(key) || "name".equals(key) ||
                            "unit".equals(key) || "job".equals(key) || "phone".equals(key) ||
                            "finishRate".equals(key) || "createTime".equals(key)) {
                        continue;
                    }

                    // 对于视频数据，取最大值
                    Object existingValue = existingData.get(key);
                    if (existingValue == null) {
                        existingData.put(key, value);
                    } else {
                        // 转换为数字并取最大值
                        try {
                            long existingLong = ((Number) existingValue).longValue();
                            long newLong = ((Number) value).longValue();
                            existingData.put(key, Math.max(existingLong, newLong));
                        } catch (Exception e) {
                            // 如果转换失败，保留原有值
                            existingData.put(key, existingValue);
                        }
                    }
                }

                // 处理创建时间，取最新的时间
                handleCreateTime(existingData, personData);

                // 重新计算完成率
                recalculateFinishRate(existingData);
            } else {
                // 如果不存在，直接添加
                mergedMap.put(mergeKey, new HashMap<>(personData));
            }
        }

        return new ArrayList<>(mergedMap.values());
    }

    /**
     * 处理创建时间，取最新的时间
     * @param existingData 已存在的数据
     * @param newData 新数据
     */
    private void handleCreateTime(Map<String, Object> existingData, Map<String, Object> newData) {
        Date existingCreateTime = (Date) existingData.get("createTime");
        Date newCreateTime = (Date) newData.get("createTime");

        // 如果新数据的时间存在且比现有时间更晚，则更新
        if (newCreateTime != null) {
            if (existingCreateTime == null || newCreateTime.after(existingCreateTime)) {
                existingData.put("createTime", newCreateTime);
            }
        }
    }

    /**
     * 重新计算完成率
     * @param personData 人员数据
     */
    private void recalculateFinishRate(Map<String, Object> personData) {
        // 计算完成的视频数量
        long completedCount = 0;
        long totalCount = 0;

        for (Map.Entry<String, Object> entry : personData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 跳过基本信息字段
            if ("id".equals(key) || "region".equals(key) || "name".equals(key) ||
                    "unit".equals(key) || "job".equals(key) || "phone".equals(key) ||
                    "finishRate".equals(key) || "createTime".equals(key)) {
                continue;
            }

            // 统计视频数据
            totalCount++;
            try {
                long videoStatus = ((Number) value).longValue();
                if (videoStatus > 0) {
                    completedCount++;
                }
            } catch (Exception e) {
                // 忽略无法转换的值
            }
        }

        // 计算完成率
        double finishRate = (totalCount == 0) ? 0 :
                Math.round((double) completedCount * 100 / totalCount);
        personData.put("finishRate", finishRate);
    }


}
