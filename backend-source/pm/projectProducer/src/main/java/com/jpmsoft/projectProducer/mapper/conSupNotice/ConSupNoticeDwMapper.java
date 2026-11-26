package com.jpmsoft.projectProducer.mapper.conSupNotice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeDw;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/6 14:43
 */
public interface ConSupNoticeDwMapper extends BaseMapper<ConSupNoticeDw> {


    @Select("select c1.C_Name as sndCompanyName,mn.* from Con_Sup_Notice_Dw mn left join  sys_company c1 on c1.C_Id=mn.Snd_Company_Id where mn.Sn_Id=#{snId}")
    List<ConSupNoticeDw> getBySnId(@Param("snId") String snId);
}
