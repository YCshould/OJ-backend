package com.yupi.yuoj.model.dto.comment;

import lombok.Data;
import java.io.Serializable;

@Data
public class CommentParentRequest implements Serializable {
    private Long questionId;
    private long current;
    private long pageSize;
} 