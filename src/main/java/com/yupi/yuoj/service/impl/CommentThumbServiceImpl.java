package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.mapper.CommentThumbMapper;
import com.yupi.yuoj.model.entity.Comment;
import com.yupi.yuoj.model.entity.CommentThumb;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.service.CommentService;
import com.yupi.yuoj.service.CommentThumbService;
import javax.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论点赞服务实现
 */
@Service
public class CommentThumbServiceImpl extends ServiceImpl<CommentThumbMapper, CommentThumb>
        implements CommentThumbService {

    @Resource
    private CommentService commentService;


    /**
     * 点赞
     *
     * @param commentId 评论id
     * @param loginUser 登录用户
     * @return 本次点赞变化数
     */
    @Override
    public int doCommentThumb(long commentId, User loginUser) {
        // 判断评论是否存在
        Comment comment = commentService.getById(commentId);
        if (comment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论不存在");
        }
        // 是否已点赞
        long userId = loginUser.getId();
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        CommentThumbService commentThumbService = (CommentThumbService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return commentThumbService.doCommentThumbInner(userId, commentId);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 本次点赞变化数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doCommentThumbInner(long userId, long commentId) {
        CommentThumb commentThumb = new CommentThumb();
        commentThumb.setUserId(userId);
        commentThumb.setCommentId(commentId);
        QueryWrapper<CommentThumb> thumbQueryWrapper = new QueryWrapper<>(commentThumb);
        CommentThumb oldCommentThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        // 已点赞
        if (oldCommentThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = commentService.update()
                        .eq("id", commentId)
                        .gt("thumbNum", 0)
                        .setSql("thumbNum = thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(commentThumb);
            if (result) {
                // 点赞数 + 1
                result = commentService.update()
                        .eq("id", commentId)
                        .setSql("thumbNum = thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }
} 