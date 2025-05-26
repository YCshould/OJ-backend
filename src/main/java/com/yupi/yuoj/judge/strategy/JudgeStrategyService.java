package com.yupi.yuoj.judge.strategy;

import com.yupi.yuoj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategyService {
    JudgeInfo doJudge(JudgeContext context);
}
