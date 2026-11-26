package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionUser;
import com.jpmsoft.jpbpm.pojo.vo.userCVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysPositionUserMapper extends BaseMapper<SysPositionUser> {


    List<userCVo> getUserIdByDid(@Param("uId") String uId, @Param("teId") String teId);


    List<String> getDidByDid(@Param("uId") String uId);
    List<String> getUserIdByDids(@Param("uIdSql") String uIdSql,@Param("teId") String teId);

    List<userCVo> getUserIdByPid(@Param("pId") String pId,@Param("teId") String teId);


    List<Map<String, Object>> selectUidByUid(@Param("params") Map<String, Object> params);

    List<userCVo> getUserObjByDids(@Param("dids") List<String> dids);

}
