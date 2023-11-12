package org.shawn.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.shawn.pojo.Article;
import org.shawn.pojo.Category;
import org.shawn.pojo.PageBean;
import org.shawn.pojo.Result;

import org.shawn.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Slf4j
@Tag(name = "文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "发布文章")
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return  Result.success();
    }

    @PutMapping
    @Operation(summary = "更新文章")
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return  Result.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "根据id查询文章")
    public Result<Article> getById(@RequestParam Integer id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    @DeleteMapping
    @Operation(summary = "根据ID删除文章")
    public Result deleteById(@RequestParam Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    @GetMapping
    @Operation(summary = "文章列表")
    public Result<PageBean<Article>> list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state){
        PageBean<Article> articles = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(articles);
    }

}
