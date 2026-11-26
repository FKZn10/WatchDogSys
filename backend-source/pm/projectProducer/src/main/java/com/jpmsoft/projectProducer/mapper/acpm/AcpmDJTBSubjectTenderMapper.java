package com.jpmsoft.projectProducer.mapper.acpm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBSubjectTender;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBSubjectTenderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:59
 */

public interface AcpmDJTBSubjectTenderMapper extends BaseMapper<AcpmDJTBSubjectTender> {


    public List<AcpmDJTBSubjectTenderVo> getByDaId(@Param("daId") String daId);

    List<AcpmDJTBSubjectTenderVo> getBathByDaIds(@Param("daIds") List<String> daIds);
}
