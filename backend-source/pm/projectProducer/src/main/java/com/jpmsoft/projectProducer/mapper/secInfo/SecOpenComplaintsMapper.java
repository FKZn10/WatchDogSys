package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenComplaints;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecOpenComplaintsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 13:50
 */
public interface SecOpenComplaintsMapper extends BaseMapper<SecOpenComplaints> {

    SecOpenComplaintsVo getSecOpenComplaintsById(@Param("id") String id);


    List<SecOpenComplaintsVo> getSecOpenComplaintsList(@Param("page") IPage<SecOpenComplaintsVo> page,
                                                       @Param("params") Map<String, Object> params);
}
