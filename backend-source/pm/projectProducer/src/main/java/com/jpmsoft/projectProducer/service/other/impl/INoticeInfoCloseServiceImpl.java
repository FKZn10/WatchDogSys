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
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.blxw.BlxwInfoMapper;
import com.jpmsoft.projectProducer.mapper.other.NoticeInfoCloseMapper;
import com.jpmsoft.projectProducer.mapper.other.NoticeInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfoClose;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.other.INoticeInfoCloseService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class INoticeInfoCloseServiceImpl extends ServiceImpl<NoticeInfoCloseMapper, NoticeInfoClose> implements INoticeInfoCloseService {
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;
    @Autowired
    private BlxwInfoMapper blxwInfoMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse saveOrUpdateNoticeInfoClose(NoticeInfoCloseModel request) {
        log.info("saveOrUpdateNoticeInfoClose request:{}", JacksonUtils.toJSONString(request));
        String supportingDocument = request.getSupportingDocument();
        String supportingDocumentFileIds = request.getSupportingDocumentFileIds();
        if(StrUtil.isEmpty(supportingDocument)||StrUtil.isEmpty(supportingDocumentFileIds)){
            return BaseResponse.fail("请上传附件");
        }
        if(StrUtil.isEmpty(request.getSourceId())){
            return BaseResponse.fail("请选择业务信息");
        }
        BlxwInfo blxwInfo = blxwInfoMapper.selectById(request.getSourceId());
        if (blxwInfo==null){
            return BaseResponse.fail("不良行为信息不存在");
        }
        String primaryId = request.getId();
        NoticeInfoClose entity;
        if(StrUtil.isEmpty(primaryId)){
            entity= new NoticeInfoClose();
            BeanUtil.copyProperties(request,entity);
            primaryId = IdWorker.getGenerateID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            blxwInfo.setCloseStatus("closing");
            baseMapper.insert(entity);
            blxwInfoMapper.updateById(blxwInfo);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),supportingDocument),
                    entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else{
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),supportingDocument),
                    entity.getId());
        }
        log.info("saveOrUpdateNoticeClose:{}", JacksonUtils.toJSONString(entity));
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse approvalData(NoticeInfoCloseModel request) {
        NoticeInfoClose entity ;
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
            NoticeInfo noticeInfo = noticeInfoMapper.selectById(entity.getNoticeInfoId());
            if (ApprovalStatusEnum.Invalid.getIndex().toString().equals(fileFlowStatus)){
                noticeInfo.setCloseStatus("unclosed");
            } else if (ApprovalStatusEnum.Already_Over.getIndex().toString().equals(fileFlowStatus)){
                noticeInfo.setCloseStatus("closed");
            }else {
                noticeInfo.setCloseStatus("closing");
            }
            noticeInfoMapper.updateById(noticeInfo);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getNoticeInfoCloseList(NoticeInfoCloseModel request, String token) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        IPage<NoticeInfoCloseModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<NoticeInfoCloseModel> params = new QueryMap<>(request);
        params.put("sourceId").put("noticeInfoCode");
        List<NoticeInfoCloseModel> list = baseMapper.getNoticeInfoCloseList(params.getQuery(), page);
        log.info("list:{}",JacksonUtils.toJSONString(list));
        return BaseResponse.ok(list,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getNoticeInfoCloseDetail(String id) {
        NoticeInfoCloseModel model= baseMapper.getNoticeInfoCloseById(id);
        if(model==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}",model);
        return BaseResponse.ok(model);
    }
}