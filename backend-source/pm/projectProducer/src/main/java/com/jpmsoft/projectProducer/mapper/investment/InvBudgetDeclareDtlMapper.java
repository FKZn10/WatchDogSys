package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.response.InvestmentControlLedgerHBModel;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetConPayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvBudgetDeclareDtlMapper extends BaseMapper<InvBudgetDeclareDtl> {


    void addBatch(@Param("entitys")List<InvBudgetDeclareDtl> details);

    List<BudgetConPayVo> getInvBudgetConPayInfo(@Param("pId") String pId);

    @Select("Select * from Inv_Budget_Declare_Dtl where Bd_Id=(select Bd_Id from Inv_Budget_Declare_Dtl where Bdd_Id=#{bddId})")
    List<InvBudgetDeclareDtl> getAllByBddId(@Param("bddId") String bddId);

    @Select("Select bdd.Bdd_Id from Inv_Budget_Declare_Dtl bdd left join Inv_Budget_Declare bd on bd.Bd_Id=bdd.Bd_Id where bd.P_Id=#{pId}")
    List<String> getAllByPId(@Param("pId") String pId);

    List<InvBudgetDeclareDtl> getInvBudgetDeclareDtlByPid(@Param("pId") String pId, @Param("typeList") List<String> typeList);

    List<InvBudgetDeclareDtl> getInvTypeBudgetDeclareDtlByBdIdList(@Param("bdIdList") List<String> bdIdList);

    List<InvestmentControlLedgerHBModel> getInvBudgetDeclareDtlDetailsByPid(@Param("pId") String pId);
}
