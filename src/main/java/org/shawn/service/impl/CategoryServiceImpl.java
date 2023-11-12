package org.shawn.service.impl;

import lombok.RequiredArgsConstructor;
import org.shawn.mapper.CategoryMapper;
import org.shawn.pojo.Category;
import org.shawn.service.CategoryService;
import org.shawn.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    @Override
    public List<Category> list() {
        Map<String,Object> o = ThreadLocalUtil.get();
        Integer userId = (Integer) o.get("id");
        List<Category> categoryList = categoryMapper.list(userId);
        return categoryList;
    }

    @Override
    public Category getByCategoryName(String categoryName) {

        return categoryMapper.getByCategoryName(categoryName);
    }

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);

        categoryMapper.add(category);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public Category findById(Integer id) {
        Category category = categoryMapper.findById(id);
        return category;
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
