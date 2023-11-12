package org.shawn.controller;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.shawn.pojo.Result;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Tag(name = "文章相关接口")
public class ArticleController {

    @GetMapping("/list")
    public Result<String> list() {
//
        return Result.success("登陆之后，才可以查询所有文章");
    }
}
