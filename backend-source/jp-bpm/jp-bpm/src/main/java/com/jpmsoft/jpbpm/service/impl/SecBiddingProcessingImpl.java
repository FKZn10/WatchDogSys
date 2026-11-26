package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SecBiddingProcessingMapper;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.entity.SecBiddingProcessing;
import com.jpmsoft.jpbpm.service.FileService;
import com.jpmsoft.jpbpm.service.FlowService;
import com.jpmsoft.jpbpm.service.SecBiddingProcessingService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SecBiddingProcessingImpl extends ServiceImpl<SecBiddingProcessingMapper, SecBiddingProcessing> implements SecBiddingProcessingService {

    @Autowired
    FlowService flowService;
    @Autowired
    FileService fileService;

    @Override
    @Transactional
    public BaseResponse createSecBiddingProcessing(SecBiddingProcessing secBiddingProcessing) {
        baseMapper.insert(secBiddingProcessing);
        return BaseResponse.ok("操作成功");

    }

    @Override
    @Transactional
    public BaseResponse updateSecBiddingProcessing(String id, Integer states) {
        SecBiddingProcessing obj = baseMapper.selectById(id);
        obj.setState(states);
        baseMapper.updateById(obj);
        return BaseResponse.ok("设置成功");
    }

    @Override
    @Transactional
    public BaseResponse updateSecBiddingProcessing(SecBiddingProcessing obj) {
        baseMapper.updateById(obj);
        return BaseResponse.ok("设置成功");
    }

    @Override
    @Transactional
    public BaseResponse updateOtherSecBiddingProcessing(String id, String tCode, Integer states, String customerId,String year) {
        File fileObj = fileService.getFileById(id, tCode,year);
        if (fileObj != null) {
            if (fileObj.getFileflowstatus() == 7) {
                updateSecBiddingProcessing(fileObj.getFilesectionid(), fileObj.getFilesectionid(), tCode, states, customerId);
            }
        }

        return BaseResponse.ok("设置成功");
    }


    @Override
    @Transactional
    public BaseResponse updateSecBiddingProcessing(String id, String secId, String tCode, Integer states, String customerId) {
        try {
            List<FlowActivity> actList = flowService.getFlowActivities("BidHandle", customerId);
            log.error("获取BidHandle："+actList.size());
            if (CollectionUtils.isNotEmpty(actList)) {
                List<String> tCodeList = actList.stream().map(n -> n.getActtemplatecode()).collect(Collectors.toList());
                log.error("tCode:"+tCode);
                log.error("tCodeList:"+tCodeList);
                log.error("判断tCode是否在tCodeList中："+tCodeList.contains(tCode));
                if (tCodeList.contains(tCode)) {
                    QueryWrapper<SecBiddingProcessing> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("T_Code", tCode);
                    queryWrapper.eq("Sec_Id", secId);

                    List<SecBiddingProcessing> objs = baseMapper.selectList(queryWrapper);
                    log.error("判断objs："+objs);
                    if (CollectionUtils.isEmpty(objs)) {
                        SecBiddingProcessing entity = new SecBiddingProcessing();
                        entity.setId(GenerateUUID.getUUID());
                        entity.setSecId(secId);
                        entity.setTCode(tCode);
                        entity.setState(states);
                        entity.setStartTime(new Date());
                        entity.setEndTime(new Date());
                        baseMapper.insert(entity);
                    } else {
                        SecBiddingProcessing obj = objs.get(0);
                        log.error("执行更新：");
                        String state = obj.getState() + "";
                        if ("3".equals(state) && "0".equals(states + "")) {
                            //其中某个节点重新办理
                            //获取招标办理的流程节点
                            tCodeList.remove(tCode);
                            QueryWrapper<SecBiddingProcessing> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.in("T_Code", tCodeList);
                            queryWrapper1.ne("T_Code", tCode);
                            queryWrapper1.eq("Sec_Id", secId);
                            queryWrapper1.gt("Start_Time", obj.getStartTime());

                            List<SecBiddingProcessing> objs1 = baseMapper.selectList(queryWrapper1);
                            if (CollectionUtils.isNotEmpty(objs1)) {
                                for (SecBiddingProcessing obj2 : objs1) {
                                    obj2.setState(0);
                                    obj2.setNotes("重新办理");
                                    baseMapper.updateById(obj2);
                                }
                            }
                            log.error("执行前置节点更新：");
                        }
                        obj.setState(states);
                        obj.setNotes("");
                        obj.setEndTime(new Date());
                        baseMapper.updateById(obj);
                    }
                }
            }
        } catch (Exception e) {
            log.error("流程服务：更新招标办理状态错误---》" + e.getMessage(), e);
            e.printStackTrace();
            return BaseResponse.fail("设置失败");
        }
        return BaseResponse.ok("设置成功");
    }


    @Override
    public List<SecBiddingProcessing> getSecBiddingProcessingBySecId(String secId) {
        QueryWrapper<SecBiddingProcessing> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SecBiddingProcessing> getSecBiddingProcessingBySecIds(List<String> secIds) {
        if (ValidateUtil.isEmpty(secIds)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<SecBiddingProcessing>lambdaQuery().in(SecBiddingProcessing::getSecId, secIds));
    }
}
