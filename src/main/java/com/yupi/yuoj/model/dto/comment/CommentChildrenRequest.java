package com.yupi.yuoj.model.dto.comment;

import lombok.Data;
import java.io.Serializable;

@Data
public class CommentChildrenRequest implements Serializable {
    private Long parentId;
    private long current;
    private long pageSize;
} 