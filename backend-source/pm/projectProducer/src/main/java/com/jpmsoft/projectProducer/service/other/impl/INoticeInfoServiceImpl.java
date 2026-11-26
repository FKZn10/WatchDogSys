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
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.blxw.BlxwInfoMapper;
import com.jpmsoft.projectProducer.mapper.other.NoticeInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.other.INoticeInfoService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class INoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements INoticeInfoService {
    @Autowired
    private BlxwInfoMapper blxwInfoMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse saveOrUpdateNoticeInfo(NoticeInfoModel dto) {
        log.info("request:{}",JacksonUtils.toJSONString(dto));
        String supportingDocument = dto.getSupportingDocument();
        String supportingDocumentFileIds = dto.getSupportingDocumentFileIds();
        if(StrUtil.isEmpty(supportingDocument)||StrUtil.isEmpty(supportingDocumentFileIds)){
            return BaseResponse.fail("请上传附件");
        }
        String primaryId = dto.getId();
        NoticeInfo entity ;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new NoticeInfo();
            BeanUtil.copyProperties(dto,entity);
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setCreateTime(DateTime.now());
            primaryId=GenerateUUID.getUUID();
            entity.setId(primaryId);
            baseMapper.insert(entity);
            if (entity.getSourceCode().equals("blxwInfo")){
                 BlxwInfo blxwInfo =blxwInfoMapper.selectById(entity.getSourceId());
                 if (blxwInfo != null){
                     blxwInfo.setBlxwState(2);
                 }
                 blxwInfoMapper.updateById(blxwInfo);
            }
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(dto.getFileRelationId(),supportingDocument), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,dto,"create",dto.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(dto,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(dto.getFileRelationId(),supportingDocument), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse approvalData(NoticeInfoModel request) {
        NoticeInfo entity ;
        SysFile fileEntity;
        String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(DateTime.now());
            baseMapper.updateById(entity);
            BlxwInfo blxwInfo =blxwInfoMapper.selectById(entity.getSourceId());
            if (entity.getSourceCode().equals("blxwInfo")){
                if (fileFlowStatus.equals(ApprovalStatusEnum.Already_Over.getIndex().toString())){
                    //更新不良行为处理状态
                    if (blxwInfo != null){
                        blxwInfo.setBlxwState(1);
                    }
                    blxwInfoMapper.updateById(blxwInfo);
                }else if (fileFlowStatus.equals(ApprovalStatusEnum.Invalid.getIndex().toString())){
                    //更新不良行为处理状态
                    if (blxwInfo != null){
                        blxwInfo.setBlxwState(0);
                    }
                    blxwInfoMapper.updateById(blxwInfo);
                }else{
                    //更新不良行为处理状态
                    if (blxwInfo != null){
                        blxwInfo.setBlxwState(2);
                    }
                    blxwInfoMapper.updateById(blxwInfo);
                }
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getNoticeInfoList(NoticeInfoModel request,String token) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        IPage<NoticeInfoModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<NoticeInfoModel> params = new QueryMap<>(request);
        params.put("sourceId").put("noticeInfoCode");
        List<NoticeInfoModel> noticeInfoList = baseMapper.getNoticeInfoList(params.getQuery(), page);
        log.info("blxwInfoVos:{}",JacksonUtils.toJSONString(noticeInfoList));
        return BaseResponse.ok(noticeInfoList,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getNoticeInfoDetail(String id) {
        NoticeInfoModel noticeInfoModel= baseMapper.getNoticeInfoById(id);
        if(noticeInfoModel==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}",noticeInfoModel);
        return BaseResponse.ok(noticeInfoModel);
    }
}