package com.example.demo.config;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.conditions.interfaces.Nested;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.handlers.CompositeEnumTypeHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.demo.entity.Demo;
import com.example.demo.service.impl.DemoServiceImpl;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.SqlSession;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.stream.Stream;

/**
 * MyBatis-Plus Native Image 反射配置
 * 通过编程方式注册所有需要的反射、代理和序列化支持
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(MybatisPlusRuntimeHintsRegistrar.class)
public class MybatisPlusRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

    // ==================== JDK 代理接口 ====================
    private static final Class<?>[] PROXY_INTERFACES = {
            Func.class, Join.class, Query.class, IPage.class, Nested.class,
            Compare.class, IService.class, SqlSession.class
    };

    // ==================== 全部反射权限的类 ====================
    private static final Class<?>[] FULL_REFLECTION_TYPES = {
            // MyBatis-Plus 核心类
            MybatisXMLLanguageDriver.class, MybatisSqlSessionFactoryBean.class,
            LambdaQueryWrapper.class, LambdaUpdateWrapper.class, UpdateWrapper.class, QueryWrapper.class,
            PageDTO.class, MybatisMapperProxy.class,
            LambdaQueryChainWrapper.class, LambdaUpdateChainWrapper.class, LambdaUtils.class,

            // Lambda 序列化支持
            SFunction.class, SerializedLambda.class, java.lang.invoke.SerializedLambda.class,


            // 项目实体和服务类
            Demo.class, DemoServiceImpl.class
    };

    // ==================== 方法调用权限的类 ====================
    private static final Class<?>[] METHOD_INVOKE_TYPES = {
            Wrapper.class, Wrappers.class, QueryWrapper.class, AbstractWrapper.class,
            AbstractChainWrapper.class, Page.class
    };

    // ==================== 构造器调用权限的类 ====================
    private static final Class<?>[] CONSTRUCTOR_INVOKE_TYPES = {
            CompositeEnumTypeHandler.class, FastjsonTypeHandler.class,
            GsonTypeHandler.class, JacksonTypeHandler.class, MybatisEnumTypeHandler.class
    };

    // ==================== 序列化的类 ====================
    private static final Class<?>[] SERIALIZATION_TYPES = {
            SFunction.class, SerializedLambda.class, java.lang.invoke.SerializedLambda.class
    };

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        registerProxies(hints);
        registerReflection(hints);
        registerSerialization(hints);
    }

    /**
     * 注册 JDK 代理
     */
    private void registerProxies(RuntimeHints hints) {
        Stream.of(PROXY_INTERFACES).forEach(hints.proxies()::registerJdkProxy);
    }

    /**
     * 注册反射
     */
    private void registerReflection(RuntimeHints hints) {
        // 全部反射权限
        Stream.of(FULL_REFLECTION_TYPES).forEach(type ->
                hints.reflection().registerType(type, builder -> builder.withMembers(MemberCategory.values()))
        );

        // 方法调用权限
        Stream.of(METHOD_INVOKE_TYPES).forEach(type ->
                hints.reflection().registerType(type, builder -> builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_PUBLIC_METHODS))
        );

        // 构造器调用权限
        Stream.of(CONSTRUCTOR_INVOKE_TYPES).forEach(type ->
                hints.reflection().registerType(type, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
        );

        // 其他特殊注册
        hints.reflection()
                .registerType(BoundSql.class, builder -> builder.withMembers(
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.ACCESS_DECLARED_FIELDS))
                .registerType(IEnum.class, MemberCategory.INVOKE_PUBLIC_METHODS)
                .registerType(MybatisParameterHandler.class, MemberCategory.ACCESS_DECLARED_FIELDS)
                .registerType(MybatisPlusInterceptor.class);
    }

    /**
     * 注册序列化
     */
    private void registerSerialization(RuntimeHints hints) {
        Stream.of(SERIALIZATION_TYPES).forEach(type ->
                hints.serialization().registerType(TypeReference.of(type)));
    }

}