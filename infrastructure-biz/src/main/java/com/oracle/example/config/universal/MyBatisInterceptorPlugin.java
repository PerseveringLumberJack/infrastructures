package com.oracle.example.config.universal;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

@Slf4j
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
public class MyBatisInterceptorPlugin  implements Interceptor {


    private Snowflake snowflake = new Snowflake(2,3,true);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();

        MappedStatement arg = (MappedStatement) args[0];

        if (SqlCommandType.INSERT.name().equals(arg.getSqlCommandType().name())) {

            ParameterMap parameterMap = arg.getParameterMap();

            Class<?> type = parameterMap.getType();

            Field[] fields = type.getDeclaredFields();

            Object arg1 = args[1];

            for (Field field : fields) {
                IdAnnotation annotation = field.getAnnotation(IdAnnotation.class);
                if (annotation != null) {
                    setField(arg1, field.getName(), snowflake.nextId());
                }
            }

        }
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {}

    private void setField(Object o,String args,Object attributeValue){

        Class cls = o.getClass();

        //判断该属性是否存在
        Field field = null;
        try {

            field = cls.getDeclaredField(args);
            if(field == null){

                field = cls.getField(args);
            }
            if(field == null){

                return;
            }
        } catch (NoSuchFieldException e) {

            log.error("[插入保存异常.e:{}]", e.getMessage());
        }

        String fieldName = "set"+args.substring(0,1).toUpperCase()+(args.length()>1?args.substring(1):"");

        Method method = null;

        try {
            method = cls.getMethod(fieldName,attributeValue.getClass());
            method.invoke(o,attributeValue);
        } catch (Exception e) {
            log.error("[插入保存异常.e:{}]", e.getMessage());
        }
    }
}
