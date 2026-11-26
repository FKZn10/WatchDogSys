package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.GanttTasksProcessMapper;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasksProcess;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.service.IGanttTasksProcessService;
import com.jpmsoft.fileUpload.service.ISysFileService;
import com.jpmsoft.fileUpload.service.ISysFileTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/26 10:28
 */
@Service
public class GanttTasksProcessImpl extends ServiceImpl<GanttTasksProcessMapper, GanttTasksProcess> implements IGanttTasksProcessService {

    @Resource
    private ISysFileTemplateService sysFileTemplateService;
    @Resource
    private ISysFileService sysFileService;

    @Override
    public List<GanttTasksProcess> getByTaskIds(List<String> taskIds) {
        if (CollectionUtils.isEmpty(taskIds)) return null;
        QueryWrapper<GanttTasksProcess> wrapper = new QueryWrapper<>();
        wrapper.in("Task_Id", taskIds);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void create(List<GanttTasksProcess> gtpAdds) {
        if (CollectionUtils.isEmpty(gtpAdds)) return;
        SysFileTemplate temp = sysFileTemplateService.getByCode("PlanFeedback");
        if (temp == null) throw new JPMException(String.format(FileMsg.notFoundTemp, "PlanFeedback"));
        List<SysFile> sysFiles = new ArrayList<>();
        for (GanttTasksProcess g : gtpAdds) {
            SysFile f = new SysFile();
            f.setFileid(g.getGtpId());
            f.settCode(temp.gettCode());
            f.settId(temp.gettId());
            f.setFiletitle(temp.gettName());
            f.setFilecode(temp.gettCode());
            f.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex() + "");
            f.setFileRelationId(GenerateUUID.getUUID());
            f.setFilecreateusername(g.getGtpCreateUname());
            f.setFilecreateuser(g.getGtpCreateUid());
            f.setFilecreatedate(g.getGtpCreateTime());
            sysFiles.add(f);
        }
        this.saveBatch(gtpAdds);
        if (!CollectionUtils.isEmpty(sysFiles))
            sysFileService.saveBatch(sysFiles);
    }
}
