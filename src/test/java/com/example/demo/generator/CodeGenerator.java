package com.example.demo.generator;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;

public class CodeGenerator {

    @Test
    void gen() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test", "root", "test")
                .globalConfig(builder -> {
                    builder.author("test")
                            .outputDir("src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.demo")
                            .serviceImpl("repository");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("demo") // 设置需要生成的表名

                            .entityBuilder()
                            .disableSerialVersionUID()
                            .enableLombok(new ClassAnnotationAttributes("@Data","lombok.Data"))
                            .enableTableFieldAnnotation()
                            .fieldUseJavaDoc(true)

                            .mapperBuilder()
                            .disableMapperXml()
                            .mapperAnnotation(Mapper.class)

                            .serviceBuilder()
                            .disableService()
                            .superServiceImplClass(CrudRepository.class)
                            .formatServiceImplFileName("%sRepository")

                            .controllerBuilder()
                            .enableHyphenStyle()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
