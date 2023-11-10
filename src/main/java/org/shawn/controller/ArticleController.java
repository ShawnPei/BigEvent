package org.shawn.controller;


import org.shawn.pojo.Result;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public Result<String> list() {
//
        return Result.success("登陆之后，才可以查询所有文章");
    }
}