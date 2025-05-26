package com.yupi.yuoj.judge;


import com.yupi.yuoj.model.entity.QuestionSubmit;

/**
 * 判题服务接口
 */
public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
