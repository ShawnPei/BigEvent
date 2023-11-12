package org.shawn.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shawn.pojo.Article;
import org.shawn.pojo.Category;
import org.shawn.pojo.Result;

import org.shawn.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Slf4j
@Tag(name = "文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "发布文章")
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return  Result.success();
    }



}
