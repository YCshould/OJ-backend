package com.yupi.yuoj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteCodeRequest {
    private List<String> inputList;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 代码
     */
    private String code;
}
