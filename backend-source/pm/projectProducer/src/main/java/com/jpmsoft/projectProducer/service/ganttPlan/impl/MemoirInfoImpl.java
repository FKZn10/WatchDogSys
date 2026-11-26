package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.response.Plan.TaskProcessRateDTO;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.MemoirInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirImageDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirInfoDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirPraiseDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirInfo;
import com.jpmsoft.projectProducer.service.ganttPlan.*;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 大事记
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
@Service
public class MemoirInfoImpl extends FileImpl<MemoirInfoMapper, MemoirInfo, MemoirInfoDTO> implements IMemoirInfoService {

    @Resource
    private IMemoirImageService memoirImageService;
    @Resource
    private IMemoirPraiseService memoirPraiseService;
    @Resource
    private IMemoirCommentService memoirCommentService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IGanttTasksService ganttTasksService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMemoirInfo(MemoirInfoDTO request) {
        MemoirInfo mi = new MemoirInfo();
        createOrUpdateFileEntity(mi, request);
        memoirImageService.createOrUpdate(request.getMemoirImages(), mi.getId());
        return BaseResponse.ok(mi.getId());
    }

    @Override
    public BaseResponse getMemoirInfoList(MemoirInfoDTO request, String token) {
        String authSql = sysUserService.getAuthSql("memoir_info", redisUtils.getUid(token), "create_uid", OutUserAuthEnum.Organization.getIndex(), "state");
        IPage<MemoirInfoDTO> page = new Page<>(request.getPage(), request.getSize());
        List<MemoirInfoDTO> data = baseMapper.getMemoirInfoList(page, request, authSql);
        if (!CollectionUtils.isEmpty(data)) {
            List<String> miIds = data.stream().map(MemoirInfoDTO::getId).collect(Collectors.toList());
            List<MemoirImageDTO> images = memoirImageService.selectByMiIds(miIds);
            List<MemoirPraiseDTO> praises = memoirPraiseService.selectByMiIds(miIds);
            List<MemoirCommentDTO> comments = memoirCommentService.selectByMiIds(miIds);
            data.forEach(d -> {
                if (!CollectionUtils.isEmpty(images))
                    d.setMemoirImages(images.stream().filter(f -> f.getMiId().equals(d.getId())).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(praises))
                    d.setMemoirPraises(praises.stream().filter(f -> f.getMiId().equals(d.getId())).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(comments))
                    d.setMemoirComments(comments.stream().filter(f -> f.getMiId().equals(d.getId())).collect(Collectors.toList()));
            });
        }
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse approve2ComplateGanntTask(String id) {
        MemoirInfo entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.ok(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.ok(ResMsg.stateCannotAct);
        TaskProcessRateDTO gtr = new TaskProcessRateDTO();
        gtr.setTaskId(entity.getTaskId());
        gtr.setRate(100F);
        gtr.setCompleteDate(entity.getMiTime());
        ganttTasksService.process2RefreshGanttTask(gtr);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getMemoirInfoById(String id) {
        MemoirInfoDTO data = baseMapper.getMemoirInfoById(id);
        if (data != null) {
            data.setMemoirImages(memoirImageService.selectByMiIds(Collections.singletonList(id)));
            data.setMemoirComments(memoirCommentService.selectByMiIds(Collections.singletonList(id)));
            data.setMemoirPraises(memoirPraiseService.selectByMiIds(Collections.singletonList(id)));
        }
        return BaseResponse.ok(data);
    }
}
