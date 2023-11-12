package org.shawn.service;

import org.shawn.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    Category getByCategoryName(String categoryName);

    void add(Category category);

    void update(Category category);

    Category findById(Integer id);

    void deleteById(Integer id);
}
