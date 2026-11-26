package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.ChangeMeetingNotification;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:01
 */
public interface ChangeMeetingNotificationMapper extends BaseMapper<ChangeMeetingNotification> {


    @Select("select cmn.* from Change_Meeting_Notification cmn where cmn.state=7 and cmn.Cmn_Cs_State=1 and cmn.Cmn_End_Time<#{time} and cmn.id not in " +
            "(select ec.Cmn_Id from Con_EC ec where ec.Ec_State!=3 and ec.Cmn_Id is not null )")
    List<ChangeMeetingNotification> getTimeOutMeeting(@Param("time") String time);


    @Update("update Change_Meeting_Notification set Cmn_Cs_State=#{scState} where id=#{id}")
    Integer apply2UnlockMeeting(@Param("id") String id, @Param("scState") Integer scState);
}
