package com.yupi.yuoj.model.dto.comment;

import lombok.Data;
import java.io.Serializable;

/**
 * 评论点赞请求
 */
@Data
public class CommentThumbAddRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    private static final long serialVersionUID = 1L;
} 