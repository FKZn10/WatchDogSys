package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.Archive;
import com.jpmsoft.fileUpload.pojo.vo.AutoArchiveDirectoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ArchiveMapper extends BaseMapper<Archive> {

    /**
     * 自动归档
     * 获取所有的目录（ID对应的文档编码）
     *
     * @return
     */
    @Select("select aft.at_t_code as \"tCode\",ad.ad_id as \"adId\",ad.ad_name as \"adName\",ad.Ad_Parent_Id as \"adParentId\"   " +
            " from Archive_Directory ad  " +
            " left join Archive_FileTemplate aft on ad.Ad_Id=aft.Ad_Id " +
            " where ad.Ad_State=1 ")
    List<AutoArchiveDirectoryVo> getAutoArchiveDirectory();

    /**
     * 自动归档
     * 获取所有的目录（ID对应的文档编码）
     * 行政档案目录
     *
     * @return
     */
    @Select("select aft.at_t_code as \"tCode\",ad.ad_id as \"adId\",ad.Ad_Parent_Id as \"adParentId\"   " +
            " from Archive_Directory ad  " +
            " left join Archive_FileTemplate aft on ad.Ad_Id=aft.Ad_Id " +
            " where ad.Ad_State=1 and ad.Ad_Type='02'")
    List<AutoArchiveDirectoryVo> getAutoXzArchiveDirectory();

    @Select("select P_Id from Sec_Project where Sec_Id=#{secId} limit 1")
    String getPidBySecId(@Param("secId") String secId);

    @Select("select Con_Project_Id from Con_Contract where Con_Id=#{conId}")
    String getPidByConId(@Param("conId") String conId);

    Integer refreshApStateBath(@Param("apIds") List<String> apIds, @Param("apState") Integer apState);

    Integer autoArchiveProCpRprFileInputCode();
}
