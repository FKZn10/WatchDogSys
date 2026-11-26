package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionSourcesFunds;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:44
 */
public interface ProConstructionSourcesFundsMapper extends BaseMapper<ProConstructionSourcesFunds> {

    @Delete("delete from Pro_Construction_Sources_Funds where Cpl_Id in (select Cpl_Id from Pro_Construction_Plan_List where Pcp_Id=#{pcpId})")
    Integer delProConstructionSourcesFundsByPcpId(@Param("pcpId") String pcpId);

    @Select("select * from Pro_Construction_Sources_Funds where Cpl_Id in (select Cpl_Id from Pro_Construction_Plan_List where Pcp_Id=#{pcpId}) order by Fs_Sort ASC")
    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByPcpId(@Param("pcpId") String pcpId);

    @Select("select * from Pro_Construction_Sources_Funds where Cpl_Id in (select Cpl_Id from Pro_Year_Plan_Select where Ycp_Id=#{ycpId}) order by Fs_Sort ASC")
    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByYcpId(@Param("ycpId") String ycpId);

    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByCplIds(@Param("cplIds") List<String> cplIds);
}
