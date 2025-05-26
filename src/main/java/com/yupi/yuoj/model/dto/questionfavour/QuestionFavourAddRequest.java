package com.yupi.yuoj.model.dto.questionfavour;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionFavourAddRequest implements Serializable {
    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}
