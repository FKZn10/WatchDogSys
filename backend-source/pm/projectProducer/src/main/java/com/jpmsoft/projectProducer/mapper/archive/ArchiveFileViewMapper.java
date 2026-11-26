package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFileView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ArchiveFileViewMapper extends BaseMapper<ArchiveFileView> {

    List<ArchiveFileViewModel> getArchiveFileViewList(@Param("page") IPage<ArchiveFileViewModel> page, @Param("params") Map<String, Object> params);


    @Select("select afv.Afv_Id from Archive_File_View  afv left join Archive_File af on afv.Af_Id=af.Af_Id where af.Ap_Id=#{apId}")
    List<String>  getArchiveFileViewIdByApId(@Param("apId")  String apId);
}
