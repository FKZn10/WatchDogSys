package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysFileMapper extends BaseMapper<SysFile> {

    @Update("update  SYS_File set FileFlowStatus=#{state} where FileID=#{fileId}")
    Integer onlyUpdateState(@Param("state") Integer state, @Param("fileId") String fileId);

    Integer onlyUpdateStateBath(@Param("state") Integer state, @Param("fileIds") List<String> fileIds);
    @Select("select * from SYS_File where FileID=#{primaryId}")
    SysFile selectByFileId(@Param("primaryId")String primaryId);
    FileTemplateTree getFileTemplateByTCode(@Param("tCode") String tCode);
}
