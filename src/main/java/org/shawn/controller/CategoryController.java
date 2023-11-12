package org.shawn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shawn.pojo.Category;
import org.shawn.pojo.Result;
import org.shawn.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
@Tag(name = "文章分类相关接口")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "新增文章分类")
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        //查看文章分类是否存在
        String categoryName = category.getCategoryName();
        Category category1 = categoryService.getByCategoryName(categoryName);
        if(category1 != null) {
            return Result.error("该文章分类已存在");
        }
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    @Operation(summary = "获取全部文章分类")
    public Result<List<Category>> list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    @GetMapping("/detail")
    @Operation(summary = "获取文章分类详情")
    public Result<Category> categoryDetails(@RequestParam Integer id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    @Operation(summary = "更新文章分类")
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "删除文章分类")
    public Result delete(@RequestParam Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }
}
