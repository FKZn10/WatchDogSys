package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysAnswerLib;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 9:55
 */
public interface SysAnswerLibMapper extends BaseMapper<SysAnswerLib> {


    List<SysAnswerLibDTO> getSysAnswerLibList(@Param("page") IPage<SysAnswerLibDTO> page, @Param("params") Map<String, Object> params);

    List<SysAnswerLibDTO> searchProblem(@Param("words") List<String> words,@Param("problem") String problem
            ,@Param("alType") String alType,@Param("alKyes") String  alKyes);
}
