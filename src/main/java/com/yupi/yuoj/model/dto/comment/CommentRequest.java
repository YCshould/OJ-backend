package com.yupi.yuoj.model.dto.comment;

import lombok.Data;
import java.io.Serializable;

/**
 * 评论请求
 */
@Data
public class CommentRequest implements Serializable {
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 用户id（可选，由后端从登录状态获取）
     */
    private Long userId;
    
    /**
     * 父评论id（为0则是一级评论）
     */
    private Long parentId;
    
    /**
     * 题目id
     */
    private Long questionId;
    
    /**
     * 回复的用户id（可选）
     */
    private Long replyUserId;

    private static final long serialVersionUID = 1L;
} 