package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SecBiddingProjectMapper extends BaseMapper<SecBiddingProject> {

    Integer haveSBPCount(@Param("pIds") List<String> pIds, @Param("bpId") String bpId);

    @Select("select P_Id from Sec_Bidding_Project where Bp_Id=#{bpId}")
    List<String> getSecBiddingProjectPids(@Param("bpId") String bpId);

    @Select("select * from Sec_Bidding_Project where Bp_Id=#{bpId}")
    List<SecBiddingProject> getSecBiddingProject(@Param("bpId") String bpId);
}
