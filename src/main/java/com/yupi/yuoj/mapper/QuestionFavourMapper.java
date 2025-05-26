package com.yupi.yuoj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionFavour;

/**
* @author WuXHa
* @description 针对表【question_favour(题目收藏)】的数据库操作Mapper
* @createDate 2025-05-26 20:26:22
* @Entity generator.domain.QuestionFavour
*/
public interface QuestionFavourMapper extends BaseMapper<QuestionFavour> {

    Page<Question> listFavourQuestionsByPage(IPage<Question> page, Wrapper<Question> queryWrapper, long favourUserId);
}





