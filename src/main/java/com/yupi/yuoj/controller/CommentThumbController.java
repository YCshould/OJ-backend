package com.yupi.yuoj.controller;


import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.comment.CommentThumbAddRequest;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.service.CommentThumbService;
import com.yupi.yuoj.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment_thumb")
public class CommentThumbController {

    @Resource
    private UserService userService;

    @Resource
    private CommentThumbService commentThumbService;

    /**
     * 评论点赞
     */
    @PostMapping("/thumb")
    public BaseResponse<Integer> thumbComment(@RequestBody CommentThumbAddRequest commentThumbAddRequest,
                                              HttpServletRequest request) {
        if (commentThumbAddRequest == null || commentThumbAddRequest.getCommentId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        User loginUser = userService.getLoginUser(request);
        long commentId = commentThumbAddRequest.getCommentId();
        int result = commentThumbService.doCommentThumb(commentId,loginUser);
        return ResultUtils.success(result);
    }
}
