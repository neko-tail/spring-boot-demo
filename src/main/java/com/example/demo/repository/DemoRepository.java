package com.example.demo.repository;

import com.example.demo.entity.Demo;
import com.example.demo.mapper.DemoMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author test
 * @since 2026-02-02
 */
@Service
public class DemoRepository extends CrudRepository<DemoMapper, Demo> {

}
