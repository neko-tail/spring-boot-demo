package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class Demo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "名称不能为空")
    @TableField("name")
    private String name;

    @TableField("`desc`")
    private String desc;
}
