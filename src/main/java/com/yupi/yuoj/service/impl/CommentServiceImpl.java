package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.CommentMapper;
import com.yupi.yuoj.model.entity.Comment;
import com.yupi.yuoj.service.CommentService;
import com.yupi.yuoj.service.CommentThumbService;
import com.yupi.yuoj.model.entity.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* @author WuXHa
* @description 针对表【comment(题目评论)】的数据库操作Service实现
* @createDate 2025-05-26 16:52:48
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {



    /**
     * 获取评论列表
     * @param questionId
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public Page<Comment> listComments(Long questionId, long current, long pageSize) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("questionId", questionId).eq("parentId", 0).orderByDesc("createTime");
        return this.page(new Page<>(current, pageSize), wrapper);
    }

    /**
     * 获取评论回复列表
     * @param parentId
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public Page<Comment> listReplies(Long parentId, long current, long pageSize) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parentId", parentId).orderByAsc("createTime");
        return this.page(new Page<>(current, pageSize), wrapper);
    }
}




