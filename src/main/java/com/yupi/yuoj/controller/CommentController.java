package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.entity.Comment;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.dto.comment.CommentParentRequest;
import com.yupi.yuoj.model.dto.comment.CommentChildrenRequest;
import com.yupi.yuoj.model.dto.comment.CommentThumbAddRequest;
import com.yupi.yuoj.model.dto.comment.CommentRequest;
import com.yupi.yuoj.service.CommentService;
import com.yupi.yuoj.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    /**
     * 发布评论（一级评论和回复评论）
     */
    @PostMapping("/add")
    public BaseResponse<Long> addComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        if (commentRequest == null || commentRequest.getQuestionId() == null || commentRequest.getContent() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        
        // 将DTO转换为实体
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setQuestionId(commentRequest.getQuestionId());
        comment.setParentId(commentRequest.getParentId() != null ? commentRequest.getParentId() : 0L);
        comment.setUserId(loginUser.getId());
        comment.setReplyUserId(commentRequest.getReplyUserId());
        
        boolean result = commentService.save(comment);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "评论失败");
        }
        return ResultUtils.success(comment.getId());
    }

    /**
     * 查看所有评论（分页，一级评论）
     */
    @PostMapping("/list")
    public BaseResponse<Page<Comment>> listComments(@RequestBody CommentParentRequest commentParentRequest) {
        if (commentParentRequest == null || commentParentRequest.getQuestionId() == null || commentParentRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Comment> page = commentService.listComments(commentParentRequest.getQuestionId(), commentParentRequest.getCurrent(), commentParentRequest.getPageSize());
        return ResultUtils.success(page);
    }

    /**
     * 查看子回复（分页）
     */
    @PostMapping("/reply/list")
    public BaseResponse<Page<Comment>> listReplies(@RequestBody CommentChildrenRequest commentChildrenRequest) {
        if (commentChildrenRequest == null || commentChildrenRequest.getParentId() == null || commentChildrenRequest.getParentId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Comment> page = commentService.listReplies(commentChildrenRequest.getParentId(), commentChildrenRequest.getCurrent(), commentChildrenRequest.getPageSize());
        return ResultUtils.success(page);
    }
} 