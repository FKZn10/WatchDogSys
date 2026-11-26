package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Archive.ArchiveBorrowResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveBorrow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ArchiveBorrowMapper extends BaseMapper<ArchiveBorrow> {


    List<ArchiveBorrowResponse> getArchiveBorrowList(@Param("params") Map<String, Object> params);

    Integer getArchiveBorrowListCount(@Param("params") Map<String, Object> params);

    ArchiveBorrowResponse getArchiveBorrowById(@Param("abId") String abId);

    @Select("select ab.Ab_Id from Archive_Borrow  ab left join Archive_File af on ab.Af_Id=af.Af_Id where af.Ap_Id=#{apId}")
    List<String> getArchiveBorrowIdByApId(@Param("apId")  String apId);
}
