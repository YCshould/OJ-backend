package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.entity.Post;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionFavour;
import com.yupi.yuoj.model.entity.User;


/**
* @author WuXHa
* @description 针对表【question_favour(题目收藏)】的数据库操作Service
* @createDate 2025-05-26 20:26:22
*/
public interface QuestionFavourService extends IService<QuestionFavour> {
    /**
     * 帖子收藏
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doQuestionFavour(long postId, User loginUser);

    /**
     * 分页获取用户收藏的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Question> listFavourQuestionsByPage(IPage<Question> page, Wrapper<Question> queryWrapper,
                                        long favourUserId);

    /**
     * 帖子收藏（内部服务）
     *
     * @param userId
     * @param questionId
     * @return
     */
    int doQuestionFavourInner(long userId, long questionId);
}
