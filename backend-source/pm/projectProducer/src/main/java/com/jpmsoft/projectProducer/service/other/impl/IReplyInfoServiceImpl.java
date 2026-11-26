package com.jpmsoft.projectProducer.service.other.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.ReplyInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.ReplyInfo;
import com.jpmsoft.projectProducer.service.other.IReplyInfoService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IReplyInfoServiceImpl extends ServiceImpl<ReplyInfoMapper, ReplyInfo> implements IReplyInfoService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateReplyInfo(ReplyInfoModel request) {
        String primaryId = request.getId();
        if(StrUtil.isEmpty(primaryId)){
            primaryId = GenerateUUID.getUUID();
            ReplyInfo replyInfo=new ReplyInfo();
            request.setState(ApprovalStatusEnum.Not_Started.getIndex());
            BeanUtil.copyProperties(request,replyInfo);
            replyInfo.setId(primaryId);
            replyInfo.setCreateTime(DateTime.now());
            baseMapper.insert(replyInfo);
            //创建流程
            if(sysFileService.FileHelper(replyInfo,request,"create",request.getFileRelationId())){
                sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), replyInfo.getId());
            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else{
            ReplyInfo replyInfo = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,replyInfo);
            baseMapper.updateById(replyInfo);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(),request.getFileIds(),replyInfo.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getReplyInfoList(ReplyInfoModel request,String uid) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        QueryMap<ReplyInfoModel> params = new QueryMap<>(request);
        params.put("sourceCode").put("riCode").put("riTitle").put("riContent");
        IPage<ReplyInfoModel> page = new Page<>(request.getPage(), request.getSize());
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<ReplyInfoModel> replyInfoList = baseMapper.getReplyInfoList(page, params.getQuery());
        return BaseResponse.ok(replyInfoList, (int) page.getTotal());
    }

    @Override
    public BaseResponse getReplyInfoById(String id) {
        ReplyInfoModel replyInfoModel = baseMapper.getReplyInfoById(id);
        if(replyInfoModel==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(replyInfoModel);
    }
}
