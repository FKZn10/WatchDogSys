package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvBudgetDeclareResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclare;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetCompanyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface InvBudgetDeclareMapper extends BaseMapper<InvBudgetDeclare> {

    List<InvBudgetDeclareResponse> getInvBudgetDeclareList(@Param("page") IPage<InvBudgetDeclareResponse> page, @Param("params") Map<String, Object> params);


    InvBudgetDeclareResponse getInvBudgetDeclareByBdId(@Param("bdId") String bdId);

    InvBudgetDeclareResponse getInvBudgetDeclareAndDtlByPid(@Param("pId") String pId);

    BudgetCompanyVo getBudgetCompanyByPid(@Param("pId") String pId);

    List<InvBudgetDeclareResponse> getInvBudgetDeclareByPid(@Param("pId") String pId,@Param("bdState") Integer bdState,@Param("bdTypeList") List<String> bdTypeList);

    @Select("select * from  Inv_Budget_Declare_Dtl where Bdd_Id=#{id}")
    InvBudgetDeclareDtl getInvBudgetDeclareDtoById(@Param("id") String id);
}
