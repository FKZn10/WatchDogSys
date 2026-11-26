package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApproval;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProApprovalStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreProApprovalMapper extends BaseMapper<PreProApproval> {


    List<PreProApprovalModel> getPreProApprovalList(@Param("page") IPage<PreProApprovalModel> page, @Param("params") Map<String, Object> params);

    PreProApprovalStatisticsVo getPaTotalInvSum(@Param("params") Map<String, Object> params);

    PreProApprovalModel getPreProApprovalById(@Param("paId") String paId);

    PreProApprovalModel getPreProApprovalByPid(@Param("pId") String pId);
}
