package com.jpmsoft.projectProducer.service.leaderBoard.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.leaderBoard.GroupProjectIssueMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.yw.GroupProjectIssue;
import com.jpmsoft.projectProducer.service.leaderBoard.GroupProjectIssueService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GroupProjectIssueServiceImpl extends BaseFileImpl<GroupProjectIssueMapper, GroupProjectIssue, GroupProjectIssueModel> implements GroupProjectIssueService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse saveOrUpdateGroupProjectIssue(GroupProjectIssueModel request) {
        String issueFile = request.getFileId();
        String issueFileIds = request.getFileIds();
        String primaryId = request.getId();
        GroupProjectIssue entity ;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new GroupProjectIssue();
            BeanUtil.copyProperties(request,entity);
            primaryId= GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),issueFile), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(),issueFile),Arrays.asList(request.getFileIds(),issueFileIds),
                    entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getGroupProjectIssueList(GroupProjectIssueModel request, String token) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<GroupProjectIssueModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<GroupProjectIssueModel> params = new QueryMap<>(request);
        params.put("projectId").put("projectName").put("issueCategory");
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<GroupProjectIssueModel> groupProjectIssueList = baseMapper.getGroupProjectIssueList(params.getQuery(), page);
        log.info("GroupProjectIssues:{}",JacksonUtils.toJSONString(groupProjectIssueList));
        return BaseResponse.ok(groupProjectIssueList,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getGroupProjectIssueDetail(String id) {
        GroupProjectIssueModel groupProjectIssue= baseMapper.getGroupProjectIssueById(id);
        if(groupProjectIssue==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}",groupProjectIssue);
        return BaseResponse.ok(groupProjectIssue);
    }

    @Override
    public BaseResponse approvalData(GroupProjectIssueModel request) {
        GroupProjectIssue entity ;
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