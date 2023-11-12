package org.shawn.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.shawn.mapper.ArticleMapper;
import org.shawn.pojo.Article;
import org.shawn.pojo.PageBean;
import org.shawn.service.ArticleService;
import org.shawn.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public Article getById(Integer id) {
        Article article = articleMapper.getById(id);
        return article;
    }

    @Override
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //利用mybatis插件pagehelper来完成分页
        PageHelper.startPage(pageNum,pageSize);//会自动把这两个参数，使用limit拼接到sql语句当中

        //调用mapper层
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(categoryId,state,userId);

        //强转是因为，调用了PageHelper之后，他返回的结果类型就是Page，Page就是List的一个实现类
        Page<Article> p = (Page<Article>) as;
        //page 对象里有方法获取total和items

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
