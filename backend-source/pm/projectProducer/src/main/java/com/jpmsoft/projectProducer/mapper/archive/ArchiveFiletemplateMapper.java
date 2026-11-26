package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Archive.ArchiveFiletemplateResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFiletemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ArchiveFiletemplateMapper extends BaseMapper<ArchiveFiletemplate> {

    @Select("select mn.*,ft.T_Name as \"tName\" from  Archive_FileTemplate mn left join  SYS_FileTemplate ft on ft.T_Code=mn.At_T_Code where mn.Ad_Id=#{adId} order by mn.At_Order asc")
    List<ArchiveFiletemplateResponse> getArchiveFiletemplateByAdId(@Param("adId") String adId);


    List<ArchiveFiletemplateResponse> getArchiveFiletemplateByTCodes(@Param("tCodes") List<String> tCodes, @Param("adId") String adId);
}
