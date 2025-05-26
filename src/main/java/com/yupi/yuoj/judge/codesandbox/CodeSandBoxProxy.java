package com.yupi.yuoj.judge.codesandbox;

import com.yupi.yuoj.judge.codesandbox.impl.CodeSandBoxExample;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * CodeSandBox类的代理类
 * 目标在于增强CodeSandBox的功能，再原有类的基础上加上打印日志的功能
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {

    private CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    /**
     * 打印日志的功能
     * 使用流程
     *CodeSandBox codeSandBox = new CodeSandBoxExample();
     *codeSandBox=new CodeSandBoxProxy(codeSandBox);
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("Execute code request(请求信息): {}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("Execute code response(相应信息): {}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
