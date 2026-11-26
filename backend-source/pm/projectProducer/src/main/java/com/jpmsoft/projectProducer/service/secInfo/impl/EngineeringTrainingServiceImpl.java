package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.EngineeringTrainingModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.EngineeringTrainingMapper;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.EngineeringTraining;
import com.jpmsoft.projectProducer.pojo.request.blxw.BlxwInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.EngineeringTrainingService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EngineeringTrainingServiceImpl extends BaseFileImpl<EngineeringTrainingMapper, EngineeringTraining, EngineeringTrainingModel> implements EngineeringTrainingService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse saveOrUpdateEngineeringTraining(EngineeringTrainingModel request) {
        String etFile = request.getEtFile();
        String etFileIds = request.getEtFileIds();
//        if(StrUtil.isEmpty(etFile)||StrUtil.isEmpty(etFileIds)){
//            return BaseResponse.fail("请上传附件");
//        }
        String primaryId = request.getId();
        EngineeringTraining entity ;
        if (StrUtil.isEmpty(primaryId)){
            entity  = new EngineeringTraining();
            BeanUtil.copyProperties(request,entity);
            primaryId=IdWorker.getGenerateID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setStateTime(DateTime.now());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),etFile), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,entity);
            entity.setStateTime(DateTime.now());
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),etFile), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getEngineeringTrainingList(EngineeringTrainingModel request, String token) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<EngineeringTrainingModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<EngineeringTrainingModel> params = new QueryMap<>(request);
        params.put("state").put("etType").put("etCompanyId").put("etCompanyName").put("etForm");
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<EngineeringTrainingModel> engineeringTrainingList = baseMapper.getEngineeringTrainingList(params.getQuery(), page);
        log.info("engineeringTrainingList:{}",JacksonUtils.toJSONString(engineeringTrainingList));
        return BaseResponse.ok(engineeringTrainingList,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getEngineeringTrainingDetail(String id) {
        EngineeringTrainingModel engineeringTrainingModel= baseMapper.getEngineeringTrainingById(id);
        if(engineeringTrainingModel==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}",engineeringTrainingModel);
        return BaseResponse.ok(engineeringTrainingModel);
    }

    @Override
    public BaseResponse approvalData(EngineeringTrainingModel request) {
        EngineeringTraining entity ;
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
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }
}
