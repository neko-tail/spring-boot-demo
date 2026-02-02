package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author test
 * @since 2026-02-02
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}
