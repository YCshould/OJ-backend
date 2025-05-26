package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.model.entity.Comment;

/**
* @author WuXHa
* @description 针对表【comment(题目评论)】的数据库操作Service
* @createDate 2025-05-26 16:52:48
*/
public interface CommentService extends IService<Comment> {


    /**
     * 分页获取一级评论
     */
    Page<Comment> listComments(Long questionId, long current, long pageSize);

    /**
     * 分页获取子回复
     */
    Page<Comment> listReplies(Long parentId, long current, long pageSize);
}
