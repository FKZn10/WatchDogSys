package com.jpmsoft.projectProducer.utils;


import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 表达式计算
 *
 */
public class ExpressionUtils {

    private static final Map<Character, Integer> PRIORITY = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('%', 2);
        put('(', 0);
    }};


    public static boolean check(String expr, List<Double> xValues, List<Double> aValues) {
        return evaluate(getExpression(expr, xValues, aValues));
    }


    private static String getExpression(String expr, List<Double> xValues, List<Double> aValues) {
        if (!CollectionUtils.isEmpty(xValues)) {
            for (int i = 0; i < xValues.size(); i++) {
                expr = expr.replaceFirst("X", String.valueOf(xValues.get(i)));
            }
        }

        if (!CollectionUtils.isEmpty(aValues)) {
            for (int i = 0; i < aValues.size(); i++) {
                expr = expr.replace("#A", String.valueOf(aValues.get(i)));
            }
        }
        return expr;
    }

    public static boolean evaluate(String expr) {
        if (expr.contains("and")) {
            String[] parts = expr.split("and");
            return evaluate(parts[0]) && evaluate(parts[1]);
        } else if (expr.contains("or")) {
            String[] parts = expr.split("or");
            return evaluate(parts[0]) || evaluate(parts[1]);
        }

        if (expr.contains(">=")) {
            String[] sides = expr.split(">=");
            return calculate(sides[0]) >= calculate(sides[1]);
        } else if (expr.contains("<=")) {
            String[] sides = expr.split("<=");
            return calculate(sides[0]) <= calculate(sides[1]);
        } else if (expr.contains("<>")) {
            String[] sides = expr.split("<>");
            return calculate(sides[0]) != calculate(sides[1]);
        } else if (expr.contains("=")) {
            String[] sides = expr.split("=");
            double left = calculate(sides[0]);
            double right = calculate(sides[1]);
            return Math.abs(left - right) < 1e-6;
        } else if (expr.contains(">")) {
            String[] sides = expr.split(">");
            return calculate(sides[0]) > calculate(sides[1]);
        } else if (expr.contains("<")) {
            String[] sides = expr.split("<");
            return calculate(sides[0]) < calculate(sides[1]);
        }
        return calculate(expr) != 0;
    }


    private static double calculate(String expr) {
        Stack<Double> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() &&
                        (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i++));
                }
                i--;
                nums.push(Double.parseDouble(num.toString()));
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    nums.push(compute(ops.pop(), nums.pop(), nums.pop()));
                }
                ops.pop();
            } else if (PRIORITY.containsKey(c)) {
                while (!ops.isEmpty() &&
                        PRIORITY.get(ops.peek()) >= PRIORITY.get(c)) {
                    nums.push(compute(ops.pop(), nums.pop(), nums.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            nums.push(compute(ops.pop(), nums.pop(), nums.pop()));
        }
        return nums.pop();
    }

    private static double compute(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '%':
                return a % b;
            default:
                throw new IllegalArgumentException("未知运算符: " + op);
        }
    }
}
