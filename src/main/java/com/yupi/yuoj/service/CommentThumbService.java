package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.entity.CommentThumb;
import com.yupi.yuoj.model.entity.User;

/**
 * 评论点赞服务
 */
public interface CommentThumbService extends IService<CommentThumb> {


    /**
     * 评论点赞
     *
     * @param commentId 评论id
     * @param loginUser 登录用户
     * @return 本次点赞变化数
     */
    int doCommentThumb(long commentId, User loginUser);

    /**
     * 评论点赞（内部事务方法）
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 本次点赞变化数
     */
    int doCommentThumbInner(long userId, long commentId);
} 