package com.yupi.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.judge.codesandbox.CodeSandBox;
import com.yupi.yuoj.judge.codesandbox.CodeSandBoxFactory;
import com.yupi.yuoj.judge.codesandbox.CodeSandBoxProxy;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.judge.strategy.JudgeContext;
import com.yupi.yuoj.judge.strategy.JudgeStrategyService;
import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import com.yupi.yuoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yuoj.service.QuestionService;
import com.yupi.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 详细判题逻辑
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        //根据提交题目信息获取题目信息
        Long questionId = questionSubmit.getQuestionId();

        //根据题目id获取题目信息
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WATING.getValue())) {
            return null;
        }

        //如果题目状态为等待，就开始判题，将题目状态设置为判题中
        QuestionSubmit newQuestionSubmit = new QuestionSubmit();
        newQuestionSubmit.setId(questionSubmitId);
        newQuestionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(newQuestionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目提交状态失败");
        }

        //调用代码沙箱接口进行判题
        //目前是示例代码沙箱
        //这部分使用不同的代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);//沙箱工厂
        codeSandBox = new CodeSandBoxProxy(codeSandBox);//代理工厂



        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inPutList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder() //这部分用executeCodeRequest.set()也可以
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .inputList(inPutList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOuputList();

        String judgeInfo = questionSubmit.getJudgeInfo();
        JudgeInfo judgeInfoBefore = JSONUtil.toBean(judgeInfo, JudgeInfo.class);
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(judgeInfoBefore);
        judgeContext.setInputList(inPutList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setJudgeCaseList(judgeCaseList);

        //策略模式更趋向于调用不同的方法，工厂模式更趋向于创建不同对象
        //调用判题策略
        //就这一部分是不同的，根据不同情况使用不同的判题逻辑
        JudgeInfo judgeInfoAfter = judgeManager.doJudge(judgeContext);
        //这部分代码是用工厂模式来创建DefaultJudgeStrategyService对象,在调用他的doJudge方法来进行判题
        //也就是说用工厂模式也可以实现上面策略模式的功能
        //JudgeStrategyService newInstance = JudgeManager.newInstance("default");
        //JudgeInfo judgeInfoAfter = newInstance.doJudge(judgeContext);


        //更新题目状态
        QuestionSubmit newQuestionSubmit1 = new QuestionSubmit();
        newQuestionSubmit1.setId(questionSubmitId);
        newQuestionSubmit1.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        newQuestionSubmit1.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(newQuestionSubmit1);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目提交状态失败");
        }
        return questionSubmitService.getById(questionSubmitId);
    }
}
