package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.peInfo.PeMeetingApprovalRes;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:12
 */

public interface PeMeetingApprovalMapper extends BaseMapper<PeMeetingApproval> {

    List<PeMeetingApprovalRes> getPeMeetingApprovalList(@Param("params") Map<String, Object> params);

    Integer getPeMeetingApprovalCount(@Param("params") Map<String, Object> params);

    PeMeetingApprovalRes getPeMeetingApprovalById(@Param("maId") String maId);

    Integer updatePeStateByMaId(@Param("params") Map<String, Object> params);
}
