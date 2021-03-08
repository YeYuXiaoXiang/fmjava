package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.ad.ContentDao;
import com.fmjava.core.pojo.ad.Content;
import com.fmjava.core.pojo.ad.ContentQuery;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.utils.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentDao contentDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findPage(Content content, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        ContentQuery query = new ContentQuery();
        ContentQuery.Criteria criteria = query.createCriteria();
        if (content != null) {
            if (content.getTitle() != null && !"".equals(content.getTitle())) {
                criteria.andTitleLike("%"+content.getTitle()+"%");
            }
        }
        Page<Content> contentList = (Page<Content>)contentDao.selectByExample(query);
        return new PageResult(contentList.getTotal(), contentList.getResult());
    }

    @Override
    public void add(Content content) {
        //1. 将新广告添加到数据库中
        contentDao.insertSelective(content);
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());

    }

    @Override
    public Content findOne(Long id) {
        return contentDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Content content) {
        Content oldContent = contentDao.selectByPrimaryKey(content.getId());
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(oldContent.getCategoryId());
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());
        //将新的广告对象更新到数据库中
        contentDao.updateByPrimaryKeySelective(content);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                //1. 根据广告id, 到数据库中查询广告对象
                Content content = contentDao.selectByPrimaryKey(id);

                redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());


                //3. 根据广告id删除数据库中的广告数据
                contentDao.deleteByPrimaryKey(id);
            }
        }
    }


    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        ContentQuery query = new ContentQuery();
        ContentQuery.Criteria criteria = query.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<Content> list = contentDao.selectByExample(query);
        return list;
    }

    @Override
    public List<Content> findByCategoryIdFromRedis(Long categoryId) {

        List<Content> contentList = (List<Content>)redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).get(categoryId);

        if (contentList == null){
            contentList = this.findByCategoryId(categoryId);
            redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).put(categoryId,contentList);
            System.out.println("数据库");
        }
        System.out.println("从Redis中取到数据");
        return contentList;


    }

}
