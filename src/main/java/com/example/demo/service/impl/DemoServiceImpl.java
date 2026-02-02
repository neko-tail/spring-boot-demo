package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Demo;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemoRepository;
import com.example.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Demo 服务实现类
 *
 * @author test
 * @since 2026-02-02
 */
@Service
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService {

    private final DemoRepository demoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Demo create(Demo demo) {
        // 校验 name 是否已存在
        boolean exists = demoRepository.lambdaQuery()
                .eq(Demo::getName, demo.getName())
                .exists();
        if (exists) {
            throw new BusinessException("名称已存在");
        }

        boolean success = demoRepository.save(demo);
        if (!success) {
            throw new BusinessException("创建失败");
        }
        return demo;
    }

    @Override
    public Demo getById(Integer id) {
        Demo demo = demoRepository.getById(id);
        if (demo == null) {
            throw new ResourceNotFoundException("Demo", id);
        }
        return demo;
    }

    @Override
    public List<Demo> list() {
        return demoRepository.list();
    }

    @Override
    public Page<Demo> page(Integer current, Integer size) {
        Page<Demo> page = new Page<>(current, size);
        return demoRepository.page(page);
    }

    @Override
    public List<Demo> searchByName(String name) {
        return demoRepository.lambdaQuery()
                .like(Demo::getName, name)
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Demo update(Integer id, Demo demo) {
        Demo existingDemo = demoRepository.getById(id);
        if (existingDemo == null) {
            throw new ResourceNotFoundException("Demo", id);
        }

        demo.setId(id);
        boolean success = demoRepository.updateById(demo);
        if (!success) {
            throw new BusinessException("更新失败");
        }
        return demo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Demo partialUpdate(Integer id, Demo demo) {
        Demo existingDemo = demoRepository.getById(id);
        if (existingDemo == null) {
            throw new ResourceNotFoundException("Demo", id);
        }

        // 只更新非空字段
        if (demo.getName() != null) {
            existingDemo.setName(demo.getName());
        }
        if (demo.getDesc() != null) {
            existingDemo.setDesc(demo.getDesc());
        }

        boolean success = demoRepository.updateById(existingDemo);
        if (!success) {
            throw new BusinessException("更新失败");
        }
        return existingDemo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Demo existingDemo = demoRepository.getById(id);
        if (existingDemo == null) {
            throw new ResourceNotFoundException("Demo", id);
        }

        boolean success = demoRepository.removeById(id);
        if (!success) {
            throw new BusinessException("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("ID 列表不能为空");
        }
        boolean success = demoRepository.removeByIds(ids);
        if (!success) {
            throw new BusinessException("批量删除失败");
        }
    }
}
