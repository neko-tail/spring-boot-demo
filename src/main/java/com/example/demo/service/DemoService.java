package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Demo;

import java.util.List;

/**
 * Demo 服务接口
 *
 * @author test
 * @since 2026-02-02
 */
public interface DemoService {

    /**
     * 创建
     *
     * @param demo 实体
     * @return 创建后的实体
     */
    Demo create(Demo demo);

    /**
     * 根据 ID 获取
     *
     * @param id ID
     * @return 实体
     */
    Demo getById(Integer id);

    /**
     * 获取所有
     *
     * @return 实体列表
     */
    List<Demo> list();

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    每页大小
     * @return 分页结果
     */
    Page<Demo> page(Integer current, Integer size);


    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 实体列表
     */
    List<Demo> searchByName(String name);

    /**
     * 更新
     *
     * @param id   ID
     * @param demo 实体
     * @return 更新后的实体
     */
    Demo update(Integer id, Demo demo);

    /**
     * 部分更新
     *
     * @param id   ID
     * @param demo 实体
     * @return 更新后的实体
     */
    Demo partialUpdate(Integer id, Demo demo);

    /**
     * 删除
     *
     * @param id ID
     */
    void delete(Integer id);

    /**
     * 批量删除
     *
     * @param ids ID 列表
     */
    void deleteBatch(List<Integer> ids);
}
