package com.jpmsoft.projectProducer.mapper.acpm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBDocumentsApproval;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBDocumentsApprovalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:55
 */

public interface AcpmDJTBDocumentsApprovalMapper extends BaseMapper<AcpmDJTBDocumentsApproval> {


    List<AcpmDJTBDocumentsApprovalVo> getList(@Param("params") Map<String, Object> params);

    Integer getCount(@Param("params") Map<String, Object> params);

    AcpmDJTBDocumentsApprovalVo getById(@Param("id") String id);
}
