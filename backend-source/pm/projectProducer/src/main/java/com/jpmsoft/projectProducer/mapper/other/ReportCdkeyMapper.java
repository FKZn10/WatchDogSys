package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.ReportCdkey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 10:51
 */
public interface ReportCdkeyMapper extends BaseMapper<ReportCdkey> {


    @Select(" select count(*) from questionnaire01 where  user_phone=#{phone}")
    Integer checkPhone(@Param("phone") String phone);


    @Select(" select count(*) from questionnaire01 where  Student_Num=#{studentNum}")
    Integer checkStudentNum(@Param("studentNum") String studentNum);


    @Select(" select count(*) from questionnaire01")
    Integer size();

}

