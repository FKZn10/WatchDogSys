package com.jpmsoft.jpbpm.util;

import com.jpmsoft.jpbpm.pojo.entity.FlowRule;
import com.jpmsoft.common.utils.ValidateUtil;

import java.util.*;

public class FlowSubmitRules {

    public static int current_count = 0;      // 当前步骤剩余活动数
    public static int all_count = 0;      // 规则后继步骤所有的前驱活动数
    private static FlowRule rule = null;

    public static void setSYSFlowRuleEntity(FlowRule r) {
        rule = r;
    }

    public static Boolean CheckSysCondition(String RuleCondition) {
        if(rule.getRulecondition()==null||rule.getRulecondition().equals(""))
            return  true;

        return Postfix_Value(Postfix(rule.getRulecondition() + " #"));  // 返回表达式计算结果
    }

    public static List<String> Postfix(String condition) {
        condition = condition.replaceAll("\n", "");
        condition=condition.replaceAll(" and "," 【and】 ");
        condition=condition.replaceAll(" or "," 【or】 ");
        String[] preCondition = condition.toLowerCase(Locale.ENGLISH).split(" ");
        List<String> postCondition = new ArrayList<>();
        Stack<String> stack = new Stack<String>();
        stack.push("#");
        int i = 0;
        while (!preCondition[i].equals("#")) {
            Integer lenget=preCondition[i].length();
            if(lenget>0) {
                if (!preCondition[i].equals("(") && !preCondition[i].equals(")") && !preCondition[i].equals("【and】") && !preCondition[i].equals("【or】")) {
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
                } else if (preCondition[i].equals("【and】") || preCondition[i].equals("【or】")) {
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


    /**
     * 计算后缀表达式的值
     *
     * @param postCondition 后缀表达式
     * @return 返回布尔值
     */
    private static Boolean Postfix_Value(List<String> postCondition) {
        Stack<Boolean> stack = new Stack<Boolean>();    // 操作数栈
        int i = 0;
        Boolean x1, x2;
        while (!postCondition.get(i).equals("#")) {
            if (!postCondition.get(i).equals("(") && !postCondition.get(i).equals( ")") && !postCondition.get(i).equals( "【and】") && !postCondition.get(i).equals("【or】") && !postCondition.get(i).equals("#")) {
                stack.push(GetResult(postCondition.get(i)));
                i++;
            } else if (postCondition.get(i) .equals("【and】")) {
                x2 = stack.pop();
                x1 = stack.pop();
                stack.push(x1 && x2);
                i++;
            } else if (postCondition.get(i).equals("【or】") ) {
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
    /// 判断是否为操作符
    /// </summary>
    /// <param name="op">操作符</param>
    /// <returns>操作符优先级</returns>
    private  static int Priority(String op) {
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


    private static Boolean GetResult(String str) {
        //根据系统定义判断操作数的值
        String[] temps = str.split("[<|>|<=|>=|=|!=|⊇|!⊇]");
        String[] strList = Arrays.stream(temps).filter(x -> ValidateUtil.isEmpty(x) != true).toArray(String[]::new);
        String strOP = str.replaceAll(strList[0], "").replaceAll(strList[1], "").trim();
        //String strOP = str.replaceAll("【task】", "").replaceAll(strList[1], "").trim();

        if (strList[0] .equals ("【count】"))      // 按处理的活动数作为条件
        {
            Double count= Double.valueOf(strList[1]);
            switch (strOP) {
                case "<":
                    return (all_count - current_count) < count;
                case ">":
                    return (all_count - current_count) > count;
                case "=":
                    return (all_count - current_count) == count;
                case "<=":
                    return (all_count - current_count) <= count;
                case ">=":
                    return (all_count - current_count) >= count;
                case "!=":
                    return (all_count - current_count) != count;
                default:
                    return false;
            }
        } else if (strList[0] .equals ("【percent】"))       // 按处理的活动数所占百分比作为计算条件
        {
            Double count= Double.valueOf(strList[1]);
            switch (strOP) {
                case "<":
                    return (all_count - current_count) / all_count < count;
                case ">":
                    return (all_count - current_count) / all_count > count;
                case "=":
                    return (all_count - current_count) / all_count == count;
                case "<=":
                    return (all_count - current_count) / all_count <= count;
                case ">=":
                    return (all_count - current_count) / all_count >= count;
                case "!=":
                    return (all_count - current_count) / all_count != count;
                default:
                    return false;
            }
        } else if (strList[0] .equals ("【task】"))       // 按处理的活动数所占百分比作为计算条件
        {
            Double count= Double.valueOf(strList[1]);
            switch (strOP) {
                case "<":
                    return current_count < count;
                case ">":
                    return current_count > count;
                case "=":
                    return current_count == count;
                case "<=":
                    return current_count <= count;
                case ">=":
                    return current_count >= count;
                case "!=":
                    return current_count != count;
                default:
                    return false;
            }
        }
        return true;
    }
}
