package com.yupi.yuoj.judge.codesandbox.impl;

import com.yupi.yuoj.judge.codesandbox.CodeSandBox;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.yuoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

public class CodeSandBoxExample implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setMessage("Success");
        executeCodeResponse.setStatus(1);
        executeCodeResponse.setOuputList(inputList);
        JudgeInfo judgeInfo=new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPT.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
