package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.oa.ChangeMeetingNotificationMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.ChangeMeetingNotification;
import com.jpmsoft.projectProducer.service.oa.IChangeMeetingNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:01
 */
@Service
public class ChangeMeetingNotificationImpl extends ServiceImpl<ChangeMeetingNotificationMapper, ChangeMeetingNotification>
        implements IChangeMeetingNotificationService {

    @Autowired
    private UserProducerFeign userProducerFeign;
    @Override
    public BaseResponse timeOut2LockMeeting(Integer day) {
        if (ValidateUtil.isNull(day)) day=30;
        // 获取当前日期
//        LocalDate today = LocalDate.now();
//        // 减去指定天数
//        LocalDate thirtyDaysAgo = today.minus(day, ChronoUnit.DAYS);
//        // 定义日期格式
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

         BaseResponse date = userProducerFeign.getBeforeWorkDay(day);
         if(ValidateUtil.isNull(date.getData())){
             return  BaseResponse.fail("获取工作日失败");
         }
        String time = String.valueOf(date.getData());
//        String time=thirtyDaysAgo.format(formatter);
        List<ChangeMeetingNotification> data=baseMapper.getTimeOutMeeting(time);
        if (!CollectionUtils.isEmpty(data))
        {
            //修改为超时锁定
            data.forEach(d->{
                d.setCmnCsState(2);
            });
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    public void apply2UnlockMeeting(String id,Integer scState) {
        baseMapper.apply2UnlockMeeting(id,scState);
    }

    @Override
    public BaseResponse queryDataList(Integer page,Integer size) {

        LambdaQueryWrapper<ChangeMeetingNotification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(ChangeMeetingNotification::getCmnCsState,2);
        queryWrapper.eq(ChangeMeetingNotification::getState,7);
         IPage<ChangeMeetingNotification> pages  = baseMapper.selectPage(new Page<>(page, size), queryWrapper);
        return BaseResponse.ok(pages.getRecords(),(int)pages.getTotal());
    }
}
