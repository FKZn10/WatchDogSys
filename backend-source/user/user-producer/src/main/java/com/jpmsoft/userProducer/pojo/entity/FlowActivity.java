package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@TableName("SYS_FlowActivity")
public class FlowActivity {

    @TableId(type = IdType.INPUT)
    public String actid;
    public String actname;
    public String actcode;
    public String acttype;
    public String actresult;
    public Integer actorder;
    public String actbacktype;
    public String actsubflow;
    public String actflowid;
    public String actcancoordination;
    public String actcancarboncopy;
    public String actusertype;
    public String actccusertype;
    public String actcusertype;
    public String actcanfinish;
    public String actcountersigntitle;
    public String actremark;
    public String actuserway;
    public String actot;
    public String actotTo;
    public Integer actotH;
    public String actotA;
    public String actotFa;
    public String actpress;
    public Integer actpressSh;
    public Integer actpressEh;
    public Integer actpressRate;
    public String actpressT;
    public String actpressW;
    public String actnoticeS;
    public String actnoticeSt;
    public String actnoticeN;
    public String actnoticeNt;
    public String actnoticeH;
    public String actnoticeHt;
    public String actnoticeW;
    public String actzrrid;
    public String acttemplatecode;
    public Double acthour;
    public String acttemplatecodeo;
    public String actmrbl;
    public String actjsfs;
    public String activityuserid;
    public String actoutdeletebtn;
    public String actoutfinishbtn;
    public String actcoderole;
    public String actallowconcurrency;
    public String actconcurrentmerger;
    public String actusershowtype;
    public String actuserfilter;
    public String actlabsend;
    public String actlabsave;
    public String actlabback;
    public String actlabfinish;
    public String actlabco;
    public String actlabcc;
    public String actcarboncopyWr;
    public String actcarboncopyT;
    public String actcarboncopyCt;
    public String actcarboncopyRole;
    public String actcarboncopyPost;
    public String actcarboncopyDepartment;
    public String actcarboncopyUser;
    public String actcarboncopySql;
    public String actcarboncopyFrontNode;
    public Boolean actAllowMultiple;
    public Boolean actOpinionRequired;
    public String actViewPreCondition;
    public String actSelectAll;

    //跨系统审批
    public String actSysUrl;
    public String actSysIden;
    public String actRequestType;

    //业务办理提醒信息
    public String actBusinessStartNode;
    public String actBusinessEndNode;
    public String actBusinessHandleTips;
    public String actBusinessMenuCode;


    public String actConcurrencyType;
    public String actConcurrencyEnd;

    //自动完成
    public String actAutoCompletion;
    public Float actTriggertTime;
    public String actAutoMultiple;
    public String actAutoCompletionK;
    public String actAutoCompletionT;
    public String actAutoDefaultOpinion;
    public String actAutoTriggerField;
    public String actFormItemSet;
    public String actAllowWithdrawal;
    public String actAllowVoid;
    public String actReplaceOperator;
    public String actDefaultOpinion;
    public String actSkipStatus;
    public String actBusinessDescription;
    public String actApprovalHiddenType;
    public String actApprovalHiddenCondition;
    public String actUserPost;


    public String actApprovalNodeNumber;
    public String actRequestMethod;
    public String actInterfaceUrl;
    public String actRequestParameter;


    public String actUserAppoint;
    public String actFilterFields;
    public String actFormField;
    public String actApplyPreState;
    public String actSmsPost;
    public String actSmsDepartment;
    public String actSmsRole;
    public String actSmsUser;
    public String actSmsSql;
    public String actSmsRequestMethod;
    public String actSmsInterfaceUrl;
    public String actSmsRequestParameter;
    public String actSmsTem;
    public String actMassageType;
    public String actMassageS;
    public String actMassageSt;
    public String actMassageN;
    public String actMassageNt;
    public String actMassageH;
    public String actMassageSql;
    public String actMassageRequestMethod;
    public String actMassageInterfaceUrl;
    public String actMassageRequestParameter;
    public String actMassageTem;
    public String actMassagePost;
    public String actMassageUser;
    public String actMassageRole;
    public String actMassageDepartment;
    public String actGatewayAccess;
    public String actApprovalDrop;
    public String actNotSkipPrompt;
    public String actCustomizeSkipPrompt;
    //是否允许退回当前节点
    public String actReturnCurrent;
    //允许允许退回当前节点的节点
    public String actReturnCurrentOk;
    //节点状态权限
    public String actNodeStatusPower;
    //审批记录权限
    public String actRecordPower;
    //审批记录权限范围
    public String actRecordPowerScope;
    //指定节点
    public String actRecordPowerNode;

    public String getActRequestMethod() {
        return actRequestMethod;
    }

    public void setActRequestMethod(String actRequestMethod) {
        this.actRequestMethod = actRequestMethod;
    }

    public String getActInterfaceUrl() {
        return actInterfaceUrl;
    }

    public void setActInterfaceUrl(String actInterfaceUrl) {
        this.actInterfaceUrl = actInterfaceUrl;
    }

    public String getActRequestParameter() {
        return actRequestParameter;
    }

    public void setActRequestParameter(String actRequestParameter) {
        this.actRequestParameter = actRequestParameter;
    }

    public String getActApprovalNodeNumber() {
        return actApprovalNodeNumber;
    }

    public void setActApprovalNodeNumber(String actApprovalNodeNumber) {
        this.actApprovalNodeNumber = actApprovalNodeNumber;
    }

    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getActcode() {
        return actcode;
    }

    public void setActcode(String actcode) {
        this.actcode = actcode;
    }

    public String getActtype() {
        return acttype;
    }

    public void setActtype(String acttype) {
        this.acttype = acttype;
    }

    public String getActresult() {
        return actresult;
    }

    public void setActresult(String actresult) {
        this.actresult = actresult;
    }

    public Integer getActorder() {
        return actorder;
    }

    public void setActorder(Integer actorder) {
        this.actorder = actorder;
    }

    public String getActbacktype() {
        return actbacktype;
    }

    public void setActbacktype(String actbacktype) {
        this.actbacktype = actbacktype;
    }

    public String getActsubflow() {
        return actsubflow;
    }

    public void setActsubflow(String actsubflow) {
        this.actsubflow = actsubflow;
    }

    public String getActflowid() {
        return actflowid;
    }

    public void setActflowid(String actflowid) {
        this.actflowid = actflowid;
    }

    public String getActcancoordination() {
        return actcancoordination;
    }

    public void setActcancoordination(String actcancoordination) {
        this.actcancoordination = actcancoordination;
    }

    public String getActcancarboncopy() {
        return actcancarboncopy;
    }

    public void setActcancarboncopy(String actcancarboncopy) {
        this.actcancarboncopy = actcancarboncopy;
    }

    public String getActusertype() {
        return actusertype;
    }

    public void setActusertype(String actusertype) {
        this.actusertype = actusertype;
    }

    public String getActccusertype() {
        return actccusertype;
    }

    public void setActccusertype(String actccusertype) {
        this.actccusertype = actccusertype;
    }

    public String getActcusertype() {
        return actcusertype;
    }

    public void setActcusertype(String actcusertype) {
        this.actcusertype = actcusertype;
    }

    public String getActcanfinish() {
        return actcanfinish;
    }

    public void setActcanfinish(String actcanfinish) {
        this.actcanfinish = actcanfinish;
    }

    public String getActcountersigntitle() {
        return actcountersigntitle;
    }

    public void setActcountersigntitle(String actcountersigntitle) {
        this.actcountersigntitle = actcountersigntitle;
    }

    public String getActremark() {
        return actremark;
    }

    public void setActremark(String actremark) {
        this.actremark = actremark;
    }

    public String getActuserway() {
        return actuserway;
    }

    public void setActuserway(String actuserway) {
        this.actuserway = actuserway;
    }

    public String getActot() {
        return actot;
    }

    public void setActot(String actot) {
        this.actot = actot;
    }

    public String getActotTo() {
        return actotTo;
    }

    public void setActotTo(String actotTo) {
        this.actotTo = actotTo;
    }

    public Integer getActotH() {
        return actotH;
    }

    public void setActotH(Integer actotH) {
        this.actotH = actotH;
    }

    public String getActotA() {
        return actotA;
    }

    public void setActotA(String actotA) {
        this.actotA = actotA;
    }

    public String getActotFa() {
        return actotFa;
    }

    public void setActotFa(String actotFa) {
        this.actotFa = actotFa;
    }

    public String getActpress() {
        return actpress;
    }

    public void setActpress(String actpress) {
        this.actpress = actpress;
    }

    public Integer getActpressSh() {
        return actpressSh;
    }

    public void setActpressSh(Integer actpressSh) {
        this.actpressSh = actpressSh;
    }

    public Integer getActpressEh() {
        return actpressEh;
    }

    public void setActpressEh(Integer actpressEh) {
        this.actpressEh = actpressEh;
    }

    public Integer getActpressRate() {
        return actpressRate;
    }

    public void setActpressRate(Integer actpressRate) {
        this.actpressRate = actpressRate;
    }

    public String getActpressT() {
        return actpressT;
    }

    public void setActpressT(String actpressT) {
        this.actpressT = actpressT;
    }

    public String getActpressW() {
        return actpressW;
    }

    public void setActpressW(String actpressW) {
        this.actpressW = actpressW;
    }

    public String getActnoticeS() {
        return actnoticeS;
    }

    public void setActnoticeS(String actnoticeS) {
        this.actnoticeS = actnoticeS;
    }

    public String getActnoticeSt() {
        return actnoticeSt;
    }

    public void setActnoticeSt(String actnoticeSt) {
        this.actnoticeSt = actnoticeSt;
    }

    public String getActnoticeN() {
        return actnoticeN;
    }

    public void setActnoticeN(String actnoticeN) {
        this.actnoticeN = actnoticeN;
    }

    public String getActnoticeNt() {
        return actnoticeNt;
    }

    public void setActnoticeNt(String actnoticeNt) {
        this.actnoticeNt = actnoticeNt;
    }

    public String getActnoticeH() {
        return actnoticeH;
    }

    public void setActnoticeH(String actnoticeH) {
        this.actnoticeH = actnoticeH;
    }

    public String getActnoticeHt() {
        return actnoticeHt;
    }

    public void setActnoticeHt(String actnoticeHt) {
        this.actnoticeHt = actnoticeHt;
    }

    public String getActnoticeW() {
        return actnoticeW;
    }

    public void setActnoticeW(String actnoticeW) {
        this.actnoticeW = actnoticeW;
    }

    public String getActzrrid() {
        return actzrrid;
    }

    public void setActzrrid(String actzrrid) {
        this.actzrrid = actzrrid;
    }

    public String getActtemplatecode() {
        return acttemplatecode;
    }

    public void setActtemplatecode(String acttemplatecode) {
        this.acttemplatecode = acttemplatecode;
    }

    public Double getActhour() {
        return acthour;
    }

    public void setActhour(Double acthour) {
        this.acthour = acthour;
    }

    public String getActtemplatecodeo() {
        return acttemplatecodeo;
    }

    public void setActtemplatecodeo(String acttemplatecodeo) {
        this.acttemplatecodeo = acttemplatecodeo;
    }

    public String getActmrbl() {
        return actmrbl;
    }

    public void setActmrbl(String actmrbl) {
        this.actmrbl = actmrbl;
    }

    public String getActjsfs() {
        return actjsfs;
    }

    public void setActjsfs(String actjsfs) {
        this.actjsfs = actjsfs;
    }

    public String getActivityuserid() {
        return activityuserid;
    }

    public void setActivityuserid(String activityuserid) {
        this.activityuserid = activityuserid;
    }

    public String getActoutdeletebtn() {
        return actoutdeletebtn;
    }

    public void setActoutdeletebtn(String actoutdeletebtn) {
        this.actoutdeletebtn = actoutdeletebtn;
    }

    public String getActoutfinishbtn() {
        return actoutfinishbtn;
    }

    public void setActoutfinishbtn(String actoutfinishbtn) {
        this.actoutfinishbtn = actoutfinishbtn;
    }

    public String getActcoderole() {
        return actcoderole;
    }

    public void setActcoderole(String actcoderole) {
        this.actcoderole = actcoderole;
    }

    public String getActallowconcurrency() {
        return actallowconcurrency;
    }

    public void setActallowconcurrency(String actallowconcurrency) {
        this.actallowconcurrency = actallowconcurrency;
    }

    public String getActconcurrentmerger() {
        return actconcurrentmerger;
    }

    public void setActconcurrentmerger(String actconcurrentmerger) {
        this.actconcurrentmerger = actconcurrentmerger;
    }

    public String getActusershowtype() {
        return actusershowtype;
    }

    public void setActusershowtype(String actusershowtype) {
        this.actusershowtype = actusershowtype;
    }

    public String getActuserfilter() {
        return actuserfilter;
    }

    public void setActuserfilter(String actuserfilter) {
        this.actuserfilter = actuserfilter;
    }

    public String getActlabsend() {
        return actlabsend;
    }

    public void setActlabsend(String actlabsend) {
        this.actlabsend = actlabsend;
    }

    public String getActlabsave() {
        return actlabsave;
    }

    public void setActlabsave(String actlabsave) {
        this.actlabsave = actlabsave;
    }

    public String getActlabback() {
        return actlabback;
    }

    public void setActlabback(String actlabback) {
        this.actlabback = actlabback;
    }

    public String getActlabfinish() {
        return actlabfinish;
    }

    public void setActlabfinish(String actlabfinish) {
        this.actlabfinish = actlabfinish;
    }

    public String getActlabco() {
        return actlabco;
    }

    public void setActlabco(String actlabco) {
        this.actlabco = actlabco;
    }

    public String getActlabcc() {
        return actlabcc;
    }

    public void setActlabcc(String actlabcc) {
        this.actlabcc = actlabcc;
    }

    public String getActcarboncopyWr() {
        return actcarboncopyWr;
    }

    public void setActcarboncopyWr(String actcarboncopyWr) {
        this.actcarboncopyWr = actcarboncopyWr;
    }

    public String getActcarboncopyT() {
        return actcarboncopyT;
    }

    public void setActcarboncopyT(String actcarboncopyT) {
        this.actcarboncopyT = actcarboncopyT;
    }

    public String getActcarboncopyCt() {
        return actcarboncopyCt;
    }

    public void setActcarboncopyCt(String actcarboncopyCt) {
        this.actcarboncopyCt = actcarboncopyCt;
    }

    public String getActcarboncopyRole() {
        return actcarboncopyRole;
    }

    public void setActcarboncopyRole(String actcarboncopyRole) {
        this.actcarboncopyRole = actcarboncopyRole;
    }

    public String getActcarboncopyPost() {
        return actcarboncopyPost;
    }

    public void setActcarboncopyPost(String actcarboncopyPost) {
        this.actcarboncopyPost = actcarboncopyPost;
    }

    public String getActcarboncopyDepartment() {
        return actcarboncopyDepartment;
    }

    public void setActcarboncopyDepartment(String actcarboncopyDepartment) {
        this.actcarboncopyDepartment = actcarboncopyDepartment;
    }

    public String getActcarboncopyUser() {
        return actcarboncopyUser;
    }

    public void setActcarboncopyUser(String actcarboncopyUser) {
        this.actcarboncopyUser = actcarboncopyUser;
    }

    public String getActcarboncopySql() {
        return actcarboncopySql;
    }

    public void setActcarboncopySql(String actcarboncopySql) {
        this.actcarboncopySql = actcarboncopySql;
    }

    public String getActSysUrl() {
        return actSysUrl;
    }

    public void setActSysUrl(String actSysUrl) {
        this.actSysUrl = actSysUrl;
    }

    public String getActSysIden() {
        return actSysIden;
    }

    public void setActSysIden(String actSysIden) {
        this.actSysIden = actSysIden;
    }

    public String getActRequestType() {
        return actRequestType;
    }

    public void setActRequestType(String actRequestType) {
        this.actRequestType = actRequestType;
    }

    public String getActcarboncopyFrontNode() {
        return actcarboncopyFrontNode;
    }

    public void setActcarboncopyFrontNode(String actcarboncopyFrontNode) {
        this.actcarboncopyFrontNode = actcarboncopyFrontNode;
    }

    public Boolean getActAllowMultiple() {
        return actAllowMultiple;
    }

    public void setActAllowMultiple(Boolean actAllowMultiple) {
        this.actAllowMultiple = actAllowMultiple;
    }

    public Boolean getActOpinionRequired() {
        return actOpinionRequired;
    }

    public void setActOpinionRequired(Boolean actOpinionRequired) {
        this.actOpinionRequired = actOpinionRequired;
    }

    public String getActViewPreCondition() {
        return actViewPreCondition;
    }

    public void setActViewPreCondition(String actViewPreCondition) {
        this.actViewPreCondition = actViewPreCondition;
    }

    public String getActBusinessStartNode() {
        return actBusinessStartNode;
    }

    public void setActBusinessStartNode(String actBusinessStartNode) {
        this.actBusinessStartNode = actBusinessStartNode;
    }

    public String getActBusinessEndNode() {
        return actBusinessEndNode;
    }

    public void setActBusinessEndNode(String actBusinessEndNode) {
        this.actBusinessEndNode = actBusinessEndNode;
    }

    public String getActBusinessHandleTips() {
        return actBusinessHandleTips;
    }

    public void setActBusinessHandleTips(String actBusinessHandleTips) {
        this.actBusinessHandleTips = actBusinessHandleTips;
    }

    public String getActBusinessMenuCode() {
        return actBusinessMenuCode;
    }

    public void setActBusinessMenuCode(String actBusinessMenuCode) {
        this.actBusinessMenuCode = actBusinessMenuCode;
    }

    public String getActConcurrencyType() {
        return actConcurrencyType;
    }

    public void setActConcurrencyType(String actConcurrencyType) {
        this.actConcurrencyType = actConcurrencyType;
    }

    public String getActConcurrencyEnd() {
        return actConcurrencyEnd;
    }

    public void setActConcurrencyEnd(String actConcurrencyEnd) {
        this.actConcurrencyEnd = actConcurrencyEnd;
    }

    public String getActAutoCompletion() {
        return actAutoCompletion;
    }

    public void setActAutoCompletion(String actAutoCompletion) {
        this.actAutoCompletion = actAutoCompletion;
    }

    public Float getActTriggertTime() {
        return actTriggertTime;
    }

    public void setActTriggertTime(Float actTriggertTime) {
        this.actTriggertTime = actTriggertTime;
    }

    public String getActAutoMultiple() {
        return actAutoMultiple;
    }

    public void setActAutoMultiple(String actAutoMultiple) {
        this.actAutoMultiple = actAutoMultiple;
    }

    public String getActAutoCompletionK() {
        return actAutoCompletionK;
    }

    public void setActAutoCompletionK(String actAutoCompletionK) {
        this.actAutoCompletionK = actAutoCompletionK;
    }

    public String getActAutoCompletionT() {
        return actAutoCompletionT;
    }

    public void setActAutoCompletionT(String actAutoCompletionT) {
        this.actAutoCompletionT = actAutoCompletionT;
    }

    public String getActAutoDefaultOpinion() {
        return actAutoDefaultOpinion;
    }

    public void setActAutoDefaultOpinion(String actAutoDefaultOpinion) {
        this.actAutoDefaultOpinion = actAutoDefaultOpinion;
    }

    public String getActAutoTriggerField() {
        return actAutoTriggerField;
    }

    public void setActAutoTriggerField(String actAutoTriggerField) {
        this.actAutoTriggerField = actAutoTriggerField;
    }

    public String getActFormItemSet() {
        return actFormItemSet;
    }

    public void setActFormItemSet(String actFormItemSet) {
        this.actFormItemSet = actFormItemSet;
    }

    public String getActAllowWithdrawal() {
        return actAllowWithdrawal;
    }

    public void setActAllowWithdrawal(String actAllowWithdrawal) {
        this.actAllowWithdrawal = actAllowWithdrawal;
    }

    public String getActAllowVoid() {
        return actAllowVoid;
    }

    public void setActAllowVoid(String actAllowVoid) {
        this.actAllowVoid = actAllowVoid;
    }

    public String getActReplaceOperator() {
        return actReplaceOperator;
    }

    public void setActReplaceOperator(String actReplaceOperator) {
        this.actReplaceOperator = actReplaceOperator;
    }

    public String getActDefaultOpinion() {
        return actDefaultOpinion;
    }

    public void setActDefaultOpinion(String actDefaultOpinion) {
        this.actDefaultOpinion = actDefaultOpinion;
    }

    public String getActSkipStatus() {
        return actSkipStatus;
    }

    public void setActSkipStatus(String actSkipStatus) {
        this.actSkipStatus = actSkipStatus;
    }

    public String getActBusinessDescription() {
        return actBusinessDescription;
    }

    public void setActBusinessDescription(String actBusinessDescription) {
        this.actBusinessDescription = actBusinessDescription;
    }

    public String getActApprovalHiddenType() {
        return actApprovalHiddenType;
    }

    public void setActApprovalHiddenType(String actApprovalHiddenType) {
        this.actApprovalHiddenType = actApprovalHiddenType;
    }

    public String getActApprovalHiddenCondition() {
        return actApprovalHiddenCondition;
    }

    public void setActApprovalHiddenCondition(String actApprovalHiddenCondition) {
        this.actApprovalHiddenCondition = actApprovalHiddenCondition;
    }

    public String getActUserPost() {
        return actUserPost;
    }

    public void setActUserPost(String actUserPost) {
        this.actUserPost = actUserPost;
    }


    public String getActSelectAll() {
        return actSelectAll;
    }

    public void setActSelectAll(String actSelectAll) {
        this.actSelectAll = actSelectAll;
    }


    public String getActUserAppoint() {
        return actUserAppoint;
    }

    public void setActUserAppoint(String actUserAppoint) {
        this.actUserAppoint = actUserAppoint;
    }

    public String getActFilterFields() {
        return actFilterFields;
    }

    public void setActFilterFields(String actFilterFields) {
        this.actFilterFields = actFilterFields;
    }

    public String getActFormField() {
        return actFormField;
    }

    public void setActFormField(String actFormField) {
        this.actFormField = actFormField;
    }

    public String getActApplyPreState() {
        return actApplyPreState;
    }

    public void setActApplyPreState(String actApplyPreState) {
        this.actApplyPreState = actApplyPreState;
    }

    public String getActSmsPost() {
        return actSmsPost;
    }

    public void setActSmsPost(String actSmsPost) {
        this.actSmsPost = actSmsPost;
    }

    public String getActSmsDepartment() {
        return actSmsDepartment;
    }

    public void setActSmsDepartment(String actSmsDepartment) {
        this.actSmsDepartment = actSmsDepartment;
    }

    public String getActSmsRole() {
        return actSmsRole;
    }

    public void setActSmsRole(String actSmsRole) {
        this.actSmsRole = actSmsRole;
    }

    public String getActSmsUser() {
        return actSmsUser;
    }

    public void setActSmsUser(String actSmsUser) {
        this.actSmsUser = actSmsUser;
    }

    public String getActSmsSql() {
        return actSmsSql;
    }

    public void setActSmsSql(String actSmsSql) {
        this.actSmsSql = actSmsSql;
    }

    public String getActSmsRequestMethod() {
        return actSmsRequestMethod;
    }

    public void setActSmsRequestMethod(String actSmsRequestMethod) {
        this.actSmsRequestMethod = actSmsRequestMethod;
    }

    public String getActSmsInterfaceUrl() {
        return actSmsInterfaceUrl;
    }

    public void setActSmsInterfaceUrl(String actSmsInterfaceUrl) {
        this.actSmsInterfaceUrl = actSmsInterfaceUrl;
    }

    public String getActSmsRequestParameter() {
        return actSmsRequestParameter;
    }

    public void setActSmsRequestParameter(String actSmsRequestParameter) {
        this.actSmsRequestParameter = actSmsRequestParameter;
    }


    public String getActSmsTem() {
        return actSmsTem;
    }

    public void setActSmsTem(String actSmsTem) {
        this.actSmsTem = actSmsTem;
    }

    public String getActMassageType() {
        return actMassageType;
    }

    public void setActMassageType(String actMassageType) {
        this.actMassageType = actMassageType;
    }

    public String getActMassageS() {
        return actMassageS;
    }

    public void setActMassageS(String actMassageS) {
        this.actMassageS = actMassageS;
    }

    public String getActMassageSt() {
        return actMassageSt;
    }

    public void setActMassageSt(String actMassageSt) {
        this.actMassageSt = actMassageSt;
    }

    public String getActMassageN() {
        return actMassageN;
    }

    public void setActMassageN(String actMassageN) {
        this.actMassageN = actMassageN;
    }

    public String getActMassageNt() {
        return actMassageNt;
    }

    public void setActMassageNt(String actMassageNt) {
        this.actMassageNt = actMassageNt;
    }

    public String getActMassageH() {
        return actMassageH;
    }

    public void setActMassageH(String actMassageH) {
        this.actMassageH = actMassageH;
    }

    public String getActMassageSql() {
        return actMassageSql;
    }

    public void setActMassageSql(String actMassageSql) {
        this.actMassageSql = actMassageSql;
    }

    public String getActMassageRequestMethod() {
        return actMassageRequestMethod;
    }

    public void setActMassageRequestMethod(String actMassageRequestMethod) {
        this.actMassageRequestMethod = actMassageRequestMethod;
    }

    public String getActMassageInterfaceUrl() {
        return actMassageInterfaceUrl;
    }

    public void setActMassageInterfaceUrl(String actMassageInterfaceUrl) {
        this.actMassageInterfaceUrl = actMassageInterfaceUrl;
    }

    public String getActMassageRequestParameter() {
        return actMassageRequestParameter;
    }

    public void setActMassageRequestParameter(String actMassageRequestParameter) {
        this.actMassageRequestParameter = actMassageRequestParameter;
    }

    public String getActMassageTem() {
        return actMassageTem;
    }

    public void setActMassageTem(String actMassageTem) {
        this.actMassageTem = actMassageTem;
    }

    public String getActMassagePost() {
        return actMassagePost;
    }

    public void setActMassagePost(String actMassagePost) {
        this.actMassagePost = actMassagePost;
    }

    public String getActMassageUser() {
        return actMassageUser;
    }

    public void setActMassageUser(String actMassageUser) {
        this.actMassageUser = actMassageUser;
    }

    public String getActMassageRole() {
        return actMassageRole;
    }

    public void setActMassageRole(String actMassageRole) {
        this.actMassageRole = actMassageRole;
    }

    public String getActMassageDepartment() {
        return actMassageDepartment;
    }

    public void setActMassageDepartment(String actMassageDepartment) {
        this.actMassageDepartment = actMassageDepartment;
    }

    public String getActGatewayAccess() {
        return actGatewayAccess;
    }

    public void setActGatewayAccess(String actGatewayAccess) {
        this.actGatewayAccess = actGatewayAccess;
    }

    public String getActApprovalDrop() {
        return actApprovalDrop;
    }

    public void setActApprovalDrop(String actApprovalDrop) {
        this.actApprovalDrop = actApprovalDrop;
    }

    public String getActNotSkipPrompt() {
        return actNotSkipPrompt;
    }

    public void setActNotSkipPrompt(String actNotSkipPrompt) {
        this.actNotSkipPrompt = actNotSkipPrompt;
    }

    public String getActCustomizeSkipPrompt() {
        return actCustomizeSkipPrompt;
    }

    public void setActCustomizeSkipPrompt(String actCustomizeSkipPrompt) {
        this.actCustomizeSkipPrompt = actCustomizeSkipPrompt;
    }

    public String getActReturnCurrent() {
        return actReturnCurrent;
    }

    public void setActReturnCurrent(String actReturnCurrent) {
        this.actReturnCurrent = actReturnCurrent;
    }

    public String getActReturnCurrentOk() {
        return actReturnCurrentOk;
    }

    public void setActReturnCurrentOk(String actReturnCurrentOk) {
        this.actReturnCurrentOk = actReturnCurrentOk;
    }

    public String getActNodeStatusPower() {
        return actNodeStatusPower;
    }

    public void setActNodeStatusPower(String actNodeStatusPower) {
        this.actNodeStatusPower = actNodeStatusPower;
    }

    public String getActRecordPower() {
        return actRecordPower;
    }

    public void setActRecordPower(String actRecordPower) {
        this.actRecordPower = actRecordPower;
    }

    public String getActRecordPowerScope() {
        return actRecordPowerScope;
    }

    public void setActRecordPowerScope(String actRecordPowerScope) {
        this.actRecordPowerScope = actRecordPowerScope;
    }

    public String getActRecordPowerNode() {
        return actRecordPowerNode;
    }

    public void setActRecordPowerNode(String actRecordPowerNode) {
        this.actRecordPowerNode = actRecordPowerNode;
    }
}
