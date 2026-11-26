package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.jpmsoft.jpbpm.pojo.entity.SecInfo;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import com.jpmsoft.jpbpm.pojo.vo.ConContractVo;
import java.util.List;
import java.util.Map;

@Mapper
public interface RiskWarnMapper extends BaseMapper<RiskWarn> {


    @Select(" select P_ID,P_Name from  Pre_Project where  P_ID in('${pIds}');")
    List<PreProject> getProjectNameByPid(@Param("pIds") String pIds);

    @Select(" select Con_Id,Con_Name from  Con_Contract where Con_Id in ('${cIds}') ;")
    List<ConContractVo> getContractNameByCid(@Param("cIds") String cIds);

    @Select(" select Sec_Id,Sec_Name from  Sec_Info where Sec_Id in ('${SecIds}');")
    List<SecInfo> getsecInfoNameByPid(@Param("SecIds") String SecIds);


}
