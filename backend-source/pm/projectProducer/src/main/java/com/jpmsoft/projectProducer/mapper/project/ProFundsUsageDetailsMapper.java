package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundsUsageDetails;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:43
 */
public interface ProFundsUsageDetailsMapper extends BaseMapper<ProFundsUsageDetails> {

    @Delete("delete from Pro_Funds_Usage_Details where Cpl_Id in (select Cpl_Id from Pro_Construction_Plan_List where Pcp_Id=#{pcpId})")
    Integer delProFundsUsageDetailsByPcpId(@Param("pcpId") String pcpId);

    @Select("select * from Pro_Funds_Usage_Details where Cpl_Id in (select Cpl_Id from Pro_Construction_Plan_List where Pcp_Id=#{pcpId}) order by Fud_Sort ASC")
    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByPcpId(@Param("pcpId") String pcpId);

    @Select("select * from Pro_Funds_Usage_Details where Cpl_Id in (select Cpl_Id from Pro_Year_Plan_Select where Ycp_Id=#{ycpId}) order by Fud_Sort ASC")
    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByYcpId(@Param("ycpId") String ycpId);

    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByCplIds(@Param("cplIds") List<String> cplIds);
}
