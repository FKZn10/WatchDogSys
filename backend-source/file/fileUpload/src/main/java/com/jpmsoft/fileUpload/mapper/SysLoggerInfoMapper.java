package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysLoggerInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;


public interface SysLoggerInfoMapper extends BaseMapper<SysLoggerInfo> {


    /**
     * 嵌套select是为了兼容mysql 5.7版本
     *
     * @param num
     * @return
     */
    @Delete("delete from SYS_LoggerInfo where L_ID in (select a.* from (select  L_ID from SYS_LoggerInfo order by L_RequestTime asc  limit ${num}) a )")
    Integer delLoggerInfoByNum(@Param("num") Integer num);

}
