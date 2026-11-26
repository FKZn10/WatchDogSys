package com.jpmsoft.jpbpm.util;


import java.time.format.DateTimeParseException;
import java.time.ZonedDateTime;
import java.time.Instant;
import com.jpmsoft.common.utils.ValidateUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * 流程服务客户端规则判断类
 */
public class JPMCheckCondition {

    private static String[] strData;
    private static Map<String, Object> strDataNew;
    private static List<String> checkField;

    /**
     * 检查线上条件的单位信息
     */
    public static Boolean InspectionFieldConditions(String RuleCondition, Map<String, Object> condition, List<String> checkFieldStr) {
        if (RuleCondition == null || RuleCondition.equals("")) {
            return true;
        } else {
            if (condition == null) {
                return false;
            } else {
                strDataNew = condition;
                checkField = checkFieldStr;
                RuleCondition = RuleCondition.replaceAll("\n", "");
                RuleCondition=RuleCondition.replaceAll(" and "," 【and】 ");
                RuleCondition=RuleCondition.replaceAll(" or "," 【or】 ");
                return Postfix_Value_New(Postfix(RuleCondition + " #"), "1");  // 返回表达式计算结果
            }
        }
    }

    /**
     * 计算系统内部规则表达式结果
     *
     * @param RuleCondition 规则条件表达式
     * @param condition     规则表达式对应的真实值,多个值以|号分隔（比如 money=99|基建手续类型=土地预审
     * @return 表达式结果 布尔型
     */
    public static Boolean CheckCondition(String RuleCondition, Map<String, Object> condition) {
        if (RuleCondition == null || RuleCondition.equals("")) {
            return true;
        } else {
            if (condition == null) {
                return false;
            } else {
                strDataNew = condition;
                RuleCondition = RuleCondition.replaceAll("\n", "");
                RuleCondition=RuleCondition.replaceAll(" and "," 【and】 ");
                RuleCondition=RuleCondition.replaceAll(" or "," 【or】 ");
                return Postfix_Value_New(Postfix(RuleCondition + " #"), "0");  // 返回表达式计算结果
            }

        }
    }

    private static Boolean Postfix_Value_New(List<String> postCondition, String checkType) {
        Stack<Boolean> stack = new Stack<Boolean>();    // 操作数栈
        int i = 0;
        Boolean x1, x2;
        while (!postCondition.get(i).equals("#")) {
            if (!postCondition.get(i).equals("(") && !postCondition.get(i).equals(")") && !postCondition.get(i).equals("【and】") && !postCondition.get(i).equals("【or】") && !postCondition.get(i).equals("#")) {
                stack.push(GetResultNew(postCondition.get(i), checkType));
                i++;
            } else if (postCondition.get(i).equals("【and】")) {
                x2 = stack.pop();
                x1 = stack.pop();
                stack.push(x1 && x2);
                i++;
            } else if (postCondition.get(i).equals("【or】")) {
                x2 = stack.pop();
                x1 = stack.pop();
                stack.push(x1 || x2);
                i++;
            }
        }
        if (stack.size() == 0)
            return false;
        return stack.peek();
    }


    private static Boolean GetResultNew(String str, String checkType) {
        //根据业务判断操作数的值
        String[] temps = str.split("[<|>|<=|>=|=|!=|⊇|!⊇]");
        String[] strList = Arrays.stream(temps).filter(x -> ValidateUtil.isEmpty(x) != true).toArray(String[]::new);
        String strOP = str.replaceAll(strList[0], "").replaceAll(strList[1], "").trim();
        if (checkType.equals("1")) {
            if (checkField.contains(strList[0])) {
                switch (strList[0]) {
                    case "【task】":
                        return true;
                    case "【count】":
                        return true;
                    case "【jbrjs】":
                        return GetStrContainsResultnew(strOP, strList[1], "【jbrjs】");    // 经办人角色判断条件
                    case "【sprjs】":
                        return GetStrContainsResultnew(strOP, strList[1], "【sprjs】");    // 审批人角色判断条件
                    default:
                        return GetResultNew(strList[0], strOP, strList[1]);        // 流程条件判断结果为字符串是否相等的通用函数
                }
            } else {
                return true;
            }

        } else {
            switch (strList[0]) {
                case "【task】":
                    return true;
                case "【count】":
                    return true;
                case "【jbrjs】":
                    return GetStrContainsResultnew(strOP, strList[1], "【jbrjs】");    // 经办人角色判断条件
                case "【sprjs】":
                    return GetStrContainsResultnew(strOP, strList[1], "【sprjs】");    // 审批人角色判断条件
                default:
                    return GetResultNew(strList[0], strOP, strList[1]);        // 流程条件判断结果为字符串是否相等的通用函数
            }
        }

    }


    /// <summary>
    /// 流程条件判断结果为字符串是否相等
    /// </summary>
    /// <param name="key">参数</param>
    /// <param name="op">判断操作符</param>
    /// <param name="data">条件表达式定义时的值</param>
    /// <returns></returns>
    private static Boolean GetResultNew(String key, String op, String data) {
        key = key.replace("\n", "");
        Object olddata = strDataNew.get(key);
        if (olddata == null) {
            return false;
        } else {
            if (olddata instanceof Integer) {
                Integer c_money = Integer.valueOf(data);
                switch (op) {
                    case "<":
                        return ((Integer) olddata).compareTo(c_money) == -1;
                    case ">":
                        return ((Integer) olddata).compareTo(c_money) == 1;
                    case "=":
                        return ((Integer) olddata).compareTo(c_money) == 0;
                    case "<=":
                        return ((Integer) olddata).compareTo(c_money) < 1;
                    case ">=":
                        return ((Integer) olddata).compareTo(c_money) > -1;
                    case "!=":
                        return ((Integer) olddata).compareTo(c_money) != 0;
                    default:
                        return false;
                }
            } else if ((olddata instanceof Date)||timeJudgment(olddata)) {
                Date c_money = DateUtil.String2Date(data, "yyyy-MM-dd");
                ZonedDateTime  cDate=ZonedDateTime.parse(olddata+"");
                // 将ZonedDateTime转换为Instant
                Instant instant = cDate.toInstant();

                // 将Instant转换为java.util.Date
                Date date = Date.from(instant);
                switch (op) {
                    case "<":
                        return date.compareTo(c_money) == -1;
                    case ">":
                        return date.compareTo(c_money) == 1;
                    case "=":
                        return date.compareTo(c_money) == 0;
                    case "<=":
                        return date.compareTo(c_money) < 1;
                    case ">=":
                        return date.compareTo(c_money) > -1;
                    case "!=":
                        return date.compareTo(c_money) != 0;
                    default:
                        return false;
                }
            } else if (olddata instanceof Double) {
                Double c_money = Double.valueOf(Long.valueOf(data));
                switch (op) {
                    case "<":
                        return ((Double) olddata).compareTo(c_money) == -1;
                    case ">":
                        return ((Double) olddata).compareTo(c_money) == 1;
                    case "=":
                        return ((Double) olddata).compareTo(c_money) == 0;
                    case "<=":
                        return ((Double) olddata).compareTo(c_money) < 1;
                    case ">=":
                        return ((Double) olddata).compareTo(c_money) > -1;
                    case "!=":
                        return ((Double) olddata).compareTo(c_money) != 0;
                    default:
                        return false;
                }
            } else if (olddata instanceof Float) {
                Float c_money = Float.valueOf(Long.valueOf(data));
                switch (op) {
                    case "<":
                        return ((Float) olddata).compareTo(c_money) == -1;
                    case ">":
                        return ((Float) olddata).compareTo(c_money) == 1;
                    case "=":
                        return ((Float) olddata).compareTo(c_money) == 0;
                    case "<=":
                        return ((Float) olddata).compareTo(c_money) < 1;
                    case ">=":
                        return ((Float) olddata).compareTo(c_money) > -1;
                    case "!=":
                        return ((Float) olddata).compareTo(c_money) != 0;
                    default:
                        return false;
                }

            } else if (olddata instanceof BigDecimal) {
                BigDecimal c_money = BigDecimal.valueOf(Long.valueOf(data));
                switch (op) {
                    case "<":
                        return ((BigDecimal) olddata).compareTo(c_money) == -1;
                    case ">":
                        return ((BigDecimal) olddata).compareTo(c_money) == 1;
                    case "=":
                        return ((BigDecimal) olddata).compareTo(c_money) == 0;
                    case "<=":
                        return ((BigDecimal) olddata).compareTo(c_money) < 1;
                    case ">=":
                        return ((BigDecimal) olddata).compareTo(c_money) > -1;
                    case "!=":
                        return ((BigDecimal) olddata).compareTo(c_money) != 0;
                    default:
                        return false;
                }
            } else if (olddata instanceof Boolean) {
                switch (op) {
                    case "=":

                        return olddata ==Boolean.valueOf(data);
                    case "!=":
                        return olddata != Boolean.valueOf(data);
                    default:
                        return false;
                }
            } else if (olddata instanceof String) {
                olddata =((String) olddata).replaceAll("\\s+", "");
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(-?([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
                java.util.regex.Matcher match = pattern.matcher(olddata.toString());
                if (match.find()) {
                    if (((String) olddata).length() < 14) {
                        BigDecimal c_money = BigDecimal.valueOf(Long.valueOf(data));
                        BigDecimal olddatanew = new BigDecimal(olddata.toString());
                        switch (op) {
                            case "<":
                                return olddatanew.compareTo(c_money) == -1;
                            case ">":
                                return olddatanew.compareTo(c_money) == 1;
                            case "=":
                                return olddatanew.compareTo(c_money) == 0;
                            case "<=":
                                return olddatanew.compareTo(c_money) < 1;
                            case ">=":
                                return olddatanew.compareTo(c_money) > -1;
                            case "!=":
                                return olddatanew.compareTo(c_money) != 0;
                            default:
                                return false;
                        }
                    } else {
                        if (((String) olddata).contains(",")) {
                            String[] olddatas = ((String) olddata).split(",");
                            if (olddatas.length > 0) {
                                int count = Arrays.stream(olddatas).filter(n -> n.equals(data)).toArray().length;
                                switch (op) {
                                    case "=":
                                        return count>0;
                                    case "!=":
                                        return count==0;
                                    case "⊇":
                                        return ((String) olddata).contains(data);
                                    case "!⊇":
                                        return !((String) olddata).contains(data);
                                    default:
                                        return false;
                                }
                            }
                        }
                        switch (op) {
                            case "=":
                                return olddata.equals(data);
                            case "!=":
                                return !olddata.equals(data);
                            case "⊇":
                                return ((String) olddata).contains(data);
                            case "!⊇":
                                return !((String) olddata).contains(data);
                            default:
                                return false;
                        }


                    }
                } else {
                    if (((String) olddata).contains(",")) {
                        String[] olddatas =((String) olddata).split(",");
                        if (olddatas.length > 0) {
                            int count = Arrays.stream(olddatas).filter(n -> n.equals(data)).toArray().length;
                            switch (op) {
                                case "=":
                                    return count>0;
                                case "!=":
                                    return count==0;
                                case "⊇":
                                    return ((String) olddata).contains(data);
                                case "!⊇":
                                    return !((String) olddata).contains(data);
                                default:
                                    return false;
                            }
                        }
                    }
                    switch (op) {
                        case "=":
                            return olddata.equals(data);
                        case "!=":
                            return !olddata.equals(data);
                        case "⊇":
                            return ((String) olddata).contains(data);
                        case "!⊇":
                            return !((String) olddata).contains(data);
                        default:
                            return false;
                    }

                   /* Date newdata =DateUtil.String2Date(olddata.toString(),"yyyy-MM-dd");
                    Date c_money =DateUtil.String2Date(data,"yyyy-MM-dd");
                    if(newdata!=null){
                        switch (op) {
                            case "<":
                                return newdata.compareTo(c_money) == -1;
                            case ">":
                                return newdata.compareTo(c_money) == 1;
                            case "=":
                                return newdata.compareTo(c_money) == 0;
                            case "<=":
                                return newdata.compareTo(c_money) < 1;
                            case ">=":
                                return newdata.compareTo(c_money) > -1;
                            case "!=":
                                return newdata.compareTo(c_money) != 0;
                            default:
                                return false;
                        }
                    }else{*/

                    //}
                }
            } else {
                return false;
            }
        }
    }

    private static  Boolean timeJudgment(Object data){
        try {
            ZonedDateTime.parse(data+"");
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    private static Boolean GetStrContainsResultnew(String op, String data, String strSys) {
        Object olddata = strDataNew.get(strSys);
        if (olddata != null) {
            switch (op) {
                case "=":
                    return data.contains(olddata.toString()) == true;
                case "!=":
                    return data.contains(olddata.toString()) == false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }


//    public static Boolean CheckCondition(String RuleCondition, String condition) {
//        if(RuleCondition==null||RuleCondition.equals("")){
//            return true;
//        }else{
//            if(condition==null){
//                return false;
//            }else{
//                String[] data = condition.toLowerCase(Locale.ENGLISH).split("\\|");
//                strData = Arrays.stream(data).filter(x -> ValidateUtil.isEmpty(x) != true).toArray(String[]::new);
//                return Postfix_Value(Postfix(RuleCondition + " #"));  // 返回表达式计算结果
//            }
//        }
//    }

    private static List<String> Postfix(String condition) {
        String[] preCondition = condition.split(" ");
        List<String> postCondition = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push("#");
        int i = 0;
        while (!preCondition[i].equals("#")) {
            Integer lenget = preCondition[i].length();
            if (lenget > 0) {
                if (!preCondition[i].equals("(") && !preCondition[i].equals(")") && !preCondition[i].toLowerCase(Locale.ENGLISH).equals("【and】") && !preCondition[i].toLowerCase(Locale.ENGLISH).equals("【or】")) {
                    postCondition.add(preCondition[i]);
                } else if (preCondition[i].equals("("))    // 遇到“（”不用比较直接入栈
                {
                    stack.push(preCondition[i]);
                } else if (preCondition[i].equals(")"))    // 遇到右括号将其对应左括号后的操作符（操作符栈中的）全部写入后缀表达式
                {
                    while (!stack.peek().equals("(")) {
                        postCondition.add(stack.pop());
                    }
                    stack.pop();    // 将“（”出栈，后缀表达式中不含小括号
                } else if (preCondition[i].toLowerCase(Locale.ENGLISH).equals("【and】") || preCondition[i].toLowerCase(Locale.ENGLISH).equals("【or】")) {
                    // 当前的操作符小于等于栈顶操作符的优先级时，将栈顶操作符写入到后缀表达式，重复此过程
                    while (Priority(preCondition[i]) <= Priority(stack.peek())) {
                        postCondition.add(stack.pop());
                    }
                    stack.push(preCondition[i]); // 当前操作符优先级大于栈顶操作符的优先级，将该操作符入栈
                }
            }
            i++;

        }
        while (stack.size() > 0) // 将所有还没有出栈的操作符加入后缀表达式
        {
            postCondition.add(stack.pop());
        }
        return postCondition;
    }

    /// <summary>
    /// 判断是否为操作符
    /// </summary>
    /// <param name="op">操作符</param>
    /// <returns>操作符优先级</returns>
    private static int Priority(String op) {
        switch (op) {
            case "#":
                return -1;
            case "(":
                return 0;
            case "【and】":
            case "【or】":
                return 1;
            default:
                return -1;
        }
    }

    /// <summary>
    /// 计算后缀表达式的值
    /// </summary>
    /// <param name="postCondition">后缀表达式</param>
    /// <returns>返回布尔值</returns>
    private static Boolean Postfix_Value(List<String> postCondition) {
        Stack<Boolean> stack = new Stack<Boolean>();    // 操作数栈
        int i = 0;
        Boolean x1, x2;
        while (!postCondition.get(i).equals("#")) {
            if (!postCondition.get(i).equals("(") && !postCondition.get(i).equals(")") && !postCondition.get(i).equals("【and】") && !postCondition.get(i).equals("【or】") && !postCondition.get(i).equals("#")) {
                stack.push(GetResult(postCondition.get(i)));
                i++;
            } else if (postCondition.get(i).equals("【and】")) {
                x2 = stack.pop();
                x1 = stack.pop();
                stack.push(x1 && x2);
                i++;
            } else if (postCondition.get(i).equals("【or】")) {
                x2 = stack.pop();
                x1 = stack.pop();
                stack.push(x1 || x2);
                i++;
            }
        }
        if (stack.size() == 0)
            return false;
        return stack.peek();
    }

    /// <summary>
    /// 判断表达式操作数的值（true 或者 false）(比如条件表达式money>100 此时根据业务实际值判断此表达式的真伪)
    /// </summary>
    /// <param name="str">表达式操作数字符串（类似 money>100）</param>
    /// <returns>返回表达式的值（true、false）</returns>
    private static Boolean GetResult(String str) {
        //根据业务判断操作数的值
        String[] temps = str.split("[<|>|<=|>=|=|!=]");
        String[] strList = Arrays.stream(temps).filter(x -> ValidateUtil.isEmpty(x) != true).toArray(String[]::new);
        String strOP = str.replaceAll(strList[0], "").replaceAll(strList[1], "").trim();
        switch (strList[0]) {
            case "【task】":
                return true;
            case "【count】":
                return true;
            case "报销金额":
                return GetMoneyResult(strOP, strList[1], "报销金额");    // 金额判断条件
            case "合同金额":
                return GetMoneyResult(strOP, strList[1], "合同金额");    // 金额判断条件
            case "计划总投资":
                return GetMoneyResult(strOP, strList[1], "计划总投资");    //
            case "金额":
                return GetMoneyResult(strOP, strList[1], "金额");
            case "签证金额":
                return GetMoneyResult(strOP, strList[1], "签证金额");    //
            case "变更金额":
                return GetMoneyResult(strOP, strList[1], "变更金额");   //
            case "变更金额比例":
                return GetMoneyResult(strOP, strList[1], "变更金额比例"); //
            case "涉及金额":
                return GetMoneyEqualResult(strOP, strList[1], "涉及金额"); //
            case "合同签订金额":
                return GetMoneyResult(strOP, strList[1], "合同签订金额");    // 合同签订金额判断条件
            case "变更估算金额":
                return GetMoneyResult(strOP, strList[1], "变更估算金额");    // 变更估算金额判断条件
            case "项目来源":
                return GetStringEqualResult(strOP, strList[1], "项目来源");    // 项目来源
            case "项目实施单位":
                return GetStringEqualResult(strOP, strList[1], "项目实施单位");    // 项目实施单位判断条件
            case "承办部门":
                return GetStringEqualResult(strOP, strList[1], "承办部门");    // 承办部门
            case "校区":
                return GetStringEqualResult(strOP, strList[1], "校区");    // 校区
            case "改变使用功能":
                return GetStringEqualResult(strOP, strList[1], "改变使用功能");    //  改变使用功能
            case "改变间隔结构":
                return GetStringEqualResult(strOP, strList[1], "改变间隔结构");    //  改变间隔功能
            case "【jbrjs】":
                return GetStrContainsResult(strOP, strList[1], "[jbrjs]");    // 经办人角色判断条件
            case "【sprjs】":
                return GetStrContainsResult(strOP, strList[1], "[sprjs]");    // 审批人角色判断条件
            case "【result】":
                return GetStringEqualResult(strOP, strList[1], strList[0]);    // 活动结果条件
            case "无价材料询价总价":
                return GetMoneyResult(strOP, strList[1], "无价材料询价总价");    // 合同签订金额判断条件
            case "议题类型":
                return GetStringEqualResult(strOP, strList[1], "议题类型");    // 承办部门
            case "招标公告总金额": {
                return true;        // 招标公告总金额
            }
            default:
                return GetResult(strList[0], strOP, strList[1]);        // 流程条件判断结果为字符串是否相等的通用函数
        }
    }


    /**
     * 字符串是否包含
     *
     * @param op
     * @param data
     * @param strSys
     * @return
     */
    private static Boolean GetStrContainsResult(String op, String data, String strSys) {
        switch (op) {
            case "=":
                return data.contains(GetData(strSys)) == true;
            case "!=":
                return data.contains(GetData(strSys)) == false;
            default:
                return false;
        }
    }


    /**
     * 比较字符串是否相等
     *
     * @param op
     * @param data
     * @param strSys
     * @return
     */
    private static Boolean GetStringEqualResult(String op, String data, String strSys) {
        String b_dw = GetData(strSys);
        if (ValidateUtil.isEmpty(b_dw)) {
            return false;
        }
        switch (op) {
            case "=":
                return b_dw.equals(data);
            case "!=":
                return !b_dw.equals(data);
            default:
                return false;
        }
    }

    /**
     * 比较金额是否相等
     *
     * @param op
     * @param data
     * @param strSys
     * @return
     */
    private static Boolean GetMoneyEqualResult(String op, String data, String strSys) {
        BigDecimal b_money;
        String bMoney = GetData(strSys);
        if (ValidateUtil.isEmpty(bMoney))
            b_money = BigDecimal.ZERO;
        else
            b_money = BigDecimal.valueOf(Long.valueOf(bMoney));
        BigDecimal c_money = BigDecimal.valueOf(Long.valueOf(data));
        switch (op) {
            case "=":
                return b_money.compareTo(c_money) == 0;
            case "!=":
                return b_money.compareTo(c_money) != 0;
            default:
                return false;
        }
    }

    /// <summary>
    /// 返回合同金额条件判断时的操作数的值
    /// </summary>
    /// <param name="op">比较符</param>
    /// <param name="data">条件表达式定义时的业务实际值（比如 money>100 时的100)</param>
    /// <returns>返回操作数和业务实际值比较后的结果（比如 moeny是否大于100）</returns>
    private static Boolean GetMoneyResult(String op, String data, String strSys) {
        BigDecimal b_money;
        String bMoney = GetData(strSys);
        if (ValidateUtil.isEmpty(bMoney))
            b_money = BigDecimal.ZERO;
        else
            b_money = BigDecimal.valueOf(Long.valueOf(bMoney));

        BigDecimal c_money = BigDecimal.valueOf(Long.valueOf(data));
        switch (op) {
            case "<":
                return b_money.compareTo(c_money) == -1;
            case ">":
                return b_money.compareTo(c_money) == 1;
            case "=":
                return b_money.compareTo(c_money) == 0;
            case "<=":
                return b_money.compareTo(c_money) < 1;
            case ">=":
                return b_money.compareTo(c_money) > -1;
            case "!=":
                return b_money.compareTo(c_money) != 0;
            default:
                return false;
        }
    }


    /// <summary>
    /// 根据实际操作数字符串返回具体业务数据
    /// </summary>
    /// <param name="strSys">操作数字符串（类似 money）</param>
    /// <returns>返回业务具体数值字符串(类似 100)</returns>
    private static String GetData(String strSys) {
        String[] p = new String[]{"="};
        for (int i = 0; i < strData.length; i++) {
            if (strData[i].contains(strSys)) {
                String[] data = strData[i].split("=");
                String[] arr = Arrays.stream(data).filter(x -> ValidateUtil.isEmpty(x) != true).toArray(String[]::new);
                if (arr.length > 1)
                    return arr[1];
                else
                    return "";
            }
        }
        return null;
    }

    /// <summary>
    /// 流程条件判断结果为字符串是否相等
    /// </summary>
    /// <param name="key">参数</param>
    /// <param name="op">判断操作符</param>
    /// <param name="data">条件表达式定义时的值</param>
    /// <returns></returns>
    private static Boolean GetResult(String key, String op, String data) {
        String olddata = GetData(key);
        if (ValidateUtil.isEmpty(olddata)) {
            return false;
        }
        switch (op) {
            case "=":

                return olddata.equals(data);
            case "!=":
                return !olddata.equals(data);
            default:
                return false;
        }
    }

}
