package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_FlowRule")
public class FlowRule {
    @TableId(type = IdType.INPUT)
    public String ruleid;
    public String rulename;
    public String ruletype;
    public String rulecondition;
    public String rulesystype;
    public String ruleflowid;
    public String rulebeginactid;
    public String ruleendactid;

    public String getRuleid() {
        return ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid;
    }

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public String getRuletype() {
        return ruletype;
    }

    public void setRuletype(String ruletype) {
        this.ruletype = ruletype;
    }

    public String getRulecondition() {
        return rulecondition;
    }

    public void setRulecondition(String rulecondition) {
        this.rulecondition = rulecondition;
    }

    public String getRulesystype() {
        return rulesystype;
    }

    public void setRulesystype(String rulesystype) {
        this.rulesystype = rulesystype;
    }

    public String getRuleflowid() {
        return ruleflowid;
    }

    public void setRuleflowid(String ruleflowid) {
        this.ruleflowid = ruleflowid;
    }

    public String getRulebeginactid() {
        return rulebeginactid;
    }

    public void setRulebeginactid(String rulebeginactid) {
        this.rulebeginactid = rulebeginactid;
    }

    public String getRuleendactid() {
        return ruleendactid;
    }

    public void setRuleendactid(String ruleendactid) {
        this.ruleendactid = ruleendactid;
    }
}
