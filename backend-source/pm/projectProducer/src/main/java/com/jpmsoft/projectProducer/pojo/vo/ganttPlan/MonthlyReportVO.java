package com.jpmsoft.projectProducer.pojo.vo.ganttPlan;

import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.EndPlanAnnex;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.OutProjectVo;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


/**
 * @author: 李翔
 * @date: 2025/5/26 17:46
 */
@Data
public class MonthlyReportVO implements Serializable {
    private String year;
    private String month;
    private int projectNum;
    private int nodeNum;

    private int completeNum;//竣工验收
    private int completeRecordsNum;//竣工备案
    private int pRemoveNum;//工程移交
    private int accountSubmit;//决算上报

    private int nodeNeedFinishNum;
    private int nodeFinishNum;
    private double nodeFinishRate;

    //竣工验收
    private int completeNeedFinishNum;
    private int completeFinishNum;
    private double completeFinishRate;
    private int completeOutTimeNum;
    private int completeOutPlanNum;
    private HashMap<String, List<OutProjectVo>> completeOutTimeProjectVOList;
    private HashMap<String, List<OutProjectVo>> completeOutPlanProjectVOList;



    //竣工备案
    private int completeRecordsNeedFinishNum;
    private int completeRecordsFinishNum;
    private double completeRecordsFinishRate;
    private int completeRecordsOutTimeNum;
    private int completeRecordsOutPlanNum;
    private HashMap<String, List<OutProjectVo>> completeRecordsOutTimeProjectVOList;
    private HashMap<String, List<OutProjectVo>> completeRecordsOutPlanProjectVOList;

    //工程移交
    private int pRemoveNeedFinishNum;
    private int  pRemoveFinishNum;
    private double pRemoveFinishRate;
    private int pRemoveOutTimeNum;
    private int pRemoveOutPlanNum;
    private HashMap<String, List<OutProjectVo>> pRemoveOutTimeProjectVOList;
    private HashMap<String, List<OutProjectVo>> pRemoveOutPlanProjectVOList;

    //决算上报
    private int accountSubmitNeedFinishNum;
    private int accountSubmitFinishNum;
    private double accountSubmitFinishRate;
    private int accountSubmitOutTimeNum;
    private int accountSubmitOutPlanNum;
    private HashMap<String, List<OutProjectVo>> accountSubmitOutTimeProjectVOList;
    private HashMap<String, List<OutProjectVo>> accountSubmitOutPlanProjectVOList;

    private List<EndPlanAnnex> endPlanInAnnex;
    private List<EndPlanAnnex> endPlanOutAnnex;

    private EndPlanAnnex total;

}
