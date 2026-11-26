package com.jpmsoft.common.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * BeanUtil提供一系列与bean相关的操作.
 * todo bean相关操作需要提供到公共util包中
 *
 * @author liukaifeng
 * @date
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * The constant logger.
     */
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * The constant SERIAL_VERSION_UID.
     */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * The constant COMPARE_TYPE_INTEGER.
     */
    private static final String COMPARE_TYPE_INTEGER = "java.lang.Integer";
    /**
     * The constant COMPARE_TYPE_STRING.
     */
    private static final String COMPARE_TYPE_STRING = "java.lang.String";


    /**
     * Copy properties.
     *
     * @param source the source
     * @param target the target
     *
     * @throws BeansException the beans exception
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     * Copy properties.
     *
     * @param source           the source
     * @param target           the target
     * @param editable         the editable
     * @param ignoreProperties the ignore properties
     *
     * @throws BeansException the beans exception
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
        throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName()
                    + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            boolean ignoreCondition = ignoreList == null || !ignoreList.contains(targetPd.getName());
            if (writeMethod != null && ignoreCondition) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    boolean isAssignable = ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType());
                    if (readMethod != null && isAssignable) {
                        try {
                            setReadMethod(readMethod);
                            Object value = readMethod.invoke(source);
                            // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                            setWriteMethodMethod(value, writeMethod, target);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets read method.
     *
     * @param readMethod the read method
     */
    private static void setReadMethod(Method readMethod) {
        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
        }
    }

    /**
     * Sets write method method.
     *
     * @param value       the value
     * @param writeMethod the write method
     * @param target      the target
     *
     * @throws Exception the exception
     */
    private static void setWriteMethodMethod(Object value, Method writeMethod, Object target) throws Exception {
        if (value != null) {
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(target, value);
        }
    }


    /**
     * 复制对象.
     *
     * @param <T>       the type parameter
     * @param entity    the entity
     * @param targetCls the target cls
     *
     * @return the t
     */
    public static <T> T copyObject(Object entity, Class<? extends T> targetCls) {
        // 如果entity,直接返回null
        if (entity == null) {
            return null;
        }
        Object target = null;
        try {
            target = targetCls.newInstance();
            copyProperties(entity, target);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("error:{}", e);
        }
        return (T) target;
    }

    /**
     * 复制对象
     *
     * @param list      the list
     * @param targetCls the target cls
     * @param <T>       the type parameter
     * @return list list
     */
    public static <T> List<T> copyList(List<?> list, Class<T> targetCls) {
        List<T> resultList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Object entity : list) {
                resultList.add(copyObject(entity, targetCls));
            }
        }
        return resultList;
    }


    /**
     * java反射bean的get方法
     *
     * @param objectClass the object class
     * @param fieldName   the field name
     *
     * @return get method
     */
    @SuppressWarnings("unchecked")
    public static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error("error:{}", e);
        }
        return null;
    }


    /**
     * java反射bean的set方法
     *
     * @param objectClass the object class
     * @param fieldName   the field name
     *
     * @return set method
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:{}", e);
        }
        return null;
    }


    /**
     * 执行set方法
     *
     * @param o         执行对象
     * @param fieldName 属性
     * @param value     值
     */
    public static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            if(method != null) {
                method.invoke(o, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:{}", e);
        }
    }



    /**
     * 执行get方法
     *
     * @param o         执行对象
     * @param fieldName 属性
     *
     * @return the object
     */
    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            if(method != null) {
                return method.invoke(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:{}", e);
        }
        return null;
    }


    /**
     * 将返回值包装成String 如果是空则仍然保持空.
     *
     * @param o         the o
     * @param fieldName the field name
     *
     * @return the string
     */
    public static String invokeGetString(Object o, String fieldName) {
        Object obj = invokeGet(o, fieldName);
        return (obj == null) ? null : String.valueOf(obj);
    }


    /**
     * Merger obj object.
     *
     * @param <T>      项目域对象如果为空，则直接返回产品域对象项目域对象字段属性为空，则使用产品域覆盖项目域字段
     * @param parent   产品域对象
     * @param son      项目域对象
     * @param refClass class对象
     *
     * @return the object
     */
    public static <T> Object mergerObj(T parent, T son, Class<?> refClass) {
        Object obj = null;
        try {
            obj = refClass.newInstance();
            if (parent == null) {
                obj = son;
            } else if (son == null) {
                obj = parent;
            } else {
                for (Field field : refClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    if (SERIAL_VERSION_UID.equalsIgnoreCase(fieldName)) {
                        continue;
                    }
                    Object value = invokeGet(son, fieldName);
                    if (value == null) {
                        value = invokeGet(parent, fieldName);
                    }
                    invokeSet(obj, fieldName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error:{}", e);
        }
        logger.debug("Page object:" + obj);
        return obj;
    }


    /**
     * Merger obj object.
     *
     * @param <T>            the type parameter
     * @param parent         the parent
     * @param son            the son
     * @param refClassParent the ref class parent
     * @param refClassSon    the ref class son
     *
     * @return the object
     */
    public static <T> Object mergerObj(T parent, T son, Class<?> refClassParent, Class<?> refClassSon) {
        return mergerObj(parent, son, refClassParent, refClassSon, null);
    }


    /**
     * Merger obj object.
     *
     * @param <T>            父对象为空，则直接返回子对象<br>
     *                       子对象为空，则将子对象域父对象相同的字段拷贝给obj<br>
     *                       父对象与子对象都不为空，现将子对象的值拷贝给obj，在将子对象与父对象相同的字段拷贝给obj<br>
     *                       例如：比如指标与业务视图字段合并，则指标为父对象、业务视图字段为子对象变量命名有问题<br>
     * @param parent         父对象
     * @param son            子对象
     * @param refClassParent 父class对象
     * @param refClassSon    子class对象
     * @param prefix         前缀
     *
     * @return the object
     */
    public static <T> Object mergerObj(T parent, T son, Class<?> refClassParent,
                                       Class<?> refClassSon, String prefix) {
        Map<String, String> publicFieldMap = new HashMap<>(100);
        Object obj = null;
        try {
            //比较父class对象与子class对象，找出字段相同的字段
            for (Field pField : refClassParent.getDeclaredFields()) {
                for (Field sField : refClassSon.getDeclaredFields()) {
                    if (sField.getName().equals(pField.getName())) {
                        publicFieldMap.put(pField.getName(), sField.getName());
                        break;
                    } else if (prefix != null && sField.getName().equals(prefix + pField.getName())) {
                        publicFieldMap.put(pField.getName(), sField.getName());
                        break;
                    }
                }
            }

            obj = refClassSon.newInstance();
            //父对象为空，直接返回父对象给obj
            if (parent == null) {
                obj = son;
            } else
                //子对象为空，将父对象中与子对象中相同字段的值复制给obj
                if (son == null) {
                    for (Map.Entry<String, String> entry : publicFieldMap.entrySet()) {
                        if (SERIAL_VERSION_UID.equalsIgnoreCase(entry.getValue())) {
                            continue;
                        }
                        Object value = invokeGet(parent, entry.getValue());
                        invokeSet(obj, entry.getValue(), value);
                    }
                } else {
                    //先将子对象拷贝给obj
                    for (Field field : refClassSon.getDeclaredFields()) {
                        if (SERIAL_VERSION_UID.equalsIgnoreCase(field.getName())) {
                            continue;
                        }
                        Object value = invokeGet(son, field.getName());
                        invokeSet(obj, field.getName(), value);
                    }
                    //将相同字段的值拷贝给obj
                    for (Map.Entry<String, String> entry : publicFieldMap.entrySet()) {
                        String pFieldName = entry.getKey();
                        if (SERIAL_VERSION_UID.equalsIgnoreCase(entry.getValue())) {
                            continue;
                        }
                        Object value = invokeGet(son, entry.getValue());
                        if (null == value || "".equals(value)) {
                            value = invokeGet(parent, pFieldName);
                        }
                        invokeSet(obj, entry.getValue(), value);
                    }
                }
        } catch (Exception e) {
            logger.error("error:{}", e);
        }
        return obj;
    }


    /**
     * Division obj t.
     *
     * @param <T>      the type parameter
     * @param parent   父对象
     * @param son      子对象
     * @param newSon   新的子对象
     * @param refClass class对象
     *
     * @return T t
     */
    public static <T> T divisionObj(T parent, T son, T newSon, Class<?> refClass) {
        return divisionObj(parent, son, newSon, refClass, "");
    }


    /**
     * 比较产品域对象、项目域对象、页面对象，返回最后需要插入数据库中的对象
     *
     * @param <T>           the type parameter
     * @param parent        父对象
     * @param son           子对象
     * @param newSon        新的子对象
     * @param refClass      class对象
     * @param excludeFields 不需要比较字段的列表
     *
     * @return T t
     */
    public static <T> T divisionObj(T parent, T son, T newSon, Class<?> refClass, String... excludeFields) {
        for (Field field : refClass.getDeclaredFields()) {
            if (SERIAL_VERSION_UID.equalsIgnoreCase(field.getName())) {
                continue;
            }
            //排除不需要比较的字段
            List<String> excludeFieldList = Arrays.asList(excludeFields);
            if (!CollectionUtils.isEmpty(excludeFieldList) && excludeFieldList.contains(field.getName())) {
                continue;
            }
            /*项目域不存在，则说明该元素只是项目化没有修改，表里面不存在*/
            Object valueSon = (son == null) ? null : invokeGet(son, field.getName());
            /*子对象的对应的属性为空，则继承与产品域的属性，如果没有修改则继续继承*/
            if (valueSon == null) {
                Object valueNewSon = invokeGet(newSon, field.getName());
                Object valueParent = invokeGet(parent, field.getName());
                if (valueParent != null && valueParent.equals(valueNewSon)) {
                    invokeSet(newSon, field.getName(), null);
                }
            }
        }
        return newSon;
    }

    /**
     * 应用场景，产品域字段列表跟项目域字段列表合并，以keyName作为主键，相同的keyName，则使用项目域的覆盖产品域的
     *
     * @param <T>        the type parameter
     * @param parentList 父域列表
     * @param sonList    子域列表
     * @param keyName    主键
     *
     * @return list list
     */
    public static <T> List<T> mergerList(List<T> parentList, List<T> sonList, String keyName) {
        List<T> resultList = new ArrayList<T>();
        Map<Object, T> map = new HashMap<>(100);
        if (parentList == null) {
            resultList.addAll(sonList);
            return resultList;
        }
        if (sonList == null) {
            resultList.addAll(parentList);
            return resultList;
        }
        resultList.addAll(parentList);
        for (T obj : parentList) {
            Object keyFieldName = invokeGet(obj, keyName);
            map.put(keyFieldName, obj);
        }
        int size = sonList.size();
        for (int i = 0; i < size; i++) {
            Object keyFieldName = invokeGet(sonList.get(i), keyName);
            if (map.containsKey(keyFieldName)) {
                resultList.remove(map.get(keyFieldName));
                resultList.add(i, sonList.get(i));
            } else {
                resultList.add(sonList.get(i));
            }
        }
        return resultList;
    }


    /**
     * 返回dto的所有属性字段名称 返回其中所有字段(除serialVersionUID之外) 可以用于设置值.
     *
     * @param <T>      the type parameter
     * @param refClass the ref class
     *
     * @return the string [ ]
     */
    public static <T> String[] getAllFieldName(Class<?> refClass) {
        Field[] allField = refClass.getDeclaredFields();
        List<String> allFieldList = new ArrayList<String>();
        for (int i = 0, j = 0; i < allField.length; i++) {
            String filedName = allField[i].getName();
            if (!SERIAL_VERSION_UID.equalsIgnoreCase(filedName)) {
                allFieldList.add(filedName);
            }
        }
        return allFieldList.toArray(new String[allFieldList.size()]);
    }


    /**
     * 返回dto的所有Field字段 返回其中所有字段(除serialVersionUID之外) 可以用于设置值.
     *
     * @param <T>      the type parameter
     * @param refClass the ref class
     *
     * @return the field [ ]
     */
    public static <T> Field[] getAllField(Class<?> refClass) {
        Field[] allField = refClass.getDeclaredFields();
        List<Field> allFieldList = new ArrayList<Field>();
        for (int i = 0, j = 0; i < allField.length; i++) {
            if (!SERIAL_VERSION_UID.equalsIgnoreCase(allField[i].getName())) {
                allFieldList.add(allField[i]);
            }
        }
        return allFieldList.toArray(new Field[allFieldList.size()]);
    }




    /**
     * 蒋源对象的值复制成指定对象
     */
    public static <T> T copyData(Object source, Class<T> t, String... ignoreProperties) {
        T targetObj = null;
        try {
            targetObj = t.newInstance();

            BeanUtils.copyProperties(source, targetObj, ignoreProperties);

            Field[] fields = t.getDeclaredFields();
            //字段名相同, 但是类型不一样,相互转换,  Long-->String, String-->Long
            for (Field field : fields) {
                String fieldName = field.getName();

                Field sourceFile = null;
                //h
                try {
                    sourceFile = source.getClass().getDeclaredField(fieldName);
                } catch (Exception e) {
                    //logger.error("fail get field from source class", e);
                }
                if (sourceFile != null) {
                    // 源是Long类型 目标是String 类型   Long-->String
                    if (sourceFile.getType() == Long.class && field.getType() == String.class) {
                        String fieldValue = BeanUtilsBean.getInstance().getProperty(source, fieldName);
                        if (!StringUtils.isEmpty(fieldValue) || fieldValue == null) {
                            BeanUtils.invokeSet(targetObj, fieldName, fieldValue);
                        }
                    } else if (sourceFile.getType() == String.class && field.getType() == Long.class) {
                        // 源是类型String 目标是 Long类型   String-->Long
                        String fieldValue = BeanUtilsBean.getInstance().getProperty(source, fieldName);
                        if (!StringUtils.isEmpty(fieldValue) || fieldValue == null) {
                            BeanUtils.invokeSet(targetObj, fieldName, Long.parseLong(fieldValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("copy data fail instance class:{}", e);
        }

        return targetObj;
    }

    /**
     * 将源List中的数据 复制为指定类型的新List中
     */
    public static <T> List<T> copyListData(List<?> sources, Class<T> t, String... ignoreProperties) {
        List<T> all = new ArrayList<>();

        try {
            for (Object source : sources) {
                T targetObj = copyData(source, t, ignoreProperties);
                all.add(targetObj);
            }
        } catch (Exception e) {
            logger.error("copy list data fail instance class:{}", e);
        }

        return all;
    }

}

