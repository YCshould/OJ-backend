package com.yupi.yuoj.judge.codesandbox.model;

import com.yupi.yuoj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteCodeResponse {
    private List<String> ouputList;
    /**
     * 接口信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;
    private JudgeInfo judgeInfo;
}
