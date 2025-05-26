package com.yupi.yuoj.judge.codesandbox;

import com.yupi.yuoj.judge.codesandbox.impl.CodeSandBoxExample;
import com.yupi.yuoj.judge.codesandbox.impl.CodeSandBoxRemote;
import com.yupi.yuoj.judge.codesandbox.impl.CodeSandBoxThirdParty;

/**
 * CodeSandBox工厂模式类
 */
public class CodeSandBoxFactory {
//    这个写在用的地方，直接读取配置文件codesandbox.type
//    @Value("${codesandbox.type:example}")
//    private String type;

    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example":
                return new CodeSandBoxExample();
            case "remote":
                return new CodeSandBoxRemote();
            case "thirdParty":
                return new CodeSandBoxThirdParty();
            default:
                return new CodeSandBoxExample();
        }
    }
}
