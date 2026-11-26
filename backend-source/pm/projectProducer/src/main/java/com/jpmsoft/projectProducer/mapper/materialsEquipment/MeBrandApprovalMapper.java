package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandApproval;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandApprovalReportVo;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandApprovalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:27
 */
public interface MeBrandApprovalMapper extends BaseMapper<MeBrandApproval> {

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    Integer getMeBrandApprovalReviewsCount(@Param("pId") String pId, @Param("conId") String conId);

    List<MeBrandApprovalVo> getMeBrandApprovalList(@Param("page") IPage<MeBrandApprovalVo> page,
                                                   @Param("params") Map<String, Object> params);

    MeBrandApprovalVo getMeBrandApprovalById(@Param("id") String id);

    MeBrandApprovalReportVo getMeBrandApprovalReportById(@Param("id")String id);
}
