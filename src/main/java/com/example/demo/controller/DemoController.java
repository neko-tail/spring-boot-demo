package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Demo;
import com.example.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author test
 * @since 2026-02-02
 */
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    /**
     * 创建
     */
    @PostMapping
    public ResponseEntity<Result<Demo>> create(@Validated @RequestBody Demo demo) {
        Demo created = demoService.create(demo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success("创建成功", created));
    }

    /**
     * 根据 ID 获取
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Demo>> getById(@PathVariable Integer id) {
        Demo demo = demoService.getById(id);
        return ResponseEntity.ok(Result.success(demo));
    }

    /**
     * 列表
     */
    @GetMapping
    public ResponseEntity<Result<List<Demo>>> getAll() {
        List<Demo> demos = demoService.list();
        return ResponseEntity.ok(Result.success(demos));
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseEntity<Result<Page<Demo>>> getPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Demo> result = demoService.page(current, size);
        return ResponseEntity.ok(Result.success(result));
    }

    /**
     * 根据名称模糊查询
     */
    @GetMapping("/search")
    public ResponseEntity<Result<List<Demo>>> searchByName(@RequestParam String name) {
        List<Demo> demos = demoService.searchByName(name);
        return ResponseEntity.ok(Result.success(demos));
    }

    /**
     * 更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<Demo>> update(@PathVariable Integer id, @Validated @RequestBody Demo demo) {
        Demo updated = demoService.update(id, demo);
        return ResponseEntity.ok(Result.success("更新成功", updated));
    }

    /**
     * 部分更新
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Result<Demo>> partialUpdate(@PathVariable Integer id, @RequestBody Demo demo) {
        Demo updated = demoService.partialUpdate(id, demo);
        return ResponseEntity.ok(Result.success("更新成功", updated));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable Integer id) {
        demoService.delete(id);
        return ResponseEntity.ok(Result.success("删除成功", null));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public ResponseEntity<Result<Void>> deleteBatch(@RequestBody List<Integer> ids) {
        demoService.deleteBatch(ids);
        return ResponseEntity.ok(Result.success("批量删除成功", null));
    }
}
