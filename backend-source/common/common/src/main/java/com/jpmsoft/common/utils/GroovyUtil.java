package com.jpmsoft.common.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.SneakyThrows;

import javax.script.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class GroovyUtil {

    /**
     *
     * @param formula   具体公式
     * @param binding   公式中参数以及具体值
     * @return
     */
    public static Double calculateFormulaValue(String formula, Binding binding) {
        GroovyShell shell = new GroovyShell(binding);
        formula=formula.replaceAll("\\×","*").replaceAll("\\÷","/")
                .replaceAll("x","*").replaceAll("X","*");
        Object value =shell.evaluate(formula+"; return P ");
        return (Double) value;
    }

    @SneakyThrows
    public static Double calculateFormulaValueTwo(String formula, LinkedHashMap<String,Object> paramsMap) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        Compilable compilable = (Compilable) engine;
        Bindings bindings = engine.createBindings(); //Local级别的Binding
        CompiledScript JSFunction = null; //解析编译脚本函数
        formula=formula.replaceAll("\\×","*").replaceAll("\\÷","/")
                .replaceAll("x","*").replaceAll("X","*");
        JSFunction = compilable.compile(formula);
        if(!paramsMap.isEmpty()){
            for (Map.Entry<String, Object> item : paramsMap.entrySet()) {
                String key = item.getKey();
                Object value = item.getValue();
                bindings.put(key, value);
            }
        }
        Object result = JSFunction.eval(bindings);//调用缓存着的脚本函数对象，Bindings作为参数容器传入
        return (Double)result;
    }

    public static void main(String[] args) {
        String formula="P=(A×B+C×D+E×F)÷G";
        Binding binding = new Binding();
        binding.setVariable("A",7);
        binding.setVariable("B",0.8);
        binding.setVariable("C",8);
        binding.setVariable("D",0.8);
        binding.setVariable("E",12);
        binding.setVariable("F",0.8);
        binding.setVariable("G",1);
        calculateFormulaValue(formula,binding);
    }
}
