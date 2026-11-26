package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProjectProfileMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectProfile;
import com.jpmsoft.projectProducer.service.project.IProjectProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jjz
 */
@Service
public class ProjectProfileImpl extends ServiceImpl<ProjectProfileMapper, ProjectProfile> implements IProjectProfileService {
    @Override
    public BaseResponse getProjectProfileList(ProjectProfileModel request) {

        QueryMap<ProjectProfileModel> params = new QueryMap<>(request);
        params.put("pId").put("ppType").put("ppCurrent").put("progress").put("secId")
                .put("pDate").put("remark").put("fileStatus").put("createUid").put("createTime")
                .put("createUname").put("state").put("stateTime").put("imgShow").put("pName");
        IPage<ProjectProfileModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectProfileModel> data = baseMapper.getProjectProfileList(page,params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProjectProfileListByPid(String pId) {
        if (ValidateUtil.isEmpty(pId)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ProjectProfileModel> data = baseMapper.getProjectProfileListByPid(pId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProjectProfileById(String id) {
        if (ValidateUtil.isEmpty(id)){
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ProjectProfileModel data = baseMapper.getProjectProfileById(id);

        return BaseResponse.ok(data);
    }


}
