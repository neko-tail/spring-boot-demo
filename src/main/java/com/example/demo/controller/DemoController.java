package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Demo;
import com.example.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Demo 管理", description = "Demo CRUD 接口")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    /**
     * 创建
     */
    @Operation(summary = "创建 Demo", description = "创建一个新的 Demo 记录")
    @PostMapping
    public ResponseEntity<Result<Demo>> create(@Validated @RequestBody Demo demo) {
        Demo created = demoService.create(demo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success("创建成功", created));
    }

    /**
     * 根据 ID 获取
     */
    @Operation(summary = "根据 ID 获取", description = "根据 ID 查询 Demo 详情")
    @GetMapping("/{id}")
    public ResponseEntity<Result<Demo>> getById(
            @Parameter(description = "Demo ID") @PathVariable Integer id) {
        Demo demo = demoService.getById(id);
        return ResponseEntity.ok(Result.success(demo));
    }

    /**
     * 列表
     */
    @Operation(summary = "获取列表", description = "获取所有 Demo 列表")
    @GetMapping
    public ResponseEntity<Result<List<Demo>>> getAll() {
        List<Demo> demos = demoService.list();
        return ResponseEntity.ok(Result.success(demos));
    }

    /**
     * 分页查询
     */
    @Operation(summary = "分页查询", description = "分页获取 Demo 列表")
    @GetMapping("/page")
    public ResponseEntity<Result<Page<Demo>>> getPage(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        Page<Demo> result = demoService.page(current, size);
        return ResponseEntity.ok(Result.success(result));
    }

    /**
     * 根据名称模糊查询
     */
    @Operation(summary = "搜索", description = "根据名称模糊搜索 Demo")
    @GetMapping("/search")
    public ResponseEntity<Result<List<Demo>>> searchByName(
            @Parameter(description = "搜索关键词") @RequestParam String name) {
        List<Demo> demos = demoService.searchByName(name);
        return ResponseEntity.ok(Result.success(demos));
    }

    /**
     * 更新
     */
    @Operation(summary = "更新 Demo", description = "根据 ID 更新 Demo 全部字段")
    @PutMapping("/{id}")
    public ResponseEntity<Result<Demo>> update(
            @Parameter(description = "Demo ID") @PathVariable Integer id,
            @Validated @RequestBody Demo demo) {
        Demo updated = demoService.update(id, demo);
        return ResponseEntity.ok(Result.success("更新成功", updated));
    }

    /**
     * 部分更新
     */
    @Operation(summary = "部分更新 Demo", description = "根据 ID 更新 Demo 部分字段")
    @PatchMapping("/{id}")
    public ResponseEntity<Result<Demo>> partialUpdate(
            @Parameter(description = "Demo ID") @PathVariable Integer id,
            @RequestBody Demo demo) {
        Demo updated = demoService.partialUpdate(id, demo);
        return ResponseEntity.ok(Result.success("更新成功", updated));
    }

    /**
     * 删除
     */
    @Operation(summary = "删除 Demo", description = "根据 ID 删除 Demo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(
            @Parameter(description = "Demo ID") @PathVariable Integer id) {
        demoService.delete(id);
        return ResponseEntity.ok(Result.success("删除成功", null));
    }

    /**
     * 批量删除
     */
    @Operation(summary = "批量删除 Demo", description = "根据 ID 列表批量删除 Demo")
    @DeleteMapping("/batch")
    public ResponseEntity<Result<Void>> deleteBatch(@RequestBody List<Integer> ids) {
        demoService.deleteBatch(ids);
        return ResponseEntity.ok(Result.success("批量删除成功", null));
    }
}
