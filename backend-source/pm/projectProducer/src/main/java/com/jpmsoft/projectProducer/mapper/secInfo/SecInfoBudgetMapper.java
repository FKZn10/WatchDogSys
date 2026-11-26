package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetConSecModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudget;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SecInfoBudgetMapper extends BaseMapper<SecInfoBudget> {


    @Select("select mn.* from  Sec_Info_Budget mn left join  Sec_Info sec on sec.Sec_Id=mn.Sec_Id where sec.Te_Id=#{teId} and  mn.Sib_Cost=#{sibCost} and sec.Sec_Flow_State!=3")
    List<SecInfoBudgetModel> getSecInfoBudgetListWithTeId(@Param("sibCost") String sibCost,@Param("teId")  String teId);

    @Select("select mn.* from  Sec_Info_Budget mn left join  Sec_Info sec on sec.Sec_Id=mn.Sec_Id where mn.Sib_Cost=#{sibCost} and sec.Sec_Flow_State!=3")
    List<SecInfoBudgetModel> getSecInfoBudgetList(@Param("sibCost") String sibCost);

    @Select("select mn.* from  Sec_Info_Budget mn left join  Sec_Info sec on sec.Sec_Id=mn.Sec_Id where sec.Sec_Id=#{secId} and sec.Sec_Flow_State!=3")
    List<SecInfoBudgetModel> getSecInfoBudgetListBySecId(@Param("secId") String secId);


    List<InvBudgetConSecModel> getSecInfoBudgetById(@Param("sibCost") String sibCost);

    @Select("select mn.* from  Sec_Info_Budget_Declare_Dtl mn left join  Sec_Info sec on sec.Sec_Id=mn.Sec_Id left join Sec_Project sp on sp.Sec_Id=mn.Sec_Id  where sp.P_ID=#{pId} and sec.Sec_Flow_State!=3")
    List<SecInfoBudgetDeclareDtl> getSecInfoBudgetListByPId(@Param("pId") String pId);


}
