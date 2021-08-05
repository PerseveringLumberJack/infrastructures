package com.oracle.example.config.datasource;

import java.lang.annotation.*;

/**
 * 多数据源注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default DataSourceNames.FIRST;
}