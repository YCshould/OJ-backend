package com.yupi.yuoj.judge;

import com.yupi.yuoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.yuoj.judge.strategy.JavaJudgeStrategy;
import com.yupi.yuoj.judge.strategy.JudgeContext;
import com.yupi.yuoj.judge.strategy.JudgeStrategyService;
import com.yupi.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * 策略模式
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategyService judgeStrategy=new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy=new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

    /**
     * 工厂模式
     * @param type
     * @return
     */
    public static JudgeStrategyService newInstance(String type){
        switch (type){
            case "default":
                return new DefaultJudgeStrategy();
            case "java":
                return new JavaJudgeStrategy();
            default:
                return new DefaultJudgeStrategy();
        }
    }
}