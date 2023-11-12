package org.shawn.service;

import org.shawn.pojo.Article;
import org.shawn.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    void update(Article article);

    Article getById(Integer id);

    void deleteById(Integer id);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
