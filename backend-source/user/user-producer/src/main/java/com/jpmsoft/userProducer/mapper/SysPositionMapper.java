package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysPosition;
import com.jpmsoft.userProducer.pojo.vo.SysPositionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface SysPositionMapper extends BaseMapper<SysPosition> {


    List<SysPositionVo> GetPositionList(@Param("start") Integer start, @Param("size") Integer size, @Param("pStatus") String pStatus
            , @Param("dId") String dId, @Param("pName") String pName, @Param("sortType") String sortType, @Param("sortFiled") String sortFiled);

    Integer GetPositionListCount(@Param("pStatus") String pStatus, @Param("dId") String dId, @Param("pName") String pName);

    @Update("update SYS_Position set P_Duty=false where D_Id=#{dId}")
    Integer disableAllSyncDepDuty(@Param("dId") String dId);

    List<Map<String, String>> getPositionByRole(@Param("roleId") String roleId);
}
