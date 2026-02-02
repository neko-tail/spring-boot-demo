package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author test
 * @since 2026-02-02
 */
@Data
@TableName("demo")
@Schema(description = "Demo 实体")
public class Demo {

    @Schema(description = "主键 ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "名称", example = "测试名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "名称不能为空")
    @TableField("name")
    private String name;

    @Schema(description = "描述", example = "这是一段描述")
    @TableField("`desc`")
    private String desc;
}
